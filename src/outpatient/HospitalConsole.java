package outpatient;





import BL.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static outpatient.ConsoleRepo.*;



//SHOULD I SET FORMS UP INTO SINGLE MENU?
/**
 * Created by Chaos on 8.6.2017..
 */
public class HospitalConsole {
    private static Hospital hospital = new Hospital();//RADI LI INICIJALIZACIJA?
    private static Doctor doctorHolder;
    private static Patient patientHolder;
    private static Scanner scanner = new Scanner(System.in);
    private static FullAppointment fullAppointment;


    public static void consoleProgram() throws SQLException {
        Scanner scanner = new Scanner(System.in);

            ArrayList<Bill> bills = hospital.getBillsByPatientID(2, false);

        for (Bill b:bills) {
            System.out.println(b.getDate());
            System.out.println(b.getDoctorID());
            System.out.println(b.getAppointmentID());
            System.out.println(b.getTotalPrice());
            for (BillItem bi:b.getItems()) {
                System.out.println(bi.getItemName()+"/"+bi.getPrice()+"$/"+bi.getAmount());
            }

        }


            System.out.println("----HOSPITAL GENERICS----");
            System.out.println("[1] Reception");
            System.out.println("[2] Doctor");
            System.out.println("[0] EXIT");
            
            int pick = Integer.parseInt(scanner.nextLine());

            switch (pick) {
                case 1:
                    receptionMenu();
                    break;
                case 2:
                    doctorMenu();
                    break;
                case 0:
                    break;
                default:
                    break;


        }
    }

    //RECEPTION STUFF

    private static void receptionMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("----RECEPTION----");
        System.out.println("[1] Patient: Short Form");
        System.out.println("[2] Patient: Full Form");
        System.out.println("[3] Doctor management");
        System.out.println("[4] Payments");
        System.out.println("[5] Create appointments");
        System.out.println("[6] Reports");

        System.out.println("[0] EXIT");

        int pick = Integer.parseInt(scanner.nextLine());

        switch (pick) {
            case 0:
                consoleProgram();
                break;
            case 1:
                shortFormInsert();
                break;
            case 2:
                fullFormInsert(true);
                break;
            case 3:
                doctorEditMenu();
                break;
            case 4:
                paymentMenu(null);
                break;
            case 5:
                appointmentsManagement();
                break;
            case 6:
                reportsManagement();
                break;
        }
    }

    private static void reportsManagement() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("----REPORTS----");
        System.out.println("[1] Daily report");
        System.out.println("[2] Weekly report");
        System.out.println("[3] Monthly report");
        System.out.println("[0] Return");
        int pick = Integer.parseInt(scanner.nextLine());

        switch (pick){
            case 1:

                reportsManagement();
                break;
            case 2:

                reportsManagement();
                break;
            case 3:

                reportsManagement();
                break;
            case 0:
                receptionMenu();
                break;
            default:
                receptionMenu();
                break;
        }
    }

    private static void shortFormInsert() throws SQLException {
        //DATA
        Scanner scanner = new Scanner(System.in);
        Patient newPatient = new Patient();
        Doctor doctor = new Doctor();
        Record newRecord = new Record();
        Contact newContact = new Contact();
        NextOfKin nextOfKin = new NextOfKin();

        // 1)FULLNAME
        System.out.println("Write full name of patient\n");
        newPatient.setFullName(scanner.nextLine());

        // 2)SEX
        System.out.println("Sex (m/f)\n");
        String hold = "";

        while (!(hold.equals("m")) && !(hold.equals("f"))) {
            hold = scanner.nextLine();
            System.out.println(hold);
            if (!(hold.equals("m")) && !(hold.equals("f"))) {
                System.out.println("Wrong input, try again!");
            }
        }
        if (hold.equals("m")) {
            newRecord.setSex(Enums.Sex.MALE);
        } else {
            newRecord.setSex(Enums.Sex.FEMALE);
        }

        // 3)DOB
        System.out.println("Date of birth(Format: dd.MM.yyyy.):");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        String date = scanner.nextLine();
        LocalDate theDate = LocalDate.parse(date, formatter);
        System.out.println("!!!!!!TEST TIME: "+theDate);
        newRecord.setDateOfBirth(theDate);

        // 4)COMPLAINT
        System.out.println("Brief statement of complaint: ");
        newRecord.setComplaint(scanner.nextLine());

        // 5)PHONES
        System.out.println("Telephone 1:");
        newContact.setTelephoneWork(scanner.nextLine());

        System.out.println("Telephone 2:");
        newContact.setTelephoneHome(scanner.nextLine());

        // 6)NEXT OF KIN
        System.out.println("Full name of next of kin");
        nextOfKin.setFullName(scanner.nextLine());
        System.out.println("Relationship to outpatient");
        nextOfKin.setRelationshipToPatient(scanner.nextLine());

        //ADD DATE CREATED
        newRecord.setDateCreated(LocalDateTime.now());
        //LOAD DATA
//        Address address = new Address();
//        newContact.setCurrentAddress(address);
//        newContact.setPermanantAddress(address);
//        newRecord.setContact(newContact);
//        nextOfKin.setContact(newContact);
        newRecord.setNextOfKin(nextOfKin);
        newPatient.setRecord(newRecord);


        //NEWvsOLD patient and CONFIRMATION
        System.out.println("Is patient new? (y/n)");
        String newOutpatient = scanner.nextLine();
        System.out.println("Save patient? (y/n)");
        String save = scanner.nextLine();

        if (newOutpatient.equals("y") && save.equals("y")) {
            // 7)ADD A DOCTOR
            ArrayList<Doctor> doctors = hospital.getAllDoctors();
            for (Doctor d : doctors) {
                System.out.println("ID:" + d.getID() + " NAME:" + d.getFullName() + "\n");
            }
            System.out.println("Choose ID of wanted doctor (just a number)");
            String doc = scanner.nextLine();
            Integer docID = Integer.parseInt(doc);
            doctor = hospital.getDoctor(docID);
            newPatient.setDoctor(doctor);
            hospital.addNewPatient(newPatient);

        } else if (newOutpatient.equals("n") && save.equals("y")) {
            hospital.addOldPatient(newPatient);
        } else {
            receptionMenu();
        }
        receptionMenu();


    }

    private static void fullFormInsert(boolean fullForm) throws SQLException {
        //BASIC INFO
        //DATA
        Scanner scanner = new Scanner(System.in);
        Patient newPatient = new Patient();
        Doctor doctor = new Doctor();
        Record newRecord = new Record();
        Contact newContact = new Contact();
        NextOfKin nextOfKin = new NextOfKin();

        if(fullForm) {
            // 1)PATIENT ID
            System.out.println("Write patient ID(0 if the patient is new): ");
            String patID = scanner.nextLine();
            newPatient.setID(Integer.parseInt(patID));
        }

        // 2)FULLNAME
        System.out.println("Write full name of patient\n");
        newPatient.setFullName(scanner.nextLine());


        // 3)SEX
        System.out.println("Sex (m/f):");
        String hold = "";

        while (!(stringEquals(hold, "m", true)) && !(stringEquals(hold, "f", true))) {
            hold = scanner.nextLine();
            System.out.println(hold);
            if (!(stringEquals(hold, "m", true)) && !(stringEquals(hold, "f", true))) {
                System.out.println("Wrong input, try again");
            }
        }
        if (stringEquals(hold, "m", true)) {
            newRecord.setSex(Enums.Sex.MALE);
        } else {
            newRecord.setSex(Enums.Sex.FEMALE);
        }

        // 4)DOB
        System.out.println("Date of birth(Format: dd.MM.yyyy.):");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        String date = scanner.nextLine();
        LocalDate theDate = LocalDate.parse(date, formatter);
        System.out.println("!!!!!!TEST TIME: "+theDate);
        newRecord.setDateOfBirth(theDate);

        //CONTACT DETAILS
        //5+6
        Address presentAddress = addressInput("Present");
        Address permanentAddress = addressInput("Permanent");

        // 7)PHONES+
        newContact = phoneInput();

        newContact.setCurrentAddress(presentAddress);
        newContact.setPermanantAddress(permanentAddress);
        newRecord.setContact(newContact);

        //NEXT OF KIN
        System.out.println("Full name of next of kin");
        nextOfKin.setFullName(scanner.nextLine());
        System.out.println("Relationship to outpatient");
        nextOfKin.setRelationshipToPatient(scanner.nextLine());

        Address nextOfKinAddress = addressInput("Next of kin");
        Contact nextOfKinContact = phoneInput();
        nextOfKinContact.setPermanantAddress(nextOfKinAddress);
        nextOfKinContact.setCurrentAddress(nextOfKinAddress);

        nextOfKin.setContact(nextOfKinContact);
        newRecord.setNextOfKin(nextOfKin);

        //PERSONAL AND PROFESSION DETAILS
        System.out.println("Marital status(m for married, n for not married)");
        String married = scanner.nextLine();
        newRecord.setMaritalStatus(stringEquals(married, "m", true));

        System.out.println("No. of dependents:");
        newRecord.setNumberOfDependents(Integer.parseInt(scanner.nextLine()));

        System.out.println("Height:");
        newRecord.setHeightCM(Integer.parseInt(scanner.nextLine()));

        System.out.println("Weight:");
        newRecord.setWeightKG(Integer.parseInt(scanner.nextLine()));

        System.out.println("Blood type - RH:");
        newRecord.setBloodTypeRH(scanner.nextLine());

        System.out.println("Occupation:");
        newRecord.setOccupation(scanner.nextLine());
        System.out.println("Gross Annual Income:");
        newRecord.setGrossAnualIncome(Double.parseDouble(scanner.nextLine()));


        //LIFESTYLE
        System.out.println("Vegetarian? (y/n)");
        String vegy = scanner.nextLine();
        newRecord.setVegetarian(stringEquals(vegy, "y", true));
        System.out.println("Average no. of cigarettes/day");
        newRecord.setCigarettesPerDay(Double.parseDouble(scanner.nextLine()));
        System.out.println("Average no. of drinks/day");
        newRecord.setDrinksPerDay(Double.parseDouble(scanner.nextLine()));

        System.out.println("Use stimulants(specify)");
        newRecord.setStimulantsUse(scanner.nextLine());

        System.out.println("Average no. of coffee-tea/day");
        newRecord.setCoffeeTeaPerDay(Double.parseDouble(scanner.nextLine()));

        System.out.println("Average no. of soft drinks/day");
        newRecord.setSoftDrinksPerDay(Double.parseDouble(scanner.nextLine()));


        System.out.println("Have regular meals(breakfast/lunch/dinner) choose 1-10");
        newRecord.setRegularMeals(scanner.nextShort());
        scanner.nextLine();
        System.out.println("Eat home food/outside predominantly (choose 1-10)");
        newRecord.setEatingHomeVsOut(scanner.nextShort());
        scanner.nextLine();


        //BASIC COMPLAINTS
        System.out.println("Statement of complaint:");
        newRecord.setComplaint(scanner.nextLine());
        System.out.println("History of previous treatment:");
        newRecord.setPreviousTreatment(scanner.nextLine());
        System.out.println("Physician/Hospital treated:/");
        newRecord.setPhysicianAndHospitalTreated(scanner.nextLine());

        //MEDICAL COMPLAINTS
        System.out.println("Diabetic:");
        newRecord.setDiabetic(scanner.nextLine());
        System.out.println("Hypertensive:");
        newRecord.setHypertensive(scanner.nextLine());
        System.out.println("Cardiac condition:");
        newRecord.setCardiacCondition(scanner.nextLine());
        System.out.println("Respiratory condition:");
        newRecord.setRespiratoryCondition(scanner.nextLine());
        System.out.println("Digestive condition:");
        newRecord.setDigestiveCondition(scanner.nextLine());
        System.out.println("Orthopedic condition:");
        newRecord.setOrthopedCondition(scanner.nextLine());
        System.out.println("Muscular condition:");
        newRecord.setMuscularCondition(scanner.nextLine());
        System.out.println("Neurological condition");
        newRecord.setNeurologicalCondition(scanner.nextLine());
        System.out.println("Known allergies:");
        newRecord.setKnownAllergies(scanner.nextLine());
        System.out.println("Known adverse reaction to specific drugs:");
        newRecord.setKnownAdverseReactionsToDrugs(scanner.nextLine());
        System.out.println("Major surgeries(history):");
        newRecord.setMajorSurgeries(scanner.nextLine());

        //ADDING DATENOW!!
        newRecord.setDateCreated(LocalDateTime.now());

        //LOAD DATA
        newPatient.setRecord(newRecord);
        newPatient.setDoctor(doctor);


        System.out.println("Save patient? (y/n)");
        String save = scanner.nextLine();

        if (save.equals("y")) {
            boolean success;
            if (hospital.patientExists(newPatient.getID())) {
                hospital.addOldPatient(newPatient);

            } else {
                // 9)ADD A DOCTOR
                ArrayList<Doctor> doctors = hospital.getAllDoctors();
                for (Doctor d : doctors) {
                    System.out.println("ID:" + d.getID() + " NAME:" + d.getFullName() + "\n");
                }
                System.out.println("Choose ID of wanted doctor (just a number)");
                String doc = scanner.nextLine();
                Integer docID = Integer.parseInt(doc);
                doctor = hospital.getDoctor(docID);
                newPatient.setDoctor(doctor);
                hospital.addNewPatient(newPatient);

            }
        }
        receptionMenu();
    }

    private static void doctorEditMenu() throws SQLException {
        ArrayList<Doctor> doctors = hospital.getAllDoctors();
        Scanner scanner = new Scanner(System.in);
        Doctor editDoctor = new Doctor();
        boolean confirm;
        System.out.println("----DOCTOR EDIT----");
        System.out.println("[1] Add doctor");
        System.out.println("[2] Update Doctor");
        System.out.println("[3] Remove doctor");
        System.out.println("[4] View all doctors");
        System.out.println("[0] RETURN");
        int pick = Integer.parseInt(scanner.nextLine());


        switch (pick) {
            case 1:
                System.out.println("ADDING DOCTOR");
                System.out.println("Full name:");
                editDoctor.setFullName(scanner.nextLine());
                confirm = confirm();
                if (confirm) {
                    hospital.addDoctor(editDoctor);
                }
                doctorEditMenu();
                break;
            case 2:
                System.out.println("UPDATING DOCTOR");
                printDoctors(doctors);
                System.out.println("CHOOSE DOCTOR BY ID(write)");
                editDoctor.setID(Integer.parseInt(scanner.nextLine()));
                System.out.println("Write new updated doctor's name");
                editDoctor.setFullName(scanner.nextLine());
                confirm = confirm();
                if (confirm) {
                    hospital.updateDoctor(editDoctor);
                }
                doctorEditMenu();
                break;
            case 3:
                System.out.println("DELETING DOCTOR");
                printDoctors(doctors);
                System.out.println("CHOOSE DOCTOR BY ID(write)");
                editDoctor.setID(Integer.parseInt(scanner.nextLine()));
                confirm = confirm();
                if (confirm) {
                    hospital.deleteDoctor(editDoctor.getID());
                }
                doctorEditMenu();
                break;
            case 4:
                printDoctors(doctors);
                pressEnterToContinue();
                doctorEditMenu();
            case 0:
                receptionMenu();
                break;
            default:
                receptionMenu();
                break;
        }


    }

    private static void paymentMenu(Patient patient) throws SQLException {
        Patient billPatient = patient;
        ArrayList<Bill> bills = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("----PAYMENT MENU----");
        if (billPatient == null) {
            System.out.println("Patient not selected");
        }
        else{
            System.out.format("Selected patient: %s, ID:%d",
                    billPatient.getFullName(), billPatient.getID());
            System.out.println();
        }
        System.out.println("[1] Select patient");
        System.out.println("[2] Paid bills");
        System.out.println("[3] Unpaid bills");
        System.out.println("[0] Return");

        int pick = Integer.parseInt(scanner.nextLine());

        switch (pick) {
            case 1:
                ArrayList<Patient> patients = hospital.getAllPatients();
                printPatients(patients);
                System.out.println("Choose patient by ID");
                billPatient = hospital.getPatient(Integer.parseInt(scanner.nextLine()));
                paymentMenu(billPatient);
                break;
            case 2:
                if (billPatient == null){
                    System.out.println("Patient not selected");
                    paymentMenu(null);
                    break;
                }else{
                    bills = hospital.getBillsByPatientID(billPatient.getID(), true);
                    System.out.println("Paid bills");
                    printBills(bills);
                    pressEnterToContinue();
                }
                paymentMenu(billPatient);
                break;
            case 3:
                if (billPatient == null){
                    System.out.println("Patient not selected");
                }else{
                    System.out.println("Unpaid bills");
                    bills = hospital.getBillsByPatientID(billPatient.getID(), false);
                    //ISPISATI BILLZ
                    printBills(bills);
                    System.out.println("Choose bill ID to pay");
                    int billID = Integer.parseInt(scanner.nextLine());
                    System.out.println("CASH[1]/CREDIT CARD[2]/CANCEL[0]");
                    int cashCredit = Integer.parseInt(scanner.nextLine());
                    switch (cashCredit) {
                        case 1:
                            hospital.payBill(billID, Enums.PAYMENT.CASH);
                            System.out.println("Cash payment confirmed");
                            break;
                        case 2:
                            hospital.payBill(billID, Enums.PAYMENT.CREDIT_CARD);
                            System.out.println("Credit card payment confirmed");
                            break;
                        case 0:
                            break;
                        default:
                            break;
                    }
                }
                paymentMenu(billPatient);
                break;
            case 0:
                receptionMenu();
                break;
            default:
                receptionMenu();
                break;

        }
    }

    private static void appointmentsManagement() throws SQLException {
        System.out.println("----APPOINTMENTS----");
        System.out.println("[1] Add an appointment");
        System.out.println("[0] Return");

        Scanner scanner = new Scanner(System.in);
        int pick = Integer.parseInt(scanner.nextLine());

        switch (pick) {
            case 0:
                receptionMenu();
                break;
            case 1:
                ArrayList<Patient> patients = hospital.getAllPatients();
                printPatients(patients);
                System.out.println("Choose patient by ID:");
                int chosen =Integer.parseInt(scanner.nextLine());
                Patient p = hospital.getPatient(chosen);
                appointmentCreator(p, p.getDoctor());

                appointmentsManagement();
                break;
            default:
                receptionMenu();
                return;
        }
    }

    public static void appointmentCreator(Patient p, Doctor d) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Date of appointment(Format: dd.MM.yyyy.):");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        String date = scanner.nextLine();
        LocalDate localDate = LocalDate.parse(date, formatter);
        System.out.println("Hour of appointment");
        int hour = Integer.parseInt(scanner.nextLine());
        LocalTime localTime = LocalTime.of(hour, 0);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        boolean myDoctorIsFree = hospital.isDoctorAvailable(d, localDateTime);
        if (myDoctorIsFree){
            hospital.setCalendarAppointment(p, d, localDateTime);
            System.out.println("Appointment confirmed, dr: " + d.getFullName());
            return;
        }else{
            ArrayList<Doctor> doctors = hospital.getAllDoctors();
            for (Doctor dr:doctors) {
                if (hospital.isDoctorAvailable(dr, localDateTime))
                {
                    hospital.setCalendarAppointment(p, dr, localDateTime);
                    System.out.println("Appointment confirmed, dr: "+ dr.getFullName());
                    return;
                }
            }
        }
        System.out.println("All doctors are busy");
    }


    //DOCTOR STUFF
    private static void doctorMenu() throws SQLException {
        if (doctorHolder== null){
            boolean loginSuccess = selectDoctor();
            if (!loginSuccess){
            return;
            }
        }
        if(patientHolder==null) {
            selectPatient();
        }
        System.out.println("[1] Select patient");
        if(patientHolder!=null){
            if(fullAppointment == null)
            fullAppointment = new FullAppointment(doctorHolder, patientHolder, LocalDateTime.now());
            System.out.println("[2] View patient records");
            System.out.println("[3] Add diagnosis");
            System.out.println("[4] Add test result");
            System.out.println("[5] Order test");
            System.out.println("[6] Add drug");
            System.out.println("[7] Add an appointment");
            System.out.println("[8] View appointment from history");
            System.out.println("[9] Save appointment");
            System.out.println("[10] Cancel appointment");
        }
        System.out.println("[0] Logout");


        int pick = Integer.parseInt(scanner.nextLine());

        switch (pick){
            case 0:
                fullAppointment = null;
                doctorHolder = null;
                patientHolder = null;
                break;
            case 1:
                selectPatient();
                doctorMenu();
                break;
            case 2:
                Record record = patientHolder.getRecord();
                System.out.println(record);
                doctorMenu();
                break;
            case 3:
                System.out.println("Write a diagnosis title");
                String diagnosisTitle = scanner.nextLine();
                System.out.println("Write a diagnosis");
                String diagnosisTxt = scanner.nextLine();
                boolean confirm = confirm();
                if (confirm) {
                    fullAppointment.addDiagnosis(diagnosisTitle, diagnosisTxt);
                }
                doctorMenu();
                break;
            case 4:
                Test newTest = new Test();
                System.out.println("Write test title");
                newTest.setTitle(scanner.nextLine());
                System.out.println("Write test result");
                newTest.setResult(scanner.nextLine());
                boolean confirmTestResult = confirm();
                if (confirmTestResult){
                    fullAppointment.addTestResult(newTest);
                }
                doctorMenu();
                break;
            case 5:
                Test orderTest = new Test();
                List<Test> tests = hospital.getAllTests();
                System.out.println("Name/Price(ID)");
                for (Test t: tests) {
                    System.out.println(t.getName() + "/" + t.getPrice() + "(" + t.getProductID() + ")");
                }
                orderTest.setProductID(testIDPicker());
                if(confirm()) {
                    fullAppointment.orderTest(orderTest);
                }
                doctorMenu();
                break;
            case 6:
                List<Drug> drugs = hospital.getAllDrugs();
                Drug addDrug = new Drug();
                for (Drug d:drugs) {
                    System.out.println("Name: " + d.getName() + ", ID: "+d.getID());
                }
                System.out.println("Select by ID");
                addDrug.setID(Integer.parseInt(scanner.nextLine()));
                System.out.println("Choose amount");
                addDrug.setAmount(Double.parseDouble(scanner.nextLine()));
                if(confirm()) {
                    fullAppointment.addDrug(addDrug);
                }
                doctorMenu();
                break;
            case 7:
                appointmentCreator(patientHolder, doctorHolder);
                doctorMenu();
                break;
            case 8:
                ArrayList<FullAppointment> patientAppointments = hospital.getAllAppointmentsByPatient(patientHolder);
                for (FullAppointment fa:patientAppointments) {
                    System.out.println("Date: " + fa.getDate()+ ", ID: " + fa.getAppointmentID());
                }
                System.out.println("Select by ID to view");
                int numOfAppointment = Integer.parseInt(scanner.nextLine());
                if (hospital.appointmentExists(numOfAppointment)){
                FullAppointment fullAppointment1 = hospital.getFullAppointment(numOfAppointment);
                System.out.println("Date: " + fullAppointment1.getDate().toString() +
                                    ", duration in min: " +  fullAppointment1.getDurationMinutes());
                System.out.println("Drugs(name/amount)");
                for (Drug drug:fullAppointment1.getDrugs()) {
                    System.out.println(drug.getName() +"/"+ drug.getAmount());
                }
                System.out.println("Ordered test(type)");
                for (Test orderedTest:fullAppointment1.getOrderedTests()) {
                    System.out.println(orderedTest.getName());
                }
                System.out.println("Test results");
                for (Test testResults:fullAppointment1.getTestResults()) {
                    System.out.println(testResults.getName());
                    System.out.println(testResults.getResult());
                }
                for (Diagnosis diagnosis:fullAppointment1.getDiagnosis()) {
                    System.out.println("");
                    System.out.println(diagnosis.getTitle());
                    System.out.println(diagnosis.getText());
                }
                    doctorMenu();
                }else{
                    System.out.println("ID not valid");
                    doctorMenu();
                }

                break;
            case 9:
                System.out.println("Duration of an appointment in minutes");
                fullAppointment.setDurationMinutes(Integer.parseInt(scanner.nextLine()));
                fullAppointment.setDate(LocalDateTime.now());
                hospital.addAppointment(fullAppointment);
                fullAppointment = null;
                doctorMenu();
                break;
            case 10:
                fullAppointment = null;
                doctorMenu();
                break;
        }
    }

    public static int testIDPicker(){
        ArrayList<Test> testTypes = new ArrayList<>();
        for (Test t:testTypes) {
            System.out.println("Test type: " + t.getName() + " ID: " + t.getProductID());
        }
        System.out.println("Select test type by ID");
        return Integer.parseInt(scanner.nextLine());
    }

    private static void selectPatient() throws SQLException {
        System.out.println("Search patient by full name");
        String fullName = scanner.nextLine();
        ArrayList<Patient> patients = hospital.searchPatientByName(fullName);
        for (Patient p:patients) {
            System.out.println(p.getFullName() + p.getID() + p.getRecord().getContact().getMobilePhone()
            + p.getRecord().getContact().getEmail());
        }
        System.out.println("Choose patient by ID:");

        int pick = Integer.parseInt(scanner.nextLine());
        if(hospital.patientExists(pick)) {
            patientHolder = hospital.getPatient(pick);
        }
        else{
            System.out.println("Patient not selected");

        }
    }

    private static boolean  selectDoctor() throws SQLException {
        System.out.println("Enter your ID:");
        int doctorPick = Integer.parseInt(scanner.nextLine());
        if(hospital.doctorExists(doctorPick)){
            doctorHolder = hospital.getDoctor(doctorPick);
            System.out.println("Login successful");
            return true;
        }
        else{
            System.out.println("Login failed");
            return false;

        }
    }


}


