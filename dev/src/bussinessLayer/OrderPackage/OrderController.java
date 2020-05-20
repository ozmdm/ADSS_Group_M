package bussinessLayer.OrderPackage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import DataAccessLaye.Repo;
import ServiceLayer.ResponseT;
import bussinessLayer.DTOPackage.OrderDTO;
import bussinessLayer.DTOPackage.ScheduledDTO;
import bussinessLayer.SupplierPackage.Supplier;
import javafx.util.Pair;

public class OrderController {

	public OrderController() {
	}

	private bussinessLayer.OrderPackage.Order getOrder(int orderId) throws Exception {
		return new Order(Repo.getInstance().getOrderByID(orderId));
	}

	private List<Order> getOrders(int branchId) throws Exception {
		List<OrderDTO> ordersDTO;
		ordersDTO = Repo.getInstance().getAllOrderByBranchId(branchId);
		List<Order> list = new ArrayList<Order>();
		for (OrderDTO order : ordersDTO) {
			list.add(new Order(order));
		}
		return list;
	}

	public bussinessLayer.DTOPackage.OrderDTO getOrderDetails(int orderId) throws Exception {
		Order o = getOrder(orderId);
		return o.converToDTO();
	}

	public ResponseT<Integer> createAnOrder(int supplierId, int branchId) throws Exception {
		Order o = new Order(new Supplier(Repo.getInstance().getSupplierById(supplierId)), branchId);
		if(Repo.getInstance().isThereScheduledOrderForNextDay(supplierId, branchId, o.getDeliveryDate().getDayOfWeek().getValue())) { //maybe problem
			return new ResponseT<Integer>(Repo.getInstance().getOrderIdBy(supplierId, branchId, o.getDeliveryDate().getDayOfYear(), o.getDeliveryDate().getYear()));
		}
		int orderId = Repo.getInstance().insertOrder(o.converToDTO());
		if(orderId>0) return new ResponseT<Integer>(orderId);
		else return new ResponseT<Integer>("Error occured");
	}

	public void addItemToCart(int orderId, int catalogItemId, int amount) throws Exception {
		Order order = getOrder(orderId);
		order.addItemToCart(catalogItemId, amount);
		Repo.getInstance().insertLineCatalogItem(order.getLineCatalogItemDTO(catalogItemId), orderId);
	}

	public void removeFromCart(int orderId, int catalogItemId) throws Exception {
		getOrder(orderId).removeFromCart(catalogItemId);
		Repo.getInstance().deleteItemFromOrder(catalogItemId, orderId);
	}

	public void sendOrder(int orderId) throws Exception {
		Order order = getOrder(orderId);
		order.sendOrder();
		OrderDTO orderDTO = order.converToDTO();
		Repo.getInstance().updateOrder(orderDTO);

	}

	public void endOrder(int orderId) throws Exception {
		Order order = getOrder(orderId);
		order.endOrder();
		OrderDTO orderDTO = order.converToDTO();
		Repo.getInstance().updateOrder(orderDTO);
	}

	public List<bussinessLayer.DTOPackage.OrderDTO> getOrdersOfSupplier(int supplierId, int branchId)
			throws Exception {
		List<Order> orders = getOrders(branchId);
		List<bussinessLayer.DTOPackage.OrderDTO> DTOlist = new ArrayList<bussinessLayer.DTOPackage.OrderDTO>();
		for (Order order : orders) {
			if (order.getSupplierId() == supplierId) {
				DTOlist.add(order.converToDTO());
			}
		}

		return DTOlist;
	}

	public void startScheduledOrder() throws SQLException {
		ScheduledHandler.getInstance().start();
	}

	public void subscribeScheduleOrder(int branchId, int supplierId, int day, List<Pair<Integer,Integer>> itemsToOrder) throws Exception {
		ScheduledDTO schedule = new ScheduledDTO(day, supplierId, itemsToOrder, branchId); 
		isScheduleValid(schedule);
		Repo.getInstance().insertScheduled(schedule);
		ScheduledHandler.getInstance().addSchedule(schedule);
	}

	private void isScheduleValid(ScheduledDTO schedule) throws Exception {
		Repo.getInstance().getBranchById(schedule.getBranchId());
		Supplier supplier = new Supplier(Repo.getInstance().getSupplierById(schedule.getSupplierId()));
		supplier.isDayValidDelivery(schedule.getDay());
		isItemsValid(schedule.getItemsToOrder(), supplier);
	}

	private void isItemsValid(List<Pair<Integer, Integer>> itemsToOrder,
			bussinessLayer.SupplierPackage.Supplier supplier) throws Exception {
		for (Pair<Integer,Integer> pair : itemsToOrder) {
			if(pair.getValue()<0) throw new Exception("Amount is not valid");
			supplier.getCatalogItem(pair.getKey());
		}
	}

	public void purgeTimer() {
		ScheduledHandler.getInstance().getTimer().cancel();
	}
    
    

}
