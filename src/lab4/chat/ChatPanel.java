package lab4.chat;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Created by jens on 25/03/2016.
 */
public class ChatPanel {

    private TextArea content;
    private ChatModel model;
    private ChatController controller;

    private ChatPanel() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("chatclient.fxml"));
        controller = loader.getController();
        controller.setModel(new ChatModel());
        content.textProperty().bind(controller.getModel().chattextProperty());
        try {
            content = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ChatPanel createChatPanel() {
        return new ChatPanel();
    }

    public void setContent(TextArea content) {
        this.content = content;
    }

    public TextArea getContent() {
        return content;
    }

    public void setChatModel(ChatModel model) {
        this.model = model;
    }

    public ChatModel getChatModel() {
        return model;
    }


//    Deze methode is overbodig.
//    public void setChatController(ChatController controller) {
//
//    }

    public ChatController getChatController() {
        return controller;
    }

}
