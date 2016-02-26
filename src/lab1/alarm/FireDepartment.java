package lab1.alarm;

import java.util.Random;

/**
 * Created by domien on 19/02/2016.
 */
public class FireDepartment implements AlarmListener {
    @Override
    public void alarm(Alarm alarm) {
        System.out.println("Fire unit " + new Random().nextInt(10) + " has been sent to the "+ alarm.getType() + " at " + alarm.getLocation());
    }
}
