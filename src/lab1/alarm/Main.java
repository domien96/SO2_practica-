package lab1.alarm;

public class Main {
    
    public static void main(String[] args){

        Hospital hospital1 = new Hospital("UZ");
        PoliceDepartment police = new PoliceDepartment();
        Hospital hospital2 = new Hospital("AZ");
        FireDepartment firemen = new FireDepartment();
        
        EmergencyCallCenter callCenter = new EmergencyCallCenter("112");
        callCenter.addListener(hospital1);
        callCenter.addListener(police);
        callCenter.addListener(hospital2);
        callCenter.addListener(firemen);
        
        callCenter.incomingCall("crash", "Plateaustraat");
        callCenter.incomingCall("assault", "Veldstraat");
        callCenter.incomingCall("fire", "Zwijnaardse Steenweg");

        EmergencyCallCenter callCenter2 = new EmergencyCallCenter("101");
        callCenter2.addListener(police);

        callCenter2.incomingCall("fire","Voskenslaan");
        callCenter2.incomingCall("fire","Hogent campus Schoonmeersen");

        
    }
}
