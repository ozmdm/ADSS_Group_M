package DataAccessLaye;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ServiceLayer.ServiceObjects.CatalogDTO;
import ServiceLayer.ServiceObjects.CatalogItemDTO;

public class CatalogItemDAOImpl implements ICatalogItemDAO {


    private Connection conn;

    public CatalogItemDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public CatalogItemDTO getCatalogItem(int catalogItemId) {
        return null;
    }

    @Override
    public void updateCatalogItem() {

    }

    @Override
    public void deleteCatalogItem(int contractId, int catalogItemId) {

    }

    @Override
    public CatalogDTO getCatalog(int contractId) {
        return null;
    }
}
