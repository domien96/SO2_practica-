package lab7.game;

import eventbroker.Event;
import eventbroker.EventListener;
import eventbroker.EventPublisher;
import javafx.application.Platform;
import lab7.game.events.GameInvitationEvent;
import lab7.game.events.GameInviteAcceptedEvent;
import lab7.game.events.GameInviteDeclinedEvent;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by domien on 7/05/2016.
 */
public class GameManager extends EventPublisher implements EventListener {

    GameInterface loadedGame;

    public void loadGame(File gameFile) {
        if(gameFile==null)
            return;
        URLClassLoader ldr;
        try {
            ldr = URLClassLoader.newInstance(new URL[] {gameFile.toURI().toURL()});
        } catch (MalformedURLException e) {
            writeConsole("Something went wrong.");
            e.printStackTrace();
            return;
        }
    }

    public GameInterface getLoadedGame() {
        return null;
    }

    @Override
    public void handleEvent(Event event) {
        Platform.runLater( () -> {
            if (event instanceof GameInvitationEvent) {

            } else if (event instanceof GameInviteAcceptedEvent) {

            } else if (event instanceof GameInviteDeclinedEvent) {

            }
        });
    }

    public void sendInvitation() {
        if (loadedGame != null) {
            publishEvent(new GameInvitationEvent("new game invitation"));
        } else {
            throw new NoGameLoadedException();
        }
    }

    public void acceptInvitation() {
        if (loadedGame != null) {
            publishEvent(new GameInviteAcceptedEvent("challenge accepted"));
        } else {
            throw new NoGameLoadedException();
        }
    }

    public void declineInvitation() {
        if (loadedGame != null) {
            publishEvent(new GameInviteDeclinedEvent("challenge declined"));
        } else {
            throw new NoGameLoadedException();
        }
    }
}
