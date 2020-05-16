package DataAccessLaye;

import ServiceLayer.ServiceObjects.CartDTO;
import ServiceLayer.ServiceObjects.LineCatalogItemDTO;
import ServiceLayer.ServiceObjects.OrderDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDAOImpl implements IOrderDAO {
    private Connection conn;
    private LineCatalogItemInCartDAOImpl lineCatalogItemInCartDAO;

    public OrderDAOImpl(Connection conn)
    {
        this.conn = conn;
        this.lineCatalogItemInCartDAO = new LineCatalogItemInCartDAOImpl(conn);
    }

    @Override
    public OrderDTO find(int orderId) throws SQLException {
        String sql = "SELECT * "
                + "FROM Orders WHERE orderId = ? ";

        PreparedStatement pstmt = conn.prepareStatement(sql);

      /*  // set the value
        pstmt.set(1, catalogItemId,contractId);*/
        //
        ResultSet rs = pstmt.executeQuery();
        int orderIds = rs.getInt("orderId");
        int branchId = rs.getInt("branchId");
        Date actualDeliverDate = rs.getDate("actualDeliverDate");
        String  status = rs.getString("status");
        int supplierId = rs.getInt("supplierId");
        Date creationDate = rs.getDate("creationTime");
        Date deliveryDate = rs.getDate("deliveryDate");
        List<LineCatalogItemDTO> lineCatalogItemDTOS = lineCatalogItemInCartDAO.findAllByOrderId(orderIds);
        int totalAmount=0;
        double totalPrice = 0;
        for (LineCatalogItemDTO lineCatalogItemDTO : lineCatalogItemDTOS)
        {
            totalAmount = totalAmount + lineCatalogItemDTO.getAmount();
            totalPrice = totalPrice + lineCatalogItemDTO.getPriceAfterDiscount();
        }
        CartDTO cartDTO = new CartDTO(lineCatalogItemDTOS,totalAmount,totalPrice);
        OrderDTO orderDTO = new OrderDTO(orderIds,supplierId,status,LocalDateTime.from(creationDate.toInstant()),LocalDateTime.from(deliveryDate.toInstant()), LocalDateTime.from(actualDeliverDate.toInstant()),cartDTO,branchId); // TO DO : CREAT CART
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
            Date actualDeliverDate = rs.getDate("actualDeliverDate");
            String  status = rs.getString("status");
            int supplierId = rs.getInt("supplierId");
            Date creationDate = rs.getDate("creationTime");
            Date deliveryDate = rs.getDate("deliveryDate");
            List<LineCatalogItemDTO> lineCatalogItemDTOS = lineCatalogItemInCartDAO.findAllByOrderId(orderIds);
            int totalAmount=0;
            double totalPrice = 0;
            for (LineCatalogItemDTO lineCatalogItemDTO : lineCatalogItemDTOS)
            {
                totalAmount = totalAmount + lineCatalogItemDTO.getAmount();
                totalPrice = totalPrice + lineCatalogItemDTO.getPriceAfterDiscount();
            }
            CartDTO cartDTO = new CartDTO(lineCatalogItemDTOS,totalAmount,totalPrice);
            OrderDTO orderDTO = new OrderDTO(orderIds,supplierId,status,LocalDateTime.from(creationDate.toInstant()),LocalDateTime.from(deliveryDate.toInstant()), LocalDateTime.from(actualDeliverDate.toInstant()),cartDTO,branchId); // TO DO : CREAT CART
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;


    }

    @Override
    public void insert(OrderDTO orderDTO) throws SQLException {
        String sql = "INSERT INTO Orders(orderId,branchId,actualDeliverDate,status,supplierId,creationTime,deliveryDate) VALUES(?,?,?,?,?,?,?)";

        LocalDate creationD = LocalDate.of(orderDTO.getCreationDate().getYear(),orderDTO.getCreationDate().getMonth(),orderDTO.getCreationDate().getDayOfMonth());
        LocalDate deliveryD = LocalDate.of(orderDTO.getDeliveryDate().getYear(),orderDTO.getDeliveryDate().getMonth(),orderDTO.getDeliveryDate().getDayOfMonth());
        LocalDate actualD = LocalDate.of(orderDTO.getActualDate().getYear(),orderDTO.getActualDate().getMonth(),orderDTO.getActualDate().getDayOfMonth());

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,orderDTO.getOrderId());
        pstmt.setInt(2, orderDTO.getBranchId());
        pstmt.setDate(3, java.sql.Timestamp.valueOf(order)); // TODO : chack how actual day delivery initiate???
        pstmt.setString(4, orderDTO.getOrderStatus());
        pstmt.setInt(5, orderDTO.getSupplierId());
        pstmt.setDate(6, java.sql.Date.valueOf(creationD));
        pstmt.setDate(7, java.sql.Date.valueOf(deliveryD));
        pstmt.executeUpdate();

    }
}
