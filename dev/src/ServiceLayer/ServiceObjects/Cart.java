package ServiceLayer.ServiceObjects;

import java.util.List;

public class Cart {

	private List<LineCatalogItem> lineItems;
	private int totalAmount;
	private double totalPrice;

	public Cart(List<LineCatalogItem> lineItems, int totalAmount, double totalPrice) {
		this.lineItems = lineItems;
		this.totalAmount = totalAmount;
		this.totalPrice = totalPrice;
	}

	public List<LineCatalogItem> getLineItems() {
		return lineItems;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public double getTotalPrice() {
		return totalPrice;
	}
    
}