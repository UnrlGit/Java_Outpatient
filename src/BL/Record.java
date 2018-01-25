package BL;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by Chaos on 5.6.2017..
 */
public class Record {
    private Enums.Sex sex;
    private LocalDate dateOfBirth;
    private String complaint;
    private Contact contact = new Contact();
    private NextOfKin nextOfKin = new NextOfKin();
    private boolean maritalStatus;
    private int numberOfDependents;
    private double heightCM;
    private double weightKG;
    private String bloodTypeRH;
    private String occupation;
    private double grossAnualIncome;
    private boolean vegetarian;
    private boolean smoker;
    private double cigarettesPerDay;
    private double drinksPerDay;
    private String stimulantsUse;
    private double coffeeTeaPerDay;
    private double softDrinksPerDay;
    private short regularMeals;
    private short eatingHomeVsOut;
    private String previousTreatment;
    private String physicianAndHospitalTreated;
    private String diabetic;
    private String hypertensive;
    private String cardiacCondition;
    private String respiratoryCondition;
    private String digestiveCondition;
    private String orthopedCondition;
    private String muscularCondition;
    private String neurologicalCondition;
    private String knownAllergies;
    private String knownAdverseReactionsToDrugs;
    private String majorSurgeries;
    private LocalDateTime dateCreated;

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "Record{" +
                "sex=" + sex +
                ", dateOfBirth=" + dateOfBirth +"\n" +
                ", complaint='" + complaint + '\'' +"\n" +
                ", contact=" + contact +"\n" +
                ", nextOfKin=" + nextOfKin +"\n" +
                ", maritalStatus=" + maritalStatus +"\n" +
                ", numberOfDependents=" + numberOfDependents +"\n" +
                ", heightCM=" + heightCM +"\n" +
                ", weightKG=" + weightKG +"\n" +
                ", bloodTypeRH='" + bloodTypeRH + '\'' +"\n" +
                ", occupation='" + occupation + '\'' +"\n" +
                ", grossAnnualIncome=" + grossAnualIncome +"\n" +
                ", vegetarian=" + vegetarian +"\n" +
                ", smoker=" + smoker +"\n" +
                ", cigarettesPerDay=" + cigarettesPerDay +"\n" +
                ", drinksPerDay=" + drinksPerDay +"\n" +
                ", stimulantsUse='" + stimulantsUse + '\'' +"\n" +
                ", coffeeTeaPerDay=" + coffeeTeaPerDay +"\n" +
                ", softDrinksPerDay=" + softDrinksPerDay +"\n" +
                ", regularMeals=" + regularMeals +"\n" +
                ", eatingHomeVsOut=" + eatingHomeVsOut +"\n" +
                ", previousTreatment='" + previousTreatment + '\'' +"\n" +
                ", physicianAndHospitalTreated='" + physicianAndHospitalTreated + '\'' +"\n" +
                ", diabetic='" + diabetic + '\'' +"\n" +
                ", hypertensive='" + hypertensive + '\'' +"\n" +
                ", cardiacCondition='" + cardiacCondition + '\'' +"\n" +
                ", respiratoryCondition='" + respiratoryCondition + '\'' +"\n" +
                ", digestiveCondition='" + digestiveCondition + '\'' +"\n" +
                ", orthopedicCondition='" + orthopedCondition + '\'' +"\n" +
                ", muscularCondition='" + muscularCondition + '\'' +"\n" +
                ", neurologicalCondition='" + neurologicalCondition + '\'' +"\n" +
                ", knownAllergies='" + knownAllergies + '\'' +"\n" +
                ", knownAdverseReactionsToDrugs='" + knownAdverseReactionsToDrugs + '\'' +"\n" +
                ", majorSurgeries='" + majorSurgeries + '\'' +
                '}';
    }

    public Enums.Sex getSex() {
        return sex;
    }

    public void setSex(Enums.Sex sex) {
        this.sex = sex;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public NextOfKin getNextOfKin() {
        return nextOfKin;
    }

    public void setNextOfKin(NextOfKin nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    public boolean isMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(boolean maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public int getNumberOfDependents() {
        return numberOfDependents;
    }

    public void setNumberOfDependents(int numberOfDependents) {
        this.numberOfDependents = numberOfDependents;
    }

    public double getHeightCM() {
        return heightCM;
    }

    public void setHeightCM(double heightCM) {
        this.heightCM = heightCM;
    }

    public double getWeightKG() {
        return weightKG;
    }

    public void setWeightKG(double weightKG) {
        this.weightKG = weightKG;
    }

    public String getBloodTypeRH() {
        return bloodTypeRH;
    }

    public void setBloodTypeRH(String bloodTypeRH) {
        this.bloodTypeRH = bloodTypeRH;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public double getGrossAnualIncome() {
        return grossAnualIncome;
    }

    public void setGrossAnualIncome(double grossAnualIncome) {
        this.grossAnualIncome = grossAnualIncome;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public boolean isSmoker() {
        return smoker;
    }

    public void setSmoker(boolean smoker) {
        this.smoker = smoker;
    }

    public double getCigarettesPerDay() {
        return cigarettesPerDay;
    }

    public void setCigarettesPerDay(double cigarettesPerDay) {
        this.cigarettesPerDay = cigarettesPerDay;
    }

    public double getDrinksPerDay() {
        return drinksPerDay;
    }

    public void setDrinksPerDay(double drinksPerDay) {
        this.drinksPerDay = drinksPerDay;
    }

    public String getStimulantsUse() {
        return stimulantsUse;
    }

    public void setStimulantsUse(String stimulantsUse) {
        this.stimulantsUse = stimulantsUse;
    }

    public double getCoffeeTeaPerDay() {
        return coffeeTeaPerDay;
    }

    public void setCoffeeTeaPerDay(double coffeeTeaPerDay) {
        this.coffeeTeaPerDay = coffeeTeaPerDay;
    }

    public double getSoftDrinksPerDay() {
        return softDrinksPerDay;
    }

    public void setSoftDrinksPerDay(double softDrinksPerDay) {
        this.softDrinksPerDay = softDrinksPerDay;
    }

    public short getRegularMeals() {
        return regularMeals;
    }

    public void setRegularMeals(short regularMeals) {
        this.regularMeals = regularMeals;
    }

    public short getEatingHomeVsOut() {
        return eatingHomeVsOut;
    }

    public void setEatingHomeVsOut(short eatingHomeVsOut) {
        this.eatingHomeVsOut = eatingHomeVsOut;
    }

    public String getPreviousTreatment() {
        return previousTreatment;
    }

    public void setPreviousTreatment(String previousTreatment) {
        this.previousTreatment = previousTreatment;
    }

    public String getPhysicianAndHospitalTreated() {
        return physicianAndHospitalTreated;
    }

    public void setPhysicianAndHospitalTreated(String physicianAndHospitalTreated) {
        this.physicianAndHospitalTreated = physicianAndHospitalTreated;
    }

    public String getDiabetic() {
        return diabetic;
    }

    public void setDiabetic(String diabetic) {
        this.diabetic = diabetic;
    }

    public String getHypertensive() {
        return hypertensive;
    }

    public void setHypertensive(String hypertensive) {
        this.hypertensive = hypertensive;
    }

    public String getCardiacCondition() {
        return cardiacCondition;
    }

    public void setCardiacCondition(String cardiacCondition) {
        this.cardiacCondition = cardiacCondition;
    }

    public String getRespiratoryCondition() {
        return respiratoryCondition;
    }

    public void setRespiratoryCondition(String respiratoryCondition) {
        this.respiratoryCondition = respiratoryCondition;
    }

    public String getDigestiveCondition() {
        return digestiveCondition;
    }

    public void setDigestiveCondition(String digestiveCondition) {
        this.digestiveCondition = digestiveCondition;
    }

    public String getOrthopedCondition() {
        return orthopedCondition;
    }

    public void setOrthopedCondition(String orthopedCondition) {
        this.orthopedCondition = orthopedCondition;
    }

    public String getMuscularCondition() {
        return muscularCondition;
    }

    public void setMuscularCondition(String muscularCondition) {
        this.muscularCondition = muscularCondition;
    }

    public String getNeurologicalCondition() {
        return neurologicalCondition;
    }

    public void setNeurologicalCondition(String neurologicalCondition) {
        this.neurologicalCondition = neurologicalCondition;
    }

    public String getKnownAllergies() {
        return knownAllergies;
    }

    public void setKnownAllergies(String knownAllergies) {
        this.knownAllergies = knownAllergies;
    }

    public String getKnownAdverseReactionsToDrugs() {
        return knownAdverseReactionsToDrugs;
    }

    public void setKnownAdverseReactionsToDrugs(String knownAdverseReactionsToDrugs) {
        this.knownAdverseReactionsToDrugs = knownAdverseReactionsToDrugs;
    }

    public String getMajorSurgeries() {
        return majorSurgeries;
    }

    public void setMajorSurgeries(String majorSurgeries) {
        this.majorSurgeries = majorSurgeries;
    }
}
