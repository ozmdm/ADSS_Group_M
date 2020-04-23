package bussinessLayer;

import Data.Data;

public class CatalogItem {

	private Item item;
	private  int catalogItemId;
	private double price;

	public CatalogItem(int itemId, int catalogItemId, double price) {
		this.item = Data.getItemById(itemId);
		this.catalogItemId = catalogItemId;
		this.price = price;
	}

	public Item getItem() {
		return item;
	}

	public int getCatalogItemId() {
		return catalogItemId;
	}

	public double getPrice() {
		return price;
	}

	public String toString (){
		String s = "";
		s = s + item.toString() + " catalog-Item-Id: "+ this.getCatalogItemId()+ ", price: "+ this.getPrice();
		return s;
	}

	public String getDescription() {
		return item.getDescription();
	}


}
