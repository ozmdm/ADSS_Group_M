package bussinessLayer;

public class LineCatalogItem {

    private CatalogItem catalogItem;
    private int amount;

    public LineCatalogItem(CatalogItem catItem, int amount) {
        catalogItem = catItem;
        this.amount = amount;
    }

    public int getAmount(){
        return amount;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }

	public int getCatalogItemId() {
		return catalogItem.getCatalogItemId();
	}
}
