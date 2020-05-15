package ServiceLayer.ServiceObjects;

public class LineCatalogItemDTO {
	private int orderId;
	private int catalogItemId;
	private int amount;
	private double priceAfterDiscount;

	public LineCatalogItemDTO(int catalogItemId, int amount, double priceAfterDiscount,int orderId) {
		this.catalogItemId = catalogItemId;
		this.amount = amount;
		this.priceAfterDiscount = priceAfterDiscount;
		this.orderId = orderId;
	}

	public int getCatalogItemId() {
		return catalogItemId;
	}

	public int getAmount() {
		return amount;
	}

	public double getPriceAfterDiscount() {
		return priceAfterDiscount;
	}

	public int getOrderId(){
		return orderId;
	}

}
