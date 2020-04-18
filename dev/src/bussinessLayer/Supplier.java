package bussinessLayer;
import javafx.util.Pair;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Supplier {
    private String name;
    private int supplierId;
    private int bankAccountNumber;


    public enum bilingOption {EOM30, EOM60, CASH,BANKTRANSFER, CHECK}

    bilingOption bilingOptions;
    private List<Contact> contactsList;
    private Contract contract;

    public Supplier(String name, int supplierId, int bankAccountNumber, bilingOption bilingOption, boolean isDeliver) {
        this.name = name;
        this.supplierId = supplierId;
        this.bankAccountNumber = bankAccountNumber;
        this.contactsList = new ArrayList<>();
        this.contract = new Contract(isDeliver,supplierId);
        bilingOptions = bilingOption;
    }

    public void updateBilingOptions(String bilingOption) {
        this.bilingOptions = Supplier.bilingOption.valueOf(bilingOption);
    }
    public void setDeliverContrect(boolean isDeliver) {
        contract.setDeliver(isDeliver);
    }

    public void addCatalogItemToCatalogIncontract(int itemId, int catalogId, double price) {

        contract.addNewItemToCatalog(itemId, catalogId, price);

    }


    public String getName() {
        return name;
    }

    public int getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }


    public int getSupplierId() {
        return supplierId;
    }

    public String getCatalogItemPrinted(){
        String s = contract.getCatalogToPrint();
        return s;
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
                    break;
                }

            }
        }
        return discount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addToMap(int catalogItemId, int max, int min, double price) {
        contract.addToMap(catalogItemId, max, min, price);

    }
    public String getBilingToPrint(){
      return "our bailing method is : " + bilingOptions.toString();
    }


    public void deleteFromMap(int catalogItemId) {
        contract.deleteFromMap(catalogItemId);
    }

    public void setBankAccountNumber(int bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }


    public void addNewContact(Contact contact) {
        if (!this.contactsList.contains(contract))
            this.contactsList.add(contact);
    }


    public void setContact(String firstName, String lastName, String phoneNum, String address) {
        Contact c = new Contact(firstName, lastName, phoneNum, address);
        addNewContact(c);
    }

    public void deleteContact(int contactId) {
        for (int i = 0; i < contactsList.size(); i++) {
            if (contactsList.get(i).getContactId() == contactId) {
                contactsList.remove(i);
                break;
            }
        }
    }


    public CatalogItem getCatalogItem(int catalogItemId) { //RETURNS THE CATALOG-ITEM WITH @catalogItemId

        return contract.getCatalogItem(catalogItemId);
    }

    public String contactListPrinted ()
    {
        String s = "";
        for (Contact contact : contactsList)
        {
           s = s + "\n" +contact.toString();
        }
        return s;
    }

	public LocalDateTime getNextDateOfDelivery() {
		return contract.getNextDateOfDelivery();
	}

	public static void loadFirstSuppliers() {
        //TODO
	}
	public String getSupplierInfo()
    {
        String s="";
        s = s + "Supplier Name: "+this.getName()+", "+"Supplier Id: "+ this.getSupplierId()+", Bank Account: "+ this.getBankAccountNumber();
    return s;
    }
}





