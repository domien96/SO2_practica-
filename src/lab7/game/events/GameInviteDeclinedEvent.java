package lab7.game.events;

import eventbroker.Event;

/**
 * Created by domien on 7/05/2016.
 */
public class GameInviteDeclinedEvent extends Event {

    private static final String TYPE = "Declined";

    public GameInviteDeclinedEvent(String message) {
        super(TYPE, message);
    }
}
