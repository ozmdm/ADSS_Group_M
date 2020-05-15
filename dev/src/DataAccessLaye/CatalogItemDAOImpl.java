package DataAccessLaye;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ServiceLayer.ServiceObjects.CatalogDTO;
import ServiceLayer.ServiceObjects.CatalogItemDTO;

public class CatalogItemDAOImpl implements ICatalogItemDAO {


    private Connection conn;

    public CatalogItemDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public CatalogItemDTO find(int catalogItemId) throws SQLException { // TODO : check if miss some fields in tables..
        String sql = "SELECT * "
                + "FROM CatalogItem WHERE catalogItemId = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        // set the value
        pstmt.setDouble(1, catalogItemId);
        //
        ResultSet rs = pstmt.executeQuery();

        int catalogItemIds = rs.getInt("catalogItemId");
        int itemId = rs.getInt("itemId");
        double price = rs.getDouble("price");
        String description = rs.getString("description");
        int contractId = rs.getInt("contractId");

        CatalogItemDTO catalogItemDTO = new CatalogItemDTO(catalogItemIds, description, price, itemId, contractId);
        return catalogItemDTO;
    }

    @Override
    public void insert(CatalogItemDTO catalogItemDTO) throws SQLException {
        String sql = "INSERT INTO CatalogItem(catalogItemId,contractId, price, description) VALUES(?,?,?,?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, catalogItemDTO.getCatalogItemId());
        pstmt.setInt(2, catalogItemDTO.getContractId());
        pstmt.setInt(3, catalogItemDTO.getItemId());
        pstmt.setDouble(4, catalogItemDTO.getPrice());
        pstmt.setString(5, catalogItemDTO.getDescription());
        pstmt.executeUpdate();

    }

    @Override
    public void deleteCatalogItem(int contractId, int catalogItemId) {

    }

    @Override
    public List<CatalogItemDTO> findAll() throws SQLException {
        List<CatalogItemDTO> catalogItemDTOS = new ArrayList<>();

        String sql = "SELECT * "
                + "FROM CatalogItem" +
                "order By itemId";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        //
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            int catalogItemIds = rs.getInt("catalogItemId");
            int itemId = rs.getInt("itemId");
            double price = rs.getDouble("price");
            String description = rs.getString("description");
            int contractId = rs.getInt("contractId");
            CatalogItemDTO catalogItemDTO = new CatalogItemDTO(catalogItemIds, description, price, itemId, contractId);
            catalogItemDTOS.add(catalogItemDTO);
        }
        return catalogItemDTOS;
    }
}
