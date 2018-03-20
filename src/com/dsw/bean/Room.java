package com.dsw.bean;

/**
 * 房间的javaBean
 * 
 * @author 郑龙
 *
 */
public class Room {
	private String roomName;// 房间名
	/* 房间的四个坐标点 */
	private Point left_bottom = new Point();
	private Point left_top = new Point();
	private Point right_bottom = new Point();
	private Point right_top = new Point();
	private boolean yj_flag;// 是否在房间内的标识

	public String isExist(Point point) {
		if ((left_bottom.getX() < point.getX()) && (right_bottom.getX() > point.getX())
				&& (left_bottom.getY() < point.getY()) && (left_top.getY() > point.getY())) {
			// 如果在房间内则返回房间名
			return this.roomName;
		}
		// 如果不在房间内则返回空
		return null;
	}

	/**
	 * 在构造方法中默认填充一些数据，防止程序抛异常
	 */
	public Room() {
		this.roomName = "初始化の测试房间";
		this.left_bottom.setPoint(0, 0);
		this.left_top.setPoint(0, 10);
		this.right_bottom.setPoint(30, 0);
		this.right_top.setPoint(30, 10);
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Point getLeft_bottom() {
		return left_bottom;
	}

	public void setLeft_bottom(Point left_bottom) {
		this.left_bottom = left_bottom;
	}

	public Point getLeft_top() {
		return left_top;
	}

	public void setLeft_top(Point left_top) {
		this.left_top = left_top;
	}

	public Point getRight_bottom() {
		return right_bottom;
	}

	public void setRight_bottom(Point right_bottom) {
		this.right_bottom = right_bottom;
	}

	public Point getRight_top() {
		return right_top;
	}

	public void setRight_top(Point right_top) {
		this.right_top = right_top;
	}

	public boolean isYj_flag() {
		return yj_flag;
	}

	public void setYj_flag(boolean yj_flag) {
		this.yj_flag = yj_flag;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */

	@Override
	public String toString() {
		return "Room [roomName=" + roomName + ", left_bottom=" + left_bottom + ", left_top=" + left_top
				+ ", right_bottom=" + right_bottom + ", right_top=" + right_top + ", yj_flag=" + yj_flag + "]";
	}

}
