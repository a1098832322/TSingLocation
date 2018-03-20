package com.dsw.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dsw.global.Constant;
import com.dsw.properties.tool.IO;

/**
 * 配置文件类
 * 
 * @author 郑龙
 *
 */
public class Config {
	private String dbip;
	private String dbname;
	private String db_user;
	private String db_passwd;
	private String position_host;
	private String warn_org;
	private String warn_level;
	private String alarm_url;
	private static Config mConfig = null;
	private String real_alarm_ip;
	private String real_alarm_port;
	private String real_alarm_index;

	public String getReal_alarm_ip() {
		return real_alarm_ip;
	}

	public void setReal_alarm_ip(String real_alarm_ip) {
		this.real_alarm_ip = real_alarm_ip;
	}

	public String getReal_alarm_port() {
		return real_alarm_port;
	}

	public void setReal_alarm_port(String real_alarm_port) {
		this.real_alarm_port = real_alarm_port;
	}

	public String getReal_alarm_index() {
		return real_alarm_index;
	}

	public void setReal_alarm_index(String real_alarm_index) {
		this.real_alarm_index = real_alarm_index;
	}

	/**
	 * 单例返回
	 * 
	 * @return Config
	 */

	public static Config getInstance() {
		if (mConfig == null) {
			mConfig = new Config();
		}
		return mConfig;
	}

	/**
	 * 默认无参构造方法
	 */
	private Config() {

	}

	/**
	 * 可能要用到的构造方法
	 * 
	 * @param dbip
	 * @param dbname
	 * @param db_user
	 * @param db_passwd
	 * @param position_host
	 * @param warn_org
	 * @param warn_level
	 * @param alarm_url
	 * @param real_alarm_ip
	 * @param real_alarm_port
	 * @param real_alarm_index
	 */
	public Config(String dbip, String dbname, String db_user, String db_passwd, String position_host, String warn_org,
			String warn_level, String alarm_url, String real_alarm_ip, String real_alarm_port,
			String real_alarm_index) {
		super();
		this.dbip = dbip;
		this.dbname = dbname;
		this.db_user = db_user;
		this.db_passwd = db_passwd;
		this.position_host = position_host;
		this.warn_org = warn_org;
		this.warn_level = warn_level;
		this.alarm_url = alarm_url;
		this.real_alarm_ip = real_alarm_ip;
		this.real_alarm_port = real_alarm_port;
		this.real_alarm_index = real_alarm_index;
	}

	public String getAlarm_url() {
		return alarm_url;
	}

	public void setAlarm_url(String alarm_url) {
		this.alarm_url = alarm_url;
	}

	public String getWarn_org() {
		return warn_org;
	}

	public void setWarn_org(String warn_org) {
		this.warn_org = warn_org;
	}

	public String getWarn_level() {
		return warn_level;
	}

	public void setWarn_level(String warn_level) {
		this.warn_level = warn_level;
	}

	public static void setNewConfig(Config config) {
		mConfig = config;
	}

	public String getDbip() {
		return dbip;
	}

	public void setDbip(String dbip) {
		this.dbip = dbip;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getDb_user() {
		return db_user;
	}

	public void setDb_user(String db_user) {
		this.db_user = db_user;
	}

	public String getDb_passwd() {
		return db_passwd;
	}

	public void setDb_passwd(String db_passwd) {
		this.db_passwd = db_passwd;
	}

	public String getPosition_host() {
		return position_host;
	}

	public void setPosition_host(String position_host) {
		this.position_host = position_host;
	}

	@Override
	public String toString() {
		return "\n\n\n\nConfig [dbip=" + dbip + ", dbname=" + dbname + ", db_user=" + db_user + ", db_passwd=" + db_passwd
				+ ", position_host=" + position_host + ", warn_org=" + warn_org + ", warn_level=" + warn_level
				+ ", alarm_url=" + alarm_url + ", real_alarm_ip=" + real_alarm_ip + ", real_alarm_port="
				+ real_alarm_port + ", real_alarm_index=" + real_alarm_index + "]\n\n\n\n";
	}
	
	

}
