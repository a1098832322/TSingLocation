package com.tsing.location;

import java.io.File;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.dsw.adapters.ListAdapter;
import com.dsw.bean.Point;
import com.dsw.bean.Room;
import com.dsw.config.Config;
import com.dsw.db.dao.RYGJDao;
import com.dsw.db.daoimpl.RYGJDaoImpl;
import com.dsw.global.Constant;
import com.dsw.httpclient.OkhttpClient;
import com.dsw.properties.tool.IO;
import com.mysql.fabric.xmlrpc.base.Data;
import com.dsw.db.daoimpl.RYGJDaoImpl;
import com.tsingoal.com.RtlsWebsocketClient;
import com.tsingoal.com.RtlsWsManager;
import com.tsingoal.com.TCapacityInfo;
import com.tsingoal.com.TPersonStatistics;
import com.tsingoal.com.TPosInfo;
import com.tsingoal.com.TRichAlarmInfo;
import com.tsingoal.com.TSimpleAlarmInfo;
import com.zl.jydam.controller.AlarmListener;

/**
 * 人员定位的功能类
 *
 * @author 郑龙
 */
public class Alert {
	private static int tagID = 25941;// 测试时使用的一个手环ID
	private static List<Room> roomList;// 从配置文件中读取房间列表
	private static final ListAdapter mListAdapter = ListAdapter.getInstanceListAdapter();

	/**
	 * 将读取的房间配置信息转换成Room List
	 */
	public static void decodeProperties(String json) {
		System.out.println(json);// log
		roomList = JSONArray.parseArray(json, Room.class);
	}

	/**
	 * 将配置文件转成JavaBean
	 */
	public static void reLoadSetting(String json) {
		System.out.println(json);
		if (IO.judeFileExists(new File(Constant.OtherSetingURL))) {
			json = IO.readtxt(Constant.OtherSetingURL);
		}

		Config.getInstance().setNewConfig(JSON.parseObject(json, new TypeReference<Config>() {
		}));
	}

	/**
	 * 主要功能方法，从SDK包中获得所有定位标签的位置数据，并可以实现预警，数据库交互等操作。
	 */
	public static void sendLocation(List<TPosInfo> posList) {
		List<TPosInfo> targetList = new ArrayList<>();// 如果有多个设备进入禁止空间的话，则存入这个列表中（明天写
		for (final TPosInfo tPosInfo : posList) {
			// if (tPosInfo.getTagId() == tagID) {//测试时删掉注释
			final Point point = new Point(tPosInfo.getPosX(), tPosInfo.getPosY(), tPosInfo.getTagId() + "");

			// 判断在哪间房
			for (final Room room : roomList) {
				if (room.isExist(point) != null) {
					RYGJDaoImpl.getInstance().insertGJ(point.getSbId() + "", room.getRoomName());// 测试定位效果时注释掉，可以不往数据库写数据

					if (room.isYj_flag()) {
						/* 写入数据库预警表 */
						int flag = 0;
						try {
							Config conig = Config.getInstance();
							flag = RYGJDaoImpl.getInstance().insertYJ(String.valueOf(tPosInfo.getTagId()),
									conig.getWarn_org(), conig.getWarn_level(), "进入了未授权区域", "触发", "电子围栏警报");// 测试定位效果时注释掉，可以不往数据库写数据
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (flag == 1) {
							final SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

							// System.out.println(tPosInfo.getTagId() + "
							// 进入了未授权区域："
							// + room.getRoomName());// log

							mListAdapter.writeMessageToConsole("<html><font color=red>" + tPosInfo.getTagId()
									+ " 进入了未授权区域：" + room.getRoomName() + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
									+ sdf.format(new Date()) + "</font></html>");

							new Thread(new Runnable() {
								// 触发警报的线程
								@Override
								public void run() {
									// 方式1：直接使用服务器端部署的驱动包触发报警请求
									// 实例化http请求对象
									// OkhttpClient mClient = new
									// OkhttpClient();
									// mClient.triggerAlarm();// 触发警报

									// 方式2：使用自有驱动包触发警报
									// OkhttpClient mClient = new
									// OkhttpClient();
									// mClient.triggerAlarm(Config.getInstance().getReal_alarm_ip(),
									// Config.getInstance().getReal_alarm_port(),
									// Config.getInstance().getReal_alarm_index());//
									// 触发警报

									// 方法3：使用警报线程触发警报（最优方案，可以防止多线程情况下继电器板拒绝连接的情况发生）
									AlarmListener listener = new AlarmListener();
									listener.sendMessage(Config.getInstance().getReal_alarm_ip(), 10000,
											Integer.parseInt(Config.getInstance().getReal_alarm_index()), 1,false);
								}
							}).start();
						}

					}
				}
			}
		}
	}

	/**
	 * @return the tagID
	 */
	public static int getTagID() {
		return tagID;
	}

	/**
	 * @param tagID
	 *            the tagID to set
	 */
	public static void setTagID(int ID) {
		tagID = ID;
	}
}
