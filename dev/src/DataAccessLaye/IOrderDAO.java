package DataAccessLaye;

import ServiceLayer.ServiceObjects.OrderDTO;

import java.sql.SQLException;
import java.util.List;

public interface IOrderDAO {

    OrderDTO find(int orderId) throws SQLException;
    List<OrderDTO> findAll() throws SQLException;
    void insert(OrderDTO orderDTO) throws SQLException;



}
