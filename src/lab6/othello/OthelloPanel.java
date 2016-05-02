package lab6.othello;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lab6.othello.exception.BoardIndexOutOfBoundsException;
import lab6.othello.exception.InvalidBoardSizeException;
import lab6.othello.exception.InvalidMoveException;

import java.io.Serializable;

/**
 * Created by domien on 17/04/2016.
 */
public class OthelloPanel implements Serializable {

    private final HBox panel = new HBox();
    private final OthelloModel model;
    private final OthelloController ctrl;
    private final int size;
    private final OthelloPiece[][] board;


    public OthelloPanel(int SIZE) throws InvalidBoardSizeException {
        // initialize ...
        this.size = SIZE;
        GridPane board = new GridPane();
        this.board = new OthelloPiece[SIZE][SIZE];
        board.setVgap(10);
        board.setHgap(10);

        //model + ctrl
        this.model = new OthelloModel(SIZE);
        this.ctrl = new OthelloController(model);
        Handler h = new Handler();
        model.validProperty().addListener(h);

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
                this.board[r][c] = pce;
                pce.stateProperty().addListener(black);
                pce.stateProperty().addListener(empty);
                pce.stateProperty().addListener(white);
                board.add(pce.getContent(),c,r);
                initActions(pce,r,c);

                // Set color of center Cells
                try {
                    if(c==mid) {
                        if(r==mid) {
                            model.setState(r,c,1);
                        } else if (r==mid+1) {
                            model.setState(r,c,-1);
                        }
                    } else if (c==mid+1) {
                        if(r==mid) {
                            model.setState(r,c,-1);
                        } else if (r==mid+1) {
                            model.setState(r,c,1);
                        }
                    }
                } catch (BoardIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        }

        h.drawBoard();
        // Root pane
        panel.getChildren().addAll(board,counters);
        panel.setPadding(new Insets(25));
    }

    public Pane getContent() {
        return panel;
    }

    private void initActions(OthelloPiece pce, int r, int c) {
        pce.getContent().setOnMouseClicked(e -> {
            try {
                if (ctrl.isValidMove(r,c))
                    ctrl.doMove(r,c);
            } catch (BoardIndexOutOfBoundsException | InvalidMoveException e1) {
                return;
            }
        });

        pce.getContent().hoverProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (ctrl.isValidMove(r,c))
                    pce.setState(newValue? 2*model.getTurn()-1 : 0);
            } catch (BoardIndexOutOfBoundsException e1) {
                return;
            }
        });
    }

    private class Handler implements ChangeListener<Boolean> {
        private void drawBoard() {
            for(int r=0; r<OthelloPanel.this.size;r++) {
                for (int c = 0; c < OthelloPanel.this.size; c++) {
                    if (OthelloPanel.this.board[r][c] != null)
                        OthelloPanel.this.board[r][c].setState(model.getState(r,c));
                }
            }
        }

        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (newValue) { // enkel indien terug valid
                drawBoard();
                int res;
                if((res = ctrl.isFinished())!=0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Spel Beëindigd");
                    alert.setHeaderText(null);
                    String[] messages = {"Zwart wint!",null,"Wit wint!","Gelijkspel..."};
                    alert.setContentText(messages[res+1]); // checken voor indexOutOfbounds?

                    alert.showAndWait();
                }
            }
        }
    }
}
