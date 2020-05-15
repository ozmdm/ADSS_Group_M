package ServiceLayer.ServiceObjects;

import java.time.DayOfWeek;
import java.util.List;
import javafx.util.Pair;


public class ScheduledDTO {

    private DayOfWeek day;
    private int supplierId;
    private int branchId;
    private List<Pair<CatalogItemDTO, Integer>> itemsToOrder;

    public ScheduledDTO(DayOfWeek day, int supplierId, List<Pair<CatalogItemDTO, Integer>> itemsToOrder,int branchId) {
        this.day = day;
        this.supplierId = supplierId;
        this.itemsToOrder = itemsToOrder;
        this.branchId = branchId;
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
    public int getBranchId(){
        return this.branchId;
    }


}