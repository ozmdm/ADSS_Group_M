package bussinessLayer.InventoryPackage;


import java.util.HashMap;
import java.util.Map;

public class Inventory {
    // static variable single_instance of type Singleton
    private static Inventory single_instance = null;


    private Map<Integer, bussinessLayer.InventoryPackage.Item> items;
    private int idCounter;

    private Inventory() {
        this.items = new HashMap<>();
        this.idCounter = 0;
    }

    // static method to create instance of Singleton class
    public static Inventory getInstance() {
        // To ensure only one instance is created
        if (single_instance == null) {
            single_instance = new Inventory();
        }
        return single_instance;
    }

    public Map<Integer, bussinessLayer.InventoryPackage.Item> getItems() {
        return items;
    }

    public void setItems(Map<Integer, bussinessLayer.InventoryPackage.Item> items) {
        this.items = items;
    }

    public int addItem(String description, double costPrice, double salePrice, String position,
                        int minimumQuantity,
                        double weight, String category, String subCategory, String sub2Category, String manufacturer) {
        this.idCounter++;
        this.items.put(idCounter, new bussinessLayer.InventoryPackage.Item(idCounter, description, costPrice, salePrice, position,
                minimumQuantity, new ItemFeatures(idCounter, weight, category, subCategory, sub2Category, manufacturer)));
        return idCounter;
    }

    public void editMinimumQuantity(int itemId, int quantity) throws Exception {
        if (!this.items.keySet().contains(itemId)) {
            throw new Exception("Item was not found");
        }
        this.items.get(itemId).setMinimumQuantity(quantity);
    }

    public void editItemDescription(int itemId, String description) throws Exception {
        if (!this.items.keySet().contains(itemId)) {
            throw new Exception("Item was not found");
        }
        this.items.get(itemId).setDescription(description);
    }

    public int getIdCounter() {
        return idCounter;
    }

    public void setIdCounter(int idCounter) {
        this.idCounter = idCounter;
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


}