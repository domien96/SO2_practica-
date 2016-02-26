package lab1.alarmevent;

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

        callCenter.incomingCall("crash", "Plateaustraat");
        callCenter.incomingCall("assault", "Veldstraat");
        callCenter.incomingCall("fire", "Zwijnaardse Steenweg");

        EmergencyCallCenter callCenter2 = new EmergencyCallCenter("101");

        callCenter2.incomingCall("fire","Voskenslaan");
        callCenter2.incomingCall("fire","Hogent campus Schoonmeersen");
    }

}
