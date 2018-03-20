package com.dsw.adapters;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dsw.bean.Room;
import com.dsw.global.Constant;
import com.dsw.othertools.ParseTool;
import com.dsw.properties.tool.IO;

/**
 * 这是一个自定义的JTable的Adapter
 * 
 * @author 郑龙
 *
 */
public class TableAdapter extends JTable {
	private DefaultTableModel model = new DefaultTableModel(); // 新建一个默认数据模型

	/**
	 * 添加一个Room数据到列表中
	 * 
	 * @param room
	 */
	public void addRoomtoTable(Room room) {
		Object[] row = { room.getRoomName(), room.isYj_flag() ? "是" : "否", room.getLeft_bottom().toString(),
				room.getLeft_top().toString(), room.getRight_bottom().toString(), room.getRight_top().toString() };
		model.addRow(row);

	}

	/**
	 * 添加一群Room数据到列表中
	 * 
	 * @param roomList
	 */
	public void addRoomstoTable(List<Room> roomList) {
		for (Room room : roomList) {
			Object[] row = { room.getRoomName(), room.isYj_flag() ? "是" : "否", room.getLeft_bottom().toString(),
					room.getLeft_top().toString(), room.getRight_bottom().toString(), room.getRight_top().toString() };
			model.addRow(row);
		}

	}

	/**
	 * 将Table的行对象转换为Room对象
	 * 
	 * @param index
	 * @return Room
	 */
	public Room getRoomInfoBySelectRow(int index) {
		Vector<List<Object>> vector = model.getDataVector();
		List<Object> row = vector.get(index);
		return ParseTool.rowToRoom(row);
	}

	/**
	 * 奥义·删除之术
	 * 
	 * @param index
	 */
	public void deleteSelectRow(int index) {
		model.removeRow(index);
	}

	/**
	 * 构造函数
	 * 
	 * @param data
	 * @param titles
	 */
	public TableAdapter(Object[][] data, Object[] titles) {
		this.model.setDataVector(data, titles);
		this.setModel(model);
		
	}

	/**
	 * 将Table中的所有数据以json格式保存到文本中
	 */
	public void save() {
		Vector<List<Object>> vector = model.getDataVector();

		List<Room> roomList = new ArrayList<>();

		for (int i = 0; i < vector.size(); i++) {
			roomList.add(ParseTool.rowToRoom(vector.get(i)));
		}
		String jsonStr = JSONArray.parseArray(JSON.toJSONString(roomList)).toString();
		System.out.println("保存的JSON：" + jsonStr);// debug
		if (IO.writeToTxt(Constant.propertiesFileURL, jsonStr)) {
			JOptionPane.showMessageDialog(null, "恭喜！(#^.^#) 没想到居然保存成功了！");
		} else {
			JOptionPane.showMessageDialog(null, "可恶啊！ ヽ(`Д´)ﾉ \n 想不到保存失败了！", "消息", JOptionPane.ERROR_MESSAGE);
		}
	}

}
