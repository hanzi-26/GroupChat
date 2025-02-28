package com.gdupt.client;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

import com.gdupt.single.*;
import com.gdupt.util.*;

public class ClientReceiveThread extends Thread {
	private Socket socket;
	private DataInputStream dis;
	private static Client client;
	private String message;
	private JFrame frame;
	private ClientLogin login;
	private static String username;
	private static String nameList;
	private ClientSingle single;
	private static List<String> list = new ArrayList<String>();		// 作为单聊用户列表
	private String user;
	private String beUser;
	private String msg[];

	public static void setNameList(String nameList) {
		ClientReceiveThread.nameList = nameList;
	}

	public static String getNameList() {
		return nameList;
	}

	public ClientReceiveThread(JFrame _frame, ClientLogin _login) {
		this.socket = ClientLogin.getSocket();
		dis = new DataInputStream(ClientLogin.getIn());
		username = ClientLogin.getUsername();
		this.frame = _frame;
		this.login = _login;
	}

	public ClientReceiveThread(String user_, ClientSingle single_,
			JFrame frame_, Client client_) {
		username = ClientLogin.getUsername();
		this.user = user_;
		this.single = single_;
		this.dis = new DataInputStream(ClientLogin.getIn());
		this.frame = frame_;
		client = client_;
	}

	@Override
	public void run() {
		try {

			while ((message = dis.readUTF()) != null) {
				if (message.endsWith(Checkout.NORMAL_MESSAGE)) { // 正常通信
					if (client != null) {
						message = message.substring(0,
								message.indexOf(Checkout.NORMAL_MESSAGE));
						Client.getjTextAreaMain().append(message + '\n');
						Client.getjTextAreaMain().setCaretPosition(
								Client.getjTextAreaMain().getText().length());
					}
				} else if (message.endsWith(Checkout.SINGLE_CHATING)) { // 单聊信息

					msg = Checkout.analyzingMessage(message, Checkout.SINGLE_CHATING);

					if (username.equals(msg[2]) && user.equals(msg[1])) {
						
						// 判断窗口是否隐藏了
						if (!single.isShowing())  single.setVisible(true);
						
						single.getjTextAreaMain().append(
								msg[1] + ": " + msg[0] + "\n");
					}
				} else if (message.endsWith(Checkout.SEND_FILE)) {
					// username user fileServer po
					
					msg = Checkout.analyzingMessage(message, Checkout.SEND_FILE);
					
					try {
						
						if (username.equals(msg[1]) && user.equals(msg[0])) {
							new ClientTest(single, msg[2], Integer.parseInt(msg[3])).start();
						}
					} catch (Exception e) {
						e.printStackTrace();
						return;
					}

				} else if (message.endsWith(Checkout.ADD_ERROR)) { // 用户请求登陆验证失败
					dis.close();
					socket.close();

					JOptionPane.showMessageDialog(frame, "该用户已经登录，请换一个用户名",
							"错误", JOptionPane.ERROR_MESSAGE);
					
					login.getjTextFieldname().setText("");
					login.getjTextFieldname().requestFocus();

					return;
				} else if (message.endsWith(Checkout.ADD_SUCCESS)) {	// 用户请求登陆成功
					login.setVisible(false);
					msg = Checkout.analyzingMessage(message, Checkout.ADD_SUCCESS);

					if (client == null) { // 第一次接收到添加成功的时候为自己的验证成功信息，新建client窗口
											// 并更新用户列表
						client = new Client(socket, username, login, msg);
					} else { // 后来的都为他人的验证成功信息，只用更新用户列表即可

						System.out.println("未发现情况111");
					}
				} else if (message.endsWith(Checkout.ADD_USER)) { // 添加新用户
					// 获取需要添加的用户的用户名
					nameList = message.substring(0,
							message.indexOf(Checkout.ADD_USER));
					if (client != null) {
						Client.getListModel().addElement(nameList);
					} else {
						System.out.println("未处理情况2222");
					}

				} else if (message.endsWith(Checkout.REMOVE_USER)) {
					// 获取需要删除的用户的用户名
					nameList = message.substring(0,
							message.indexOf(Checkout.REMOVE_USER));
					if (client != null) {
						Client.getListModel().removeElement(nameList);
					} else {
						System.out.println("未处理情况33333");
					}

					// 如果单聊的对方下线了，单聊将关闭
					if (single != null && nameList.equals(user)) {

						if (single.isShowing()) {
							JOptionPane.showMessageDialog(frame,
									"对方已下线，窗口即将关闭", "错误",
									JOptionPane.ERROR_MESSAGE);
						}

						single.dispose();
						list.remove(user);
					}

				} else if (message.endsWith(Checkout.REMOVE)) { // 服务器关闭前发来的信息，属于正常关闭
					if (client != null) {
						JOptionPane.showMessageDialog(frame, "服务器停机维护中...，程序即将关闭",
								"警告", JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					}
					return;
				} else if (message.endsWith(Checkout.DOWN)) { 	// 被管理员T下线
					JOptionPane.showMessageDialog(frame, "你因在聊天室的不正当操作，被管理员强制踢下线了",
							"警告", JOptionPane.ERROR_MESSAGE);
					Client.closing();
					return;
				} else if (message.endsWith(Checkout.SHIELD)) {
					JOptionPane.showMessageDialog(frame, "你被管理员屏蔽聊天，请注意用语文明！",
							"警告", JOptionPane.ERROR_MESSAGE);
				} else if (message.endsWith(Checkout.SHIELDREREASE)) {
					JOptionPane.showMessageDialog(frame, "管理员解除了你的聊天屏蔽！",
							"", JOptionPane.WARNING_MESSAGE);
				} else if (message.endsWith(Checkout.SINGLE_CHAT_REQUEST)) {

					msg = Checkout.analyzingMessage(message, Checkout.SINGLE_CHAT_REQUEST);

					if (msg[0].equals(username)) {
						beUser = msg[1];

						if (!list.contains(beUser)) {	// 列表不存在该用户，开启新线程并停止循环，交给新线程管理
							list.add(beUser);
							new Thread(new ClientSingle(beUser, client)).start();
							break;
						} else if (list.contains(beUser)) {
							if (single != null && !single.isShowing()) {
								single.setVisible(true);
							}
						}
					} else if (msg[1].equals(username)) {
						beUser = msg[0];

						if (!list.contains(beUser)) {
							list.add(beUser);
							new Thread(new ClientSingle(beUser, client)).start();
							break;
						} else if (list.contains(beUser)) {
							if (single != null && !single.isShowing()) {
								single.setVisible(true);
							}
						}
					}
				}
			}
		} catch (SocketException e) { // 此异常用来处理服务器在未通知客户端的情况下紧急关闭的情况
			if (client != null) {
				JOptionPane.showMessageDialog(frame, "服务器连接中断，程序将要关闭", "错误",
						JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

	}

	public static String getUsername() {
		return username;
	}

	public static List<String> getList() {
		return list;
	}
}
