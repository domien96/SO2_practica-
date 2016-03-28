package lab4.chat;

import eventbroker.Event;
import eventbroker.EventListener;
import eventbroker.EventPublisher;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Created by domien on 25/03/2016.
 */
public class ChatController extends EventPublisher {

    @FXML
    public TextArea inputfield;

    @FXML
    public TextArea content;

    public TextArea getContent() {
        return content;
    }

    public ChatModel getModel() {
        return model;
    }

    public void setModel(ChatModel model) {
        this.model = model;
    }

    private ChatModel model;

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    int serverPort;

    public ChatController() {

    }

    private void initialize() {

    }

    @FXML
    public void sendMessage() {
        publishEvent(new ChatMessage("chatmessage",model.getStarter(),inputfield.getText()));
    }

    public void sendMessage(String sender, String msg) {
        publishEvent(new ChatMessage("chatmessage",sender,msg));
    }

    public class ChatEventHandler implements EventListener {

        @Override
        public void handleEvent(Event event) {
            ChatMessage msg = (ChatMessage) event;
            model.addMessage(String.format("%1s: %2s",msg.getSender(),msg.getMessage()) + '\n');
        }
    }
}
