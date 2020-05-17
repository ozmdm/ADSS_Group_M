package bussinessLayer.OrderPackage;

import java.time.LocalDateTime;
import java.util.Date;

import Data.Data;
import ServiceLayer.ServiceObjects.CatalogItemDTO;
import ServiceLayer.ServiceObjects.OrderDTO;
import ServiceLayer.ServiceObjects.ScheduledDTO;
import javafx.util.Pair;

public class Order {

    enum Status {
        OPEN, INPROGRESS, COMPLETE
    }

    private Status status = Status.OPEN;
    private Cart cart = new Cart();
    private bussinessLayer.SupplierPackage.Supplier supplier;
    private int orderId = -1;
    private LocalDateTime dateTimeAtCreation = LocalDateTime.now();
    private LocalDateTime deliveryDate;
    private LocalDateTime actualDeliveryDate = null;
    private int branchId;


    public Order(int supplierId, int branchId) throws Exception {
        this.supplier = Data.getSupplierById(supplierId);
        this.branchId = branchId;
        deliveryDate = null;
    }

    public Order(ScheduledDTO scheduled, Date date) throws Exception {
        this.supplier = Data.getSupplierById(scheduled.getSupplierId());// TODO NEED TO CHANGE TO DB
        //branchId = Data.getBranchById(scheduled.getBranchId()).getBranchId(); //TODO
        deliveryDate = LocalDateTime.from(date.toInstant());
        fillCart(scheduled);
    }

    public Order(OrderDTO orderDTO) {
        status = Status.valueOf(orderDTO.getOrderStatus());
	}

	private void fillCart(ScheduledDTO scheduled)throws Exception {
        for (Pair<CatalogItemDTO, Integer> it : scheduled.getItemsToOrder()) {
            addItemToCart(it.getKey().getCatalogItemId(), it.getValue());
        }
    }

    /**
     * @return the branchId
     */
    public int getBranchId() {
        return branchId;
    }

    /**
     * @return the orderId
     */
    public int getOrderId() {
        return orderId;
    }

    public void addItemToCart(int catalogItemId, int amount) throws Exception {
        if (amount <= 0) throw new Exception("Amount must be larger than zero");
        cart.addItemToCart(supplier.getCatalogItem(catalogItemId), amount, supplier.getPriceAfterDiscountByItem(catalogItemId, amount));
    }

    public void removeFromCart(int catalogItemId) throws Exception {
        cart.removeFromCart(catalogItemId);
    }

    public void sendOrder() throws Exception {
        if (!status.toString().equals("OPEN")) throw new Exception("Order is not OPEN");
        deliveryDate = supplier.getNextDateOfDelivery();
        status = Status.INPROGRESS;
    }

    /**
     * @return the status
     */
    public Status getOrderStatus() {
        return status;
    }

    public void endOrder() throws Exception {
        if (status.toString().equals("COMPLETE")) throw new Exception("Already completed");
        if (status.toString().equals("OPEN")) throw new Exception("The order is still OPEN confirm it first");
        status = Status.COMPLETE;
    }

    public int getSupplierId() {
        return supplier.getSupplierId();
    }


	/**
     * @return the cart
     */
    public Cart getCart() {
        return cart;
    }


	/**
     * @return the creation date
     */
    public LocalDateTime getCreationDate() {
        return dateTimeAtCreation;
    }


	/**
     * @return the deliveryDate
     */
    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }
    
    /**
     * @return the actualDeliveryDate
     */
    public LocalDateTime getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    /**
     * Convert BL Order to OrderDTO
     * @return
     */
	public OrderDTO converToDTO() {
        return new ServiceLayer.ServiceObjects.OrderDTO(orderId, getSupplierId(),
                getOrderStatus().name(), dateTimeAtCreation, deliveryDate, actualDeliveryDate, cart.converToDTO(), branchId);
	}
}
