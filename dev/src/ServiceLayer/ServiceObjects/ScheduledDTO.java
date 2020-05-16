package ServiceLayer.ServiceObjects;

import java.time.DayOfWeek;
import java.util.List;
import javafx.util.Pair;


public class ScheduledDTO {

    private int branchId;
    private DayOfWeek day;
    private int supplierId;
    private List<Pair<CatalogItemDTO, Integer>> itemsToOrder;

    public ScheduledDTO(DayOfWeek day, int supplierId, int catalogItemId, List<Pair<CatalogItemDTO, Integer>> itemsToOrder, int branchId) {
        this.day = day;
        this.supplierId = supplierId;
        this.itemsToOrder = itemsToOrder;
        this.branchId = branchId;
    }

    /**
     * @return the branchId
     */
    public int getBranchId() {
        return branchId;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public int getSupplierId() {
        return supplierId;
    }

    /**
     * @return the itemsToOrder
     */
    public List<Pair<CatalogItemDTO, Integer>> getItemsToOrder() {
        return itemsToOrder;
    }


}
