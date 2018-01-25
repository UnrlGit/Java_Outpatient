package BL;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Chaos on 6.6.2017..
 */
public class Appointment {
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime date;

    public Appointment(Doctor doctor, Patient patient, LocalDateTime date) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
    }

    public Appointment() {
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
