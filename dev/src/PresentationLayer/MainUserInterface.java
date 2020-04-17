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
                    endOrder();
                    break;
                case 4:
                    getOrderDetails();//GET ORDER DETAILS OF A specific order
                    break;
                case 5:
                    creatSupplierAndContract();//GET ORDER DETAILS OF A specific order
                    break;
                case 6:
                    deleteSupplier();//GET ORDER DETAILS OF A specific order
                    break;
                case 7:
                    updateSupplier();//GET ORDER DETAILS OF A specific order
                    break;
                case 8:
                    deleteContactFromSupplier();
                    break;
                case 9:
                    updateContactForSupplier(); 
                    break;
                case 10:
                    addItemToSupplierCatalog(); 
                    break;
                case 11:
                    deleteItemFromCatalog();
                    break;
                case 12:
                    Quit(); //QUIT
                    break;
                default:
                    break;
            }
        } while (input != 12);
    }

    private  void  deleteItemFromCatalog(){
        int supplierId = 0;
        int catalogItemId = 0;
        System.out.println("Pleas enter supplierId to delete from this supplier some item\n");
        supplierId = Integer.valueOf(getUserInput());
        System.out.println("Pleas enter CatalogItemId For the item you want to remove\n");
        catalogItemId = Integer.valueOf(getUserInput());
        supService.deleteCatalogItemFromCatlogInContract(supplierId,catalogItemId);
    }
    private void addItemToSupplierCatalog() {
        int supplierId = 0;
        int ItemId = 0;
        int catalogItemId = 0;
        double price = 0;
        System.out.println("Pleas enter supplierId to add for this supplier some item\n");
        supplierId = Integer.valueOf(getUserInput());
        System.out.println("Pleas enter ItemId from the list of items\n");
        System.out.println(Data.toStringItemsList());
        ItemId = Integer.valueOf(getUserInput());
        System.out.println("Pleas enter CatalogItemId For the item you choose\n");
        catalogItemId = Integer.valueOf(getUserInput());
        System.out.println("Pleas enter price for CatalogItem you choose\n");
        price = Double.valueOf(getUserInput());
        supService.addCatalogItemToCatalogInContract(supplierId, ItemId, catalogItemId, price);

        System.out.println("Enter item amount price agreement as follow: min:max:price if entered max = -1 means done");
        int max = 0;
        int min = 0;
        double priceafterDisc = 0;
        do {
            String split[] = getUserInput().split(":");
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
        System.out.println("Pleas enter supplierId to get all contact of the supplier\n");
        supplierId = Integer.valueOf(getUserInput());
        System.out.println("Pleas enter contactId you would like to change from list Of contact\n");
        System.out.println(supService.getSupplierById(supplierId).contactListPrinted());
        contactId = Integer.valueOf(getUserInput());
        System.out.println("Pleas enter detailes as follow: firstName:lastName:phoneNumber:address if change is not needed for one put \" \"");
        String[] update = getUserInput().split(":");
        supService.updateContact(supplierId, update, contactId);

    }

    private void deleteContactFromSupplier() {
        int supplierId = 0;
        int contactId = 0;
        System.out.println("Pleas enter supplierId to get his contact of the supplier\n");
        supplierId = Integer.valueOf(getUserInput());
        System.out.println("Pleas enter contactId you would like to delete from list Of contact\n");
        System.out.println(supService.getSupplierById(supplierId).contactListPrinted());
        contactId = Integer.valueOf(getUserInput());
        supService.deleteContact(supplierId, contactId);
    }

    private void updateSupplier() {
        int input = 0;
        int supplierid;
        System.out.println("Pleas enter supplierId To Update\n");
        supplierid = Integer.valueOf(getUserInput());
        System.out.println("1) update Supplier name \n2) update bank Account Number\n3) update bilingOption \n4) update if supplier is deliver\n5)exit");
        do {
            input = Integer.valueOf(getUserInput());
            switch (input) {
                case 1:
                    System.out.println("Pleas enter new Supplier name\n");
                    String name = getUserInput();
                    supService.updateSupplierName(supplierid, name); //ADD ITEM TO CART
                    break;
                case 2:
                    System.out.println("Pleas enter new Supplier bank Account\n");
                    int bankAccount = Integer.valueOf(getUserInput());
                    supService.updateSupplierBankAccount(supplierid, bankAccount); // REMOVES AN ITEM FROM CART
                    break;
                case 3:
                    System.out.println("Pleas enter one of Supplier biling option -> {eom30 / eom 60 / cash / bankTransfer / Check}\n");
                    String bilingOption = getUserInput();
                    supService.updateBillingOptions(supplierid, bilingOption); //CONFIRM ORDER(SEND ORDER())
                    break;
                case 4:
                    System.out.println("Pleas enter if supplier Deliver or not -> (y/n)\n");
                    String IsDelivery = getUserInput();
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
        int input = 0;
        System.out.println("Enter supplierId To Remove:\n");
        int SupplierId = Integer.valueOf(getUserInput());
        supService.removeSupplier(SupplierId);
    }

    private void creatSupplierAndContract() { // TODO - > finish the creatsupplier need to fill the contract ADT (map, catalog ... )
        int input = 0;
        System.out.println("Enter supplier ID:\n");
        int supplierId = Integer.valueOf(getUserInput());
        System.out.println("Enter supplier Name:\n");
        String SupplierName = getUserInput();
        System.out.println("Enter supplier BankAccount:\n");
        int bankAcount = Integer.valueOf(getUserInput());
        System.out.println("Enter biling Options -> {eom30 / eom 60 / cash / bankTransfer / Check}:\n");
        String bilingOptions = getUserInput();
        System.out.println("the supplier is deliver if y/n ? \n");
        String IsDelivery = getUserInput();
        boolean isDeliver;
        if (IsDelivery.equals('y')) {
            isDeliver = true;
        } else {
            isDeliver = false;
        }
        supService.AddSupplier(SupplierName, supplierId, bankAcount, bilingOptions, isDeliver);
        do {
            input = Integer.valueOf(getUserInput());
            System.out.println("add contact -> {firstName:lastName:phoneNumber:address}: to add contact pleas pres enter to finish pres 0 and then enter  \n");
            String contactDeitals = getUserInput();
            String[] contact = contactDeitals.split(":");
            supService.addContect(supplierId, contact[0], contact[1], contact[2], contact[3]);
        }
        while (input != 0);


    }

    private void Quit() {
        sc.close();
        System.out.println("BYE!");
    }

    private void getOrderDetails() { // GET ORDER DETAILS OF ORDER NO.@orderId
        System.out.println("Enter order ID:\n");
        int orderId = Integer.valueOf(getUserInput());
        oService.getOrderDetails(orderId);
    }

    private void endOrder() { // CHANGES ORDER STATUS TO COMPLETE
        System.out.println("Enter order ID:\n");
        int orderId = Integer.valueOf(getUserInput());
        oService.endOrder(orderId);
    }

    private void printOrdersFromSupplier() { // PRINTS ALL ORDERS FROM SUPPLIER
        int supplierId = Integer.valueOf(getUserInput());
        System.out.println(oService.printOrdersFromSupplier(supplierId));
    }

    private void makeAnOrder() { // ORDER MENU
        int input = 0;
        int supplierId = 0;
        System.out.println("Enter supplier ID:\n");
        supplierId = Integer.valueOf(getUserInput());
        int orderId = oService.createAnOrder(supplierId);
        System.out.println("1) Add item\n2) Remove item\n3) Confirm order");

        do {
            input = Integer.valueOf(getUserInput());
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
        System.out.println("Enter catalog item ID: itemId:Amount\n");
        String catalogItemId = getUserInput();
        oService.removeFromCart(orderId, Integer.valueOf(catalogItemId));
    }

    private void addItemToCart(int orderId) { //ADD ITEM TO CART
        System.out.println("Enter as Follow: CatalogItemId:Amount\n");
        String split[] = getUserInput().split(":");
        oService.addItemToCart(orderId, Integer.valueOf(split[0]), Integer.valueOf(split[1]));
    }

    public void loadProgramDefault() { //LOADING THE PROGRAM WITH BASIC OBJECTS OR CLEAN START
        System.out.println("1) Load with objects\n2) Clean start");
        if (!getUserInput().equals("1")) return;
        //TODO FILL THE PROGRAM WITH ESSENTIAL OBJECTS
    }

    public void printMenu() { //PRINTS THE MENU
        System.out.println("1) Make an order\n2) Print all orders from supplier\n3) End order\n4) Get order details\n5) Quit");
    }

    public String getUserInput() { //GET USER INPUT
        String input = sc.nextLine();
        return input;
    }


}