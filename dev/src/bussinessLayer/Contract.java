package bussinessLayer;

import javafx.scene.shape.StrokeLineCap;
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

    public void setConstDayDeliveryByList(List<DayOfWeek> days) {
        this.constDayDelivery = days;
    }

    public void setConstDayDelivery(String day) {
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(day);
        if (!this.constDayDelivery.contains(day))
            this.constDayDelivery.add(dayOfWeek);
    }

    public String getCatalogToPrint() {

        String s = catalog.toString();
        return s;
    }

    public void addToMap(int catalogItemId, int max, int min, double price) {
        if (!discountByAmountItems.containsKey(catalogItemId)) {
            discountByAmountItems.put(catalogItemId, new ArrayList<>());
        }
        Range range = new Range(max, min);
        Pair pair = new Pair(range, price);
        if (!discountByAmountItems.get(catalogItemId).contains(pair))
            discountByAmountItems.get(catalogItemId).add(pair);

    }

    public void deleteFromMap(int catalogItem) {
        if (discountByAmountItems.containsKey(catalogItem)) {
            for (int i = 0; i < discountByAmountItems.get(catalogItem).size(); i++)
                discountByAmountItems.get(catalogItem).remove(i);
        }
    }

    public String getConstDayDelivierToPrinted() {
        String s = "";
        if (isDeliver == false) {
            return s = s + "this supplier dose not do delivery, only pick-up";
        }

        String days = "";
        for (DayOfWeek dayOfWeek : constDayDelivery) {
            days = days + "," + dayOfWeek.toString();
        }
        return s = s + "the const day delivery is: " + days;
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
    }

    public void addNewItemToCatalog(int itemId, int catalogId, double price) {
        CatalogItem catalogItem = new CatalogItem(itemId, catalogId, price);
        if (!catalog.getItems().contains(catalogItem)) {
            addItemToCatalog(catalogItem);
        }

    }

    public void addItemToCatalog(CatalogItem catalogItem) {
        this.catalog.addItemToCatalog(catalogItem);
    }

    public void removItemFromCatalog(CatalogItem catalogItem) {
        this.catalog.removItemFromList(catalogItem);
        deleteFromMap(catalogItem.getCatalogItemId());

    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public void setDiscountByAmountItems(HashMap<Integer, List<Pair<Range, Double>>> discountByAmountItems) {
        this.discountByAmountItems = discountByAmountItems;
    }

    public CatalogItem getCatalogItem(int catalogItemId) {
        return catalog.getCatalogItem(catalogItemId);
    }

    public LocalDateTime getNextDateOfDelivery() {
        LocalDateTime now = LocalDateTime.now();
        if (!isDeliver()) {
            //TODO NEED TO CHANGE THIS TO CALL TO ARRANGE PICKUP
            return now.plusDays(1);
        }

        if (constDayDelivery.isEmpty()) {
            return now.plusDays(1);
        }

        int minDay = 0;
        for (DayOfWeek dw : constDayDelivery) { //LOOPING OVER CONSTDAY.. AND DECIDE WHICH THE DAY OF DELIVERY IS THE CLOSEST AND RETURN IT
            int diff = now.getDayOfWeek().getValue() - dw.getValue(); // THIS DAY - DAYOFREGULARDELIVERY
            if (diff < 0) {
                diff = (-1) * diff;
            } else if (diff > 0) {
                diff = 7 - diff;
            }
            if (minDay > diff && diff != 0) minDay = diff; //IF THE DIFF!=0 MEANING ITS NOT TODAY
        }

        return now.plusDays(minDay);
    }
}
