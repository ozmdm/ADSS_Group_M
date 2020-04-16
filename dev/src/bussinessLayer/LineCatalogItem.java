package bussinessLayer;

public class LineCatalogItem {

    private CatalogItem catalogItem;
    private int amount;

    public LineCatalogItem(CatalogItem catalogItem, int amount) {
        this.catalogItem = catalogItem;
        this.amount = amount;
    }
}
