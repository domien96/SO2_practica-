package lab4.main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lab4.chat.ChatPanel;

import java.util.ArrayList;

/**
 * Labo JavaFX.
 * Created by jens on 25/03/2016.
 */
public class Main extends Application {

    private GridPane pane;
    private TextField username;
    private TextField port;
    private Button login;
    private Label errorLabel;

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

        primaryStage.show();
    }

    private void loginClicked() {
        errorLabel.setVisible(false);
        if (username.getLength() != 0) {
            int poort = -1;
            if (isNum(port.getText())) {
                poort = Integer.parseInt(port.getText());
                if (poort > 0 && poort < 65535) { //2^16 - 1
                    //geldige login
                    ChatPanel pnl = ChatPanel.createChatPanel();
                    pnl.getChatController().setServerPort(poort);
                    pnl.getChatController().getModel().setStarter(username.getText());
                    //laad fxml in

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
    }
}
