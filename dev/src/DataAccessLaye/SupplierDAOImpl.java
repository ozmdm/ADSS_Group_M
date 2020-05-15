package DataAccessLaye;

import ServiceLayer.ServiceObjects.SupplierDTO;

import java.sql.Connection;
import java.util.List;

public class SupplierDAOImpl implements ISupplierDAO {
    private Connection conn;

    public SupplierDAOImpl(Connection conn)
    {
        this.conn =conn;
    }


    @Override
    public SupplierDTO find(int supplierId) {
        return null;
    }

    @Override
    public List<SupplierDTO> findAll() {
        return null;
    }

    @Override
    public void insertSupplier() {

    }
}
