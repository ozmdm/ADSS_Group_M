package bussinessLayer;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Contract {
    private boolean isDeliver;
    private Catalog catalog;
    private List<Pair<Enum, Boolean>> constDayDelivery;
    private int supplierId;
    private HashMap<Integer, List<Pair<Range, Double>>> discountByAmountItems;

    enum constDayDelivierys {sunday, monday, tuesday, wednesday, thursday, friday, saturday}

    public Contract(boolean isDeliver, List<CatalogItem> catalogItemList, int supplierId) {
        this.isDeliver = isDeliver;
        this.catalog = new Catalog();
        this.supplierId = supplierId;
        this.discountByAmountItems = new HashMap<Integer, List<Pair<Range, Double>>>();
        constDayDelivery = new ArrayList<>();
    }

    public void setConstDayDelivery(constDayDelivierys dayDelivery, Boolean imDeliverThisDay) {
        Pair p = new Pair(dayDelivery, imDeliverThisDay);
        constDayDelivery.add(p);
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

    public void addItemToCatalog(CatalogItem catalogItem) {
        this.catalog.addItemToCatalog(catalogItem);
    }

    public void removItemToCatalog(CatalogItem catalogItem) {
        this.catalog.removItemFromList(catalogItem);
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public void setDiscountByAmountItems(HashMap<Integer, List<Pair<Range, Double>>> discountByAmountItems) {
        this.discountByAmountItems = discountByAmountItems;
    }

}
