package ServiceLayer.ServiceObjects;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderDTO {

	private int orderId;
	private int supplierId;
	private String orderStatus;
	private LocalDate creationDate;
	private LocalDate deliveryDate;
	private LocalDate actualDeliDate;
	private CartDTO cart;
	private int branchId;

	public OrderDTO(int orderId, int supplierId, String orderStatus, LocalDate dateTimeAtCreation,
			LocalDate deliveryDate, CartDTO cart, int branchId) {
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

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public CartDTO getCart() {
		return cart;
	}

	public int getBranchId() {
		return branchId;
	}

	public LocalDate getActualDate() {
		return actualDeliDate;
	}
}