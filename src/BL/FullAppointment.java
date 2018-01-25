package BL;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Created by Chaos on 6.6.2017..
 */
public class FullAppointment extends Appointment {

    private int appointmentID;
    private LocalDateTime timeOfAppointment;
    private ArrayList<Diagnosis> diagnosis = new ArrayList<>();
    private ArrayList<Test> testResults = new ArrayList<>();
    private ArrayList<Drug> drugs = new ArrayList<>();
    private ArrayList<Test> orderedTests = new ArrayList<>();
    private int durationMinutes;

    @Override
    public String toString() {
//        return "FullAppointment{"
//                + "appointmentID=" + appointmentID
//                + ", timeOfAppointment=" + timeOfAppointment
//                + ", diagnosis=" + diagnosis
//                + ", testResults=" + testResults
//                + ", drugs=" + drugs
//                + ", orderedTests=" + orderedTests
//                + ", durationMinutes=" + durationMinutes
//                + '}';
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        return getDate().format(formatter);
    }

    public FullAppointment(Doctor doctor, Patient patient, LocalDateTime date) {
        super(doctor, patient, date);
    }

    public FullAppointment() {

    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public void addTestResult(Test newTest) {
        this.testResults.add(newTest);
    }

    public void orderTest(Test orderTest) {
        this.orderedTests.add(orderTest);
    }

    public void addDrug(Drug newDrug) {
        this.drugs.add(newDrug);
    }

    public LocalDateTime getTimeOfAppointment() {
        return timeOfAppointment;
    }

    public void setTimeOfAppointment(LocalDateTime timeOfAppointment) {
        this.timeOfAppointment = timeOfAppointment;
    }

    public ArrayList<Diagnosis> getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(ArrayList<Diagnosis> diagnosis) {
        this.diagnosis = diagnosis;
    }

    public ArrayList<Test> getTestResults() {
        return testResults;
    }

    public void setTestResults(ArrayList<Test> testResults) {
        this.testResults = testResults;
    }

    public ArrayList<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(ArrayList<Drug> drugs) {
        this.drugs = drugs;
    }

    public ArrayList<Test> getOrderedTests() {
        return orderedTests;
    }

    public void setOrderedTests(ArrayList<Test> orderedTests) {
        this.orderedTests = orderedTests;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void addDiagnosis(String diagnosisTitle, String diagnosisTxt) {
        this.diagnosis.add(new Diagnosis(diagnosisTitle, diagnosisTxt));
    }
}
