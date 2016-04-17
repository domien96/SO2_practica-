package lab5.othello;

import javafx.scene.layout.Pane;
import lab5.game.GameInterface;

/**
 * Created by domien on 17/04/2016.
 */
public class Othello implements GameInterface {

    private final OthelloPanel pnl;

    public Othello() {
        this.pnl = new OthelloPanel(4);
    }

    @Override
    public Pane getGamePanel() {
        return pnl.getContent();
    }
}
