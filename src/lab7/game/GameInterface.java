package lab7.game;

import javafx.scene.layout.Pane;

/**
 * Created by domien on 17/04/2016.
 */
public interface GameInterface {
    public Pane getGamePanel();

    public String getGameName();

    public void startGame(int role);
}
