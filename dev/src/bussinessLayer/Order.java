package bussinessLayer;

import java.time.LocalDateTime;

public class Order {
    private static int index=1;
    enum Status {OPEN, INPROGRESS, COMPLETE};
    private Status status = Status.OPEN;
    private Cart cart;
    private Supplier supplier;
    private int orderId; //TODO CHANGE IN UML
    private LocalDateTime dateTimeAtCreation;
    private LocalDateTime deliveryDate;

    public Order(int supplierId) {
        orderId = index;
        index+=1;
        this.cart = new Cart();
        this.supplier = Supplier.getSupplier(supplierId); 
        this.dateTimeAtCreation = LocalDateTime.now();
        this.deliveryDate = null; //TODO endOrder() INITIALIZES THIS
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
        status = Status.INPROGRESS;
	}

	public String getOrderStatus() {
		return status.name();
	}

	public void endOrder() {
        status = Status.COMPLETE;
	}

	public Object getOrderDetails() {
		return null; //TODO NEED TO UNDERSTAND WHICH DETAILS TO RENTURN AND HOW
	}
}
