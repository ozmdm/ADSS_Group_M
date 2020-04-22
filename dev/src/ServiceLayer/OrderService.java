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

    private Order getOrder(int orderId) throws Exception{ //SEARCH THE ORDER WITH THE ID AND RETURNING IT
        for(Order order : Data.getOrders()){
            if(order.getOrderId() == orderId){
                return order;
            }
        }
        throw new Exception("Order does not Exist!\n");
    }

    public String getOrderDetails(int orderId){ // RETURNING SPECIFIC DETAILS TO UI
        try {
			return getOrder(orderId).getOrderDetails();
		} catch (Exception e) {
			return e.getMessage();
		}
    }

    public int createAnOrder(int supplierId){ //CREATES NEW ORDER AND ADD IT TO @orders
        Order o = new Order(supplierId);
        Data.getOrders().add(o);
        return o.getOrderId(); //TODO RETURN ABOUT SUCCESS
    }

    public String addItemToCart(int orderId,int catalogItemId, int amount){ //ADD ONE ITEM TO THE CART
        try {
			getOrder(orderId).addItemToCart(catalogItemId,amount);
			return "Done";
		} catch (Exception e) {
			return e.getMessage();
		}

    }

    public String removeFromCart(int orderId, int catalogItemId){ //REMOVES ONE ITEM FROM THE CART
        try {
			getOrder(orderId).removeFromCart(catalogItemId);
			return "Done";
		} catch (Exception e) {
			return e.getMessage();
		}
    }

    public String sendOrder(int orderId){ // CHANGES ORDER'S STATUS TO INPROGRESS
        try {
			getOrder(orderId).sendOrder();
			return "Done";
		} catch (Exception e) {
			return e.getMessage();
		}
    }

    public String getOrderStatus(int orderId){ //RETURNS THE STATUS OF THE ORDER //TODO MAYBE STRING NEED TO CHANGE
        try {
			return getOrder(orderId).getOrderStatus();
		} catch (Exception e) {
			return e.getMessage();
		}
    }

    public Object endOrder(int orderId){ // CHANGES ORDER'S STATUS TO COMPLETE
        getOrder(orderId).endOrder();
        return null; //TODO MAYBE NEED TO SUPPORT FAIL/SUCCESS SYSTEM
    }

	public String printOrdersFromSupplier(int supplierId) { // PRINTS ALL ORDERS FROM SUPPLIER
		String s = "";
		for(Order order : Data.getOrders()) {
			if(order.getSupplierId() == supplierId) s += "\n" + order.getOrderDetails();
		}
		
		return s;
	}

	public void loadFirstItems() {
        Order.loadFirstItems();
	}

	public void loadFirstOrders() {
        Order.loadFirstOrders();
	}

}
