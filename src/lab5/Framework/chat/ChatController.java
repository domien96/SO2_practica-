package lab5.Framework.chat;

import eventbroker.Event;
import eventbroker.EventBroker;
import eventbroker.EventListener;
import eventbroker.EventPublisher;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import lab5.Framework.main.Main;
import lab5.game.GameInterface;
import network.Network;

import java.io.IOException;
import java.net.*;
import java.nio.file.Path;
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

    /************GAME************/
    public void chooseGame(ActionEvent e) {
        FileChooser chsr = new FileChooser();
        chsr.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Java Archive",".jar"));
        Path path = chsr.showOpenDialog(content.getScene().getWindow()).toPath();
        if(path != null) {
            loadGame(path);
        }
    }

    private void loadGame(Path path) {
        if(path==null)
            return;
        URLClassLoader ldr;
        try {
            ldr = URLClassLoader.newInstance(new URL[] {new URL(path.toString())});
        } catch (MalformedURLException e) {
            writeConsole("Something went wrong.");
            e.printStackTrace();
            return;
        }

        try {
            Class othello = ldr.loadClass("Othello");
            GameInterface game = (GameInterface) othello.newInstance();
            showPane(game.getGamePanel());
        } catch (ClassNotFoundException e) {
            writeConsole("Jar does not follow the conventions: Jar has to contain a class with the same name.");
            e.printStackTrace();
            return;
        } catch (InstantiationException e) {
            writeConsole("Class loaded, but no default constructor.");
            e.printStackTrace();
            return;
        } catch (IllegalAccessException e) {
            writeConsole("Class loaded, but class or constructor cannot be accessed.");
            e.printStackTrace();
            return;
        } catch (ClassCastException e) {
            writeConsole("Class loaded, but does not implement GameInterface.");
            return;
        }
    }

    private void showPane(Pane gamePanel) {
    }


    /**********************************/

    public class ChatEventHandler implements EventListener {

        @Override
        public void handleEvent(Event event) {
            ChatMessage msg = (ChatMessage) event;
            Platform.runLater(
                    () -> model.addMessage(String.format("%1s: %2s",msg.getSender(),msg.getMessage()) + '\n')
            );
        }
    }
}
