package alarmwhiteboard;

import alarm.Alarm;
import alarm.AlarmListener;
import alarm.Hospital;

import java.util.Set;

/**
 * Created by domien on 19/02/2016.
 */
public class EmergencyCallCenter {

    private String number;

    public EmergencyCallCenter(String number) {
        this.number = number;
    }

    public void incomingCall(String alarm, String location) {
        Whiteboard whiteboard = Whiteboard.getWhiteboard();
        Set<AlarmListener> set = whiteboard.getAlarmListeners(alarm);

        boolean hospitalNotified = false;

        if (set == null) {
            throw new IllegalStateException("Type: " + alarm + " not registered at an AlarmListener");
        } else {
            for (AlarmListener listener : set) {
                if (listener instanceof Hospital && !hospitalNotified) {
                    hospitalNotified = true;
                    listener.alarm(new Alarm(alarm, location));
                } else {
                    listener.alarm(new Alarm(alarm, location));
                }
            }
        }
    }

}
