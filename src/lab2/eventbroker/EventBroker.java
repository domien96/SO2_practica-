package lab2.eventbroker;

import lab2.order.OrderProcessor;

import java.util.*;

final public class EventBroker implements Runnable{

    private LinkedList<QueueItem> queue = new LinkedList<>();
    private volatile boolean stop = false;
    private Thread eventsVerwerker;

    // Generieke luistenaars
    protected Set<EventListener> listeners = new HashSet<>();

    // Niet-generieke luisteraars
    protected Map<String, Set<EventListener>> typedlisteners = new HashMap<>();

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

    public void addEventListener(String type, EventListener s) {
        if(typedlisteners.get(type) == null) {
                typedlisteners.put(type, new HashSet<>());
            }
        typedlisteners.get(type).add(s);
    }

    public void removeEventListener(EventListener s) {
        listeners.remove(s);
    }

    public synchronized void addEvent(EventPublisher source, Event e) {
        if (!stop) {
            synchronized (queue) {
                queue.add(new QueueItem(source,e));
                queue.notifyAll();
            }
        }
    }

    @Override
    public void run() {
        QueueItem cur;
        while(!stop || !queue.isEmpty()) {
            synchronized (queue) {
                if(queue.isEmpty())
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                cur = queue.poll();
            }
            if (cur != null) {
                // GENERIEKE LUISTERAARS
                for (EventListener l : listeners) {
                        if (l != cur.source) {
                                l.handleEvent(cur.e); // prevent loops !
                        }
                }
                // NIETGENERIEKE LUISTERAARS
                if (typedlisteners.containsKey(cur.e.getType())) {
                    for (EventListener l : typedlisteners.get(cur.e.getType())) {
                        if (l != cur.source) {
                                l.handleEvent(cur.e); // prevent loops !
                        }
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
            synchronized (queue) {
                queue.notifyAll(); //laat run afwerken indien die aan het wachten zou zijn op nieuwe events.
            }
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
