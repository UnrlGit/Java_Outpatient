package BL;

/**
 * Created by Chaos on 6.6.2017..
 */
public class NextOfKin extends Person {
    private String relationshipToPatient;
    private Contact contact = new Contact();

    @Override
    public String toString() {
        return "NextOfKin{" +
                "relationshipToPatient='" + relationshipToPatient + '\'' +"\n" +
                ", contact=" + contact +"\n" +
                '}';
    }

    public String getRelationshipToPatient() {
        return relationshipToPatient;
    }

    public void setRelationshipToPatient(String relationshipToPatient) {
        this.relationshipToPatient = relationshipToPatient;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }


    public NextOfKin(String relationshipToPatient, Contact contact) {
        this.relationshipToPatient = relationshipToPatient;
        this.contact = contact;
    }

    public NextOfKin(Integer id, String fullName, String relationshipToPatient, Contact contact) {
        super(id, fullName);
        this.relationshipToPatient = relationshipToPatient;
        this.contact = contact;
    }

    public NextOfKin(){}
}
