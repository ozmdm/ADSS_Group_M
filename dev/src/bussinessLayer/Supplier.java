package bussinessLayer;

import java.util.List;

public class Supplier {
 private String name;
 private  int supplierId;
 private int bankAccountNumber;
 private enum  bilingOption {eom30,eom60,cash,bankTransfer,Check};
 private List<Contact> contactsList;
 private Contract contract;


    public Supplier(String name, int supplierId, int bankAccountNumber, List<Contact> contactsList, Contract contract) {
        this.name = name;
        this.supplierId = supplierId;
        this.bankAccountNumber = bankAccountNumber;
        this.contactsList = contactsList;
        this.contract = contract;
    }

    public String getName() {
        return name;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public int getBankAccountNumber() {
        return bankAccountNumber;
    }

    public List<Contact> getContactsList() {
        return contactsList;
    }

    public Contract getContract() {
        return contract;
    }
}

