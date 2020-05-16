package DataAccessLaye;

import ServiceLayer.ServiceObjects.BranchDTO;
import ServiceLayer.ServiceObjects.InventoryDTO;

import java.sql.SQLException;
import java.util.List;

public interface IInventoryDAO {
    InventoryDTO find() throws SQLException;

    void insert(InventoryDTO inventoryDTO) throws SQLException;
}
