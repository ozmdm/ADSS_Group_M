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
        int orderIds = rs.getInt("orderId");
        int branchId = rs.getInt("branchId");
        Timestamp actualDeliverDate = rs.getTimestamp("actualDeliverDate");
        String status = rs.getString("status");
        int supplierId = rs.getInt("supplierId");
        Timestamp creationDate = rs.getTimestamp("creationTime");
        Timestamp deliveryDate = rs.getTimestamp("deliveryDate");
        List<LineCatalogItemDTO> lineCatalogItemDTOS = lineCatalogItemInCartDAO.findAllByOrderId(orderIds);
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
            List<LineCatalogItemDTO> lineCatalogItemDTOS = lineCatalogItemInCartDAO.findAllByOrderId(orderIds);
            int totalAmount = 0;
            double totalPrice = 0;
            for (LineCatalogItemDTO lineCatalogItemDTO : lineCatalogItemDTOS) {
                totalAmount = totalAmount + lineCatalogItemDTO.getAmount();
                totalPrice = totalPrice + lineCatalogItemDTO.getPriceAfterDiscount();
            }
            CartDTO cartDTO = new CartDTO(lineCatalogItemDTOS, totalAmount, totalPrice);
            OrderDTO orderDTO = new OrderDTO(orderIds, supplierId, status, LocalDateTime.from(creationDate.toInstant()), LocalDateTime.from(deliveryDate.toInstant()), LocalDateTime.from(actualDeliverDate.toInstant()), cartDTO, branchId); // TO DO : CREAT CART
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;


    }

    @Override
    public void insert(OrderDTO orderDTO) throws SQLException {
        String sql = "INSERT INTO Orders(branchId,actualDeliverDate,status,supplierId,creationTime,deliveryDate) VALUES(?,?,?,?,?,?)";


        PreparedStatement pstmt = conn.prepareStatement(sql);
        //pstmt.setInt(1, orderDTO.getOrderId());
        pstmt.setInt(1, orderDTO.getBranchId());
        pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(orderDTO.getActualDate())); // TODO : chack how actual day delivery initiate???
        pstmt.setString(3, orderDTO.getOrderStatus());
        pstmt.setInt(4, orderDTO.getSupplierId());
        pstmt.setTimestamp(5, java.sql.Timestamp.valueOf(orderDTO.getCreationDate()));
        pstmt.setTimestamp(6, java.sql.Timestamp.valueOf(orderDTO.getDeliveryDate()));
        pstmt.executeUpdate();
        int orderId = this.findAll().size();
        if (orderDTO.getCart().getLineItems().size() > 0) {
            for (LineCatalogItemDTO lineCatalogItemDTO : orderDTO.getCart().getLineItems()) {
                Repo.repo.insertLineCatalogItem(lineCatalogItemDTO, orderId);
            }
        }

    }
}
