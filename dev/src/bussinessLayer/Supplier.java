package bussinessLayer;

import javafx.util.Pair;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Data.Data;

public class Supplier {
    private String name;
    private int supplierId;
    private int bankAccountNumber;
    public enum bilingOption {EOM30, EOM60, CASH, BANKTRANSFER, CHECK}
    bilingOption bilingOptions;
    private List<Contact> contactsList;
    private Contract contract;

    public void addConstDayDeliveryDays(String[] constDayDeli) {
        ArrayList<DayOfWeek> days = new ArrayList<>();
        for (int i = 0; i < constDayDeli.length; i++) {
            days.add(DayOfWeek.valueOf(constDayDeli[i]));
        }
        contract.setConstDayDeliveryByList(days);
    }


    public Supplier(String name, int supplierId, int bankAccountNumber, bilingOption bilingOption, boolean isDeliver) {
        this.name = name;
        this.supplierId = supplierId;
        this.bankAccountNumber = bankAccountNumber;
        this.contactsList = new ArrayList<>();
        this.contract = new Contract(isDeliver, supplierId);
        bilingOptions = bilingOption;
    }

    public Supplier(String name, int supplierId, int bankAccountNumber, bilingOption bilingOption, boolean isDeliver, Contract contract, Contact contact) {
        this(name, supplierId, bankAccountNumber, bilingOption, isDeliver);
        this.contract = contract;
        contactsList.add(contact);
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

    public String getCatalogItemPrinted() {
        String s = contract.getCatalogToPrint();
        return s;
    }

    public List<Contact> getContactsList() {
        return contactsList;
    }

    public Contract getContract() {
        return contract;
    }

    public double getPriceAfterDiscountByItem(int catalogItemId, int amount) {
        double discount = 0;
        if (contract.getDiscountByAmountItems().containsKey(catalogItemId)) {
            List<Pair<Range, Double>> tempList = contract.getDiscountByAmountItems().get(catalogItemId);
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

    public String getBilingToPrint() {
        return "our bailing method is : " + bilingOptions.toString();
    }


    public void deleteFromMap(int catalogItemId) {
        contract.deleteFromMap(catalogItemId);
    }

    public void setBankAccountNumber(int bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }


    public void addNewContact(Contact contact) {
        if (!this.contactsList.contains(contact))
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

    public String contactListPrinted() {
        String s = "";
        for (Contact contact : contactsList) {
            s = s + "\n" + contact.toString();
        }
        return s;
    }

    public LocalDate getNextDateOfDelivery() {
        return contract.getNextDateOfDelivery();
    }

    public static void loadFirstSuppliers() {
        List<CatalogItem> items = new ArrayList<CatalogItem>();
        items.add(new CatalogItem(1, 10, 3));
        items.add(new CatalogItem(2, 16, 15));
        Catalog catalog = new Catalog(items);
        List<Pair<Range, Double>> rangeList = new ArrayList<Pair<Range, Double>>();
        List<Pair<Range, Double>> rangeList2 = new ArrayList<Pair<Range, Double>>();
        rangeList.add(new Pair<Range, Double>(new Range(1, 30), 3.0));
        rangeList.add(new Pair<Range, Double>(new Range(31, -1), 2.5));
        HashMap<Integer, List<Pair<Range, Double>>> discountByAmountItems = new HashMap<Integer, List<Pair<Range, Double>>>();
        discountByAmountItems.put(10, rangeList);
        rangeList2.add(new Pair<Range, Double>(new Range(1, 10), 15.0));
        rangeList2.add(new Pair<Range, Double>(new Range(11, -1), 14.0));
        discountByAmountItems.put(16, rangeList2);
        List<DayOfWeek> deliveryDays = new ArrayList<DayOfWeek>();
        deliveryDays.add(DayOfWeek.MONDAY);
        deliveryDays.add(DayOfWeek.THURSDAY);
        Data.getSuppliers().add(new Supplier("tnuva", 123456, 123345, bilingOption.EOM30, true, new Contract(true, catalog, deliveryDays, 123456, discountByAmountItems), new Contact("Niv", "Davidian", "0547824018", "ziso")));

        HashMap<Integer, List<Pair<Range, Double>>> discountByAmountItems2 = new HashMap<Integer, List<Pair<Range, Double>>>();
        List<CatalogItem> items2 = new ArrayList<CatalogItem>();
        items2.add(new CatalogItem(3, 10, 30));
        items2.add(new CatalogItem(2, 7, 70));
        Catalog catalog2 = new Catalog(items);
        List<Pair<Range, Double>> rangeList3 = new ArrayList<Pair<Range, Double>>();
        rangeList3.add(new Pair<Range, Double>(new Range(1, 20), 30.0));
        rangeList3.add(new Pair<Range, Double>(new Range(21, -1), 25.0));
        discountByAmountItems2.put(10, rangeList);
        List<Pair<Range, Double>> rangeList4 = new ArrayList<Pair<Range, Double>>();
        rangeList4.add(new Pair<Range, Double>(new Range(1, 15), 70.0));
        rangeList4.add(new Pair<Range, Double>(new Range(16, -1), 60.0));
        discountByAmountItems2.put(7, rangeList);
        List<DayOfWeek> deliveryDays2 = new ArrayList<DayOfWeek>();
        Data.getSuppliers().add(new Supplier("Korkevados", 987654, 987765, bilingOption.CASH, true, new Contract(true, catalog2, deliveryDays2, 987654, discountByAmountItems2), new Contact("Dor", "Peretz", "0547824567", "Metzada")));

    }

    public String getSupplierInfo() {
        String s = "";
        s = s + "Supplier Name: " + this.getName() + ", " + "Supplier Id: " + this.getSupplierId() + ", Bank Account: " + this.getBankAccountNumber();
        return s;
    }
}





