package sample;

import sample.views.enums.Payment;

import java.util.HashMap;

public class Order {

    private double sum = 0;
    private String note = null;
    private double discount = 0;
    private HashMap<Product, Integer> items = new HashMap<>();
    private Payment payment = null;
    private int index;

    Order(int index){
        this.index = index;
    }

    void addOne(Product item){
        if(items.containsKey(item)){
            this.items.put(item, items.get(item)+1);
        }else{
            this.items.put(item, 1);
        }
        this.sum += item.getPrice();
    }

    void addOne(String productName){
        for(Product pr : CurrentUser.getInstance().getProducts()){
            if(pr.getName().equals(productName)){
                addOne(pr);
            }
        }
    }
    void removeOne(String productName){
        for(Product pr : CurrentUser.getInstance().getProducts()){
            if(pr.getName().equals(productName)){
                removeOne(pr);
            }
        }
    }
    void removeOne(Product item){
        if(!items.containsKey(item)){
            return;
        }
        if(items.get(item) > 1){
            this.items.put(item, items.get(item)-1);
        }else{
            this.items.remove(item);
        }
        this.sum -= item.getPrice();
    }

    void clearOrder(){
        // what to do with order index in LoggedUser ?
        sum = 0;
        discount = 0;
        note = null;
        items.clear();

    }

    double getSum(){
        return this.sum - this.sum * discount;
    }

    void setNote(String note){
        this.note = note;
    }

    String getNote(){
        return this.note;
    }

    boolean setDiscount(double discount){
        if(discount < 100 && discount > 0){
            this.discount = discount;
            return true;
        }
        return false;
    }

    double getDiscount(){
        return this.discount;
    }

    boolean isPaid(){
        return payment != null;
    }

    int getIndex(){
        return this.index;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    Payment getPayment(){
        return this.payment;
    }

    HashMap<Product, Integer> getItems(){
        return this.items;
    }

    boolean isEmpty(){
        System.out.println("order is empty: " + items.isEmpty());
        System.out.println(items.keySet());
        return items.isEmpty();
    }

}
