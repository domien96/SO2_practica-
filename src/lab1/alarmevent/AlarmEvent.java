package lab1.alarmevent;

import lab2.eventbroker.Event;

/**
 * Created by domien on 19/02/2016.
 */
public class AlarmEvent extends Event {
    private final String location;

    public AlarmEvent(String type, String location) {
        super(type, "ALARM! er is een "+type+" nabij "+ location);
        this.location = location;
    }
}
