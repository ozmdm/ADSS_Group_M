package bussinessLayer.OrderPackage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Data.Data;
import DataAccessLaye.Repo;
import ServiceLayer.ServiceObjects.CatalogItemDTO;
import ServiceLayer.ServiceObjects.OrderDTO;
import ServiceLayer.ServiceObjects.ScheduledDTO;
import javafx.util.Pair;

public class OrderController {

	public OrderController() {
	}

	private bussinessLayer.OrderPackage.Order getOrder(int orderId) throws Exception { 
		return new Order(Repo.getInstance().getOrderByID(orderId));
	}

	private List<Order> getOrders(int branchId)throws Exception{
		List<OrderDTO> ordersDTO;
		ordersDTO = Repo.getInstance().getAllOrderByBranchId(branchId);
		List<Order> list = new ArrayList<Order>();
		for (OrderDTO order : ordersDTO) {
			list.add(new Order(order));
		}
		return list;
	}

	public ServiceLayer.ServiceObjects.OrderDTO getOrderDetails(int orderId) throws Exception {
		Order o = getOrder(orderId);
		return o.converToDTO();
	}

	public Integer createAnOrder(int supplierId, int branchId) throws Exception {
		Repo.getInstance().getBranchById(branchId);
		Order o = new Order(supplierId, branchId);
		Repo.getInstance().insertOrder(o.converToDTO());
		return o.getOrderId();
	}

	public void addItemToCart(int orderId, int catalogItemId, int amount) throws Exception {
		Order order = getOrder(orderId);
		order.addItemToCart(catalogItemId, amount);
		Repo.getInstance().insertLineCatalogItem(orderId, catalogItemId, amount, order.getPriceAfterDiscount(catalogItemId));

	}

	public void removeFromCart(int orderId, int catalogItemId) throws Exception {
		getOrder(orderId).removeFromCart(catalogItemId);
	}

	public void sendOrder(int orderId) throws Exception {
		getOrder(orderId).sendOrder();
		Repo.getInstance().updateOrder(getOrder(orderId).converToDTO());

	}

	public void endOrder(int orderId) throws Exception {
		getOrder(orderId).endOrder();
		Repo.getInstance().updateOrder(getOrder(orderId).converToDTO());
	}

	public List<ServiceLayer.ServiceObjects.OrderDTO> getOrdersOfSupplier(int supplierId,int branchId) throws Exception {
		List<Order> orders = getOrders(branchId);
		List<ServiceLayer.ServiceObjects.OrderDTO> DTOlist = new ArrayList<ServiceLayer.ServiceObjects.OrderDTO>();
		for (Order order : orders) {
			if (order.getSupplierId() == supplierId) {
				DTOlist.add(order.converToDTO());
			}
		}

		return DTOlist;
	}

	public void startScheduledOrder() {
		ScheduledHandler.getInstance().start();
	}

	public void createScheduledOrder(ScheduledDTO scheduled, Date date) throws Exception {
		Order order = new Order(scheduled, date);
		// TODO SAVE TO DB
	}

	public void subscribeScheduleOrder(ServiceLayer.ServiceObjects.ScheduledDTO schedule) throws Exception {
		isScheduleValid(schedule);
		// TODO DB.INSERT(schedule)
	}

	private void isScheduleValid(ScheduledDTO schedule) throws Exception {
		// Data.getBranchById(branchId);
		bussinessLayer.SupplierPackage.Supplier supplier = Data.getSupplierById(schedule.getSupplierId());
		supplier.isDayValidDelivery(schedule.getDay());
		isItemsValid(schedule.getItemsToOrder(), supplier);
	}

	private void isItemsValid(List<Pair<CatalogItemDTO, Integer>> itemsToOrder,
			bussinessLayer.SupplierPackage.Supplier supplier) throws Exception {
		for (Pair<CatalogItemDTO,Integer> pair : itemsToOrder) {
			if(pair.getValue()<0) throw new Exception("Amount is not valid");
			supplier.getCatalogItem(pair.getKey().getCatalogItemId());
		}
	}

	public void purgeTimer() {
		ScheduledHandler.getInstance().getTimer().cancel();
	}
    
    

}
