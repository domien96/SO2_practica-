
package lab2.order;

import lab2.eventbroker.EventPublisher;


public class Customer extends EventPublisher{

    private String name;
    
    public Customer(String name){
        this.name = name;
    }
    
    public void buy(String item){
        System.out.println(name + " places an order for item "+item);
        publishEvent(new OrderEvent(name, item));
    }
}
