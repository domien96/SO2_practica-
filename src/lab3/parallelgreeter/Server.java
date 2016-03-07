package lab3.parallelgreeter;

import jdk.internal.util.xml.impl.Input;

import java.io.*;
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
                        try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            PrintWriter out = new PrintWriter(new DataOutputStream(socket.getOutputStream()))){
                            while (true) {
                                byte[] buffer = new byte[bufferSize];
                                buffer = in.readLine().getBytes();
                                String rMess = verwerk(new String(buffer));
                                out.print(rMess);
                                out.flush();
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

