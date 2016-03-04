package lab3.alarmevent;


import lab1.eventbroker.Event;
import lab1.eventbroker.EventBroker;
import lab1.eventbroker.EventListener;

public class FireDepartment implements EventListener {

    public FireDepartment(){
        EventBroker.getEventBroker().addEventListener("fire",this);
    }
    
    @Override
    public void handleEvent(Event e) {
        if(e instanceof AlarmEvent){
            AlarmEvent alarm = (AlarmEvent) e;
            System.out.println("Fire squad on the move to "+alarm.getLocation()+" for "+alarm.getType());
        }
    }
    
}
