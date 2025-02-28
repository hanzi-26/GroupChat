package com.gdupt.server;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

import javax.swing.*;
import javax.swing.event.*;

import com.gdupt.util.*;

public class Server extends JFrame {

	public Server() {
		initComponents();
		setResizable(false);
		jButtonReset.setEnabled(false);
		map = new HashMap<String, String>();
	}

	public static JButton getjButtonForceDown() {
		return jButtonForceDown;
	}

	public static JButton getjButtonShield() {
		return jButtonShield;
	}

	private void initComponents() {

		jPanel1 = new JPanel();
		jLabel4 = new JLabel();
		jLabelState = new JLabel();
		jLabel2 = new JLabel();
		jTextFieldServer = new JTextField();
		jLabel3 = new JLabel();
		jTextFieldPort = new JTextField();
		jButtonReset = new JButton();
		jButtonStart = new JButton();
		jPanel2 = new JPanel();
		jScrollPane2 = new JScrollPane();
		jButtonShield = new JButton();
		jButtonForceDown = new JButton();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				new ServerSendThead(Checkout.REMOVE, list, null).start();
				System.exit(0);
			}
		});

		jTextFieldServer.setText("192.168.1.129");
		jTextFieldPort.setText("5555");
		jPanel1.setName("服务器信息"); // NOI18N

		jLabel4.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
		jLabel4.setText("服务器状态");

		jLabelState.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
		jLabelState.setForeground(new java.awt.Color(243, 15, 72));
		jLabelState.setText("停止");

		jLabel2.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
		jLabel2.setText("服务器：");

		jLabel3.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
		jLabel3.setText("端口号：");

		jButtonReset.setText("重启服务器");
		/*jButtonReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ButtonReset();
			}
		});*/

		jButtonStart.setText("启动服务器");
		jButtonStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				buttonStart();
			}
		});

		jTextFieldServer.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				pressed(e);
			}
		});

		jTextFieldPort.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				pressed(e);
			}
		});

		GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				jLabel2)
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addGroup(
																								jPanel1Layout
																										.createSequentialGroup()
																										.addGap(18,
																												18,
																												18)
																										.addComponent(
																												jLabel4)
																										.addGap(18,
																												18,
																												18)
																										.addComponent(
																												jLabelState))
																						.addGroup(
																								jPanel1Layout
																										.createSequentialGroup()
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												jTextFieldServer,
																												GroupLayout.PREFERRED_SIZE,
																												160,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.UNRELATED)
																										.addComponent(
																												jButtonReset))))
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				jLabel3)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jTextFieldPort,
																				GroupLayout.PREFERRED_SIZE,
																				160,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				jButtonStart)))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				jPanel1Layout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								jPanel1Layout
										.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel4)
										.addComponent(jLabelState))
						.addGap(12, 12, 12)
						.addGroup(
								jPanel1Layout
										.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel2)
										.addComponent(jTextFieldServer,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(jButtonReset))
						.addPreferredGap(
								LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(
								jPanel1Layout
										.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel3)
										.addComponent(jTextFieldPort,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(jButtonStart))
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));

		// 设置用户列表
		listModel = new DefaultListModel<String>();
		jListNamelist = new JList<String>(listModel);

		jListNamelist.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				listAction(e);
			}
		});

		jScrollPane2.setViewportView(jListNamelist);

		jButtonShield.setText("屏蔽用户");

		jButtonForceDown.setText("强制下线");

		jPanel2.setBorder(BorderFactory.createTitledBorder("在线用户列表"));
		GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jScrollPane2)
				.addGroup(
						jPanel2Layout.createSequentialGroup()
								.addComponent(jButtonShield).addGap(18, 18, 18)
								.addComponent(jButtonForceDown)
								.addGap(0, 0, Short.MAX_VALUE)));
		jPanel2Layout
				.setVerticalGroup(jPanel2Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel2Layout
										.createSequentialGroup()
										.addComponent(jScrollPane2,
												GroupLayout.PREFERRED_SIZE,
												421, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																jButtonShield)
														.addComponent(
																jButtonForceDown))
										.addGap(0, 10, Short.MAX_VALUE)));

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(
										GroupLayout.Alignment.TRAILING, false)
										.addComponent(jPanel2,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(jPanel1,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jPanel1, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addPreferredGap(
								LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jPanel2, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addContainerGap()));

		pack();
	}

	// 启动服务器按钮
	public void buttonStart() {
		String str_server = jTextFieldServer.getText();
		String port_str = jTextFieldPort.getText();
		

		// 校验服务器
		if (!Checkout.isAddress(str_server)) {
			JOptionPane.showMessageDialog(this, "输入服务器地址不正确，请重新输入", "错误",
					JOptionPane.ERROR_MESSAGE);
			jTextFieldServer.setText("");
			jTextFieldServer.requestFocus();
			return;
		} else if (Checkout.isEmptyStart(str_server)) {
			JOptionPane.showMessageDialog(this, "服务器地址首尾不能包含空格", "错误",
					JOptionPane.ERROR_MESSAGE);
			jTextFieldServer.requestFocus();
			return;
		}

		
		
		// 校验端口号
		if (Checkout.isEmpty(port_str)) {
			JOptionPane.showMessageDialog(this, "端口不能为空", "警告",
					JOptionPane.ERROR_MESSAGE);
			jTextFieldPort.requestFocus();
			return;
		} else if (!Checkout.isNumber(port_str)) {
			JOptionPane.showMessageDialog(this, "端口号必须是整数形的数字", "警告",
					JOptionPane.ERROR_MESSAGE);
			jTextFieldPort.setText("");
			jTextFieldPort.requestFocus();
			return;
		} else if (!Checkout.isPortCorrect(port_str)) {
			JOptionPane.showMessageDialog(this, "端口号范围必须在1024到65535之间", "警告",
					JOptionPane.ERROR_MESSAGE);
			jTextFieldPort.setText("");
			jTextFieldPort.requestFocus();
			return;
		}

		

		

		//new ServerThread(this, address, port).start();
		try {
			port = Integer.parseInt(port_str);
			address = InetAddress.getByName(str_server);
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						serverSocket = new ServerSocket(port, 20, address);
						Server.getjLabelState().setText("运行中..");
						
						System.out.println(address);
						System.out.println(serverSocket.getLocalSocketAddress());
						System.out.println(serverSocket.getInetAddress());
						System.out.println(serverSocket.toString());
						
						Server.getjTextFieldServer().setEditable(false);
						Server.getjTextFieldPort().setEditable(false);
						Server.getjButtonStart().setEnabled(false);
						
						while(true) {
							socket = serverSocket.accept();
							list.add(socket);
							new ServerReceiveThread(socket, list, Server.this).start();
						}
					} catch (IOException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(Server.this, "端口被占用", "警告", JOptionPane.ERROR_MESSAGE);
						Server.getjTextFieldPort().setText("");
						Server.getjTextFieldPort().requestFocus();
						return;
					}
				}
			}).start();
			
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
	}

	/*// 重启服务器按钮
	public void ButtonReset() {
		jTextFieldServer.setEditable(true);
		jTextFieldPort.setEditable(true);
		jButtonStart.setEnabled(true);
		new ServerSendThead(Checkout.REMOVE, list, null).start();
	}
*/
	public static Map<String, String> getMap() {
		return map;
	}

	// 文本框的回车键响应
	private void pressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			buttonStart();
		}
	}

	// 列表事件监听
	private void listAction(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			user = jListNamelist.getSelectedValue().toString();
			jButtonShield.setText(map.get(user));
		}
	}

	public static void main(String args[]) {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Server.class.getName())
					.log(Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(Server.class.getName())
					.log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(Server.class.getName())
					.log(Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			Logger.getLogger(Server.class.getName())
					.log(Level.SEVERE, null, ex);
		}
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Server().setVisible(true);
			}
		});
	}

	private static JButton jButtonForceDown;
	private JButton jButtonReset;
	private static JButton jButtonShield;
	private static JButton jButtonStart;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private static JLabel jLabelState;
	private JList<String> jListNamelist;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JScrollPane jScrollPane2;
	private static JTextField jTextFieldPort;
	private static String user;

	public static String getUser() {
		return user;
	}

	public static JButton getjButtonStart() {
		return jButtonStart;
	}

	public static JLabel getjLabelState() {
		return jLabelState;
	}

	public static JTextField getjTextFieldPort() {
		return jTextFieldPort;
	}

	public static DefaultListModel<String> getListModel() {
		return listModel;
	}

	public static JTextField getjTextFieldServer() {
		return jTextFieldServer;
	}

	private static JTextField jTextFieldServer;
	private static DefaultListModel<String> listModel;
	private List<Socket> list = new ArrayList<Socket>();
	private ServerSocket serverSocket;
	private Socket socket;
	private static InetAddress address = null;
	private static int port;
	private static Map<String, String> map;
}