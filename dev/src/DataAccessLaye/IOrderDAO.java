package DataAccessLaye;

import bussinessLayer.DTOPackage.OrderDTO;

import java.util.List;

public interface IOrderDAO {

    OrderDTO find(int orderId) throws Exception;
    List<OrderDTO> findAll() throws Exception;
    void insert(OrderDTO orderDTO) throws Exception;



}
