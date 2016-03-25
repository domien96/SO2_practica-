package lab4.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by jens on 25/03/2016.
 */
public class Main extends Application {

    private GridPane pane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setPadding(new Insets(0, 10, 0, 10));
        primaryStage.setTitle("Log in");
        primaryStage.setScene(new Scene(pane, 300, 200));
        pane.add(new Label("Username"),0,0);
        pane.add(new TextField(),1,0);
        pane.add(new Label("Port"),0,1);
        pane.add(new TextField(),1,1);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
