package ServiceLayer.ServiceObjects;

public class ContactDTO {

/*
	private int contactId;
*/
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address;

	public ContactDTO(/*int contactId,*/ String firstName, String lastName, String phonNumber, String address) {
/*
		this.contactId = contactId;
*/
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phonNumber;
		this.address = address;
	}

/*	public int getContactId() {
		return contactId;
	}*/

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return /*contactId + "\t" +*/ firstName + "\t" + lastName
				+ "\t" + phoneNumber + "\t" + address;
	}
}