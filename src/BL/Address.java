package BL;

/**
 * Created by Chaos on 6.6.2017..
 */
public class Address {
    private int doorsNumber;
    private String street;
    private String area;
    private String city;
    private String states;
    private int pincode;

    @Override
    public String toString() {
        return "Address{" +
                "doorsNumber=" + doorsNumber + "\n" +
                ", street='" + street + '\'' +"\n" +
                ", area='" + area + '\'' +"\n" +
                ", city='" + city + '\'' +"\n" +
                ", states='" + states + '\'' +"\n" +
                ", pin code=" + pincode +
                '}';
    }

    public int getDoorsNumber() {
        return doorsNumber;
    }

    public void setDoorsNumber(int doorsNumber) {
        this.doorsNumber = doorsNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public Address(int doorsNumber, String street, String area, String city, String states, int pincode) {
        this.doorsNumber = doorsNumber;
        this.street = street;
        this.area = area;
        this.city = city;
        this.states = states;
        this.pincode = pincode;
    }

    public Address() {
    }
}
