package DataAccessLaye;

import ServiceLayer.ServiceObjects.OrderDTO;

import java.sql.Connection;
import java.util.List;

public class OrderDAOImpl implements IOrderDAO {
    private Connection conn;

    public OrderDAOImpl(Connection conn)
    {
        this.conn = conn;
    }

    @Override
    public OrderDTO find(int orderId) {
        return null;
    }

    @Override
    public List<OrderDTO> findAll() {
        return null;
    }

    @Override
    public void insert(OrderDTO orderDTO) {

    }
}
