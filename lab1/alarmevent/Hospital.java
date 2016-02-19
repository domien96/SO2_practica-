package alarmevent;

import eventbroker.Event;
import eventbroker.EventBroker;
import eventbroker.EventListener;

/**
 * Created by domien on 19/02/2016.
 */
public class Hospital implements EventListener {
    private final String name;

    public Hospital(String name) {
        this.name = name;
        EventBroker.getEventBroker().addEventListener("fire",this);
        EventBroker.getEventBroker().addEventListener("crash",this);
    }

    @Override
    public void handleEvent(Event e) {
        System.out.println("Hospital "+name+" receives an Alarm with Message : "+e.getMessage());
    }
}
