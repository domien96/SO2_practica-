package lab4.chat;

import eventbroker.Event;

/**
 * Created by domien on 25/03/2016.
 */
public class ChatMessage extends Event {
    private String sender;
    private String message;

    public ChatMessage(String type, String message) {
        super(type, message);
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }
}
