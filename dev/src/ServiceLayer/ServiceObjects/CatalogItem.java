package ServiceLayer.ServiceObjects;

public class CatalogItem {

	private int catalogItemId;
	private int itemId;
	private double price;
	private String description;

	public CatalogItem(int catalogItemId, String description, double price, int itemId) {
		this.catalogItemId = catalogItemId;
		this.description = description;
		this.price = price;
		this.itemId = itemId;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public int getCatalogItemId() {
		return catalogItemId;
	}

	@Override
	public String toString() {
		return catalogItemId + "\t" + itemId + "\t" + price+ "\t" + description;
	}

	public int getItemId() {
		return itemId;
	}
    
}