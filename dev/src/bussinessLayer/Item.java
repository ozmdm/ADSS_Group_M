package bussinessLayer;

public class Item {
    private int id;
    private String description;
    private  String manufactuer;

    public Item(int id, String description, String manufactuer) {
        this.id = id;
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

	public static Item getItem(int itemId) {
		return null;
	}

}
