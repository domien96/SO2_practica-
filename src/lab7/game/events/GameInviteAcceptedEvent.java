package lab7.game.events;

import eventbroker.Event;

/**
 * Created by domien on 7/05/2016.
 */
public class GameInviteAcceptedEvent extends Event {

    private static final String TYPE = "Accept";

    public GameInviteAcceptedEvent(String message) {
        super(TYPE, message);
    }
}
