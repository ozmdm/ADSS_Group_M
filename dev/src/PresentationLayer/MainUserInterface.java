package PresentationLayer;


import java.util.Scanner;

import Data.Data;
import ServiceLayer.*;

public class MainUserInterface {


    private OrderService oService;
    private SupplierService supService;
    private Scanner sc = new Scanner(System.in);

    public MainUserInterface() {
        this.supService = SupplierService.getInstance();
        this.oService = OrderService.getInstance();
    }

    public void start() {
        loadProgramDefault();
        int input = 0;
        do {
            printMenu();
            input = Integer.valueOf(getUserInput());
            switch (input) {
                case 1:
                    makeAnOrder(); //ORDER MENU
                    break;
                case 2:
                    printOrdersFromSupplier(); // PRINTS ALL ORDERS FROM SUPPLIER
                    break;
                case 3:
                    endOrder(); //CHANGE ORDER'S STATUS TO INPROGRESS
                    break;
                case 4:
                    getOrderDetails();//GET ORDER DETAILS OF A specific order
                    break;
                case 5:
                    creatSupplierAndContract();//CREAT A NEW SUPPLIER AND ADD IT TO SYSTEM
                    break;
                case 6:
                    deleteSupplier();//DELETE SUPPLIER FROM THE SYSTEM
                    break;
                case 7:
                    updateSupplier();//UPDATE FIELDS OF SUPPLIER
                    break;
                case 8:
                    deleteContactFromSupplier(); // DELETE CONTACT LIST FROM SPECIFIC SUPPLIER
                    break;
                case 9:
                    updateContactForSupplier();  // UPDATE CONTACT INFO FROM SPECIFIC SUPPLIER
                    break;
                case 10:
                    addItemToSupplierCatalog();  // ADD NEW ITEM TO CATALOG FOR SPECIFIC SUPPLIER
                    break;
                case 11:
                    deleteItemFromCatalog();// DELETE ITEM  FROM CATALOG FOR SPECIFIC SUPPLIER
                    break;
                case 12:
                    getSuppliersInfo(); //PRINT THE SUPPLIERS INFORMATION (NAME,ID,BANK-ACCOUNT)
                    break;
                case 13:
                    Quit(); //QUIT
                    break;
            }
            System.out.println("\n");
        } while (input != 12);
    }

    private void getSuppliersInfo() {
        int input;
        supService.getSuppliersInfo();
        System.out.println("Do you want to see the following details for a particular Supplier?");
        System.out.println("1)present the catalogItem for Supplier\n2)present the contact list for Supplier\n3)present payment option for Supplier\n4)present what days Supplier do delivery\n you can always can go back to main menu with press 'b'");
        do {
           String userInput = getUserInput();
            if (userInput.equals("b")) return;
            input = Integer.valueOf(userInput);
            switch (input) {
                case 1:
                    System.out.println("Pleas enter Supplier id\n");
                    userInput = getUserInput();
                    if (userInput.equals("b")) return;
                   int supplierid  = Integer.valueOf(userInput);
                    System.out.println(supService.getCatalogPrinted(supplierid));
                   break;
                   case 2:
                    System.out.println("Pleas enter Supplier id\n");
                       userInput = getUserInput();
                       if (userInput.equals("b")) return;
                        supplierid  = Integer.valueOf(userInput);
                       System.out.println(supService.getSupplierById(supplierid).getCatalogItemPrinted()); // REMOVES AN ITEM FROM CART
                    break;
                case 3:
                    System.out.println("Pleas enter Supplier id\n");
                    userInput = getUserInput();
                    if (userInput.equals("b")) return;
                    supplierid  = Integer.valueOf(userInput);
                    System.out.println(supService.getSupplierById(supplierid).getBilingToPrint());
                    break;
                case 4:
                    System.out.println("Pleas enter Supplier id\n");
                    userInput = getUserInput();
                    if (userInput.equals("b")) return;
                    supplierid  = Integer.valueOf(userInput);
                    System.out.println(supService.getSupplierById(supplierid).getContract().getConstDayDelivierToPrinted());
                    break;
            }
        } while (input != 5);

    }

    private  void  deleteItemFromCatalog(){
        int supplierId = 0;
        int catalogItemId = 0;
        System.out.println("Pleas enter supplierId to delete from this supplier some item");
        String string = getUserInput();
        if (string.equals("b")) return;
        supplierId = Integer.valueOf(string);
        System.out.println("Pleas enter CatalogItemId For the item you want to remove");
         string = getUserInput();
        if (string.equals("b")) return;
        catalogItemId = Integer.valueOf(string);
        supService.deleteCatalogItemFromCatlogInContract(supplierId,catalogItemId);
    }
    private void addItemToSupplierCatalog() {
        int supplierId = 0;
        int ItemId = 0;
        int catalogItemId = 0;
        double price = 0;
        System.out.println("Pleas enter supplierId to add for this supplier some item");
        String string = getUserInput();
        if (string.equals("b")) return;
        supplierId = Integer.valueOf(string);
        System.out.println("Pleas enter ItemId from the list of items");
        System.out.println(Data.toStringItemsList());
         string = getUserInput();
        if (string.equals("b")) return;
        ItemId = Integer.valueOf(string);
        System.out.println("Pleas enter CatalogItemId For the item you choose");
         string = getUserInput();
        if (string.equals("b")) return;
        catalogItemId = Integer.valueOf(string);
        System.out.println("Pleas enter price for CatalogItem you choose");
         string = getUserInput();
        if (string.equals("b")) return;
        price = Double.valueOf(string);
        supService.addCatalogItemToCatalogInContract(supplierId, ItemId, catalogItemId, price);

        System.out.println("Enter item amount price agreement as follow: min:max:price if entered max = -1 means done");
        int max = 0;
        int min = 0;
        double priceafterDisc = 0;
        do {
             string = getUserInput();
            if (string.equals("b")) return;
            String split[] = string.split(":");
            min = Integer.valueOf(split[0]);
            max = Integer.valueOf(split[1]);
            priceafterDisc = Double.valueOf(split[2]);
            supService.addToMap(supplierId, catalogItemId, min, max, priceafterDisc);
        }
        while (max != -1);
    }

    private void updateContactForSupplier() {
        int supplierId = 0;
        int contactId = 0;
        System.out.println("Pleas enter supplierId to get all contact of the supplier");
       String string = getUserInput();
        if (string.equals("b")) return;
        supplierId = Integer.valueOf(string);
        System.out.println("Pleas enter contactId you would like to change from list Of contact");
        System.out.println(supService.getSupplierById(supplierId).contactListPrinted());
        string = getUserInput();
        if (string.equals("b")) return;
        contactId = Integer.valueOf(string);
        System.out.println("Pleas enter detailes as follow: firstName:lastName:phoneNumber:address if change is not needed for one put \" \"");
        string = getUserInput();
        if (string.equals("b")) return;
        String[] update = string.split(":");
        supService.updateContact(supplierId, update, contactId);

    }

    private void deleteContactFromSupplier() {
        int supplierId = 0;
        int contactId = 0;
        System.out.println("Pleas enter supplierId to get his contact of the supplier");
        String s = getUserInput();
        if (s.equals("b")) return;
        supplierId = Integer.valueOf(s);
        System.out.println("Pleas enter contactId you would like to delete from list Of contact");
        System.out.println(supService.getSupplierById(supplierId).contactListPrinted());
        s = getUserInput();
        if (s.equals("b")) return;
        contactId = Integer.valueOf(s);
        supService.deleteContact(supplierId, contactId);
    }

    private void updateSupplier() {
        int input = 0;
        int supplierid;
        System.out.println("Pleas enter supplierId To Update");
        String s = getUserInput();
        if (s.equals("b")) return;
        supplierid = Integer.valueOf(s);
        System.out.println("1) update Supplier name \n2) update bank Account Number\n3) update bilingOption \n4) update if supplier is deliver\n5)exit");
        do {
             s = getUserInput();
            if (s.equals("b")) return;
            input = Integer.valueOf(s);
            switch (input) {
                case 1:
                    System.out.println("Pleas enter new Supplier name\n");
                    s = getUserInput();
                    if (s.equals("b")) return;
                    String name = s;
                    supService.updateSupplierName(supplierid, name); //ADD ITEM TO CART
                    break;
                case 2:
                    System.out.println("Pleas enter new Supplier bank Account\n");
                    s = getUserInput();
                    if (s.equals("b")) return;
                    int bankAccount = Integer.valueOf(s);
                    supService.updateSupplierBankAccount(supplierid, bankAccount); // REMOVES AN ITEM FROM CART
                    break;
                case 3:
                    System.out.println("Pleas enter one of Supplier biling option -> {eom30 / eom 60 / cash / bankTransfer / Check}\n");
                    s = getUserInput();
                    if (s.equals("b")) return;
                    String bilingOption = s;
                    supService.updateBillingOptions(supplierid, bilingOption); //CONFIRM ORDER(SEND ORDER())
                    break;
                case 4:
                    System.out.println("Pleas enter if supplier Deliver or not -> (y/n)\n");
                    s = getUserInput();
                    if (s.equals("b")) return;
                    String IsDelivery = s;
                    boolean isDeliver;
                    if (IsDelivery.equals('y')) {
                        isDeliver = true;
                    } else {
                        isDeliver = false;
                    }
                    supService.updateContractIsDeliver(supplierid, isDeliver);
                    break;
            }
        } while (input != 5);
    }

    private void deleteSupplier() {
        System.out.println("Enter supplierId To Remove:\n");
        String s = getUserInput();
        if (s.equals("b")) return;
        int SupplierId = Integer.valueOf(s);
        supService.removeSupplier(SupplierId);
    }

    private void creatSupplierAndContract() {
        System.out.println("Enter supplier ID:\n");
        String s = getUserInput();
        if (s.equals("b")) return;
        int supplierId = Integer.valueOf(s);
        System.out.println("Enter supplier Name:\n");
         s = getUserInput();
        if (s.equals("b")) return;
        String SupplierName = s;
        System.out.println("Enter supplier BankAccount:\n");
         s = getUserInput();
        if (s.equals("b")) return;
        int bankAcount = Integer.valueOf(s);
        System.out.println("Enter biling Options -> {eom30 / eom 60 / cash / bankTransfer / Check}:\n");
        s = getUserInput();
        if (s.equals("b")) return;
        String bilingOptions = s;
        System.out.println("the supplier is deliver if y/n ? \n");
         s = getUserInput();
        if (s.equals("b")) return;
        String IsDelivery = s;
        boolean isDeliver;
        if (IsDelivery.equals('y')) {
            isDeliver = true;
        } else {
            isDeliver = false;
        }
        supService.AddSupplier(SupplierName, supplierId, bankAcount, bilingOptions, isDeliver);
        do {
            System.out.println("add contact -> {firstName:lastName:phoneNumber:address},  to add contact pleas pres enter to finish pres 0 and then enter");
           // input = Integer.valueOf(getUserInput());
             s = getUserInput();
            if (s.equals("b") || s.equals("0")) return;
            String[] contact = s.split(":");
            supService.addContact(supplierId, contact[0], contact[1], contact[2], contact[3]);
        }
        while (true);


    }

    private void Quit() {
        sc.close();
        System.out.println("BYE!");
    }

    private void getOrderDetails() { // GET ORDER DETAILS OF ORDER NO.@orderId
        System.out.println("Enter order ID:");
        String s = getUserInput();
        if (s.equals("b")) return;
        int orderId = Integer.valueOf(s);
        oService.getOrderDetails(orderId);
    }

    private void endOrder() { // CHANGES ORDER STATUS TO COMPLETE
        System.out.println("Enter order ID:");
        String s = getUserInput();
        if (s.equals("b")) return;
        int orderId = Integer.valueOf(s);
        oService.endOrder(orderId);
    }

    private void printOrdersFromSupplier() { // PRINTS ALL ORDERS FROM SUPPLIER
        System.out.println("Enter Supplier ID:");
        String s = getUserInput();
        if (s.equals("b")) return;
        int supplierId = Integer.valueOf(s);
        System.out.println(oService.printOrdersFromSupplier(supplierId));
    }

    private void makeAnOrder() { // ORDER MENU
        int input = 0;
        int supplierId = 0;
        System.out.println("Enter supplier ID:");
        String s = getUserInput();
        if (s.equals("b")) return;
        supplierId = Integer.valueOf(s);
        int orderId = oService.createAnOrder(supplierId);
        System.out.println("1) Add item\n2) Remove item\n3) Confirm order");

        do {
             s = getUserInput();
            if (s.equals("b")) return;
            input = Integer.valueOf(s);
            switch (input) {
                case 1:
                    addItemToCart(orderId); //ADD ITEM TO CART
                    break;
                case 2:
                    removeItemFromCart(orderId); // REMOVES AN ITEM FROM CART
                    break;
                case 3:
                    oService.sendOrder(orderId); //CONFIRM ORDER(SEND ORDER())
                    break;
            }
        } while (input != 3);
    }

    private void removeItemFromCart(int orderId) { // REMOVES AN ITEM FROM CART
        System.out.println("Enter catalog item ID: itemId:Amount");
        String s = getUserInput();
        if (s.equals("b")) return;
        String catalogItemId = s;
        oService.removeFromCart(orderId, Integer.valueOf(catalogItemId));
    }

    private void addItemToCart(int orderId) { //ADD ITEM TO CART
        System.out.println("Enter as Follow: CatalogItemId:Amount");
        String s = getUserInput();
        if (s.equals("b")) return;
        String split[] =s.split(":");
        oService.addItemToCart(orderId, Integer.valueOf(split[0]), Integer.valueOf(split[1]));
    }

    public void loadProgramDefault() { //LOADING THE PROGRAM WITH BASIC OBJECTS OR CLEAN START
        System.out.println("1) Load with objects\n2) Clean start");
        int input = Integer.valueOf(getUserInput());

        if(input==1) loadFirstObjectsToProgram();
    }

    private void loadFirstObjectsToProgram() {
        oService.loadFirstItems();
        supService.loadFirstSuppliers();
        oService.loadFirstOrders();
    }

    public void printMenu() { // PRINTS THE MENU
        System.out.println("1) Make an order\n2) Print all orders from supplier\n3) End order\n4) Get order details\n5) Create a new supplier \n6) Delete supplier \n7) Update supplier Info \n8) Delete contact from supplier  \n9) Update contact for a supplier \n10) Add item to supplier catalog \n11) Delete Item from catalog\n12) Quit");
        System.out.println("at anytime you would like to go back press b");
    }

    public String getUserInput() { //GET USER INPUT
        String input = sc.nextLine();
        return input;
    }


}