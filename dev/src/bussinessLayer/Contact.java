package bussinessLayer;

public class Contact {
    private  String firstName;
    private  String lastName;
    private  String phonNumber;
    private  String address;

    public Contact(String firstName, String lastName, String phonNumber, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phonNumber = phonNumber;
        this.address = address;
    }
    public Contact()
    {
        this.address="";
        this.firstName="";
        this.lastName="";
        this.phonNumber="";
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhonNumber() {
        return phonNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhonNumber(String phonNumber) {
        this.phonNumber = phonNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
