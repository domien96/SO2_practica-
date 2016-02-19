package alarmevent;

import eventbroker.Event;
import eventbroker.EventBroker;
import eventbroker.EventListener;
import eventbroker.EventPublisher;

/**
 * Created by domien on 19/02/2016.
 */
public class PoliceDepartment implements EventListener {
    public PoliceDepartment() {
        EventBroker.getEventBroker().addEventListener(this);
    }

    @Override
    public void handleEvent(Event e) {
        System.out.println("Police receives an Alarm with Message : "+e.getMessage());
    }
}
