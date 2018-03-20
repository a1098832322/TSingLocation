package com.tsing.location;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft_17;

import com.tsingoal.com.RtlsWebsocketClient;
import com.tsingoal.com.RtlsWsManager;
import com.tsingoal.com.TCapacityInfo;
import com.tsingoal.com.TPersonStatistics;
import com.tsingoal.com.TPosInfo;
import com.tsingoal.com.TRichAlarmInfo;
import com.tsingoal.com.TSimpleAlarmInfo;

/**
 * 重写清研讯科SDK开发包，主要实现自由启停线程的功能
 *
 * @author 郑龙
 * @see RtlsWsManager
 */
public class WebSocketClient extends RtlsWsManager {
	private String LOCK = "";// 锁

	private String serverIp = null;// 定位服务器ip
	private int serverPort = 0;// 定位服务器端口
	private String wsProtocal = null;
	private RtlsWebsocketClient wc = null;
	private String wsUserName = null;// 登录用户名
	private String wsUserPasswd = null;// 登录密码（加密后）

	private boolean isStop = false;// 定位线程停止标识

	/**
	 * 设置服务端地址
	 */
	@Override
	public void setHost(String server_ip) {
		serverIp = server_ip;
	}

	/**
	 * 设置服务端端口号
	 */
	@Override
	public void setServerPort(int port) {
		serverPort = port;
	}

	/**
	 * 设置websocket子协议
	 */
	@Override
	public void setProtocal(String protocal) {
		wsProtocal = protocal;
	}

	/**
	 * 设置websocket登录的用户名
	 */
	@Override
	public void setWsUserName(String wsUserName) {
		this.wsUserName = wsUserName;
	}

	/**
	 * 设置websocket登录密码（加密后的）
	 */
	@Override
	public void setWsUserPasswd(String wsUserPasswd) {
		this.wsUserPasswd = wsUserPasswd;
	}

	/**
	 * 连接服务端
	 */
	@Override
	public void connectToServer() {
		newAndConnecWebSocket();
		timerCheckWebSocket();
	}

	@Override
	public String getWsUserName() {
		return wsUserName;
	}

	@Override
	public String getWsUserPasswd() {
		return wsUserPasswd;
	}

	@Override
	public void send(String str) {
		if (getWc() != null) {
			getWc().send(str);
		}
	}

	@Override
	public void send(byte[] bs) {
		if (getWc() != null) {
			getWc().send(bs);
		}
	}

	/**
	 * 重新开始
	 */
	public void reStart() {
		isStop = false;
	}

	/**
	 * 停止监听
	 */
	public void stop() {
		System.out.println("stop()");
		getWc().close();
		isStop = true;
	}

	private RtlsWebsocketClient getWc() {
		return wc;
	}

	private void setWc(RtlsWebsocketClient wc) {
		this.wc = wc;
	}

	public WebSocketClient() {
	}

	public WebSocketClient(String wsUserName, String wsUserPasswd) {
		this.isStop = false;
		this.wsUserName = wsUserName;
		this.wsUserPasswd = wsUserPasswd;
	}

	/**
	 * 连接Server
	 */
	private void newAndConnecWebSocket() {
		if (getWc() != null) {
			getWc().close();
			setWc(null);// 清空
		}

		Map<String, String> headers = new HashMap<String, String>();
		if (wsProtocal != null)
			headers.put("Sec-WebSocket-Protocol", wsProtocal);

		try {
			RtlsWebsocketClient webSocketClient = new RtlsWebsocketClient(
					new URI("ws://" + serverIp + ":" + serverPort), new Draft_17(), headers, 0);
			webSocketClient.setUserName(wsUserName);
			webSocketClient.setPasswd(wsUserPasswd);
			setWc(webSocketClient);
			wc.setManager(this);
			webSocketClient.connectBlocking();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 守护线程，当定位线程被关闭时，由此线程再次打开。
	 */
	private void timerCheckWebSocket() {
		// 定时检测websocket连接状态
		Timer dogCheckTimer = new Timer();
		dogCheckTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				WebSocket.READYSTATE dogReadyState = getWc().getReadyState();
				if (!dogReadyState.equals(WebSocket.READYSTATE.OPEN) && !isStop) {
					try {
						newAndConnecWebSocket();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}, 5000, 5000);
	}

	@Override
	public void OnCapacityInfo(List<TCapacityInfo> tagCapcityList) {
		// TODO Auto-generated method stub

	}

	/**
	 * 获取定位目标位置
	 */
	@Override
	public void OnPosInfo(final List<TPosInfo> posList) {
		Location.sendLocation(posList);
	}

	@Override
	public void OnSimpleAlarm(TSimpleAlarmInfo alarm) {
		// TODO Auto-generated method stub
		Alarm.sendAlarmMessage(alarm);
	}

	@Override
	public void OnRichAlarm(TRichAlarmInfo alarm) {
		// TODO Auto-generated method stub
	}

	@Override
	public void OnPersonStatistics(TPersonStatistics statisticsInfo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnUnknownMessage(ByteBuffer blob) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnMessage(String s) {
		// TODO Auto-generated method stub

	}

}
