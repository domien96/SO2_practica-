package lab3.network;

import lab3.eventbroker.Event;
import lab3.eventbroker.EventBroker;
import lab3.eventbroker.EventListener;
import lab3.eventbroker.EventPublisher;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Network  extends EventPublisher implements EventListener {
    private final int serverPort;
    private final ConnectionListener connectionListener;

    public Network() {
        //default poort
        this(1024);
    }

    public Network(int serverPort) {
        this.serverPort = serverPort;
        EventBroker.getEventBroker().addEventListener(this);
        this.connectionListener = new ConnectionListener(this,serverPort);
        connectionListener.start();
    }

    public Connection connect(InetAddress address, int port) {
        try {
            return this.connect(new Socket(address,port));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Connection connect(Socket socket) {
        return new Connection(socket,this);
    }

    public void handleEvent(Event e) {
        publishEvent(e);
    }

    public void terminate() {

    }
}
