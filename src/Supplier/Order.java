package Supplier;

import javafx.util.Pair;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.print.attribute.standard.DateTimeAtCreation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private enum status {};
    private List<Pair<CatalogItem,Integer>> itemToDelivery;
    private Supplier supplier;
    private  double totalPrice;
    private int id;
    private  int totalAmount;
    private DateTimeAtCreation dateTimeAtCreation;
    private DateTimeAtCompleted deliveryDate;

    public Order(Supplier supplier, double totalPrice, int id, int totalAmount,int deliveryTimeByYearMinus1900, int deliveryTimeByMonth, int deliveryTimeByDay) {
        this.supplier = supplier;
        this.totalPrice = totalPrice;
        this.id = id;
        this.totalAmount = totalAmount;
        itemToDelivery = new ArrayList<>();
        dateTimeAtCreation = new DateTimeAtCreation(new Date());
        deliveryDate = new DateTimeAtCompleted(new Date(deliveryTimeByYearMinus1900,deliveryTimeByMonth,deliveryTimeByDay));

    }
}
