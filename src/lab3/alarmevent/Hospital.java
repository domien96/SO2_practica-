package lab3.alarmevent;

import lab1.eventbroker.Event;
import lab1.eventbroker.EventBroker;
import lab1.eventbroker.EventListener;

public class Hospital implements EventListener {

    private String name;
    
    public Hospital(String name){
        this.name = name;
        EventBroker.getEventBroker().addEventListener("fire",this);
        EventBroker.getEventBroker().addEventListener("crash",this);
    }
    
    public String getName(){
        return name;
    }
    
    @Override
    public void handleEvent(Event e) {
        if(e instanceof AlarmEvent){
            AlarmEvent alarm = (AlarmEvent) e;
            System.out.println(name+" sends an ambulance to "+alarm.getLocation()+" for "+alarm.getType());
        }
    }
    
}
