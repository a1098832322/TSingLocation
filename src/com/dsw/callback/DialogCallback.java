package com.dsw.callback;

import com.dsw.bean.Room;

/**
 * Dialog的一些回调方法
 * 
 * @author 郑龙
 *
 */
public interface DialogCallback {
	/**
	 * 设置房间配置信息
	 * 
	 * @param room
	 */
	void setRoomInfo(Room room);

	/**
	 * 修改房间配置信息
	 * 
	 * @param room
	 */
	void changeRoomInfo(Room room);
}
