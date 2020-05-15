package ServiceLayer.ServiceObjects;

public class CatalogItemDTO {

	private int catalogItemId;
	private int contractId;
	private int itemId;
	private double price;
	private String description;

	public CatalogItemDTO(int catalogItemId, String description, double price, int itemId,int contractId) {
		this.catalogItemId = catalogItemId;
		this.description = description;
		this.price = price;
		this.itemId = itemId;
		this.contractId = contractId;
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
	public int getContractId(){return contractId;}
    
}