package bussinessLayer.SupplierPackage;

import ServiceLayer.ServiceObjects.CatalogItemDTO;

public class CatalogItem {

    private bussinessLayer.InventoryPackage.Item item;
    private int catalogItemId;
    private double price;

    public CatalogItem(int itemId, int catalogItemId, double price) {
        this.item = bussinessLayer.InventoryPackage.Inventory.getInstance().getItems().get(itemId);
        this.catalogItemId = catalogItemId;
        this.price = price;
    }

    public bussinessLayer.InventoryPackage.Item getItem() {
        return item;
    }

    public int getCatalogItemId() {
        return catalogItemId;
    }

    public double getPrice() {
        return price;
    }

    public String toString() {
        String s = "";
        s = s + item.toString() + " catalog-Item-Id: " + this.getCatalogItemId() + ", price: " + this.getPrice();
        return s;
    }

    public String getDescription() {
        return item.getDescription();
    }

	public int getItemId() {
		return item.getId();
	}

	public CatalogItemDTO converToDTO() {
        return new CatalogItemDTO(catalogItemId, item.getDescription(), price, item.getId());
	}


}
