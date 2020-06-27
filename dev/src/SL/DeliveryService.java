package SL;

import BL.Transports.DeliveryPackage.*;
import DL.Transports.DTO;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DeliveryService {

    private DeliveryController deliveryController;

    public DeliveryService()
    {
        deliveryController = DeliveryController.getInstance();
    }

    public Location createLocation(int id, String name, String address, String telNumber, String contactName, String shippingArea) throws Exception
    {
        try
        {
            Location l = deliveryController.createLocation(id, name, address, telNumber, contactName, shippingArea);
            return l;
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void addLocation(Location location) throws Exception
    {
        try
        {
            deliveryController.addLocation(location);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void removeLocation(int id) throws Exception
    {
        try
        {
            deliveryController.removeLocation(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void changetelNumber(int id, String telNumber) throws Exception
    {
        try
        {
            deliveryController.changetelNumber(id, telNumber);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void changecontactName(int id, String contactName) throws Exception
    {
        try
        {
            deliveryController.changecontactName(id, contactName);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public Truck createTruck(String id, String model, double netoWeight, double totalWeight) throws Exception
    {
        try
        {
            Truck t = deliveryController.createTruck(id, model, netoWeight, totalWeight);
            return t;
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void addTruck(Truck truck) throws Exception
    {
        try
        {
            deliveryController.addTruck(truck);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public Truck getTruck(String id) throws Exception {
        try
        {
            return deliveryController.getTruck(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void removeTruck(String id) throws Exception
    {
        try
        {
            deliveryController.removeTruck(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void setTruckUsed(String id) throws Exception
    {
        try
        {
            deliveryController.setTruckUsed(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void setTruckNotUsed(String id) throws Exception
    {
        try
        {
            deliveryController.setTruckNotUsed(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public Order createOrder(int id, Map<String, Integer> items, String supplierId, int locationId, double totalWeight) throws Exception
    {
        try
        {
            Order o = deliveryController.createOrder(id, items, supplierId, locationId, totalWeight);

            return o;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public Map<Integer, Order> getOrders()
    {
        return deliveryController.getOrders();
    }

    public void addOrder(Order order) throws Exception
    {
        try
        {
            deliveryController.addOrder(order);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void removeOrder(int id) throws Exception
    {

        try
        {
            deliveryController.removeOrder(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void addItem(int id, String item, int quantity) throws Exception
    {
        try
        {
            deliveryController.addItem(id, item, quantity);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void removeItem(int id, String item) throws Exception
    {
        try
        {
            deliveryController.removeItem(id, item);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void changeQuantity(int id, String item, int quantity) throws Exception
    {
        try
        {
            deliveryController.changeQuantity(id, item, quantity);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void changeTotalWeight(int id, double totalWeight) throws Exception
    {
        try
        {
            deliveryController.changeTotalWeight(id, totalWeight);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public Delivery createDelivery(String id, Date deliveryDay, Time leavingTime, int driverId, int srcLocation, List<Integer> targetLocation,
                                   String truckId, List<Integer> orders) throws Exception
    {
        try {
            Delivery d= deliveryController.createDelivery(id, deliveryDay, leavingTime, driverId, srcLocation, targetLocation, truckId, orders);

            return d;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public boolean checkArea(List<Integer> locationAreas){
        try
        {
            return deliveryController.checkArea(locationAreas);
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public void addDelivery(Delivery delivery) throws Exception {
        try
        {
            deliveryController.addDelivery(delivery);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void removeDelivery(String id) throws Exception {
       try
       {
           deliveryController.removeDelivery(id);
       }
       catch (Exception e)
       {
           throw e;
       }
    }

    public void changeDeliveryDay(String id, Date deliveryDay) throws Exception {
        try
        {
            deliveryController.changeDeliveryDay(id, deliveryDay);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void changeLeavingTime(String id, Time leavingTime) throws Exception {
        try
        {
            deliveryController.changeLeavingTime(id, leavingTime);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void changeDriverId(String id, int driverId) throws Exception {
        try
        {
            deliveryController.changeDriverId(id, driverId);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void removeOrderAndLocation(String id, int locationId, int orderId) throws Exception {
        try
        {
            deliveryController.removeOrderAndLocation(id, locationId, orderId);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void addOrderAndLocation(String id, int locationId, int orderId) throws Exception {
        try
        {
            deliveryController.addOrderAndLocation(id, locationId, orderId);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void changeWeight(String id, double weight) throws Exception {
        try
        {
            deliveryController.changeWeight(id, weight);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void changeTruckId(String id, String truckId) throws Exception {
        try
        {
            deliveryController.changeTruckId(id, truckId);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public boolean changeStatus(String id, String status) throws Exception {
        try
        {
           return deliveryController.changeStatus(id, status);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public Date getDeliveryDate(String id) throws Exception {
        try {
            return deliveryController.getDeliveryDate(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public double getDeliveryTruckWeight(String id) throws Exception {
        try {
            return deliveryController.getDeliveryTruckWeight(id);
        }
        catch (Exception e){
            throw e;
        }
        }
    public double getWeightForType(String type)
    {
        return deliveryController.getWeightForType(type);
    }
    public int getDeliveryDriverID(String id) throws Exception
    {
        try
        {
            return deliveryController.getDeliveryDriverID(id);
        }catch (Exception e){
            throw e;
        }
    }

    public Time getDeliveryLeavingTime(String id) throws Exception
    {
        try {
            return deliveryController.getDeliveryLeavingTime(id);
        }catch (Exception e)
        {
            throw e;
        }
    }

    public void printDeliveries() throws SQLException { deliveryController.printDeliveries();}
    public void printOrders() throws SQLException { deliveryController.printOrders();}
    public void printTrucks() throws SQLException { deliveryController.printTrucks();}
    public void printLocations() throws SQLException { deliveryController.printLocations();}
}