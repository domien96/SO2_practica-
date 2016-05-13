package lab7.game.events;

import eventbroker.Event;

/**
 * Created by domien on 7/05/2016.
 */
public class GameInviteDeclinedEvent extends Event {

    public GameInviteDeclinedEvent(String type, String message) {
        super(type, message);
    }
}
