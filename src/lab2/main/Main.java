
package lab2.main;

import lab2.order.Customer;
import lab2.order.OrderProcessor;


public class Main {
    
    public static int noOrders = 5;    

    public static void main(String[] args){
        
        String[] names = new String[]{"Jan", "Piet", "Joris", "Corneel"};
        
        OrderProcessor orderProcessor = new OrderProcessor();  

        for(String name : names){
            Customer customer = new Customer(name);
            Thread t = new Thread(() -> {
                for(int i=0;i<noOrders;i++){
                    customer.buy("item-"+i);
                }
            });
            t.start();
        }
    }
    
}
