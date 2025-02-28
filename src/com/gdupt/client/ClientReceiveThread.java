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
	private static List<String> list = new ArrayList<String>();		// ��Ϊ�����û��б�
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
				if (message.endsWith(Checkout.NORMAL_MESSAGE)) { // ����ͨ��
					if (client != null) {
						message = message.substring(0,
								message.indexOf(Checkout.NORMAL_MESSAGE));
						Client.getjTextAreaMain().append(message + '\n');
						Client.getjTextAreaMain().setCaretPosition(
								Client.getjTextAreaMain().getText().length());
					}
				} else if (message.endsWith(Checkout.SINGLE_CHATING)) { // ������Ϣ

					msg = Checkout.analyzingMessage(message, Checkout.SINGLE_CHATING);

					if (username.equals(msg[2]) && user.equals(msg[1])) {
						
						// �жϴ����Ƿ�������
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

				} else if (message.endsWith(Checkout.ADD_ERROR)) { // �û������½��֤ʧ��
					dis.close();
					socket.close();

					JOptionPane.showMessageDialog(frame, "���û��Ѿ���¼���뻻һ���û���",
							"����", JOptionPane.ERROR_MESSAGE);
					
					login.getjTextFieldname().setText("");
					login.getjTextFieldname().requestFocus();

					return;
				} else if (message.endsWith(Checkout.ADD_SUCCESS)) {	// �û������½�ɹ�
					login.setVisible(false);
					msg = Checkout.analyzingMessage(message, Checkout.ADD_SUCCESS);

					if (client == null) { // ��һ�ν��յ���ӳɹ���ʱ��Ϊ�Լ�����֤�ɹ���Ϣ���½�client����
											// �������û��б�
						client = new Client(socket, username, login, msg);
					} else { // �����Ķ�Ϊ���˵���֤�ɹ���Ϣ��ֻ�ø����û��б���

						System.out.println("δ�������111");
					}
				} else if (message.endsWith(Checkout.ADD_USER)) { // ������û�
					// ��ȡ��Ҫ��ӵ��û����û���
					nameList = message.substring(0,
							message.indexOf(Checkout.ADD_USER));
					if (client != null) {
						Client.getListModel().addElement(nameList);
					} else {
						System.out.println("δ�������2222");
					}

				} else if (message.endsWith(Checkout.REMOVE_USER)) {
					// ��ȡ��Ҫɾ�����û����û���
					nameList = message.substring(0,
							message.indexOf(Checkout.REMOVE_USER));
					if (client != null) {
						Client.getListModel().removeElement(nameList);
					} else {
						System.out.println("δ�������33333");
					}

					// ������ĵĶԷ������ˣ����Ľ��ر�
					if (single != null && nameList.equals(user)) {

						if (single.isShowing()) {
							JOptionPane.showMessageDialog(frame,
									"�Է������ߣ����ڼ����ر�", "����",
									JOptionPane.ERROR_MESSAGE);
						}

						single.dispose();
						list.remove(user);
					}

				} else if (message.endsWith(Checkout.REMOVE)) { // �������ر�ǰ��������Ϣ�����������ر�
					if (client != null) {
						JOptionPane.showMessageDialog(frame, "������ͣ��ά����...�����򼴽��ر�",
								"����", JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					}
					return;
				} else if (message.endsWith(Checkout.DOWN)) { 	// ������ԱT����
					JOptionPane.showMessageDialog(frame, "�����������ҵĲ�����������������Աǿ����������",
							"����", JOptionPane.ERROR_MESSAGE);
					Client.closing();
					return;
				} else if (message.endsWith(Checkout.SHIELD)) {
					JOptionPane.showMessageDialog(frame, "�㱻����Ա�������죬��ע������������",
							"����", JOptionPane.ERROR_MESSAGE);
				} else if (message.endsWith(Checkout.SHIELDREREASE)) {
					JOptionPane.showMessageDialog(frame, "����Ա���������������Σ�",
							"", JOptionPane.WARNING_MESSAGE);
				} else if (message.endsWith(Checkout.SINGLE_CHAT_REQUEST)) {

					msg = Checkout.analyzingMessage(message, Checkout.SINGLE_CHAT_REQUEST);

					if (msg[0].equals(username)) {
						beUser = msg[1];

						if (!list.contains(beUser)) {	// �б����ڸ��û����������̲߳�ֹͣѭ�����������̹߳���
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
		} catch (SocketException e) { // ���쳣���������������δ֪ͨ�ͻ��˵�����½����رյ����
			if (client != null) {
				JOptionPane.showMessageDialog(frame, "�����������жϣ�����Ҫ�ر�", "����",
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
