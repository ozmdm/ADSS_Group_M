package InventoryPackage;

import java.util.LinkedList;

public class Item {
    private int id;
    private String description;
    private double costPrice;
    private double salePrice;
    private String position;
    private LinkedList<Double> oldCostPrices;
    private LinkedList<Double> oldSalePrices;
    private int minimumQuantity;
    private ItemFeatures features;

    public Item(int id, String description, double costPrice, double salePrice, String position, int minimumQuantity, ItemFeatures features) {
        this.id = id;
        this.description = description;
        this.costPrice = costPrice;
        this.salePrice = salePrice;
        this.position = position;
        this.oldCostPrices = new LinkedList<>();
        this.oldSalePrices = new LinkedList<>();
        this.minimumQuantity = minimumQuantity;
        this.features = features;
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

    public ItemFeatures getFeatures() {
        return features;
    }

    public void setFeatures(ItemFeatures features) {
        this.features = features;
    }

    public int getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(int minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }
}
