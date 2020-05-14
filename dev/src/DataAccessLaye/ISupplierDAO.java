package DataAccessLaye;

import ServiceLayer.ServiceObjects.SupplierDTO;

import java.util.List;

public interface ISupplierDAO {

    SupplierDTO getSupplierById(int supplierId);
    void updateSupplier();
    List<SupplierDTO> getAllSuppliers();
    void insertSupplier();
}
