package GUI;

import BL.*;
import GUI.InterfacesPlus.PatientListener;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import jdk.nashorn.internal.objects.NativeArray;

public class PatientForm extends javax.swing.JPanel implements ActionListener {

    
    private PatientListener patientListener;
    public PatientForm() {
        initComponents();
        btnSavePatient.addActionListener(this);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton)e.getSource();
          
        if (clicked == btnSavePatient) {
            if(mandatoryFieldsCheck()){
            int dialogResult = JOptionPane.showConfirmDialog(null, "Save patient", "CONFIRM", JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION){
            patientListener.patientEmmited(getPatientData());
            }
            }
        else{
              patientListener.patientEmmited(null);  
            }      
        }
    }
    public boolean mandatoryFieldsCheck(){
        if (mobileTxt.getText().trim().length()==0 ||
            fullNameTxt.getText().trim().length()==0 ||
            complaintTxt.getText().trim().length()==0 ||
            telephoneHomeTxt.getText().trim().length()==0||
            nokFullNameTxt.getText().trim().length()==0 ||
            nokRelationship.getText().trim().length()==0||
            dateChooserCombo.getSelectedDate()==null){
            infoLabel.setText("Data missing in mandatory fields");
            return false;
        }
        
        
        infoLabel.setText("");
        return true;
    }
    public void setPatientListner(PatientListener listener){
        this.patientListener = listener;
    }
    
     private Patient getPatientData() {
        Patient p = new Patient();
        Record record = new Record();
        Contact contact = new Contact();
        NextOfKin nextofKin = new NextOfKin();
        
        
        //BASIC STUFF
        p.setID((Integer)patientIDSpinner.getValue());
        p.setFullName(fullNameTxt.getText());
        if (jRadioButtonFemale.isSelected()) {
             record.setSex(Enums.Sex.FEMALE);
        }else{
             record.setSex(Enums.Sex.MALE);
        }
        
        //DATE
        //LocalDate bday = LocalDate.of((Integer)yySpinner.getValue(),
         //       (Integer)mmSpinner.getValue(), (Integer)ddSpinner.getValue());
        
        Calendar selectedValue = (Calendar) dateChooserCombo.getSelectedDate();
        Date selectedDate = selectedValue.getTime();
        LocalDate bday = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        record.setDateOfBirth(bday);
        
        record.setDateCreated(LocalDateTime.now());
         //CONTACT STUFF
         Address present = new Address(); 
         present.setArea(areaText.getText());
         present.setCity(cityText.getText());
         present.setDoorsNumber((Integer)doorNoSpinner.getValue());
         present.setPincode((Integer)pinSpinner.getValue());
         present.setStates(stateText.getText());
         present.setStreet(streetText.getText());
         Address permanent = new Address();
         permanent.setArea(areaText1.getText());
         permanent.setCity(cityText1.getText());
         permanent.setDoorsNumber((Integer)doorNoSpinner1.getValue());
         permanent.setPincode((Integer)pinSpinner1.getValue());
         permanent.setStates(stateText1.getText());
         permanent.setStreet(streetText1.getText());
         
         contact.setCurrentAddress(present);
         contact.setPermanantAddress(permanent);
         contact.setEmail(emailTxt.getText());
         contact.setFax(faxText.getText());
         contact.setMobilePhone(mobileTxt.getText());
         contact.setPager(pagerTxt.getText());
         contact.setTelephoneHome(telephoneHomeTxt.getText());
         contact.setTelephoneWork(telephoneWorkTxt.getText());
         record.setContact(contact);
         
         //NEXT OF KIN
         nextofKin.setFullName(nokFullNameTxt.getText());
         nextofKin.setRelationshipToPatient(nokRelationship.getText());
         Address nokAddress = new Address();
         nokAddress.setArea(nokAreaText.getText());
         nokAddress.setCity(nokCityText.getText());
         nokAddress.setDoorsNumber((Integer)nokDoorNoSpinner.getValue());
         nokAddress.setPincode((Integer)nokPinSpinner.getValue());
         nokAddress.setStates(nokStateText.getText());
         nokAddress.setStreet(nokStreetText.getText());
         Contact nokContact = new Contact();
         nokContact.setCurrentAddress(nokAddress);
         nokContact.setPermanantAddress(nokAddress);
         nokContact.setEmail(nokEmailTxt.getText());
         nokContact.setFax(nokFaxText.getText());
         nokContact.setMobilePhone(nokMobileTxt.getText());
         nokContact.setPager(nokPagerTxt.getText());
         nokContact.setTelephoneHome(nokTelephoneHomeTxt.getText());
         nokContact.setTelephoneWork(nokTelephoneWorkTxt.getText());
         nextofKin.setContact(nokContact);
         
         //PERSONAL AND PROFESSION
         record.setMaritalStatus(marriedCheckBox.isSelected());
         record.setNumberOfDependents((Integer)dependentsSpinner.getValue());
         record.setHeightCM((Double)heightSpinner.getValue());
         record.setWeightKG((Double)weightSpinner.getValue());
         record.setBloodTypeRH(bloodTypeTxt.getText());
         record.setOccupation(occupationTxt.getText());
         record.setGrossAnualIncome((Double)grossAnnualIncomeSpinner.getValue());
         
         //LIFESTYLE
         record.setVegetarian(vegetarianCB.isSelected());
         record.setCigarettesPerDay((Double)cigarettesSpinner.getValue());
         record.setDrinksPerDay((Double)alcoholSpinner.getValue());
         record.setStimulantsUse(stimulantsTxt.getText());
         record.setCoffeeTeaPerDay((Double)coffeeTeaSpinner.getValue());
         record.setSoftDrinksPerDay((Double)softDrinksSpinner.getValue());
         record.setRegularMeals((Short)regularMealsSpinner.getValue());
         record.setEatingHomeVsOut((Short)eatHomeVsOutSpinner.getValue());
         
         //BASIC COMPLAINTS
         record.setComplaint(complaintTxt.getText());
         record.setPreviousTreatment(previousTreatmentTxt.getText());
         record.setPhysicianAndHospitalTreated(physicianHospitalTreatedTxt.getText());
         
         //MEDICAL COMPLAINTS
         record.setDiabetic(diabeticTxtPane.getText());
         record.setHypertensive(hypertensiveTxtPanel.getText());
         record.setCardiacCondition(cardiacTxtPane.getText());
         record.setRespiratoryCondition(respiratoryTxtPane.getText());
         record.setDigestiveCondition(digestiveTxtPane.getText());
         record.setOrthopedCondition(orthopedicTxtPane.getText());
         record.setMuscularCondition(muscularTxtPane.getText());
         record.setNeurologicalCondition(neurologicalTxtPane.getText());
         record.setKnownAllergies(knownAllergiesTxtPane.getText());
         record.setKnownAdverseReactionsToDrugs(reactionsToDrugsTxtPane.getText());
         record.setMajorSurgeries(majorSurgeriesTxtPane.getText());
         
         p.setRecord(record);
         p.setDoctor((Doctor) doctorsComboBox.getSelectedItem());
        
        
        return p;
    }
    
    public void setDoctors (List<Doctor> doctors){
        DefaultComboBoxModel docModel = new DefaultComboBoxModel();
        for (Doctor doctor : doctors) {
            docModel.addElement(doctor);
        }
        doctorsComboBox.setModel(docModel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sexBtnGroup = new javax.swing.ButtonGroup();
        jDatePickerUtil1 = new org.jdatepicker.util.JDatePickerUtil();
        jDatePickerUtil2 = new org.jdatepicker.util.JDatePickerUtil();
        marriedBtnGroup = new javax.swing.ButtonGroup();
        dateChooserDialog1 = new datechooser.beans.DateChooserDialog();
        confirmSaveDialog = new javax.swing.JDialog();
        patientScroll = new javax.swing.JScrollPane();
        panelData = new javax.swing.JPanel();
        basicDetailsPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        patientIDSpinner = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        fullNameTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jRadioButtonFemale = new javax.swing.JRadioButton();
        jRadioButtonMale = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        dateChooserCombo = new datechooser.beans.DateChooserCombo();
        contactDetailsPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        doorNoSpinner = new javax.swing.JSpinner();
        pinSpinner = new javax.swing.JSpinner();
        streetText = new javax.swing.JTextField();
        areaText = new javax.swing.JTextField();
        stateText = new javax.swing.JTextField();
        cityText = new javax.swing.JTextField();
        pinSpinner1 = new javax.swing.JSpinner();
        stateText1 = new javax.swing.JTextField();
        cityText1 = new javax.swing.JTextField();
        areaText1 = new javax.swing.JTextField();
        streetText1 = new javax.swing.JTextField();
        doorNoSpinner1 = new javax.swing.JSpinner();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        telephoneWorkTxt = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        telephoneHomeTxt = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        mobileTxt = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        pagerTxt = new javax.swing.JTextField();
        faxText = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        emailTxt = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        nextOfKinPanel = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        nokDoorNoSpinner = new javax.swing.JSpinner();
        nokPinSpinner = new javax.swing.JSpinner();
        nokStreetText = new javax.swing.JTextField();
        nokAreaText = new javax.swing.JTextField();
        nokStateText = new javax.swing.JTextField();
        nokCityText = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        nokTelephoneWorkTxt = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        nokTelephoneHomeTxt = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        nokMobileTxt = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        nokPagerTxt = new javax.swing.JTextField();
        nokFaxText = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        nokEmailTxt = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        nokFullNameTxt = new javax.swing.JTextField();
        nokRelationship = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        personalDetailsPanel = new javax.swing.JPanel();
        marriedCheckBox = new javax.swing.JCheckBox();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        dependentsSpinner = new javax.swing.JSpinner();
        heightSpinner = new javax.swing.JSpinner();
        weightSpinner = new javax.swing.JSpinner();
        bloodTypeTxt = new javax.swing.JTextField();
        professionDetailsPanel = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        occupationTxt = new javax.swing.JTextField();
        grossAnnualIncomeSpinner = new javax.swing.JSpinner();
        lifestylePanel = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        vegetarianCB = new javax.swing.JCheckBox();
        cigarettesSpinner = new javax.swing.JSpinner();
        alcoholSpinner = new javax.swing.JSpinner();
        coffeeTeaSpinner = new javax.swing.JSpinner();
        softDrinksSpinner = new javax.swing.JSpinner();
        regularMealsSpinner = new javax.swing.JSpinner();
        eatHomeVsOutSpinner = new javax.swing.JSpinner();
        stimulantsScrollPane = new javax.swing.JScrollPane();
        stimulantsTxt = new javax.swing.JTextPane();
        complaintsPanel = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        complaintScrollPane = new javax.swing.JScrollPane();
        complaintTxt = new javax.swing.JTextPane();
        jLabel61 = new javax.swing.JLabel();
        previousTreatmentScroll = new javax.swing.JScrollPane();
        previousTreatmentTxt = new javax.swing.JTextPane();
        jLabel62 = new javax.swing.JLabel();
        physicianHospitalTreatedScroll = new javax.swing.JScrollPane();
        physicianHospitalTreatedTxt = new javax.swing.JTextPane();
        jLabel85 = new javax.swing.JLabel();
        medicalComplaintsPanel = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        diabeticScrollPane = new javax.swing.JScrollPane();
        diabeticTxtPane = new javax.swing.JTextPane();
        jLabel64 = new javax.swing.JLabel();
        hypertensiveScroll = new javax.swing.JScrollPane();
        hypertensiveTxtPanel = new javax.swing.JTextPane();
        jLabel65 = new javax.swing.JLabel();
        cardiacScroll = new javax.swing.JScrollPane();
        cardiacTxtPane = new javax.swing.JTextPane();
        jLabel66 = new javax.swing.JLabel();
        respiratoryScroll = new javax.swing.JScrollPane();
        respiratoryTxtPane = new javax.swing.JTextPane();
        jLabel67 = new javax.swing.JLabel();
        digestiveScroll = new javax.swing.JScrollPane();
        digestiveTxtPane = new javax.swing.JTextPane();
        jLabel68 = new javax.swing.JLabel();
        orthopedicScroll = new javax.swing.JScrollPane();
        orthopedicTxtPane = new javax.swing.JTextPane();
        jLabel69 = new javax.swing.JLabel();
        muscularScroll = new javax.swing.JScrollPane();
        muscularTxtPane = new javax.swing.JTextPane();
        jLabel70 = new javax.swing.JLabel();
        neurologicalScroll = new javax.swing.JScrollPane();
        neurologicalTxtPane = new javax.swing.JTextPane();
        jLabel71 = new javax.swing.JLabel();
        knownAllergiesScroll = new javax.swing.JScrollPane();
        knownAllergiesTxtPane = new javax.swing.JTextPane();
        jLabel72 = new javax.swing.JLabel();
        reactionsToDrugsScroll = new javax.swing.JScrollPane();
        reactionsToDrugsTxtPane = new javax.swing.JTextPane();
        jLabel73 = new javax.swing.JLabel();
        majorSurgeriesScroll = new javax.swing.JScrollPane();
        majorSurgeriesTxtPane = new javax.swing.JTextPane();
        finalizePanel = new javax.swing.JPanel();
        doctorsComboBox = new javax.swing.JComboBox<>();
        jLabel74 = new javax.swing.JLabel();
        btnSavePatient = new javax.swing.JButton();
        infoLabel = new javax.swing.JLabel();
        infoPanel = new javax.swing.JPanel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();

        confirmSaveDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        confirmSaveDialog.setTitle("Save?");
        confirmSaveDialog.setAlwaysOnTop(true);

        javax.swing.GroupLayout confirmSaveDialogLayout = new javax.swing.GroupLayout(confirmSaveDialog.getContentPane());
        confirmSaveDialog.getContentPane().setLayout(confirmSaveDialogLayout);
        confirmSaveDialogLayout.setHorizontalGroup(
            confirmSaveDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        confirmSaveDialogLayout.setVerticalGroup(
            confirmSaveDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setName("mainPanel"); // NOI18N
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        patientScroll.setMinimumSize(new java.awt.Dimension(230, 230));
        patientScroll.setName("patientScroll"); // NOI18N

        panelData.setMinimumSize(new java.awt.Dimension(300, 200));
        panelData.setPreferredSize(new java.awt.Dimension(633, 3700));

        basicDetailsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Basic Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        basicDetailsPanel.setToolTipText("");
        basicDetailsPanel.setName(""); // NOI18N

        jLabel1.setText("Outpatient ID (0 = new):");

        patientIDSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        jLabel2.setText("Full name:");

        jLabel3.setText("Sex:");

        sexBtnGroup.add(jRadioButtonFemale);
        jRadioButtonFemale.setSelected(true);
        jRadioButtonFemale.setText("Female");

        sexBtnGroup.add(jRadioButtonMale);
        jRadioButtonMale.setText("Male");

        jLabel4.setText("Date of birth:");

        jLabel78.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel78.setText("*");

        jLabel79.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel79.setText("*");

        jLabel80.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel80.setText("*");

        javax.swing.GroupLayout basicDetailsPanelLayout = new javax.swing.GroupLayout(basicDetailsPanel);
        basicDetailsPanel.setLayout(basicDetailsPanelLayout);
        basicDetailsPanelLayout.setHorizontalGroup(
            basicDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basicDetailsPanelLayout.createSequentialGroup()
                .addGroup(basicDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(basicDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(patientIDSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(basicDetailsPanelLayout.createSequentialGroup()
                        .addComponent(jRadioButtonFemale)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonMale))
                    .addComponent(fullNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateChooserCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(basicDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel78)
                    .addComponent(jLabel79)
                    .addComponent(jLabel80))
                .addContainerGap(110, Short.MAX_VALUE))
        );
        basicDetailsPanelLayout.setVerticalGroup(
            basicDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basicDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(basicDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(patientIDSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(basicDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(fullNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel78))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(basicDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jRadioButtonFemale)
                    .addComponent(jRadioButtonMale)
                    .addComponent(jLabel79))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(basicDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel80)
                    .addComponent(jLabel4)
                    .addComponent(dateChooserCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        contactDetailsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contact Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 2, 13)); // NOI18N
        jLabel8.setText("Present Address");

        jLabel9.setText("Door No:");

        jLabel10.setText("Street:");

        jLabel11.setText("Area:");

        jLabel12.setText("City:");

        jLabel13.setText("State:");

        jLabel14.setText("Pincode:");

        doorNoSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        pinSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        streetText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                streetTextActionPerformed(evt);
            }
        });

        pinSpinner1.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        streetText1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                streetText1ActionPerformed(evt);
            }
        });

        doorNoSpinner1.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 2, 13)); // NOI18N
        jLabel15.setText("Permanent Address");

        jLabel16.setText("Door No:");

        jLabel17.setText("Street:");

        jLabel18.setText("Area:");

        jLabel19.setText("City:");

        jLabel20.setText("State:");

        jLabel21.setText("Pincode:");

        jLabel22.setFont(new java.awt.Font("Tahoma", 2, 13)); // NOI18N
        jLabel22.setText("Telephones/Fax/Email");

        jLabel23.setText("Telephone (Work):");

        telephoneWorkTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telephoneWorkTxtActionPerformed(evt);
            }
        });

        jLabel24.setText("Telephone (Home):");

        telephoneHomeTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telephoneHomeTxtActionPerformed(evt);
            }
        });

        jLabel25.setText("Mobile:");

        mobileTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mobileTxtActionPerformed(evt);
            }
        });

        jLabel26.setText("Pager:");

        pagerTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pagerTxtActionPerformed(evt);
            }
        });

        faxText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                faxTextActionPerformed(evt);
            }
        });

        jLabel27.setText("Fax:");

        emailTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailTxtActionPerformed(evt);
            }
        });

        jLabel28.setText("Email:");

        jLabel81.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel81.setText("*");

        jLabel82.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel82.setText("*");

        javax.swing.GroupLayout contactDetailsPanelLayout = new javax.swing.GroupLayout(contactDetailsPanel);
        contactDetailsPanel.setLayout(contactDetailsPanelLayout);
        contactDetailsPanelLayout.setHorizontalGroup(
            contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contactDetailsPanelLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(contactDetailsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(2, 2, 2)
                        .addComponent(pagerTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contactDetailsPanelLayout.createSequentialGroup()
                        .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(streetText)
                            .addComponent(doorNoSpinner)
                            .addComponent(pinSpinner)
                            .addComponent(areaText)
                            .addComponent(stateText)
                            .addComponent(cityText, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(contactDetailsPanelLayout.createSequentialGroup()
                        .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contactDetailsPanelLayout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(3, 3, 3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contactDetailsPanelLayout.createSequentialGroup()
                                .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel21))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(streetText1)
                            .addComponent(doorNoSpinner1)
                            .addComponent(pinSpinner1)
                            .addComponent(areaText1)
                            .addComponent(stateText1)
                            .addComponent(cityText1)
                            .addComponent(telephoneWorkTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)))
                    .addGroup(contactDetailsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contactDetailsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(telephoneHomeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contactDetailsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(3, 3, 3)
                        .addComponent(faxText, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contactDetailsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mobileTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel81)
                    .addComponent(jLabel82))
                .addGap(80, 80, 80))
        );
        contactDetailsPanelLayout.setVerticalGroup(
            contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contactDetailsPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(doorNoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(streetText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(areaText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(cityText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(stateText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(pinSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(doorNoSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(streetText1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(areaText1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(cityText1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(stateText1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(pinSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(telephoneWorkTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(telephoneHomeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel81))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(mobileTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel82)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pagerTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(faxText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contactDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        nextOfKinPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Next of Kin", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel29.setFont(new java.awt.Font("Tahoma", 2, 13)); // NOI18N
        jLabel29.setText("Contact Address");

        jLabel30.setText("Door No:");

        jLabel31.setText("Street:");

        jLabel32.setText("Area:");

        jLabel33.setText("City:");

        jLabel34.setText("State:");

        jLabel35.setText("Pincode:");

        nokDoorNoSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        nokPinSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        nokStreetText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nokStreetTextActionPerformed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Tahoma", 2, 13)); // NOI18N
        jLabel43.setText("Telephones/Fax/Email");

        jLabel44.setText("Telephone (Work):");

        nokTelephoneWorkTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nokTelephoneWorkTxtActionPerformed(evt);
            }
        });

        jLabel45.setText("Telephone (Home):");

        nokTelephoneHomeTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nokTelephoneHomeTxtActionPerformed(evt);
            }
        });

        jLabel46.setText("Mobile:");

        nokMobileTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nokMobileTxtActionPerformed(evt);
            }
        });

        jLabel47.setText("Pager:");

        nokPagerTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nokPagerTxtActionPerformed(evt);
            }
        });

        nokFaxText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nokFaxTextActionPerformed(evt);
            }
        });

        jLabel48.setText("Fax: ");

        nokEmailTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nokEmailTxtActionPerformed(evt);
            }
        });

        jLabel49.setText("Email:");

        jLabel50.setText("Full name:");

        nokFullNameTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nokFullNameTxtActionPerformed(evt);
            }
        });

        jLabel51.setText("Relationship with outpatient:");

        jLabel83.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel83.setText("*");

        jLabel84.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel84.setText("*");

        javax.swing.GroupLayout nextOfKinPanelLayout = new javax.swing.GroupLayout(nextOfKinPanel);
        nextOfKinPanel.setLayout(nextOfKinPanelLayout);
        nextOfKinPanelLayout.setHorizontalGroup(
            nextOfKinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nextOfKinPanelLayout.createSequentialGroup()
                .addGroup(nextOfKinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nextOfKinPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(nextOfKinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel51)
                            .addComponent(jLabel50))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(nextOfKinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(nextOfKinPanelLayout.createSequentialGroup()
                                .addComponent(nokFullNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel83))
                            .addGroup(nextOfKinPanelLayout.createSequentialGroup()
                                .addComponent(nokRelationship, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel84))))
                    .addGroup(nextOfKinPanelLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(nextOfKinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(nextOfKinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(nextOfKinPanelLayout.createSequentialGroup()
                                    .addGroup(nextOfKinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel32)
                                        .addComponent(jLabel31)
                                        .addComponent(jLabel30)
                                        .addComponent(jLabel33)
                                        .addComponent(jLabel34)
                                        .addComponent(jLabel35))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(nextOfKinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(nokStreetText)
                                        .addComponent(nokDoorNoSpinner)
                                        .addComponent(nokPinSpinner)
                                        .addComponent(nokAreaText)
                                        .addComponent(nokStateText)
                                        .addComponent(nokCityText, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(nextOfKinPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel44)
                                    .addGap(3, 3, 3)
                                    .addGroup(nextOfKinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(nokTelephoneWorkTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(nextOfKinPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel49)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(nokEmailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(nextOfKinPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel45)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(nokTelephoneHomeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(nextOfKinPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel48)
                                    .addGap(3, 3, 3)
                                    .addComponent(nokFaxText, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(nextOfKinPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel46)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(nokMobileTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(nextOfKinPanelLayout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addComponent(jLabel47)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(nokPagerTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(113, Short.MAX_VALUE))
        );
        nextOfKinPanelLayout.setVerticalGroup(
            nextOfKinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nextOfKinPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(nextOfKinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(nokFullNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel83))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(nextOfKinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nokRelationship, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51)
                    .addComponent(jLabel84))
                .addGap(18, 18, 18)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(nextOfKinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(nokDoorNoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(nextOfKinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nokStreetText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(nextOfKinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(nokAreaText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(nextOfKinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(nokCityText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(nextOfKinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(nokStateText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(nextOfKinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(nokPinSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(nextOfKinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nokTelephoneWorkTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(nextOfKinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nokTelephoneHomeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(nextOfKinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nokMobileTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(nextOfKinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nokPagerTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(nextOfKinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nokFaxText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(nextOfKinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nokEmailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(118, 118, 118))
        );

        personalDetailsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Personal Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel36.setText("Married:");

        jLabel37.setText("No. of Dependents:");

        jLabel38.setText("Height (cm):");

        jLabel39.setText("Weight (KG):");

        jLabel40.setText("BLood Type - RH:");

        dependentsSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        heightSpinner.setModel(new javax.swing.SpinnerNumberModel(175.0d, 0.0d, null, 1.0d));

        weightSpinner.setModel(new javax.swing.SpinnerNumberModel(75.0d, 0.0d, null, 1.0d));

        bloodTypeTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bloodTypeTxtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout personalDetailsPanelLayout = new javax.swing.GroupLayout(personalDetailsPanel);
        personalDetailsPanel.setLayout(personalDetailsPanelLayout);
        personalDetailsPanelLayout.setHorizontalGroup(
            personalDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personalDetailsPanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(personalDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel40)
                    .addComponent(jLabel39)
                    .addComponent(jLabel38)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(personalDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(marriedCheckBox)
                    .addComponent(heightSpinner)
                    .addComponent(bloodTypeTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                    .addComponent(weightSpinner)
                    .addComponent(dependentsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        personalDetailsPanelLayout.setVerticalGroup(
            personalDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personalDetailsPanelLayout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(personalDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(personalDetailsPanelLayout.createSequentialGroup()
                        .addGroup(personalDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel36)
                            .addComponent(marriedCheckBox))
                        .addGap(9, 9, 9)
                        .addComponent(jLabel37))
                    .addComponent(dependentsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personalDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(heightSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personalDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(weightSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personalDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(bloodTypeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        professionDetailsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Profession Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel41.setText("Occupation:");

        jLabel42.setText("Gross Annual Income:");

        grossAnnualIncomeSpinner.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));

        javax.swing.GroupLayout professionDetailsPanelLayout = new javax.swing.GroupLayout(professionDetailsPanel);
        professionDetailsPanel.setLayout(professionDetailsPanelLayout);
        professionDetailsPanelLayout.setHorizontalGroup(
            professionDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(professionDetailsPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(professionDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel42)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(professionDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(occupationTxt)
                    .addComponent(grossAnnualIncomeSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        professionDetailsPanelLayout.setVerticalGroup(
            professionDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(professionDetailsPanelLayout.createSequentialGroup()
                .addGroup(professionDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(occupationTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(professionDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(grossAnnualIncomeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        lifestylePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lifestyle", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel52.setText("Vegetarian:");

        jLabel53.setText("Average no. of cigarettes/day:");

        jLabel54.setText("Average no. of drinks/day:");

        jLabel55.setText("Use stimulants(specify):");

        jLabel56.setText("Consumption of Coffee-Tea/day:");

        jLabel57.setText("Consumption of Soft Drinks/day:");

        jLabel58.setText("Have Regular Meals (Breakfast/Lunch/Dinner)- choose 1-10:");

        jLabel59.setText("Eat home food/outside predominantly (choose 1-10):");

        vegetarianCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vegetarianCBActionPerformed(evt);
            }
        });

        cigarettesSpinner.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));

        alcoholSpinner.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));

        coffeeTeaSpinner.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 0.0d));

        softDrinksSpinner.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));

        regularMealsSpinner.setModel(new javax.swing.SpinnerNumberModel(Short.valueOf((short)1), Short.valueOf((short)1), Short.valueOf((short)10), Short.valueOf((short)1)));

        eatHomeVsOutSpinner.setModel(new javax.swing.SpinnerNumberModel(Short.valueOf((short)1), Short.valueOf((short)1), Short.valueOf((short)10), Short.valueOf((short)1)));

        stimulantsScrollPane.setViewportView(stimulantsTxt);

        javax.swing.GroupLayout lifestylePanelLayout = new javax.swing.GroupLayout(lifestylePanel);
        lifestylePanel.setLayout(lifestylePanelLayout);
        lifestylePanelLayout.setHorizontalGroup(
            lifestylePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lifestylePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lifestylePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lifestylePanelLayout.createSequentialGroup()
                        .addGroup(lifestylePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel52)
                            .addComponent(jLabel53))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(lifestylePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(vegetarianCB)
                            .addComponent(cigarettesSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(lifestylePanelLayout.createSequentialGroup()
                        .addGroup(lifestylePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel59)
                            .addGroup(lifestylePanelLayout.createSequentialGroup()
                                .addGroup(lifestylePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(lifestylePanelLayout.createSequentialGroup()
                                        .addGroup(lifestylePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel57)
                                            .addComponent(jLabel56))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(lifestylePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(coffeeTeaSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                                            .addComponent(softDrinksSpinner)))
                                    .addComponent(jLabel58))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(lifestylePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(regularMealsSpinner)
                            .addComponent(eatHomeVsOutSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(94, 94, 94))
            .addGroup(lifestylePanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(lifestylePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel55)
                    .addComponent(jLabel54))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lifestylePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lifestylePanelLayout.createSequentialGroup()
                        .addComponent(alcoholSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(stimulantsScrollPane))
                .addContainerGap())
        );
        lifestylePanelLayout.setVerticalGroup(
            lifestylePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lifestylePanelLayout.createSequentialGroup()
                .addGroup(lifestylePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel52)
                    .addComponent(vegetarianCB))
                .addGap(10, 10, 10)
                .addGroup(lifestylePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(cigarettesSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lifestylePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(alcoholSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(lifestylePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel55)
                    .addComponent(stimulantsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lifestylePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(coffeeTeaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lifestylePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(softDrinksSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(lifestylePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(regularMealsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lifestylePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59)
                    .addComponent(eatHomeVsOutSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        complaintsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Basic complaints", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel60.setText("Statement of complaint:");

        complaintScrollPane.setViewportView(complaintTxt);

        jLabel61.setText("History of previous treatment:");

        previousTreatmentScroll.setViewportView(previousTreatmentTxt);

        jLabel62.setText("Physician/Hospital treated:");

        physicianHospitalTreatedScroll.setViewportView(physicianHospitalTreatedTxt);

        jLabel85.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel85.setText("*");

        javax.swing.GroupLayout complaintsPanelLayout = new javax.swing.GroupLayout(complaintsPanel);
        complaintsPanel.setLayout(complaintsPanelLayout);
        complaintsPanelLayout.setHorizontalGroup(
            complaintsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(complaintsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(complaintsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(complaintsPanelLayout.createSequentialGroup()
                        .addGroup(complaintsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(complaintScrollPane)
                            .addComponent(previousTreatmentScroll)
                            .addComponent(physicianHospitalTreatedScroll)
                            .addGroup(complaintsPanelLayout.createSequentialGroup()
                                .addGroup(complaintsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel61)
                                    .addComponent(jLabel62))
                                .addGap(0, 307, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(complaintsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel60)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel85)
                        .addGap(72, 72, 72))))
        );
        complaintsPanelLayout.setVerticalGroup(
            complaintsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(complaintsPanelLayout.createSequentialGroup()
                .addGroup(complaintsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(complaintsPanelLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel60))
                    .addGroup(complaintsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel85)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(complaintScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel61)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(previousTreatmentScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel62)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(physicianHospitalTreatedScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        medicalComplaintsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Important Medical Complaints", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel63.setText("Diabetic:");

        diabeticScrollPane.setViewportView(diabeticTxtPane);

        jLabel64.setText("Hypertensive:");

        hypertensiveScroll.setViewportView(hypertensiveTxtPanel);

        jLabel65.setText("Cardiac Condition:");

        cardiacScroll.setViewportView(cardiacTxtPane);

        jLabel66.setText("Respiratory Condition:");

        respiratoryScroll.setViewportView(respiratoryTxtPane);

        jLabel67.setText("Digestive Condition:");

        digestiveScroll.setViewportView(digestiveTxtPane);

        jLabel68.setText("Orthopedic Condition:");

        orthopedicScroll.setViewportView(orthopedicTxtPane);

        jLabel69.setText("Muscular Condition:");

        muscularScroll.setViewportView(muscularTxtPane);

        jLabel70.setText("Neurological Condition:");

        neurologicalScroll.setViewportView(neurologicalTxtPane);

        jLabel71.setText("Known Allergies:");

        knownAllergiesScroll.setViewportView(knownAllergiesTxtPane);

        jLabel72.setText("Known Adverse Reactions to Specific Drugs:");

        reactionsToDrugsScroll.setViewportView(reactionsToDrugsTxtPane);

        jLabel73.setText("Major Surgeries (History):");

        majorSurgeriesScroll.setViewportView(majorSurgeriesTxtPane);

        javax.swing.GroupLayout medicalComplaintsPanelLayout = new javax.swing.GroupLayout(medicalComplaintsPanel);
        medicalComplaintsPanel.setLayout(medicalComplaintsPanelLayout);
        medicalComplaintsPanelLayout.setHorizontalGroup(
            medicalComplaintsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(medicalComplaintsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(medicalComplaintsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(diabeticScrollPane)
                    .addComponent(hypertensiveScroll)
                    .addComponent(cardiacScroll)
                    .addComponent(respiratoryScroll)
                    .addComponent(digestiveScroll)
                    .addComponent(orthopedicScroll)
                    .addComponent(muscularScroll)
                    .addComponent(neurologicalScroll)
                    .addComponent(knownAllergiesScroll)
                    .addComponent(reactionsToDrugsScroll)
                    .addComponent(majorSurgeriesScroll)
                    .addGroup(medicalComplaintsPanelLayout.createSequentialGroup()
                        .addGroup(medicalComplaintsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel63)
                            .addComponent(jLabel64)
                            .addComponent(jLabel65)
                            .addComponent(jLabel66)
                            .addComponent(jLabel67)
                            .addComponent(jLabel68)
                            .addComponent(jLabel69)
                            .addComponent(jLabel70)
                            .addComponent(jLabel71)
                            .addComponent(jLabel72)
                            .addComponent(jLabel73))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        medicalComplaintsPanelLayout.setVerticalGroup(
            medicalComplaintsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(medicalComplaintsPanelLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel63)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(diabeticScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel64)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hypertensiveScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel65)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardiacScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel66)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(respiratoryScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel67)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(digestiveScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel68)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(orthopedicScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel69)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(muscularScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel70)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(neurologicalScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel71)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(knownAllergiesScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel72)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reactionsToDrugsScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel73)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(majorSurgeriesScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        doctorsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        doctorsComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doctorsComboBoxActionPerformed(evt);
            }
        });

        jLabel74.setText("Odaberite doktora:");

        btnSavePatient.setText("Save Patient");
        btnSavePatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSavePatientActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout finalizePanelLayout = new javax.swing.GroupLayout(finalizePanel);
        finalizePanel.setLayout(finalizePanelLayout);
        finalizePanelLayout.setHorizontalGroup(
            finalizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, finalizePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel74)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(finalizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(doctorsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSavePatient, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(infoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(96, 96, 96))
        );
        finalizePanelLayout.setVerticalGroup(
            finalizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(finalizePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(finalizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(doctorsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel74))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSavePatient, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        infoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("INFO"));
        infoPanel.setMaximumSize(new java.awt.Dimension(140, 140));

        jLabel76.setText("Fields marked with *");

        jLabel77.setText("are mandatory (patient");

        jLabel86.setText("name, sex, DoB, mobile,");

        jLabel87.setText("tel(home), Next of kin:");

        jLabel88.setText("name and relationship)");

        javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel76, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel77)
                    .addComponent(jLabel86)
                    .addComponent(jLabel87)
                    .addComponent(jLabel88))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        infoPanelLayout.setVerticalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addComponent(jLabel76)
                .addGap(5, 5, 5)
                .addComponent(jLabel77)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel86)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel87)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel88)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelDataLayout = new javax.swing.GroupLayout(panelData);
        panelData.setLayout(panelDataLayout);
        panelDataLayout.setHorizontalGroup(
            panelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDataLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(contactDetailsPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(basicDetailsPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nextOfKinPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(personalDetailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(professionDetailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lifestylePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(complaintsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(medicalComplaintsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(finalizePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(29, 29, 29))
        );
        panelDataLayout.setVerticalGroup(
            panelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(infoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(basicDetailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contactDetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nextOfKinPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(personalDetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(professionDetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lifestylePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(complaintsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(medicalComplaintsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(finalizePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        basicDetailsPanel.getAccessibleContext().setAccessibleName("");

        patientScroll.setViewportView(panelData);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(patientScroll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(patientScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 927, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleName("mainPanel");
    }// </editor-fold>//GEN-END:initComponents

    private void streetTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_streetTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_streetTextActionPerformed

    private void streetText1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_streetText1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_streetText1ActionPerformed

    private void telephoneWorkTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telephoneWorkTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telephoneWorkTxtActionPerformed

    private void telephoneHomeTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telephoneHomeTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telephoneHomeTxtActionPerformed

    private void mobileTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mobileTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mobileTxtActionPerformed

    private void pagerTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pagerTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pagerTxtActionPerformed

    private void faxTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_faxTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_faxTextActionPerformed

    private void emailTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailTxtActionPerformed

    private void nokStreetTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nokStreetTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nokStreetTextActionPerformed

    private void nokTelephoneWorkTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nokTelephoneWorkTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nokTelephoneWorkTxtActionPerformed

    private void nokTelephoneHomeTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nokTelephoneHomeTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nokTelephoneHomeTxtActionPerformed

    private void nokMobileTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nokMobileTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nokMobileTxtActionPerformed

    private void nokPagerTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nokPagerTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nokPagerTxtActionPerformed

    private void nokFaxTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nokFaxTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nokFaxTextActionPerformed

    private void nokEmailTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nokEmailTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nokEmailTxtActionPerformed

    private void nokFullNameTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nokFullNameTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nokFullNameTxtActionPerformed

    private void bloodTypeTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bloodTypeTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bloodTypeTxtActionPerformed

    private void vegetarianCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vegetarianCBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vegetarianCBActionPerformed

    private void doctorsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doctorsComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_doctorsComboBoxActionPerformed

    private void btnSavePatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSavePatientActionPerformed
        
    }//GEN-LAST:event_btnSavePatientActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        try {
            setDoctors(new Hospital().getAllDoctors());
        } catch (SQLException ex) {
            //Logger.getLogger(PatientForm.class.getName()).log(Level.SEVERE, null, ex);
            new DBErrorDialog("Reloading doctors failed");
        }
    }//GEN-LAST:event_formComponentShown


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner alcoholSpinner;
    private javax.swing.JTextField areaText;
    private javax.swing.JTextField areaText1;
    private javax.swing.JPanel basicDetailsPanel;
    private javax.swing.JTextField bloodTypeTxt;
    private javax.swing.JButton btnSavePatient;
    private javax.swing.JScrollPane cardiacScroll;
    private javax.swing.JTextPane cardiacTxtPane;
    private javax.swing.JSpinner cigarettesSpinner;
    private javax.swing.JTextField cityText;
    private javax.swing.JTextField cityText1;
    private javax.swing.JSpinner coffeeTeaSpinner;
    private javax.swing.JScrollPane complaintScrollPane;
    private javax.swing.JTextPane complaintTxt;
    private javax.swing.JPanel complaintsPanel;
    private javax.swing.JDialog confirmSaveDialog;
    private javax.swing.JPanel contactDetailsPanel;
    private datechooser.beans.DateChooserCombo dateChooserCombo;
    private datechooser.beans.DateChooserDialog dateChooserDialog1;
    private javax.swing.JSpinner dependentsSpinner;
    private javax.swing.JScrollPane diabeticScrollPane;
    private javax.swing.JTextPane diabeticTxtPane;
    private javax.swing.JScrollPane digestiveScroll;
    private javax.swing.JTextPane digestiveTxtPane;
    private javax.swing.JComboBox<String> doctorsComboBox;
    private javax.swing.JSpinner doorNoSpinner;
    private javax.swing.JSpinner doorNoSpinner1;
    private javax.swing.JSpinner eatHomeVsOutSpinner;
    private javax.swing.JTextField emailTxt;
    private javax.swing.JTextField faxText;
    private javax.swing.JPanel finalizePanel;
    private javax.swing.JTextField fullNameTxt;
    private javax.swing.JSpinner grossAnnualIncomeSpinner;
    private javax.swing.JSpinner heightSpinner;
    private javax.swing.JScrollPane hypertensiveScroll;
    private javax.swing.JTextPane hypertensiveTxtPanel;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JPanel infoPanel;
    private org.jdatepicker.util.JDatePickerUtil jDatePickerUtil1;
    private org.jdatepicker.util.JDatePickerUtil jDatePickerUtil2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton jRadioButtonFemale;
    private javax.swing.JRadioButton jRadioButtonMale;
    private javax.swing.JScrollPane knownAllergiesScroll;
    private javax.swing.JTextPane knownAllergiesTxtPane;
    private javax.swing.JPanel lifestylePanel;
    private javax.swing.JScrollPane majorSurgeriesScroll;
    private javax.swing.JTextPane majorSurgeriesTxtPane;
    private javax.swing.ButtonGroup marriedBtnGroup;
    private javax.swing.JCheckBox marriedCheckBox;
    private javax.swing.JPanel medicalComplaintsPanel;
    private javax.swing.JTextField mobileTxt;
    private javax.swing.JScrollPane muscularScroll;
    private javax.swing.JTextPane muscularTxtPane;
    private javax.swing.JScrollPane neurologicalScroll;
    private javax.swing.JTextPane neurologicalTxtPane;
    private javax.swing.JPanel nextOfKinPanel;
    private javax.swing.JTextField nokAreaText;
    private javax.swing.JTextField nokCityText;
    private javax.swing.JSpinner nokDoorNoSpinner;
    private javax.swing.JTextField nokEmailTxt;
    private javax.swing.JTextField nokFaxText;
    private javax.swing.JTextField nokFullNameTxt;
    private javax.swing.JTextField nokMobileTxt;
    private javax.swing.JTextField nokPagerTxt;
    private javax.swing.JSpinner nokPinSpinner;
    private javax.swing.JTextField nokRelationship;
    private javax.swing.JTextField nokStateText;
    private javax.swing.JTextField nokStreetText;
    private javax.swing.JTextField nokTelephoneHomeTxt;
    private javax.swing.JTextField nokTelephoneWorkTxt;
    private javax.swing.JTextField occupationTxt;
    private javax.swing.JScrollPane orthopedicScroll;
    private javax.swing.JTextPane orthopedicTxtPane;
    private javax.swing.JTextField pagerTxt;
    private javax.swing.JPanel panelData;
    private javax.swing.JSpinner patientIDSpinner;
    private javax.swing.JScrollPane patientScroll;
    private javax.swing.JPanel personalDetailsPanel;
    private javax.swing.JScrollPane physicianHospitalTreatedScroll;
    private javax.swing.JTextPane physicianHospitalTreatedTxt;
    private javax.swing.JSpinner pinSpinner;
    private javax.swing.JSpinner pinSpinner1;
    private javax.swing.JScrollPane previousTreatmentScroll;
    private javax.swing.JTextPane previousTreatmentTxt;
    private javax.swing.JPanel professionDetailsPanel;
    private javax.swing.JScrollPane reactionsToDrugsScroll;
    private javax.swing.JTextPane reactionsToDrugsTxtPane;
    private javax.swing.JSpinner regularMealsSpinner;
    private javax.swing.JScrollPane respiratoryScroll;
    private javax.swing.JTextPane respiratoryTxtPane;
    private javax.swing.ButtonGroup sexBtnGroup;
    private javax.swing.JSpinner softDrinksSpinner;
    private javax.swing.JTextField stateText;
    private javax.swing.JTextField stateText1;
    private javax.swing.JScrollPane stimulantsScrollPane;
    private javax.swing.JTextPane stimulantsTxt;
    private javax.swing.JTextField streetText;
    private javax.swing.JTextField streetText1;
    private javax.swing.JTextField telephoneHomeTxt;
    private javax.swing.JTextField telephoneWorkTxt;
    private javax.swing.JCheckBox vegetarianCB;
    private javax.swing.JSpinner weightSpinner;
    // End of variables declaration//GEN-END:variables

   
   

    
}
