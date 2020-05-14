package DataAccessLaye;

import ServiceLayer.ServiceObjects.OrderDTO;

import java.sql.Connection;
import java.util.List;

public class LineCatalogItemInCartDAOImpl implements ILineCatalogItemInCartDAO {
    private Connection conn;

    public LineCatalogItemInCartDAOImpl(Connection conn)
    {
        this.conn = conn;
    }
    @Override
    public List<OrderDTO> getOrderItems(int orderId) {
        return null;
    }

    @Override
    public void updateOrderItem() {

    }

    @Override
    public void insertLineCatalogItem() {

    }

    @Override
    public void deleteItemFromOrder(int catalodItemId, int orderId) {

    }
}
