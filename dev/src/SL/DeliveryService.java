package SL;

import BL.DeliveryPackage.*;
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

    public Location createLocation(String id, String name, String address, String telNumber, String contactName, String shippingArea) throws Exception
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
    public void removeLocation(String id) throws Exception
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
    public void changetelNumber(String id, String telNumber) throws Exception
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
    public void changecontactName(String id, String contactName) throws Exception
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
            deliveryController.addTruck(t);
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
    public Order createOrder(String id, Map<String, Integer> items, String supplierId, String locationId, double totalWeight) throws Exception
    {
        try
        {
            Order o = deliveryController.createOrder(id, items, supplierId, locationId, totalWeight);
            deliveryController.addOrder(o);
            return o;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public Map<String, Order> getOrders()
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
    public void removeOrder(String id) throws Exception
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
    public void addItem(String id, String item, int quantity) throws Exception
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
    public void removeItem(String id, String item) throws Exception
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
    public void changeQuantity(String id, String item, int quantity) throws Exception
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
    public void changeTotalWeight(String id, double totalWeight) throws Exception
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
    public Delivery createDelivery(String id, Date deliveryDay, Time leavingTime, String driverId, String srcLocation, List<String> targetLocation,
                                   String truckId, List<String> orders) throws Exception
    {
        try {
            return deliveryController.createDelivery(id, deliveryDay, leavingTime, driverId, srcLocation, targetLocation, truckId, orders);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public boolean checkArea(List<String> locationAreas){
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

    public void changeDriverId(String id, String driverId) throws Exception {
        try
        {
            deliveryController.changeDriverId(id, driverId);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void removeOrderAndLocation(String id, String locationId, String orderId) throws Exception {
        try
        {
            deliveryController.removeOrderAndLocation(id, locationId, orderId);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void addOrderAndLocation(String id, String locationId, String orderId) throws Exception {
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

    public void changeStatus(String id, String status) throws Exception {
        try
        {
            deliveryController.changeStatus(id, status);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
}
