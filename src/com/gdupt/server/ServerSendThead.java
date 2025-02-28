package com.gdupt.server;

import java.io.*;
import java.net.*;
import java.util.*;

import com.gdupt.util.*;

public class ServerSendThead extends Thread {
	String message;
	private DataOutputStream dos;
	List<Socket> list;
	List<Socket> list2;
	Socket socket;
	Socket socket2;
	
	public ServerSendThead(String str, List<Socket> _list, Socket _socket) {
		this.message = str;
		this.list = _list;
		this.socket = _socket;
	}
	
	public ServerSendThead(String str, Socket _socket1, Socket _socket2, List<Socket> _list) {
		this.message = str;
		this.socket = _socket1;
		this.socket2 = _socket2;
		list2 = new ArrayList<Socket>();
		list2.add(socket);
		list2.add(socket2);
		list = list2;
	}
	
	@Override
	public void run() {
		try {
			if(list == null && socket != null) {
				dos = new DataOutputStream(socket.getOutputStream());
				dos.writeUTF(message);
			} else if(message.endsWith(Checkout.ADD_USER)) {
				for(Socket socket_ : list) {
					if(socket_ != socket) {
						dos = new DataOutputStream(socket_.getOutputStream());
						dos.writeUTF(message);
					}
				}
			} else if (message.endsWith(Checkout.SINGLE_CHAT_REQUEST)) {
				for(Socket socket_ : list2) {
					dos = new DataOutputStream(socket_.getOutputStream());
					dos.writeUTF(message);
				}
			} else if (message.endsWith(Checkout.SINGLE_CHATING)) {
					dos = new DataOutputStream(socket2.getOutputStream());
					dos.writeUTF(message);
			} else {
				for(Socket socket_ : list) {
					dos = new DataOutputStream(socket_.getOutputStream());
					dos.writeUTF(message);
				}
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
