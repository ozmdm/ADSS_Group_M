package ServiceLayer.ServiceObjects;

public class OldSalePricesDTO {

    private int itemId;
    private int counter; //saleCounter
    private int price;

    public OldSalePricesDTO(int itemId, int counter, int price) {
        this.itemId = itemId;
        this.counter = counter;
        this.price = price;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
