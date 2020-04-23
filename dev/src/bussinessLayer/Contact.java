package bussinessLayer;

public class Contact {
    private static int index = 0;
    private int contactId;
    private String firstName;
    private String lastName;
    private String phonNumber;
    private String address;

    public static int getIndex() {
        return index;
    }

    public int getContactId() {
        return contactId;
    }

    public Contact(String firstName, String lastName, String phonNumber, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phonNumber = phonNumber;
        this.address = address;
        index++;
        contactId = index;
    }

    public Contact() {
        this.address = "";
        this.firstName = "";
        this.lastName = "";
        this.phonNumber = "";
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
        if (!firstName.equals(""))
            this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        if (!lastName.equals(""))
            this.lastName = lastName;
    }

    public void setPhonNumber(String phonNumber) {
        if (!phonNumber.equals(""))
            this.phonNumber = phonNumber;
    }

    public void setAddress(String address) {
        if (!address.equals(""))
            this.address = address;

    }

    public String toString() {
        return "contactId:  " + contactId + " ," + "contactName: " + this.firstName + " " + this.lastName + " ," + "contactPhoneNUM: " + this.phonNumber + " ," + "contact address: " + this.getAddress();
    }
}
