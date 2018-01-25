package BL;

/**
 * Created by Chaos on 5.6.2017..
 */
public class Patient extends Person {
    private Record record;
    private Doctor doctor;

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    @Override
    public String toString() {
        //return super.toString() + "Patient{" +
        //        "record=" + record + "\n" +
         //       ", doctor=" + doctor + "\n" +
         //       '}';
         return String.format("%s (ID:%d)", getFullName(), getID());
    }

    public Patient(Record record, Doctor doctor) {
        this.record = record;
        this.doctor = doctor;
    }

    public Patient(Integer id, String fullName, Record record, Doctor doctor) {
        super(id, fullName);
        this.record = record;
        this.doctor = doctor;
    }

    public Patient() {
    }


    
}