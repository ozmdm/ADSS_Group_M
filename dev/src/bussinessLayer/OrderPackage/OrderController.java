package bussinessLayer.OrderPackage;

import java.util.ArrayList;
import java.util.List;
import Data.Data;

public class OrderController {
	
	public OrderController() {}
	
    private bussinessLayer.OrderPackage.Order getOrder(int orderId) throws Exception { //SEARCH THE ORDER WITH THE ID AND RETURNING IT
        List<bussinessLayer.OrderPackage.Order> orders = Data.getOrders();
        for (bussinessLayer.OrderPackage.Order order : orders) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        throw new Exception("Order does not Exist!\n");
    }

	public ServiceLayer.ServiceObjects.Order getOrderDetails(int orderId) throws Exception {
		Order o = getOrder(orderId);
		ServiceLayer.ServiceObjects.Cart cart = convertToServiceCart(o.getCart());
		return new ServiceLayer.ServiceObjects.Order(o.getOrderId(), o.getSupplierId(), o.getOrderStatus(), o.getDateTimeAtCreation(),o.getDeliveryDate(), cart);
	}

	private ServiceLayer.ServiceObjects.Cart convertToServiceCart(bussinessLayer.OrderPackage.Cart cart) {
		List<ServiceLayer.ServiceObjects.LineCatalogItem> lineItems = convertToLineItemsService(cart.getItemsToDelivery());
		return new ServiceLayer.ServiceObjects.Cart(lineItems, cart.getTotalAmount(),cart.getTotalPrice());
	}

	private List<ServiceLayer.ServiceObjects.LineCatalogItem> convertToLineItemsService(List<LineCatalogItem> itemsToDelivery) {
		List<ServiceLayer.ServiceObjects.LineCatalogItem> list = new ArrayList<ServiceLayer.ServiceObjects.LineCatalogItem>();
		
		for(LineCatalogItem lineItem : itemsToDelivery) {
			list.add(new ServiceLayer.ServiceObjects.LineCatalogItem(lineItem.getCatalogItemId(),lineItem.getAmount(), lineItem.getPriceAfterDiscount()));
		}
		
		return list;
	}

	public Integer createAnOrder(int supplierId) throws Exception {
		Order o = new Order(supplierId);
		Data.getOrders().add(o);
		return o.getOrderId();
	}

	public void addItemToCart(int orderId, int catalogItemId, int amount) throws Exception {
		getOrder(orderId).addItemToCart(catalogItemId, amount);
		
	}

	public void removeFromCart(int orderId, int catalogItemId) throws Exception {
		getOrder(orderId).removeFromCart(catalogItemId);
	}

	public void sendOrder(int orderId) throws Exception {
		getOrder(orderId).sendOrder();
		
	}

	public void endOrder(int orderId) throws Exception {
		getOrder(orderId).endOrder();
	}

	public List<ServiceLayer.ServiceObjects.Order> getOrdersOfSupplier(int supplierId) {
		List<Order> orders = Data.getOrders();
		List<Order> buisSupOrders = new ArrayList<Order>();
		for(Order order : orders) {
			if(order.getSupplierId() == supplierId) {
				buisSupOrders.add(order);
			}
		}
		
		return converBuisToServOrder(buisSupOrders);
	}

	private List<ServiceLayer.ServiceObjects.Order> converBuisToServOrder(List<Order> buisSupOrders) {
		List<ServiceLayer.ServiceObjects.Order> orders = new ArrayList<ServiceLayer.ServiceObjects.Order>();
		for(Order order : buisSupOrders) {
			ServiceLayer.ServiceObjects.Cart cart = convertToServiceCart(order.getCart());
			orders.add(new ServiceLayer.ServiceObjects.Order(order.getOrderId(), order.getSupplierId(), order.getOrderStatus(), order.getDateTimeAtCreation(), order.getDeliveryDate(), cart));
		}
		
		return orders;
	}
    
    

}
