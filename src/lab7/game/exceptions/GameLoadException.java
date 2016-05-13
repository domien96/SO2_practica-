package lab7.game.exceptions;

/**
 * Created by domien on 7/05/2016.
 */
public class GameLoadException extends Exception {

    public GameLoadException() {
        super("Error while loading game");
    }

    public GameLoadException(String s) {
        super(s);
    }
}
