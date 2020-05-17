package ServiceLayer.ServiceObjects;

import bussinessLayer.InventoryPackage.Item;

import java.util.LinkedList;

public class ItemDTO {

    private int id;
    private String description;
    private double costPrice;
    private double salePrice;
    private String position;
    private LinkedList<Double> oldCostPrices;
    private LinkedList<Double> oldSalePrices;
    private int minimumQuantity;
    private ItemFeaturesDTO featuresDTO;

    public ItemDTO(int id, String description, double costPrice, double salePrice, String position, LinkedList<Double> oldCostPrices, LinkedList<Double> oldSalePrices, int minimumQuantity, ItemFeaturesDTO featuresDTO) {
        this.id = id;
        this.description = description;
        this.costPrice = costPrice;
        this.salePrice = salePrice;
        this.position = position;
        this.oldCostPrices = oldCostPrices;
        this.oldSalePrices = oldSalePrices;
        this.minimumQuantity = minimumQuantity;
        this.featuresDTO = featuresDTO;
    }

    public ItemDTO(Item item) {
        this.id = item.getId();
        this.description = item.getDescription();
        this.costPrice = item.getCostPrice();
        this.salePrice = item.getSalePrice();
        this.position = item.getPosition();
        this.oldCostPrices = item.getOldCostPrices();
        this.oldSalePrices = item.getOldSalePrices();
        this.minimumQuantity = item.getMinimumQuantity();
        this.featuresDTO = new ItemFeaturesDTO(item.getId(), item.getFeatures().getWeight(), item.getFeatures().getCategory(), item.getFeatures().getSubCategory(), item.getFeatures().getSub2Category(), item.getFeatures().getManufacturer());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public LinkedList<Double> getOldCostPrices() {
        return oldCostPrices;
    }

    public void setOldCostPrices(LinkedList<Double> oldCostPrices) {
        this.oldCostPrices = oldCostPrices;
    }

    public LinkedList<Double> getOldSalePrices() {
        return oldSalePrices;
    }

    public void setOldSalePrices(LinkedList<Double> oldSalePrices) {
        this.oldSalePrices = oldSalePrices;
    }

    public int getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(int minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public ItemFeaturesDTO getFeaturesDTO() {
        return featuresDTO;
    }

    public void setFeaturesDTO(ItemFeaturesDTO featuresDTO) {
        this.featuresDTO = featuresDTO;
    }
}
