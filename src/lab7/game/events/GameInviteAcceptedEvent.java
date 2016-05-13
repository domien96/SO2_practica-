package lab7.game.events;

import eventbroker.Event;

/**
 * Created by domien on 7/05/2016.
 */
public class GameInviteAcceptedEvent extends Event {

    public GameInviteAcceptedEvent(String type, String message) {
        super(type, message);
    }
}
