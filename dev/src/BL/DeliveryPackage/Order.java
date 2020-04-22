package BL.DeliveryPackage;

import java.util.*;

public class Order {

    private String id;
    private Map<String, Integer> items;
    private String supplierId;
    private String locationId;
    private double totalWeight;

    public Order(String id, Map<String, Integer> items, String supplierId, String locationId, double totalWeight) {
        this.id = id;
        this.items = items;
        this.supplierId = supplierId;
        this.locationId = locationId;
        this.totalWeight = totalWeight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public void setItems(Map<String, Integer> items) {
        this.items = items;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    @Override
    public String toString() {
        return "Order {" +
                "id='" + id + '\'' +
                ", items=" + items +
                ", supplierId='" + supplierId + '\'' +
                ", locationId='" + locationId + '\'' +
                ", totalWeight=" + totalWeight +
                '}';
    }
}
