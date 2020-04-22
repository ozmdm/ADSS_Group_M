package Tests;

import BL.DeliveryPackage.Delivery;
import BL.DeliveryPackage.DeliveryController;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;;
import java.util.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DeliveryTest {

    private DeliveryController deliveryController = DeliveryController.getInstance();

    @BeforeEach
    public void setUp() throws Exception {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/2030");
        Time newTime = Time.valueOf("12:30:00");
        Map<String, Integer> items1 = new HashMap<String, Integer>() {
            {
                put("milk", 20);
                put("pasta", 50);
                put("chocolate", 10);
                put("cola", 10);
            }
        };
        Map<String, Integer> items3 = new HashMap<String, Integer>() {
            {
                put("eggs", 10);
                put("cola zero", 15);
                put("beer", 23);
                put("candy", 17);
            }
        };
        List<String> centerLocations = new ArrayList<>() {
            {
                add("1");
                add("3");
            }
        };
        List<String> orders1 = new ArrayList<>() {
            {
                add("12");
                add("56");
            }
        };
        deliveryController.createDriver("208938985", "omri", "A", date);
        deliveryController.createLocation("1", "superli", "lachish 151 shoham", "0543160553", "yossi", "center");
        deliveryController.createLocation("3", "shufersal", "azrieli tel aviv", "0543160550", "ronit", "center");
        deliveryController.createLocation("4", "tara", "bialik 32 ramat gan", "0581234567", "moshe", "center");
        deliveryController.createTruck("2360154", "volvo", 1000.0, 4500.0);
        deliveryController.createOrder("12", items1, "487", "1", 1000.0);
        deliveryController.createOrder("56", items3, "263", "3", 2500.0);
        deliveryController.createDelivery("101", date, newTime, "208938985", "4", centerLocations, "2360154", orders1);
    }

    public void updateForNextTest() throws Exception
    {
        if(deliveryController.getDrivers().containsKey("208938985"))
            deliveryController.removeDriver("208938985");
        if(deliveryController.getLocations().containsKey("1"))
            deliveryController.removeLocation("1");
        if(deliveryController.getLocations().containsKey("3"))
            deliveryController.removeLocation("3");
        if(deliveryController.getLocations().containsKey("4"))
            deliveryController.removeLocation("4");
        if(deliveryController.getTrucks().containsKey("2360154"))
            deliveryController.removeTruck("2360154");
        if(deliveryController.getOrders().containsKey("12"))
            deliveryController.removeOrder("12");
        if(deliveryController.getOrders().containsKey("56"))
            deliveryController.removeOrder("56");
        if(deliveryController.getDeliveries().containsKey("101"))
            deliveryController.removeDelivery("101");
    }

    @Test
    public void testCreateObjects() throws Exception {
        this.setUp();
        assertTrue(deliveryController.getDriver("208938985").getName().equals("omri"));
        assertTrue(deliveryController.getDriver("208938985").getLicenseType().equals("A"));
        assertTrue(deliveryController.getLocation("1").getName().equals("superli"));
        assertTrue(deliveryController.getLocation("1").getAddress().equals("lachish 151 shoham"));
        assertTrue(deliveryController.getLocation("1").getContactName().equals("yossi"));
        assertTrue(deliveryController.getLocation("1").getShippingArea().equals("center"));
        assertTrue(deliveryController.getTruck("2360154").getModel().equals("volvo"));
        assertEquals(1000.0, deliveryController.getTruck("2360154").getNetoWeight(), 0.0);
        assertEquals(4500.0, deliveryController.getTruck("2360154").getTotalWeight(), 0.0);
        assertTrue(deliveryController.getOrders().get("12").getLocationId().equals("1"));
        int amount = deliveryController.getOrders().get("12").getItems().get("milk");
        assertEquals(20, amount, 0);
        assertEquals(1000.0, deliveryController.getOrders().get("12").getTotalWeight(), 0.0);
        assertTrue(deliveryController.getDelivery("101").getDriverId().equals("208938985"));
        assertTrue(deliveryController.getDelivery("101").getTruckId().equals("2360154"));
        assertTrue(deliveryController.getDelivery("101").getSrcLocation().equals("4"));
        assertTrue(deliveryController.getDelivery("101").getTargetLocation().get(0).equals("1"));
        assertTrue(deliveryController.getDelivery("101").getOrders().get(0).equals("12"));
        this.updateForNextTest();
    }


    @Test
    public void testRemoveObjectcs() throws Exception {
        this.setUp();
        deliveryController.removeDriver("208938985");
        deliveryController.removeTruck("2360154");
        deliveryController.removeLocation("1");
        deliveryController.removeOrder("12");
        deliveryController.removeDelivery("101");
        assertTrue(!deliveryController.getDrivers().containsKey("208938985"));
        assertTrue(!deliveryController.getTrucks().containsKey("2360154"));
        assertTrue(!deliveryController.getLocations().containsKey("1"));
        assertTrue(!deliveryController.getOrders().containsKey("12"));
        assertTrue(!deliveryController.getDeliveries().containsKey("101"));
        this.updateForNextTest();
    }

    @Test
    public void testOrderChanges() throws Exception {
        this.setUp();
        deliveryController.addItem("12", "bamba", 5);
        int amount = deliveryController.getOrders().get("12").getItems().get("bamba");
        assertEquals(5, amount, 0);
        deliveryController.changeQuantity("12", "bamba", 10);
        amount = deliveryController.getOrders().get("12").getItems().get("bamba");
        assertEquals(10, amount, 0);
        deliveryController.removeItem("12", "bamba");
        assertTrue(!deliveryController.getOrders().get("12").getItems().values().contains("bamba"));
        this.updateForNextTest();
    }

    @Test
    public void testTruckDetails() throws Exception {
        this.setUp();
        deliveryController.getTruck("2360154").setUsed();
        assertTrue(deliveryController.getTruck("2360154").isUsed());
        deliveryController.getTruck("2360154").setNotUsed();
        assertTrue(!deliveryController.getTruck("2360154").isUsed());
        this.updateForNextTest();
    }

    @Test
    public void testDriverDetails() throws Exception {
        this.setUp();
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2030");
        deliveryController.getDriver("208938985").setDriving();
        assertTrue(deliveryController.getDriver("208938985").isDriving());
        deliveryController.getDriver("208938985").setNotDriving();
        assertTrue(!deliveryController.getDriver("208938985").isDriving());
        deliveryController.getDriver("208938985").setLicenseType("B");
        assertTrue(deliveryController.getDriver("208938985").getLicenseType().equals("B"));
        deliveryController.getDriver("208938985").setExpLicenseDate(date);
        assertTrue(deliveryController.getDriver("208938985").getExpLicenseDate().equals(date));
        this.updateForNextTest();
    }

    @Test
    public void testLocationDetails() throws Exception {
        this.setUp();
        deliveryController.getLocation("1").setTelNumber("0521234567");
        assertTrue(deliveryController.getLocation("1").getTelNumber().equals("0521234567"));
        deliveryController.getLocation("1").setContactName("roi");
        assertTrue(deliveryController.getLocation("1").getContactName().equals("roi"));
        this.updateForNextTest();
    }

    @Test
    public void testDeliveryDetails() throws Exception {
        this.setUp();
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2030");
        Time newTime = Time.valueOf("14:30:00");
        deliveryController.createDriver("312164668", "noa", "B", date);
        deliveryController.createTruck("8523123", "chevrolet", 5000.0, 9000.5);
        deliveryController.getDelivery("101").setDeliveryDay(date);
        assertTrue(deliveryController.getDelivery("101").getDeliveryDay().equals(date));
        deliveryController.getDelivery("101").setLeavingTime(newTime);
        assertTrue(deliveryController.getDelivery("101").getLeavingTime().equals(newTime));
        deliveryController.getDelivery("101").setWeight(100);
        assertEquals(100, deliveryController.getDelivery("101").getWeight(), 0);
        deliveryController.getDelivery("101").setTruckId("8523123");
        assertTrue(deliveryController.getDelivery("101").getTruckId().equals("8523123"));
        deliveryController.getDelivery("101").setDriverId("312164668");
        assertTrue(deliveryController.getDelivery("101").getDriverId().equals("312164668"));
        this.updateForNextTest();
    }

    @Test
    public void testFullWorkFlowOrder() throws Exception {
        this.setUp();
        Map<String, Integer> items2 = new HashMap<String, Integer>() {
            {
                put("milk", 25);
                put("rice", 30);
                put("cheese", 40);
                put("eggs", 45);
            }
        };
        deliveryController.createOrder("34", items2, "159", "2", 3500.0);
        deliveryController.addItem("34", "bamba", 5);
        deliveryController.addItem("34", "bisli", 10);
        int amount = deliveryController.getOrders().get("34").getItems().get("bamba");
        assertEquals(amount, 5);
        deliveryController.changeQuantity("34", "bamba", 20);
        amount = deliveryController.getOrders().get("34").getItems().get("bamba");
        assertEquals(amount, 20);
        deliveryController.removeItem("34", "bamba");
        assertTrue(deliveryController.getOrders().containsKey("34"));
        deliveryController.removeItem("34", "bisli");
        assertTrue(!deliveryController.getOrders().get("34").getItems().values().contains("bamba"));
        assertTrue(!deliveryController.getOrders().get("34").getItems().values().contains("bisli"));
        this.updateForNextTest();
    }

    @Test
    public void testFullWorkFlowDelivery() throws Exception {
        this.setUp();
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2030");
        Time newTime = Time.valueOf("14:30:00");
        deliveryController.createDriver("123456789", "lidor", "C", date);
        deliveryController.createLocation("2", "maxstock", "shoham 26 haifa", "0504616909", "ben", "north");
        deliveryController.createLocation("5", "tnuva", "rabin 12 beer sheva", "0538523644", "assaf", "north");
        deliveryController.createLocation("6", "osem", "shimshon 24 krayot", "0528549847", "shoshana", "south");
        deliveryController.createTruck("30122623", "chevrolet", 5000.0, 9000.5);
        Map<String, Integer> items4 = new HashMap<String, Integer>() {
            {
                put("eggs", 10);
                put("milk", 15);
                put("tomato", 23);
                put("cucumber", 17);
            }
        };
        Map<String, Integer> items5 = new HashMap<String, Integer>() {
            {
                put("ice cream", 20);
                put("toilet paper", 15);
                put("cucumber", 50);
                put("fish", 10);
            }
        };
        deliveryController.createOrder("78", items4, "546", "2", 2000.0);
        deliveryController.createOrder("98", items5, "943", "5", 2000.0);
        List<String> orders3 = new ArrayList<>() {
            {
                add("78");
                add("98");
            }
        };
        List<String> locations = new ArrayList<>() {
            {
                add("2");
                add("5");
            }
        };
        deliveryController.createDelivery("103", date, newTime, "123456789", "6", locations, "30122623", orders3);
        deliveryController.setDriverToDrive("123456789");
        assertTrue(deliveryController.getDriver("123456789").isDriving());
        deliveryController.setTruckUsed("30122623");
        assertTrue(deliveryController.getTruck("30122623").isUsed());
        deliveryController.changeStatus("103", "InTransit");
        assertTrue(deliveryController.getDelivery("103").getStatus().equals(Delivery.Status.InTransit));
        deliveryController.changeStatus("103", "Delivered");
        assertTrue(deliveryController.getDelivery("103").getStatus().equals(Delivery.Status.Delivered));
        deliveryController.setDriverNotToDrive("123456789");
        assertTrue(!deliveryController.getDriver("123456789").isDriving());
        deliveryController.setTruckNotUsed("30122623");
        assertTrue(!deliveryController.getTruck("30122623").isUsed());
        this.updateForNextTest();
    }

    @Test
    public void testFullWorkFlowDelivery2() throws Exception {
        this.setUp();
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2030");
        Time newTime = Time.valueOf("14:30:00");
        deliveryController.createDriver("852963741", "lidor", "C", date);
        deliveryController.createLocation("7", "maxstock", "shoham 26 haifa", "0504616909", "ben", "north");
        deliveryController.createLocation("8", "tnuva", "rabin 12 beer sheva", "0538523644", "assaf", "north");
        deliveryController.createLocation("9", "osem", "shimshon 24 krayot", "0528549847", "shoshana", "south");
        deliveryController.createTruck("7896541", "chevrolet", 5000.0, 9000.5);
        Map<String, Integer> items2 = new HashMap<String, Integer>() {
            {
                put("milk", 25);
                put("rice", 30);
                put("cheese", 40);
                put("eggs", 45);
            }
        };
        Map<String, Integer> items3 = new HashMap<String, Integer>() {
            {
                put("eggs", 10);
                put("cola zero", 15);
                put("beer", 23);
                put("candy", 17);
            }
        };
        deliveryController.createOrder("50", items2, "546", "2", 2000.0);
        deliveryController.createOrder("60", items3, "943", "5", 2000.0);
        List<String> orders3 = new ArrayList<>() {
            {
                add("50");
                add("60");
            }
        };
        List<String> locations = new ArrayList<>() {
            {
                add("7");
                add("8");
            }
        };
        deliveryController.createDelivery("105", date, newTime, "852963741", "9", locations, "7896541", orders3);
        deliveryController.setDriverToDrive("852963741");
        assertTrue(deliveryController.getDriver("852963741").isDriving());
        deliveryController.setTruckUsed("7896541");
        assertTrue(deliveryController.getTruck("7896541").isUsed());
        deliveryController.changeStatus("105", "InTransit");
        assertTrue(deliveryController.getDelivery("105").getStatus().equals(Delivery.Status.InTransit));
        deliveryController.changeStatus("105", "Delivered");
        assertTrue(deliveryController.getDelivery("105").getStatus().equals(Delivery.Status.Delivered));
        deliveryController.setDriverNotToDrive("852963741");
        assertTrue(!deliveryController.getDriver("852963741").isDriving());
        deliveryController.setTruckNotUsed("7896541");
        assertTrue(!deliveryController.getTruck("7896541").isUsed());
        deliveryController.createDriver("741852963", "lidor", "C", date);
        deliveryController.createTruck("846315", "volvo", 1000.0, 10800.0);
        Assertions.assertThrows(java.lang.Exception.class, () -> {
            deliveryController.createDelivery("108", date, newTime, "208938985", "9", locations, "846315", orders3);
        });
        Assertions.assertThrows(java.lang.Exception.class, () -> {
            deliveryController.createDelivery("107", date, newTime, "741852963", "9", locations, "7896541", orders3);
        });
        this.updateForNextTest();
    }
}
