package lab7.othello;

import javafx.scene.layout.Pane;
import lab7.game.GameInterface;
import lab7.othello.exception.InvalidBoardSizeException;

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

    @Override
    public String getGameName() {
        return "othello";
    }

    @Override
    public void startGame(int role) {
        pnl.getCtrl().setRole(role);
    }
}
