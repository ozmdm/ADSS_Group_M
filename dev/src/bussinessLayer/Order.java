package bussinessLayer;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.print.attribute.standard.DateTimeAtCreation;

public class Order {
    private enum status {};
    private Cart cart;
    private Supplier supplier;
    private int orderId; //TODO CHANGE IN UML
    private DateTimeAtCreation dateTimeAtCreation;
    private DateTimeAtCompleted deliveryDate;

    public Order(Cart cart, Supplier supplier, int id, DateTimeAtCreation dateTimeAtCreation, DateTimeAtCompleted deliveryDate) {
        this.cart = cart;
        this.supplier = supplier;
        this.orderId = id;
        this.dateTimeAtCreation = dateTimeAtCreation;
        this.deliveryDate = deliveryDate;
    }
}
