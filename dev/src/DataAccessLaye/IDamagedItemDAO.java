package DataAccessLaye;

import ServiceLayer.ServiceObjects.DamagedItemDTO;

import java.sql.SQLException;
import java.util.List;

public interface IDamagedItemDAO {

    DamagedItemDTO find(int branchId, int itemId) throws SQLException;
    void insert(DamagedItemDTO damagedItemDTO) throws SQLException;
    List<DamagedItemDTO> findAll(int branchId) throws SQLException;

}
