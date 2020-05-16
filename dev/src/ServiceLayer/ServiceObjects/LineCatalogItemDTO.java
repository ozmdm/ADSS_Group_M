package ServiceLayer.ServiceObjects;

public class LineCatalogItemDTO {
	private CatalogItemDTO catalogItem;
	private int amount;
	private double priceAfterDiscount;

	public LineCatalogItemDTO(CatalogItemDTO catalogItem, int amount, double priceAfterDiscount) {
		this.catalogItem = catalogItem;
		this.amount = amount;
		this.priceAfterDiscount = priceAfterDiscount;
	}

	/**
	 * @return the catalogItem
	 */
	public CatalogItemDTO getCatalogItem() {
		return catalogItem;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @return the priceAfterDiscount
	 */
	public double getPriceAfterDiscount() {
		return priceAfterDiscount;
	}

	@Override
	public String toString() {
		return "" + catalogItem.getCatalogItemId() +"\t" + catalogItem.getDescription() + "\t" + amount + "\t" +
			(100 - priceAfterDiscount*100/catalogItem.getPrice()) + "\t" + priceAfterDiscount;
	}

}
