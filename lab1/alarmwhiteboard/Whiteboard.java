package alarmwhiteboard;

import alarm.AlarmListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by domien on 19/02/2016.
 */
final public class Whiteboard {
    // Volgens de gegeven figuur zou ik een set moeten geberuiken, maar met een Set kan ik moeilijk de relatie tussen listeners en types vastleggen.
    //Set<AlarmListener> alarmListeners = new HashSet<>();
    // Hierdoor gebruiken we een Mapping.
    private Map<String, Set<AlarmListener>> alarmListeners = new HashMap<>();

    private final static Whiteboard singleton = new Whiteboard();

    private Whiteboard() {}

    public static Whiteboard getWhiteboard() { return singleton; }

    public void addAlarmListener(String type, AlarmListener l) {
        if(!alarmListeners.containsKey(type))
            alarmListeners.put(type,new HashSet<>());
        alarmListeners.get(type).add(l);
    }

    public void removeAlarmListener(String type, AlarmListener l) {
        if(alarmListeners.containsKey(type)) {
            Set<AlarmListener> filtered = alarmListeners.get(type);
            filtered.remove(l);
            if(filtered.size()==0)
                alarmListeners.remove(type,l);
        } else {
            //do nothing
        }
    }

    public Set<AlarmListener> getAlarmListeners(String type) {
        return alarmListeners.get(type);
    }


}
