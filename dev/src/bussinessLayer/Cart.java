package bussinessLayer;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<LineCatalogItem> itemsToDelivery;
    private  int totalAmount;
    private double totalPrice;

    public Cart() {
        this.totalAmount = 0;
        this.totalPrice = 0;
        itemsToDelivery = new ArrayList<LineCatalogItem>();
    }

	public void addItemToCart(CatalogItem catItem, int amount) {
        itemsToDelivery.add(new LineCatalogItem(catItem, amount));
        this.totalAmount += amount;
        //TODO NEEDS TO UPDATE PRICE
	}

	public void removeFromCart(int catalogItemId) {
        int i=0;
        for(LineCatalogItem lineCatItem : itemsToDelivery){
            if(lineCatItem.getCatalogItemId() == catalogItemId){
                itemsToDelivery.remove(i);
                this.totalAmount -= lineCatItem.getAmount();
                //TODO NEEDS TO UPDATE PRICE
                return;
            }
            i+=1;
        }
    }
    
}
