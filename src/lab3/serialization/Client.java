package lab3.serialization;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {

    public static void main(String[] args) {

        InetAddress host;
        int serverPort = 1024;
        Socket socket = null;
        BufferedReader consoleInput;
        ObjectOutputStream os = null;
        ObjectInputStream is = null;
        Person p = null;

        try {
            host = InetAddress.getLocalHost();
            socket = new Socket(host, serverPort);
            os = new ObjectOutputStream(socket.getOutputStream());
            os.flush();
            is = new ObjectInputStream(socket.getInputStream());
            while(true) {
                // read in a name
                System.out.println("Voornaam : ");
                consoleInput = new BufferedReader(new InputStreamReader(
                        System.in));
                String name = consoleInput.readLine();
                if (name.equals("stop")) break;

                System.out.println("Achternaam : ");
                consoleInput = new BufferedReader(new InputStreamReader(
                        System.in));
                String lastName = consoleInput.readLine();
                if (lastName.equals("stop")) break;

                p = new Person(name, lastName);

                os.writeObject(p);

                p = (Person) is.readObject();

                //print name and number
                System.out.println(p.getFirstName() + " " + p.getLastName() + ": "
                        + p.getPhoneNumber().getCountryCode() + " " + p.getPhoneNumber().getAreaCode()
                        + " " + p.getPhoneNumber().getNumber());

            }
        } catch (UnknownHostException e) {
            System.err.println(e);
        } catch (SocketException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
