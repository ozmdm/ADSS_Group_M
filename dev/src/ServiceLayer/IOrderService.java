package ServiceLayer;

import java.util.List;

import ServiceLayer.ServiceObjects.*;


public interface IOrderService {

    public ResponseT<OrderDTO> getOrderDetails(int orderId);
    public ResponseT<Integer> createAnOrder(int supplierId);
    public Response addItemToCart(int orderId, int catalogItemId, int amount);
    public Response removeFromCart(int orderId, int catalogItemId);
    public Response sendOrder(int orderId);
    public Response endOrder(int orderId);
    public ResponseT<List<OrderDTO>> printOrdersFromSupplier(int supplierId);
    
}