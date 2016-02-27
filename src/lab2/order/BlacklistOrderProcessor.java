
package lab2.order;

import lab2.eventbroker.EventBroker;

import java.util.HashSet;
import java.util.Set;


public class BlacklistOrderProcessor extends OrderProcessor {

    private Set<String> blacklist;
    
    private BlacklistOrderProcessor(){
        super();
        
        // some work to get to the blacklist data
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {}
       
        // add the blacklisted customers
        blacklist = new HashSet<String>();
        blacklist.add("Jan");
    }

    public static BlacklistOrderProcessor createInstance() {
        BlacklistOrderProcessor a = new BlacklistOrderProcessor();
        EventBroker.getEventBroker().addEventListener(OrderEvent.TYPE_ORDER, a);
        return a;
    }
    
    @Override
    protected synchronized void processOrder(OrderEvent order){
        // ignore blacklisted customers
        if(blacklist.contains(order.getCustomer())){
            System.out.println("Order of customer "+order.getCustomer()+" discarded");
            return;
        }
        
        super.processOrder(order);
    }
    
}
