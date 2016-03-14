package lab3.serialization;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

public class Server {

    private static ArrayList<Person> db = new ArrayList<Person>();

    public static void main(String[] args) {
        fillInPhoneNumberDataBase();

        ServerSocket listen=null;
        int serverPort=1024;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        Person p = null;

        try {
            listen = new ServerSocket(serverPort);
            Socket socket = listen.accept();
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            while(true) {
                if (p != null) {
                    p = lookUpPhoneNumber(p);
                    out.writeObject(p);
                }

                p = (Person) in.readObject();
            }
        } catch(SocketException e) {
            System.err.println(e);
        } catch(IOException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        } finally {
            if(listen!=null) {
                try {
                    listen.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
