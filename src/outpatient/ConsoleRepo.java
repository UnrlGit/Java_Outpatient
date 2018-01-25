package outpatient;



import BL.*;

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Chaos on 7.6.2017..
 */
public class ConsoleRepo {
    private static Scanner sr = new Scanner(System.in);

    public static Address addressInput(String addressType) {
        Address newAddress = new Address();
        Scanner sr = new Scanner(System.in);
        System.out.println(addressType + " address");
        System.out.println(addressType + " door number:");
        newAddress.setDoorsNumber(Integer.parseInt(sr.nextLine()));
        System.out.println(addressType + " street:");
        newAddress.setStreet(sr.nextLine());
        System.out.println(addressType + " area:");
        newAddress.setArea(sr.nextLine());
        System.out.println(addressType + " city:");
        newAddress.setCity(sr.nextLine());
        System.out.println(addressType + " state:");
        newAddress.setStates(sr.nextLine());
        System.out.println(addressType + " pin code:");
        newAddress.setPincode(Integer.parseInt(sr.nextLine()));
        return newAddress;
    }

    public static Contact phoneInput(){
        Scanner scanner  = new Scanner(System.in);
        Contact newContact = new Contact();
        System.out.println("Telephone work:");
        newContact.setTelephoneHome(scanner.nextLine());

        System.out.println("Telephone home:");
        newContact.setTelephoneWork(scanner.nextLine());

        System.out.println("Mobile:");
        newContact.setMobilePhone(scanner.nextLine());

        System.out.println("Pager:");
        newContact.setPager(scanner.nextLine());

        System.out.println("Fax:");
        newContact.setFax(scanner.nextLine());

        System.out.println("Email:");
        newContact.setEmail(scanner.nextLine());

        return newContact;
    }

    public static Boolean stringEquals(String forChecking, String checkingWith, boolean toLower){
        String temp = forChecking;
        if (toLower){
            temp = forChecking.toLowerCase();
        }
        if(temp.equals(checkingWith)){
            return true;
        }
        return false;
    }

    public static boolean confirm(){
        String confirm;
        System.out.println("Confirm entry? (y/n)");
        confirm = sr.nextLine();
        confirm.toLowerCase();
        if(confirm.equals("y")){
            System.out.println("Confirmed");
            return true;
        }
        else{
            System.out.println("Canceled");
            return false;
        }
    }

    public static void printDoctors(ArrayList<Doctor> doctors) {
        System.out.println("Doctor name/ID");
        for (Doctor d:doctors) {
            System.out.format("%s/%d", d.getFullName(), d.getID());
          System.out.println();
        }


    }

    public static void printPatients(ArrayList<Patient> patients) {
        for (Patient p:patients) {
            System.out.format("%s , ID:%d", p.getFullName(), p.getID());
            System.out.println();
        }

    }

    public static void printBills(ArrayList<Bill> bills) {
        for (Bill b:bills) {
            System.out.println(b.getDate());
            System.out.println(b.getAppointmentID());
        }
    }

    public static void pressEnterToContinue() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press enter to continue");
        scanner.nextLine();
    }

}
