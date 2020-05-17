package DataAccessLaye;

import ServiceLayer.ServiceObjects.InventoryDTO;
import ServiceLayer.ServiceObjects.ItemDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InventoryDAOImpl implements IInventoryDAO {
    private Connection conn;
    private IItemDAO itemDAO;

    public InventoryDAOImpl(Connection conn)
    {
        this.conn = conn;
    }

    @Override
    public InventoryDTO find() throws SQLException {
        String sql = "SELECT * "
                + "FROM Inventory";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        List<ItemDTO> itemDTOS = itemDAO.findAll();
        Map<Integer, ItemDTO> itemDTOMap = new HashMap<>();
        for (ItemDTO itemDTO: itemDTOS) {
            itemDTOMap.put(itemDTO.getId(), itemDTO);
        }

        int idCounter = rs.getInt("idCounter");

        InventoryDTO inventoryDTO = new InventoryDTO(itemDTOMap, idCounter);
        return inventoryDTO;
    }

    @Override
    public void insert(InventoryDTO inventoryDTO) throws SQLException {
        String sql = "INSERT INTO Inventory(idCounter) VALUES(?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, inventoryDTO.getIdCounter());
        pstmt.executeUpdate();
    }

    @Override
    public void updateIdCounter(int idCounter) throws SQLException {
        String sql = "UPDATE Inventory SET idCounter = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, idCounter);

        pstmt.executeUpdate();
    }

}
