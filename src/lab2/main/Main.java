
package lab2.main;

import lab2.eventbroker.EventBroker;
import lab2.order.BlacklistOrderProcessor;
import lab2.order.Customer;
import lab2.order.OrderProcessor;

import java.awt.*;
import java.util.concurrent.TimeUnit;


public class Main {
    
    public static int noOrders = 5;

    public static void main(String[] args){

        String[] names = new String[]{"Jan", "Piet", "Joris", "Corneel"};

        OrderProcessor orderProcessor = OrderProcessor.createInstance();
        BlacklistOrderProcessor blorderProcessor = BlacklistOrderProcessor.createInstance();

        // De main instructies zien er anders uit aan de hand van de opgave.
        //opgave1en2(names); // LET OP: Deze code zal de events niet meer de events verwerken, omdat de eventbroker.addEvent() is aangepast in opgave 3.
        long start = System.currentTimeMillis();
        opgave3(names);
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    private static void opgave3(String[] names) {
        EventBroker.getEventBroker().start();
        for(String name : names){
            Customer customer = new Customer(name);
            for(int i=0;i<noOrders;i++){
                customer.buy("item-"+i);

                //opgave 5: even wachten.
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
        System.out.println("------------ All orders have been sent. ------------");
        EventBroker.getEventBroker().stop();

        // opgave 6
        OrderProcessor.threadpool.shutdown();
        try {
            OrderProcessor.threadpool.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //
        System.out.println("Orders processed: "+OrderProcessor.getNumberOfOrders());

    }

    private static void opgave1en2(String[] names) {
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
