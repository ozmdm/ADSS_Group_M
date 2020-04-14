package Supplier;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public class Contract {
    private  boolean isDeliver;
    private List<CatalogItem> catalogItemList;
    private  enum  constDayDelivery {sundy, mondy, tuesday, wednesday, thursday,friday,saturday};
    private int supplierId;
    private HashMap<CatalogItem, List<Pair<Range,Integer>>> discountByAmountItems;

    public boolean isDeliver() {
        return isDeliver;
    }

    public List<CatalogItem> getCatalogItemList() {
        return catalogItemList;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public HashMap<CatalogItem, List<Pair<Range, Integer>>> getDiscountByAmountItems() {
        return discountByAmountItems;
    }

    public Contract(boolean isDeliver, List<CatalogItem> catalogItemList, int supplierId) {
        this.isDeliver = isDeliver;
        this.catalogItemList = catalogItemList;
        this.supplierId = supplierId;
        this.discountByAmountItems = new HashMap<CatalogItem, List<Pair<Range, Integer>>>();

    }
}
