
package lab2.main;

import lab2.order.Customer;
import lab2.order.OrderProcessor;


public class Main {
    
    public static int noOrders = 500;

    public static void main(String[] args){
        
        String[] names = new String[]{"Jan", "Piet", "Joris", "Corneel"};
        
        OrderProcessor orderProcessor = new OrderProcessor();

        Thread[] threads = new Thread[names.length];
        int threadIdx = 0;

        for(String name : names){
            Customer customer = new Customer(name);
            Thread t = new Thread(() -> {
                for(int i=0;i<noOrders;i++){
                    customer.buy("item-"+i);

                }
            });
            //t.setName(name); // controle
            threads[threadIdx++] = t;
            t.start();
        }

        // Wait for each thread
        for(Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //All threads have done their work from now on.
        System.out.println(OrderProcessor.getNumberOfOrders());
    }
    
}
