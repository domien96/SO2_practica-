package lab5.othello;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by domien on 17/04/2016.
 */
public class MainCounter extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();
        primaryStage.setScene(new Scene(root,300,300));

        Counter black= new Counter(), empty=new Counter(), white=new Counter();
        black.setStateToCount(-1);
        root.add(black.getContent(),1,0);
        empty.setStateToCount(0);
        root.add(empty.getContent(),1,1);
        white.setStateToCount(1);
        root.add(white.getContent(),1,2);

        empty.setNumber(3);
        for(int r=0;r<3;r++) {
            OthelloPiece pce = new OthelloPiece();
            pce.stateProperty().addListener(black);
            pce.stateProperty().addListener(empty);
            pce.stateProperty().addListener(white);
            root.add(pce.getContent(),0,r);

        }

        primaryStage.setTitle("MainCounter");
        root.setVgap(10);
        root.setHgap(10);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
