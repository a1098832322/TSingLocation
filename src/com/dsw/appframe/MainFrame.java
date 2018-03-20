package com.dsw.appframe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dsw.adapters.TableAdapter;
import com.dsw.adapters.DialogAdapter;
import com.dsw.adapters.WarnMessageConsoleAdapter;
import com.dsw.bean.Room;
import com.dsw.callback.DialogCallback;
import com.dsw.config.Config;
import com.dsw.global.Constant;
import com.dsw.properties.tool.IO;
import com.dsw.properties.tool.IO.FileType;
import com.tsing.location.WebSocketThread;
import com.zl.jydam.controller.AlarmListener;

import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLabel;

/**
 * 程序主界面UI
 * 
 * @author 郑龙
 *
 */
public class MainFrame {
	private JFrame frame;
	private TableAdapter table;
	private JScrollPane mainPane;
	private List<Room> roomList;
	private JLabel logo;
	private static int selectRow = -1;
	private static boolean isSelectedItem = false;// 是否选中表格内容の标记
	private static boolean isStartThread = false;// 是否开启定位县城の标记
	private static boolean isOneClick = true;// true：程序启动则自动开启定位线程；false：程序启动手动开启定位线程
	private static final WebSocketThread client = new WebSocketThread();// 定义一个定位的client
	private static JButton btnStart = new JButton("开始");

	/**
	 * 重写的回调事件
	 */
	private DialogCallback mCallback = new DialogCallback() {

		@Override
		public void setRoomInfo(Room room) {
			System.out.println(room);
			table.addRoomtoTable(room);
		}

		@Override
		public void changeRoomInfo(Room room) {
			// 返回Room对象给Table的行赋值
			table.setValueAt(room.getRoomName(), selectRow, 0);
			table.setValueAt(room.isYj_flag() ? "是" : "否", selectRow, 1);
			table.setValueAt(room.getLeft_bottom(), selectRow, 2);
			table.setValueAt(room.getLeft_top(), selectRow, 3);
			table.setValueAt(room.getRight_bottom(), selectRow, 4);
			table.setValueAt(room.getRight_top(), selectRow, 5);

		}
	};

	/**
	 * 列表表头
	 */
	private Object[] title = { "房间名", "是否是预警房间", "左下点坐标", "左上点坐标", "右下点坐标", "右上点坐标" };

	/**
	 * 单元格数据
	 */
	private Object[][] data = { { "", "", "", "", "", "" } };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/* 加载配置数据 */
		initSettingData();

		/* 开启警报器监听线程 */
		Thread alarmThread = new Thread(new AlarmListener());
		alarmThread.start();

		/* 显示主窗口 */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
						/* 设置皮肤属性 */
						BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
						UIManager.put("RootPane.setupButtonVisible", false);
						BeautyEyeLNFHelper.translucencyAtFrameInactive = false;// 是否在窗口失焦时变成半透明状态
						org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();// 加载皮肤
					} catch (Exception e) {
						// TODO exception
					}
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 783, 448);
		frame.setResizable(false);// 不允许改变大小
		frame.setTitle("迪赛威® 房间定位数据配置程序");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);// 使用绝对布局
		// 创建图片对象

		try {
			ImageIcon image = new ImageIcon(MainFrame.class.getResource("/imgs/logo_1.png"));//
			image.setImage(image.getImage().getScaledInstance(61, 54, Image.SCALE_DEFAULT));
			frame.setIconImage(image.getImage()); // 设置图标
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		logo = new JLabel();
		ImageIcon image = new ImageIcon(MainFrame.class.getResource("/imgs/logo.png"));
		image.setImage(image.getImage().getScaledInstance(133, 38, Image.SCALE_SMOOTH));
		logo.setIcon(image);
		logo.setBounds(10, 10, 133, 38);
		frame.getContentPane().add(logo);

		final JButton btnChange = new JButton("修改");
		btnChange.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		btnChange.setEnabled(isSelectedItem);
		btnChange.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectRow = table.getSelectedRow();
				Room room = table.getRoomInfoBySelectRow(selectRow);// 获得选择的行实例化而成的对象
				JDialog dialog = DialogAdapter.inputMessageDialog(DialogAdapter.CHANGE_DIALOG, frame, "提示",
						"请输入房间信息，坐标点请精确到小数点后两位", room.getRoomName(), room.isYj_flag(), room.getLeft_bottom(),
						room.getLeft_top(), room.getRight_bottom(), room.getRight_top(), mCallback);

			}
		});
		btnChange.setBounds(657, 113, 100, 40);
		frame.getContentPane().add(btnChange);

		final JButton btnDelete = new JButton("删除");
		btnDelete.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		btnDelete.setEnabled(isSelectedItem);
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectRow = table.getSelectedRow(); // 找到选中行们

				table.deleteSelectRow(selectRow);// 删除

				// 改变“修改”和“删除”按钮点击事件
				isSelectedItem = false;
				btnDelete.setEnabled(isSelectedItem);
				btnChange.setEnabled(isSelectedItem);
			}
		});
		btnDelete.setBounds(657, 177, 100, 40);
		frame.getContentPane().add(btnDelete);

		JButton btnAdd = new JButton("添加");
		btnAdd.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JDialog mDialog = DialogAdapter.inputMessageDialog(DialogAdapter.NEW_DIALOG, frame, "提示",
						"请输入房间信息，坐标点请精确到小数点后两位", mCallback);
				btnDelete.setEnabled(isSelectedItem);
				btnChange.setEnabled(isSelectedItem);
			}
		});
		btnAdd.setBounds(657, 51, 100, 40);
		frame.getContentPane().add(btnAdd);

		JButton btnOK = new JButton("保存");
		btnOK.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		btnOK.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				table.save();
			}
		});
		btnOK.setBounds(664, 348, 93, 23);
		frame.getContentPane().add(btnOK);

		JButton btnExit = new JButton("退出");
		btnExit.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		btnExit.setBounds(664, 381, 93, 23);
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		frame.getContentPane().add(btnExit);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 50, 632, 349);
		frame.getContentPane().add(scrollPane);

		DefaultTableModel model = new DefaultTableModel(data, title);

		table = new TableAdapter(new Object[][] {}, title);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				isSelectedItem = true;
				btnChange.setEnabled(isSelectedItem);
				btnDelete.setEnabled(isSelectedItem);
			}
		});

		/* 将JTable加入到ScrollPane中 */
		scrollPane.setViewportView(table);

		/* 初始化JTable数据 */
		initTableData(table);

		/* 开始按钮点击事件 */
		btnStart.setFont(new Font("微软雅黑", Font.PLAIN, 12));

		btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnStart == e.getSource()) {
					// 判断如果点击的是开始按钮
					isStartThread = !isStartThread;// 改变点击状态
					if (isOneClick) {
						client.start();
						isOneClick = false;
					}
					client.setListening(isStartThread);
					btnStart.setText(isStartThread ? "停止" : "开始");

					if (isStartThread) {
						// 实例化控制台窗口
						WarnMessageConsoleAdapter dialog = WarnMessageConsoleAdapter.getInstanceDialog(frame, "控制台",
								false);
						dialog.show();
					}
				}
			}
		});

		btnStart.setBounds(664, 265, 93, 23);
		frame.getContentPane().add(btnStart);

		JLabel lblNewLabel = new JLabel("人员定位线程");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel.setBounds(664, 239, 113, 15);
		frame.getContentPane().add(lblNewLabel);

		btnStart.doClick();// 点一下它试试
	}

	/**
	 * 使用配置文件初始化加载一些配置数据
	 */
	private static void initSettingData() {
		String json = null;
		if (IO.judeFileExists(new File(Constant.OtherSetingURL), FileType.CONFIG)) {
			json = IO.readtxt(Constant.OtherSetingURL);
		}

		Config.setNewConfig(JSON.parseObject(json, new TypeReference<Config>() {}));

		System.out.println(Config.getInstance());//log

	}

	/**
	 * 使用配置文件初始化显示JTable数据
	 * 
	 * @param table
	 */
	private void initTableData(TableAdapter table) {
		String json = null;
		if (IO.judeFileExists(new File(Constant.propertiesFileURL))) {
			json = IO.readtxt(Constant.propertiesFileURL);
		}
		try {
			// 当房间配置文件不为空时加载进JTable
			roomList = JSONArray.parseArray(json, Room.class);
			table.addRoomstoTable(roomList);
		} catch (Exception e) {
			// 否则不加载
		}

	}
}
