package Test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;


import Data.Data;
import bussinessLayer.*;

import ServiceLayer.OrderService;
import ServiceLayer.SupplierService;



public class TestClass {

	private static SupplierService supService = SupplierService.getInstance();
	private static OrderService oService = OrderService.getInstance();



	@Before
	public void setup() {
		supService.loadFirstSuppliers();
	}

	@Test
	public void createOrder() {
		setup();
		List<Order> orders = Data.getOrders();
		int ordersSize = orders.size();
		oService.createAnOrder(123456);
		assertEquals("create order wrong", ordersSize + 1, orders.size());
	}

	@Test
	public void creatSupplier() {
		setup();
		List<Supplier> suppliers = Data.getSuppliers();
		int suppliersSize = suppliers.size();
		supService.AddSupplier("d", 0, 1, "EOM30", true);
		assertEquals("Size of suppliers wrong",suppliersSize + 1, suppliers.size());
	}

	@Test
	public void addItemToCatalog() {
		setup();
		supService.AddSupplier("d", 0, 1, "EOM30", true);
		int catalogSize=0;
		try {
			catalogSize = supService.getSupplierById(0).getContract().getCatalog().getItems().size();
			supService.addCatalogItemToCatalogInContract(0, 1, 10, 6);
			assertEquals( "Size of catalogItem wrong", catalogSize + 1, supService.getSupplierById(0).getContract().getCatalog().getItems().size());
		}catch(Exception e) {
		}

	}

	@Test
	public void RemoveItemFromCatalog() {
		setup();
		try {
			addItemToCatalog();
			int catalogSize = supService.getSupplierById(0).getContract().getCatalog().getItems().size();
			supService.deleteCatalogItemFromCatlogInContract(0, 10);
			assertEquals( "Size of catalogItem wrong",catalogSize - 1, supService.getSupplierById(0).getContract().getCatalog().getItems().size());
		}catch(Exception e) {

		}
	}

	@Test
	public void IsExist() {
		setup();
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
		setup();
		List<Supplier> suppliers = Data.getSuppliers();
		int suppliersSize = suppliers.size();
		supService.AddSupplier("d", 0, 1, "EOM30", true);
		suppliersSize = suppliers.size();
		supService.removeSupplier(0);
		suppliers = Data.getSuppliers();
		assertEquals("Size of suppliers wrong", suppliersSize - 1, suppliers.size());
	}

	@Test
	public void addItemToCart() {
		setup();
		Cart cart = new Cart();
		try {
			int cartSize = cart.getItemsToDelivery().size();
			cart.addItemToCart(supService.getSupplierById(123456).getCatalogItem(10), 10, 10);
			assertEquals("add item to cart wrong", cart.getItemsToDelivery().size() - 1, cartSize);
		} catch (Exception e) {}
	}

	@Test
	public void removeFromCart() {
		setup();
		Cart cart = new Cart();
		try {
			cart.addItemToCart(supService.getSupplierById(123456).getCatalogItem(10), 10, 10);
			int cartSize = cart.getItemsToDelivery().size();
			cart.removeFromCart(10);
			assertEquals("add item to cart wrong", cart.getItemsToDelivery().size() + 1, cartSize);
		} catch (Exception e) {}
	}

	@Test
	public void sendOrder() {
		setup();
		int orderId = Integer.valueOf(oService.createAnOrder(123456));
		oService.addItemToCart(orderId, 10, 10);
		String initial = oService.getOrderStatus(orderId);
		assertEquals("OPEN", initial);
		oService.sendOrder(orderId);
		assertEquals("INPROGRESS", oService.getOrderStatus(orderId));
	}

	@Test
	public void endOrder() {
		setup();
		int orderId = Integer.valueOf(oService.createAnOrder(123456));
		oService.addItemToCart(orderId, 10, 10);
		oService.sendOrder(orderId);
		assertEquals("INPROGRESS", oService.getOrderStatus(orderId));
		oService.endOrder(orderId);
		assertEquals("COMPLETE", oService.getOrderStatus(orderId));
	}
}