package DataAccessLaye;

import ServiceLayer.ServiceObjects.OrderDTO;

import java.util.List;

public interface ILineCatalogItemInCartDAO {

    List<OrderDTO> getOrderItems(int orderId);
    void updateOrderItem();
    void insertLineCatalogItem();
    void deleteItemFromOrder(int catalodItemId, int orderId);
}
