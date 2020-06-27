package BL.Transports.DeliveryPackage;

import DataAccessLaye.Transports.DTO;
import bussinessLayer.DTOPackage.LineCatalogItemDTO;
import bussinessLayer.DTOPackage.OrderDTO;
import bussinessLayer.InventoryPackage.Inventory;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;

public class DeliveryController {
    //decided morning shift is from 6-14 , evening shift 14-22
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
    private TruckController truckController;
    private LocationController locationController;
    //private OrderController orderController;
    private Inventory inventory;
    private int index = 1;

    private DeliveryController()
    {
        this.deliveries = new HashMap<>();
        truckController = TruckController.getInstance();
        locationController = LocationController.getInstance();
        //orderController = OrderController.getInstance();
        inventory = Inventory.getInstance();
    }

    public static DeliveryController getInstance()
    {
        if(deliveryController == null)
            deliveryController = new DeliveryController();
        return deliveryController;
    }

    public Delivery getDelivery(String id) throws Exception {
        Delivery d= DataAccessLaye.Transports.Delivery.checkDelivery(id);
        if (d==null)
            throw new Exception("the delivery doesn't exists");
        return d;
    }

    /*public Location getLocation(int id) throws Exception {
        if (locationController.getLocation(id) == null)
            throw new Exception("the delivery doesn't exists");
        return locationController.getLocation(id);
    }*/

    public Delivery createDelivery(String id, Date deliveryDay, Time leavingTime, int driverId, int srcLocation, int targetLocation,
                                   String truckId, OrderDTO order) throws Exception
    {
        double weight = 0.0;
        for (LineCatalogItemDTO item : order.getCart().getLineItems())
        {
            weight += (inventory.getItemWeight(item.getCatalogItem().getItemId()) * item.getAmount());
        }
        weight += truckController.getTruck(truckId).getNetoWeight();
        Date date = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        Calendar cal = Calendar.getInstance();
        Time time = new Time(timeFormat.parse(timeFormat.format(cal.getTime())).getTime());
        if (DataAccessLaye.Transports.Delivery.checkDelivery(id)!=null)
            throw new Exception("the delivery already exists");
        if (deliveryDay.compareTo(date) < 0 )
            throw new Exception("delivery date must be future date");
        if (leavingTime.compareTo(time) < 0)
            throw new Exception("delivery time must be future time");
        if(weight <= 0)
            throw new Exception("weight must be greater than 0");
        if(weight > truckController.getTruck(truckId).getTotalWeight())
            throw new Exception("the weight of the order and the truck bigger than the max weight");
        if(DataAccessLaye.Transports.Delivery.checkDTforDate(id, new java.sql.Date(deliveryDay.getTime()),driverId,truckId))
            throw new Exception("the truck or driver is used at the same date");
        if(locationController.getLocation(srcLocation)==null)
            throw new Exception("sorce location doesn't exists");
//        if(!checkArea(targetLocation))
//            throw new Exception("locations are not in the another area");

        Delivery delivery = new Delivery(String.valueOf(index), deliveryDay, leavingTime, driverId, srcLocation, targetLocation, weight, truckId, order);
        index++;
        deliveryController.addDelivery(delivery);
        return delivery;
    }

    public Delivery createDelivery(OrderDTO order) throws Exception
    {
        double weight = 0.0;
        for (LineCatalogItemDTO item : order.getCart().getLineItems())
        {
            weight += (inventory.getItemWeight(item.getCatalogItem().getItemId()) * item.getAmount());
        }
        Date date = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        Calendar cal = Calendar.getInstance();
        if (Date.from(order.getDeliveryDate().atZone(ZoneId.systemDefault()).toInstant()).compareTo(date) < 0)
            throw new Exception("delivery date must be future date");
        if(weight <= 0)
            throw new Exception("weight must be greater than 0");
        if(locationController.getLocation(order.getSupplierId())==null)
            throw new Exception("source location doesn't exists");
//        if(!checkArea(targetLocation))
//            throw new Exception("locations are not in the another area");
        Time time = new Time(order.getDeliveryDate().getHour(), order.getDeliveryDate().getMinute(), order.getDeliveryDate().getSecond());
        Delivery delivery = new Delivery(String.valueOf(index), Date.from(order.getDeliveryDate().atZone(ZoneId.systemDefault()).toInstant()), time, 0, order.getSupplierId(), order.getBranchId(), weight, null, order);
        index++;
        deliveryController.addDelivery(delivery);
        return delivery;
    }

//    public boolean checkArea(int locationAreas){
//        try
//        {
//            for (Integer id : locationAreas)
//                if(locationController.getLocation(id).getShippingArea().compareTo(locationController.getLocation(locationAreas.get(0)).getShippingArea()) != 0)
//                    return false;
//            return true;
//        }
//        catch (Exception e)
//        {
//            return false;
//        }
//    }

    public void addDelivery(Delivery delivery) throws Exception {
        if (DataAccessLaye.Transports.Delivery.checkDelivery(delivery.getId())!=null)
            throw new Exception("the delivery already exists");
        this.deliveries.put(delivery.getId(), delivery);
        DataAccessLaye.Transports.Delivery.insertDelivery(new DTO.Delivery(delivery.getId(),delivery.getDeliveryDay(),delivery.getLeavingTime(),delivery.getDriverId(),delivery.getSrcLocation(),delivery.getTargetLocation(),delivery.getWeight(),delivery.getTruckId(),delivery.getStatus().toString()));
        for ( item: delivery.getOrders().getCart().getLineItems()) {
            DataAccessLaye.Transports.Delivery.insertItemsForOrders(new DTO.ItemsForOrders(delivery.getId(),delivery.getOrders().getOrderId(),));

        }
        //DataAccessLaye.Transports.Delivery.insertDeliveryTargetLocation(new DTO.DeliverytargetLocation(delivery.getId(),delivery.getTargetLocation()));
//        for (int o: delivery.getOrders())
//        {
//            DataAccessLaye.Transports.Delivery.insertOrdersForDeliveries(new DTO.OrdersForDelivery(delivery.getId(),o));
//        }
    }

    public void removeDelivery(String id) throws Exception {
        Delivery d= DataAccessLaye.Transports.Delivery.checkDelivery(id);
        if (d==null)
            throw new Exception("the delivery doesn't exists");
        if(d.getStatus().equals(Delivery.Status.InTransit) ||d.getStatus().equals(Delivery.Status.Delivered))
            throw new Exception("edit delivery details only for Created delivery");
        //this.deliveries.remove(id);
        DataAccessLaye.Transports.Delivery.deleteDelivery(id);
    }

    public void changeDeliveryDay(String id, Date deliveryDay) throws Exception {
        Date date = new Date();
        Delivery d= DataAccessLaye.Transports.Delivery.checkDelivery(id);
        if (d==null)
            throw new Exception("the delivery doesn't exists");
        if (deliveryDay.compareTo(date) < 0 )
            throw new Exception("delivery date must be future date");
        if(d.getStatus().equals(Delivery.Status.InTransit) || d.getStatus().equals(Delivery.Status.Delivered))
            throw new Exception("edit delivery details only for Created delivery");
        //deliveries.get(id).setDeliveryDay(deliveryDay);
        DataAccessLaye.Transports.Delivery.updateDeliveryDay(id,deliveryDay);
    }

    public void changeLeavingTime(String id, Time leavingTime) throws Exception {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        Calendar cal = Calendar.getInstance();
        Time time = new Time(timeFormat.parse(timeFormat.format(cal.getTime())).getTime());
        Delivery d= DataAccessLaye.Transports.Delivery.checkDelivery(id);
        if (d==null)
            throw new Exception("the delivery doesn't exists");
        if(leavingTime.after(Time.valueOf("22:00:00")) || leavingTime.before(Time.valueOf("7:00:00")))
            throw new Exception("delivery time must be future time");
        if(d.getStatus().equals(Delivery.Status.InTransit) || d.getStatus().equals(Delivery.Status.Delivered))
            throw new Exception("edit delivery details only for Created delivery");
        //deliveries.get(id).setLeavingTime(leavingTime);
        DataAccessLaye.Transports.Delivery.updateLeavingTime(id,leavingTime);
    }

    public void changeDriverId(String id, int driverId) throws Exception {
        Delivery d= DataAccessLaye.Transports.Delivery.checkDelivery(id);
        if (d==null)
            throw new Exception("the delivery doesn't exists");

        if(d.getStatus().equals(Delivery.Status.InTransit) || d.getStatus().equals(Delivery.Status.Delivered))
            throw new Exception("edit delivery details only for Created delivery");
        if(DataAccessLaye.Transports.Delivery.checkDriverForDel(id,new java.sql.Date(d.getDeliveryDay().getTime()),driverId))
                    throw new Exception("the driver already in use");

        try {

            DataAccessLaye.Transports.Delivery.updateDriverId(id,driverId);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void removeOrderAndLocation(String id, int locationId, int orderId) throws Exception {
        Delivery d= DataAccessLaye.Transports.Delivery.checkDelivery(id);
        if (d==null)
            throw new Exception("the delivery doesn't exists");
        if (d.getTargetLocation() != locationId)
            throw new Exception("the target location doesn't exists in the delivery");
        if (d.getOrders().getOrderId() != orderId)
            throw new Exception("the order doesn't exists in the delivery");
        if(d.getStatus().equals(Delivery.Status.InTransit) || d.getStatus().equals(Delivery.Status.Delivered))
            throw new Exception("edit delivery details only for Created delivery");
        try {
            /*Location l = locationController.getLocation(locationId);
            Order o = orderController.getOrder(orderId);
            deliveries.get(id).removeTargetLocation(locationId);
            deliveries.get(id).removeOrder(orderId);*/
            DataAccessLaye.Transports.Delivery.removeOrderAndLocation(id,locationId,orderId);
        } catch (Exception e)
        {
            throw e;
        }
    }

    public void addOrderAndLocation(String id, int locationId, int orderId) throws Exception {
        Delivery d= DataAccessLaye.Transports.Delivery.checkDelivery(id);

        if (d==null)
            throw new Exception("the delivery doesn't exists");
        if (d.getTargetLocation() != locationId)
            throw new Exception("the target location already exists in the delivery");
        if (d.getOrders().getOrderId() != orderId)
            throw new Exception("the order already exists in the delivery");
//        if(locationController.getLocation(locationId).getShippingArea().compareTo(locationController.getLocation(d.getTargetLocation().get(0)).getShippingArea()) != 0)
//            throw new Exception("location in another area");
        if(d.getStatus().equals(Delivery.Status.InTransit) || d.getStatus().equals(Delivery.Status.Delivered))
            throw new Exception("edit delivery details only for Created delivery");
        try {
//            if(d.getWeight() + orderController.getOrder(orderId).getTotalWeight() >
//                    truckController.getTruck(d.getTruckId()).getTotalWeight())
//                throw new Exception("cannot add the order to the delivery, the weight of the delivery passes the max capacity of the truck");
            DataAccessLaye.Transports.Delivery.addOrderAndLocation(id,locationId,orderId);
//            double we=d.getWeight() + orderController.getOrder(orderId).getTotalWeight();
//            DataAccessLaye.Transports.Delivery.updateDelWeight(id,we);

        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void changeWeight(String id, double weight) throws Exception {
        Delivery d= DataAccessLaye.Transports.Delivery.checkDelivery(id);
        if (d==null)
            throw new Exception("the delivery doesn't exists");
        if(weight <= 0)
            throw new Exception("the weight is lower than 0");
        if(weight + truckController.getTruck(d.getTruckId()).getNetoWeight() > truckController.getTruck(d.getTruckId()).getTotalWeight())
            throw new Exception("the weight of the order and the truck bigger than the max weight");
        if(d.getStatus().equals(Delivery.Status.InTransit) || d.getStatus().equals(Delivery.Status.Delivered))
            throw new Exception("edit delivery details only for Created delivery");
        //deliveries.get(id).setWeight(weight);
        DataAccessLaye.Transports.Delivery.updateDelWeight(id,weight+truckController.getTruck(d.getTruckId()).getNetoWeight());
    }

    public void changeTruckId(String id, String truckId) throws Exception {
        Delivery d= DataAccessLaye.Transports.Delivery.checkDelivery(id);
        if (d==null)
            throw new Exception("the delivery doesn't exists");
        if(d.getStatus().equals(Delivery.Status.InTransit) || d.getStatus().equals(Delivery.Status.Delivered))
            throw new Exception("edit delivery details only for Created delivery");
        if(DataAccessLaye.Transports.Truck.checkTruck(truckId)==null)
            throw new Exception("the truck doesn't exists");
        if(d.getWeight()>truckController.getTruck(truckId).getTotalWeight())
            throw new Exception("weight of delivery is bigger then the truck's max weight");
        if(DataAccessLaye.Transports.Delivery.checkTruckForDel(id,new java.sql.Date(d.getDeliveryDay().getTime()),truckId))
            throw new Exception("the truck is already is used at the same");

        try {

            //deliveries.get(id).setTruckId(truckId);
            DataAccessLaye.Transports.Delivery.updateTruckID(id,truckId);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public boolean changeStatus(String id, String status) throws Exception {
        Delivery d= DataAccessLaye.Transports.Delivery.checkDelivery(id);
        if (d==null)
            throw new Exception("the delivery doesn't exists");
        if(status.compareTo("InTransit") != 0 && status.compareTo("Delivered") != 0)
            throw new Exception("status can be changed only to InTransit or Delivered");
        /*if(d.getWeight()>truckController.getTruck(d.getTruckId()).getTotalWeight())
            throw new Exception("weight of delivery is bigger then the truck's max weight");*/
        if(status.compareTo("InTransit") == 0 && d.getWeight() <= truckController.getTruck(d.getTruckId()).getTotalWeight())
        {
            d.setStatus(Delivery.Status.InTransit);
            DataAccessLaye.Transports.Delivery.updateStatus(id,status);
            truckController.getTruck(d.getTruckId()).setUsed();
            DataAccessLaye.Transports.Truck.updateUsed(d.getTruckId(),true);
            //driverController.getDriver(deliveries.get(id).getDriverId()).setDriving();
        }
        else
            if(status.compareTo("InTransit") == 0)
                throw new Exception("cannot start the delivery process, the weight of the delivery is bigger than the total weight possible\n" +
                    "please rearrange the delivery");
        if(status.compareTo("Delivered") == 0)
        {
            d.setStatus(Delivery.Status.Delivered);
            DataAccessLaye.Transports.Delivery.updateStatus(id,status);
            truckController.getTruck(d.getTruckId()).setNotUsed();
            DataAccessLaye.Transports.Truck.updateUsed(d.getTruckId(),false);
            //driverController.getDriver(deliveries.get(id).getDriverId()).setNotDriving();
        }
        return true;
    }

//    public Order createOrder(int id, Map<String, Integer> items, String supplierId, int locationId, double totalWeight) throws Exception
//    {
//        try
//        {
//            Order o = orderController.createOrder(id, items, supplierId, locationId, totalWeight);
//            orderController.addOrder(o);
//            return o;
//        }
//        catch (Exception e)
//        {
//            throw e;
//        }
//    }
//
//    public Map<Integer, Order> getOrders()
//    {
//        return orderController.getOrders();
//    }
//
//    public void addOrder(Order order) throws Exception
//    {
//        try
//        {
//            orderController.addOrder(order);
//        }
//        catch (Exception e)
//        {
//            throw e;
//        }
//    }
//    public void removeOrder(int id) throws Exception
//    {
//        if(DataAccessLaye.Transports.Delivery.checkOrder(id))
//            throw new Exception("cant change delivery that is InTransit or Delivered");
//        try
//        {
//            orderController.removeOrder(orderController.getOrder(id));
//        }
//        catch (Exception e)
//        {
//            throw e;
//        }
//    }
//    public void addItem(int id, String item, int quantity) throws Exception
//    {
//        if(DataAccessLaye.Transports.Delivery.checkOrder(id))
//            throw new Exception("cant change delivery that is InTransit or Delivered");
//        try
//        {
//            orderController.addItem(id, item, quantity);
//        }
//        catch (Exception e)
//        {
//            throw e;
//        }
//    }
//    public void removeItem(int id, String item) throws Exception
//    {
//        if(DataAccessLaye.Transports.Delivery.checkOrder(id))
//            throw new Exception("cant change delivery that is InTransit or Delivered");
//        try
//        {
//            orderController.removeItem(id, item);
//        }
//        catch (Exception e)
//        {
//            throw e;
//        }
//    }
//    public void changeQuantity(int id, String item, int quantity) throws Exception
//    {
//        if(DataAccessLaye.Transports.Delivery.checkOrder(id))
//            throw new Exception("cant change delivery that is InTransit or Delivered");
//        try
//        {
//            orderController.changeQuantity(id, item, quantity);
//        }
//        catch (Exception e)
//        {
//            throw e;
//        }
//    }
//    public void changeTotalWeight(int id, double totalWeight) throws Exception
//    {
//        if(DataAccessLaye.Transports.Delivery.checkOrder(id))
//            throw new Exception("cant change delivery that is InTransit or Delivered");
//        try
//        {
//            /*for (String deliveryId : deliveries.keySet())
//            {
//                if(deliveries.get(deliveryId).getOrders().contains(id) &&
//                        deliveries.get(deliveryId).getWeight() + totalWeight <= truckController.getTruck(deliveries.get(deliveryId).getTruckId()).getTotalWeight())
//                    deliveries.get(deliveryId).setWeight(deliveries.get(deliveryId).getWeight() + totalWeight);
//                else
//                    if(deliveries.get(deliveryId).getOrders().contains(id))
//                        throw new Exception("the weight of delivery: " + deliveryId + " passed the max weight possible");
//            }*/
//            orderController.changeTotalWeight(id, totalWeight);
//        }
//        catch (Exception e)
//        {
//            throw e;
//        }
//    }
    public Location createLocation(int id, String name, String address, String telNumber, String contactName, String shippingArea) throws Exception
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
    public void removeLocation(int id) throws Exception
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
    public void changetelNumber(int id, String telNumber) throws Exception
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
    public void changecontactName(int id, String contactName) throws Exception
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
            truckController.removeTruck(id);
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
    public Map<String, Delivery> getDeliveries()
    {
        return deliveries;
    }
    public Map<String, Truck> getTrucks()
    {
        return truckController.getTrucks();
    }
    public Map<Integer, Location> getLocations()
    {
        return locationController.getLocations();
    }
    public Date getDeliveryDate(String id) throws Exception {
        Delivery d= DataAccessLaye.Transports.Delivery.checkDelivery(id);
        if(d==null)
            throw new Exception("the delivery id does not exists");
        return d.getDeliveryDay();
    }
    public double getDeliveryTruckWeight(String id) throws Exception {
        Delivery d= DataAccessLaye.Transports.Delivery.checkDelivery(id);
        if(d==null)
            throw new Exception("the delivery id does not exists");

        return truckController.getTruck(d.getTruckId()).getTotalWeight();
    }

    public double getWeightForType(String type)
    {
        return licenseTypes.get(type);
    }

    public int getDeliveryDriverID(String id) throws Exception {
        Delivery d= DataAccessLaye.Transports.Delivery.checkDelivery(id);
        if(d==null)
            throw new Exception("the driver doesn't exists");
        return d.getDriverId();
    }
    public Time getDeliveryLeavingTime(String id) throws Exception {
        Delivery d= DataAccessLaye.Transports.Delivery.checkDelivery(id);
        if(d==null)
            throw new Exception("delivery doesn't exists");
        return d.getLeavingTime();

    }

    public void printDeliveries() throws Exception { DataAccessLaye.Transports.Delivery.printDeliveries(); }

    public void printLocations() throws Exception {  locationController.printLocations(); }

    //public void printOrders() throws SQLException{ orderController.printOrders(); }

    public  void printTrucks() throws Exception { truckController.printTrucks(); }

}