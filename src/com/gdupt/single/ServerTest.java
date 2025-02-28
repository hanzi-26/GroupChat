package com.gdupt.single;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.*;

import com.gdupt.client.*;

public class ServerTest extends Thread {
	ClientSingle single;
	File file;
	ServerSocket serverSocket;
	Socket socket;
	
	// 光标始终在尾部
	public void carePosition() {
		single.getjTextAreaMain().setCaretPosition(
				single.getjTextAreaMain().getText().length());
	}
	
	@Override
	public void run() {
		
		try {

			single.getjTextAreaMain()
					.append("文件长度: " + (int) file.length() + "\n");
			single.getjTextAreaMain().append("正在连接..." + "\n");
			carePosition();

			// IOException侦听并接受到此套接字的连接。此方法在进行连接之前一直阻塞。

			socket = serverSocket.accept();
			
			single.getjTextAreaMain().append("成功建立连接" + "\n");
			carePosition();

			// 作为socket的输入流
			DataInputStream dis = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			//dis.readByte();

			// 读取文件
			DataInputStream fis = new DataInputStream(new BufferedInputStream(
					new FileInputStream(file)));

			DataOutputStream ps = new DataOutputStream(socket.getOutputStream());

			ps.writeUTF(file.getName());	// 发送文件名
			ps.flush();
			ps.writeLong((long) file.length());		// 发送文件长度
			ps.flush();

			int bufferSize = 8192;
			byte[] buf = new byte[bufferSize];

			while (true) {
				int read = 0;
				if (fis != null) {
					read = fis.read(buf);
				}

				if (read == -1) {
					break;
				}
				ps.write(buf, 0, read);
			}
			ps.flush();
			// 注意关闭socket链接哦，不然客户端会等待server的数据过来，
			// 直到socket超时，导致数据不完整。

			single.getjTextAreaMain().append("文件传输完成" + "\n");
			carePosition();
			
			dis.readUTF();
			
			fis.close();
			ps.close();
			dis.close();
			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ServerTest(ServerSocket serverSocket_, File file,
			ClientSingle single_) {
		this.serverSocket = serverSocket_;
		this.file = file;
		this.single = single_;
	}
}
