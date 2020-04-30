package ServiceLayer;

public interface IOrderService {

    public String getOrderDetails(int orderId);
    public String createAnOrder(int supplierId);
    public String addItemToCart(int orderId, int catalogItemId, int amount);
    public String removeFromCart(int orderId, int catalogItemId);
    public String sendOrder(int orderId);
    public String getOrderStatus(int orderId);
    public String endOrder(int orderId);
    public String printOrdersFromSupplier(int supplierId);
    
}