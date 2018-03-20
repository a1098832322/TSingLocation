package com.dsw.adapters;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.dsw.bean.Point;
import com.dsw.bean.Room;
import com.dsw.callback.DialogCallback;
import com.dsw.othertools.ParseTool;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.WindowListener;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JRadioButton;

/**
 * 这是一个自定义的模态对话框的Adapter，可以在里面编辑输入房间信息
 * 
 * @author 郑龙
 *
 */
public class DialogAdapter extends JDialog {
	/**
	 * 新增dialog
	 */
	public static final int NEW_DIALOG = 0;
	/**
	 * 修改dialog
	 */
	public static final int CHANGE_DIALOG = 1;

	/*初始化声明一些控件*/
	private final JPanel contentPanel = new JPanel();
	private static DialogAdapter dialog;
	private static JTextField roomName;
	private static JTextField left_bottom;
	private static JTextField left_top;
	private static JTextField right_bottom;
	private static JTextField right_top;
	private static JRadioButton rdbtnTrue;
	private static JRadioButton rdbtnFalse;
	private static JLabel mainWarn;
	private static DialogCallback mCallback;// 回调方法

	/**
	 * 返回新的编辑框
	 * 
	 * @param flag 对话框类型：0：新增；1：修改
	 * @param frame 生成模态对话框时需要引入的父窗口对象
	 * @param title
	 * @param warnMessage 提示语句
	 * @param callback 回调
	 * @return JDialog
	 */
	public static JDialog inputMessageDialog(int flag, JFrame frame, String title, String warnMessage,
			DialogCallback callback) {
		return inputMessageDialog(NEW_DIALOG, frame, title, warnMessage, "请输入房间名", false, new Point(0, 0),
				new Point(0, 0), new Point(0, 0), new Point(0, 0), callback);
	}

	/**
	 * 返回修改用的编辑框
	 * 
	 * @param flag 对话框类型：0：新增；1：修改
	 * @param frame 生成模态对话框时需要引入的父窗口对象
	 * @param title
	 * @param warnMessage 提示语句
	 * @param room
	 * @param isYjRoom
	 * @param left_bottom
	 * @param left_top
	 * @param right_bottom
	 * @param right_top
	 * @param callback
	 * @return JDialog
	 */
	public static JDialog inputMessageDialog(int flag, JFrame frame, String title, String warnMessage, String room,
			boolean isYjRoom, Point left_bottom, Point left_top, Point right_bottom, Point right_top,
			DialogCallback callback) {
		mCallback = callback;
		try {
			dialog = new DialogAdapter(flag, frame, title, true);
			dialog.setTitle(title);
			mainWarn.setText(warnMessage);

			// 自动填充标签
			roomName.setText(room);

			if (isYjRoom) {
				// 根据状态选择点击状态
				rdbtnTrue.setSelected(true);
			} else {
				rdbtnFalse.setSelected(true);
			}

			DialogAdapter.left_bottom.setText(left_bottom.toString());
			DialogAdapter.left_top.setText(left_top.toString());
			DialogAdapter.right_bottom.setText(right_bottom.toString());
			DialogAdapter.right_top.setText(right_top.toString());

			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);

			return dialog;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 构造方法
	 * 
	 * @param frame 生成模态对话框时需要引入的父窗口对象
	 * @param title 标题头
	 * @param isMod 是否生成模态对话框
	 */
	private DialogAdapter(final int tag, Frame frame, String title, boolean isMod) {
		super(frame, title, isMod);
		setBounds(100, 100, 431, 412);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			mainWarn = new JLabel("New label");
			mainWarn.setFont(new Font("微软雅黑", Font.BOLD, 16));
			mainWarn.setBounds(10, 10, 395, 28);
			contentPanel.add(mainWarn);
		}

		roomName = new JTextField();
		roomName.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		roomName.setText("房间名");
		roomName.setBounds(10, 77, 255, 21);
		contentPanel.add(roomName);
		roomName.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("请输入房间名称:");
		lblNewLabel_1.setFont(new Font("微软雅黑 Light", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(10, 57, 134, 15);
		contentPanel.add(lblNewLabel_1);

		JLabel lblxy = new JLabel("请输入左下点坐标（x,y）:");
		lblxy.setFont(new Font("微软雅黑 Light", Font.PLAIN, 12));
		lblxy.setBounds(10, 110, 227, 15);
		contentPanel.add(lblxy);

		left_bottom = new JTextField();
		left_bottom.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		left_bottom.setText("(0.00,0.00)");
		left_bottom.setColumns(10);
		left_bottom.setBounds(10, 130, 395, 21);
		contentPanel.add(left_bottom);

		JLabel lblxy_1 = new JLabel("请输入左上点坐标（x,y）:");
		lblxy_1.setFont(new Font("微软雅黑 Light", Font.PLAIN, 12));
		lblxy_1.setBounds(10, 161, 169, 15);
		contentPanel.add(lblxy_1);

		left_top = new JTextField();
		left_top.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		left_top.setText("(0.00,0.00)");
		left_top.setColumns(10);
		left_top.setBounds(10, 181, 395, 21);
		contentPanel.add(left_top);

		JLabel lblxy_2 = new JLabel("请输入右下点坐标（x,y）:");
		lblxy_2.setFont(new Font("微软雅黑 Light", Font.PLAIN, 12));
		lblxy_2.setBounds(10, 212, 169, 15);
		contentPanel.add(lblxy_2);

		right_bottom = new JTextField();
		right_bottom.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		right_bottom.setText("(0.00,0.00)");
		right_bottom.setColumns(10);
		right_bottom.setBounds(10, 232, 395, 21);
		contentPanel.add(right_bottom);

		JLabel lblxy_3 = new JLabel("请输入右上点坐标（x,y）:");
		lblxy_3.setFont(new Font("微软雅黑 Light", Font.PLAIN, 12));
		lblxy_3.setBounds(10, 270, 169, 15);
		contentPanel.add(lblxy_3);

		right_top = new JTextField();
		right_top.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		right_top.setText("(0.00,0.00)");
		right_top.setColumns(10);
		right_top.setBounds(10, 290, 395, 21);
		contentPanel.add(right_top);

		JLabel label = new JLabel("是否设置预警房间：");
		label.setFont(new Font("微软雅黑 Light", Font.PLAIN, 12));
		label.setBounds(271, 57, 134, 15);
		contentPanel.add(label);

		rdbtnTrue = new JRadioButton("是");
		rdbtnTrue.setBounds(271, 76, 59, 23);
		contentPanel.add(rdbtnTrue);

		rdbtnFalse = new JRadioButton("否");
		rdbtnFalse.setBounds(332, 76, 59, 23);

		/* 将单选按钮们加入Group */
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnTrue);
		group.add(rdbtnFalse);

		contentPanel.add(rdbtnFalse);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("保存");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						Room r = new Room();

						r.setRoomName(roomName.getText());
						r.setYj_flag(rdbtnTrue.isSelected() ? true : false);
						r.setLeft_bottom(ParseTool.formateString(left_bottom.getText().trim()));
						r.setLeft_top(ParseTool.formateString(left_top.getText().trim()));
						r.setRight_bottom(ParseTool.formateString(right_bottom.getText().trim()));
						r.setRight_top(ParseTool.formateString(right_top.getText().trim()));

						if (tag == NEW_DIALOG) {
							// 如果是添加窗口
							mCallback.setRoomInfo(r);
						} else {
							if (tag == CHANGE_DIALOG) {
								// 如果是修改窗口
								mCallback.changeRoomInfo(r);
							}
						}

						dialog.dispose();// 关闭对话框
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("取消");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						dialog.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
