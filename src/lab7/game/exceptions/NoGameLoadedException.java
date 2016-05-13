package lab7.game.exceptions;

/**
 * Created by domien on 7/05/2016.
 */
public class NoGameLoadedException extends Exception {

    public NoGameLoadedException() {
        super("No game loaded.");
    }
}
