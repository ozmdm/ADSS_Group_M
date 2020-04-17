package ServiceLayer;

import bussinessLayer.Order;
import Data.*;

public class OrderService {

    private static OrderService orderService = null;

    private OrderService (){}

    public static OrderService getInstance(){
        if(orderService == null){
            orderService = new OrderService();
        }
        return orderService;
    }

    private Order getOrder(int orderId){ //SEARCH THE ORDER WITH THE ID AND RETURNING IT
        for(Order order : Data.getOrders()){
            if(order.getOrderId() == orderId){
                return order;
            }
        }
        return null; // NULL MEANS FAILURE
    }

    public Object getOrderDetails(int orderId){ // RETURNING SPECIFIC DETAILS TO UI
        return getOrder(orderId).getOrderDetails();
    }

    public int createAnOrder(int supplierId){ //CREATES NEW ORDER AND ADD IT TO @orders
        Order o = new Order(supplierId);
        Data.getOrders().add(o);
        return o.getOrderId(); //TODO RETURN ABOUT SUCCESS
    }

    public Object addItemToCart(int orderId,int catalogItemId, int amount){ //ADD ONE ITEM TO THE CART
        getOrder(orderId).addItemToCart(catalogItemId,amount);
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

	public String printOrdersFromSupplier(int supplierId) { // PRINTS ALL ORDERS FROM SUPPLIER
		return "null"; //TODO NEED TO CHECK WHETHER HERE SEARCH ALL THE ORDERS OR FROM BUISSNESSLOGIC.ORDER AND WHAT TO RETURN IN THE STRING
	}

}
