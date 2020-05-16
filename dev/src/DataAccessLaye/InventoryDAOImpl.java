package DataAccessLaye;

import ServiceLayer.ServiceObjects.InventoryDTO;
import ServiceLayer.ServiceObjects.OldSalePriceDTO;
import bussinessLayer.InventoryPackage.Inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class InventoryDAOImpl implements IInventoryDAO {
    private Connection conn;

    public InventoryDAOImpl(Connection conn)
    {
        this.conn = conn;
    }

    @Override
    public InventoryDTO find() throws SQLException {
        String sql = "SELECT * "
                + "FROM Inventory";

        PreparedStatement pstmt = conn.prepareStatement(sql);


        //
        ResultSet rs = pstmt.executeQuery();

        int idCounter = rs.getInt("idCounter");

        InventoryDTO inventoryDTO = new InventoryDTO(idCounter);
        return inventoryDTO;
    }

    @Override
    public void insert(InventoryDTO inventoryDTO) throws SQLException {
        String sql = "INSERT INTO Inventory(idCounter) VALUES(?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, inventoryDTO.getIdCounter());
        pstmt.executeUpdate();
    }

}
