package BL;

/**
 * Created by Chaos on 21.6.2017..
 */
public class BillItem {
    private String itemName;
    private double price;
    private double amount;

    public BillItem(String itemName, double price, double amount) {
        this.itemName = itemName;
        this.price = price;
        this.amount = amount;
    }

    public BillItem() {
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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
