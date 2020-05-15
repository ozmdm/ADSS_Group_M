package ServiceLayer;

import java.util.List;

import Data.*;
import ServiceLayer.ServiceObjects.OrderDTO;
import bussinessLayer.OrderPackage.OrderController;

public class OrderService implements IOrderService {

	private static OrderService orderService = null;
	private OrderController oController;

	public static OrderService getInstance() {
		if (orderService == null) {
			orderService = new OrderService();
		}
		return orderService;
	}


	private OrderService() {
		this.oController = new OrderController();
	}

	public ResponseT<OrderDTO> getOrderDetails(int orderId) { // RETURNING SPECIFIC DETAILS TO UI
		try {
			return new ResponseT<OrderDTO>(oController.getOrderDetails(orderId));
		} catch (Exception e) {
			return new ResponseT<OrderDTO>(e.getMessage());
		}
	}

	public ResponseT<Integer> createAnOrder(int supplierId,int branchId) { //CREATES NEW ORDER AND ADD IT TO @orders
		try {
			Integer orderId = oController.createAnOrder(supplierId, branchId);
			return new ResponseT<Integer>(orderId);
		} catch (Exception e) {
			return new ResponseT<Integer>(e.getMessage());
		}

	}

	public Response addItemToCart(int orderId, int catalogItemId, int amount) { //ADD ONE ITEM TO THE CART
		try {
			oController.addItemToCart(orderId, catalogItemId, amount);
			return new Response();
		} catch (Exception e) {
			return new Response(e.getMessage());
		}

	}

	public Response removeFromCart(int orderId, int catalogItemId) { //REMOVES ONE ITEM FROM THE CART
		try {
			oController.removeFromCart(orderId, catalogItemId);
			return new Response();
		} catch (Exception e) {
			return new Response(e.getMessage());
		}
	}

	public Response sendOrder(int orderId) { // CHANGES ORDER'S STATUS TO INPROGRESS
		try {
			oController.sendOrder(orderId);
			return new Response();
		} catch (Exception e) {
			return new Response(e.getMessage());
		}
	}

	public Response endOrder(int orderId) { // CHANGES ORDER'S STATUS TO COMPLETE
		try {
			oController.endOrder(orderId);
			return new Response();
		} catch (Exception e) {
			return new Response(e.getMessage());
		}
	}

	public ResponseT<List<OrderDTO>> printOrdersFromSupplier(int supplierId) { // PRINTS ALL ORDERS FROM SUPPLIER
		try {
			List<OrderDTO> list = oController.getOrdersOfSupplier(supplierId);
			return new ResponseT<List<OrderDTO>>(list);
		}catch(Exception e) {
			return new ResponseT<List<OrderDTO>>(e.getMessage());
		}
	}

	public void loadFirstOrders() {
		bussinessLayer.OrderPackage.Order.loadFirstOrders();
	}

	public String addItem(String itemDes, String manufactuer) {
		try {
			Data.addItem(itemDes, manufactuer);
			return "Done";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
