package DataAccessLaye;

import ServiceLayer.ServiceObjects.CartDTO;
import ServiceLayer.ServiceObjects.LineCatalogItemDTO;
import ServiceLayer.ServiceObjects.OrderDTO;

import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDAOImpl implements IOrderDAO {
    private Connection conn;
    private LineCatalogItemInCartDAOImpl lineCatalogItemInCartDAO;

    public OrderDAOImpl(Connection conn) {
        this.conn = conn;
        this.lineCatalogItemInCartDAO = new LineCatalogItemInCartDAOImpl(conn);
    }

    @Override
    public OrderDTO find(int orderId) throws SQLException {
        String sql = "SELECT * "
                + "FROM Orders WHERE orderId = ? ";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, orderId);
      /*  // set the value
        pstmt.set(1, catalogItemId,contractId);*/
        //
        ResultSet rs = pstmt.executeQuery();
        if(!rs.next()) throw new SQLException("Not Found!");
        int orderIds = rs.getInt("orderId");
        int branchId = rs.getInt("branchId");
        Timestamp actualDeliverDate = rs.getTimestamp("actualDeliverDate");
        String status = rs.getString("status");
        int supplierId = rs.getInt("supplierId");
        Timestamp creationDate = rs.getTimestamp("creationTime");
        Timestamp deliveryDate = rs.getTimestamp("deliveryDate");
        List<LineCatalogItemDTO> lineCatalogItemDTOS = Repo.getInstance().getAllCatalogItemByOrder(orderId);
        int totalAmount = 0;
        double totalPrice = 0;
        for (LineCatalogItemDTO lineCatalogItemDTO : lineCatalogItemDTOS) {
            totalAmount = totalAmount + lineCatalogItemDTO.getAmount();
            totalPrice = totalPrice + lineCatalogItemDTO.getPriceAfterDiscount();
        }
        CartDTO cartDTO = new CartDTO(lineCatalogItemDTOS, totalAmount, totalPrice);
        OrderDTO orderDTO = new OrderDTO(orderIds, supplierId, status, LocalDateTime.from(creationDate.toInstant()), LocalDateTime.from(deliveryDate.toInstant()), LocalDateTime.from(actualDeliverDate.toInstant()), cartDTO, branchId); // TO DO : CREAT CART
        return orderDTO;
    }

    @Override
    public List<OrderDTO> findAll() throws SQLException {
        List<OrderDTO> orderDTOS = new ArrayList<>();
        String sql = "SELECT * "
                + "FROM Orders ";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        //
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            int orderIds = rs.getInt("orderId");
            int branchId = rs.getInt("branchId");
            Timestamp actualDeliverDate = rs.getTimestamp("actualDeliverDate");
            String status = rs.getString("status");
            int supplierId = rs.getInt("supplierId");
            Timestamp creationDate = rs.getTimestamp("creationTime");
            Timestamp deliveryDate = rs.getTimestamp("deliveryDate");
            List<LineCatalogItemDTO> lineCatalogItemDTOS = Repo.getInstance().getAllCatalogItemByOrder(orderIds);
            int totalAmount = 0;
            double totalPrice = 0;
            for (LineCatalogItemDTO lineCatalogItemDTO : lineCatalogItemDTOS) {
                totalAmount = totalAmount + lineCatalogItemDTO.getAmount();
                totalPrice = totalPrice + lineCatalogItemDTO.getPriceAfterDiscount();
            }
            CartDTO cartDTO = new CartDTO(lineCatalogItemDTOS, totalAmount, totalPrice);
            LocalDateTime actual;try{ actual = LocalDateTime.from(actualDeliverDate.toInstant());}catch(Exception e) {actual = null;}
            LocalDateTime estimate; try{estimate = LocalDateTime.from(deliveryDate.toInstant());}catch(Exception e) {estimate = null;}
            LocalDateTime creation; try{creation = LocalDateTime.from(creationDate.toInstant());}catch(Exception e) {creation = null;}
            OrderDTO orderDTO = new OrderDTO(orderIds, supplierId, status, creation, estimate, actual, cartDTO, branchId); // TO DO : CREAT CART
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;


    }

    @Override
    public void insert(OrderDTO orderDTO) throws SQLException {
        String sql = "INSERT INTO Orders(branchId,status,supplierId,creationTime,deliveryDate,orderId) VALUES(?,?,?,?,?,?)";
        
        int orderId = this.findAll().size()+1;

        PreparedStatement pstmt = conn.prepareStatement(sql);
        //pstmt.setInt(1, orderDTO.getOrderId());
        pstmt.setInt(1, orderDTO.getBranchId());
        //pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(orderDTO.getActualDate())); // TODO : chack how actual day delivery initiate???
        //pstmt.setTimestamp(2, null);
        pstmt.setString(2, orderDTO.getOrderStatus());
        pstmt.setInt(3, orderDTO.getSupplierId());
        pstmt.setTimestamp(4, java.sql.Timestamp.valueOf(orderDTO.getCreationDate()));
        pstmt.setTimestamp(5, java.sql.Timestamp.valueOf(orderDTO.getDeliveryDate()));
        pstmt.setInt(6, orderId);
        
        pstmt.executeUpdate();
        
        if (orderDTO.getCart().getLineItems().size() > 0) {
            for (LineCatalogItemDTO lineCatalogItemDTO : orderDTO.getCart().getLineItems()) {
                Repo.getInstance().insertLineCatalogItem(lineCatalogItemDTO, orderId);
            }
        }

    }
}
