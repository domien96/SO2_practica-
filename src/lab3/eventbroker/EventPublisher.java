package lab3.eventbroker;

public class EventPublisher {

    public void publishEvent(Event e) {
        EventBroker.getEventBroker().addEvent(this, e);
    }
}
