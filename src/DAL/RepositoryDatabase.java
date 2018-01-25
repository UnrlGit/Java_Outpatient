package DAL;

import BL.*;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RepositoryDatabase implements IRepo {

    public static SQLServerDataSource getDataSource(){
        SQLServerDataSource dataSource = new SQLServerDataSource();
        dataSource.setServerName("localhost");
        dataSource.setDatabaseName("OutPatient");
        dataSource.setUser("sa");
        dataSource.setPassword("SQL");
        dataSource.setPortNumber(1433);
        return  dataSource;
    }

    //1)DOCTOR
    @Override
    public Doctor getDoctor(Integer docID) throws SQLException {
        Doctor dr = new Doctor();

        Connection conn = getDataSource().getConnection();
        CallableStatement stmt = conn.prepareCall("{call GetDoctor(?)}");
        stmt.setInt(1, docID);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()){
            dr.setID(resultSet.getInt("IDDoctor"));
            dr.setFullName(resultSet.getString("FullName"));
        }
        return  dr;
    }

    @Override
    public ArrayList<Doctor> getAllDoctors() throws SQLException {
        ArrayList<Doctor> doctors = new ArrayList<>();

        Connection connection = getDataSource().getConnection();
        CallableStatement statement = connection.prepareCall("{CALL GetAllDoctors}");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Doctor dr = new Doctor(
                    resultSet.getInt("IDDoctor"),
                    resultSet.getString("FullName"));

            doctors.add(dr);

        }
        connection.close();
        return doctors;
    }

    @Override
    public void addDoctor(Doctor doctor) throws SQLException {
        Connection con = getDataSource().getConnection();
        String sqlAddDoctor = "{call AddDoctor(?)}";
        CallableStatement stmt = con.prepareCall(sqlAddDoctor);
        stmt.setString(1, doctor.getFullName());
        stmt.execute();
        con.close();

    }

    @Override
    public void updateDoctor(Doctor updateDoctor) throws SQLException {
        Connection con = getDataSource().getConnection();
        CallableStatement statement = con.prepareCall("{call UpdateDoctor(?,?)}");
        statement.setInt(1, updateDoctor.getID());
        statement.setString(2, updateDoctor.getFullName());
        statement.execute();
        con.close();
    }

    @Override
    public void deleteDoctor(int idDoctor) throws SQLException {
        Connection conn = getDataSource().getConnection();
        CallableStatement stmt = conn.prepareCall("{call DeleteDoctor(?)}");
        stmt.setInt(1, idDoctor);
        stmt.execute();
        conn.close();
    }

    @Override
    public boolean isDoctorAvailable(Doctor d, LocalDateTime localDateTime) throws SQLException {
        Connection conn = getDataSource().getConnection();
        CallableStatement stmt = conn.prepareCall("{? = call IsDoctorAvailable(?,?)}");
        stmt.registerOutParameter(1, Types.INTEGER);
        stmt.setTimestamp(2, Timestamp.valueOf(localDateTime));
        stmt.setInt(3, d.getID());
        ResultSet resultSet = stmt.executeQuery();
        int exists=-1;
        while (resultSet.next())
        {
            exists = resultSet.getInt(1);
        }

        conn.close();

        //EXISTS HAS TO BE 1 IF EXISTS(AVAILABLE)
        if (exists == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean doctorExists(int doctorPick) throws SQLException {
        Connection conn = getDataSource().getConnection();
        CallableStatement stmt = conn.prepareCall("{? = call DoctorExists(?)}");
        stmt.registerOutParameter(1, Types.INTEGER);
        stmt.setInt(2, doctorPick);
        ResultSet resultSet = stmt.executeQuery();
        int exists=-1;
        while (resultSet.next())
        {
            exists = resultSet.getInt(1);
        }

        conn.close();

        //EXISTS HAS TO BE 1 IF EXISTS
        if (exists == 1){
            return true;
        }
        return false;
    }


    //2)PATIENTS
    @Override
    public void addPatient(Patient newPatient, boolean isNewPatient) throws SQLException {
        Connection connection = getDataSource().getConnection();
        CallableStatement statement;
        if(isNewPatient) {
            String sqlAddNewPatient = "{call AddNewPatient(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
                    ",?,?,?,?,?,?,?,?,?)}";//67?
            statement = connection.prepareCall(sqlAddNewPatient);
        }
        else{
            String sqlAddOldPatient="{call AddOldPatient(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
                    ",?,?,?,?,?,?,?,?,?)}";//67?
            statement = connection.prepareCall(sqlAddOldPatient);
        }


        statement.setString(1, newPatient.getFullName());
        if (newPatient.getRecord().getSex() == Enums.Sex.FEMALE) {
            statement.setInt(2, 0);
        }
        else{
            statement.setInt(2, 1);
        }
        Date bday  = Date.valueOf(newPatient.getRecord().getDateOfBirth());
        statement.setDate(3, bday);
        statement.setString(4, newPatient.getRecord().getComplaint());
        statement.setInt(5, newPatient.getDoctor().getID());
        if (newPatient.getRecord().isMaritalStatus() == false) {
            statement.setInt(6, 0);
        }
        else{
            statement.setInt(6, 1);
        }
        statement.setInt(7, newPatient.getRecord().getNumberOfDependents());
        statement.setDouble(8, newPatient.getRecord().getHeightCM());
        statement.setDouble(9, newPatient.getRecord().getWeightKG());
        statement.setString(10, newPatient.getRecord().getBloodTypeRH());
        statement.setString(11, newPatient.getRecord().getOccupation());
        statement.setDouble(12, newPatient.getRecord().getGrossAnualIncome());

        if (newPatient.getRecord().isVegetarian() == false) {
            statement.setInt(13, 0);
        }
        else{
            statement.setInt(13, 1);
        }
        if (newPatient.getRecord().isSmoker() == false) {
            statement.setInt(14, 0);
        }
        else{
            statement.setInt(14, 1);
        }
        statement.setDouble(15, newPatient.getRecord().getCigarettesPerDay());
        statement.setDouble(16, newPatient.getRecord().getDrinksPerDay());
        statement.setString(17, newPatient.getRecord().getStimulantsUse());
        statement.setDouble(18, newPatient.getRecord().getCoffeeTeaPerDay());
        statement.setDouble(19, newPatient.getRecord().getSoftDrinksPerDay());
        statement.setInt(20, newPatient.getRecord().getRegularMeals());
        statement.setInt(21, newPatient.getRecord().getEatingHomeVsOut());
        statement.setString(22, newPatient.getRecord().getPreviousTreatment());
        statement.setString(23, newPatient.getRecord().getPhysicianAndHospitalTreated());
        statement.setString(24, newPatient.getRecord().getDiabetic());
        statement.setString(25, newPatient.getRecord().getHypertensive());
        statement.setString(26, newPatient.getRecord().getCardiacCondition());
        statement.setString(27, newPatient.getRecord().getRespiratoryCondition());
        statement.setString(28, newPatient.getRecord().getDigestiveCondition());
        statement.setString(29, newPatient.getRecord().getOrthopedCondition());
        statement.setString(30, newPatient.getRecord().getMuscularCondition());
        statement.setString(31, newPatient.getRecord().getNeurologicalCondition());
        statement.setString(32, newPatient.getRecord().getKnownAllergies());
        statement.setString(33, newPatient.getRecord().getKnownAdverseReactionsToDrugs());
        statement.setString(34, newPatient.getRecord().getMajorSurgeries());
        statement.setInt(35, newPatient.getRecord().getContact().getCurrentAddress().getDoorsNumber());
        statement.setString(36, newPatient.getRecord().getContact().getCurrentAddress().getStreet());
        statement.setString(37, newPatient.getRecord().getContact().getCurrentAddress().getArea());
        statement.setString(38, newPatient.getRecord().getContact().getCurrentAddress().getCity());
        statement.setString(39, newPatient.getRecord().getContact().getCurrentAddress().getStates());
        statement.setInt(40, newPatient.getRecord().getContact().getCurrentAddress().getPincode());

        statement.setInt(41, newPatient.getRecord().getContact().getPermanantAddress().getDoorsNumber());
        statement.setString(42, newPatient.getRecord().getContact().getPermanantAddress().getStreet());
        statement.setString(43, newPatient.getRecord().getContact().getPermanantAddress().getArea());
        statement.setString(44, newPatient.getRecord().getContact().getPermanantAddress().getCity());
        statement.setString(45, newPatient.getRecord().getContact().getPermanantAddress().getStates());
        statement.setInt(46, newPatient.getRecord().getContact().getPermanantAddress().getPincode());

        statement.setString(47, newPatient.getRecord().getContact().getTelephoneWork());
        statement.setString(48, newPatient.getRecord().getContact().getTelephoneHome());
        statement.setString(49, newPatient.getRecord().getContact().getMobilePhone());
        statement.setString(50, newPatient.getRecord().getContact().getPager());
        statement.setString(51, newPatient.getRecord().getContact().getFax());
        statement.setString(52, newPatient.getRecord().getContact().getEmail());
        //NEXT OF KIN
        statement.setString(53, newPatient.getRecord().getNextOfKin().getFullName());
        statement.setString(54, newPatient.getRecord().getNextOfKin().getRelationshipToPatient());
        statement.setInt(55, newPatient.getRecord().getNextOfKin().getContact().getPermanantAddress().getDoorsNumber());
        statement.setString(56, newPatient.getRecord().getNextOfKin().getContact().getPermanantAddress().getStreet());
        statement.setString(57, newPatient.getRecord().getNextOfKin().getContact().getPermanantAddress().getArea());
        statement.setString(58, newPatient.getRecord().getNextOfKin().getContact().getPermanantAddress().getCity());
        statement.setString(59, newPatient.getRecord().getNextOfKin().getContact().getPermanantAddress().getStates());
        statement.setInt(60, newPatient.getRecord().getNextOfKin().getContact().getPermanantAddress().getPincode());

        statement.setString(61, newPatient.getRecord().getNextOfKin().getContact().getTelephoneWork());
        statement.setString(62, newPatient.getRecord().getNextOfKin().getContact().getTelephoneHome());
        statement.setString(63, newPatient.getRecord().getNextOfKin().getContact().getMobilePhone());
        statement.setString(64, newPatient.getRecord().getNextOfKin().getContact().getPager());
        statement.setString(65, newPatient.getRecord().getNextOfKin().getContact().getFax());
        statement.setString(66, newPatient.getRecord().getNextOfKin().getContact().getEmail());

        if (!isNewPatient){
            statement.setInt(67,newPatient.getID());
        }
        if (isNewPatient){
            statement.setTimestamp(67, Timestamp.valueOf(newPatient.getRecord().getDateCreated()));
        }



        statement.execute();
        connection.close();


    }

    @Override
    public boolean patientExists(int patientID) throws SQLException {
        Connection conn = getDataSource().getConnection();
        CallableStatement stmt = conn.prepareCall("{? = call PatientExists(?)}");
        stmt.registerOutParameter(1, Types.INTEGER);
        stmt.setInt(2, patientID);
        ResultSet resultSet = stmt.executeQuery();
        int exists=-1;
        while (resultSet.next())
        {
            exists = resultSet.getInt(1);
        }

        conn.close();

        //EXISTS HAS TO BE 1 IF EXISTS
        if (exists == 1){
            return true;
        }
        return false;
    }

    @Override
    public Patient getPatient(int patientID) throws SQLException {
        Patient patient = new Patient();
        Record patientRecord = new Record();
        Contact patientContact = new Contact();
        Address patientCurrentAddress = new Address();
        Address patientPermanentAdress = new Address();
        NextOfKin nok = new NextOfKin();
        Address nokAddress = new Address();
        Contact nokContact = new Contact();

        Connection conn = getDataSource().getConnection();
        CallableStatement stmt = conn.prepareCall("{call GetPatient(?)}");
        stmt.setInt(1, patientID);
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()){
            patient.setID(resultSet.getInt("IDPatient"));
            patient.setFullName(resultSet.getString("FullName"));
            patient.setDoctor(getDoctor(resultSet.getInt("DoctorID")));
            int sex = resultSet.getInt("Sex");
            if(sex == 0){
                patientRecord.setSex(Enums.Sex.FEMALE);
            }
            else{
                patientRecord.setSex(Enums.Sex.MALE);
            }
            Date date = resultSet.getDate("DateOfBirth");
            patientRecord.setDateOfBirth(date.toLocalDate());
            patientRecord.setComplaint(resultSet.getString("Complaint"));
            int married = resultSet.getInt("MaritalStatus");
            if(married == 0){
                patientRecord.setMaritalStatus(false);
            }
            else{
                patientRecord.setMaritalStatus(true);
            }
            patientRecord.setNumberOfDependents(resultSet.getInt("NumberOfDependents"));
            patientRecord.setHeightCM(resultSet.getInt("HeightCM"));
            patientRecord.setWeightKG(resultSet.getDouble("WeightKG"));
            patientRecord.setBloodTypeRH(resultSet.getString("BloodTypeRH"));
            patientRecord.setOccupation(resultSet.getString("Occupation"));
            patientRecord.setGrossAnualIncome(resultSet.getDouble("GrossAnualIncome"));
            patientRecord.setVegetarian(resultSet.getBoolean("Vegetarian"));
            patientRecord.setSmoker(resultSet.getBoolean("Smoker"));
            patientRecord.setCigarettesPerDay(resultSet.getDouble("CigarettesPerDay"));
            patientRecord.setDrinksPerDay(resultSet.getDouble("DrinksPerDay"));
            patientRecord.setStimulantsUse(resultSet.getString("StimulantsUse"));
            patientRecord.setCoffeeTeaPerDay(resultSet.getDouble("CoffeeTeaPerDay"));
            patientRecord.setSoftDrinksPerDay(resultSet.getDouble("SoftDrinksPerDay"));
            patientRecord.setRegularMeals(resultSet.getShort("RegularMeals"));
            patientRecord.setEatingHomeVsOut(resultSet.getShort("EatingHomeVsOutSide"));
            patientRecord.setPreviousTreatment(resultSet.getString("PreviousTreatment"));
            patientRecord.setPhysicianAndHospitalTreated(resultSet.getString("PhysicianAndHospitalTreated"));
            patientRecord.setDiabetic(resultSet.getString("Diabetic"));
            patientRecord.setHypertensive(resultSet.getString("Hypertensive"));
            patientRecord.setCardiacCondition(resultSet.getString("CardiacCondition"));
            patientRecord.setRespiratoryCondition(resultSet.getString("RespiratoryCondition"));
            patientRecord.setDigestiveCondition(resultSet.getString("DigestiveCondition"));
            patientRecord.setOrthopedCondition(resultSet.getString("OrthopedicCondition"));
            patientRecord.setMuscularCondition(resultSet.getString("MuscularCondition"));
            patientRecord.setNeurologicalCondition(resultSet.getString("NeurologicalCondition"));
            patientRecord.setKnownAllergies(resultSet.getString("KnownAllergies"));
            patientRecord.setKnownAdverseReactionsToDrugs(resultSet.getString("KnownAdverseReactionToDrugs"));
            patientRecord.setMajorSurgeries(resultSet.getString("MajorSurgeries"));
            Timestamp dateAdded = resultSet.getTimestamp("DateAdded");
            patientRecord.setDateCreated(dateAdded.toLocalDateTime());

            patientCurrentAddress.setDoorsNumber(resultSet.getInt("PresentDoorNo"));
            patientCurrentAddress.setStreet(resultSet.getString("PresentStreet"));
            patientCurrentAddress.setArea(resultSet.getString("PresentArea"));
            patientCurrentAddress.setCity(resultSet.getString("PresentCity"));
            patientCurrentAddress.setStates(resultSet.getString("PresentState"));
            patientCurrentAddress.setPincode(resultSet.getInt("PresentPincode"));

            patientPermanentAdress.setDoorsNumber(resultSet.getInt("PermanentDoorNo"));
            patientPermanentAdress.setStreet(resultSet.getString("PermanentStreet"));
            patientPermanentAdress.setArea(resultSet.getString("PermanentArea"));
            patientPermanentAdress.setCity(resultSet.getString("PermanentCity"));
            patientPermanentAdress.setStates(resultSet.getString("PermanentState"));
            patientPermanentAdress.setPincode(resultSet.getInt("PermanentPincode"));

            patientContact.setTelephoneWork(resultSet.getString("TelephoneWork"));
            patientContact.setTelephoneHome(resultSet.getString("TelephoneHome"));
            patientContact.setMobilePhone(resultSet.getString("Mobile"));
            patientContact.setPager(resultSet.getString("Pager"));
            patientContact.setFax(resultSet.getString("Fax"));
            patientContact.setEmail(resultSet.getString("Email"));
            //SETTING ADDRESSES INTO CONTACT AND CONTACT INTO RECORD
            patientContact.setCurrentAddress(patientCurrentAddress);
            patientContact.setPermanantAddress(patientPermanentAdress);
            patientRecord.setContact(patientContact);

            //NEXT OF KIN
            nok.setFullName(resultSet.getString("NOKFullName"));
            nok.setRelationshipToPatient("NOKRelationshipToPatient");


            nokAddress.setDoorsNumber(resultSet.getInt("NOKDoorNo"));
            nokAddress.setStreet(resultSet.getString("NOKStreet"));
            nokAddress.setArea(resultSet.getString("NOKArea"));
            nokAddress.setCity(resultSet.getString("NOKCity"));
            nokAddress.setStates(resultSet.getString("NOKStates"));
            nokAddress.setPincode(resultSet.getInt("NOKPincode"));

            nokContact.setTelephoneWork(resultSet.getString("NOKTelephoneWork"));
            nokContact.setTelephoneHome(resultSet.getString("NOKTelephoneHome"));
            nokContact.setMobilePhone(resultSet.getString("NOKMobile"));
            nokContact.setPager(resultSet.getString("NOKPager"));
            nokContact.setFax(resultSet.getString("NOKFax"));
            nokContact.setEmail(resultSet.getString("NOKEmail"));
            //FILLING NOK
            nokContact.setPermanantAddress(nokAddress);
            nokContact.setCurrentAddress(nokAddress);
            nok.setContact(nokContact);
            //SETTING NOK INTO PATIENT RECORD AND ADDING RECORD TO PATIENT
            patientRecord.setNextOfKin(nok);
            patient.setRecord(patientRecord);


        }


        conn.close();
        return  patient;
    }

    @Override
    public ArrayList<Patient> getAllPatients(String nameFilter) throws SQLException {
        ArrayList<Patient> patientsList = new ArrayList<>();
        String sqlCall;
        if(nameFilter==null){
            sqlCall = "{call GetAllPatients}";
        }
        else{
            sqlCall = "{call SearchPatientsByName(?)}";
        }


        Connection conn = getDataSource().getConnection();
        CallableStatement stmt = conn.prepareCall(sqlCall);
        if (nameFilter!=null){
            stmt.setString(1,nameFilter);
        }
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()){
            Patient patient = new Patient();
            Record patientRecord = new Record();
            Contact patientContact = new Contact();
            Address patientCurrentAddress = new Address();
            Address patientPermanentAdress = new Address();
            NextOfKin nok = new NextOfKin();
            Address nokAddress = new Address();
            Contact nokContact = new Contact();
            patient.setID(resultSet.getInt("IDPatient"));
            patient.setFullName(resultSet.getString("FullName"));
            patient.setDoctor(getDoctor(resultSet.getInt("DoctorID")));
            int sex = resultSet.getInt("Sex");
            if(sex == 0){
                patientRecord.setSex(Enums.Sex.FEMALE);
            }
            else{
                patientRecord.setSex(Enums.Sex.MALE);
            }
            Date date = resultSet.getDate("DateOfBirth");
            patientRecord.setDateOfBirth(date.toLocalDate());
            patientRecord.setComplaint(resultSet.getString("Complaint"));
            int married = resultSet.getInt("MaritalStatus");
            if(married == 0){
                patientRecord.setMaritalStatus(false);
            }
            else{
                patientRecord.setMaritalStatus(true);
            }
            patientRecord.setNumberOfDependents(resultSet.getInt("NumberOfDependents"));
            patientRecord.setHeightCM(resultSet.getInt("HeightCM"));
            patientRecord.setWeightKG(resultSet.getDouble("WeightKG"));
            patientRecord.setBloodTypeRH(resultSet.getString("BloodTypeRH"));
            patientRecord.setOccupation(resultSet.getString("Occupation"));
            patientRecord.setGrossAnualIncome(resultSet.getDouble("GrossAnualIncome"));
            patientRecord.setVegetarian(resultSet.getBoolean("Vegetarian"));
            patientRecord.setSmoker(resultSet.getBoolean("Smoker"));
            patientRecord.setCigarettesPerDay(resultSet.getDouble("CigarettesPerDay"));
            patientRecord.setDrinksPerDay(resultSet.getDouble("DrinksPerDay"));
            patientRecord.setStimulantsUse(resultSet.getString("StimulantsUse"));
            patientRecord.setCoffeeTeaPerDay(resultSet.getDouble("CoffeeTeaPerDay"));
            patientRecord.setSoftDrinksPerDay(resultSet.getDouble("SoftDrinksPerDay"));
            patientRecord.setRegularMeals(resultSet.getShort("RegularMeals"));
            patientRecord.setEatingHomeVsOut(resultSet.getShort("EatingHomeVsOutSide"));
            patientRecord.setPreviousTreatment(resultSet.getString("PreviousTreatment"));
            patientRecord.setPhysicianAndHospitalTreated(resultSet.getString("PhysicianAndHospitalTreated"));
            patientRecord.setDiabetic(resultSet.getString("Diabetic"));
            patientRecord.setHypertensive(resultSet.getString("Hypertensive"));
            patientRecord.setCardiacCondition(resultSet.getString("CardiacCondition"));
            patientRecord.setRespiratoryCondition(resultSet.getString("RespiratoryCondition"));
            patientRecord.setDigestiveCondition(resultSet.getString("DigestiveCondition"));
            patientRecord.setOrthopedCondition(resultSet.getString("OrthopedicCondition"));
            patientRecord.setMuscularCondition(resultSet.getString("MuscularCondition"));
            patientRecord.setNeurologicalCondition(resultSet.getString("NeurologicalCondition"));
            patientRecord.setKnownAllergies(resultSet.getString("KnownAllergies"));
            patientRecord.setKnownAdverseReactionsToDrugs(resultSet.getString("KnownAdverseReactionToDrugs"));
            patientRecord.setMajorSurgeries(resultSet.getString("MajorSurgeries"));
            Timestamp dateAdded = resultSet.getTimestamp("DateAdded");
            //Date date1 = new Date(dateAdded.getTime());
            patientRecord.setDateCreated(dateAdded.toLocalDateTime());

            patientCurrentAddress.setDoorsNumber(resultSet.getInt("PresentDoorNo"));
            patientCurrentAddress.setStreet(resultSet.getString("PresentStreet"));
            patientCurrentAddress.setArea(resultSet.getString("PresentArea"));
            patientCurrentAddress.setCity(resultSet.getString("PresentCity"));
            patientCurrentAddress.setStates(resultSet.getString("PresentState"));
            patientCurrentAddress.setPincode(resultSet.getInt("PresentPincode"));

            patientPermanentAdress.setDoorsNumber(resultSet.getInt("PermanentDoorNo"));
            patientPermanentAdress.setStreet(resultSet.getString("PermanentStreet"));
            patientPermanentAdress.setArea(resultSet.getString("PermanentArea"));
            patientPermanentAdress.setCity(resultSet.getString("PermanentCity"));
            patientPermanentAdress.setStates(resultSet.getString("PermanentState"));
            patientPermanentAdress.setPincode(resultSet.getInt("PermanentPincode"));

            patientContact.setTelephoneWork(resultSet.getString("TelephoneWork"));
            patientContact.setTelephoneHome(resultSet.getString("TelephoneHome"));
            patientContact.setMobilePhone(resultSet.getString("Mobile"));
            patientContact.setPager(resultSet.getString("Pager"));
            patientContact.setFax(resultSet.getString("Fax"));
            patientContact.setEmail(resultSet.getString("Email"));
            //SETTING ADDRESSES INTO CONTACT AND CONTACT INTO RECORD
            patientContact.setCurrentAddress(patientCurrentAddress);
            patientContact.setPermanantAddress(patientPermanentAdress);
            patientRecord.setContact(patientContact);

            //NEXT OF KIN
            nok.setFullName(resultSet.getString("NOKFullName"));
            nok.setRelationshipToPatient("NOKRelationshipToPatient");


            nokAddress.setDoorsNumber(resultSet.getInt("NOKDoorNo"));
            nokAddress.setStreet(resultSet.getString("NOKStreet"));
            nokAddress.setArea(resultSet.getString("NOKArea"));
            nokAddress.setCity(resultSet.getString("NOKCity"));
            nokAddress.setStates(resultSet.getString("NOKStates"));
            nokAddress.setPincode(resultSet.getInt("NOKPincode"));

            nokContact.setTelephoneWork(resultSet.getString("NOKTelephoneWork"));
            nokContact.setTelephoneHome(resultSet.getString("NOKTelephoneHome"));
            nokContact.setMobilePhone(resultSet.getString("NOKMobile"));
            nokContact.setPager(resultSet.getString("NOKPager"));
            nokContact.setFax(resultSet.getString("NOKFax"));
            nokContact.setEmail(resultSet.getString("NOKEmail"));
            //FILLING NOK
            nokContact.setPermanantAddress(nokAddress);
            nokContact.setCurrentAddress(nokAddress);
            nok.setContact(nokContact);
            //SETTING NOK INTO PATIENT RECORD AND ADDING RECORD TO PATIENT
            patientRecord.setNextOfKin(nok);
            patient.setRecord(patientRecord);

            patientsList.add(patient);
        }


        conn.close();
        return  patientsList;
    }


    @Override
    public void setCalendarAppointment(Patient p, Doctor d, LocalDateTime localDateTime) throws SQLException {
        Connection con = getDataSource().getConnection();
        String sqlCalendarApp = "{call AddCalendarAppointment(?,?,?)}";
        CallableStatement stmt = con.prepareCall(sqlCalendarApp);
        stmt.setTimestamp(1, Timestamp.valueOf(localDateTime));
        stmt.setInt(2, d.getID());
        stmt.setInt(3, p.getID());
        stmt.execute();
        con.close();
    }

    //APPOINTMENTS
    @Override
    public ArrayList<FullAppointment> getAllAppointmentsByPatient(Patient patient) throws SQLException {
        ArrayList<FullAppointment> fullAppointments = new ArrayList<>();

        Connection connection = getDataSource().getConnection();
        CallableStatement statement = connection.prepareCall("{CALL GetAppointmentsByPatient(?)}");
        statement.setInt(1, patient.getID());
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            FullAppointment fa = new FullAppointment();
            fa.setDate(resultSet.getTimestamp("DateOf").toLocalDateTime());
            fa.setAppointmentID(resultSet.getInt("IDAppointment"));
            Doctor dr = new Doctor();
            dr.setID(resultSet.getInt("DoctorID"));
            fa.setDoctor(dr);
            fullAppointments.add(fa);
        }
        connection.close();
        return fullAppointments;
    }

    @Override
    public FullAppointment getFullAppointment(int appointmentID) throws SQLException {
        FullAppointment fullAppointment = new FullAppointment();
        fullAppointment.setAppointmentID(appointmentID);


        //DOCTOR, PATIENT, DATE
        Integer drID = null;
        Integer patientID = null;
        LocalDateTime localDateTime = null;

        Connection connection = getDataSource().getConnection();
        CallableStatement statement = connection.prepareCall("{CALL GetAppointmentByID(?)}");
        statement.setInt(1, appointmentID);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            localDateTime = resultSet.getTimestamp("DateOf").toLocalDateTime();
            drID = resultSet.getInt("DoctorID");
            patientID = resultSet.getInt("PatientID");
        }
        connection.close();
        fullAppointment.setDoctor(getDoctor(drID));
        fullAppointment.setPatient(getPatient(patientID));
        fullAppointment.setTimeOfAppointment(localDateTime);

        //DIAGNOSIS LIST
        ArrayList<Diagnosis> diagnoses = getDiagnosesByID(appointmentID);
        fullAppointment.setDiagnosis(diagnoses);

        //TEST RESULTS
        ArrayList<Test> testResults = getTestResultsByAppID(appointmentID);
        fullAppointment.setTestResults(testResults);

        //ORDERED TESTS
        ArrayList<Test> orderedTests = getOrderedTestsByAppID(appointmentID);
        fullAppointment.setOrderedTests(orderedTests);

        //DRUGS
        ArrayList<Drug> drugs = getDrugsByApptID(appointmentID);
        fullAppointment.setDrugs(drugs);        
        
        //APPOINTMENT TIME
        Integer time = getAptDurationByID(appointmentID);
        if (time == null) {
            fullAppointment.setDurationMinutes(0);
        }else{
        fullAppointment.setDurationMinutes(time);
        }

        return fullAppointment;
    }

    private Integer getAptDurationByID(int appointmentID) throws SQLException {
        Connection connection = getDataSource().getConnection();
        Integer duration = null;
        CallableStatement statement = connection.prepareCall("{CALL getAptDurationByID(?)}");
        statement.setInt(1, appointmentID);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            Integer find = rs.getInt("Amount");
            duration = find;
        }
        connection.close();
        return duration;
    }

    private ArrayList<Drug> getDrugsByApptID(int appointmentID) throws SQLException {
        Connection connection = getDataSource().getConnection();
        ArrayList<Drug> drugs = new ArrayList<>();
        String sqlGetDrugsByAptID = "{CALL GetDrugsByAptID(?)}";
        CallableStatement statement = connection.prepareCall(sqlGetDrugsByAptID);
        statement.setInt(1, appointmentID);
        ResultSet resultSet4 = statement.executeQuery();
        while (resultSet4.next()){
            Drug drug = new Drug();
            drug.setAmount(resultSet4.getDouble("Amount"));
            drug.setName(resultSet4.getString("ProductName"));
            drug.setID(resultSet4.getInt("ProductID"));
            drug.setPrice(resultSet4.getDouble("Price"));
            drugs.add(drug);
        }
        connection.close();
        return  drugs;
    }

    private ArrayList<Test> getOrderedTestsByAppID(int appointmentID) throws SQLException {
        Connection connection = getDataSource().getConnection();
        ArrayList<Test> orderedTests = new ArrayList<>();
        String sqlGetOrderedTestsByAppID = "{CALL GetOrderedTestsByAppID(?)}";
        CallableStatement statement = connection.prepareCall(sqlGetOrderedTestsByAppID);
        statement.setInt(1, appointmentID);
        ResultSet resultSet3 = statement.executeQuery();
        while (resultSet3.next()){
            Test ordered = new Test();
            ordered.setName(resultSet3.getString("ProductName"));
            ordered.setProductID(resultSet3.getInt("ProductID"));
            ordered.setPrice(resultSet3.getDouble("Price"));
            orderedTests.add(ordered);
        }
        connection.close();
        return  orderedTests;
    }

    private ArrayList<Test> getTestResultsByAppID(int appointmentID) throws SQLException {
        Connection connection = getDataSource().getConnection();
        ArrayList<Test> testResults = new ArrayList<>();
        String sqlGetTestResultsByAppID = "{CALL GetTestResultsByAppID(?)}";
        CallableStatement  statement = connection.prepareCall(sqlGetTestResultsByAppID);
        statement.setInt(1, appointmentID);
        ResultSet resultSet2 = statement.executeQuery();
        while (resultSet2.next()){
            Test testResult = new Test();
            testResult.setTitle(resultSet2.getString("Title"));
            testResult.setResult(resultSet2.getString("TextData"));
            testResults.add(testResult);
        }
        connection.close();
        return testResults;
    }

    private ArrayList<Diagnosis> getDiagnosesByID(int appointmentID) throws SQLException {
        Connection connection = getDataSource().getConnection();
        ArrayList<Diagnosis> diagnoses = new ArrayList<>();
        String sqlGetDiagnosisByAppID = "{CALL GetDiagnosisByAppID(?)}";
        CallableStatement statement = connection.prepareCall(sqlGetDiagnosisByAppID);
        statement.setInt(1, appointmentID);
        ResultSet resultSet1 = statement.executeQuery();
        while (resultSet1.next()){
            Diagnosis d = new Diagnosis();
            d.setTitle(resultSet1.getString("Title"));
            d.setText(resultSet1.getString("TextData"));
            diagnoses.add(d);
        }
        connection.close();
        return diagnoses;
    }

    @Override
    public boolean appointmentExists(int appointmentID) throws SQLException {
        Connection conn = getDataSource().getConnection();
        CallableStatement stmt = conn.prepareCall("{? = call AppointmentExists(?)}");
        stmt.registerOutParameter(1, Types.INTEGER);
        stmt.setInt(2, appointmentID);
        ResultSet resultSet = stmt.executeQuery();
        int exists=-1;
        while (resultSet.next())
        {
            exists = resultSet.getInt(1);
        }

        conn.close();

        //EXISTS HAS TO BE 1 IF EXISTS
        if (exists == 1){
            return true;
        }
        return false;
    }

    @Override
    public void AddAppointment(FullAppointment fullAppointment) throws SQLException {
        Connection con = getDataSource().getConnection();
        String sqlAddAppointment = "{call AddFullAppointment(?,?,?)}";
        CallableStatement cs = con.prepareCall(sqlAddAppointment);
        cs.setInt(1, fullAppointment.getPatient().getID());
        cs.setInt(2, fullAppointment.getDoctor().getID());
        cs.setTimestamp(3, Timestamp.valueOf(fullAppointment.getDate()));

        Integer appointmentID = null;
        ResultSet rs = cs.executeQuery();
        if(rs.next()) {
            appointmentID = rs.getInt("AppointmentID");
        }
        con.close();

        if(fullAppointment.getDiagnosis().size() > 0){
            for (Diagnosis d:fullAppointment.getDiagnosis()) {
                addDiagnosis(d, appointmentID);
            }
        }
        if(fullAppointment.getTestResults().size() >0){
            for (Test testResult:fullAppointment.getTestResults()) {
                addTestResult(testResult, appointmentID);
            }
        }
        if(fullAppointment.getDrugs().size()>0){
            for (Drug drug:fullAppointment.getDrugs()) {
                addDrug(drug, appointmentID);
            }
        }
        if(fullAppointment.getOrderedTests().size()>0){
            for (Test ordered:fullAppointment.getOrderedTests()) {
                orderTest(ordered, appointmentID);
            }
        }
        addTime(fullAppointment.getDurationMinutes(), appointmentID);
    }

    private void addTime(int durationMinutes, Integer appointmentID) throws SQLException {
        Connection con = getDataSource().getConnection();
        String sqlAddTime = "{call AddTime(?,?)}";
        CallableStatement stmt = con.prepareCall(sqlAddTime);
        stmt.setInt(1, durationMinutes);
        stmt.setInt(2, appointmentID);
        stmt.execute();
        con.close();
    }

    private void orderTest(Test ordered, Integer appointmentID) throws SQLException {
        Connection con = getDataSource().getConnection();
        String sqlOrderTest = "{call OrderTest(?,?)}";
        CallableStatement stmt = con.prepareCall(sqlOrderTest);
        stmt.setInt(1, ordered.getProductID());
        stmt.setInt(2, appointmentID);
        stmt.execute();
        con.close();
    }

    private void addDrug(Drug drug, Integer appointmentID) throws SQLException {
        Connection con = getDataSource().getConnection();
        String sqlAddDrug = "{call AddDrug(?,?,?)}";
        CallableStatement stmt = con.prepareCall(sqlAddDrug);
        stmt.setInt(1, drug.getID());
        stmt.setDouble(2, drug.getAmount());
        stmt.setInt(3, appointmentID);
        stmt.execute();
        con.close();
    }

    private void addTestResult(Test testResult, Integer appointmentID) throws SQLException {
        Connection con = getDataSource().getConnection();
        String sqlAddTestResult = "{call AddTestResult(?,?,?)}";
        CallableStatement stmt = con.prepareCall(sqlAddTestResult);
        stmt.setString(1, testResult.getTitle());
        stmt.setString(2, testResult.getResult());
        stmt.setInt(3, appointmentID);
        stmt.execute();
        con.close();
    }

    private void addDiagnosis(Diagnosis d, Integer appointmentID) throws SQLException {
        Connection con = getDataSource().getConnection();
        String sqlAddDiagnosis = "{call AddDiagnosis(?,?,?)}";
        CallableStatement stmt = con.prepareCall(sqlAddDiagnosis);
        stmt.setString(1, d.getTitle());
        stmt.setString(2, d.getText());
        stmt.setInt(3, appointmentID);
        stmt.execute();
        con.close();
    }

    @Override
    public ArrayList<Drug> getAllDrugs() throws SQLException {
        ArrayList<Drug> drugs = new ArrayList<>();

        Connection connection = getDataSource().getConnection();
        CallableStatement statement = connection.prepareCall("{CALL GetAllDrugs}");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Drug dr = new Drug();
            dr.setID(resultSet.getInt("IDProduct"));
            dr.setName( resultSet.getString("ProductName"));
            dr.setPrice(resultSet.getDouble("Price"));

            drugs.add(dr);
        }
        connection.close();
        return drugs;
    }

    @Override
    public ArrayList<Test> getAllTests() throws SQLException {
        ArrayList<Test> tests = new ArrayList<>();

        Connection connection = getDataSource().getConnection();
        CallableStatement statement = connection.prepareCall("{CALL GetAllTests}");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Test test = new Test();
            test.setProductID(resultSet.getInt("IDProduct"));
            test.setName( resultSet.getString("ProductName"));
            test.setPrice(resultSet.getDouble("Price"));

            tests.add(test);
        }
        connection.close();
        return tests;
    }

    @Override
    public void payBill(int billID, Enums.PAYMENT typeOfPayment) throws SQLException {
        Connection connection = getDataSource().getConnection();
        CallableStatement statement = connection.prepareCall("{CALL PayBill(?,?)}");
        statement.setInt(1, billID);
        int paymentType;
        if (typeOfPayment == Enums.PAYMENT.CASH){
            paymentType = 1;
        }
        else{
            paymentType= 0;
        }
        statement.setInt(2, paymentType);
        statement.execute();
        connection.close();
    }

    @Override
    public ArrayList<Bill> getBillsByPatientID(int patientID, boolean paid) throws SQLException {
        Patient pt = new Patient();
        pt.setID(patientID);
        ArrayList<FullAppointment> appointments = getAllAppointmentsByPatient(pt);

        ArrayList<Bill> allBills = new ArrayList<>();
        for (FullAppointment apt:appointments) {
            Bill bill = new Bill();
            bill.setAppointmentID(apt.getAppointmentID());
            bill.setDate(apt.getDate());
            bill.setDoctorID(apt.getDoctor().getID());
            if (paid){
                if (billPaid(apt.getAppointmentID())){
                    bill.setItems(getBillItemsByAptID(apt.getAppointmentID()));
                    allBills.add(bill);
                }
            }
            else if (paid == false){
                if (!billPaid(apt.getAppointmentID())){
                    bill.setItems(getBillItemsByAptID(apt.getAppointmentID()));
                    allBills.add(bill);
                }
            }
        }

        return allBills;
    }

    public ArrayList<BillItem> getBillItemsByAptID(int appointmentID) throws SQLException {
        Connection connection = getDataSource().getConnection();
        CallableStatement statement = connection.prepareCall("{CALL GetBillByAppointmentID(?)}");
        statement.setInt(1,appointmentID);
        ResultSet resultSet = statement.executeQuery();

        ArrayList<BillItem> billItems = new ArrayList<>();
        while(resultSet.next()){
            BillItem billItem = new BillItem();
            billItem.setAmount(resultSet.getDouble("Amount"));
            billItem.setItemName(resultSet.getString("ProductName"));
            billItem.setPrice(resultSet.getDouble("Price"));

            billItems.add(billItem);
        }
        connection.close();
        return billItems;
    }

    @Override
    public boolean billPaid(int appointmentID) throws SQLException {
        Connection conn = getDataSource().getConnection();
        CallableStatement stmt = conn.prepareCall("{? = call BillPaid(?)}");
        stmt.registerOutParameter(1, Types.INTEGER);
        stmt.setInt(2, appointmentID);
        ResultSet resultSet = stmt.executeQuery();
        int exists=-1;
        while (resultSet.next())
        {
            exists = resultSet.getInt(1);
        }

        conn.close();

        //EXISTS HAS TO BE 1 IF EXISTS
        if (exists == 1){
            return true;
        }
        return false;
    }
    @Override
    public boolean isNotBookedByPatient(Patient d, LocalDateTime localDateTime) throws SQLException {
        Connection conn = getDataSource().getConnection();
        CallableStatement stmt = conn.prepareCall("{? = call IsNotBookedByPatient(?,?)}");
        stmt.registerOutParameter(1, Types.INTEGER);
        stmt.setTimestamp(2, Timestamp.valueOf(localDateTime));
        stmt.setInt(3, d.getID());
        ResultSet resultSet = stmt.executeQuery();
        int exists=-1;
        while (resultSet.next())
        {
            exists = resultSet.getInt(1);
        }

        conn.close();

        //EXISTS HAS TO BE 1 IF EXISTS(AVAILABLE)
        if (exists == 1){
            return true;
        }
        return false;
    }
    
    
    //REPORTS
    @Override
    public ArrayList<Report> getDoctorReport(int minusDays) throws SQLException {
        ArrayList<Report> reports = new ArrayList<>();

        Connection connection = getDataSource().getConnection();
        CallableStatement statement = connection.prepareCall("{CALL ReportOnDoctors(?)}");
        statement.setInt(1,minusDays);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Report r = new Report();
            r.setCol1(resultSet.getString("DoctorName"));
            r.setCol2(resultSet.getString("PatientNumber"));
            

            reports.add(r);
        }
        connection.close();
        return reports;
    }
}
