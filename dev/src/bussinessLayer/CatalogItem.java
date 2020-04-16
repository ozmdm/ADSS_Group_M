package bussinessLayer;

public class CatalogItem {
    public Item getItem() {
        return item;
    }

    public int getCatalogItemId() {
        return catalogItemId;
    }

    public double getPrice() {
        return price;
    }

    private Item item;
    private  int catalogItemId;
    private double price;

    public CatalogItem(int itemId, int catalogItemId, double price) {
        this.item = item;
        this.catalogItemId = catalogItemId;
        this.price = price;
    }
}
