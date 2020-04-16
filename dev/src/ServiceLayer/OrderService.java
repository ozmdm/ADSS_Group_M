package ServiceLayer;

import bussinessLayer.Order;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    public List<Order> orders;

    public OrderService ()
    {
        orders = new ArrayList<Order>();
    }

    private Order getOrder(int orderId){ //SEARCH THE ORDER WITH THE ID AND RETURNING IT
        for(Order order : orders){
            if(order.getOrderId() == orderId){
                return order;
            }
        }
        return null; // NULL MEANS FAILURE
    }

    public Object getOrderDetails(int orderId){ // RETURNING SPECIFIC DETAILS TO UI
        return getOrder(orderId).getOrderDetails();
    }

    public Object createAnOrder(int supplierId){ //CREATES NEW ORDER AND ADD IT TO @orders
        orders.add(new Order(supplierId));
        return null; //TODO RETURN ABOUT SUCCESS
    }

    public Object addItemToCart(int orderId,int itemId, int amount){ //ADD ONE ITEM TO THE CART
        getOrder(orderId).addItemToCart(itemId,amount);
        return null; //TODO MAYBE NEED TO SUPPORT FAIL/SUCCESS SYSTEM

    }

    public Object removeFromCart(int orderId, int catalogItemId){ //REMOVES ONE ITEM FROM THE CART
        getOrder(orderId).removeFromCart(catalogItemId);
        return null; //TODO MAYBE NEED TO SUPPORT FAIL/SUCCESS SYSTEM
    }

    public Object sendOrder(int orderId){ // CHANGES ORDER'S STATUS TO INPROGRESS
        getOrder(orderId).sendOrder();
        return null; //TODO MAYBE NEED TO SUPPORT FAIL/SUCCESS SYSTEM
    }

    public String getOrderStatus(int orderId){ //RETURNS THE STATUS OF THE ORDER //TODO MAYBE STRING NEED TO CHANGE
        return getOrder(orderId).getOrderStatus();
    }

    public Object endOrder(int orderId){ // CHANGES ORDER'S STATUS TO COMPLETE
        getOrder(orderId).endOrder();
        return null; //TODO MAYBE NEED TO SUPPORT FAIL/SUCCESS SYSTEM
    }

}
