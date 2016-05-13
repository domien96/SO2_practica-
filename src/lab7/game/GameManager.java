package lab7.game;

import eventbroker.Event;
import eventbroker.EventListener;
import eventbroker.EventPublisher;
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
        if (event instanceof Game)
    }

    public void sendInvitation() {
        publishEvent(new GameInvitationEvent());
    }

    public void acceptInvitation() {
        publishEvent(new GameInviteAcceptedEvent());
    }

    public void declineInvitation() {
        publishEvent(new GameInviteDeclinedEvent());
    }
}
