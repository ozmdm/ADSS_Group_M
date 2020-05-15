package Data;

import bussinessLayer.OrderPackage.Order;
import bussinessLayer.SupplierPackage.Supplier;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private static List<Order> orders = new ArrayList<Order>();
    private static List<Supplier> suppliers = new ArrayList<Supplier>();

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

    public static Supplier getSupplierById(int supplierId) throws Exception {
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierId() == supplierId) return supplier;
        }
        throw new Exception("Supplier does not exist");
    }
    
    public static void clean() {
    	orders = new ArrayList<Order>();
    	suppliers = new ArrayList<Supplier>();
    }

}
