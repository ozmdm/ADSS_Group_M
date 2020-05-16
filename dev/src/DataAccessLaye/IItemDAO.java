package DataAccessLaye;

import ServiceLayer.ServiceObjects.ItemDTO;

import java.sql.SQLException;
import java.util.List;

public interface IItemDAO {
    ItemDTO find() throws SQLException;

    void insert(ItemDTO itemDTO) throws SQLException;

    List<ItemDTO> findAll() throws SQLException;

}
