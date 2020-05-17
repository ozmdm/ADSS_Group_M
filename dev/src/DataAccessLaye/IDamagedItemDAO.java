package DataAccessLaye;

import ServiceLayer.ServiceObjects.DamagedControllerDTO;

import java.sql.SQLException;
import java.util.List;

public interface IDamagedItemDAO {

    DamagedControllerDTO find(int branchId, int itemId) throws SQLException;
    void insert(DamagedControllerDTO damagedControllerDTO) throws SQLException;
    List<DamagedControllerDTO> findAll(int branchId) throws SQLException;

}
