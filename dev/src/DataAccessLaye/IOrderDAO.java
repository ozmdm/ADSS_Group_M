package DataAccessLaye;

import ServiceLayer.ServiceObjects.OrderDTO;

import java.util.List;

public interface IOrderDAO {

    OrderDTO find(int orderId);
    List<OrderDTO> findAll();
    void insert(OrderDTO orderDTO);
}
