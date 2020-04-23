package bussinessLayer;

import java.util.ArrayList;
import java.util.List;

public class Catalog {
	private List<CatalogItem> items;

	public Catalog() {
		items = new ArrayList<>();
	}

	public Catalog(List<CatalogItem> items) {
		this.items = items;
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

	public void removItemFromList(CatalogItem item) throws Exception {
		if (items.contains(item)) {
			items.remove(item);
			return;
		}
		throw new Exception("the item do not exist");
	}


	public CatalogItem getCatalogItem(int catalogItemId) throws Exception {
		for (CatalogItem catalogItem : items) {
			if (catalogItem.getCatalogItemId() == catalogItemId)
				return catalogItem;

		}
		throw new Exception("the catalog-item do not found");
	}

	public String toString()
	{
		String s ="";
		if (items.isEmpty()) {
			try {
				throw new Exception("catalog is empty");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (CatalogItem catalogItem : items)
		{
			s = s + "\n"+ catalogItem.toString();
		}
		return s;
	}
}