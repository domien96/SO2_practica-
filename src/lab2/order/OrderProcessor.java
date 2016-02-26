
package lab2.order;


import lab1.eventbroker.Event;
import lab1.eventbroker.EventBroker;
import lab1.eventbroker.EventListener;

public class OrderProcessor implements EventListener {

    protected static int processedOrders = 0;
    
    public OrderProcessor(){
        EventBroker.getEventBroker().addEventListener(OrderEvent.TYPE_ORDER, this);
    }
    
    @Override
    public void handleEvent(Event e) {
        OrderEvent order = (OrderEvent) e;
        processOrder(order);
    }

    
    protected synchronized void processOrder(OrderEvent order){
        processedOrders++;
        System.out.println("Order of item "+order.getItem()+" for customer "+order.getCustomer()+ " processed!");
    }

    public static synchronized int getNumberOfOrders(){
        return processedOrders;
    }
   
    // do some dummy work for @milliseconds ms
    private void doWork(int milliseconds){
        long t1 = System.currentTimeMillis();
        int counter = 0;
        while(System.currentTimeMillis()-t1 < milliseconds){
            counter++;
        }
    }
}
