package BL;

/**
 * Created by Chaos on 6.6.2017..
 */
public class Test {
    private String name;
    private int productID;
    private String result;
    private double price;
    private String title;
    
    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
//        return "Test{" +
//                "name='" + name + '\'' +
//                ", result='" + result + '\'' +
//                '}';
        if (title!=null) {
            return title;
        }
        return name;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
