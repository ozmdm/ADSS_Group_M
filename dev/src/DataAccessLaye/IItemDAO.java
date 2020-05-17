package DataAccessLaye;

import ServiceLayer.ServiceObjects.ItemDTO;

import java.sql.SQLException;
import java.util.List;

public interface IItemDAO {
    ItemDTO find() throws SQLException;

    void insert(ItemDTO itemDTO) throws SQLException;

    List<ItemDTO> findAll() throws SQLException;

    void updateWithoutOldPrices(ItemDTO itemDTO) throws SQLException;

    void updateCostPrice(int itemId, double newPrice, int costCounter) throws SQLException;

    void updateSalePrice(int itemId, double newPrice, int saleCounter) throws SQLException;

}
