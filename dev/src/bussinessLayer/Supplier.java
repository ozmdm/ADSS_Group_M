package bussinessLayer;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Supplier {
    private String name;
    private int supplierId;
    private int bankAccountNumber;
    private enum bilingOption {eom30, eom60, cash, bankTransfer, Check}
    bilingOption bilingOptions;
    private List<Contact> contactsList;
    private Contract contract;


    public Supplier(String name, int supplierId, int bankAccountNumber, List<Contact> contactsList, Contract contract, bilingOption bilingOption) {
        this.name = name;
        this.supplierId = supplierId;
        this.bankAccountNumber = bankAccountNumber;
        this.contactsList = contactsList;
        this.contract = contract;
        bilingOptions = bilingOption;
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

    public double getPriceAfterDiscountByItem(int CatalogItemId, int amount) {
        double discount = 0;
        if (contract.getDiscountByAmountItems().containsKey(CatalogItemId)) {
            List<Pair<Range, Double>> tempList = contract.getDiscountByAmountItems().get(CatalogItemId);
            for (int i = 0; i < tempList.size(); i++) {
                if (tempList.get(i).getKey().getMax() >= amount && tempList.get(i).getKey().getMin() <= amount) {
                    discount = tempList.get(i).getValue();
                    break;
                } else if (tempList.get(i).getKey().getMax() == -1) {
                    discount = tempList.get(i).getValue();
                }
                break;
            }
        }
        return discount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public void setBankAccountNumber(int bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }


    public void setContactsList(Contact contact) {
        if (!this.contactsList.contains(contract))
            this.contactsList.add(contact);
    }

    public void setContactsList(List<Contact> contactsList) {
        this.contactsList = contactsList;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
    public void deleteContacts (Contact contact)
    {
        if (this.contactsList.contains(contact))
            this.contactsList.remove(contact);
    }
}


