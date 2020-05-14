package DataAccessLaye;

import ServiceLayer.ServiceObjects.OrderDTO;

import java.util.List;

public interface IOrderDAO {

    OrderDTO getOrderByID(int orderId);
    void updateOrder();
    List<OrderDTO> getSupplierOrders(int supplierId);
    void insertOrder();
}
