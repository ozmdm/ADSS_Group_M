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

	public void addItemToCart(CatalogItem catItem, int amount, double priceAfterDiscount) {
        itemsToDelivery.add(new LineCatalogItem(catItem, amount,priceAfterDiscount));
        this.totalAmount += amount;
        totalPrice += priceAfterDiscount*(double)amount;
	}

	public void removeFromCart(int catalogItemId) throws Exception {
        int i=0;
        for(LineCatalogItem lineCatItem : itemsToDelivery){
            if(lineCatItem.getCatalogItemId() == catalogItemId){
                itemsToDelivery.remove(i);
                this.totalAmount -= lineCatItem.getAmount();
                totalPrice -= (double)lineCatItem.getAmount()*lineCatItem.getPriceAfterDiscount();
                return;
            }
            i+=1;
        }
        throw new Exception("Item does not Exist in the cart.");
    }

    /**
     * @return the totalAmount
     */
    public int getTotalAmount() {
        return totalAmount;
    }

    /**
     * @return the totalPrice
     */
    public double getTotalPrice() {
        return totalPrice;
    }
    
    @Override
    public String toString() {
    	String s ="\nItem name:ID\tPrice\tAmount";
    	for(LineCatalogItem item : itemsToDelivery) {
    		s += "\n" + item.toString();
    	}
    	s += "\nTotal:\t" + totalPrice;
    	return s;
    }

	public List<LineCatalogItem> getItemsToDelivery() {
		return itemsToDelivery;
	}
    
    
    
}
