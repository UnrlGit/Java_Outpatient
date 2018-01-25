package BL;


import DAL.IRepo;
import DAL.RepositoryFactory;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chaos on 6.6.2017..
 */
public class Hospital {
    private IRepo repo = RepositoryFactory.GetInstance();


    //DOCTOR
    public ArrayList<Doctor> getAllDoctors() throws SQLException {
        return  repo.getAllDoctors();
    }

    public Doctor getDoctor(Integer docID) throws SQLException {
        return repo.getDoctor(docID);
    }

    public void addDoctor(Doctor doctor) throws SQLException {
        repo.addDoctor(doctor);
    }

    public void updateDoctor(Doctor updateDoctor) throws SQLException {
        repo.updateDoctor(updateDoctor);
    }

    public void deleteDoctor(int idDoctor) throws SQLException {repo.deleteDoctor(idDoctor); }

    public boolean isDoctorAvailable(Doctor d, LocalDateTime localDateTime) throws SQLException {
        return repo.isDoctorAvailable(d, localDateTime);
    }//NOT YET 1

    public boolean doctorExists(int doctorPick) throws SQLException {
        return repo.doctorExists(doctorPick);
    }


    //PATIENT
    public void addNewPatient(Patient newPatient) throws SQLException {repo.addPatient(newPatient, true);
    }

    public void addOldPatient(Patient newPatient) throws SQLException {
        repo.addPatient(newPatient, false);
    }

    public boolean patientExists(int id) throws SQLException {
        return repo.patientExists(id);
    }

    public Patient getPatient(int patientID) throws SQLException {
        return repo.getPatient(patientID);
    }

    public ArrayList<Patient> searchPatientByName(String fullName) throws SQLException {
        return repo.getAllPatients(fullName);
    }

    public ArrayList<Patient> getAllPatients() throws SQLException {
        return repo.getAllPatients(null);
    }


    //APPOINTMENTS
    public ArrayList<FullAppointment> getAllAppointmentsByPatient(Patient patient) throws SQLException {
        return repo.getAllAppointmentsByPatient(patient);
    }

    public FullAppointment getFullAppointment(int appointmentID) throws SQLException {
        return repo.getFullAppointment(appointmentID);
    }

    public boolean appointmentExists(int appointmentID) throws SQLException {
        return  repo.appointmentExists(appointmentID);
    }

    public void setCalendarAppointment(Patient p, Doctor d, LocalDateTime localDateTime) throws SQLException {
        repo.setCalendarAppointment(p, d, localDateTime);
    }

    public void addAppointment(FullAppointment fullAppointment) throws SQLException {
        repo.AddAppointment(fullAppointment);

    }


    //OTHER
    public void payBill(int billID, Enums.PAYMENT typeOfPayment) throws SQLException {
        repo.payBill(billID, typeOfPayment);
    }

    public ArrayList<Bill> getBillsByPatientID(int id, boolean paid) throws SQLException {
        return repo.getBillsByPatientID(id, paid);
    }

    public List<Drug> getAllDrugs() throws SQLException {
        return repo.getAllDrugs();
    }

    public List<Test> getAllTests() throws SQLException {
        return repo.getAllTests();
    }

    public boolean billPaid(int appointmentID) throws SQLException{
        return repo.billPaid(appointmentID);
    }
    
    public boolean isNotBookedByPatient(Patient d, LocalDateTime localDateTime) throws SQLException {
        return repo.isNotBookedByPatient(d, localDateTime);
    }
    
    public ArrayList<Report> getDoctorReport(int minusDays) throws SQLException{
        return repo.getDoctorReport(minusDays);
    }
}
