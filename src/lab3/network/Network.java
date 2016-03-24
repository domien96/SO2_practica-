package lab3.network;

import lab3.eventbroker.Event;
import lab3.eventbroker.EventBroker;
import lab3.eventbroker.EventListener;
import lab3.eventbroker.EventPublisher;

import java.net.InetAddress;
import java.net.Socket;

public class Network  extends EventPublisher implements EventListener {
    private Network() {
        //vermijden van default constructor.
    }

    public Network(int serverPort) {
        EventBroker.getEventBroker().addEventListener(this);
    }

    public Connection connect(InetAddress address, int port) {

    }

    public Connection connect(Socket socket) {

    }

    public void handleEvent(Event e) {

    }

    public void terminate() {

    }
}
