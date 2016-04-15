package lab5.othelllo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by domien on 15/04/2016.
 */
public class MainOthelloPiece extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();
        primaryStage.setScene(new Scene(root,300,300));

        root.add(new OthelloPiece().getContent(),0,0);
        root.add(new OthelloPiece().getContent(),0,1);
        root.add(new OthelloPiece().getContent(),1,0);
        root.add(new OthelloPiece().getContent(),1,1);

        primaryStage.setTitle("MainOthelloPiece");
        root.setVgap(10);
        root.setHgap(10);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
