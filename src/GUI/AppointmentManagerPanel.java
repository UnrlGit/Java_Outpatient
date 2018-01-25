package GUI;

import BL.Diagnosis;
import BL.Doctor;
import BL.Drug;
import BL.FullAppointment;
import BL.Hospital;
import BL.Patient;
import BL.Test;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AppointmentManagerPanel extends javax.swing.JPanel {

    private Hospital hospitalManager;
    private Doctor myDoctor;
    private Patient currentPatient;
    private DefaultListModel listModelPatients;
    private DefaultListModel listModelDiagnosisHistory;
    private DefaultListModel listModelTestsHistory;
    
    private FullAppointment currentHistoryFullapt;
    private ArrayList<FullAppointment> allAppointments;
    private DefaultListModel modelHistoryApps;
    private boolean appointmentSet;
    
    
    //APPOINTMENT STUFF
    private DefaultListModel listModelDrugs;
    private DefaultListModel listModelTests;
    
    private DefaultListModel listModelRemoveDrugs;
    private DefaultListModel listModelRemoveTests;

    private DefaultListModel listModelDiagnosisAdd;
    private DefaultListModel listModelTestResultAdd;
    
    public AppointmentManagerPanel(){
    myDoctor = new Doctor();
        currentPatient = new Patient();

        initComponents();
        this.setVisible(true);//PROMIJENITI ZA VISIBLE
        
        recordsTextPane.setEditable(false);
        
        listModelPatients = new DefaultListModel();
        modelHistoryApps = new DefaultListModel();
        listModelDiagnosisHistory = new DefaultListModel();
        listModelTestsHistory = new DefaultListModel();
        
        historyDiagnosisTextArea.setLineWrap(true);
        historyDiagnosisTextArea.setWrapStyleWord(true);
        historyTestResultTextArea.setLineWrap(true);
        historyTestResultTextArea.setWrapStyleWord(true);
        
        //APT STUFF
         listModelRemoveDrugs= new DefaultListModel();
         listModelRemoveTests = new DefaultListModel();
         
         listModelDiagnosisAdd = new DefaultListModel();
         listModelTestResultAdd = new DefaultListModel();
    }
    public AppointmentManagerPanel(Hospital hospital) {
        hospitalManager = hospital;
        myDoctor = new Doctor();
        currentPatient = new Patient();

        initComponents();
        this.setVisible(true);//PROMIJENITI ZA VISIBLE
        
        recordsTextPane.setEditable(false);
        
        listModelPatients = new DefaultListModel();
        modelHistoryApps = new DefaultListModel();
        listModelDiagnosisHistory = new DefaultListModel();
        listModelTestsHistory = new DefaultListModel();
        
        historyDiagnosisTextArea.setLineWrap(true);
        historyDiagnosisTextArea.setWrapStyleWord(true);
        historyTestResultTextArea.setLineWrap(true);
        historyTestResultTextArea.setWrapStyleWord(true);
        
        
        
    }
    
    
    //METHODS
    public void setManager(Hospital manager){
        this.hospitalManager = manager;
    }
    
    
    public void setAppointment(Doctor d, Patient p){
        try {
            this.myDoctor =   hospitalManager.getDoctor(d.getID());
            this.currentPatient =  hospitalManager.getPatient(p.getID());
            setPatient();
            loadData();
        } catch (Exception ex) {
            //Logger.getLogger(AppointmentManagerPanel.class.getName()).log(Level.SEVERE, null, ex);
            new DBErrorDialog();
        }
    }
    
    private void setPatient() {
        try {
           
            recordsTextPane.setText(currentPatient.getRecord().toString());
            currentPatientLbl.setText("Current patient: " + currentPatient.getFullName());
            //fullAppointment = new FullAppointment(myDoctor, currentPatient, LocalDateTime.now());
            allAppointments = hospitalManager.getAllAppointmentsByPatient(currentPatient);
            

            modelHistoryApps.clear();        

            for (FullAppointment apt : allAppointments) {
                modelHistoryApps.addElement(apt);                       
                }
            historyAppointmentsList.setModel(modelHistoryApps);
            historyAppointmentsList.setSelectedIndex(0);
            
            
        } catch (SQLException ex) {
            //Logger.getLogger(DoctorModule.class.getName()).log(Level.SEVERE, null, ex);
            new DBErrorDialog();
        }
        clearAppointmentData();
    }

    private void loadHistoryData() {
        try {
            if (historyAppointmentsList.isSelectionEmpty()) {
                return;
            }
            
            //LOADING DATA FROM ID FOR APT
            currentHistoryFullapt = (FullAppointment)modelHistoryApps.getElementAt
                                        (historyAppointmentsList.getSelectedIndex());
            currentHistoryFullapt = hospitalManager.getFullAppointment(currentHistoryFullapt.getAppointmentID());
            
            Doctor dr = hospitalManager.getDoctor(currentHistoryFullapt.getDoctor().getID());
            historyDoctorLbl.setText(dr.toString());
            historyIDLbl.setText("Appointment ID: " +String.valueOf(currentHistoryFullapt.getAppointmentID()));
            
            
           
            
            
            //SETTING TABLES
            DefaultTableModel model = (DefaultTableModel)historyMedicineTable.getModel();
            model.setRowCount(0);
            for (Drug item : currentHistoryFullapt.getDrugs()) {
                
                model.addRow(new Object[]{item.getName(), item.getAmount(), item.getPrice()});
            }
            historyMedicineTable.setModel(model);
            
            DefaultTableModel model2 = (DefaultTableModel)historyOrderedTestsTable.getModel();
            model2.setRowCount(0);
            for (Test item : currentHistoryFullapt.getOrderedTests()) {
                
                model2.addRow(new Object[]{item.getName(),item.getPrice()});
            }
            historyOrderedTestsTable.setModel(model2);
            
            //SETTING LISTS
            listModelDiagnosisHistory.clear();
            for (Diagnosis d : currentHistoryFullapt.getDiagnosis()) {
            listModelDiagnosisHistory.addElement(d);                       
            }
            historyDiagnosisList.setModel(listModelDiagnosisHistory);
            historyDiagnosisList.setSelectedIndex(0);
            
            listModelTestsHistory.clear();
            for (Test t : currentHistoryFullapt.getTestResults()) {
            listModelTestsHistory.addElement(t);                       
            }
            historyTestResultsList.setModel(listModelTestsHistory);
            historyTestResultsList.setSelectedIndex(0);
            
            
        } catch (SQLException ex) {
            //Logger.getLogger(DoctorModule.class.getName()).log(Level.SEVERE, null, ex);
            new DBErrorDialog();
        }
        
    }

    public void clearAll() {
            listModelDiagnosisHistory.clear();
            listModelTestsHistory.clear();
            historyIDLbl.setText("");
            historyDoctorLbl.setText("");
            recordsTextPane.setText("");
            DefaultTableModel model = (DefaultTableModel) historyMedicineTable.getModel();
            model.setRowCount(0);
            model = (DefaultTableModel) historyOrderedTestsTable.getModel();
            model.setRowCount(0);
    }
    
     private void loadData() {
        try {
            ArrayList<Drug> drugs = (ArrayList<Drug>) hospitalManager.getAllDrugs();
            ArrayList<Test> tests = (ArrayList<Test>) hospitalManager.getAllTests();
            
            listModelDrugs = new DefaultListModel();
            listModelDrugs.clear();
            for (Drug d : drugs) {
            listModelDrugs.addElement(d);                       
            }
            medicineAddJList.setModel(listModelDrugs);
            medicineAddJList.setSelectedIndex(0);
            
            listModelTests = new DefaultListModel();
            listModelTests.clear();
            for (Test t : tests) {
            listModelTests.addElement(t);                       
            }
            addOrderTestsJList.setModel(listModelTests);
            addOrderTestsJList.setSelectedIndex(0);
            
            
        } catch (SQLException ex) {
            //Logger.getLogger(AppointmentManagerPanel.class.getName()).log(Level.SEVERE, null, ex);
            new DBErrorDialog();
        }
    }
     public void clearAppointmentData() {        
        //APT STUFF
         listModelRemoveDrugs.clear();
         medicineRemoveJList.setModel(listModelRemoveDrugs);
         listModelRemoveTests.clear();
         removeOrderTestsJList.setModel(listModelRemoveTests);
         
         listModelDiagnosisAdd.clear();
         removeDiagnosisJList.setModel(listModelDiagnosisAdd);
         listModelTestResultAdd.clear();
         removeResultJList.setModel(listModelTestResultAdd);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        currentPatientLbl = new javax.swing.JLabel();
        recordsPane = new javax.swing.JTabbedPane();
        recordsPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        recordsTextPane = new javax.swing.JTextPane();
        historyPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        historyAppointmentsList = new javax.swing.JList<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        historyDiagnosisList = new javax.swing.JList<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        historyTestResultsList = new javax.swing.JList<>();
        jScrollPane7 = new javax.swing.JScrollPane();
        historyDiagnosisTextArea = new javax.swing.JTextArea();
        jScrollPane8 = new javax.swing.JScrollPane();
        historyTestResultTextArea = new javax.swing.JTextArea();
        jScrollPane9 = new javax.swing.JScrollPane();
        historyOrderedTestsTable = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        historyMedicineTable = new javax.swing.JTable();
        historyIDLbl = new javax.swing.JLabel();
        historyDoctorLbl = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        appointmentPanel = new javax.swing.JPanel();
        medicinePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        medicineAddJList = new javax.swing.JList<>();
        medicineAmountJSpinner = new javax.swing.JSpinner();
        addMedicineBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        medicineRemoveJList = new javax.swing.JList<>();
        removeMedicineJBtn = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        testsPanel = new javax.swing.JPanel();
        addOrderTestBtn = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        removeOrderTestsJList = new javax.swing.JList<>();
        removeOrderTestBtn = new javax.swing.JButton();
        jScrollPane15 = new javax.swing.JScrollPane();
        addOrderTestsJList = new javax.swing.JList<>();
        jLabel11 = new javax.swing.JLabel();
        diagnosisPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        titleJTextField = new javax.swing.JTextField();
        jScrollPane13 = new javax.swing.JScrollPane();
        textTextArea = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        removeDiagnosisJList = new javax.swing.JList<>();
        jScrollPane16 = new javax.swing.JScrollPane();
        removeResultJList = new javax.swing.JList<>();
        jLabel8 = new javax.swing.JLabel();
        removeDiagnosisBtn = new javax.swing.JButton();
        removeResultBtn = new javax.swing.JButton();
        addAsDiagnBtn = new javax.swing.JButton();
        addAsResultBtn = new javax.swing.JButton();
        confirmPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        durationSpinner = new javax.swing.JSpinner();
        saveAptBtn = new javax.swing.JButton();
        cancelAllBtn = new javax.swing.JButton();

        currentPatientLbl.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        currentPatientLbl.setText("Current patient: ");

        jScrollPane4.setViewportView(recordsTextPane);

        javax.swing.GroupLayout recordsPanelLayout = new javax.swing.GroupLayout(recordsPanel);
        recordsPanel.setLayout(recordsPanelLayout);
        recordsPanelLayout.setHorizontalGroup(
            recordsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
        );
        recordsPanelLayout.setVerticalGroup(
            recordsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recordsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE))
        );

        recordsPane.addTab("Patient Records", recordsPanel);

        historyAppointmentsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        historyAppointmentsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                historyAppointmentsListValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(historyAppointmentsList);

        historyDiagnosisList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                historyDiagnosisListValueChanged(evt);
            }
        });
        jScrollPane5.setViewportView(historyDiagnosisList);

        historyTestResultsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                historyTestResultsListValueChanged(evt);
            }
        });
        jScrollPane6.setViewportView(historyTestResultsList);

        historyDiagnosisTextArea.setColumns(20);
        historyDiagnosisTextArea.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
        historyDiagnosisTextArea.setRows(5);
        historyDiagnosisTextArea.setWrapStyleWord(true);
        jScrollPane7.setViewportView(historyDiagnosisTextArea);

        historyTestResultTextArea.setColumns(20);
        historyTestResultTextArea.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
        historyTestResultTextArea.setRows(5);
        jScrollPane8.setViewportView(historyTestResultTextArea);

        historyOrderedTestsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Name", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane9.setViewportView(historyOrderedTestsTable);

        historyMedicineTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Name", "Amount", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane10.setViewportView(historyMedicineTable);

        jLabel1.setText("Medicine");

        jLabel2.setText("Diagnosis");

        jLabel3.setText("Test Results");

        jLabel4.setText("Ordered Tests");

        jLabel12.setText("Doctor:");

        javax.swing.GroupLayout historyPanelLayout = new javax.swing.GroupLayout(historyPanel);
        historyPanel.setLayout(historyPanelLayout);
        historyPanelLayout.setHorizontalGroup(
            historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(historyPanelLayout.createSequentialGroup()
                .addGroup(historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(historyPanelLayout.createSequentialGroup()
                        .addGroup(historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(historyPanelLayout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(historyPanelLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                    .addGroup(historyPanelLayout.createSequentialGroup()
                                        .addGap(44, 44, 44)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE))))
                            .addGroup(historyPanelLayout.createSequentialGroup()
                                .addGroup(historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(historyIDLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(historyDoctorLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(166, 166, 166))
                            .addGroup(historyPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(historyPanelLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, historyPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, historyPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(53, 53, 53))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, historyPanelLayout.createSequentialGroup()
                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(1, 1, 1))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, historyPanelLayout.createSequentialGroup()
                                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2))))))
                    .addGroup(historyPanelLayout.createSequentialGroup()
                        .addGroup(historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(historyPanelLayout.createSequentialGroup()
                                .addGap(202, 202, 202)
                                .addComponent(jLabel1))
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                    .addComponent(jScrollPane7)))
        );
        historyPanelLayout.setVerticalGroup(
            historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(historyPanelLayout.createSequentialGroup()
                .addGroup(historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(historyPanelLayout.createSequentialGroup()
                        .addGroup(historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(historyPanelLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(historyPanelLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane8)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, historyPanelLayout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(historyDoctorLbl)
                                .addGap(57, 57, 57)
                                .addGroup(historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, historyPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, historyPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(historyPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(historyIDLbl)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        recordsPane.addTab("History of appointments", historyPanel);

        medicinePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Medicine"));

        medicineAddJList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(medicineAddJList);

        medicineAmountJSpinner.setModel(new javax.swing.SpinnerNumberModel(1.0d, 1.0d, 100.0d, 1.0d));

        addMedicineBtn.setText("Add");
        addMedicineBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMedicineBtnActionPerformed(evt);
            }
        });

        medicineRemoveJList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(medicineRemoveJList);

        removeMedicineJBtn.setText("Remove selected");
        removeMedicineJBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeMedicineJBtnActionPerformed(evt);
            }
        });

        jLabel10.setText("Added");

        javax.swing.GroupLayout medicinePanelLayout = new javax.swing.GroupLayout(medicinePanel);
        medicinePanel.setLayout(medicinePanelLayout);
        medicinePanelLayout.setHorizontalGroup(
            medicinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(medicinePanelLayout.createSequentialGroup()
                .addGroup(medicinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(medicinePanelLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(medicineAmountJSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addMedicineBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addGroup(medicinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(removeMedicineJBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, medicinePanelLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(53, 53, 53))))
        );
        medicinePanelLayout.setVerticalGroup(
            medicinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(medicinePanelLayout.createSequentialGroup()
                .addGroup(medicinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(medicinePanelLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(medicinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(medicineAmountJSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addMedicineBtn)
                    .addComponent(removeMedicineJBtn)))
        );

        testsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Order Tests"));
        testsPanel.setToolTipText("");

        addOrderTestBtn.setText("Add");
        addOrderTestBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addOrderTestBtnActionPerformed(evt);
            }
        });

        removeOrderTestsJList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane11.setViewportView(removeOrderTestsJList);

        removeOrderTestBtn.setText("Remove selected");
        removeOrderTestBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeOrderTestBtnActionPerformed(evt);
            }
        });

        addOrderTestsJList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane15.setViewportView(addOrderTestsJList);

        jLabel11.setText("Added");

        javax.swing.GroupLayout testsPanelLayout = new javax.swing.GroupLayout(testsPanel);
        testsPanel.setLayout(testsPanelLayout);
        testsPanelLayout.setHorizontalGroup(
            testsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(testsPanelLayout.createSequentialGroup()
                .addGroup(testsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(addOrderTestBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addGroup(testsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, testsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(56, 56, 56))
                    .addComponent(removeOrderTestBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)))
        );
        testsPanelLayout.setVerticalGroup(
            testsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(testsPanelLayout.createSequentialGroup()
                .addGroup(testsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(testsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane11)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(testsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addOrderTestBtn)
                    .addComponent(removeOrderTestBtn)))
        );

        diagnosisPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Diagnosis/Test Results"));

        jLabel5.setText("Title:");

        jLabel6.setText("Text:");

        textTextArea.setColumns(20);
        textTextArea.setRows(5);
        jScrollPane13.setViewportView(textTextArea);

        jLabel7.setText("Diagnosis");

        removeDiagnosisJList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane14.setViewportView(removeDiagnosisJList);

        removeResultJList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane16.setViewportView(removeResultJList);

        jLabel8.setText("Test Results");

        removeDiagnosisBtn.setText("Remove selected diagnosis");
        removeDiagnosisBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeDiagnosisBtnActionPerformed(evt);
            }
        });

        removeResultBtn.setText("Remove selected result");
        removeResultBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeResultBtnActionPerformed(evt);
            }
        });

        addAsDiagnBtn.setText("Add as diagnosis");
        addAsDiagnBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAsDiagnBtnActionPerformed(evt);
            }
        });

        addAsResultBtn.setText("Add as result");
        addAsResultBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAsResultBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout diagnosisPanelLayout = new javax.swing.GroupLayout(diagnosisPanel);
        diagnosisPanel.setLayout(diagnosisPanelLayout);
        diagnosisPanelLayout.setHorizontalGroup(
            diagnosisPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(diagnosisPanelLayout.createSequentialGroup()
                .addGroup(diagnosisPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(diagnosisPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(titleJTextField))
                .addGroup(diagnosisPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(diagnosisPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(diagnosisPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(addAsResultBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                            .addComponent(addAsDiagnBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                        .addGap(17, 17, 17)
                        .addGroup(diagnosisPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(diagnosisPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(diagnosisPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(removeDiagnosisBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(removeResultBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(94, 94, 94))
                            .addGroup(diagnosisPanelLayout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addComponent(jLabel7)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, diagnosisPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addGap(149, 149, 149))))
        );
        diagnosisPanelLayout.setVerticalGroup(
            diagnosisPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(diagnosisPanelLayout.createSequentialGroup()
                .addGroup(diagnosisPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(titleJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(addAsDiagnBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(diagnosisPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(diagnosisPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane13)
                        .addGap(2, 2, 2))
                    .addGroup(diagnosisPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(diagnosisPanelLayout.createSequentialGroup()
                        .addGroup(diagnosisPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                            .addComponent(addAsResultBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addComponent(removeDiagnosisBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addGap(1, 1, 1)
                        .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeResultBtn))))
        );

        confirmPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Confirm"));

        jLabel9.setText("Duration (min):");

        durationSpinner.setModel(new javax.swing.SpinnerNumberModel(30, 0, 600, 1));

        saveAptBtn.setText("Save All");
        saveAptBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAptBtnActionPerformed(evt);
            }
        });

        cancelAllBtn.setText("Cancel All");
        cancelAllBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelAllBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout confirmPanelLayout = new javax.swing.GroupLayout(confirmPanel);
        confirmPanel.setLayout(confirmPanelLayout);
        confirmPanelLayout.setHorizontalGroup(
            confirmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(confirmPanelLayout.createSequentialGroup()
                .addComponent(jLabel9)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(durationSpinner)
            .addComponent(saveAptBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cancelAllBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
        );
        confirmPanelLayout.setVerticalGroup(
            confirmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(confirmPanelLayout.createSequentialGroup()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(durationSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saveAptBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelAllBtn)
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout appointmentPanelLayout = new javax.swing.GroupLayout(appointmentPanel);
        appointmentPanel.setLayout(appointmentPanelLayout);
        appointmentPanelLayout.setHorizontalGroup(
            appointmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(appointmentPanelLayout.createSequentialGroup()
                .addComponent(medicinePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(testsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(appointmentPanelLayout.createSequentialGroup()
                .addComponent(diagnosisPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(confirmPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        appointmentPanelLayout.setVerticalGroup(
            appointmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(appointmentPanelLayout.createSequentialGroup()
                .addGroup(appointmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(testsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(medicinePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(appointmentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(diagnosisPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(confirmPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        recordsPane.addTab("Appointment", appointmentPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(currentPatientLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(recordsPane, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(currentPatientLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(recordsPane)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void historyAppointmentsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_historyAppointmentsListValueChanged
        loadHistoryData();
    }//GEN-LAST:event_historyAppointmentsListValueChanged

    private void historyDiagnosisListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_historyDiagnosisListValueChanged
        if (historyDiagnosisList.isSelectionEmpty()) {
            historyDiagnosisTextArea.setText("");
            return;
        }
        Diagnosis d = (Diagnosis)listModelDiagnosisHistory.getElementAt(historyDiagnosisList.getSelectedIndex());
        historyDiagnosisTextArea.setText(d.getText());
    }//GEN-LAST:event_historyDiagnosisListValueChanged

    private void historyTestResultsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_historyTestResultsListValueChanged
        if (historyTestResultsList.isSelectionEmpty()) {
            historyTestResultTextArea.setText("");
            return;
        }
        Test t = (Test)listModelTestsHistory.getElementAt(historyTestResultsList.getSelectedIndex());
        historyTestResultTextArea.setText(t.getResult());
    }//GEN-LAST:event_historyTestResultsListValueChanged

    private void saveAptBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAptBtnActionPerformed
        int reply = JOptionPane.showConfirmDialog(null, "Confirm appointment save?", "Save Appointment", JOptionPane.YES_NO_OPTION);
        if(reply == JOptionPane.YES_OPTION){
            FullAppointment fullAppointment = new FullAppointment(myDoctor, currentPatient, LocalDateTime.now());
            fullAppointment.setDurationMinutes((int) durationSpinner.getValue());
            ArrayList<Diagnosis> diagnosises = new ArrayList<>();
            ArrayList<Test> testResults = new ArrayList<>();
            ArrayList<Drug> drugs = new ArrayList<>();
            ArrayList<Test> orderedTests = new ArrayList<>();  

            //LOADING LISTS
            for (Object diagnosis : listModelDiagnosisAdd.toArray()) {
                diagnosises.add((Diagnosis) diagnosis);
            }
            for (Object test : listModelTestResultAdd.toArray()) {
                testResults.add((Test) test);
            }
            for (Object drug : listModelRemoveDrugs.toArray()) {
                drugs.add((Drug) drug);
            }
            for (Object oTest : listModelRemoveTests.toArray()) {
                orderedTests.add((Test)oTest);
            }
            fullAppointment.setDiagnosis(diagnosises);
            fullAppointment.setDrugs(drugs);
            fullAppointment.setOrderedTests(orderedTests);
            fullAppointment.setTestResults(testResults);

            try {
                hospitalManager.addAppointment(fullAppointment);
            } catch (SQLException ex) {
                //Logger.getLogger(AppointmentManagerPanel.class.getName()).log(Level.SEVERE, null, ex);
                new DBErrorDialog("Appointment Save Failed, problem with database");
            }
        }
        
    }//GEN-LAST:event_saveAptBtnActionPerformed

    private void cancelAllBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelAllBtnActionPerformed
       clearAppointmentData();
    }//GEN-LAST:event_cancelAllBtnActionPerformed

    private void addMedicineBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMedicineBtnActionPerformed
    if(!medicineAddJList.isSelectionEmpty()){
        Drug d = (Drug)listModelDrugs.getElementAt(medicineAddJList.getSelectedIndex());
        d.setAmount((double) medicineAmountJSpinner.getValue());
           
        listModelRemoveDrugs.addElement(d);          
        medicineRemoveJList.setModel(listModelRemoveDrugs);
        medicineRemoveJList.setSelectedIndex(0);
        }
    }//GEN-LAST:event_addMedicineBtnActionPerformed

    private void removeMedicineJBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeMedicineJBtnActionPerformed
        if(!medicineRemoveJList.isSelectionEmpty()){
            listModelRemoveDrugs.remove(medicineRemoveJList.getSelectedIndex());
            medicineRemoveJList.setSelectedIndex(0);
        }
    }//GEN-LAST:event_removeMedicineJBtnActionPerformed

    private void addOrderTestBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addOrderTestBtnActionPerformed
        if (!addOrderTestsJList.isSelectionEmpty()) {
            Test t = (Test)listModelTests.getElementAt(addOrderTestsJList.getSelectedIndex());
            
            listModelRemoveTests.addElement(t);
            removeOrderTestsJList.setModel(listModelRemoveTests);
            removeOrderTestsJList.setSelectedIndex(0);
        }
    }//GEN-LAST:event_addOrderTestBtnActionPerformed

    private void removeOrderTestBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeOrderTestBtnActionPerformed
         if(!removeOrderTestsJList.isSelectionEmpty()){
            listModelRemoveTests.remove(removeOrderTestsJList.getSelectedIndex());
            removeOrderTestsJList.setSelectedIndex(0);
        }
    }//GEN-LAST:event_removeOrderTestBtnActionPerformed

    private void addAsDiagnBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAsDiagnBtnActionPerformed
        if(titleJTextField.getText()!=null && titleJTextField.getText().trim().length()!= 0 &&
                textTextArea.getText()!=null && textTextArea.getText().trim().length()!= 0){
        Diagnosis d = new Diagnosis();
        d.setTitle(titleJTextField.getText());
        d.setText(textTextArea.getText());
        
        listModelDiagnosisAdd.addElement(d);
        removeDiagnosisJList.setModel(listModelDiagnosisAdd);
        removeDiagnosisJList.setSelectedIndex(0);
        
        titleJTextField.setText("");
        textTextArea.setText("");
        }
        else{
            JOptionPane.showMessageDialog(null, "Title and text field are required");
        }
    }//GEN-LAST:event_addAsDiagnBtnActionPerformed

    private void removeDiagnosisBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeDiagnosisBtnActionPerformed
        if(!removeDiagnosisJList.isSelectionEmpty()){
            listModelDiagnosisAdd.remove(removeDiagnosisJList.getSelectedIndex());
            removeDiagnosisJList.setSelectedIndex(0);
        }
    }//GEN-LAST:event_removeDiagnosisBtnActionPerformed

    private void addAsResultBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAsResultBtnActionPerformed
        if(titleJTextField.getText()!=null && titleJTextField.getText().trim().length()!= 0 &&
                textTextArea.getText()!=null && textTextArea.getText().trim().length()!= 0){
        Test d = new Test();
        d.setTitle(titleJTextField.getText());
        d.setResult(textTextArea.getText());
        
        listModelTestResultAdd.addElement(d);
        removeResultJList.setModel(listModelTestResultAdd);
        removeResultJList.setSelectedIndex(0);
        
        titleJTextField.setText("");
        textTextArea.setText("");
        }
        else{
            JOptionPane.showMessageDialog(null, "Title and text field are required");
        }
    }//GEN-LAST:event_addAsResultBtnActionPerformed

    private void removeResultBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeResultBtnActionPerformed
        if(!removeResultJList.isSelectionEmpty()){
            listModelTestResultAdd.remove(removeResultJList.getSelectedIndex());
            removeResultJList.setSelectedIndex(0);
        }
    }//GEN-LAST:event_removeResultBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addAsDiagnBtn;
    private javax.swing.JButton addAsResultBtn;
    private javax.swing.JButton addMedicineBtn;
    private javax.swing.JButton addOrderTestBtn;
    private javax.swing.JList<String> addOrderTestsJList;
    private javax.swing.JPanel appointmentPanel;
    private javax.swing.JButton cancelAllBtn;
    private javax.swing.JPanel confirmPanel;
    private javax.swing.JLabel currentPatientLbl;
    private javax.swing.JPanel diagnosisPanel;
    private javax.swing.JSpinner durationSpinner;
    private javax.swing.JList<String> historyAppointmentsList;
    private javax.swing.JList<String> historyDiagnosisList;
    private javax.swing.JTextArea historyDiagnosisTextArea;
    private javax.swing.JLabel historyDoctorLbl;
    private javax.swing.JLabel historyIDLbl;
    private javax.swing.JTable historyMedicineTable;
    private javax.swing.JTable historyOrderedTestsTable;
    private javax.swing.JPanel historyPanel;
    private javax.swing.JTextArea historyTestResultTextArea;
    private javax.swing.JList<String> historyTestResultsList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JList<String> medicineAddJList;
    private javax.swing.JSpinner medicineAmountJSpinner;
    private javax.swing.JPanel medicinePanel;
    private javax.swing.JList<String> medicineRemoveJList;
    private javax.swing.JTabbedPane recordsPane;
    private javax.swing.JPanel recordsPanel;
    private javax.swing.JTextPane recordsTextPane;
    private javax.swing.JButton removeDiagnosisBtn;
    private javax.swing.JList<String> removeDiagnosisJList;
    private javax.swing.JButton removeMedicineJBtn;
    private javax.swing.JButton removeOrderTestBtn;
    private javax.swing.JList<String> removeOrderTestsJList;
    private javax.swing.JButton removeResultBtn;
    private javax.swing.JList<String> removeResultJList;
    private javax.swing.JButton saveAptBtn;
    private javax.swing.JPanel testsPanel;
    private javax.swing.JTextArea textTextArea;
    private javax.swing.JTextField titleJTextField;
    // End of variables declaration//GEN-END:variables

    

   
}
