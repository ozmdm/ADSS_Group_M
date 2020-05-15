package DataAccessLaye;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import ServiceLayer.ServiceObjects.CatalogDTO;
import ServiceLayer.ServiceObjects.CatalogItemDTO;

public class CatalogItemDAOImpl implements ICatalogItemDAO {


    private Connection conn;

    public CatalogItemDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public CatalogItemDTO find(int catalogItemId) {
        return null;
    }

    @Override
    public void insert(CatalogItemDTO catalogItemDTO) {

    }

    @Override
    public void deleteCatalogItem(int contractId, int catalogItemId) {

    }

    @Override
    public List<CatalogItemDTO> findAll() {
        return null;
    }
}
