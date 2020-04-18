package bussinessLayer;

import Data.Data;

public class Item {
    private static int index = 1;
    private int id;
    private String description;
    private  String manufactuer;

    public Item(String description, String manufactuer) {
        id = index;
        index += 1;
        this.description = description;
        this.manufactuer = manufactuer;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getManufactuer() {
        return manufactuer;
    }

    public String toString (){
        return "itemId : "+ id +  ", "+"item Description : "+description+", "+"manufacturer :" + manufactuer;
    }

	public static void loadFirstItems() {
        Item item = new Item("Milk", "tnuva");
        Item item1 = new Item("cheese", "tnuva");
        Item item2 = new Item("meat", "Korkevados");
        Item item3 = new Item("Steak", "korkevados");
        
        Data.getItems().add(item);
        Data.getItems().add(item1);
        Data.getItems().add(item2);
        Data.getItems().add(item3);
	}

}
