package DataAccessLaye;

import ServiceLayer.ServiceObjects.RangeDTO;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RangesDAODAOImpl implements IRangesDAO {
    private Connection conn;

    public RangesDAODAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public HashMap<Integer, List<Pair<RangeDTO, Double>>> findAll(int contractId) throws SQLException {

        HashMap<Integer, List<Pair<RangeDTO, Double>>> res = new HashMap<>();

        String sql = "SELECT * "
                + "FROM Ranges WHERE contractId = ? ORDER BY catalogItemId";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,contractId);

        ResultSet rs = pstmt.executeQuery();
        int currentCatalogItemId = rs.getInt("catalogItemId");
        int catalogItemId;
        while (rs.next()) {
            catalogItemId = rs.getInt("catalogItemId");
            int minimum = rs.getInt("minimum");
            int maximum = rs.getInt("maximum");
            Double price = rs.getDouble("price");
            if(currentCatalogItemId != catalogItemId){
                res.put(catalogItemId,new ArrayList<Pair<RangeDTO, Double>>());
                currentCatalogItemId = catalogItemId;
            }
            res.get(currentCatalogItemId).add(new Pair<RangeDTO,Double>(new RangeDTO(minimum,maximum),price));
        }
        return res;
    }


    @Override
    public void insert(RangeDTO rangeDTO,int contractId,int catalogItemId) throws SQLException {
        String sql = "INSERT INTO Ranges(catalogItemId, contractId,minimum,maximum,price) VALUES(?,?,?,?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, catalogItemId);
        pstmt.setInt(2, contractId);
        pstmt.setInt(3, rangeDTO.getMin());
        pstmt.setDouble(4, rangeDTO.getMax());
        pstmt.executeUpdate();

    }

    @Override
    public void deleteAllRangesByContractId(int contractId, int catalogItemId) {

    }
}
