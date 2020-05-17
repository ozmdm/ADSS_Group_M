package bussinessLayer.SupplierPackage;

import Data.Data;
import ServiceLayer.ServiceObjects.SupplierDTO;
import javafx.util.Pair;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Supplier {




    public enum billingOptions {EOM30, EOM60, CASH, BANKTRANSFER, CHECK}
	
    private String name;
    private int supplierId;
    private int bankAccountNumber;
    private billingOptions bilingOptions;
    private List<Contact> contactsList;
    private Contract contract;

    public void removItemFromCatalog(CatalogItem catalogItem) throws Exception {
        contract.removItemFromCatalog(catalogItem);
    }

    public void cleanRangeListItemFromMap(int catalogItemId) throws Exception {
        this.contract.cleanRangeListItemFromMap(catalogItemId);
    }


    public void addConstDayDeliveryDays(String[] constDayDeli) throws Exception {
        if (constDayDeli.length <= 0) throw new Exception("you must to give legal const day delivery");
        ArrayList<DayOfWeek> days = new ArrayList<>();
        for (int i = 0; i < constDayDeli.length; i++) {
            days.add(DayOfWeek.valueOf(constDayDeli[i]));
        }
        if (days.isEmpty()) throw new Exception("you must to give legal const day delivery ");
        contract.setConstDayDeliveryByList(days);
    }


    public Supplier(String name, int supplierId, int bankAccountNumber, billingOptions billingOptions, boolean isDeliver) {
        this.name = name;
        this.supplierId = supplierId;
        this.bankAccountNumber = bankAccountNumber;
        this.contactsList = new ArrayList<>();
        this.contract = new Contract(isDeliver, supplierId);
        bilingOptions = billingOptions;
    }

    public Supplier(String name, int supplierId, int bankAccountNumber, billingOptions billingOptions, boolean isDeliver, Contract contract, Contact contact) {
        this(name, supplierId, bankAccountNumber, billingOptions, isDeliver);
        this.contract = contract;
        contactsList.add(contact);
    }

    public Supplier(SupplierDTO supplier) {
        
	}

	public void updateBilingOptions(String bilingOption) {
        this.bilingOptions = billingOptions.valueOf(bilingOption);
    }

    public void setDeliverContrect(boolean isDeliver) {
        contract.setDeliver(isDeliver);

    }

    public void addCatalogItemToCatalogIncontract(int itemId, int catalogId, double price) throws Exception {

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

    public void addToMap(int catalogItemId, int max, int min, double price) throws Exception {
        contract.addToMap(catalogItemId, max, min, price);

    }

    public void updateMap(int catalogItemId, int min, int max, double priceAfterDisc) throws Exception {
        contract.addToMap(catalogItemId, min, max, priceAfterDisc);
    }

    public void setBankAccountNumber(int bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }


    public void addNewContact(Contact contact) {
        if (!this.contactsList.contains(contact))
            this.contactsList.add(contact);
    }


    public void setContact(String firstName, String lastName, String phoneNum, String address) throws Exception {
        for (Contact c : this.contactsList) {
            if (phoneNum.equals(c.getPhonNumber()))
                throw new Exception("the contact is already exist");
        }
        Contact c = new Contact(firstName, lastName, phoneNum, address);
        addNewContact(c);
    }

    public void deleteContact(String phoneNumber) throws Exception {
        for (int i = 0; i < contactsList.size(); i++) {
            if (contactsList.get(i).getPhonNumber().equals(phoneNumber)) {
                contactsList.remove(i);
                return;
            }
        }
        throw new Exception("contact doesnt exist");
    }


    public CatalogItem getCatalogItem(int catalogItemId) throws Exception { //RETURNS THE CATALOG-ITEM WITH @catalogItemId

        return contract.getCatalogItem(catalogItemId);
    }

    public LocalDateTime getNextDateOfDelivery() {
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
        Data.getSuppliers().add(new Supplier("tnuva", 123456, 123345, billingOptions.EOM30, true, new Contract(true, catalog, deliveryDays, 123456, discountByAmountItems), new Contact("Niv", "Davidian", "0547824018", "ziso")));

        HashMap<Integer, List<Pair<Range, Double>>> discountByAmountItems2 = new HashMap<Integer, List<Pair<Range, Double>>>();
        List<CatalogItem> items2 = new ArrayList<CatalogItem>();
        items2.add(new CatalogItem(3, 10, 30));
        items2.add(new CatalogItem(4, 7, 5));
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
        Data.getSuppliers().add(new Supplier("Korkevados", 987654, 987765, billingOptions.CASH, true, new Contract(true, catalog2, deliveryDays2, 987654, discountByAmountItems2), new Contact("Dor", "Peretz", "0547824567", "Metzada")));

    }

    public String getSupplierInfo() {
        String s = "";
        s = s + "Supplier Name: " + this.getName() + ", " + "Supplier Id: " + this.getSupplierId() + ", Bank Account: " + this.getBankAccountNumber();
        return s;
    }

	public billingOptions getBilingOption() {
		return bilingOptions;
	}

	public Catalog getCatalog() {
		return contract.getCatalog();
	}

	public void isDayValidDelivery(DayOfWeek day)throws Exception {
         contract.isDayValidDelivery(day);
	}

    public int getCatalogItemIdByItem(Integer itemId) throws Exception {
       if (this.contract.getCatalogItemIdByItemId(itemId) == -1) throw new Exception("item Dose Not Exist in catalog");

            return this.contract.getCatalogItemIdByItemId(itemId);
    }
    public double getPriceForItemWithAmountAfterDiscount(Integer itemId, Integer amount) throws Exception {
    HashMap<Integer, List<Pair<Range,Double>>> hashMap = this.contract.getDiscountByAmountItems();
    for (Pair<Range,Double> pair : hashMap.get(itemId))
    {
        if (pair.getKey().getMin()<= amount && pair.getKey().getMax()>= amount)
            return pair.getValue();
    }
    throw new Exception("item Dose not exist in supplier catalog");

    }

}





