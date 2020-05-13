package InventoryPackage;

import MessageTypes.StockReport;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<Integer, Item> items;
    private DamagedController damagedController;
    private int idCounter;

    public Inventory() {
        this.items = new HashMap<>();
        this.damagedController = new DamagedController(new HashMap<>());
        this.idCounter = 0;
    }

    public Map<Integer, Item> getItems() {
        return items;
    }

    public void setItems(Map<Integer, Item> items) {
        this.items = items;
    }

    public int addItem(String description, int quantityShelf,
                        int quantityStock, double costPrice, double salePrice, String position,
                        int minimumQuantity,
                        double weight, String category, String subCategory, String sub2Category, String manufacturer) {
        this.idCounter++;
        this.items.put(idCounter, new Item(idCounter, description, quantityShelf+quantityStock, quantityShelf, quantityStock,
                costPrice, salePrice, position, minimumQuantity, new ItemFeatures(idCounter, weight, category, subCategory, sub2Category, manufacturer)));
        return idCounter;
    }

    public void editMinimumQuantity(int itemId, int quantity) throws Exception {
        if (!this.items.keySet().contains(itemId)) {
            throw new Exception("Item was not found");
        }
        this.items.get(itemId).setMinimumQuantity(quantity);
    }

    public void editShelfQuantity(int itemId, int delta) throws Exception {
        if (!this.items.keySet().contains(itemId)) {
            throw new Exception("Item was not found");
        }
        this.items.get(itemId).setQuantityShelf(delta + this.items.get(itemId).getQuantityShelf());
    }

    public void editStockQuantity(int itemId, int delta) throws Exception {
        if (!this.items.keySet().contains(itemId)) {
            throw new Exception("Item was not found");
        }
        this.items.get(itemId).setQuantityStock(delta + this.items.get(itemId).getQuantityStock());
    }

    public void cancelCard(int itemId, int quantityToCancel) throws Exception {
        if (!this.items.keySet().contains(itemId)) {
            throw new Exception("Item was not found");
        }
        this.items.get(itemId).setQuantityShelf(this.items.get(itemId).getQuantityShelf() + quantityToCancel);
    }

    public void updateItemCostPrice(int itemId, int newPrice) throws Exception{
        if (!this.items.keySet().contains(itemId)) {
            throw new Exception("Item was not found");
        }
        this.items.get(itemId).getOldCostPrices().add(this.items.get(itemId).getCostPrice());
        this.items.get(itemId).setCostPrice(newPrice);
    }

    public void updateItemSalePrice(int itemId, int newPrice) throws Exception{
        if (!this.items.keySet().contains(itemId)) {
            throw new Exception("Item was not found");
        }
        this.items.get(itemId).getOldSalePrices().add(this.items.get(itemId).getSalePrice());
        this.items.get(itemId).setSalePrice(newPrice);
    }

    public void updateDamagedItem(int itemId, int delta) throws Exception {
        if (!this.items.keySet().contains(itemId)) {
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
        for (Item item: this.items.values()) {
            int quantity = 5 * item.getMinimumQuantity() - item.getQuantityOverall();
            if(quantity<=0)
                continue;
            res.put(item.getId(), quantity);
        }
        return res;
    }

    /*
    create warning only for items which have overall quantity<=minimum quantity.
     */
    public Map<Integer, Integer> generateWarningReport(){
        Map<Integer, Integer> res = new HashMap<>();
        for (Item item: this.items.values()) {
            int quantity = item.getQuantityOverall() - item.getMinimumQuantity();
            if(quantity>0)
                continue;
            res.put(item.getId(), item.getQuantityOverall());
        }
        return res;
    }

    public Map<Integer, Integer> generateDamagedReport(){
        return this.damagedController.getQuantityById();
    }

    public StockReport generateStockReport(String[] categories){
        StockReport res = new StockReport();

        for (Item item:this.items.values()) {
            if(categories.length == 0){ //case: print all
                res.getItemsIdToBeReported().add(item.getId());
                res.getDescById().put(item.getId(), item.getDescription());
                res.getPositionById().put(item.getId(), item.getPosition());
                res.getManufacturerById().put(item.getId(), item.getFeatures().getManufacturer());
                res.getOverallQuantityById().put(item.getId(), item.getQuantityOverall());
                res.getShelfQuantityById().put(item.getId(), item.getQuantityShelf());
                res.getStockQuantityById().put(item.getId(), item.getQuantityStock());
            }
            else if(categories.length == 1){
                if(!item.getFeatures().getCategory().equals(categories[0]))
                    continue;
                res.getItemsIdToBeReported().add(item.getId());
                res.getDescById().put(item.getId(), item.getDescription());
                res.getPositionById().put(item.getId(), item.getPosition());
                res.getManufacturerById().put(item.getId(), item.getFeatures().getManufacturer());
                res.getOverallQuantityById().put(item.getId(), item.getQuantityOverall());
                res.getShelfQuantityById().put(item.getId(), item.getQuantityShelf());
                res.getStockQuantityById().put(item.getId(), item.getQuantityStock());
            }
            else if(categories.length == 2){
                if(item.getFeatures().getCategory().equals(categories[0])==false  || item.getFeatures().getSubCategory().equals(categories[1])==false)
                    continue;
                res.getItemsIdToBeReported().add(item.getId());
                res.getDescById().put(item.getId(), item.getDescription());
                res.getPositionById().put(item.getId(), item.getPosition());
                res.getManufacturerById().put(item.getId(), item.getFeatures().getManufacturer());
                res.getOverallQuantityById().put(item.getId(), item.getQuantityOverall());
                res.getShelfQuantityById().put(item.getId(), item.getQuantityShelf());
                res.getStockQuantityById().put(item.getId(), item.getQuantityStock());
            }
            else if(categories.length == 3){
                if(item.getFeatures().getCategory().equals(categories[0])==false  || item.getFeatures().getSubCategory().equals(categories[1])==false ||
                        item.getFeatures().getSub2Category().equals(categories[2])==false)
                    continue;
                res.getItemsIdToBeReported().add(item.getId());
                res.getDescById().put(item.getId(), item.getDescription());
                res.getPositionById().put(item.getId(), item.getPosition());
                res.getManufacturerById().put(item.getId(), item.getFeatures().getManufacturer());
                res.getOverallQuantityById().put(item.getId(), item.getQuantityOverall());
                res.getShelfQuantityById().put(item.getId(), item.getQuantityShelf());
                res.getStockQuantityById().put(item.getId(), item.getQuantityStock());
            }
        }
        return res;
    }

    public DamagedController getDamagedController() {
        return damagedController;
    }

    public void setDamagedController(DamagedController damagedController) {
        this.damagedController = damagedController;
    }

    public int getIdCounter() {
        return idCounter;
    }

    public void setIdCounter(int idCounter) {
        this.idCounter = idCounter;
    }
}