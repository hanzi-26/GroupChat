package com.gdupt.client;

import java.io.*;
import com.gdupt.util.*;

public class ClientSendThread extends Thread {
	private String sendMessage;
	private String username;
	private DataOutputStream dos;
	

	public ClientSendThread(String message) {
		
		this.username = ClientLogin.getUsername();
		this.sendMessage = message;
		this.dos = new DataOutputStream(ClientLogin.getOut());
	}

	@Override
	public void run() {
		try {
			if(sendMessage.endsWith(Checkout.SINGLE_CHAT_REQUEST) || sendMessage.endsWith(Checkout.SEND_FILE)
					|| sendMessage.endsWith(Checkout.SINGLE_CHATING)) {
				dos.writeUTF(sendMessage);
			} else if (sendMessage.endsWith(Checkout.ADD)) {
				dos.writeUTF(username + sendMessage);
			} else {
				dos.writeUTF(username + ": " + sendMessage);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
