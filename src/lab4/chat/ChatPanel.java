package lab4.chat;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by jens on 25/03/2016.
 */
public class ChatPanel {

    private AnchorPane content;
    private ChatModel model;

    private ChatPanel() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("chatclient.fxml"));
        try {
            content = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ChatPanel createChatPanel() {
        return new ChatPanel();
    }

    public void setContent(AnchorPane content) {
        this.content = content;
    }

    public AnchorPane getContent() {
        return content;
    }

    public void setChatModel(ChatModel model) {
        this.model = model;
    }

    public ChatModel getChatModel() {
        return model;
    }

    public void setChatController(ChatController controller) {

    }

    public ChatControler getChatController() {

    }

}
