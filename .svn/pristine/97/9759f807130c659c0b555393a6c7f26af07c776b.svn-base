package com.dsw.adapters;

import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListModel;

/**
 * 这是一个自定义的JList的Adapter
 * 
 * @author 郑龙
 *
 */
public class ListAdapter extends JList<Object> {
	private static DefaultListModel<Object> model = new DefaultListModel<>();
	private static ListAdapter mListAdapter = null;

	/**
	 * 单例返回自定义的JList
	 * 
	 * @return ListAdapter
	 */
	public static ListAdapter getInstanceListAdapter() {
		if (mListAdapter == null) {
			mListAdapter = new ListAdapter();
		}

		model.clear();
		model.addElement("正在监听人员定位预警信息......");

		return mListAdapter;
	}

	/**
	 * 向控制台中写入当前的预警消息
	 * 
	 * @param msg
	 */
	public static void writeMessageToConsole(Object msg) {
		model.addElement(msg);
	}

	/**
	 * 构造方法
	 */
	private ListAdapter() {
		super();
		model.addElement("正在监听人员定位预警信息......");
		this.setModel(model);
	}
}
