package DataAccessLaye;

import ServiceLayer.ServiceObjects.LineCatalogItemDTO;

import java.sql.SQLException;
import java.util.List;

public interface ILineCatalogItemInCartDAO {

    LineCatalogItemDTO find(int orderId,int CatalogItemId) throws SQLException;
    List<LineCatalogItemDTO>findAllByOrderId(int orderId) throws SQLException;
    void insert(LineCatalogItemDTO lineCatalogItemDTO, int orderId) throws SQLException;
    void deleteItemFromOrder(int catalodItemId, int orderId);
}
