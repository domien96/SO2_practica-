package lab5.othello;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by domien on 17/04/2016.
 */
public class MainOthello extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        OthelloPanel pnl = new OthelloPanel(8);
        primaryStage.setScene(new Scene(pnl.getContent(),500,500));

        primaryStage.setTitle("MainOthello");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
