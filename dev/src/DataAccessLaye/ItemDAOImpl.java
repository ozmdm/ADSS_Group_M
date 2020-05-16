package DataAccessLaye;

import ServiceLayer.ServiceObjects.DamagedItemDTO;
import ServiceLayer.ServiceObjects.ItemDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements IItemDAO {
    private Connection conn;

    public ItemDAOImpl(Connection conn)
    {
        this.conn = conn;
    }

    @Override
    public ItemDTO find() throws SQLException {
        String sql = "SELECT * "
                + "FROM Item order by id";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        //
        ResultSet rs = pstmt.executeQuery();

        int idO = rs.getInt("id");
        String descriptionO = rs.getString("description");
        double costPriceO = rs.getDouble("costPrice");
        double salePriceO = rs.getDouble("salePrice");
        double weightO = rs.getDouble("weight");
        String category = rs.getString("category");
        String subCategory = rs.getString("subCategory");
        String sub2Category = rs.getString("sub2Category");
        String manufacturer = rs.getString("manufacturer");
        int costCounter = rs.getInt("costCounter");
        int saleCounter = rs.getInt("saleCounter");


        ItemDTO itemDTO = new ItemDTO(idO, descriptionO, costPriceO, salePriceO, weightO, category, subCategory, sub2Category, manufacturer, costCounter, saleCounter);
        return itemDTO;    }

    @Override
    public void insert(ItemDTO itemDTO) throws SQLException {
        String sql = "INSERT INTO Item(id, description, costPrice, salePrice, weight, category, subCategory, sub2Category, manufacturer, costCounter, saleCounter) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, itemDTO.getId());
        pstmt.setString(2, itemDTO.getDescription());
        pstmt.setDouble(3,itemDTO.getCostPrice());
        pstmt.setDouble(4,itemDTO.getSalePrice());
        pstmt.setDouble(5,itemDTO.getWeight());
        pstmt.setString(6,itemDTO.getCategory());
        pstmt.setString(7,itemDTO.getSubCategory());
        pstmt.setString(8,itemDTO.getSub2Category());
        pstmt.setString(9,itemDTO.getManufacturer());
        pstmt.setInt(10,itemDTO.getCostCounter());
        pstmt.setInt(11,itemDTO.getSaleCounter());
        pstmt.executeUpdate();
    }

    @Override
    public List<ItemDTO> findAll() throws SQLException {
        List<ItemDTO> itemDTOS = new ArrayList<>();

        String sql = "SELECT * "
                + "FROM Item order by id";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            int idO = rs.getInt("id");
            String descriptionO = rs.getString("description");
            double costPriceO = rs.getDouble("costPrice");
            double salePriceO = rs.getDouble("salePrice");
            double weightO = rs.getDouble("weight");
            String category = rs.getString("category");
            String subCategory = rs.getString("subCategory");
            String sub2Category = rs.getString("sub2Category");
            String manufacturer = rs.getString("manufacturer");
            int costCounter = rs.getInt("costCounter");
            int saleCounter = rs.getInt("saleCounter");
            ItemDTO itemDTO = new ItemDTO(idO, descriptionO, costPriceO, salePriceO, weightO, category, subCategory, sub2Category, manufacturer, costCounter, saleCounter);
            itemDTOS.add(itemDTO);
        }
        return itemDTOS;
    }
}
