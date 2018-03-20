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
public class Location {
	private static int tagID = 32338;// 测试时使用的一个手环ID
	private static List<Room> roomList;// 从配置文件中读取房间列表

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
			// if (tPosInfo.getTagId() == tagID) {// 测试时删掉注释
			// System.out.println(tPosInfo.getTagId());
			// }
			final Point point = new Point(tPosInfo.getPosX(), tPosInfo.getPosY(), tPosInfo.getTagId() + "");
			if (tPosInfo.getTagId() == tagID) {// 测试时删掉注释
				System.out.println(point);
			}
			// 判断在哪间房
			for (final Room room : roomList) {
				if (room.isExist(point) != null) {
					RYGJDaoImpl.getInstance().insertGJ(point.getSbId() + "", room.getRoomName());// 测试定位效果时注释掉，可以不往数据库写数据
					if (tPosInfo.getTagId() == tagID) {
						System.out.println("tagID:" + tagID + "\t坐标:" + point + "\t在:" + room.getRoomName());
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
