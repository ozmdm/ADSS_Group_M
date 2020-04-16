package ServiceLayer;

import bussinessLayer.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    public List<Order> orders;

    public OrderService ()
    {
        orders = new ArrayList<>();
    }

    private Order getOrder(int orderId){ //SEARCH THE ORDER WITH THE ID AND RETURNING IT
        for(Order order : orders){
            if(order.getOrderId() == orderId){
                return order;
            }
        }
        return NULL; // NULL MEANS FAILURE
    }

    public Object getOrderDetails(int orderId){ // RETURNING SPECIFIC DETAILS TO UI
        for(Order order : orders){
            if(order.getOrderId() == orderId){
                return null; //TODO RETURN AN OBJECT WITH THE DESIRED DETAILS
            }
        }
    }

    public Supplier getSupplier(int supplierId) //TODO NEED TO CHECK MAYBE THE PRESENTATION LAYER WILL GET SUPPLIER AND THIS FUNCTION DEAD

    public Object createAnOrder(int supplierId){ //CREATES NEW ORDER AND ADD IT TO @orders
        orders.add(new Order(supplierId);
        return null; //TODO RETURN ABOUT SUCCESS
    }

    public Object addItemToCart(int orderId,int itemId){ //ADD ONE ITEM TO THE CART
        getOrder(orderId).addItemToCart(itemId);
        return null; //TODO MAYBE NEED TO SUPPORT FAIL/SUCCESS SYSTEM

    }

    public Object removeFromCart(int orderId, int itemId){ //REMOVES ONE ITEM FROM THE CART
        getOrder(orderId).removeFromCart(itemId);
        return null; //TODO MAYBE NEED TO SUPPORT FAIL/SUCCESS SYSTEM
    }

    public Object sendOrder(int orderId){ // CHANGES ORDER'S STATUS TO ONPROGRESS
        getOrder(orderId).sendOrder();
        return null; //TODO MAYBE NEED TO SUPPORT FAIL/SUCCESS SYSTEM
    }

    public Object getOrderStatus(int orderId){ //RETURNS THE STATUS OF THE ORDER
        return getOrder(orderId).getOrderStatus();
    }

    public Object endOrder(int orderId){ // CHANGES ORDER'S STATUS TO ONPROGRESS
        getOrder(orderId).endOrder();
        return null; //TODO MAYBE NEED TO SUPPORT FAIL/SUCCESS SYSTEM
    }

}
