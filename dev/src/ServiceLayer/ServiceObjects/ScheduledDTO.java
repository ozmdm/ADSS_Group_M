package ServiceLayer.ServiceObjects;

import java.time.DayOfWeek;
import java.util.List;
import javafx.util.Pair;


public class ScheduledDTO {

    private DayOfWeek day;
    private int supplierId;
    private int branchId;
    private List<Pair<Integer, Integer>> itemsToOrder;

    public ScheduledDTO(int day, int supplierId, List<Pair<Integer, Integer>> itemsToOrder,int branchId) {
        try{this.day = DayOfWeek.of(day);} catch(Exception e){ this.day = null;}
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
    public List<Pair<Integer, Integer>> getItemsToOrder() {
        return itemsToOrder;
    }

    public int getBranchId(){
        return this.branchId;
    }


}