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

		// Ϊǿ�����߰�ť��Ӽ���
		Server.getjButtonForceDown().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				user = Server.getUser();
				
				if (NameBuffer.getKeyByValue(user) == socket) {
					new ServerSendThead(Checkout.DOWN, null, socket).start();
				}
			}
		});
		
		// Ϊ�����û���ť��Ӽ���
		Server.getjButtonShield().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				user = Server.getUser();

				if (user != null) {
					if (NameBuffer.getKeyByValue(user) == socket) {
						send = !send;
						if (Server.getjButtonShield().getText() == "�����û�") {
							Server.getjButtonShield().setText("ȡ������");
							Server.getMap().put(user, "ȡ������");
						} else if (Server.getjButtonShield().getText() == "ȡ������") {
							Server.getjButtonShield().setText("�����û�");
							Server.getMap().put(user, "�����û�");
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

				if (message.endsWith(Checkout.NORMAL_MESSAGE)) { // ������Ϣ
					if(send) new ServerSendThead(message, list, null).start();
				} else if (message.endsWith(Checkout.REMOVE)) { // �û�����ر���Ϣ
					list.remove(socket);

					username = NameBuffer.getMap().get(socket);
					// ɾ���û���
					NameBuffer.remove(socket);
					Server.getMap().remove(username);

					// ���������û��б�
					Server.getListModel().removeElement(username);

					// ���������ߵ��û�����Ҫɾ�����û�
					new ServerSendThead(username + Checkout.REMOVE_USER, list,
							null).start();
					return;
				} else if (message.endsWith(Checkout.ADD)) { // �����½��Ϣ
					username = message.substring(0,
							message.indexOf(Checkout.ADD));	// �õ��û���

					if (NameBuffer.getMap().containsValue(username)) { // �û������ڵ������ֻ�������½���û���������ʧ�ܵ���Ϣ
						new Thread(new ServerSendThead(Checkout.ADD_ERROR,
								null, socket)).start();
						list.remove(socket);
						send = false;
						return;
					} else { // �û��������ڵ����
						NameBuffer.addName(socket, username);
						Server.getListModel().addElement(username);
						Server.getMap().put(username, "�����û�");

						// �����ϢΪ�����û���Ϣ����
						message = username + Checkout.ADD_USER;

						// ������ڵ������û��б�
						nameList = NameBuffer.getNameList()
								+ Checkout.ADD_SUCCESS;

						// �����������û��б������͸������½���û�
						new ServerSendThead(nameList, null, socket).start();

						// ���͸����û��б���Ϣ��������û�
						new Thread(new ServerSendThead(message, list, socket))
								.start();
					}
				} else if (message.endsWith(Checkout.SINGLE_CHATING)) { // ��������Ϣ
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
		} catch (SocketException e) { // �˴�����������ͻ�����û��֪ͨ���������쳣�����رյ����
			list.remove(socket);

			username = NameBuffer.getMap().get(socket);
			// ɾ���û���
			NameBuffer.remove(socket);
			Server.getMap().remove(username);

			Server.getListModel().removeElement(username);

			// ��ȡ���µ��û��б�
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
