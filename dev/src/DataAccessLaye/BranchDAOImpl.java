package DataAccessLaye;

import ServiceLayer.ServiceObjects.BranchDTO;
import ServiceLayer.ServiceObjects.DamagedItemDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BranchDAOImpl implements IBranchDAO {

    private Connection conn;
    public BranchDAOImpl(Connection conn)
    {
        this.conn = conn;
    }

    @Override
    public BranchDTO find(int branchId) throws SQLException {
        String sql = "SELECT * "
                + "FROM Branch WHERE branchId = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);


        pstmt.setInt(1, branchId);

        //
        ResultSet rs = pstmt.executeQuery();
        String description = rs.getString("description");

        BranchDTO branchDTO = new BranchDTO(branchId,description);
        return branchDTO;
    }

    @Override
    public void insert(BranchDTO branchDTO) throws SQLException {
        String sql = "INSERT INTO Branch(branchID,description) VALUES(?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, branchDTO.getId());
        pstmt.setString(2,branchDTO.getDescription());
        pstmt.executeUpdate();
    }

    @Override
    public List<BranchDTO> findAll() throws SQLException {
        List<BranchDTO> branchDTOS = new ArrayList<>();

        String sql = "SELECT * "
                + "FROM Branch order By branchId";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            int branchIdO = rs.getInt("branchId");
            String descriptionO = rs.getString("description");
            BranchDTO branchDTO = new BranchDTO(branchIdO, descriptionO);
            branchDTOS.add(branchDTO);
        }
        return branchDTOS;
    }
}
