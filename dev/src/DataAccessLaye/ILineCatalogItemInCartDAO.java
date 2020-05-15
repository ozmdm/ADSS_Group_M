package DataAccessLaye;

import ServiceLayer.ServiceObjects.LineCatalogItemDTO;
import ServiceLayer.ServiceObjects.OrderDTO;
import bussinessLayer.OrderPackage.LineCatalogItem;

import java.util.List;

public interface ILineCatalogItemInCartDAO {

    LineCatalogItemDTO find(int orderId,int CatalogItemId);
    List<LineCatalogItemDTO>findAll();
    void insert(LineCatalogItemDTO lineCatalogItemDTO);
    void deleteItemFromOrder(int catalodItemId, int orderId);
}
