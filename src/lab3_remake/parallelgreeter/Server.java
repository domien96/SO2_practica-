package lab3_remake.parallelgreeter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {

    public static void main(String[] args){
        
        // parallelle servercode
        ServerSocket ssocket=null;
        int serverPort=1024;
        int bufferSize=100;

        try {
            ssocket=new ServerSocket(serverPort);
            while(true) {
                Socket msocket = ssocket.accept();
                new Thread(() -> {
                    try {
                        //Socket socket = msocket;
                        while(true) {
                            byte[] buffer=new byte[bufferSize];
                            int length = msocket.getInputStream().read(buffer);
                            if (length>0) {
                                String rMess="Hello, "+ (new String(buffer,0,length));
                                msocket.getOutputStream().write(rMess.getBytes());
                            } else {
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch(SocketException e) {
            System.err.println(e);
        } catch(IOException e) {
            System.err.println(e);
        } finally {
            if(ssocket!=null)
                try {
                    ssocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}

