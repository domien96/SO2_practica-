package lab7.game;

import eventbroker.Event;
import eventbroker.EventListener;
import eventbroker.EventPublisher;
import javafx.application.Platform;
import lab7.game.events.GameInvitationEvent;
import lab7.game.events.GameInviteAcceptedEvent;
import lab7.game.events.GameInviteDeclinedEvent;
import lab7.game.exceptions.GameLoadException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by domien on 7/05/2016.
 */
public class GameManager extends EventPublisher implements EventListener {

    GameInterface loadedGame;

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
            Class othello = ldr.loadClass("lab5.othello.Othello");
            lab5.game.GameInterface game = (lab5.game.GameInterface) othello.newInstance();
            loadedGame = game.getGamePanel();

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
    }

    public GameInterface getLoadedGame() {
        if(loadedGame==null)
            throw new NoGameLoadedException();
        return loadedGame;
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
