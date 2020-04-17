package Data;

import bussinessLayer.Order;
import bussinessLayer.Supplier;

import java.util.ArrayList;
import java.util.List;

import bussinessLayer.Item;
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

    public static Item getItemById(int itemId){
        for(Item item : items){
            if(item.getId() == itemId) return item;
        }
        return null;
    }

    public static Supplier getSupplierById(int supplierId){
        for(Supplier supplier : suppliers){
            if(supplier.getSupplierId() == supplierId) return supplier;
        }
        return null;
    }
}