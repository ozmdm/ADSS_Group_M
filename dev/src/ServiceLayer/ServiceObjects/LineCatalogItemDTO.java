package ServiceLayer.ServiceObjects;

public class LineCatalogItemDTO {

	private int catalogItemId;
	private int amount;
	private double priceAfterDiscount;

	public LineCatalogItemDTO(int catalogItemId, int amount, double priceAfterDiscount) {
		this.catalogItemId = catalogItemId;
		this.amount = amount;
		this.priceAfterDiscount = priceAfterDiscount;
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

}
