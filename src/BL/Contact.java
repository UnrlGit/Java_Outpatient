package BL;

/**
 * Created by Chaos on 6.6.2017..
 */
public class Contact {
    private Address currentAddress = new Address();
    private Address permanantAddress = new Address();
    private String telephoneWork;
    private String telephoneHome;
    private String mobilePhone;
    private String pager;
    private String fax;
    private String email;

    @Override
    public String toString() {
        return "Contact{" +
                "currentAddress=" + currentAddress + "\n" +
                ", permanentAddress=" + permanantAddress +"\n" +
                ", telephoneWork='" + telephoneWork + '\'' +"\n" +
                ", telephoneHome='" + telephoneHome + '\'' +"\n" +
                ", mobilePhone='" + mobilePhone + '\'' +"\n" +
                ", pager='" + pager + '\'' +"\n" +
                ", fax='" + fax + '\'' +"\n" +
                ", email='" + email + '\''+
                '}';
    }

    public Address getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(Address currentAddress) {
        this.currentAddress = currentAddress;
    }

    public Address getPermanantAddress() {
        return permanantAddress;
    }

    public void setPermanantAddress(Address permanantAddress) {
        this.permanantAddress = permanantAddress;
    }

    public String getTelephoneWork() {
        return telephoneWork;
    }

    public void setTelephoneWork(String telephoneWork) {
        this.telephoneWork = telephoneWork;
    }

    public String getTelephoneHome() {
        return telephoneHome;
    }

    public void setTelephoneHome(String telephoneHome) {
        this.telephoneHome = telephoneHome;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPager() {
        return pager;
    }

    public void setPager(String pager) {
        this.pager = pager;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Contact(Address currentAddress, Address permanantAddress, String telephoneWork, String telephoneHome,
                   String mobilePhone, String pager, String fax, String email) {
        this.currentAddress = currentAddress;
        this.permanantAddress = permanantAddress;
        this.telephoneWork = telephoneWork;
        this.telephoneHome = telephoneHome;
        this.mobilePhone = mobilePhone;
        this.pager = pager;
        this.fax = fax;
        this.email = email;
    }

    public Contact() {
    }
}
