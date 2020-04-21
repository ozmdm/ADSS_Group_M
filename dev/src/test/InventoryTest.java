package test;

import InventoryPackage.Inventory;
import InventoryPackage.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
public class InventoryTest {

    private Inventory inventory;
    private Item item1;
    private Item item2;

    @BeforeEach
    public void setUp(){
        inventory = new Inventory();

    }


    @Test
    void testAddItem() {
        this.inventory.addItem("milk",1,2,3,4,"branch",
                4,4,"cat","sub","sub2", "tnuva");
        assertTrue(inventory.getItems().get(1).getDescription().equals("milk"));
        assertTrue(inventory.getItems().get(1).getFeatures().getManufacturer().equals("tnuva"));
    }

    @Test
    void testCounterId() {
        this.inventory.addItem("milk",1,2,3,4,"branch",
                4,4,"cat","sub","sub2", "tnuva");
        assertTrue(inventory.getIdCounter() == 1);
    }

    @Test
    void testEditMinimumQuantity() throws Exception {
        this.inventory.addItem("milk",1,2,3,4,"branch",
                4,4,"cat","sub","sub2", "tnuva");
        assertTrue(inventory.getItems().get(1).getMinimumQuantity()==4);
        inventory.editMinimumQuantity(1,10);
        assertTrue(inventory.getItems().get(1).getMinimumQuantity()==10);
    }


    @Test
    void testEditShelfQuantity  () throws Exception {
        this.inventory.addItem("milk",1,2,3,4,"branch",
                4,4,"cat","sub","sub2", "tnuva");
        assertTrue(inventory.getItems().get(1).getQuantityShelf()==1);
        inventory.editShelfQuantity(1,20);
        assertTrue(inventory.getItems().get(1).getQuantityShelf()==21);
    }

    @Test
    void testEditStockQuantity  () throws Exception {
        this.inventory.addItem("milk",1,15,3,4,"branch",
                4,4,"cat","sub","sub2", "tnuva");
        assertTrue(inventory.getItems().get(1).getQuantityStock()==15);
        inventory.editStockQuantity(1,-5);
        assertTrue(inventory.getItems().get(1).getQuantityStock()==10);
    }

    @Test
    void testCancelCard  () throws Exception {
        this.inventory.addItem("milk",1,15,3,4,"branch",
                4,4,"cat","sub","sub2", "tnuva");
        assertTrue(inventory.getItems().get(1).getQuantityShelf()==1);
        inventory.cancelCard(1,6);
        assertTrue(inventory.getItems().get(1).getQuantityShelf()==7);
    }

    @Test
    void testUpdateItemCostPrice  () throws Exception {
        this.inventory.addItem("milk",1,15,3,4,"branch",
                4,4,"cat","sub","sub2", "tnuva");
        assertTrue(inventory.getItems().get(1).getCostPrice()==3);
        inventory.updateItemCostPrice(1,6);
        assertTrue(inventory.getItems().get(1).getCostPrice()==6);
        assertTrue(inventory.getItems().get(1).getOldCostPrices().get(0)==3);
    }

    @Test
    void testUpdateItemSalePrice  () throws Exception {
        this.inventory.addItem("milk",1,15,3,4,"branch",
                4,4,"cat","sub","sub2", "tnuva");
        assertTrue(inventory.getItems().get(1).getSalePrice()==4);
        inventory.updateItemSalePrice(1,6);
        assertTrue(inventory.getItems().get(1).getSalePrice()==6);
        assertTrue(inventory.getItems().get(1).getOldSalePrices().get(0)==4);
    }


    @Test
    void testUpdateDamagedItem  () throws Exception {
        this.inventory.addItem("milk",10,15,3,4,"branch",
                4,4,"cat","sub","sub2", "tnuva");
        inventory.updateDamagedItem(1, 6);

        assertTrue(inventory.getDamagedController().getQuantityById().get(1) == 6);
    }

    @Test
    void testToOrderReport () throws Exception {
        this.inventory.addItem("milk",10,15,3,4,"branch",
                30,4,"cat","sub","sub2", "tnuva");
        Map<Integer,Integer> warnings = inventory.generateWarningReport();
        assertTrue(warnings.get(1) == 25);
    }

}