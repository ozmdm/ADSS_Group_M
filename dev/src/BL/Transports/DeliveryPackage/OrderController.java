package BL.Transports.DeliveryPackage;

import DL.Transports.DTO;

import java.sql.SQLException;
import java.util.*;

public class OrderController {

    private Map<Integer, Order> orders;
    private static OrderController orderController = null;

    private OrderController()
    {
        this.orders = new HashMap<>();
    }

    public static OrderController getInstance()
    {
        if(orderController == null)
            orderController = new OrderController();
        return orderController;
    }

    public Map<Integer, Order> getOrders()
    {
        return orders;
    }

    public Order getOrder(int id) throws Exception {
        Order o= DL.Transports.Order.checkOrder(id);
        if(o==null)
            throw new Exception("the order doesn't exists");
        return o;
    }

    public Order createOrder(int id, Map<String, Integer> items, String supplierId, int locationId, double totalWeight) throws Exception {
        if(DL.Transports.Order.checkOrder(id)!=null)
            throw new Exception("the order already exists");
        if(items.isEmpty())
            throw new Exception("can't create an order with no items");
        Order order = new Order(id, items, supplierId, locationId, totalWeight);
        return order;
    }

    public void addOrder(Order order) throws Exception {
        if(DL.Transports.Order.checkOrder(order.getId())!=null)
            throw new Exception("the order already exists");
        this.orders.put(order.getId(), order);
        DL.Transports.Order.insertOrder(new DTO.Order(order.getId(),order.getSupplierId(),order.getLocationId(),order.getTotalWeight()));
        for (Map.Entry<String,Integer > entry: order.getItems().entrySet()
        ) {
            DL.Transports.Order.insertItemsForOrders(new DTO.ItemsForOrders(order.getId(),entry.getKey(),entry.getValue()));
        }
    }

    public void removeOrder(Order order) throws Exception {
        if(DL.Transports.Order.checkOrder(order.getId())==null)
            throw new Exception("the order doesn't exists");
        //this.orders.remove(order.getId());
        DL.Transports.Order.deleteOrder(order.getId());
    }

    public void addItem(int id, String item, int quantity) throws Exception {
        if(DL.Transports.Order.checkOrder(id)==null)
            throw new Exception("the order doesn't exists");
        if(quantity <= 0)
            throw new Exception("the quantity is illegal");
        if(DL.Transports.Order.checkItem(id,item))
            throw new Exception("item already exists in order");
        //orders.get(id).getItems().put(item, quantity);
        DL.Transports.Order.insertItemsForOrders(new DTO.ItemsForOrders(id,item,quantity));
    }
    public void removeItem(int id, String item) throws Exception
    {
        Order o =DL.Transports.Order.checkOrder(id);

        if(o==null)
            throw new Exception("the order doesn't exists");
        if(!DL.Transports.Order.checkItem(id,item))
            throw new Exception("the item doesn't exists");
        if(o.getItems().size()==1)
            throw new Exception("orders can't have zero items");
        //orders.get(id).getItems().remove(item);
        DL.Transports.Order.deleteItem(id,item);
    }
    public void changeQuantity(int id, String item, int quantity) throws Exception
    {
        if(DL.Transports.Order.checkOrder(id)==null)
            throw new Exception("the order doesn't exists");
        if(quantity <= 0)
            throw new Exception("the quantity is illegal");
        if(!DL.Transports.Order.checkItem(id,item))
            throw new Exception("item doesn't exists in order");
        DL.Transports.Order.checkOrder(id).getItems().put(item, quantity);
        DL.Transports.Order.updatQunt(id,item,quantity);
    }
    public void changeTotalWeight(int id, double totalWeight) throws Exception {
        if(DL.Transports.Order.checkOrder(id)==null)
            throw new Exception("the order doesn't exists");
        DL.Transports.Order.checkOrder(id).setTotalWeight(totalWeight);
        DL.Transports.Order.updateTotal(id,totalWeight);
    }

    public void printOrders() throws SQLException {
        DL.Transports.Order.printOrders();
    }
}
