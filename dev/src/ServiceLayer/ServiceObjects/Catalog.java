package ServiceLayer.ServiceObjects;

import java.util.List;

public class Catalog {

	private List<CatalogItem> catalogItems;

	public Catalog(List<CatalogItem> catalogItems) {
		this.catalogItems = catalogItems;
	}

	public List<CatalogItem> getCatalogItems() {
		return catalogItems;
	}

	@Override
	public String toString() {
		String out = "";
		for(CatalogItem item : catalogItems) {
			out += "\n" + item.toString();
		}
		
		return out;
	}
	
	

}
