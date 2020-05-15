package DataAccessLaye;

import ServiceLayer.ServiceObjects.SupplierDTO;

import java.util.List;

public interface ISupplierDAO {

    SupplierDTO find(int supplierId);
    List<SupplierDTO> findAll();
    void insertSupplier();
}
