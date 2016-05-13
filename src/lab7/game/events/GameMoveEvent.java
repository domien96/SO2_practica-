package lab7.game.events;

import eventbroker.Event;

/**
 * Created by domien on 7/05/2016.
 */
public class GameMoveEvent extends Event {

    private static final String TYPE = "Move";

    public GameMoveEvent(String message) {
        super(TYPE, message);
    }
}
