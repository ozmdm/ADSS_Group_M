package bussinessLayer.OrderPackage;

import bussinessLayer.DTOPackage.CatalogItemDTO;
import bussinessLayer.DTOPackage.LineCatalogItemDTO;
import bussinessLayer.SupplierPackage.CatalogItem;

public class LineCatalogItem {

    private bussinessLayer.SupplierPackage.CatalogItem catalogItem;
    private int amount;
    private double priceAfterDiscount;

    public LineCatalogItem(bussinessLayer.SupplierPackage.CatalogItem catItem, int amount, double priceAfterDiscount) {
        this.catalogItem = catItem;
        this.amount = amount;
        this.priceAfterDiscount = priceAfterDiscount;
    }

    public LineCatalogItem(CatalogItemDTO catalogItem2, int totalAmount, double priceAfterDiscount) {
		this.catalogItem = new CatalogItem(catalogItem2);
		this.amount = totalAmount;
		this.priceAfterDiscount = priceAfterDiscount;
		
	}

	public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCatalogItemId() {
        return catalogItem.getCatalogItemId();
    }

    /**
     * @return the priceAfterDiscount
     */
    public double getPriceAfterDiscount() {
        return priceAfterDiscount;
    }

    @Override
    public String toString() {
        return "" + catalogItem.getDescription() + "\t" + catalogItem.getCatalogItemId() + "\t" + priceAfterDiscount + "\t" + amount;
    }

	public LineCatalogItemDTO converToDTO() {
        return new LineCatalogItemDTO(catalogItem.converToDTO(), amount, priceAfterDiscount);
	}
}
