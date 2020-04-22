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

    public Order(int supplierId) {
        orderId = index;
        index+=1;
        this.cart = new Cart();
        this.supplier = Data.getSupplierById(supplierId);
        this.dateTimeAtCreation = LocalDateTime.now();
        this.deliveryDate = null;
	}

	private Order(int supplierId, Cart cart){
		orderId = index;
		index+=1;
		this.supplier = Data.getSupplierById(supplierId);
		status = Status.COMPLETE;
		this.cart = cart;
		this.dateTimeAtCreation = LocalDateTime.now();
		this.deliveryDate = supplier.getNextDateOfDelivery();
	}

    public int getOrderId() {
	    return orderId;
    }

	public void addItemToCart(int catalogItemId,int amount) {
        cart.addItemToCart(supplier.getCatalogItem(catalogItemId), amount,supplier.getPriceAfterDiscountByItem(catalogItemId, amount));//TODO MAYBE NEED TO CREATE EVERYTIME A NEW CATALOGITEM
	}

	public void removeFromCart(int catalogItemId) {
        cart.removeFromCart(catalogItemId);
	}

	public void sendOrder() {
		deliveryDate = supplier.getNextDateOfDelivery();
        status = Status.INPROGRESS;
	}

	public String getOrderStatus() {
		return status.name();
	}

	public void endOrder() {
        status = Status.COMPLETE;
	}

	public String getOrderDetails() {
		return "Order ID: "+ orderId +"\nStatus: " + status.toString() + "\nSupplier ID: " + supplier.getSupplierId() +
				"\nCreation Date: " + dateTimeAtCreation.toString() + "\nDelivery Date: " + deliveryDate.toString() + "\n" + cart.toString();
	}

	public static void loadFirstItems() {
        Item.loadFirstItems();
	}

	public static void loadFirstOrders() {
		//TODO
	}

	public int getSupplierId() {
		return supplier.getSupplierId();
	}
}
