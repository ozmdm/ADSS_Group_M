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
    public SupplierDTO getSupplierById(int supplierId) {
        return null;
    }

    @Override
    public void updateSupplier() {

    }

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        return null;
    }

    @Override
    public void insertSupplier() {

    }
}
