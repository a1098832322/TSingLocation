package com.dsw.othertools;

import java.util.List;

import com.dsw.bean.Point;
import com.dsw.bean.Room;

/**
 * 格式化工具类
 * 
 * @author 郑龙
 *
 */
public class ParseTool {
	/**
	 * 将输入的字符串转换成Point
	 * 
	 * @param str
	 * @return Point
	 * @see Point
	 */
	public static Point formateString(String str) {
		// 将所有中文括号，逗号全部转为符合程序要求的英文括号和逗号
		str = str.replaceAll("（", "(");
		str = str.replaceAll("）", ")");
		str = str.replaceAll("，", ",");

		// 将字符串截取，只获取所需要的部分
		String xStr = str.substring(1, str.indexOf(','));
		String yStr = str.substring(str.indexOf(',') + 1, str.indexOf(')'));

		return new Point(Float.parseFloat(xStr.trim()), Float.parseFloat(yStr.trim()));
	}

	/**
	 * 将JTable的Row对象转换成Room对象
	 * 
	 * @param row
	 * @return Room
	 * @see Room
	 */
	public static Room rowToRoom(List<Object> row) {

		Room r = new Room();
		// 填充数据
		r.setRoomName(row.get(0).toString());
		r.setYj_flag(row.get(1).equals("是") ? true : false);
		r.setLeft_bottom(ParseTool.formateString(row.get(2).toString()));
		r.setLeft_top(ParseTool.formateString(row.get(3).toString()));
		r.setRight_bottom(ParseTool.formateString(row.get(4).toString()));
		r.setRight_top(ParseTool.formateString(row.get(5).toString()));

		return r;
	}

	/**
	 * 将Room对象转换成一个JTable的Row对象
	 * 
	 * @param room
	 * @return Object[]
	 */
	public static Object[] roomToRow(Room room) {
		Object[] row = { room.getRoomName(), room.isYj_flag() ? "是" : "否", room.getLeft_bottom().toString(),
				room.getLeft_top().toString(), room.getRight_bottom().toString(), room.getRight_top().toString() };
		return row;
	}
}
