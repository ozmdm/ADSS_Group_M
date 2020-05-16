package ServiceLayer.ServiceObjects;

public class ItemDTO {

    private int id;
    private String description;
    private double costPrice;
    private double salePrice;
    private double weight;
    private String category;
    private String subCategory;
    private String sub2Category;
    private String manufacturer;
    private int costCounter;
    private int saleCounter;

    public ItemDTO(int id, String description, double costPrice, double salePrice, double weight, String category, String subCategory, String sub2Category, String manufacturer, int costCounter, int saleCounter) {
        this.id = id;
        this.description = description;
        this.costPrice = costPrice;
        this.salePrice = salePrice;
        this.weight = weight;
        this.category = category;
        this.subCategory = subCategory;
        this.sub2Category = sub2Category;
        this.manufacturer = manufacturer;
        this.costCounter = costCounter;
        this.saleCounter = saleCounter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getSub2Category() {
        return sub2Category;
    }

    public void setSub2Category(String sub2Category) {
        this.sub2Category = sub2Category;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getCostCounter() {
        return costCounter;
    }

    public void setCostCounter(int costCounter) {
        this.costCounter = costCounter;
    }

    public int getSaleCounter() {
        return saleCounter;
    }

    public void setSaleCounter(int saleCounter) {
        this.saleCounter = saleCounter;
    }
}
