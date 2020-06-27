package BL.Transports.DeliveryPackage;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Delivery {

    public enum Status {
        Created , InTransit , Delivered ;
    }
    private String id;
    private Date deliveryDay;
    private Time leavingTime;
    private int driverId;
    private int srcLocation;
    private List<Integer> targetLocation;
    private double weight;
    private String truckId;
    private List<Integer> orders;
    private Status status;

    public Delivery(String id, Date deliveryDay, Time leavingTime, int driverId, int srcLocation, List<Integer> targetLocation, double weight, String truckId, List<Integer> orders) {
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
    public Delivery(String id, Date deliveryDay, Time leavingTime, int driverId, int srcLocation, List<Integer> targetLocation, double weight, String truckId, List<Integer> orders,String status) {
        this.id = id;
        this.deliveryDay = deliveryDay;
        this.leavingTime = leavingTime;
        this.driverId = driverId;
        this.srcLocation = srcLocation;
        this.targetLocation = targetLocation;
        this.weight = weight;
        this.truckId = truckId;
        this.orders = orders;
        this.status = Status.valueOf(status);
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

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getSrcLocation() {
        return srcLocation;
    }

    public void setSrcLocation(int srcLocation) {
        this.srcLocation = srcLocation;
    }

    public List<Integer> getTargetLocation() {
        return targetLocation;
    }

    public void removeTargetLocation(int targetLocation) {
        this.targetLocation.remove(targetLocation);
    }

    public void addTargetLocation(int targetLocation) {
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

    public List<Integer> getOrders() {
        return orders;
    }

    public void removeOrder(int orderId) {
        this.targetLocation.remove(orderId);
    }

    public void addOrder(int orderId) {
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
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return "id:" + id +
                "\ndeliveryDay:" + dateFormat.format(deliveryDay) +
                "\nleavingTime:" + leavingTime +
                "\ndriverId:" + driverId + '\'' +
                "\nsrcLocation:" + srcLocation + '\'' +
                "\ntargetLocation:" + targetLocation.toString() +
                "\nweight=" + weight +
                "\ntruckId='" + truckId + '\'' +
                "\norders=" + orders.toString() +
                "\nstatus=" + status +'\n';


    }
}