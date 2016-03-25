package lab4.chat;

import java.util.List;

/**
 * Created by domien on 25/03/2016.
 */
public class ChatModel {
    private String starter; // degene die de chatsessie startte.
    private List<ChatMessage> messages;

    public String getStarter() {
        return starter;
    }

    public void setStarter(String starter) {
        this.starter = starter;
    }
}
