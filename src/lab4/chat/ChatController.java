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

    public TextArea inputfield;

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
        publishEvent(new Event("chatmessage",model.getStarter()+ ": "+inputfield.getText()));
    }

    class ChatEventHandler implements EventListener {

        @Override
        public void handleEvent(Event event) {
            model.addMessage(new ChatMessage(model.getStarter(),inputfield.getText()));
        }
    }
}
