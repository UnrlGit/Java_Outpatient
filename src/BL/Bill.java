package BL;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Created by Chaos on 6.6.2017..
 */
public class Bill {
    private LocalDateTime date;
    private int appointmentID;
    private ArrayList<BillItem> items = new ArrayList<>();
    private int doctorID;

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        String dejt = getDate().format(formatter);
        return "Date: " + dejt + ", appointment ID:" + appointmentID + ", Doctor ID=" + doctorID;
    }

   
    
    

    

    public double getTotalPrice(){
        double total = 0d;
        for (BillItem item:items) {
            total+= (item.getPrice()* item.getAmount());
        }
        return total;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public Bill(LocalDateTime date, int appointmentID, ArrayList<BillItem> items, int doctorID) {
        this.date = date;
        this.appointmentID = appointmentID;
        this.items = items;
        this.doctorID = doctorID;

    }

    public Bill() {
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public ArrayList<BillItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<BillItem> items) {
        this.items = items;
    }

    public void addBillItem(BillItem billItem){
        this.items.add(billItem);
    }
}
