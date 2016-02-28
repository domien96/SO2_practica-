
package lab2.order;


import lab2.eventbroker.Event;
import lab2.eventbroker.EventBroker;
import lab2.eventbroker.EventListener;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderProcessor implements EventListener {

    protected static int processedOrders = 0;

    //opgave 6
    public static ExecutorService threadpool = Executors.newCachedThreadPool();
    
    protected OrderProcessor(){

    }

    public static OrderProcessor createInstance() {
        OrderProcessor a = new OrderProcessor();
        EventBroker.getEventBroker().addEventListener(OrderEvent.TYPE_ORDER, a);
        return a;
    }
    
    @Override
    public void handleEvent(Event e) {
        OrderEvent order = (OrderEvent) e;
        processOrder(order);
    }

    
    protected synchronized void processOrder(OrderEvent order){
        processedOrders++;
        threadpool.execute(() -> { // opgave 6 : threadpool
            doWork(10);
            System.out.println("Order of item "+order.getItem()+" for customer "+order.getCustomer()+ " processed!");
        });
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
