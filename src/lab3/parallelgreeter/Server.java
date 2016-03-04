package lab3.parallelgreeter;

import jdk.internal.util.xml.impl.Input;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {

    public static void main(String[] args){

        // parallelle servercode

        int serverPort=1024;
        int bufferSize=100;

        try (final ServerSocket listen = new ServerSocket(serverPort)) {
            while(true) {
                Socket socket = listen.accept();
                new Thread( () -> {
                        try(InputStream is = socket.getInputStream();
                            OutputStream os = socket.getOutputStream()) {
                            while (true) {
                                    byte[] buffer = new byte[bufferSize];
                                    is.read(buffer);
                                    String rMess = verwerk(new String(buffer));
                                    os.write(rMess.getBytes());
                                    os.flush();
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
        }
    }


    public static String verwerk(String naam) {
        return "Hello, "+ naam;
    }
}

