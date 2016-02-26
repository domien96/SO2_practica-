package lab1.alarmwhiteboard;

import lab1.alarm.Alarm;
import lab1.alarm.AlarmListener;
import lab1.alarm.Hospital;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by domien on 19/02/2016.
 */
public class EmergencyCallCenter {
    String number;
    int indexlastCalledHospital;

    public EmergencyCallCenter(String number) {
        this.number = number;
    }

    public void incomingCall(String alarm, String location) {
        System.out.println("Incoming call " + number);
        Set<AlarmListener> listeners = Whiteboard.getWhiteboard().getAlarmListeners(alarm);
        Alarm al = new Alarm(alarm, location);

        // Variabelen voor Speciale Regeling voor ziekenhuizen
        List<AlarmListener> hospitals = new ArrayList<>();

        for (AlarmListener allist : listeners) {
            if(allist instanceof Hospital) {
                hospitals.add(allist);
            } else {
                allist.alarm(al);
            }
        }


        if (hospitals.size() > 0) {
            //Speciale Regeling voor ziekenhuizen
            if(indexlastCalledHospital >= hospitals.size())
                indexlastCalledHospital = 0;
            hospitals.get(indexlastCalledHospital++).alarm(al);
            // !!! mogelijks veranderende volgorde listeners
        }

        //newline
        System.out.println();
    }
}
