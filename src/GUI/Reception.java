package GUI;

import BL.Hospital;
import BL.Patient;
import GUI.InterfacesPlus.PatientListener;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Reception extends JFrame{
        Hospital hospitalManager;
        private  JTabbedPane tabPane;
        private PatientForm patientForm;
        private DoctorManagementJPanel doctorManagementJPanel;
        private PaymentPanel paymentPanel;
        private AppointmentsPanel appointmentsPanel;
        private ReportsPanel reportsPanel;

    public Reception() {
        super("Reception Module");
            try {
                hospitalManager = new Hospital();
                
                //LAYOUT
                setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
                setPreferredSize(new Dimension(500,500));
                setMinimumSize(new Dimension(300,400));
                setMaximumSize(new Dimension(1100, 800));
                setSize(900,700);
                //TABS
                tabPane = new JTabbedPane();
                tabPane.setTabPlacement(JTabbedPane.LEFT);
                //PATIENT FORM
                patientForm = new PatientForm();
                patientForm.setDoctors(hospitalManager.getAllDoctors());
                patientForm.setPatientListner(new PatientListener(){
                    @Override
                    public void patientEmmited(Patient patient) {
                        if (patient != null) {
                            if (patient.getID() == 0) {
                            try {
                                hospitalManager.addNewPatient(patient);
                            } catch (SQLException ex) {
                                //Logger.getLogger(Reception.class.getName()).log(Level.SEVERE, null, ex);
                                new DBErrorDialog();
                            }
                        }
                        else{
                            try {
                                hospitalManager.addOldPatient(patient);
                            } catch (SQLException ex) {
                                //Logger.getLogger(Reception.class.getName()).log(Level.SEVERE, null, ex);
                                new DBErrorDialog();
                            }
                        }
                        }
                    }
                });
                //DOCTOR MANAGEMENT
                doctorManagementJPanel = new DoctorManagementJPanel(hospitalManager);
                //PAYMENT
                paymentPanel = new PaymentPanel(hospitalManager);
                //APPOINTMENT
                appointmentsPanel = new AppointmentsPanel(hospitalManager);
                //REPORTS
                reportsPanel = new ReportsPanel(hospitalManager);
                
                
                
                //ADDING TABS+FINALIZE
                add(tabPane);
                tabPane.addTab("Patient form", patientForm);
                tabPane.addTab("Doctor management", doctorManagementJPanel);
                tabPane.addTab("Payment", paymentPanel);
                tabPane.addTab("Appointment", appointmentsPanel);
                tabPane.addTab("Reports", reportsPanel);
                setVisible(true);
                
            } catch (SQLException ex) {
                //Logger.getLogger(Reception.class.getName()).log(Level.SEVERE, null, ex);
                new DBErrorDialog();
            }

    }
}
