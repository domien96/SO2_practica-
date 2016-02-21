package alarmwhiteboard;

import alarm.*;

/**
 * Created by domien on 19/02/2016.
 */
public class Main {

    public static void main(String[] args) {


        Hospital hospital1 = new Hospital("UZ");
        Whiteboard.getWhiteboard().addAlarmListener("fire",hospital1);
        Whiteboard.getWhiteboard().addAlarmListener("crash",hospital1);

        PoliceDepartment police = new PoliceDepartment();
        // Voor elk type de politie linken, maar persoonlijk zou ik dit oplossen door een generieke Addlistener in Whiteboard, maar ik moet figuur 3 volgen.
        Whiteboard.getWhiteboard().addAlarmListener("crash",police);
        Whiteboard.getWhiteboard().addAlarmListener("assault",police);
        Whiteboard.getWhiteboard().addAlarmListener("fire",police);

        Hospital hospital2 = new Hospital("AZ");
        Whiteboard.getWhiteboard().addAlarmListener("fire",hospital2);
        Whiteboard.getWhiteboard().addAlarmListener("crash",hospital2);

        Hospital hospital3 = new Hospital("ASZ");
        Whiteboard.getWhiteboard().addAlarmListener("fire",hospital3);
        Whiteboard.getWhiteboard().addAlarmListener("crash",hospital3);

        FireDepartment firemen = new FireDepartment();
        Whiteboard.getWhiteboard().addAlarmListener("fire",firemen);

        //

        EmergencyCallCenter callCenter = new EmergencyCallCenter("112");
        callCenter.incomingCall("crash", "Plateaustraat");
        callCenter.incomingCall("assault", "Veldstraat");
        callCenter.incomingCall("fire", "Zwijnaardse Steenweg");

        EmergencyCallCenter callCenter2 = new EmergencyCallCenter("101");

        callCenter2.incomingCall("fire","Voskenslaan");
        callCenter2.incomingCall("fire","Hogent campus Schoonmeersen");
        callCenter2.incomingCall("fire","Voskenslaan");
        callCenter2.incomingCall("fire","Hogent campus Schoonmeersen");
        callCenter2.incomingCall("fire","Voskenslaan");
        callCenter2.incomingCall("fire","Hogent campus Schoonmeersen");
    }
}
