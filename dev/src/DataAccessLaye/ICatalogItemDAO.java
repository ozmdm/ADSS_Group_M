package DataAccessLaye;

import ServiceLayer.ServiceObjects.CatalogDTO;
import ServiceLayer.ServiceObjects.CatalogItemDTO;
import bussinessLayer.SupplierPackage.CatalogItem;

import java.util.List;

public interface ICatalogItemDAO {

    CatalogItemDTO find (int catalogItemId);

    void insert(CatalogItemDTO catalogItemDTO);

    void deleteCatalogItem(int contractId, int catalogItemId);

    List<CatalogItemDTO> findAll();
}
