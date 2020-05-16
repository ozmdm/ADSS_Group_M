package bussinessLayer.OrderPackage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Data.Data;
import ServiceLayer.ServiceObjects.ScheduledDTO;

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

	public ServiceLayer.ServiceObjects.OrderDTO getOrderDetails(int orderId) throws Exception {
		Order o = getOrder(orderId);
		ServiceLayer.ServiceObjects.CartDTO cart = convertToServiceCart(o.getCart());
		return new ServiceLayer.ServiceObjects.OrderDTO(o.getOrderId(), o.getSupplierId(), o.getOrderStatus(), o.getCreationDate(),o.getDeliveryDate(), o.getActualDeliveryDate(), cart, o.getBranchId());
	}

	private ServiceLayer.ServiceObjects.CartDTO convertToServiceCart(bussinessLayer.OrderPackage.Cart cart) {
		List<ServiceLayer.ServiceObjects.LineCatalogItemDTO> lineItems = convertToLineItemsService(cart.getItemsToDelivery());
		return new ServiceLayer.ServiceObjects.CartDTO(lineItems, cart.getTotalAmount(),cart.getTotalPrice());
	}

	private List<ServiceLayer.ServiceObjects.LineCatalogItemDTO> convertToLineItemsService(List<LineCatalogItem> itemsToDelivery) {
		List<ServiceLayer.ServiceObjects.LineCatalogItemDTO> list = new ArrayList<ServiceLayer.ServiceObjects.LineCatalogItemDTO>();
		
		for(LineCatalogItem lineItem : itemsToDelivery) {
			list.add(new ServiceLayer.ServiceObjects.LineCatalogItemDTO(lineItem.getCatalogItemId(),lineItem.getAmount(), lineItem.getPriceAfterDiscount()));
		}
		
		return list;
	}

	public Integer createAnOrder(int supplierId,int branchId) throws Exception {
		Order o = new Order(supplierId, branchId);
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

	public List<ServiceLayer.ServiceObjects.OrderDTO> getOrdersOfSupplier(int supplierId) {
		List<Order> orders = Data.getOrders();
		List<Order> buisSupOrders = new ArrayList<Order>();
		for(Order order : orders) {
			if(order.getSupplierId() == supplierId) {
				buisSupOrders.add(order);
			}
		}
		
		return converBuisToServOrder(buisSupOrders);
	}

	private List<ServiceLayer.ServiceObjects.OrderDTO> converBuisToServOrder(List<Order> buisSupOrders) {
		List<ServiceLayer.ServiceObjects.OrderDTO> orders = new ArrayList<ServiceLayer.ServiceObjects.OrderDTO>();
		for(Order order : buisSupOrders) {
			ServiceLayer.ServiceObjects.CartDTO cart = convertToServiceCart(order.getCart());
			orders.add(new ServiceLayer.ServiceObjects.OrderDTO(order.getOrderId(), order.getSupplierId(), order.getOrderStatus(), order.getCreationDate(), order.getDeliveryDate(), order.getActualDeliveryDate(), cart, order.getBranchId()));
		}
		
		return orders;
	}

	public void startScheduledOrder() {
		ScheduledHandler.getInstance().start();
	}

	public void createScheduledOrder(ScheduledDTO scheduled, Date date) throws Exception {
		Order order = new Order(scheduled,date);
		//TODO SAVE TO DB
	}

	public void subscribeScheduleOrder(ServiceLayer.ServiceObjects.ScheduledDTO schedule) throws Exception {
		// TODO DB.INSERT(schedule)
	}
    
    

}
