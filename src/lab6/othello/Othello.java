package lab6.othello;

import javafx.scene.layout.Pane;
import lab5.game.GameInterface;
import lab6.othello.exception.InvalidBoardSizeException;

/**
 * Created by domien on 17/04/2016.
 */
public class Othello implements GameInterface {

    private OthelloPanel pnl = null;

    public Othello() {
        try {
            this.pnl = new OthelloPanel(4);
        } catch (InvalidBoardSizeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Pane getGamePanel() {
        return pnl.getContent();
    }
}
