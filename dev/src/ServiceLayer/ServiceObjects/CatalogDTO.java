package ServiceLayer.ServiceObjects;

import java.util.List;

public class CatalogDTO {

	private List<CatalogItemDTO> catalogItems;

	public CatalogDTO(List<CatalogItemDTO> catalogItems) {
		this.catalogItems = catalogItems;
	}

	public List<CatalogItemDTO> getCatalogItems() {
		return catalogItems;
	}

	@Override
	public String toString() {
		String out = "";
		for(CatalogItemDTO item : catalogItems) {
			out += "\n" + item.toString();
		}
		
		return out;
	}
	
	

}
