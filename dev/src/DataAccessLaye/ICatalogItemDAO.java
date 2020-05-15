package DataAccessLaye;

import ServiceLayer.ServiceObjects.CatalogDTO;
import ServiceLayer.ServiceObjects.CatalogItemDTO;
import bussinessLayer.SupplierPackage.CatalogItem;

import java.sql.SQLException;
import java.util.List;

public interface ICatalogItemDAO {

    CatalogItemDTO find (int catalogItemId,int contractId) throws SQLException;

    void insert(CatalogItemDTO catalogItemDTO) throws SQLException;

    void deleteCatalogItem(int contractId, int catalogItemId);

    List<CatalogItemDTO> findAll() throws SQLException;
}
