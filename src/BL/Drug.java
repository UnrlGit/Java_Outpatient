package BL;

/**
 * Created by Chaos on 6.6.2017..
 */
public class Drug {
    private String name;
    private int productID;
    private double price;
    private double amount;



    @Override
    public String toString() {
//        return "Drug{" +
//                "name='" + name + '\'' +
//                ", amount=" + amount +
//                '}';
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return productID;
    }

    public void setID(int productID) {
        this.productID = productID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
