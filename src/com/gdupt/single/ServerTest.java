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
	
	// ���ʼ����β��
	public void carePosition() {
		single.getjTextAreaMain().setCaretPosition(
				single.getjTextAreaMain().getText().length());
	}
	
	@Override
	public void run() {
		
		try {

			single.getjTextAreaMain()
					.append("�ļ�����: " + (int) file.length() + "\n");
			single.getjTextAreaMain().append("��������..." + "\n");
			carePosition();

			// IOException���������ܵ����׽��ֵ����ӡ��˷����ڽ�������֮ǰһֱ������

			socket = serverSocket.accept();
			
			single.getjTextAreaMain().append("�ɹ���������" + "\n");
			carePosition();

			// ��Ϊsocket��������
			DataInputStream dis = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			//dis.readByte();

			// ��ȡ�ļ�
			DataInputStream fis = new DataInputStream(new BufferedInputStream(
					new FileInputStream(file)));

			DataOutputStream ps = new DataOutputStream(socket.getOutputStream());

			ps.writeUTF(file.getName());	// �����ļ���
			ps.flush();
			ps.writeLong((long) file.length());		// �����ļ�����
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
			// ע��ر�socket����Ŷ����Ȼ�ͻ��˻�ȴ�server�����ݹ�����
			// ֱ��socket��ʱ���������ݲ�������

			single.getjTextAreaMain().append("�ļ��������" + "\n");
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
