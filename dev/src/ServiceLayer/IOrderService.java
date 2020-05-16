package ServiceLayer;

import java.util.Date;
import java.util.List;

import ServiceLayer.ServiceObjects.*;


public interface IOrderService {

    /**
     * Get order details for a specific order by id
     * @param orderId
     * @return if success returns OrderDTO with all the details about the order else
     * the respose holds error message
     */
    public ResponseT<OrderDTO> getOrderDetails(int orderId);

    /**
     * Creates a new empty order
     * @param supplierId supplier id of the supplier we want to order from
     * @param branchId branch id of the ordering branch
     * @return the orderId which created
     */
    public ResponseT<Integer> createAnOrder(int supplierId, int branchId);

    /**
     * Add item to Order
     * @param orderId The order Id
     * @param catalogItemId The catalog item Id which we want to add to the order
     * @param amount The amount of the catalog item we want to buy
     * @return if success "Done", else error message
     */
    public Response addItemToCart(int orderId, int catalogItemId, int amount);

    /**
     * Remove item from order
     * @param orderId The order Id
     * @param catalogItemId The catalog item which we want to remove from the order
     * @return if success "Done", else error message
     */
    public Response removeFromCart(int orderId, int catalogItemId);

    /**
     * Change order status to IN PROGGRESS.
     * Meaning that the order will not be allowed to change
     * from now on.
     * @param orderId The order ID
     * @return if success "Done", else error message
     */
    public Response sendOrder(int orderId);

    /**
     * Confirms that the order arrived to branch and
     * changes its status to "COMPLETED"
     * @param orderId The order ID
     * @return if success "Done", else error message
     */
    public Response endOrder(int orderId);

    /**
     * Get the supplier's orders
     * @param supplierId The supplier ID
     * @return List of OrderDTO which contains all the orders details of
     * the supplier
     */
    public ResponseT<List<OrderDTO>> printOrdersFromSupplier(int supplierId);

    /**
     * Invokes a thread which will manage all scheduled orders
     * @return if success "Done", else error message
     */
    public Response startScheduledOrder();

    /**
     * Invoked by the Timer thread to create an order
     * to next week with specific details
     * @param scheduled contains all the details about the order
     * @param date to which date the order
     * @return if success "Done", else error message
     */
    public Response createScheduledOrder(ScheduledDTO scheduled, Date date);

    /**
     * subscribe to regular deliveries from a certain supplier
     * @param schedule contains all the details about the schedule order
     * @return if success "Done", else error message
     */
    public Response subscribeScheduleOrder(ScheduledDTO schedule);

    /**
     * Kill the Timer which handles regular deliveries
     * @return if success "Done", else error message
     */
	public Response purgeTimer();
    
}