package lab7.main;

import eventbroker.EventBroker;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lab7.Framework.chat.ChatController;
import lab7.Framework.chat.ChatModel;
import lab7.Framework.chat.ChatPanel;
import network.Network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Labo JavaFX.
 * Created by jens on 25/03/2016.
 */
public class Main extends Application {

    public final static ChatModel model = new ChatModel();
    private GridPane pane;
    private TextField username;
    private TextField port;
    private Button login;
    private Label errorLabel;

    public static Stage getStage() {
        return stage;
    }

    private static Stage stage;

    public static Network getNetwork() {
        return network;
    }

    public static void setNetwork(Network network) {
        Main.network = network;
    }

    private static Network network;
    private static ChatController ctrler;

    @Override
    public void start(Stage primaryStage) throws Exception {
        pane = new GridPane();
        username = new TextField();
        port = new TextField();
        login = new Button("Log in");
        errorLabel = new Label();
        errorLabel.setVisible(false);
        errorLabel.setTextFill(Color.RED);

        pane.setVgap(10);
        pane.setHgap(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        primaryStage.setTitle("Log in");
        primaryStage.setScene(new Scene(pane, 300, 200));

        pane.add(new Label("Username"),0,0);
        pane.add(username,1,0);
        pane.add(new Label("Port"),0,1);
        pane.add(port,1,1);
        pane.add(login, 0, 2);
        pane.add(errorLabel, 1, 2);

        login.setOnMouseClicked(event -> loginClicked());
        port.onActionProperty().setValue((event)->loginClicked());

        stage=primaryStage;
        primaryStage.show();
    }

    private void loginClicked() {
        errorLabel.setVisible(false);
        if (username.getLength() != 0) {
            int poort = -1;
            if (isNum(port.getText())) {
                poort = Integer.parseInt(port.getText());
                if (poort > 0 && poort < 65535) { //2^16 - 1
                    ChatPanel pnl = ChatPanel.createChatPanel();
                    if (startChatSessie(poort,pnl)) {
                        stage.setScene(pnl.getScene());

                    } else {
                        errorLabel.setText("Kan geen verbinding maken. Poort al in gebruik?");
                        errorLabel.setVisible(true);
                    }
                }
            } else {
                errorLabel.setText("ongeldige poortnummer");
                errorLabel.setVisible(true);
            }
        } else {
            errorLabel.setText("ongeldige naam");
            errorLabel.setVisible(true);
        }
    }

    /**
     * Zet een connectie op als server of als client naargelang er al dan niet een
     * server aan het luisteren is op de poort.
     * @param poort
     * @param pnl
     * @return
     */
    private boolean startChatSessie(int poort, ChatPanel pnl) {
        //geldige login
        pnl.getChatController().setServerPort(poort);
        pnl.getChatController().getModel().setStarter(username.getText());
        ctrler = pnl.getChatController();
        EventBroker.getEventBroker().addEventListener("chatmessage",pnl.getChatController().new ChatEventHandler());
        EventBroker.getEventBroker().start();
        try {
            if (!setupServer(poort)) {
                InetAddress loc = InetAddress.getByName(InetAddress.getLocalHost().getHostAddress());
                if (setupClient(poort,loc)) {
                    pnl.getChatController().sendInfoMessage(String.format(username.getText() + " connected to %1s:%2s.", loc, poort));
                } else {
                    return false;
                }
            } else {
                pnl.getChatController().writeConsole(String.format("<info> Listening at %1s on port %2s...",InetAddress.getLocalHost().getHostAddress(),port.getText()));
                pnl.getChatController().writeConsole("<info> Waiting for client to connect...");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * @return :
     *      True if succesfully server-Network is built.
     *      If false, probably another server is already made on this port.
     */
    private boolean setupClient(int poort, InetAddress adress) {
        try {
            network = new Network();
            network.connect(adress,poort);
            return true;
        } catch (IOException e) {
            EventBroker.getEventBroker().removeEventListener(network);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return :
     *      True if succesfully server-Network is built.
     *      If false, probably another server is already made on this port.
     */
    private boolean setupServer(int poort) {
        try {
            network = new Network(poort);
            return true;
        } catch (IOException e) {
            EventBroker.getEventBroker().removeEventListener(network);
            return false;
        }
    }

    private boolean isNum(String num) {
        ArrayList<Character> numbers = new ArrayList<>();
        for (int i = 48; i < 58; i++) { //ASCII
            numbers.add((char) i);
        }

        char[] buffer = num.toCharArray();
        for (char character: buffer) {
            if (!numbers.contains(character)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        launch(args);

        try {
            ctrler.sendInfoMessage((String.format(model.getStarter() + " disconnected.")) );
            Thread.sleep(100); // Making sure all other clients receive the message before network is closed.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            if (network!= null)
                network.terminate();
        } catch (IOException e) {
            System.err.println("Failed terminating network.");
            e.printStackTrace();
        } finally {
            EventBroker.getEventBroker().stop();
        }
    }
}
