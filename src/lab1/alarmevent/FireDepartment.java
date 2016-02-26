package lab1.alarmevent;

import lab1.eventbroker.Event;
import lab1.eventbroker.EventBroker;
import lab1.eventbroker.EventListener;

/**
 * Created by domien on 19/02/2016.
 */
public class FireDepartment implements EventListener {
    public FireDepartment() {
        EventBroker.getEventBroker().addEventListener("fire",this);
    }

    @Override
    public void handleEvent(Event e) {
        System.out.println("Fire receives an Alarm with Message : "+e.getMessage());
    }
}
