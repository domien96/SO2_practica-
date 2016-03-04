package lab3.alarmevent;

import lab1.eventbroker.Event;

public class AlarmEvent extends Event {
    
    private final String location;
    
    public AlarmEvent(String type, String location){
        super(type, type+" at "+location);
        this.location = location;
    }
    
    public String getLocation(){
        return location;
    }
}
