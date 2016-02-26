package lab2.eventbroker;

import lab2.order.OrderProcessor;

import java.util.*;

final public class EventBroker implements Runnable{

    LinkedList<QueueItem> queue = new LinkedList<>();
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
        queue.add(new QueueItem(source,e));
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

    @Override
    public void run() {
        QueueItem cur = queue.poll();
        while(cur != null) {

            //OrderProcessor. todo HOE verwerk ik de event (opgave 3.3)
            cur = queue.poll();
        }
    }


    private class QueueItem {

        public final EventPublisher source;
        public final Event e;

        public QueueItem(EventPublisher source, Event e) {
            this.source = source;
            this.e = e;
        }
    }
}
