package ServiceLayer.ServiceObjects;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Order {

	private int orderId;
	private int supplierId;
	private String orderStatus;
	private LocalDateTime creationDate;
	private LocalDate deliveryDate;
	private Cart cart;

	public Order(int orderId, int supplierId, String orderStatus, LocalDateTime dateTimeAtCreation,
			LocalDate deliveryDate, Cart cart) {
		this.orderId = orderId;
		this.supplierId = supplierId;
		this.orderStatus = orderStatus;
		this.creationDate = dateTimeAtCreation;
		this.deliveryDate = deliveryDate;
		this.cart = cart;
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

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public Cart getCart() {
		return cart;
	}
    
}