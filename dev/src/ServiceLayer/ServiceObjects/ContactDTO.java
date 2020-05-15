package ServiceLayer.ServiceObjects;

public class ContactDTO {

	private int contactId;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address;
	private int supplierId;

	public ContactDTO(int supplierId,int contactId, String firstName, String lastName, String phonNumber, String address) {
		this.supplierId = supplierId;
		this.contactId = contactId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phonNumber;
		this.address = address;
	}

	public int getContactId() {
		return contactId;
	}

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
		return contactId + "\t" + firstName + "\t" + lastName
				+ "\t" + phoneNumber + "\t" + address;
	}

	public int getSupplierId() {
		return this.supplierId;
	}
}