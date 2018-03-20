package com.dsw.properties.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSON;
import com.dsw.config.Config;
import com.dsw.global.Constant;

/**
 * 用于取读配置文件的IO类
 * 
 * @author 郑龙
 *
 */
public class IO {
	/**
	 * 一个用于判断文件类型的枚举
	 * 
	 * @author 郑龙
	 *
	 */
	public static enum FileType {
		ROOM, CONFIG
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param file
	 * @return
	 */
	public static boolean judeFileExists(File file) {
		if (!file.getParentFile().exists()) {// 判断父目录路径是否存在，即test.txt前的I:\a\b\
			try {
				file.getParentFile().mkdirs();// 不存在则创建父目录
				file.createNewFile();

			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			if (!file.exists()) {
				try {
					file.createNewFile();// 创建新文件
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 重载方法,用于默认写入配置信息格式模板
	 * 
	 * @param file
	 * @param type
	 * @return
	 */

	public static boolean judeFileExists(File file, FileType type) {
		if (type == FileType.ROOM) {
			return judeFileExists(file);
		} else {
			if (!file.getParentFile().exists()) {// 判断父目录路径是否存在，即test.txt前的I:\a\b\
				try {
					file.getParentFile().mkdirs();// 不存在则创建父目录
					file.createNewFile();
					// 将默认信息写入文件
					writeToTxt(Constant.OtherSetingURL,
							JSON.toJSONString(new Config("ip地址（真实使用时请务必修改此配置文件！）", "数据库名（真实使用时请务必修改此配置文件！）",
									"数据库登录名（真实使用时请务必修改此配置文件！）", "数据库登录密码（真实使用时请务必修改此配置文件！）",
									"定位服务器ip地址（真实使用时请务必修改此配置文件！）", "机构名", "警报等级", "网络调用触发警报器的接口（真实使用时请务必修改此配置文件！）",
									"警报器所在继电器板ip（真实使用时请务必修改此配置文件！）", "警报器所在继电器板路数（真实使用时请务必修改此配置文件！）", "警报器所在继电器板通道号"))
									.toString());
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			} else {
				if (!file.exists()) {
					try {
						file.createNewFile();// 创建新文件
						// 将默认信息写入文件
						writeToTxt(Constant.OtherSetingURL,
								JSON.toJSONString(new Config("ip地址（真实使用时请务必修改此配置文件！）", "数据库名（真实使用时请务必修改此配置文件！）",
										"数据库登录名（真实使用时请务必修改此配置文件！）", "数据库登录密码（真实使用时请务必修改此配置文件！）",
										"定位服务器ip地址（真实使用时请务必修改此配置文件！）", "机构名", "警报等级", "网络调用触发警报器的接口（真实使用时请务必修改此配置文件！）",
										"警报器所在继电器板ip（真实使用时请务必修改此配置文件！）", "警报器所在继电器板路数（真实使用时请务必修改此配置文件！）",
										"警报器所在继电器板通道号")).toString());
					} catch (IOException e) {
						e.printStackTrace();
						return false;
					}
				}
			}

			return true;
		}
	}

	/**
	 * 从配置文件中读取房间的配置信息
	 * 
	 * @param path
	 * @return JSON
	 */
	public static String readtxt(String path) {
		String result = "";
		File file = new File(path);
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "gbk");
			BufferedReader br = new BufferedReader(reader);
			String s = null;
			while ((s = br.readLine()) != null) {
				result = result + s;
			}
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 将修改后的配置信息保存到配置文件中
	 * 
	 * @param path
	 * @param jsonStr
	 * @return
	 */
	public static boolean writeToTxt(String path, String jsonStr) {
		File file = new File(path);
		if (judeFileExists(file)) {
			try {
				OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "gbk");
				oStreamWriter.write(jsonStr);// 以覆盖方式写入
				// oStreamWriter.append(jsonStr);// 以添加方法写入
				oStreamWriter.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return false;
	}
}
