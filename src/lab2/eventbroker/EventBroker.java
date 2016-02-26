package lab2.eventbroker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

final public class EventBroker {

    // Generieke luistenaars
    protected Set<EventListener> listeners = new HashSet<>();
    protected final static EventBroker broker = new EventBroker();

    private EventBroker() {
    }

    public static EventBroker getEventBroker() {
        return broker;
    }

    public void addEventListener(EventListener s) {
        listeners.add(s);
    }

    public void removeEventListener(EventListener s) {
        listeners.remove(s);
    }

    void addEvent(EventPublisher source, Event e) {
        process(source, e);
    }

    private void process(EventPublisher source, Event e) {
        // GENERIEKE LUISTERAARS
        for (EventListener l : listeners) {
            if (l != source) {
                l.handleEvent(e); // prevent loops !
            }
        }
        // NIET-GENERIEKE LUISTERAARS
        if (typedlisteners.containsKey(e.getType())) {
            for (EventListener l : typedlisteners.get(e.getType())) {
                if (l != source) {
                    l.handleEvent(e); // prevent loops !
                }
            }
        }
    }

    // Zelf toegevoegd

    // Niet-generieke luisteraars
    protected Map<String, Set<EventListener>> typedlisteners = new HashMap<>();

    public void addEventListener(String type, EventListener s) {
        if(typedlisteners.get(type) == null) {
            typedlisteners.put(type, new HashSet<>());
        }
        typedlisteners.get(type).add(s);
    }
}
