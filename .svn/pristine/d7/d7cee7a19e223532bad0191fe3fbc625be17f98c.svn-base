package com.tsing.location;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.dsw.adapters.ListAdapter;
import com.dsw.bean.Room;
import com.dsw.config.Config;
import com.dsw.db.daoimpl.RYGJDaoImpl;
import com.dsw.global.Constant;
import com.dsw.properties.tool.IO;
import com.tsingoal.com.TSimpleAlarmInfo;
import com.zl.jydam.controller.AlarmListener;

public class Alarm {
	private static int tagID = 32338;// 测试时使用的一个手环ID
	private static final ListAdapter mListAdapter = ListAdapter.getInstanceListAdapter();

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

	public static void sendAlarmMessage(TSimpleAlarmInfo alarmInfo) {
		/* 预警1 */

		/* 写入数据库预警表 */
		int flag = 0;
		try {
			Config config = Config.getInstance();
			flag = RYGJDaoImpl.getInstance().insertYJ(String.valueOf(alarmInfo.getRelatedTagId()), config.getWarn_org(),
					config.getWarn_level(), "进入了未授权区域", "触发", "电子围栏警报");// 测试定位效果时注释掉，可以不往数据库写数据
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (flag == 1) {
			final SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

			// System.out.println(tPosInfo.getTagId() + "
			// 进入了未授权区域："
			// + room.getRoomName());// log

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
							Integer.parseInt(Config.getInstance().getReal_alarm_index()), 1, true);
				}
			}).start();

			// 报警信息写入控制面板
			mListAdapter.writeMessageToConsole(
					"<html><font color=red>" + alarmInfo.getRelatedTagId() + alarmInfo.getAlarmDesc()
							+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + sdf.format(new Date()) + "</font></html>");

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
