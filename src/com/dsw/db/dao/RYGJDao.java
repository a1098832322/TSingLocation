package com.dsw.db.dao;

import java.sql.SQLException;

import com.dsw.bean.Point;
/**
 * dao类接口
 * @author 郑龙
 *
 */
public interface RYGJDao {
	/**
	 * 向轨迹表中插入信息
	 * @param SBID
	 * @param roomName
	 * @return 结果
	 */
	public abstract int insertGJ(String SBID, String roomName); // 向数据库中插入人员轨迹信息

	/**
	 * 向预警表中插入信息
	 * 
	 * @param org
	 * @param grade
	 * @param reason
	 * @param status
	 * @param msg
	 * @return 结果
	 * @throws SQLException 
	 */
	public abstract int insertYJ(String tagID, String org, String grade, String reason, String status, String msg) throws SQLException;

}
