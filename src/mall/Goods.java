package mall;

import java.io.Serializable;

public class Goods implements Serializable {
    private int id;
    private String name;
    private double price;
    private int storageSize;

    public Goods() {
    }

    public Goods(int id, String name, double price, int storageSize) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.storageSize = storageSize;
    }


    public int getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(int storageSize) {
        this.storageSize = storageSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void display() {
        System.out.println("id:" + id + " name:" + name + " price:" + price + " storageSize:" + storageSize);
    }
}
