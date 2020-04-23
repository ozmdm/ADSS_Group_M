package PL;

import BL.DeliveryPackage.DeliveryController;
import org.w3c.dom.ls.LSOutput;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

public class Menu {

    private static DeliveryController deliveryController;

    public static void main(String[] args) {
        deliveryController = DeliveryController.getInstance();
        Scanner in = new Scanner(System.in);

        try
        {
            System.out.println("welcome to the delivery system manager");
            System.out.println("please choose how to initialize the system");
            System.out.println("1 for automatic initialization and 2 for empty database");
            int choice = in.nextInt();
            if(choice == 1)
                init();
            while(true)
            {
                System.out.println("please enter the menu number you wish to enter");
                System.out.println("main menu:\n1) drivers menu\n2) trucks menu\n3) locations menu\n4) orders menu\n" +
                        "5) deliveries menu\n6) exit system");
                choice = in.nextInt();
                mainMenu(choice);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void mainMenu(int choice)
    {
        switch(choice)
        {
            case 1:
                driversMenu();
                break;
            case 2:
                trucksMenu();
                break;
            case 3:
                locationsMenu();
                break;
            case 4:
                ordersMenu();
                break;
            case 5:
                deliveriesMenu();
                break;
            case 6:
                System.exit(0);
                break;
        }
    }

    public static void driversMenu()
    {
        System.out.println("please enter the number of the function you wish to do");
        System.out.println("drivers menu:\n1) create new driver\n2) delete driver\n3) change license expiration date\n" +
                "4) change license type\n5) back to main menu");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        String id, name, licenseType, licenseExpDate;
        Date date;
        try
        {
            switch (choice)
            {
                case 1:
                    System.out.println("please enter driver details: id, name, type of license, license expiration date");
                    id = in.next();
                    name = in.next();
                    licenseType = in.next();
                    licenseExpDate = in.next();
                    date = new SimpleDateFormat("dd/MM/yyyy").parse(licenseExpDate);
                    deliveryController.createDriver(id, name, licenseType, date);
                    break;
                case 2:
                    System.out.println("please enter the driver id that you want to erase from the system");
                    id = in.next();
                    deliveryController.removeDriver(id);
                    break;
                case 3:
                    System.out.println("please enter the driver id and the new expiration date");
                    id = in.next();
                    licenseExpDate = in.next();
                    date = new SimpleDateFormat("dd/MM/yyyy").parse(licenseExpDate);
                    deliveryController.changeExpDate(id, date);
                    break;
                case 4:
                    System.out.println("please enter the driver id and the new license type");
                    id = in.next();
                    licenseType = in.next();
                    deliveryController.changeLicenseType(id, licenseType);
                    break;
                case 5:
                    break;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + "\nyou entered wrong details please try again");
        }
    }

    public static void trucksMenu()
    {
        System.out.println("please enter the number of the function you wish to do");
        System.out.println("trucks menu:\n1) create new truck\n2) delete truck\n3) back to main menu");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        String id, name;
        double netoWeight, totalWeight;
        try
        {
            switch (choice)
            {
                case 1:
                    System.out.println("please enter truck details: truck id, model, neto weight, total weight");
                    id = in.next();
                    name = in.next();
                    netoWeight = in.nextDouble();
                    totalWeight = in.nextDouble();
                    deliveryController.createTruck(id, name, netoWeight, totalWeight);
                    break;
                case 2:
                    System.out.println("please enter the truck id that you want to erase from the system");
                    id = in.next();
                    deliveryController.removeTruck(id);
                    break;
                case 3:
                    break;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + "\nyou entered wrong details please try again");
        }
    }

    public static void locationsMenu()
    {
        System.out.println("please enter the number of the function you wish to do");
        System.out.println("locations menu:\n1) create new location\n2) delete location\n3) change telephone number\n" +
                "4) change contact name\n5) back to main menu");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        String id, name, licenseType, licenseExpDate, s1, s2;
        try
        {
            switch (choice)
            {
                case 1:
                    System.out.println("please enter location details: location id, name, address, telephone number,\n" +
                            "contact name, shipping area");
                    id = in.next();
                    name = in.next();
                    licenseType = in.next();
                    licenseExpDate = in.next();
                    s1 = in.next();
                    s2 = in.next();
                    deliveryController.createLocation(id, name, licenseType, licenseExpDate, s1, s2);
                    break;
                case 2:
                    System.out.println("please enter the location id that you want to erase from the system");
                    id = in.next();
                    deliveryController.removeLocation(id);
                    break;
                case 3:
                    System.out.println("please enter location id and the new telephone number");
                    id = in.next();
                    s1 = in.next();
                    deliveryController.changetelNumber(id, s1);
                    break;
                case 4:
                    System.out.println("please enter location id and the new contact name");
                    id = in.next();
                    s1 = in.next();
                    deliveryController.changecontactName(id, s1);
                    break;
                case 5:
                    break;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + "\nyou entered wrong details please try again");
        }
    }

    public static void ordersMenu()
    {
        System.out.println("please enter the number of the function you wish to do");
        System.out.println("orders menu:\n1) create new order\n2) delete order\n3) add item to order\n" +
                "4) remove item from order\n5) change quantity of item in order\n6) change total weight of order\n" +
                "7) back to main menu");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        String id, name, s1, licenseExpDate;
        int quantity;
        double totalWeight;
        try
        {
            switch (choice)
            {
                case 1:
                    Map<String, Integer> items = new HashMap<>();
                    System.out.println("to create order details please first enter items - itemName, quantity\n" +
                            "to finish please enter x");
                    s1 = in.next();
                    while(s1.compareTo("x") != 0)
                    {
                        quantity = in.nextInt();
                        items.put(s1, quantity);
                        s1 = in.next();
                    }
                    System.out.println("please enter order details: id, supplier id, location id, total weight");
                    id = in.next();
                    name = in.next();
                    licenseExpDate = in.next();
                    totalWeight = in.nextDouble();
                    deliveryController.createOrder(id, items, name, licenseExpDate, totalWeight);
                    break;
                case 2:
                    System.out.println("please enter the order id that you want to erase from the system");
                    id = in.next();
                    deliveryController.removeOrder(id);
                    break;
                case 3:
                    System.out.println("please enter order id, item name, quantity");
                    id = in.next();
                    name = in.next();
                    quantity = in.nextInt();
                    deliveryController.addItem(id, name, quantity);
                    break;
                case 4:
                    System.out.println("please enter order id, item name");
                    id = in.next();
                    name = in.next();
                    deliveryController.removeItem(id, name);
                    break;
                case 5:
                    System.out.println("please enter order id, item name, quantity");
                    id = in.next();
                    name = in.next();
                    quantity = in.nextInt();
                    deliveryController.changeQuantity(id, name, quantity);
                    break;
                case 6:
                    System.out.println("please enter order id, total weight");
                    id = in.next();
                    totalWeight = in.nextDouble();
                    deliveryController.changeTotalWeight(id, totalWeight);
                    break;
                case 7:
                    break;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + "\nyou entered wrong details please try again");
        }
    }

    public static void deliveriesMenu()
    {
        System.out.println("please enter the number of the function you wish to do");
        System.out.println("deliveries menu:\n1) create new delivery\n2) delete delivery\n3) change delivery date\n" +
                "4) change delivery time\n5) change weight of delivery\n6) change truck for delivery\n" +
                "7) change driver for delivery\n8) add order and location\n9) remove order and location\n" +
                "10) change status\n11) back to main menu");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        String id, s1,name, licenseType, licenseExpDate, s2;
        Date date;
        double totalWeight;
        try
        {
            switch (choice)
            {
                case 1:
                    List<String> targetLocations = new ArrayList<>();
                    List<String> orders = new ArrayList<>();
                    System.out.println("to create delivery details please first enter target locations," +
                            "\nto finish please x");
                    s1 = in.next();
                    while(s1.compareTo("x") != 0)
                    {
                        targetLocations.add(s1);
                        s1 = in.next();
                    }
                    System.out.println("to create delivery details please first enter orders," +
                            "\nto finish please x");
                    s1 = in.next();
                    while(s1.compareTo("x") != 0)
                    {
                        orders.add(s1);
                        s1 = in.next();
                    }
                    System.out.println("please ender delivery details: id, delivery day, leaving time, driver id," +
                            "\nsource location, truck id");
                    id = in.next();
                    licenseExpDate = in.next();
                    date = new SimpleDateFormat("dd/MM/yyyy").parse(licenseExpDate);
                    s1 = in.next();
                    Time newTime1 = Time.valueOf(s1);
                    name = in.next();
                    licenseType = in.next();
                    s2 = in.next();
                    deliveryController.createDelivery(id, date, newTime1, name, licenseType, targetLocations, s2, orders);
                    break;
                case 2:
                    System.out.println("please enter the delivery id that you want to erase from the system");
                    id = in.next();
                    deliveryController.removeDelivery(id);
                    break;
                case 3:
                    System.out.println("please enter the delivery id and the new delivery day");
                    id = in.next();
                    licenseExpDate = in.next();
                    date = new SimpleDateFormat("dd/MM/yyyy").parse(licenseExpDate);
                    deliveryController.changeDeliveryDay(id, date);
                    break;
                case 4:
                    System.out.println("please enter the delivery id and the new delivery leaving time");
                    id = in.next();
                    s1 = in.next();
                    Time newTime = Time.valueOf(s1);
                    deliveryController.changeLeavingTime(id, newTime);
                    break;
                case 5:
                    System.out.println("please enter delivery id, new weight of delivery");
                    id = in.next();
                    totalWeight = in.nextDouble();
                    deliveryController.changeWeight(id, totalWeight);
                    break;
                case 6:
                    System.out.println("please enter delivery id, truck id");
                    id = in.next();
                    s1 = in.next();
                    deliveryController.changeTruckId(id, s1);
                    break;
                case 7:
                    System.out.println("please enter delivery id, driver id");
                    id = in.next();
                    s1 = in.next();
                    deliveryController.changeDriverId(id, s1);
                    break;
                case 8:
                    System.out.println("please enter delivery id, location id, order id");
                    id = in.next();
                    s1 = in.next();
                    s2 = in.next();
                    deliveryController.addOrderAndLocation(id, s1, s2);
                    break;
                case 9:
                    System.out.println("please enter delivery id, location id, order id");
                    id = in.next();
                    s1 = in.next();
                    s2 = in.next();
                    deliveryController.removeOrderAndLocation(id, s1, s2);
                    break;
                case 10:
                    System.out.println("please enter delivery id, new delivery status that could be" +
                            "\nInTransit or Delivered");
                    id = in.next();
                    s1 = in.next();
                    deliveryController.changeStatus(id, s1);
                    break;
                case 11:
                    break;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + "\nyou entered wrong details please try again");
        }
    }

    public static void init()
    {
        try
        {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/2030");
            deliveryController.createDriver("208938985", "omri", "A", date);
            deliveryController.createDriver("312164668", "noa", "B", date);
            deliveryController.createDriver("123456789", "lidor", "C", date);
            deliveryController.createLocation("1", "superli", "lachish 151 shoham", "0543160553", "yossi", "center");
            deliveryController.createLocation("2", "maxstock", "shoham 26 haifa", "0504616909", "ben", "north");
            deliveryController.createLocation("3", "shufersal", "azrieli tel aviv", "0543160550", "ronit", "center");
            deliveryController.createLocation("4", "tara", "bialik 32 ramat gan", "0581234567", "moshe", "center");
            deliveryController.createLocation("5", "tnuva", "rabin 12 beer sheva", "0538523644", "assaf", "south");
            deliveryController.createLocation("6", "osem", "shimshon 24 krayot", "0528549847", "shoshana", "north");
            deliveryController.createTruck("2360154", "volvo", 1000.0, 4500.0);
            deliveryController.createTruck("30122623", "chevrolet", 5000.0, 9000.5);
            deliveryController.createTruck("11122333", "honda", 10000.0, 15000.0);
            Map<String, Integer> items1 = new HashMap<String, Integer>() {
                {
                    put("milk", 20);
                    put("pasta", 50);
                    put("chocolate", 10);
                    put("cola", 10);
                }
            };
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
            deliveryController.createOrder("12", items1, "487", "1", 1000.0);
            deliveryController.createOrder("34", items2, "159", "2", 3500.0);
            deliveryController.createOrder("56", items3, "263", "3", 2500.0);
            deliveryController.createOrder("78", items4, "546", "1", 2000.0);
            deliveryController.createOrder("98", items5, "943", "3", 2000.0);
            Date newDate1 = new GregorianCalendar(2020, Calendar.MAY, 11).getTime();
            Date newDate2 = new GregorianCalendar(2020, Calendar.DECEMBER, 31).getTime();
            Date newDate3 = new GregorianCalendar(2020, Calendar.JULY, 7).getTime();
            Time newTime1 = Time.valueOf("12:30:00");
            Time newTime2 = Time.valueOf("13:00:00");
            Time newTime3 = Time.valueOf("11:25:30");
            List<String> centerLocations = new ArrayList<>() {
                {
                    add("1");
                    add("3");
                }
            };
            List<String> northLocations = new ArrayList<>() {
                {
                    add("2");
                }
            };
            List<String> orders1 = new ArrayList<>() {
                {
                    add("12");
                    add("56");
                }
            };
            List<String> orders2 = new ArrayList<>() {
                {
                    add("34");
                }
            };
            List<String> orders3 = new ArrayList<>() {
                {
                    add("78");
                    add("98");
                }
            };
            deliveryController.createDelivery("101", newDate1, newTime1, "208938985", "4", centerLocations, "2360154", orders1);
            deliveryController.createDelivery("102", newDate2, newTime2, "312164668", "5", northLocations, "30122623", orders2);
            deliveryController.createDelivery("103", newDate3, newTime3, "123456789", "6", centerLocations, "11122333", orders3);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}