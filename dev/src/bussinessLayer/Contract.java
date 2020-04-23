package bussinessLayer;

import javafx.util.Pair;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Contract {
	private boolean isDeliver;
	private Catalog catalog;
	private List<DayOfWeek> constDayDelivery;
	private int supplierId;
	private HashMap<Integer, List<Pair<Range, Double>>> discountByAmountItems;

	public Contract(boolean isDeliver, int supplierId) {
		this.isDeliver = isDeliver;
		this.catalog = new Catalog();
		this.supplierId = supplierId;
		this.discountByAmountItems = new HashMap<Integer, List<Pair<Range, Double>>>();
		this.constDayDelivery = new ArrayList<>();
	}

	public Contract(boolean isDeliver, Catalog catalog, List<DayOfWeek> constDayDelivery, int supplierId,
			HashMap<Integer, List<Pair<Range, Double>>> discountByAmountItems) {
		super();
		this.isDeliver = isDeliver;
		this.catalog = catalog;
		this.constDayDelivery = constDayDelivery;
		this.supplierId = supplierId;
		this.discountByAmountItems = discountByAmountItems;
	}

	public void setConstDayDeliveryByList(List<DayOfWeek> days) {
		this.constDayDelivery = days;
	}

	public void setConstDayDelivery(String day) {
		DayOfWeek dayOfWeek = DayOfWeek.valueOf(day);
		if (!this.constDayDelivery.contains(dayOfWeek))
			this.constDayDelivery.add(dayOfWeek);
	}

	public String getCatalogToPrint() throws Exception{
		return catalog.toString();
	}

	public void addToMap(int catalogItemId, int max, int min, double price) throws Exception {
		if (!discountByAmountItems.containsKey(catalogItemId)) {
			discountByAmountItems.put(catalogItemId, new ArrayList<>());
		}
		Range range = new Range(max, min);
		Pair<Range,Double> pair = new Pair<Range,Double>(range, price);
		if (!discountByAmountItems.get(catalogItemId).contains(pair)){
			discountByAmountItems.get(catalogItemId).add(pair);}
		else
		{
			throw new Exception("please you cannot edit filed in the agreement just add new range of discount, to edit just add new agreement");
		}

	}

	public void removeItemFromMap(int catalogItem) throws Exception {
		if (discountByAmountItems.containsKey(catalogItem)) {
			discountByAmountItems.remove(catalogItem);
			return;
		}
		throw new Exception("catalog item do not found");
	}

	public String getConstDayDelivierToPrinted() {
		String s = "";
		if (isDeliver == false) {
			return s = s + "this supplier dose not do delivery, only pick-up";
		}

		String days = "";
		for (DayOfWeek dayOfWeek : constDayDelivery) {
			days += dayOfWeek.toString() + ",";
		}
		days = days.substring(0, days.length()-1);
		return s = s + "Days of delivery: " + days;
	}

	public boolean isDeliver() {
		return isDeliver;
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public HashMap<Integer, List<Pair<Range, Double>>> getDiscountByAmountItems() {
		return discountByAmountItems;
	}

	public void setDeliver(boolean deliver) {
		isDeliver = deliver;
		if(!isDeliver) {
			constDayDelivery.clear();
		}
	}

	public void addNewItemToCatalog(int itemId, int catalogId, double price) throws Exception {
		CatalogItem catalogItem = new CatalogItem(itemId, catalogId, price);
		if (!catalog.getItems().contains(catalogItem)) {
			addItemToCatalog(catalogItem);
			return;
		}
		throw new Exception("catalog already contain the item");

	}

	public void addItemToCatalog(CatalogItem catalogItem) {
		this.catalog.addItemToCatalog(catalogItem);
	}

	public void removItemFromCatalog(CatalogItem catalogItem) throws Exception {
		this.catalog.removItemFromList(catalogItem);
		removeItemFromMap(catalogItem.getCatalogItemId());

	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public void setDiscountByAmountItems(HashMap<Integer, List<Pair<Range, Double>>> discountByAmountItems) {
		this.discountByAmountItems = discountByAmountItems;
	}

	public CatalogItem getCatalogItem(int catalogItemId) throws Exception {
		return catalog.getCatalogItem(catalogItemId);
	}

	public LocalDate getNextDateOfDelivery() {
		LocalDateTime now = LocalDateTime.now();
		if (!isDeliver()) {
			//TODO NEED TO CHANGE THIS TO CALL TO ARRANGE PICKUP
			return now.plusDays(1).toLocalDate();
		}

		if (constDayDelivery.isEmpty()) {
			return now.plusDays(1).toLocalDate();
		}

		int minDay = 8;
		for (DayOfWeek dw : constDayDelivery) { //LOOPING OVER CONSTDAY.. AND DECIDE WHICH THE DAY OF DELIVERY IS THE CLOSEST AND RETURN IT
			int diff = now.getDayOfWeek().getValue() - dw.getValue(); // THIS DAY - DAYOFREGULARDELIVERY
			if (diff < 0) {
				diff = (-1) * diff;
			} else if (diff > 0) {
				diff = 7 - diff;
			}
			if (minDay > diff && diff != 0) minDay = diff; //IF THE DIFF!=0 MEANING ITS NOT TODAY
		}

		return now.plusDays(minDay).toLocalDate();
	}

	public void cleanRangeListItemFromMap(int catalogItemId) throws Exception {
		if (discountByAmountItems.containsKey(catalogItemId))
		{
			discountByAmountItems.get(catalogItemId).clear();
		}
		throw new Exception("catalog item do not found");
	}
}
