package com.dsw.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.spi.DirStateFactory.Result;

import com.dsw.config.Config;

/**
 * 数据库连接工具
 *
 * @author 胡凌锐
 */
public class DBConnection {

	private static String URL = "";
	private static Config mConfig = Config.getInstance();

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获得连接
	 * @return Connection
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://" + mConfig.getDbip() + "/" + mConfig.getDbname()
					+ "?characterEncoding=utf-8&useSSL=true", mConfig.getDb_user(), mConfig.getDb_passwd());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 释放连接
	 */
	public static void freeConnection(ResultSet rs, PreparedStatement ps, Connection conn) {
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 释放连接
	 */
	public static void freeConnection(PreparedStatement ps, Connection conn) {
		try {
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
