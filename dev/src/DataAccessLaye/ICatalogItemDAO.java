package DataAccessLaye;

import ServiceLayer.ServiceObjects.CatalogDTO;
import ServiceLayer.ServiceObjects.CatalogItemDTO;

public interface ICatalogItemDAO {

    CatalogItemDTO getCatalogItem (int catalogItemId);

    void updateCatalogItem();

    void deleteCatalogItem(int contractId, int catalogItemId);

    CatalogDTO getCatalog(int contractId);
}
