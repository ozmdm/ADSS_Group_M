package DataAccessLaye;

import ServiceLayer.ServiceObjects.LineCatalogItemDTO;
import ServiceLayer.ServiceObjects.OrderDTO;
import bussinessLayer.OrderPackage.LineCatalogItem;

import java.sql.SQLException;
import java.util.List;

public interface ILineCatalogItemInCartDAO {

    LineCatalogItemDTO find(int orderId,int CatalogItemId) throws SQLException;
    List<LineCatalogItemDTO>findAllByOrderId(int orderId) throws SQLException;
    void insert(LineCatalogItemDTO lineCatalogItemDTO) throws SQLException;
    void deleteItemFromOrder(int catalodItemId, int orderId);
}
