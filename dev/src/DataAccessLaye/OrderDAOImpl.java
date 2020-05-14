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
    public OrderDTO getOrderByID(int orderId) {
        return null;
    }

    @Override
    public void updateOrder() {

    }

    @Override
    public List<OrderDTO> getSupplierOrders(int supplierId) {
        return null;
    }

    @Override
    public void insertOrder() {

    }
}
