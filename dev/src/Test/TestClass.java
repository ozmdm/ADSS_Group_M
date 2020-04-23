package Test;

import Data.Data;
import bussinessLayer.*;
import org.junit.jupiter.api.Test;

import ServiceLayer.OrderService;
import ServiceLayer.SupplierService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

public class TestClass {

	private static SupplierService supService = SupplierService.getInstance();
	private static OrderService oService = OrderService.getInstance();


	@BeforeAll
	public static void setup() {
		supService.loadFirstSuppliers();
	}

	@Test
	public void createOrder() throws Exception {
		List<Order> orders = Data.getOrders();
		int ordersSize = orders.size();
		oService.createAnOrder(123456);
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
		supService.AddSupplier("d", 0, 1, "EOM30", true);
		int catalogSize=0;
		try {
			catalogSize = supService.getSupplierById(0).getContract().getCatalog().getItems().size();
			supService.addCatalogItemToCatalogInContract(0, 1, 10, 6);
			assertEquals(catalogSize + 1, supService.getSupplierById(0).getContract().getCatalog().getItems().size(), "Size of catalogItem wrong");
		}catch(Exception e) {
		}

	}

	@Test
	public void RemoveItemFromCatalog() {
		try {
			addItemToCatalog();
			int catalogSize = supService.getSupplierById(0).getContract().getCatalog().getItems().size();
			supService.deleteCatalogItemFromCatlogInContract(0, 10);
			assertEquals(catalogSize - 1, supService.getSupplierById(0).getContract().getCatalog().getItems().size(), "Size of catalogItem wrong");
		}catch(Exception e) {

		}
	}

	@Test
	public void IsExist() {
		try {
			supService.AddSupplier("d", 0, 1, "EOM30", true);
			assertEquals(supService.isExist(0), "Done");
			supService.removeSupplier(0);
			assertEquals(supService.isExist(0), "Done");
		} catch(Exception e) {

		}


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
	public void addItemToCart() {
		Cart cart = new Cart();
		try {
			int cartSize = cart.getItemsToDelivery().size();
			cart.addItemToCart(supService.getSupplierById(123456).getCatalogItem(10), 10, 10);
			assertEquals(cart.getItemsToDelivery().size() - 1, cartSize,"add item to cart wrong");
		} catch (Exception e) {}
	}

	@Test
	public void removeFromCart() {
		Cart cart = new Cart();
		try {
			cart.addItemToCart(supService.getSupplierById(123456).getCatalogItem(10), 10, 10);
			int cartSize = cart.getItemsToDelivery().size();
			cart.removeFromCart(10);
			assertEquals(cart.getItemsToDelivery().size() + 1, cartSize,"add item to cart wrong");
		} catch (Exception e) {}
	}

	@Test
	public void sendOrder() {
		int orderId = Integer.valueOf(oService.createAnOrder(123456));
		oService.addItemToCart(orderId, 10, 10);
		String initial = oService.getOrderStatus(orderId);
		assertEquals("OPEN", initial);
		oService.sendOrder(orderId);
		assertEquals("INPROGRESS", oService.getOrderStatus(orderId));
	}

	@Test
	public void endOrder() {
		int orderId = Integer.valueOf(oService.createAnOrder(123456));
		oService.addItemToCart(orderId, 10, 10);
		oService.sendOrder(orderId);
		assertEquals("INPROGRESS", oService.getOrderStatus(orderId));
		oService.endOrder(orderId);
		assertEquals("COMPLETE", oService.getOrderStatus(orderId));
	}
}