package com.dsw.httpclient;

import java.io.IOException;
import com.dsw.config.Config;
import com.dsw.global.Constant;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.dsw.hardware.*;

/**
 * 使用OKHttp3的小客户端，主要用于开启警铃 此类中的方法放在定位线程中，可能会引发：多线程高并发连接继电器板导致继电器板长时间拒绝连接
 * 的问题，因此在后续版本中废弃，具体触发警报方法详见代码内：方法3 使用专有警报线程触发警报器
 * 
 * @author 郑龙
 *
 */
public class OkhttpClient {
	private static boolean isSuccess = false;

	/**
	 * 使用硬件驱动包触发警报器方法
	 * 
	 * @param 继电器板ip地址
	 * @param 继电器板端口
	 * @param 继电器板路数
	 */
	public void triggerAlarm(String ip, String port, String idx) {
		try {
			ModbusUtil.writeDigitalOutput(ip, Integer.parseInt(port), 254, Integer.parseInt(idx), 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 使用硬件驱动包停止警报器方法
	 * 
	 * @param 继电器板ip地址
	 * @param 继电器板端口
	 * @param 继电器板路数
	 */
	public void shutdownAlarm(String ip, String port, String idx) {
		try {
			ModbusUtil.writeDigitalOutput(ip, Integer.parseInt(port), 254, Integer.parseInt(idx), 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 使用服务器上部署的硬件包来触发警铃的方法
	 */
	public void triggerAlarm() {
		System.out.println("open");
		String url = Config.getInstance().getAlarm_url() + "1";
		System.out.println("打开警报：\t" + url);
		alarmOptions(url);
	}

	/**
	 * 使用服务器上部署的硬件包来停止警铃的方法
	 */
	public void shutdownAlarm() {
		System.out.println("shutdown");
		String url = Config.getInstance().getAlarm_url() + "0";
		System.out.println("关闭警报：\t" + url);
		alarmOptions(url);
	}

	/**
	 * 封装好的警铃状态操作 使用服务器上部署的硬件包
	 * 
	 * @param url
	 * @return 操作状态true,false
	 */
	private boolean alarmOptions(String url) {
		// 实例化网络请求客户端
		OkHttpClient mClient = new OkHttpClient();
		// 构建请求URL
		Request request = new Request.Builder().url(url).get().build();
		isSuccess = false;
		try {
			Response response = mClient.newCall(request).execute();
			if (response.isSuccessful()) {
				String tag = response.body().string().trim();
				if (tag == "1") {
					System.out.println("成功！\t" + response.body().string().trim());
					isSuccess = true;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isSuccess;
	}
}
