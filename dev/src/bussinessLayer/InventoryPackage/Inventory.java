package bussinessLayer.InventoryPackage;


import DataAccessLaye.Repo;
import ServiceLayer.ServiceObjects.InventoryDTO;
import ServiceLayer.ServiceObjects.ItemDTO;
import ServiceLayer.ServiceObjects.ItemFeaturesDTO;

import java.sql.SQLException;
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
                        double weight, String category, String subCategory, String sub2Category, String manufacturer) throws SQLException {
        this.idCounter++;
        this.items.put(idCounter, new bussinessLayer.InventoryPackage.Item(idCounter, description, costPrice, salePrice, position,
        minimumQuantity, new ItemFeatures(idCounter, weight, category, subCategory, sub2Category, manufacturer)));
        ItemFeaturesDTO itemFeaturesDTO = new ItemFeaturesDTO(idCounter, weight,category,subCategory,sub2Category,manufacturer);
        Repo.getInstance().addNewItem(new ItemDTO(idCounter, description,costPrice,salePrice, minimumQuantity, itemFeaturesDTO));
        return idCounter;
    }

    public void editMinimumQuantity(int itemId, int quantity) throws Exception {
        if (!this.items.keySet().contains(itemId)) {
            throw new Exception("Item was not found");
        }
        //this.items.get(itemId).setMinimumQuantity(quantity);
        ItemDTO itemDTO = Repo.getInstance().getItem(itemId);
        itemDTO.setMinimumQuantity(quantity);
        Repo.getInstance().updateAnItemWithoutOldPrices(itemDTO);
    }

    public void editItemDescription(int itemId, String description) throws Exception {
        if (!this.items.keySet().contains(itemId)) {
            throw new Exception("Item was not found");
        }
        //this.items.get(itemId).setDescription(description);
        ItemDTO itemDTO = Repo.getInstance().getItem(itemId);
        itemDTO.setDescription(description);
        Repo.getInstance().updateAnItemWithoutOldPrices(itemDTO);
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
        //this.items.get(itemId).getOldCostPrices().add(this.items.get(itemId).getCostPrice());
        //this.items.get(itemId).setCostPrice(newPrice);
        ItemDTO itemDTO = Repo.getInstance().getItem(itemId);
        itemDTO.setCostPrice(newPrice);
        Repo.getInstance().updateCostPriceForItem(itemId,newPrice,itemDTO.getCostCounter());
    }

    public void updateItemSalePrice(int itemId, int newPrice) throws Exception{
        if (!this.items.keySet().contains(itemId)) {
            throw new Exception("Item was not found");
        }
        //this.items.get(itemId).getOldSalePrices().add(this.items.get(itemId).getSalePrice());
        //this.items.get(itemId).setSalePrice(newPrice);
        ItemDTO itemDTO = Repo.getInstance().getItem(itemId);
        itemDTO.setSalePrice(newPrice);
        Repo.getInstance().updateSalePriceForItem(itemId,newPrice,itemDTO.getSaleCounter());
    }

    public InventoryDTO convertToDTO(){
        Map<Integer, ItemDTO> itemsDTO = new HashMap<>();
        for (Integer itemId: items.keySet()) {
            ItemDTO itemDTO = items.get(itemId).convertToDTO();
            itemsDTO.put(itemId, itemDTO);
        }
        return new InventoryDTO(itemsDTO, idCounter);
    }

}