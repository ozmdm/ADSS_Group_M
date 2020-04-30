package Data;

import bussinessLayer.OrderPackage.Order;
import bussinessLayer.SupplierPackage.Supplier;
import bussinessLayer.OrderPackage.Item;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private static List<Order> orders = new ArrayList<Order>();
    private static List<Supplier> suppliers = new ArrayList<Supplier>();
    private static List<Item> items = new ArrayList<Item>();

    /**
     * @return the orders
     */
    public static List<Order> getOrders() {
        return orders;
    }

    /**
     * @return the suppliers
     */
    public static List<Supplier> getSuppliers() {
        return suppliers;
    }

    /**
     * @return the items
     */
    public static List<Item> getItems() {
        return items;
    }

    public static Item getItemById(int itemId) {
        for (Item item : items) {
            if (item.getId() == itemId) return item;
        }
        return null;
    }

    public static Supplier getSupplierById(int supplierId) throws Exception {
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierId() == supplierId) return supplier;
        }
        throw new Exception("Supplier does not exist");
    }

    public static String toStringItemsList() {
        String s = "";
        for (Item item : getItems()) {
            s = s + "\n" + item.toString();
        }
        return s;
    }

    public static String addItem(String desc, String mainfac) throws Exception {
        for (Item i : getItems()) {
            if (i.getDescription().equals(desc) && i.getManufactuer().equals(mainfac))
                throw new Exception("the item already exist");
        }
        Item item = new Item(desc, mainfac);
        getItems().add(item);
        return "Done";
    }

}
