package DataAccessLaye;

import ServiceLayer.ServiceObjects.DamagedItemDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DamagedItemDAOImpl implements IDamagedItemDAO{
    private Connection conn;
    public DamagedItemDAOImpl(Connection conn)
    {
        this.conn = conn;
    }

    @Override
    public DamagedItemDTO find(int branchId, int itemId) throws SQLException {
        String sql = "SELECT * "
                + "FROM DamagedItem WHERE branchId = ? AND itemId = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);


        pstmt.setInt(1, branchId);
        pstmt.setInt(2,itemId);
        //
        ResultSet rs = pstmt.executeQuery();

        int branchIdO = rs.getInt("branchId");
        int itemIdO = rs.getInt("itemId");
        int quantityO = rs.getInt("quantity");

        DamagedItemDTO damagedItemDTO = new DamagedItemDTO(branchIdO, itemIdO, quantityO);
        return damagedItemDTO;
    }

    @Override
    public void insert(DamagedItemDTO damagedItemDTO) throws SQLException {
        String sql = "INSERT INTO DamagedItem(branchID,itemId, quantity) VALUES(?,?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, damagedItemDTO.getBranchId());
        pstmt.setInt(2, damagedItemDTO.getItemId());
        pstmt.setInt(3, damagedItemDTO.getQuantityDamaged());
        pstmt.executeUpdate();
    }

    @Override
    public List<DamagedItemDTO> findAll(int branchId) throws SQLException {
        List<DamagedItemDTO> damagedItemDTOS = new ArrayList<>();

        String sql = "SELECT * "
                + "FROM DamagedItem WHERE branchId = ?" +
                "order By itemId";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, branchId);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            int itemIdO = rs.getInt("itemId");
            int quantityO = rs.getInt("quantity");
            DamagedItemDTO damagedItemDTO = new DamagedItemDTO(branchId, itemIdO, quantityO);
            damagedItemDTOS.add(damagedItemDTO);
        }
        return damagedItemDTOS;
    }
}
