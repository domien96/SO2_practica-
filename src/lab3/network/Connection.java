package lab3.network;

import lab3.eventbroker.Event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Network network;
    private boolean stop; // receive-lus

    public Connection(Socket socket, Network network) {
        this.socket = socket;
        this.network = network;
        try {
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Cannot obtain in- or -outputstream.");
            e.printStackTrace();
        }
    }

    public void send(Event e) {
        try {
            output.writeObject(e);
        } catch (IOException e1) {
            System.err.println("Cannot write Event to outputstream.");
            e1.printStackTrace();
        }
    }

    public void receive() {
        new Thread(() ->{
            Object received;
            while(!stop) {
                try {
                    received = input.readObject();
                    assert(received instanceof Event);
                    network.handleEvent((Event)received);
                } catch (IOException e) {
                    System.err.println("Error while receiving object. Cannot receive object.");
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    System.err.println("Received Object's class cannot not be recognized.");
                    e.printStackTrace();
                } catch (ClassCastException e) {
                    System.err.println("Expected instance of lab3.eventbroker.Event but got object of another class");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void close() {
        stop = true;
        try {
            socket.close();
            input.close();
            output.close();
        } catch (IOException e) {
            System.err.println("Cannot close socket.");
            e.printStackTrace();
        }
    }
}
