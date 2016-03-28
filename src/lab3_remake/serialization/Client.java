package lab3_remake.serialization;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {

    public static void main(String[] args) {
        InetAddress host = null;
        int serverPort = 1024;
        Socket socket = null;
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(
                System.in));
        String name = "";
        try {
            host = InetAddress.getLocalHost();
            socket = new Socket(host,serverPort);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            do {
                // read in a name
                System.out.println("Enter a name : ");
                consoleInput = new BufferedReader(new InputStreamReader(
                        System.in));
                name = consoleInput.readLine();

                if (!(name.equals("stop"))) {
                    String[] split = name.split(" ");
                    if(split.length >= 2) {
                        Person p = new Person(split[0],split[1]);
                        System.out.println(p);
                        // send the name
                        out.writeObject(p);
                        out.flush();

                        // receive reply
                        p = (Person) in.readObject();

                        // print the greeting
                        System.out.println("Reply from server = " +
                                p.toString());
                    }
                }
            } while (!(name.equals("stop")));
        } catch (UnknownHostException e) {
            System.err.println(e);
        } catch (SocketException e) {
            e.printStackTrace();
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                    out.close();
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
