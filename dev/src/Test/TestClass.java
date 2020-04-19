package Test;
import org.junit.jupiter.api.Test;

import ServiceLayer.OrderService;
import ServiceLayer.SupplierService;
import bussinessLayer.Order;

import  static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestClass {
	
	private static SupplierService supService = SupplierService.getInstance();
	private static OrderService oService = OrderService.getInstance();
	
	
	@BeforeAll
	public static void setup() {
		supService.loadFirstSuppliers();
		oService.loadFirstItems();
	}
	

    @Test
    public void CheckAddingItemsToCatalogItems(){
    }
    
    @Test
    public void createOrder(){
    	List<Order> orders = Data.Data.getOrders();
    	int ordersSize = orders.size();
    	oService.createAnOrder(1);
    	assertEquals(ordersSize+1, orders.size(), "Size of orders wrong");
    }
    @Test
    public void b(){
    }
    @Test
    public void c(){
    }
    @Test
    public void d(){
    }
    @Test
    public void e(){
    }
    @Test
    public void f(){
    }
    @Test
    public void g(){
    }
}