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

    protected static final Whiteboard whiteboard = new Whiteboard();

    private Set<AlarmListener> alarmListeners = new HashSet<>();
    private Map<String, Set<AlarmListener>> typedListeners = new HashMap<>();

    private Whiteboard() {
    }

    public static Whiteboard getWhiteboard() {
        return whiteboard;
    }

    public void addAlarmListener(String type, AlarmListener alarmListener) {
        alarmListeners.add(alarmListener);

        if (!typedListeners.containsKey(type)) {
            typedListeners.put(type, new HashSet<>());
        }
        typedListeners.get(type).add(alarmListener);
    }

    public void removeAlarmListener(String type, AlarmListener alarmListener) {
        alarmListeners.remove(alarmListener);

        if (typedListeners.containsKey(type)) {
            typedListeners.get(type).remove(alarmListener);
        }
    }

    public Set<AlarmListener> getAlarmListeners(String type) {
        return typedListeners.get(type);
    }

}
