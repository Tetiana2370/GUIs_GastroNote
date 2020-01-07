package sample;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private int orderCount = 0;
    private HashMap<Integer, Order> orders = new HashMap<>();
    private ArrayList<Integer> openOrders = new ArrayList<>();
    private Order currentOrder = null;

    //temporary
    private ArrayList<Product> products = new ArrayList<>();


    User(){
        // to do: replace by reading/initializing from database
        //delete later: exists only for checking how does it work
        products.add(new Product("Kawa czarna", 10));
        products.add(new Product("Kawa z mlekiem", 12));
        products.add(new Product("Espresso", 8));
        products.add( new Product("Lattee", 13));
        products.add(new Product("Herbata czarna", 6));
        products.add(new Product("Cappuccino", 13));

    }

    public Order editOrder(int index){
        if(openOrders.contains(index)){
            return orders.get(index);
        }
        return null;
    }

    public Order addOrder(){
        orderCount++;
        currentOrder = new Order(orderCount);
        orders.put(orderCount, currentOrder);
        openOrders.add(orderCount);

        return currentOrder;

    }

    public int getOrderCount(){
        return this.orderCount;
    }

    public Order getOrder(int index){
        return orders.get(index);
    }

    public boolean closeOrder(int index){
        if(orders.get(index).isPaid()){
            openOrders.remove(index);
            return true;
        }
        return false;
    }



    public ArrayList<Integer> getOpenOrders(){
        return this.openOrders;
    }

    public Order getCurrentOrder(){
        if(currentOrder == null){
            return addOrder();
        }
        return currentOrder;
    }

    public ArrayList<Product> getProducts(){
        return products;
    }

    public void setCurrentOrder(int index){
        if(index < 0 || index > orderCount+1){
            return;
        }

        currentOrder = orders.get(index);
        System.out.println("current order is " + currentOrder.getIndex());
    }

    Order getEmptyOrder(){
        for(Order o: orders.values()){
            if(o.isEmpty()){
                System.out.println("found empty order: " + o.getIndex());
                return o;

            }
        }
        System.out.println("created new order " +( orderCount+1));
        return addOrder();
}
}
