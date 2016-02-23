package alarmwhiteboard;

import alarm.FireDepartment;
import alarm.Hospital;
import alarm.PoliceDepartment;

/**
 * Created by domien on 19/02/2016.
 */
public class Main {

    public static void main(String[] args) {
        Hospital hospital1 = new Hospital("UZ");
        PoliceDepartment police = new PoliceDepartment();
        Hospital hospital2 = new Hospital("AZ");
        FireDepartment firemen = new FireDepartment();

        EmergencyCallCenter callCenter = new EmergencyCallCenter("112");
        Whiteboard whiteboard = Whiteboard.getWhiteboard();
        whiteboard.addAlarmListener("fire", hospital1);
        whiteboard.addAlarmListener("crash", hospital1);
        whiteboard.addAlarmListener("", police);
        whiteboard.addAlarmListener("fire", hospital2);
        whiteboard.addAlarmListener("crash", hospital2);
        whiteboard.addAlarmListener("fire", firemen);


        callCenter.incomingCall("crash", "Plateaustraat");
        callCenter.incomingCall("assault", "Veldstraat");
        callCenter.incomingCall("fire", "Zwijnaardse Steenweg");

        EmergencyCallCenter callCenter2 = new EmergencyCallCenter("101");
        whiteboard.addAlarmListener("", police);

        callCenter2.incomingCall("fire","Voskenslaan");
        callCenter2.incomingCall("fire","Hogent campus Schoonmeersen");
    }
}
