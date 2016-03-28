package lab3_remake.serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

public class Server {

    private static ArrayList<Person> db = new ArrayList<Person>();

    public static void main(String[] args) {
        fillInPhoneNumberDataBase();
        // server code
        ServerSocket ssocket=null;
        int serverPort=1024;
        int bufferSize=100;

        try {
            ssocket=new ServerSocket(serverPort);
            while (true) {
                final Socket socket = ssocket.accept();
                new Thread(() -> {
                    try {
                        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        while(true) {
                            Person p = (Person) in.readObject();
                            p = lookUpPhoneNumber(p);
                            out.writeObject(p);
                            out.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch(SocketException e) {
            e.printStackTrace();
            System.err.println(e);
        } catch(IOException e) {
            e.printStackTrace();
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

    private static void fillInPhoneNumberDataBase() {
        Person[] p = {new Person("Jan", "Janssens", new PhoneNumber("32", "9", "44 55 66")),
            new Person("Piet", "Pieters", new PhoneNumber("32", "50", "11 22 33")),
            new Person("Giovanni", "Totti", new PhoneNumber("49", "22", "00 99 88")),
            new Person("Jean", "Lefevre", new PhoneNumber("33", "4", "12 34 56"))};
        for (Person i : p) {
            db.add(i);
        }
    }

    private static Person lookUpPhoneNumber(Person p) {
        int index = db.indexOf(p);
        if (index >= 0) {
            return db.get(index);
        } else {
            p.setPhoneNumber(new PhoneNumber());
            return p;
        }
    }
}
