package sample;

public class Product {
    private String name;
    private double price;

    Product(String name, double price){
        this.name = name;
        this.price = price;
    }

    double getPrice(){
        return this.price;
    }
    boolean setPrice(double price){
        if(price < 0)
            return false;
        this.price = price;
        return true;
    }

    String getName(){
        return  this.name;
    }

    boolean setName(String name){
        // to do: check if there is such name in base

        if(name == null)
            return false;
        this.name = name;
        return true;
    }

}
