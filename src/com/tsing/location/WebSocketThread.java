package com.tsing.location;

import com.dsw.config.Config;
import com.dsw.global.Constant;
import com.dsw.properties.tool.IO;
import com.tsingoal.com.*;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.List;

import javax.annotation.PostConstruct;

/**
 * 实现WebSocket监听的线程类
 * 
 * @author 郑龙
 *
 */
public class WebSocketThread extends Thread {
	private static String LOCK = "";// 锁
	private static boolean suspend = false;// 线程暂停标识
	private static WebSocketClient mClient = new WebSocketClient("admin", "ca2924d86691a890bd96ad5e11620c4a");

	@Override
	public void run() {
		// synchronized (LOCK) {
		// 设置websocket服务地址
		mClient.setHost(Config.getInstance().getPosition_host());
		// 设置websocket服务端口
		mClient.setServerPort(9001);
		// 设置websocket子协议
		mClient.setProtocal("localSensePush-protocol");
		// 连接服务器
		mClient.connectToServer();
		// }

	}

	/**
	 * 设置点击线程开启按钮时的监听事件，方便随时暂停，启动线程
	 * 
	 * @param suspend
	 */
	public static void setListening(boolean suspend) {
		/* 先设置好房间的数据 */
		String json = null;
		String json2 = null;

		if (IO.judeFileExists(new File(Constant.propertiesFileURL))
				&& IO.judeFileExists(new File(Constant.OtherSetingURL))) {
			json = IO.readtxt(Constant.propertiesFileURL);
			json2 = IO.readtxt(Constant.OtherSetingURL);
			System.out.println(json2);
		}

		/* 装载定位配置信息 */
		Location.decodeProperties(json);
		Location.reLoadSetting(json2);

		/* 加载预警信息 */
		Alarm.reLoadSetting(json2);

		/* 设置线程启停 */
		if (!suspend) {
			synchronized (LOCK) {
				mClient.stop();
			}
		} else {
			mClient.reStart();
		}
		WebSocketThread.suspend = suspend;
	}
}
