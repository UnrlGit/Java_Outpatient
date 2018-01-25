package BL;

/**
 * Created by Chaos on 5.6.2017..
 */
public class Doctor extends Person {
    public Doctor() {
    }

    public Doctor(Integer id, String fullName) {
        super(id, fullName);
    }

    @Override
    public String toString() {
        return String.format("%s (ID:%d)", getFullName(), getID());
    }
    
    


}
