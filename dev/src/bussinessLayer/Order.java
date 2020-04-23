package bussinessLayer;

import java.time.LocalDate;
import java.time.LocalDateTime;

import Data.Data;

public class Order {
	private static int index=1;
	enum Status {OPEN, INPROGRESS, COMPLETE};
	private Status status = Status.OPEN;
	private Cart cart;
	private Supplier supplier;
	private int orderId; 
	private LocalDateTime dateTimeAtCreation;
	private LocalDate deliveryDate;

	public Order(int supplierId) throws Exception {
		orderId = index;
		index+=1;
		this.cart = new Cart();
		this.supplier = Data.getSupplierById(supplierId);
		this.dateTimeAtCreation = LocalDateTime.now();
		this.deliveryDate = null;
	}

	/*
	 * private Order(int supplierId, Cart cart){ orderId = index; index+=1;
	 * this.supplier = Data.getSupplierById(supplierId); status = Status.COMPLETE;
	 * this.cart = cart; this.dateTimeAtCreation = LocalDateTime.now();
	 * this.deliveryDate = supplier.getNextDateOfDelivery(); }
	 */

	public int getOrderId() {
		return orderId;
	}

	public void addItemToCart(int catalogItemId,int amount) throws Exception {
		if(amount<=0) throw new Exception("Amount must be larger than zero");
		cart.addItemToCart(supplier.getCatalogItem(catalogItemId), amount,supplier.getPriceAfterDiscountByItem(catalogItemId, amount));//TODO MAYBE NEED TO CREATE EVERYTIME A NEW CATALOGITEM
		//TODO GetCatalogItem() NEEDS TO ADD THROW EXCEPTION
	}

	public void removeFromCart(int catalogItemId) throws Exception {
		cart.removeFromCart(catalogItemId);
	}

	public void sendOrder() throws Exception {
		if(!status.toString().equals("OPEN")) throw new Exception("Order is not OPEN");
		deliveryDate = supplier.getNextDateOfDelivery();
		status = Status.INPROGRESS;
	}

	public String getOrderStatus() {
		return status.name();
	}

	public void endOrder() throws Exception {
		if(status.toString().equals("COMPLETE")) throw new Exception("Already completed");
		if(status.toString().equals("OPEN")) throw new Exception("The order is still OPEN");
		status = Status.COMPLETE;
	}

	public String getOrderDetails() {
		return "Order ID: "+ orderId +"\nStatus: " + status.toString() + "\nSupplier ID: " + supplier.getSupplierId() +
				"\nCreation Date: " + dateTimeAtCreation.toString() + "\nDelivery Date: " + deliveryDate.toString() + "\n" + cart.toString();
	}

	public static void loadFirstOrders() {
		//TODO
	}

	public int getSupplierId() {
		return supplier.getSupplierId();
	}
}
