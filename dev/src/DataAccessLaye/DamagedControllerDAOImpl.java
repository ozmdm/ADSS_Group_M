package DataAccessLaye;

import ServiceLayer.ServiceObjects.DamagedControllerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DamagedControllerDAOImpl implements IDamagedControllerDAO {
    private Connection conn;
    public DamagedControllerDAOImpl(Connection conn)
    {
        this.conn = conn;
    }

    @Override
    public DamagedControllerDTO find(int branchId, int itemId) throws SQLException {
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

        DamagedControllerDTO damagedControllerDTO = new DamagedControllerDTO(branchIdO, itemIdO, quantityO);
        return damagedControllerDTO;
    }

    @Override
    public void insert(DamagedControllerDTO damagedControllerDTO) throws SQLException {
        String sql = "INSERT INTO DamagedItem(branchID,itemId, quantity) VALUES(?,?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, damagedControllerDTO.getBranchId());
        pstmt.setInt(2, damagedControllerDTO.getItemId());
        pstmt.setInt(3, damagedControllerDTO.getQuantityDamaged());
        pstmt.executeUpdate();
    }

    @Override
    public List<DamagedControllerDTO> findAll(int branchId) throws SQLException {
        List<DamagedControllerDTO> damagedControllerDTOS = new ArrayList<>();

        String sql = "SELECT * "
                + "FROM DamagedItem WHERE branchId = ?" +
                "order By itemId";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, branchId);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            int itemIdO = rs.getInt("itemId");
            int quantityO = rs.getInt("quantity");
            DamagedControllerDTO damagedControllerDTO = new DamagedControllerDTO(branchId, itemIdO, quantityO);
            damagedControllerDTOS.add(damagedControllerDTO);
        }
        return damagedControllerDTOS;
    }
}
