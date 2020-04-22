package BL.DeliveryPackage;

import java.sql.Time;
import java.util.*;

public class Delivery {

    public enum Status {
        Created , InTransit , Delivered ;
    }
    private String id;
    private Date deliveryDay;
    private Time leavingTime;
    private String driverId;
    private String srcLocation;
    private List<String> targetLocation;
    private double weight;
    private String truckId;
    private List<String> orders;
    private Status status;

    public Delivery(String id, Date deliveryDay, Time leavingTime, String driverId, String srcLocation, List<String> targetLocation, double weight, String truckId, List<String> orders) {
        this.id = id;
        this.deliveryDay = deliveryDay;
        this.leavingTime = leavingTime;
        this.driverId = driverId;
        this.srcLocation = srcLocation;
        this.targetLocation = targetLocation;
        this.weight = weight;
        this.truckId = truckId;
        this.orders = orders;
        this.status = Status.Created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDeliveryDay() {
        return deliveryDay;
    }

    public void setDeliveryDay(Date deliveryDay) {
        this.deliveryDay = deliveryDay;
    }

    public Time getLeavingTime() {
        return leavingTime;
    }

    public void setLeavingTime(Time leavingTime) {
        this.leavingTime = leavingTime;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getSrcLocation() {
        return srcLocation;
    }

    public void setSrcLocation(String srcLocation) {
        this.srcLocation = srcLocation;
    }

    public List<String> getTargetLocation() {
        return targetLocation;
    }

    public void removeTargetLocation(String targetLocation) {
        this.targetLocation.remove(targetLocation);
    }

    public void addTargetLocation(String targetLocation) {
        this.targetLocation.add(targetLocation);
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getTruckId() {
        return truckId;
    }

    public void setTruckId(String truckId) {
        this.truckId = truckId;
    }

    public List<String> getOrders() {
        return orders;
    }

    public void removeOrder(String orderId) {
        this.targetLocation.remove(orderId);
    }

    public void addOrder(String orderId) {
        this.targetLocation.add(orderId);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Delivery {" +
                "id='" + id + '\'' +
                ", deliveryDay=" + deliveryDay +
                ", leavingTime=" + leavingTime +
                ", driverId='" + driverId + '\'' +
                ", srcLocation='" + srcLocation + '\'' +
                ", targetLocation=" + targetLocation +
                ", weight=" + weight +
                ", truckId='" + truckId + '\'' +
                ", orders=" + orders +
                ", status=" + status +
                '}';
    }
}
