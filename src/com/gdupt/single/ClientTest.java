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
        single.getjTextAreaMain().append("服务器地址：" + fileServer + ", " + "端口号： " + port + "\n");
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
            System.out.print("连接服务器成功!" + "\n");
            single.getjTextAreaMain().append("连接服务器成功!" + "\n");
            single.getjTextAreaMain().setCaretPosition(single.getjTextAreaMain().getText().length());
            return true;
        } catch (Exception e) {
            System.out.print("连接服务器失败!" + "\n");
            single.getjTextAreaMain().append("连接服务器失败!" + "\n");
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
            System.out.print("接收消息缓存错误\n");
            single.getjTextAreaMain().append("接收消息缓存错误!" + "\n");
            single.getjTextAreaMain().setCaretPosition(single.getjTextAreaMain().getText().length());
            return;
        }

        try {
            //本地保存路径，文件名会自动从服务器端继承而来。
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
            
            single.getjTextAreaMain().append("文件的长度为:" + len + "\n");
            single.getjTextAreaMain().setCaretPosition(single.getjTextAreaMain().getText().length());
            single.getjTextAreaMain().append("开始接收文件!" + "\n");
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
                
                
                
                //下面进度条本为图形界面的prograssBar做的，这里如果是打文件，可能会重复打印出一些相同的百分比
                single.getjTextAreaMain().append("文件接收了" +  (passedlen * 100/ len) + "%\n");
                single.getjTextAreaMain().setCaretPosition(single.getjTextAreaMain().getText().length());
                fileOut.write(buf, 0, read);
                if (passedlen == len) {
                    break;
                }
            }
            single.getjTextAreaMain().append("接收完成，文件存为" + savePath + "\n");
            single.getjTextAreaMain().setCaretPosition(single.getjTextAreaMain().getText().length());
            clientSocket.sendMessage("11");

            fileOut.close();
        } catch (Exception e) {
        	e.printStackTrace();
            //single.getjTextAreaMain().append("接收消息错误" + "\n");
            //single.getjTextAreaMain().setCaretPosition(single.getjTextAreaMain().getText().length());
            return;
        }
    }
}