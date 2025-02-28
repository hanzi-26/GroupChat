package com.gdupt.client;

import java.awt.event.*;
import java.net.*;

import javax.swing.*;

import com.gdupt.util.*;

public class Client extends JFrame {
	private JButton jButtonReset;
	private JButton jButtonSend;
	private JDialog jDialog1;
	private JList<String> jList1;
	private JPanel jPanel1;
	private JPanel jPanel4;
	private JPanel jPanel5;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JScrollPane jScrollPane3;
	private static JTextArea jTextAreaInput;
	private static JTextArea jTextAreaMain;
	private static DefaultListModel<String> listModel;
	private String username;
	private String beUser;
	private String message;
	
	public Client(Socket socket_, String _username, ClientLogin _login,
			String[] _nameList) {

		gui();
		initComponents();
		setVisible(true);
		setResizable(false);

		this.username = _username;
		setTitle(username);

		for (String str : _nameList) {
			listModel.addElement(str);
		}
	}
	
	private void gui() {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Client.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Client.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Client.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Client.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
	}
	

	private void initComponents() {

		jDialog1 = new JDialog();
		jPanel4 = new JPanel();
		jScrollPane3 = new JScrollPane();
		jPanel5 = new JPanel();
		jScrollPane1 = new JScrollPane();
		jTextAreaMain = new JTextArea();
		jPanel1 = new JPanel();
		jScrollPane2 = new JScrollPane();
		jTextAreaInput = new JTextArea();
		jButtonSend = new JButton();
		jButtonReset = new JButton();

		GroupLayout jDialog1Layout = new GroupLayout(jDialog1.getContentPane());
		jDialog1.getContentPane().setLayout(jDialog1Layout);
		jDialog1Layout.setHorizontalGroup(jDialog1Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));
		jDialog1Layout.setVerticalGroup(jDialog1Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 300, Short.MAX_VALUE));

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closing();
			}
		});

		jPanel4.setBorder(BorderFactory.createTitledBorder("在线用户列表"));

		// 设置用户列表
		listModel = new DefaultListModel<String>();
		/*
		 * listModel.addElement("Debbie Scott");
		 * listModel.addElement("Scott Hommel");
		 * listModel.addElement("Alan Sommerer");
		 */
		jList1 = new JList<String>(listModel);

		jList1.addMouseListener(new MouseAdapter() {
		     public void mouseClicked(MouseEvent e) {
		         if (e.getClickCount() == 2) {
		            // int index = jList1.locationToIndex(e.getPoint());
		            // System.out.println("Double clicked on Item " + index);
		        	 listAction();
		          }
		     }
		 });


		jButtonReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonactionPerformed(e);
			}
		});

		jButtonSend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				buttonactionPerformed(e);
			}
		});

		jTextAreaInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				pressed(e);
			}
		});

		jScrollPane3.setViewportView(jList1);

		GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				jPanel4Layout
						.createSequentialGroup()
						.addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE,
								151, Short.MAX_VALUE).addContainerGap()));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(jScrollPane3));

		jPanel5.setBorder(BorderFactory.createTitledBorder("聊天信息"));

		jTextAreaMain.setEditable(false);
		jTextAreaMain.setColumns(20);
		jTextAreaMain.setRows(5);
		jScrollPane1.setViewportView(jTextAreaMain);

		GroupLayout jPanel5Layout = new GroupLayout(jPanel5);
		jPanel5.setLayout(jPanel5Layout);
		jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(jScrollPane1,
				GroupLayout.PREFERRED_SIZE, 516, GroupLayout.PREFERRED_SIZE));
		jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(jScrollPane1,
				GroupLayout.PREFERRED_SIZE, 399, GroupLayout.PREFERRED_SIZE));

		jPanel1.setBorder(BorderFactory.createTitledBorder(""));

		jTextAreaInput.setColumns(20);
		jTextAreaInput.setRows(5);
		jScrollPane2.setViewportView(jTextAreaInput);

		jButtonSend.setText("发送");

		jButtonReset.setText("清屏");

		GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								GroupLayout.Alignment.TRAILING,
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																GroupLayout.Alignment.TRAILING)
														.addComponent(
																jScrollPane2)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addGap(0,
																				0,
																				Short.MAX_VALUE)
																		.addComponent(
																				jButtonReset)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				jButtonSend)))
										.addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				jPanel1Layout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(
								LayoutStyle.ComponentPlacement.RELATED,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(
								jPanel1Layout
										.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
										.addComponent(jButtonSend)
										.addComponent(jButtonReset))));

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup()
						.addGroup(
								layout.createParallelGroup(
										GroupLayout.Alignment.LEADING, false)
										.addComponent(jPanel5,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(jPanel1,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
						.addPreferredGap(
								LayoutStyle.ComponentPlacement.RELATED,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jPanel4, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.TRAILING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(0, 0, Short.MAX_VALUE)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.TRAILING,
												false)
												.addComponent(
														jPanel4,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jPanel5,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		jPanel1,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)))
								.addContainerGap()));

		pack();
	}// </editor-fold>

	public JList<String> getjList1() {
		return jList1;
	}

	public static JTextArea getjTextAreaInput() {
		return jTextAreaInput;
	}

	public static JTextArea getjTextAreaMain() {
		return jTextAreaMain;
	}

	public static DefaultListModel<String> getListModel() {
		return listModel;
	}

	private void pressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		}
	}

	// 按钮监听
	private void buttonactionPerformed(ActionEvent e) {

		if (e.getSource() == jButtonReset) {
			jTextAreaMain.setText("");
			jTextAreaInput.requestFocus();
		} else if (e.getSource() == jButtonSend) {
			if (Checkout.isEmpty(jTextAreaInput.getText())) {
				JOptionPane.showMessageDialog(this, "发送的消息不能为空", "错误", JOptionPane.ERROR_MESSAGE);
				jTextAreaInput.requestFocus();
				return;
			} else if (Checkout.isAllBlankSpace(jTextAreaInput.getText())) {
				JOptionPane.showMessageDialog(this, "发送的消息不能全为空格", "错误", JOptionPane.ERROR_MESSAGE);
				jTextAreaInput.requestFocus();
				return;
			}
			
			message = jTextAreaInput.getText().trim() + Checkout.NORMAL_MESSAGE;
			
			new ClientSendThread(message).start();
			jTextAreaInput.setText("");
			jTextAreaInput.requestFocus();
		}

	}

	// 用户列表选择监听
	private void listAction() {
		beUser = jList1.getSelectedValue().toString();
		
		if(!username.equals(beUser)) {
			String message =username + Checkout.SEPARATOR + beUser + Checkout.SINGLE_CHAT_REQUEST;
			new ClientSendThread(message).start();
		} else {
			JOptionPane.showMessageDialog(this, "不能与自己私聊！", "错误", JOptionPane.ERROR_MESSAGE);
		}
	}

	// 窗口关闭处理
	public static void closing() {
		new ClientSendThread(Checkout.REMOVE).start();
		System.exit(0);
	}
}