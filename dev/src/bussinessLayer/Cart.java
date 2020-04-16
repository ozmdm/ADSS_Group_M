package bussinessLayer;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<LineCatalogItem> itemsToDelivery;
    private  int totalAmount;
    private double totalPrice;

    public Cart(int totalAmount, double totalPrice) {
        this.totalAmount = totalAmount;
        this.totalPrice = totalPrice;
        itemsToDelivery = new ArrayList<>();
    }

    public void addItemToCart(Item item){

    }
}
