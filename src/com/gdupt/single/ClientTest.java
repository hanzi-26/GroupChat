package com.gdupt.single;

import java.io.*;

import com.gdupt.client.*;

public class ClientTest extends Thread {
    private ClientSocket clientSocket = null;

    private String ip;

    private int port;

    private ClientSingle single;

    public ClientTest(ClientSingle single_, String fileServer, int po) {
        this.single = single_;
        ip = fileServer;
        port = po;
        single.getjTextAreaMain().append("��������ַ��" + fileServer + ", " + "�˿ںţ� " + port + "\n");
    }
    
    @Override
    public void run() {
    	try {
            if (createConnection()) {
                getMessage();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean createConnection() {
        clientSocket = new ClientSocket(ip, port);
        try {
            clientSocket.CreateConnection();
            System.out.print("���ӷ������ɹ�!" + "\n");
            single.getjTextAreaMain().append("���ӷ������ɹ�!" + "\n");
            single.getjTextAreaMain().setCaretPosition(single.getjTextAreaMain().getText().length());
            return true;
        } catch (Exception e) {
            System.out.print("���ӷ�����ʧ��!" + "\n");
            single.getjTextAreaMain().append("���ӷ�����ʧ��!" + "\n");
            single.getjTextAreaMain().setCaretPosition(single.getjTextAreaMain().getText().length());
            return false;
        }

    }

    private void getMessage() {
        if (clientSocket == null)
            return;
        DataInputStream inputStream = null;
        try {
            inputStream = clientSocket.getMessageStream();
        } catch (Exception e) {
            System.out.print("������Ϣ�������\n");
            single.getjTextAreaMain().append("������Ϣ�������!" + "\n");
            single.getjTextAreaMain().setCaretPosition(single.getjTextAreaMain().getText().length());
            return;
        }

        try {
            //���ر���·�����ļ������Զ��ӷ������˼̳ж�����
            String savePath = "c:/JavaReceive/";
            
            if(!(new File(savePath).exists())) {
            	new File(savePath).mkdir();
            }
            int bufferSize = 8192;
            byte[] buf = new byte[bufferSize];
            long passedlen = 0;
            long len=0;
            
            savePath += inputStream.readUTF();
            DataOutputStream fileOut = new DataOutputStream(new BufferedOutputStream(new BufferedOutputStream(new FileOutputStream(savePath))));
            len = inputStream.readLong();
            
            single.getjTextAreaMain().append("�ļ��ĳ���Ϊ:" + len + "\n");
            single.getjTextAreaMain().setCaretPosition(single.getjTextAreaMain().getText().length());
            single.getjTextAreaMain().append("��ʼ�����ļ�!" + "\n");
            single.getjTextAreaMain().setCaretPosition(single.getjTextAreaMain().getText().length());
                    
            while (true) {
                int read = 0;
                if (inputStream != null) {
                    read = inputStream.read(buf);
                }
                
               /* if (read == 0) {
                    break;
                }*/
                passedlen = (long)(read + passedlen);
                
                
                
                //�����������Ϊͼ�ν����prograssBar���ģ���������Ǵ��ļ������ܻ��ظ���ӡ��һЩ��ͬ�İٷֱ�
                single.getjTextAreaMain().append("�ļ�������" +  (passedlen * 100/ len) + "%\n");
                single.getjTextAreaMain().setCaretPosition(single.getjTextAreaMain().getText().length());
                fileOut.write(buf, 0, read);
                if (passedlen == len) {
                    break;
                }
            }
            single.getjTextAreaMain().append("������ɣ��ļ���Ϊ" + savePath + "\n");
            single.getjTextAreaMain().setCaretPosition(single.getjTextAreaMain().getText().length());
            clientSocket.sendMessage("11");

            fileOut.close();
        } catch (Exception e) {
        	e.printStackTrace();
            //single.getjTextAreaMain().append("������Ϣ����" + "\n");
            //single.getjTextAreaMain().setCaretPosition(single.getjTextAreaMain().getText().length());
            return;
        }
    }
}