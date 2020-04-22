package Test;

import Data.Data;
import bussinessLayer.Supplier;
import org.junit.jupiter.api.Test;

import ServiceLayer.OrderService;
import ServiceLayer.SupplierService;
import bussinessLayer.Order;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class TestClass {

    private static SupplierService supService = SupplierService.getInstance();
    private static OrderService oService = OrderService.getInstance();


    @BeforeAll
    public static void setup() {
        supService.loadFirstSuppliers();
        oService.loadFirstItems();
    }

    @Test
    public void createOrder() {
        List<Order> orders = Data.getOrders();
        int ordersSize = orders.size();
        oService.createAnOrder(1);
        assertEquals(ordersSize + 1, orders.size(), "Size of orders wrong");
    }

    @Test
    public void creatSupplier() {
        List<Supplier> suppliers = Data.getSuppliers();
        int suppliersSize = suppliers.size();
        supService.AddSupplier("d", 0, 1, "EOM30", true);
        assertEquals(suppliersSize + 1, suppliers.size(), "Size of suppliers wrong");
    }

    @Test
    public void addItemToCatalog() {
        List<Supplier> suppliers = Data.getSuppliers();
        supService.AddSupplier("d", 0, 1, "EOM30", true);
        int catalogSize = supService.getSupplierById(0).getContract().getCatalog().getItems().size();
        supService.addCatalogItemToCatalogInContract(0, 1, 10, 6);
        assertEquals(catalogSize + 1, supService.getSupplierById(0).getContract().getCatalog().getItems().size(), "Size of catalogItem wrong");
    }

    @Test
    public void RemoveItemFromCatalog() {
        addItemToCatalog();
        int catalogSize = supService.getSupplierById(0).getContract().getCatalog().getItems().size();
        supService.deleteCatalogItemFromCatlogInContract(0, 10);
        assertEquals(catalogSize - 1, supService.getSupplierById(0).getContract().getCatalog().getItems().size(), "Size of catalogItem wrong");
    }

    @Test
    public void IsExist() {
        List<Supplier> suppliers = Data.getSuppliers();
        int suppliersSize = suppliers.size();
        supService.AddSupplier("d", 0, 1, "EOM30", true);
        assertTrue(supService.isExist(0), "dont exist problem with the method");
        supService.removeSupplier(0);
        assertFalse(supService.isExist(0), "supplier exist problem with the method");

    }

    @Test
    public void removeSupplier() {
        List<Supplier> suppliers = Data.getSuppliers();
        int suppliersSize = suppliers.size();
        supService.AddSupplier("d", 0, 1, "EOM30", true);
         suppliersSize = suppliers.size();
        supService.removeSupplier(0);
        suppliers = Data.getSuppliers();
        assertEquals(suppliersSize - 1, suppliers.size(), "Size of suppliers wrong");
    }

    @Test
    public void CompliteOrder() {
        List<Order> orders = Data.getOrders();
        int ordersSize = orders.size();
        oService.createAnOrder(1);
        oService.
        supService.addCatalogItemToCatalogInContract(1,1,1,2.0);
        oService.addItemToCart()
    }

    @Test
    public void h() {
    }

    @Test
    public void i() {
    }

    @Test
    public void j() {
    }
}