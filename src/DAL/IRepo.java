package DAL;

import BL.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by Chaos on 6.6.2017..
 */
public interface IRepo {
    Doctor getDoctor(Integer docID) throws SQLException;

    
    //DOCTOR
    ArrayList<Doctor> getAllDoctors() throws SQLException;

    void addDoctor(Doctor doctor) throws SQLException;
    
    void updateDoctor(Doctor updateDoctor) throws SQLException;

    void deleteDoctor(int idDoctor) throws SQLException;

    boolean isDoctorAvailable(Doctor d, LocalDateTime localDateTime) throws SQLException;

    boolean doctorExists(int doctorPick) throws SQLException;
    
    //PATIENT
    void addPatient(Patient newPatient, boolean isNewPatient) throws SQLException;

    boolean patientExists(int id) throws SQLException;

    Patient getPatient(int patientID) throws SQLException;

    ArrayList<Patient> getAllPatients(String nameFilter) throws SQLException;
    
    //APPOINTMENTS
    ArrayList<FullAppointment> getAllAppointmentsByPatient(Patient patient) throws SQLException;

    FullAppointment getFullAppointment(int appointmentID) throws SQLException;

    boolean appointmentExists(int appointmentID) throws SQLException;

    void setCalendarAppointment(Patient p, Doctor d, LocalDateTime localDateTime) throws SQLException;

    void AddAppointment(FullAppointment fullAppointment) throws SQLException;
    
    //OTHER
    void payBill(int billID, Enums.PAYMENT typeOfPayment) throws SQLException;
    
    ArrayList<Bill> getBillsByPatientID(int patientID, boolean paid) throws SQLException;

    ArrayList<Drug> getAllDrugs() throws SQLException;

    ArrayList<Test> getAllTests() throws SQLException;

    boolean billPaid(int appointmentID) throws SQLException;
    
    boolean isNotBookedByPatient(Patient d, LocalDateTime localDateTime)throws SQLException;
    
    ArrayList<Report> getDoctorReport(int minusDays) throws SQLException;
}
