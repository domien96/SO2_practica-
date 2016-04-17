package lab5.Framework.chat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import lab5.main.Main;

import java.io.IOException;

/**
 * Created by jens on 25/03/2016.
 */
public class ChatPanel {

    private TextArea content;
    private ChatModel model = Main.model;
    private ChatController controller;
    private Scene scene;

    private ChatPanel() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("ChatPane.fxml"));

        try {
            scene = new Scene(loader.load());
        } catch (IOException var3) {
            var3.printStackTrace();
        }

        this.controller = (ChatController)loader.getController();
        this.controller.setModel(model);
        this.content = controller.getContent();
        this.content.textProperty().bind(this.controller.getModel().chattextProperty());
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

    public Scene getScene() {
        return scene;
    }
}
