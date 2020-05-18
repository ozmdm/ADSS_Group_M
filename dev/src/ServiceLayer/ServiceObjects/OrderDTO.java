package ServiceLayer.ServiceObjects;

import java.time.LocalDateTime;

public class OrderDTO {

	private int orderId;
	private int supplierId;
	private String orderStatus;
	private LocalDateTime creationDate;
	private LocalDateTime deliveryDate;
	private LocalDateTime actualDeliDate;
	private CartDTO cart;
	private int branchId;

	public OrderDTO(int orderId, int supplierId, String orderStatus, LocalDateTime dateTimeAtCreation,
	LocalDateTime deliveryDate, LocalDateTime actualDeliDate, CartDTO cart, int branchId) {
		this.actualDeliDate = actualDeliDate;
		this.orderId = orderId;
		this.supplierId = supplierId;
		this.orderStatus = orderStatus;
		this.creationDate = dateTimeAtCreation;
		this.deliveryDate = deliveryDate;
		this.cart = cart;
		this.branchId = branchId;
	}

	public int getOrderId() {
		return orderId;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public LocalDateTime getDeliveryDate() {
		return deliveryDate;
	}

	public CartDTO getCart() {
		return cart;
	}

	public int getBranchId() {
		return branchId;
	}

	public LocalDateTime getActualDate() {
		return actualDeliDate;
	}

    public void setOrderId(int orderId) {
		this.orderId = orderId;
    }
    
    @Override
    public String toString() {
    	return "Order Num: " + orderId + "\nBranch ID: " + branchId+ "\nSupplier ID: " + supplierId + "\nCreation Date: " + creationDate
    			+"Order Status: "+ orderStatus+  "\nEstimated Delivery Date: " + deliveryDate.toString() + "\nActual Delivery Date: " + actualDeliDate.toString() + cart.toString();
    }
}