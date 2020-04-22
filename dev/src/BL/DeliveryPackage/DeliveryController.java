package BL.DeliveryPackage;

import BL.DriverPackage.Driver;
import BL.DriverPackage.DriverController;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class DeliveryController {

    private final Map<String, Double> licenseTypes = new HashMap<String, Double>() {
        {
            put("A", 5000.0);
            put("B", 10000.0);
            put("C", 15000.0);
            put("D", 20000.0);
        }
    };
    private Map<String, Delivery> deliveries;
    private static DeliveryController deliveryController = null;
    private DriverController driverController;
    private TruckController truckController;
    private LocationController locationController;
    private OrderController orderController;

    private DeliveryController()
    {
        this.deliveries = new HashMap<>();
        driverController = DriverController.getInstance();
        truckController = TruckController.getInstance();
        locationController = LocationController.getInstance();
        orderController = OrderController.getInstance();
    }

    public static DeliveryController getInstance()
    {
        if(deliveryController == null)
            deliveryController = new DeliveryController();
        return deliveryController;
    }

    public Delivery getDelivery(String id) throws Exception {
        if (!deliveries.containsKey(id))
            throw new Exception("the delivery doesn't exists");
        return deliveries.get(id);
    }

    public Location getLocation(String id) throws Exception {
        if (locationController.getLocation(id) == null)
            throw new Exception("the delivery doesn't exists");
        return locationController.getLocation(id);
    }

    public Delivery createDelivery(String id, Date deliveryDay, Time leavingTime, String driverId, String srcLocation, List<String> targetLocation,
                                   String truckId, List<String> orders) throws Exception
    {
        double weight = 0.0;
        for (String s : orders)
        {
            if(!this.getOrders().containsKey(s))
                throw new Exception("the order is not exist");
            weight += orderController.getOrder(s).getTotalWeight();
        }
        weight += truckController.getTruck(truckId).getNetoWeight();
        Date date = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        Calendar cal = Calendar.getInstance();
        Time time = new Time(timeFormat.parse(timeFormat.format(cal.getTime())).getTime());
        if (deliveries.containsKey(id))
            throw new Exception("the delivery already exists");
        if (deliveryDay.compareTo(date) < 0 )
            throw new Exception("delivery date must be future date");
        if(driverController.getDriver(driverId).getExpLicenseDate().compareTo(deliveryDay) < 0)
            throw new Exception("the driver cannot drive the delivery without a valid license");
        if (leavingTime.compareTo(time) < 0)
            throw new Exception("delivery time must be future time");
        if(weight <= 0)
            throw new Exception("weight must be greater than 0");
        if(weight > truckController.getTruck(truckId).getTotalWeight())
            throw new Exception("the weight of the order and the truck bigger than the max weight");
        if(truckController.getTruck(truckId).isUsed())
            throw new Exception("the truck is used");
        if(driverController.getDriver(driverId).isDriving())
            throw new Exception("the driver is driving");
        if(licenseTypes.get(driverController.getDriver(driverId).getLicenseType()) < truckController.getTruck(truckId).getTotalWeight())
            throw new Exception("the driver cannot drive the truck");
        for (Delivery d : deliveries.values())
        {
            if(d.getDeliveryDay().compareTo(deliveryDay) == 0)
                if(d.getTruckId().compareTo(truckId) == 0 || d.getDriverId().compareTo(driverId) == 0)
                    throw new Exception("the driver or the truck already in use");
        }
        if(!checkArea(targetLocation))
            throw new Exception("location in another area");
        Delivery delivery = new Delivery(id, deliveryDay, leavingTime, driverId, srcLocation, targetLocation, weight, truckId, orders);
        deliveryController.addDelivery(delivery);
        return delivery;
    }

    public boolean checkArea(List<String> locationAreas){
        try
        {
            for (String id : locationAreas)
                if(locationController.getLocation(id).getShippingArea().compareTo(locationController.getLocation(locationAreas.get(0)).getShippingArea()) != 0)
                    return false;
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public void addDelivery(Delivery delivery) throws Exception {
        if (deliveries.containsKey(delivery.getId()))
            throw new Exception("the delivery already exists");
        this.deliveries.put(delivery.getId(), delivery);
    }

    public void removeDelivery(String id) throws Exception {
        if (!deliveries.containsKey(id))
            throw new Exception("the delivery doesn't exists");
        this.deliveries.remove(id);
    }

    public void changeDeliveryDay(String id, Date deliveryDay) throws Exception {
        Date date = new Date();
        if (!deliveries.containsKey(id))
            throw new Exception("the delivery doesn't exists");
        if (deliveryDay.compareTo(date) < 0 )
            throw new Exception("delivery date must be future date");
        deliveries.get(id).setDeliveryDay(deliveryDay);
    }

    public void changeLeavingTime(String id, Time leavingTime) throws Exception {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        Calendar cal = Calendar.getInstance();
        Time time = new Time(timeFormat.parse(timeFormat.format(cal.getTime())).getTime());
        if (!deliveries.containsKey(id))
            throw new Exception("the delivery doesn't exists");
        if (leavingTime.compareTo(time) < 0)
            throw new Exception("delivery time must be future time");
        deliveries.get(id).setLeavingTime(leavingTime);
    }

    public void changeDriverId(String id, String driverId) throws Exception {
        if (!deliveries.containsKey(id))
            throw new Exception("the delivery doesn't exists");
        if(driverController.getDriver(driverId).getExpLicenseDate().compareTo(deliveries.get(id).getDeliveryDay()) < 0)
            throw new Exception("the driver cannot drive the delivery without a valid license");
        for (Delivery d : deliveries.values())
        {
            if(d.getDeliveryDay().compareTo(deliveries.get(id).getDeliveryDay()) == 0 && d.getId().compareTo(id) != 0)
                if(d.getDriverId().compareTo(driverId) == 0)
                    throw new Exception("the driver already in use");
        }
        try {
            Driver d = driverController.getDriver(driverId);
            if(licenseTypes.get(d.getLicenseType()) < truckController.getTruck(deliveries.get(id).getTruckId()).getTotalWeight())
                throw new Exception("the driver cannot drive the truck");
            deliveries.get(id).setDriverId(driverId);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void removeOrderAndLocation(String id, String locationId, String orderId) throws Exception {
        if (!deliveries.containsKey(id))
            throw new Exception("the delivery doesn't exists");
        if (!deliveries.get(id).getTargetLocation().contains(locationId))
            throw new Exception("the target location doesn't exists in the delivery");
        if (!deliveries.get(id).getOrders().contains(orderId))
            throw new Exception("the order doesn't exists in the delivery");
        try {
            Location l = locationController.getLocation(locationId);
            Order o = orderController.getOrder(orderId);
            deliveries.get(id).removeTargetLocation(locationId);
            deliveries.get(id).removeOrder(orderId);
        } catch (Exception e)
        {
            throw e;
        }
    }

    public void addOrderAndLocation(String id, String locationId, String orderId) throws Exception {
        if (!deliveries.containsKey(id))
            throw new Exception("the delivery doesn't exists");
        if (deliveries.get(id).getTargetLocation().contains(locationId))
            throw new Exception("the target location already exists in the delivery");
        if (deliveries.get(id).getOrders().contains(orderId))
            throw new Exception("the order already exists in the delivery");
        if(locationId.compareTo(locationController.getLocation(deliveries.get(id).getTargetLocation().get(0)).getShippingArea()) != 0)
            throw new Exception("location in another area");
        try {
            Location l = locationController.getLocation(locationId);
            Order o = orderController.getOrder(orderId);
            deliveries.get(id).addTargetLocation(locationId);
            deliveries.get(id).addOrder(orderId);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void changeWeight(String id, double weight) throws Exception {
        if (!deliveries.containsKey(id))
            throw new Exception("the delivery doesn't exists");
        if(weight <= 0)
            throw new Exception("the weight is lower than 0");
        if(weight + truckController.getTruck(deliveries.get(id).getTruckId()).getNetoWeight() > truckController.getTruck(deliveries.get(id).getTruckId()).getTotalWeight())
            throw new Exception("the weight of the order and the truck bigger than the max weight");
        deliveries.get(id).setWeight(weight);
    }

    public void changeTruckId(String id, String truckId) throws Exception {
        if (!deliveries.containsKey(id))
            throw new Exception("the delivery doesn't exists");
        for (Delivery d : deliveries.values())
        {
            if(d.getDeliveryDay().compareTo(deliveries.get(id).getDeliveryDay()) == 0 && d.getId().compareTo(id) != 0)
                if(d.getTruckId().compareTo(truckId) == 0)
                    throw new Exception("the truck already in use");
        }
        try {
            Truck t = truckController.getTruck(truckId);
            if(licenseTypes.get(driverController.getDriver(deliveries.get(id).getDriverId()).getLicenseType()) < truckController.getTruck(truckId).getTotalWeight())
                throw new Exception("the driver cannot drive the truck");
            deliveries.get(id).setTruckId(truckId);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void changeStatus(String id, String status) throws Exception {
        if (!deliveries.containsKey(id))
            throw new Exception("the delivery doesn't exists");
        if(status.compareTo("InTransit") != 0 && status.compareTo("Delivered") != 0)
            throw new Exception("status can be changed only to InTransit or Delivered");
        if(status.compareTo("InTransit") == 0)
            deliveries.get(id).setStatus(Delivery.Status.InTransit);
        if(status.compareTo("Delivered") == 0)
            deliveries.get(id).setStatus(Delivery.Status.Delivered);
    }

    public Order createOrder(String id, Map<String, Integer> items, String supplierId, String locationId, double totalWeight) throws Exception
    {
        try
        {
            Order o = orderController.createOrder(id, items, supplierId, locationId, totalWeight);
            orderController.addOrder(o);
            return o;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public Map<String, Order> getOrders()
    {
        return orderController.getOrders();
    }

    public void addOrder(Order order) throws Exception
    {
        try
        {
            orderController.addOrder(order);
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
            orderController.removeOrder(orderController.getOrder(id));
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
            orderController.addItem(id, item, quantity);
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
            orderController.removeItem(id, item);
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
            orderController.changeQuantity(id, item, quantity);
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
            orderController.changeTotalWeight(id, totalWeight);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public Location createLocation(String id, String name, String address, String telNumber, String contactName, String shippingArea) throws Exception
    {
        try
        {
            Location l = locationController.createLocation(id, name, address, telNumber, contactName, shippingArea);
            locationController.addLocation(l);
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
            locationController.addLocation(location);
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
            locationController.removeLocation(locationController.getLocation(id));
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
            locationController.changetelNumber(id, telNumber);
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
            locationController.changecontactName(id, contactName);
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
            Truck t = truckController.createTruck(id, model, netoWeight, totalWeight);
            truckController.addTruck(t);
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
            truckController.addTruck(truck);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public Truck getTruck(String id) throws Exception {
        try
        {
            return truckController.getTruck(id);
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
            truckController.removeTruck(truckController.getTruck(id));
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
            truckController.setTruckUsed(id);
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
            truckController.setTruckNotUsed(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public Driver createDriver(String id, String name, String licenseType, Date expLicenseDate) throws Exception
    {
        try
        {
            if(!licenseTypes.containsKey(licenseType))
                throw new Exception("not valid license type");
            Driver d = driverController.createDriver(id, name, licenseType, expLicenseDate);
            driverController.addDriver(d);
            return d;
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public Driver getDriver(String id) throws Exception {
        try
        {
            return driverController.getDriver(id);
        }
        catch (Exception e)
        {
            throw e;
        }

    }

    public void addDriver(Driver driver) throws Exception
    {
        try
        {
            driverController.addDriver(driver);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void removeDriver(String id) throws Exception
    {
        try
        {
            driverController.removeDriver(driverController.getDriver(id));
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void changeExpDate(String id, Date expLicenseDate) throws Exception
    {
        try
        {
            driverController.changeExpDate(id, expLicenseDate);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void changeLicenseType(String id, String licenseType) throws Exception
    {
        try
        {
            if(!licenseTypes.containsKey(licenseType))
                throw new Exception("not valid license type");
            driverController.changeLicenseType(id, licenseType);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void setDriverToDrive(String id) throws Exception
    {
        try
        {
            driverController.setDriverToDrive(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    public void setDriverNotToDrive(String id) throws Exception
    {
        try
        {
            driverController.setDriverNotToDrive(id);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public Map<String, Delivery> getDeliveries()
    {
        return deliveries;
    }
    public Map<String, Driver> getDrivers()
    {
        return driverController.getDrivers();
    }
    public Map<String, Truck> getTrucks()
    {
        return truckController.getTrucks();
    }
    public Map<String, Location> getLocations()
    {
        return locationController.getLocations();
    }
}
