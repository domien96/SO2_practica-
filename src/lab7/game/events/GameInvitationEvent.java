package lab7.game.events;

import eventbroker.Event;

/**
 * Created by domien on 7/05/2016.
 */
public class GameInvitationEvent extends Event {

    private static final String TYPE = "Invitation";

    public GameInvitationEvent(String message) {
        super(TYPE, message);
    }
}
