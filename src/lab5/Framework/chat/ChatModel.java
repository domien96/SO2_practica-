package lab5.Framework.chat;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by domien on 25/03/2016.
 */
public class ChatModel {
    private String starter; // degene die de chatsessie startte.
    private List<String> messages = new ArrayList<>();

    public String getChattext() {
        return chattext.get();
    }

    public StringProperty chattextProperty() {
        return chattext;
    }

    private StringProperty chattext = new SimpleStringProperty("");

    public String getStarter() {
        return starter;
    }

    public void setStarter(String starter) {
        this.starter = starter;
    }

    public void addMessage(String msg) {
        messages.add(msg);
        chattext.setValue(chattext.get() + msg);
    }
}
