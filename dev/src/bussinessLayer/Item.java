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

}
