package lab5.othelllo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.Serializable;

/**
 * Created by domien on 17/04/2016.
 */
public class OthelloPanel implements Serializable, ChangeListener<Integer> {

    private final HBox panel = new HBox();

    public OthelloPanel(int SIZE) {
        // initialize ...
        GridPane board = new GridPane();
        board.setVgap(10);
        board.setHgap(10);

        //counter
        VBox counters = new VBox();
        counters.setPadding(new Insets(15));

        Counter black= new Counter(), empty=new Counter(), white=new Counter();
        black.setStateToCount(-1);
        empty.setStateToCount(0);
        empty.setNumber(SIZE*SIZE); // BY DEFAULT ALL EMPTY
        white.setStateToCount(1);
        counters.getChildren().addAll(empty.getContent(),black.getContent(),white.getContent());

        // Initializing cells

        // // First 4 cells in center
        int mid= Math.round(SIZE/2)-1; //left upper center-cell index

        for(int r=0; r<SIZE;r++) {
            for (int c=0;c<SIZE;c++) {
                OthelloPiece pce = new OthelloPiece();
                pce.stateProperty().addListener(black);
                pce.stateProperty().addListener(empty);
                pce.stateProperty().addListener(white);
                board.add(pce.getContent(),c,r);

                // Set color of center Cells
                if(c==mid) {
                    if(r==mid) {
                        pce.setState(1);
                    } else if (r==mid+1) {
                        pce.setState(-1);
                    }
                } else if (c==mid+1) {
                    if(r==mid) {
                        pce.setState(-1);
                    } else if (r==mid+1) {
                        pce.setState(1);
                    }
                }
            }
        }

        // Root pane
        panel.getChildren().addAll(board,counters);
        panel.setPadding(new Insets(25));
    }

    public Pane getContent() {
        return panel;
    }

    @Override
    public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {

    }
}
