package lab4.chat;

import eventbroker.Event;
import eventbroker.EventBroker;
import eventbroker.EventListener;
import eventbroker.EventPublisher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import lab4.main.Main;
import network.Network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * Created by domien on 25/03/2016.
 */
public class ChatController extends EventPublisher {

    @FXML
    public TextField inputfield;

    @FXML
    public TextArea content;

    public TextArea getContent() {
        return content;
    }

    public ChatModel getModel() {
        return model;
    }

    public void setModel(ChatModel model) {
        this.model = model;
    }

    private ChatModel model;

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    int serverPort;

    public ChatController() {

    }

    private void initialize() {
        content.setScrollTop(Double.MAX_VALUE);
    }

    @FXML
    public void sendMessage() {
        if (!(inputfield.getText().trim().isEmpty())) {
            publishEvent(new ChatMessage("chatmessage",model.getStarter(),inputfield.getText()));
            inputfield.setText("");
        }
    }

    public void sendInfoMessage(String msg) {
        publishEvent(new ChatMessage("chatmessage","<Info>",msg));
    }

    public void writeConsole(String msg) {
        model.addMessage(msg);
    }

    /*****************************
     * CONNECT... (Menu-option)
     *****************************/
    /**
     * Zal het adres en poort opvragen er vervolgens mee proberen te verbinden.
     * @param actionEvent
     */
    public void openConnectDialog(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Connect...");
        dialog.setHeaderText("Step 1/2");
        dialog.setContentText("Please enter the IP-address:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()){
            String ip = result.get();
            dialog = new TextInputDialog();
            dialog.setTitle("Connect...");
            dialog.setHeaderText("Step 2/2");
            dialog.setContentText("Please enter the port:");
            result = dialog.showAndWait();
            if (result.isPresent()){
                String port = result.get();
                connect(ip,port);
            }
        }
    }

    private void connect(String ip, String poort) {
        try {
            if (setupClient(Integer.parseInt(poort),ip)) {
                sendInfoMessage(String.format(model.getStarter() + " connected to %1s:%2s.", ip, poort));
            }
        } catch (NumberFormatException e) {
            writeConsole("<Error> Port is an unsigned number.");
        }
    }

    /**
     * @return :
     *      True if succesfully server-Network is built.
     *      If false, probably another server is already made on this portfield.
     */
    private boolean setupClient(int poort, String IP) {
        Network network = Main.getNetwork();
        try {
            // Destroy previous network. (This will be a server network if connecting for the first time)
            EventBroker.getEventBroker().removeEventListener(network);
            network.terminate();
        } catch (IOException e) {

        }
        try {
            // Transform this application to client.
            InetAddress address = InetAddress.getByName(IP);
            network = new Network();
            Main.setNetwork(network);
            network.connect(address, poort);
            return true;
        } catch (UnknownHostException e) {
            EventBroker.getEventBroker().removeEventListener(network);
            writeConsole("<Error> Invalid address or Address not found.");
            return false;
        } catch (IOException e) {
            EventBroker.getEventBroker().removeEventListener(network);
            writeConsole("<Error> Address found, but I cannot connect to given address. Received no response.");
            return false;
            //e.printStackTrace();
        }
    }


    /**********************************/

    public class ChatEventHandler implements EventListener {

        @Override
        public void handleEvent(Event event) {
            ChatMessage msg = (ChatMessage) event;
            model.addMessage(String.format("%1s: %2s",msg.getSender(),msg.getMessage()) + '\n');
        }
    }
}
