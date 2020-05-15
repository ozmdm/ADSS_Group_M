package DataAccessLaye;

import ServiceLayer.ServiceObjects.LineCatalogItemDTO;
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
    public LineCatalogItemDTO find(int orderId, int CatalogItemId) {
        return null;
    }

    @Override
    public List<LineCatalogItemDTO> findAll() {
        return null;
    }

    @Override
    public void insert(LineCatalogItemDTO lineCatalogItemDTO) {

    }

    @Override
    public void deleteItemFromOrder(int catalodItemId, int orderId) {

    }
}
