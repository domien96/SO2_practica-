package alarmevent;

import eventbroker.EventPublisher;

/**
 * Created by domien on 19/02/2016.
 */
public class EmergencyCallCenter extends EventPublisher {
    private String emergencyNumber;

    public EmergencyCallCenter(String number){
        this.emergencyNumber = number;
    }

    public void incomingCall(String alarm, String location) {
        System.out.println("Incoming call on number "+emergencyNumber);
        publishEvent(new AlarmEvent(alarm, location));
    }
}
