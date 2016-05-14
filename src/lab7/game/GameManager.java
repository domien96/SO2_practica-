package lab7.game;

import eventbroker.Event;
import eventbroker.EventBroker;
import eventbroker.EventListener;
import eventbroker.EventPublisher;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lab7.game.events.GameInvitationEvent;
import lab7.game.events.GameInviteAcceptedEvent;
import lab7.game.events.GameInviteDeclinedEvent;
import lab7.game.exceptions.GameLoadException;
import lab7.game.exceptions.NoGameLoadedException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by domien on 7/05/2016.
 */
public class GameManager extends EventPublisher implements EventListener {

    private GameInterface loadedGame;

    private GameManager() {
        EventBroker.getEventBroker().addEventListener("Invitation",this);
        EventBroker.getEventBroker().addEventListener("Accept",this);
        EventBroker.getEventBroker().addEventListener("Declined",this);
    }

    public final static GameManager mng = new GameManager();

    public static GameManager getGameManager() { return mng; }

    public void loadGame(File gameFile) throws GameLoadException {
        if(gameFile==null)
            return;
        URLClassLoader ldr;
        try {
            ldr = URLClassLoader.newInstance(new URL[] {gameFile.toURI().toURL()});
        } catch (MalformedURLException e) {
            throw new GameLoadException();
        }

        try {
            Class othello = ldr.loadClass("lab7.othello.Othello");
            GameInterface game = (GameInterface) othello.newInstance();
            loadedGame = game;

            // Zonder cast naar GameInterface
            //Method m = othello.getMethod("getGamePanel");
            //showPane((Pane) m.invoke(othello.newInstance()));
        } catch (ClassNotFoundException e) {
            throw new GameLoadException("Jar does not follow the conventions: Jar has to contain a class with the same name.");
        } catch (InstantiationException e) {
            throw new GameLoadException("Class loaded, but no default constructor.");
        } catch (IllegalAccessException e) {
            throw new GameLoadException("Class loaded, but class or constructor cannot be accessed.");
        } catch (ClassCastException e) {
            throw new GameLoadException("Class loaded, but does not implement GameInterface.");
        }
    }

    public GameInterface getLoadedGame() throws NoGameLoadedException {
        if(loadedGame==null)
            throw new NoGameLoadedException();
        return loadedGame;
    }

    @Override
    public void handleEvent(Event event) {
        Platform.runLater( () -> {
            if (event instanceof GameInvitationEvent) {
                Alert l = new Alert(Alert.AlertType.CONFIRMATION);
                ButtonType accept = new ButtonType("ok"), decline = new ButtonType("nene");
                l.getButtonTypes().clear();
                l.getButtonTypes().addAll(accept,decline);
                if(l.showAndWait().get()==accept) {
                    try {
                        acceptInvitation();
                        loadedGame.startGame(1);
                    } catch (NoGameLoadedException e) {
                        declineInvitation();
                    }
                } else {
                        declineInvitation();
                }
            } else if (event instanceof GameInviteAcceptedEvent) {
                Alert l = new Alert(Alert.AlertType.INFORMATION);
                l.setHeaderText("Other player Accepted");
                l.showAndWait();
                if(loadedGame!=null){
                    loadedGame.startGame(0);}
                else {
                    System.err.println("blabla");
                }
            } else if (event instanceof GameInviteDeclinedEvent) {
                Alert l = new Alert(Alert.AlertType.INFORMATION);
                l.setHeaderText("Other player Declined");
                l.showAndWait();
            }
        });
    }

    public void sendInvitation() throws NoGameLoadedException {
        if (loadedGame != null) {
            publishEvent(new GameInvitationEvent("new game invitation"));
        } else {
            throw new NoGameLoadedException();
        }
    }

    public void acceptInvitation() throws NoGameLoadedException {
        if (loadedGame != null) {
            publishEvent(new GameInviteAcceptedEvent("challenge accepted"));
        } else {
            throw new NoGameLoadedException();
        }
    }

    public void declineInvitation() {
        publishEvent(new GameInviteDeclinedEvent("challenge declined"));
    }
}
