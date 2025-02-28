package com.gdupt.server;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

import com.gdupt.util.*;

public class ServerReceiveThread extends Thread {
	private DataInputStream dis;
	private Socket socket;
	List<Socket> list;
	JFrame frame;
	boolean send = true;
	String user;
	private String msg[];

	public ServerReceiveThread(Socket _socket, List<Socket> _list,
			JFrame _frame) {
		this.socket = _socket;
		this.list = _list;
		this.frame = _frame;
		
		try {
			dis = new DataInputStream(this.socket.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 为强制下线按钮添加监听
		Server.getjButtonForceDown().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				user = Server.getUser();
				
				if (NameBuffer.getKeyByValue(user) == socket) {
					new ServerSendThead(Checkout.DOWN, null, socket).start();
				}
			}
		});
		
		// 为屏蔽用户按钮添加监听
		Server.getjButtonShield().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				user = Server.getUser();

				if (user != null) {
					if (NameBuffer.getKeyByValue(user) == socket) {
						send = !send;
						if (Server.getjButtonShield().getText() == "屏蔽用户") {
							Server.getjButtonShield().setText("取消屏蔽");
							Server.getMap().put(user, "取消屏蔽");
						} else if (Server.getjButtonShield().getText() == "取消屏蔽") {
							Server.getjButtonShield().setText("屏蔽用户");
							Server.getMap().put(user, "屏蔽用户");
						}
						
						if (!send) {
							new ServerSendThead(Checkout.SHIELD, null, socket).start();
						} else {
							new ServerSendThead(Checkout.SHIELDREREASE, null, socket).start();
						}
					}
				}
			}
		});
	}


	@Override
	public void run() {
		String message = "";
		String username;
		String nameList;

		try {
			while ((message = dis.readUTF().trim()) != null) {

				if (message.endsWith(Checkout.NORMAL_MESSAGE)) { // 正常信息
					if(send) new ServerSendThead(message, list, null).start();
				} else if (message.endsWith(Checkout.REMOVE)) { // 用户请求关闭信息
					list.remove(socket);

					username = NameBuffer.getMap().get(socket);
					// 删除用户名
					NameBuffer.remove(socket);
					Server.getMap().remove(username);

					// 更新在线用户列表
					Server.getListModel().removeElement(username);

					// 给所有在线的用户发送要删除的用户
					new ServerSendThead(username + Checkout.REMOVE_USER, list,
							null).start();
					return;
				} else if (message.endsWith(Checkout.ADD)) { // 请求登陆信息
					username = message.substring(0,
							message.indexOf(Checkout.ADD));	// 得到用户名

					if (NameBuffer.getMap().containsValue(username)) { // 用户名存在的情况，只向请求登陆的用户发送请求失败的信息
						new Thread(new ServerSendThead(Checkout.ADD_ERROR,
								null, socket)).start();
						list.remove(socket);
						send = false;
						return;
					} else { // 用户名不存在的情况
						NameBuffer.addName(socket, username);
						Server.getListModel().addElement(username);
						Server.getMap().put(username, "屏蔽用户");

						// 标记信息为更新用户信息类型
						message = username + Checkout.ADD_USER;

						// 获得现在的在线用户列表
						nameList = NameBuffer.getNameList()
								+ Checkout.ADD_SUCCESS;

						// 把整个在线用户列表单独发送给请求登陆的用户
						new ServerSendThead(nameList, null, socket).start();

						// 发送更新用户列表信息给其余的用户
						new Thread(new ServerSendThead(message, list, socket))
								.start();
					}
				} else if (message.endsWith(Checkout.SINGLE_CHATING)) { // 请求单聊信息
					// message username user
					msg = Checkout.analyzingMessage(message, Checkout.SINGLE_CHATING);
					
					Map<Socket, String> map = NameBuffer.getMap();

					Set<Map.Entry<Socket, String>> set = map.entrySet();
					for (Iterator<Map.Entry<Socket, String>> it = set
							.iterator(); it.hasNext();) {

						Map.Entry<Socket, String> entry = (Map.Entry<Socket, String>) it
								.next();
						if (entry.getValue().equals(msg[2])) {
							Socket sock = entry.getKey();
							new ServerSendThead(message, socket, sock, list)
									.start();
						}
					}

				} else if (message.endsWith(Checkout.SEND_FILE)) {
					// message username user
					msg = Checkout.analyzingMessage(message, Checkout.SEND_FILE);
					
					Map<Socket, String> map = NameBuffer.getMap();

					Set<Map.Entry<Socket, String>> set = map.entrySet();
					for (Iterator<Map.Entry<Socket, String>> it = set
							.iterator(); it.hasNext();) {

						Map.Entry<Socket, String> entry = (Map.Entry<Socket, String>) it
								.next();
						if (entry.getValue().equals(msg[1])) {
							Socket sock = entry.getKey();
							new ServerSendThead(message, socket, sock, list)
									.start();
						}
					}
				} else if (message.endsWith(Checkout.SINGLE_CHAT_REQUEST)) {
					// message username user
					msg = Checkout.analyzingMessage(message, Checkout.SINGLE_CHAT_REQUEST);

					Map<Socket, String> map = NameBuffer.getMap();

					Set<Map.Entry<Socket, String>> set = map.entrySet();
					for (Iterator<Map.Entry<Socket, String>> it = set
							.iterator(); it.hasNext();) {

						Map.Entry<Socket, String> entry = (Map.Entry<Socket, String>) it
								.next();
						if (entry.getValue().equals(msg[1])) {
							Socket sock = entry.getKey();
							new ServerSendThead(message, socket, sock, list)
									.start();
						}
					}
				}

			}
		} catch (SocketException e) { // 此错误用来处理客户端在没有通知服务器就异常紧急关闭的情况
			list.remove(socket);

			username = NameBuffer.getMap().get(socket);
			// 删除用户名
			NameBuffer.remove(socket);
			Server.getMap().remove(username);

			Server.getListModel().removeElement(username);

			// 获取到新的用户列表
			message = username + Checkout.REMOVE_USER;
			new Thread(new ServerSendThead(message, list, socket)).start();
			return;

		} catch (EOFException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

	}
}
