package com.gdupt.client;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

import javax.swing.*;

import com.gdupt.single.*;
import com.gdupt.util.*;

public class ClientSingle extends JFrame implements Runnable {
	private JButton jButtonSendFile;
	private JButton jButtonReset;
	private JButton jButtonSend;
	private JLabel jLabel1;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JTextArea jTextAreaInput;
	private JTextArea jTextAreaMain;
	private String username;
	private String user;
	private String message;
	private List<String> list;
	private Client client;
	private static ServerSocket serverSocket;
	

	@Override
	public void run() {
		new ClientReceiveThread(user, this, this, client).start();
	}
	
	
	public JTextArea getjTextAreaMain() {
		return jTextAreaMain;
	}


	public ClientSingle(String _user, Client client_) {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(ClientSingle.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(ClientSingle.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(ClientSingle.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			Logger.getLogger(ClientSingle.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		
		initComponents();
		setVisible(true);
		setResizable(false);
		
		this.username = ClientReceiveThread.getUsername();
		
		this.user = _user;
		this.client = client_;
		setTitle(username);
		jLabel1.setText("与 " + user + " 聊天中");
	}

	public List<String> getList() {
		return list;
	}


	private void initComponents() {

		jPanel1 = new JPanel();
		jScrollPane2 = new JScrollPane();
		jTextAreaMain = new JTextArea();
		jPanel2 = new JPanel();
		jScrollPane1 = new JScrollPane();
		jTextAreaInput = new JTextArea();
		jButtonSendFile = new JButton();
		jButtonSend = new JButton();
		jButtonReset = new JButton();
		jLabel1 = new JLabel();
		jTextAreaMain.setEditable(false);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closing();
			}
		});

		jTextAreaInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				pressed(e);
			}
		});

		jPanel1.setBorder(BorderFactory.createTitledBorder("聊天信息"));

		jTextAreaMain.setColumns(20);
		jTextAreaMain.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
		jTextAreaMain.setRows(5);
		jScrollPane2.setViewportView(jTextAreaMain);

		GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				jPanel1Layout.createSequentialGroup().addContainerGap()
						.addComponent(jScrollPane2).addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				GroupLayout.Alignment.TRAILING,
				jPanel1Layout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE,
								201, Short.MAX_VALUE)));

		jPanel2.setBorder(BorderFactory.createTitledBorder(""));

		jTextAreaInput.setColumns(20);
		jTextAreaInput.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
		jTextAreaInput.setRows(5);
		jScrollPane1.setViewportView(jTextAreaInput);

		jButtonSendFile.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
		jButtonSendFile.setText("发送文件");

		jButtonSendFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				buttonSendFile();
			}
		});

		jButtonSend.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
		jButtonSend.setText("发送");
		jButtonSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				buttonSend();
			}
		});

		jButtonReset.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
		jButtonReset.setText("清屏");
		jButtonReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				buttonReset();
			}
		});

		jLabel1.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
		jLabel1.setForeground(new java.awt.Color(0, 204, 204));
		jLabel1.setText("与****聊天中");

		GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout
				.setHorizontalGroup(jPanel2Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								GroupLayout.Alignment.TRAILING,
								jPanel2Layout
										.createSequentialGroup()
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																GroupLayout.Alignment.TRAILING)
														.addGroup(
																jPanel2Layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				jScrollPane1))
														.addGroup(
																jPanel2Layout
																		.createSequentialGroup()
																		.addGap(37,
																				37,
																				37)
																		.addComponent(
																				jLabel1)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED,
																				158,
																				Short.MAX_VALUE)
																		.addComponent(
																				jButtonReset)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jButtonSend)
																		.addGap(25,
																				25,
																				25)
																		.addComponent(
																				jButtonSendFile)))
										.addContainerGap()));
		jPanel2Layout
				.setVerticalGroup(jPanel2Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								GroupLayout.Alignment.TRAILING,
								jPanel2Layout
										.createSequentialGroup()
										.addComponent(jScrollPane1,
												GroupLayout.PREFERRED_SIZE, 88,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																jButtonSendFile)
														.addComponent(
																jButtonSend)
														.addComponent(
																jButtonReset)
														.addComponent(jLabel1))));

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jPanel1, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jPanel2, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(jPanel1,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jPanel2,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));

		pack();
	}// </editor-fold>

	public JLabel getjLabel1() {
		return jLabel1;
	}
	
	
	// 得到以太网连接的IP地址
	private InetAddress findInetAddress() {
		
		Enumeration<NetworkInterface> e;
		try {
			e = NetworkInterface.getNetworkInterfaces();
			while (e.hasMoreElements()) {

				NetworkInterface ni = e.nextElement();

				byte[] mac = ni.getHardwareAddress();
				if (mac == null || mac.length <= 0)
					continue;

				if (ni.getDisplayName().contains("VMware")) {
					continue;
				}
				if (!ni.isUp()) {
					continue;
				}

				Enumeration<InetAddress> addrArray = ni.getInetAddresses();
				
				return addrArray.nextElement();
			}
			
		} catch (SocketException e1) {
			e1.printStackTrace();
			
		}
		return null;
	}
	

	// 发送文件按钮监听
	private void buttonSendFile() {
		// 建立一个文件选择器
		JFileChooser fc = new JFileChooser();
		//fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showOpenDialog(this);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {	// 选择了文件
			File file = fc.getSelectedFile();
				
			int po = new Random().nextInt(55000) + 10000;
			InetAddress addr = findInetAddress();
			
			if(addr != null) {
				createServerSocket(po, addr);
			}
			
			// 定义向服务器发送申请连接的信息格式
			message = username + Checkout.SEPARATOR + 
					user + Checkout.SEPARATOR + addr.toString().substring(1)
					+ Checkout.SEPARATOR + po + Checkout.SEND_FILE;
			
			new ServerTest(serverSocket, file, this).start();
			new ClientSendThread(message).start();
			jTextAreaInput.setText("");
			jTextAreaInput.requestFocus();
		}
	}

	// 清空按钮监听
	private void buttonReset() {
		jTextAreaMain.setText("");
	}
	
	// 发送按钮监听
	private void buttonSend() {
		String str = jTextAreaInput.getText();
		
		if(Checkout.isAllBlankSpace(str)) {
			JOptionPane.showMessageDialog(this, "信息不能全为空格", "", JOptionPane.ERROR_MESSAGE);
			jTextAreaInput.setText("");
			jTextAreaInput.requestFocus();
			return;
		} else if(Checkout.isEmpty(str)) {
			JOptionPane.showMessageDialog(this, "信息不能为空，请重新输入", "", JOptionPane.ERROR_MESSAGE);
			jTextAreaInput.setText("");
			jTextAreaInput.requestFocus();
			return;
		}
		
		message = str.trim() + Checkout.SEPARATOR + username + 
				Checkout.SEPARATOR + user + Checkout.SINGLE_CHATING;
		
		new ClientSendThread(message).start();
		
		jTextAreaInput.setText("");
		jTextAreaInput.requestFocus();
		jTextAreaMain.append(username + ": " + str + '\n');
		jTextAreaMain.setCaretPosition(jTextAreaMain.getText().length());
	}
	
	
	private void createServerSocket(int port, InetAddress addr) {
		try {
			serverSocket = new ServerSocket(port, 1, addr);
			jTextAreaMain.append("服务器地址：" + addr.toString() + ", " + "端口号： " + port + "\n");
		} catch (IOException e1) {
			// 若出错，则再换一个随机的端口号建立socket服务器
			createServerSocket(new Random().nextInt(55000) + 10000, addr);
			e1.printStackTrace();
		}
	}

	
	// 窗口关闭处理
	private void closing() {
		//if(ClientReceiveThread.getList().contains(user))
		jTextAreaMain.setText("");
		this.dispose();
	}

	// 回车键响应
	private void pressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		}
	}
}
