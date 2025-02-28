package com.gdupt.client;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

import com.gdupt.util.*;

public class ClientLogin extends JFrame {

	public ClientLogin() {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(ClientLogin.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(ClientLogin.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(ClientLogin.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(ClientLogin.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}

		initComponents();
	}

	public static InputStream getIn() {
		return in;
	}

	public void setIn(InputStream _in) {
		in = _in;
	}

	public static OutputStream getOut() {
		return out;
	}

	public void setOut(OutputStream _out) {
		out = _out;
	}
	
	
	private void initComponents() {
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		jPanel1 = new JPanel();
		jLabel1 = new JLabel();
		jTextFieldname = new JTextField();
		jLabel2 = new JLabel();
		jTextFieldserver = new JTextField();
		reset = new JButton();
		loging = new JButton();
		jLabel3 = new JLabel();
		jTextFieldport = new JTextField();
		jPanel1.setBorder(BorderFactory.createTitledBorder("�û���¼"));

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				closing();
			}
			
		});

		jLabel1.setFont(new java.awt.Font("����", 1, 14)); // NOI18N
		jLabel1.setForeground(Color.blue);
		jLabel1.setText("�û���");

		jTextFieldname.setFont(new java.awt.Font("����", 0, 14)); // NOI18N

		jLabel2.setFont(new java.awt.Font("����", 1, 14)); // NOI18N
		jLabel2.setForeground(Color.blue);
		jLabel2.setText("������");

		jTextFieldserver.setFont(new java.awt.Font("����", 0, 14)); // NOI18N
		
		jTextFieldname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				addKeyPressed(e);
			}
		});
		
		jTextFieldserver.setText("192.168.1.129");
		jTextFieldserver.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				addKeyPressed(e);
			}
		});
		
		jTextFieldport.setText("5555");
		jTextFieldport.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				addKeyPressed(e);
			}
		});

		
		reset.setFont(new java.awt.Font("����", 1, 14)); // NOI18N
		reset.setText("reset");
		reset.setForeground(Color.blue);
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetActionPerformed();
			}
		});

		
		loging.setFont(new java.awt.Font("����", 1, 14)); // NOI18N
		loging.setText("loging");
		loging.setForeground(Color.blue);
		loging.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				logingActionPerformed();
			}
		});

		jLabel3.setFont(new java.awt.Font("����", 1, 14)); // NOI18N
		jLabel3.setForeground(Color.blue);
		jLabel3.setText("�˿ں�");

		jTextFieldport.setFont(new java.awt.Font("����", 0, 14)); // NOI18N

		GroupLayout jPanel1Layout = new GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING,
																false)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				jLabel1)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				jTextFieldname,
																				GroupLayout.PREFERRED_SIZE,
																				197,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				jLabel2)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				jTextFieldserver))
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				jLabel3)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jTextFieldport)
																						.addGroup(
																								jPanel1Layout
																										.createSequentialGroup()
																										.addComponent(
																												reset)
																										.addGap(18,
																												18,
																												18)
																										.addComponent(
																												loging)
																										.addGap(0,
																												0,
																												Short.MAX_VALUE)))))
										.addContainerGap(67, Short.MAX_VALUE)));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addGap(45, 45, 45)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel1)
														.addComponent(
																jTextFieldname,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(28, 28, 28)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel2)
														.addComponent(
																jTextFieldserver,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(29, 29, 29)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																jTextFieldport,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel3))
										.addGap(18, 18, 18)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(reset)
														.addComponent(loging))
										.addContainerGap(22, Short.MAX_VALUE)));

		GroupLayout layout = new GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jPanel1,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jPanel1,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));

		pack();
	}

	private void logingActionPerformed() {
		username = jTextFieldname.getText();
		str_server = jTextFieldserver.getText();
		str_port = jTextFieldport.getText();
		
		// У���û���
		if(Checkout.isEmpty(username)) {
			JOptionPane.showMessageDialog(this, "�û�������Ϊ��", "����", JOptionPane.ERROR_MESSAGE);
			jTextFieldname.requestFocus();
			return;
		} else if(!Checkout.isCorrect(username)) {		//�Ƿ����������
			JOptionPane.showMessageDialog(this, "�û������ܰ���������", "����", JOptionPane.ERROR_MESSAGE);
			jTextFieldname.setText("");
			jTextFieldname.requestFocus();
			return;
		} else if(Checkout.isEmptyStart(username)) {		//��β�Ƿ�����ո�
			JOptionPane.showMessageDialog(this, "�û�����β���ܺ��пո�", "����", JOptionPane.ERROR_MESSAGE);
			jTextFieldname.setText("");
			jTextFieldname.requestFocus();
			return;
		} 
		
		// У�������
		if(Checkout.isEmpty(str_server)) {
			JOptionPane.showMessageDialog(this, "��������ַ����Ϊ��", "����", JOptionPane.ERROR_MESSAGE);
			jTextFieldserver.requestFocus();
			return;
		} else if(Checkout.isEmptyStart(str_server)) {		//��β�Ƿ�����ո�
			JOptionPane.showMessageDialog(this, "��������ַ��β���ܺ��пո�", "����", JOptionPane.ERROR_MESSAGE);
			jTextFieldserver.setText("");
			jTextFieldserver.requestFocus();
			return;
		}else if(!Checkout.isAddress(str_server)) {
			JOptionPane.showMessageDialog(this, "�����������ַ����ȷ������������", "����", JOptionPane.ERROR_MESSAGE);
			jTextFieldserver.setText("");
			jTextFieldserver.requestFocus();
			return;
		} else {
			try {
				address = InetAddress.getByName(str_server);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		
		// У��˿ں�
		if(Checkout.isEmpty(str_port)) {
			JOptionPane.showMessageDialog(this, "�˿ڲ���Ϊ��", "����", JOptionPane.ERROR_MESSAGE);
			jTextFieldport.requestFocus();
			return;
		} else if(!Checkout.isNumber(str_port)) {
			JOptionPane.showMessageDialog(this, "�˿ںű����������ε�����", "����", JOptionPane.ERROR_MESSAGE);
			jTextFieldport.setText("");
			jTextFieldport.requestFocus();
			return;
		} else if(!Checkout.isPortCorrect(str_port)) {
			JOptionPane.showMessageDialog(this, "�˿ںŷ�Χ������1024��65535֮��", "����", JOptionPane.ERROR_MESSAGE);
			jTextFieldport.setText("");
			jTextFieldport.requestFocus();
			return;
		} else port = Integer.parseInt(str_port);
		
		try {
			socket = new Socket(address, port);
			in=socket.getInputStream();
			out=socket.getOutputStream();
			// �����û��������½
			new ClientSendThread(Checkout.ADD).start();
			
			// �����̼߳�����������������Ӧ��
			new ClientReceiveThread(this, this).start();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "����ķ�������ַ��˿ں�δ�ҵ�", "����", JOptionPane.ERROR_MESSAGE);
			jTextFieldserver.setText("");
			jTextFieldserver.requestFocus();
			jTextFieldport.setText("");
			return;
		}
	}

	
	public static String getUsername() {
		return username;
	}

	public JTextField getjTextFieldname() {
		return jTextFieldname;
	}

	private void resetActionPerformed() {
		jTextFieldname.setText("");
		jTextFieldserver.setText("");
		jTextFieldport.setText("");
	}
	
	// �س�������
	private void addKeyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			logingActionPerformed();
	}

	// ���ڹر�
	public void closing() {
		System.exit(0);
	}
	
	public static void main(String args[]) {

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ClientLogin().setVisible(true);
			}
		});
	}

	private JButton reset;
	private JButton loging;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JPanel jPanel1;
	private JTextField jTextFieldname;
	private JTextField jTextFieldport;
	private JTextField jTextFieldserver;
	public static Socket getSocket() {
		return socket;
	}

	private static Socket socket;
    ///���
	private static InputStream in;
	private static OutputStream out;
	///end
	private static String username;
	private String str_server;
	private String str_port;
	private InetAddress address = null;
	private int port;
}
