package DataAccessLaye;

import ServiceLayer.ServiceObjects.SupplierDTO;

import java.sql.SQLException;
import java.util.List;

public interface ISupplierDAO {

    SupplierDTO find(int supplierId) throws SQLException;
    List<SupplierDTO> findAll() throws SQLException;
    void insertSupplier(bussinessLayer.SupplierPackage.Supplier supplier) throws SQLException;
}
