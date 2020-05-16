package DataAccessLaye;

import ServiceLayer.ServiceObjects.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LineCatalogItemInCartDAOImpl implements ILineCatalogItemInCartDAO {
    private Connection conn;

    public LineCatalogItemInCartDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public LineCatalogItemDTO find(int orderId, int CatalogItemId) throws SQLException {
        String sql = "SELECT * "
                + "FROM LineCatalogItem WHERE orderId = ? AND catalogItemId = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);

      /*  // set the value
        pstmt.set(1, catalogItemId,contractId);*/
        //
        ResultSet rs = pstmt.executeQuery();
        //int orderIds = rs.getInt("orderId"); // TODO : creatIndexs IN TABLE
        // TODO LATER EXPLAIN TO DOR WHY WE DONT NEED THIS
        int CatalogItemIds = rs.getInt("catalogItemId");
        int amount = rs.getInt("amount");
        double price = rs.getDouble("price");


        LineCatalogItemDTO lineCatalogItemDTO = new LineCatalogItemDTO(CatalogItemIds, amount, price);
        return lineCatalogItemDTO;
    }

    @Override
    public List<LineCatalogItemDTO> findAllByOrderId(int orderId) throws SQLException {
        List<LineCatalogItemDTO> lineCatalogItemDTOS = new ArrayList<>();

        String sql = "SELECT * "
                + "FROM Contract WHERE orderId = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,orderId);
        //
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            int CatalogItemIds = rs.getInt("catalogItemId");
            int amount = rs.getInt("amount");
            double price = rs.getDouble("price");
            LineCatalogItemDTO lineCatalogItemDTO = new LineCatalogItemDTO(CatalogItemIds, amount, price);
            lineCatalogItemDTOS.add(lineCatalogItemDTO);
        }
        return lineCatalogItemDTOS;
    }

    @Override
    public void insert(LineCatalogItemDTO lineCatalogItemDTO,int orderId) throws SQLException {
        String sql = "INSERT INTO LineCatalogItem(orderId,catalogItemId,amount,price) VALUES(?,?,?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, orderId);
        pstmt.setInt(2, lineCatalogItemDTO.getCatalogItemId());
        pstmt.setInt(3, lineCatalogItemDTO.getAmount());
        pstmt.setDouble(4, lineCatalogItemDTO.getPriceAfterDiscount());
        pstmt.executeUpdate();
    }

    @Override
    public void deleteItemFromOrder(int catalodItemId, int orderId) {

    }
}
