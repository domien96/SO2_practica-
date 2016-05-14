package lab7.Framework.chat;

import eventbroker.Event;

/**
 * Created by domien on 25/03/2016.
 */
public class ChatMessage extends Event {
    private String sender;

    public ChatMessage(String type, String sender, String message) {
        super(type, message);
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }
}
