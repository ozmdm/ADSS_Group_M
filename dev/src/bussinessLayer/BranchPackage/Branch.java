package bussinessLayer.BranchPackage;

import bussinessLayer.InventoryPackage.Inventory;
import MessageTypes.StockReport;

import java.util.HashMap;
import java.util.Map;

public class Branch {

    private int id;
    private String description;
    private DamagedController damagedController;
    private Inventory inventory;
    private Map<Integer, ItemStatus> stockByItemId;

    public Branch(int id, String description) {
        this.id = id;
        this.description = description;
        this.damagedController = new DamagedController();
        this.inventory = Inventory.getInstance();
        this.stockByItemId = new HashMap<>();
    }

    public void editShelfQuantity(int itemId, int delta) throws Exception {
        if (!this.stockByItemId.keySet().contains(itemId)) {
            throw new Exception("Item was not found");
        }
        this.stockByItemId.get(itemId).setQuantityShelf(delta + this.stockByItemId.get(itemId).getQuantityShelf());
    }

    public void editStockQuantity(int itemId, int delta) throws Exception {
        if (!this.stockByItemId.keySet().contains(itemId)) {
            throw new Exception("Item was not found");
        }
        this.stockByItemId.get(itemId).setQuantityStock(delta + this.stockByItemId.get(itemId).getQuantityStock());
    }

    public void cancelCard(int itemId, int quantityToCancel) throws Exception {
        if (!this.stockByItemId.keySet().contains(itemId)) {
            throw new Exception("Item was not found");
        }
        this.stockByItemId.get(itemId).setQuantityShelf(this.stockByItemId.get(itemId).getQuantityShelf() + quantityToCancel);
    }



    public void updateDamagedItem(int itemId, int delta) throws Exception {
        if (!this.stockByItemId.keySet().contains(itemId)) {
            throw new Exception("Item was not found");
        }
        if(!this.damagedController.getQuantityById().keySet().contains(itemId)) {
            this.damagedController.getQuantityById().put(itemId, delta);
            return;
        }
        this.damagedController.getQuantityById().put(itemId, this.damagedController.getQuantityById().get(itemId) + delta);

    }

    /*
         the report will include only items which has overall quantity < 5 * min. quantity
     */
    public Map<Integer, Integer> generateToOrderReport(){
        Map<Integer, Integer> res = new HashMap<>();
        for (ItemStatus itemStatus: this.stockByItemId.values()) {
            int quantity = 5 * this.inventory.getItems().get(itemStatus.getItemId()).getMinimumQuantity() - itemStatus.getQuantityOverall();
            if(quantity<=0)
                continue;
            res.put(itemStatus.getItemId(), quantity);
        }
        return res;
    }

    /*
    create warning only for items which have overall quantity<=minimum quantity.
     */
    public Map<Integer, Integer> generateWarningReport(){
        Map<Integer, Integer> res = new HashMap<>();
        for (ItemStatus itemStatus: this.stockByItemId.values()) {
            int quantity = itemStatus.getQuantityOverall() - inventory.getItems().get(itemStatus.getItemId()).getMinimumQuantity();
            if(quantity>0)
                continue;
            res.put(itemStatus.getItemId(), itemStatus.getQuantityOverall());
        }
        return res;
    }

    public Map<Integer, Integer> generateDamagedReport(){
        return this.damagedController.getQuantityById();
    }

    public StockReport generateStockReport(String[] categories){
        StockReport res = new StockReport();

        for (ItemStatus itemStatus:this.stockByItemId.values()) {
            if(categories.length == 0){ //case: print all
                res.getItemsIdToBeReported().add(itemStatus.getItemId());
                res.getDescById().put(itemStatus.getItemId(), inventory.getItems().get(itemStatus.getItemId()).getDescription());
                res.getPositionById().put(itemStatus.getItemId(), inventory.getItems().get(itemStatus.getItemId()).getPosition());
                res.getManufacturerById().put(itemStatus.getItemId(), inventory.getItems().get(itemStatus.getItemId()).getFeatures().getManufacturer());
                res.getOverallQuantityById().put(itemStatus.getItemId(), itemStatus.getQuantityOverall());
                res.getShelfQuantityById().put(itemStatus.getItemId(), itemStatus.getQuantityShelf());
                res.getStockQuantityById().put(itemStatus.getItemId(), itemStatus.getQuantityStock());
            }
            else if(categories.length == 1){
                if(!inventory.getItems().get(itemStatus.getItemId()).getFeatures().getCategory().equals(categories[0]))
                    continue;
                res.getItemsIdToBeReported().add(itemStatus.getItemId());
                res.getDescById().put(itemStatus.getItemId(), inventory.getItems().get(itemStatus.getItemId()).getDescription());
                res.getPositionById().put(itemStatus.getItemId(), inventory.getItems().get(itemStatus.getItemId()).getPosition());
                res.getManufacturerById().put(itemStatus.getItemId(), inventory.getItems().get(itemStatus.getItemId()).getFeatures().getManufacturer());
                res.getOverallQuantityById().put(itemStatus.getItemId(), itemStatus.getQuantityOverall());
                res.getShelfQuantityById().put(itemStatus.getItemId(), itemStatus.getQuantityShelf());
                res.getStockQuantityById().put(itemStatus.getItemId(), itemStatus.getQuantityStock());
            }
            else if(categories.length == 2){
                if(inventory.getItems().get(itemStatus.getItemId()).getFeatures().getCategory().equals(categories[0])==false  || inventory.getItems().get(itemStatus.getItemId()).getFeatures().getSubCategory().equals(categories[1])==false)
                    continue;
                res.getItemsIdToBeReported().add(itemStatus.getItemId());
                res.getDescById().put(itemStatus.getItemId(), inventory.getItems().get(itemStatus.getItemId()).getDescription());
                res.getPositionById().put(itemStatus.getItemId(), inventory.getItems().get(itemStatus.getItemId()).getPosition());
                res.getManufacturerById().put(itemStatus.getItemId(), inventory.getItems().get(itemStatus.getItemId()).getFeatures().getManufacturer());
                res.getOverallQuantityById().put(itemStatus.getItemId(), itemStatus.getQuantityOverall());
                res.getShelfQuantityById().put(itemStatus.getItemId(), itemStatus.getQuantityShelf());
                res.getStockQuantityById().put(itemStatus.getItemId(), itemStatus.getQuantityStock());
                
            }
            else if(categories.length == 3){
                if(inventory.getItems().get(itemStatus.getItemId()).getFeatures().getCategory().equals(categories[0])==false  || inventory.getItems().get(itemStatus.getItemId()).getFeatures().getSubCategory().equals(categories[1])==false ||
                        inventory.getItems().get(itemStatus.getItemId()).getFeatures().getSub2Category().equals(categories[2])==false)
                    continue;
                res.getItemsIdToBeReported().add(itemStatus.getItemId());
                res.getDescById().put(itemStatus.getItemId(), inventory.getItems().get(itemStatus.getItemId()).getDescription());
                res.getPositionById().put(itemStatus.getItemId(), inventory.getItems().get(itemStatus.getItemId()).getPosition());
                res.getManufacturerById().put(itemStatus.getItemId(), inventory.getItems().get(itemStatus.getItemId()).getFeatures().getManufacturer());
                res.getOverallQuantityById().put(itemStatus.getItemId(), itemStatus.getQuantityOverall());
                res.getShelfQuantityById().put(itemStatus.getItemId(), itemStatus.getQuantityShelf());
                res.getStockQuantityById().put(itemStatus.getItemId(), itemStatus.getQuantityStock());
            }
        }
        return res;
    }

    public bussinessLayer.BranchPackage.DamagedController getDamagedController() {
        return damagedController;
    }

    public void setDamagedController(DamagedController damagedController) {
        this.damagedController = damagedController;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
