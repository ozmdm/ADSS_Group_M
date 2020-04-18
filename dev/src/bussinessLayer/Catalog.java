package bussinessLayer;

import java.util.ArrayList;
import java.util.List;

public class Catalog {
    private List<CatalogItem> items;

    public Catalog() {
        items = new ArrayList<>();
    }

    public List<CatalogItem> getItems() {
        return items;
    }

    public void addItemsToCatalogByList(List<CatalogItem> itemsList) {
        for (int i = 0; i < itemsList.size(); i++) {
            items.add(itemsList.get(i));
        }
    }

    public void addItemToCatalog(CatalogItem item) {
        if (!items.contains(item))
            items.add(item);
    }

    public void removItemFromList(CatalogItem item) {
        if (items.contains(item)) {
            items.remove(item);
        }
    }


    public CatalogItem getCatalogItem(int catalogItemId) {
        for (CatalogItem catalogItem : items) {
            if (catalogItem.getCatalogItemId() == catalogItemId)
                return catalogItem;

        }
        return null;
    }

    public String toString()
    {
        String s ="";
        for (CatalogItem catalogItem : items)
        {
            s = s + "\n"+ catalogItem.toString();
        }
        return s;
    }
}
