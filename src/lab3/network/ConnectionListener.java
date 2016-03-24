package lab3.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// listens for incoming connections
public class ConnectionListener extends Thread {

    private Network network;
    private int serverPort;
    private ServerSocket listen = null;
    private Socket socket = null;
    private boolean stop;

    public ConnectionListener(Network network, int serverPort) {
        this.network = network;
        this.serverPort = serverPort;
        stop = false;
    }

    public void run() {
        while(!stop) { //blijven runnen tot terminate opgeroepen wordt
            try {
                listen = new ServerSocket(serverPort);
                socket = listen.accept();
                if (socket != null) { //client gevonden
                    Connection connection = new Connection(socket, network);
                    connection.receive();
                    terminate(); //stop thread als connectie aangemaakt is
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (listen != null) {
                    try {
                        listen.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void terminate() {
        stop = true;
    }
}
