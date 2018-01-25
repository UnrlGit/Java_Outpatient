package BL;



/**
 * Created by Chaos on 5.6.2017..
 */
public class Person {
    private  Integer ID;
    private String fullName;

    @Override
    public String toString() {
        return "Person{{" +
                "ID=" + ID +
                "," + "\n"+ "fullName='" + fullName +
                '}'+ '\'' + "\n";

    }



    public  int getID() {
        return ID;
    }

    public  void setID(int ID) {
        this.ID = ID;
    }

    public  String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Person() {

    }

    public Person(Integer id, String fullName){
        this.ID = id;
        this.fullName = fullName;
    }


}
