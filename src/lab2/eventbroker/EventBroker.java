package lab2.eventbroker;

import lab2.order.OrderProcessor;

import java.util.*;

final public class EventBroker implements Runnable{

    private LinkedList<QueueItem> queue = new LinkedList<>();
    private volatile boolean stop = false;
    private Thread eventsVerwerker;

    // Generieke luistenaars
    protected Set<EventListener> listeners = new HashSet<>();

    protected final static EventBroker broker = new EventBroker();

    /**
     *
     *  METHODS
     *
     */

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

    public synchronized void addEvent(EventPublisher source, Event e) {
        queue.add(new QueueItem(source,e));
    }

    @Override
    public void run() {
        QueueItem cur;
        while(!stop || !queue.isEmpty()) {
            cur = queue.poll();
            if (cur != null) {
                for (EventListener l : listeners) {
                    if (cur.source != l) {
                        l.handleEvent(cur.e); // prevent loops !
                    }
                }
            }
        }
    }

    public synchronized void start() {
        stop = false;
        eventsVerwerker = new Thread(() -> run());
        eventsVerwerker.setName("Eventsverwerker");
        eventsVerwerker.start();
    }

    public synchronized void stop() {
        stop = true;
        try {
            eventsVerwerker.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
