package Menu;

import DataAccessLaye.Repo;
import ServiceLayer.*;
import ServiceLayer.Response;
import ServiceLayer.ServiceObjects.SupplierDTO;
import bussinessLayer.InventoryPackage.Inventory;
import MessageTypes.*;
import bussinessLayer.SupplierPackage.Supplier;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class mainMenu {

    public static int currentBranchId; //as received from main menu

    private static UserService userService = new UserService();
    private static InventoryService inventoryService = new InventoryService();
    private static BranchService branchService = new BranchService();

    public void start() {

        Scanner scanner = new Scanner(System.in);

        int choice;

        while (true) {
            showMainMenu();
            choice = scanner.nextInt();
            scanner.nextLine();
            int userId;
            String password;
            Response response;
            switch (choice) {
                case 1:
                    System.out.println("Please insert user id:");
                    userId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Please insert password:");
                    password = scanner.nextLine();
                    response = userService.login(userId, password);
                    System.out.println(response.getMessage());
                    System.out.println();
                    if (response.isErrorOccured()) //in case there is an exception - something went wrong
                        continue;
                    showInventoryMenu(); //login succeed
                    break;
                case 2:
                    System.out.println("Please choose a password:");
                    password = scanner.nextLine();
                    response = userService.register(password);
                    System.out.println(response.getMessage());
                    if (response.isErrorOccured())
                        continue;
                    System.out.println("Now please login");
                    continue;
                case 3:
                    System.out.println("Loading data to the system");
                    initData();
                    System.out.println("Data loaded successfully\n");
                    continue;
                case 4:
                    scanner.close();
                    System.exit(0);
                    break;
                case 999: //debugging mode
                    showInventoryMenu();
                    break;
            }
        }
    }

    public static void showMainMenu() {
        System.out.println("This is the main menu");
        System.out.println("Please choose an option:");
        System.out.println("1) login");
        System.out.println("2) register");
        System.out.println("3) load data");
        System.out.println("4) quit");

    }

    public static void showBranchMenu() {
        Response response;
        Scanner scanner = new Scanner(System.in);
        boolean returnMainMenu = false;
        int choice, itemId, quantity;
        while (true) {
            System.out.println("Welcome to the Branch menu.");
            System.out.println("Please select an option:");
            System.out.println("Edit an item in a branch:\n"
                    + "\t1) update shelf quantity for an item by delta\n"
                    + "\t2) update stock quantity for an item by delta\n"
                    + "\t3) use Canceling Card for an item\n"
                    + "\t4) update delta quantity for a damaged item\n"
                    + "Generate a report for a branch:\n"
                    + "\t5) stock report\n"
                    + "\t6) damaged items report\n"
                    + "\t7) warnings report\n"
                    + "\t8) items to order report\n"
                    + "9) generate to order report AND MAKE AN ORDER BY LACKS\n"
                    + "10) create a new branch\n"
                    + "11) edit branch description\n"
                    + "12) return to main menu");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Insert item id:");
                    itemId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Insert the DELTA for shelf quantity to be added to the current quantity - positive or negative:");
                    quantity = Integer.parseInt(scanner.nextLine()); //delta
                    response = branchService.updateItemShelfQuantity(currentBranchId, itemId, quantity);
                    System.out.println(response.getMessage());
                    break;
                case 2:
                    System.out.println("Insert item id:");
                    itemId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Insert the DELTA for stock quantity to be added to the current quantity - positive or negative:");
                    quantity = Integer.parseInt(scanner.nextLine()); //delta
                    response = branchService.updateItemStockQuantity(currentBranchId, itemId, quantity);
                    System.out.println(response.getMessage());
                    break;
                case 3:
                    System.out.println("Insert item id:");
                    itemId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Insert the quantity of sale to cancel - a NON negative number:");
                    quantity = Integer.parseInt(scanner.nextLine());
                    response = branchService.cancelCard(currentBranchId, itemId, quantity);
                    System.out.println(response.getMessage());
                    break;
                case 4:
                    System.out.println("Insert item id:");
                    itemId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Insert the DELTA for damaged quantity to be added to the current quantity - positive or negative:");
                    quantity = Integer.parseInt(scanner.nextLine());
                    response = branchService.updateDamagedItem(currentBranchId, itemId, quantity);
                    System.out.println(response.getMessage());
                case 5:
                    System.out.println("Please insert following data. If you wish to show all of the category/sub category - press only Enter, without an input: ");
                    System.out.println("Insert first category:");
                    String category = scanner.nextLine();
                    if (category.length() == 0) {
                        ResponseT<StockReport> responseT = branchService.generateStockReport(currentBranchId, new String[0]);
                        System.out.println(responseT.getObj());
                    } else {
                        System.out.println("Insert sub category:");
                        String subCategory = scanner.nextLine();
                        if (subCategory.length() == 0) {
                            ResponseT<StockReport> responseT = branchService.generateStockReport(currentBranchId, new String[]{category});
                            System.out.println(responseT.getObj());
                        } else {
                            System.out.println("Insert sub sub (2) category:");
                            String sub2Category = scanner.nextLine();
                            if (sub2Category.length() == 0) {
                                ResponseT<StockReport> responseT = branchService.generateStockReport(currentBranchId, new String[]{category, subCategory});
                                System.out.println(responseT.getObj());
                            } else {
                                ResponseT<StockReport> responseT = branchService.generateStockReport(currentBranchId, new String[]{category, subCategory, sub2Category});
                                System.out.println(responseT.getObj());
                            }
                        }
                    }
                    break;
                case 6:
                    ResponseT<Damaged> responseT = branchService.generateDamagedReport(currentBranchId);
                    System.out.println(responseT.getObj());
                    break;
                case 7:
                    ResponseT<ItemWarning> responseTt = branchService.generateWarningReport(currentBranchId);
                    System.out.println(responseTt.getObj());
                    break;
                case 8:
                    ResponseT<ToOrder> respons = branchService.generateToOrderReport(currentBranchId);
                    System.out.println(respons.getObj());
                    break;
                case 9:
                    ResponseT<ToOrder> responsAndMake = null;
                    List<Supplier> suppliers = createListFromDB();
                    try {
                        responsAndMake = branchService.generateAndSendOrder(currentBranchId, suppliers);
                    } catch (Exception e) {
                        System.out.println("Error while making an order. "+e.getMessage());
                    }

                    System.out.println("An order has been sent, as detailed in the next 'To Order' Report:\n" + responsAndMake.getObj());
                    break;
                case 10:
                    System.out.println("Please enter a description for the new branch:\n");
                    String description = scanner.nextLine();
                    Response res = branchService.createBranch(description);
                    System.out.println(res.getMessage());
                    break;
                case 11:
                    System.out.println("Please enter the new branch description:\n");
                    String desc = scanner.nextLine();
                    Response res1 = branchService.updateBranchDescription(currentBranchId, desc);
                    System.out.println(res1.getMessage());
                    break;
                case 12:
                    returnMainMenu = true;
                    break;
            }
            if(returnMainMenu == true)
                break;
        }
    }

    private static List<Supplier> createListFromDB(){
        List<Supplier> suppliers = new LinkedList<>();
        try {
            List<SupplierDTO> list = Repo.getInstance().getAllSuppliers();

            for (SupplierDTO supplier: list) {
                Supplier supp = new Supplier(supplier);
                suppliers.add(supp);
            }
        } catch (SQLException throwables) {
            System.out.println("Error while getting suppliers from database. "+throwables.getSQLState());
        }
        return suppliers;
    }
    public static void showInventoryMenu () {
                Response response;
                Scanner scanner = new Scanner(System.in);
                boolean returnMainMenu = false;
                int choice, itemId, quantity, price;
                while (true) {
                    System.out.println("Welcome to the inventory menu.");
                    System.out.println("Please select an option:");
                    System.out.println("1) add item to the inventory\n"
                            + "Edit an item in the inventory:\n"
                                + "\t2) edit minimum quantity for an item\n"
                            + "\tUpdate price:\n"
                                + "\t\t3) update cost price for an item\n"
                                + "\t\t4) update sale price for an item\n"
                            + "5) return to main menu");
                    choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1:
                            System.out.println("You chose to add an item. Please insert the following data:");
                            System.out.println("description:");
                            String description = scanner.nextLine();
                            System.out.println("shelf quantity (integer only):");
                            int quantityShelf = Integer.parseInt(scanner.nextLine());
                            System.out.println("stock quantity (integer only):");
                            int quantityStock = Integer.parseInt(scanner.nextLine());
                            System.out.println("cost price: (double)");
                            double costPrice = Double.parseDouble(scanner.nextLine());
                            System.out.println("sale price: (double)");
                            double salePrice = Double.parseDouble(scanner.nextLine());
                            System.out.println("position:");
                            String position = scanner.nextLine();
                            System.out.println("minimum quantity (integer only):");
                            int minQuantity = Integer.parseInt(scanner.nextLine());
                            System.out.println("weight in kilograms (double):");
                            double weight = Double.parseDouble(scanner.nextLine());
                            System.out.println("manufacturer:");
                            String manufacturer = scanner.nextLine();
                            System.out.println("category:");
                            String category = scanner.nextLine();
                            System.out.println("sub category:");
                            String subCategory = scanner.nextLine();
                            System.out.println("sub sub (2) category:");
                            String sub2Category = scanner.nextLine();
                            response = inventoryService.addItem(description, quantityShelf, quantityStock, costPrice, salePrice,
                                    position, minQuantity, weight, category, subCategory, sub2Category, manufacturer);
                            System.out.println(response.getMessage());
                            break;
                        case 2:
                            System.out.println("Insert item id:");
                            itemId = Integer.parseInt(scanner.nextLine());
                            System.out.println("Insert new minimum quantity:");
                            quantity = Integer.parseInt(scanner.nextLine());
                            response = inventoryService.editMinimumQuantity(itemId, quantity);
                            System.out.println(response.getMessage());
                            break;
                        case 3:
                            System.out.println("Insert item id:");
                            itemId = Integer.parseInt(scanner.nextLine());
                            System.out.println("Insert new cost price:");
                            price = Integer.parseInt(scanner.nextLine());
                            response = inventoryService.updateItemCostPrice(itemId, price);
                            System.out.println(response.getMessage());
                            break;
                        case 4:
                            System.out.println("Insert item id:");
                            itemId = Integer.parseInt(scanner.nextLine());
                            System.out.println("Insert new sale price:");
                            price = Integer.parseInt(scanner.nextLine());
                            response = inventoryService.updateItemSalePrice(itemId, price);
                            System.out.println(response.getMessage());
                            break;
                        case 5:
                            returnMainMenu = true;
                            break;
                    }
                    if (returnMainMenu)
                        break; //break the while and return to main menu
                }
            }

            public static void initData () {


//                userService.register("1234");
//                userService.register("AbccbA");

                inventoryService.addItem("Milk", 10, 15, 3.5, 5, "pos", 7, 1.5, "Dairy", "Milk", "3%", "Tnuva");
                inventoryService.addItem("Cheese", 7, 7, 7.5, 10, "pos", 10, 0.5, "Dairy", "Cheese", "7%", "Tnuva");
                inventoryService.addItem("Shampoo", 20, 30, 4.5, 6.5, "pos", 20, 2, "Hygiene", "Toiletries", "Shampoo", "Kef");
                inventoryService.addItem("Conditioner", 20, 30, 4.5, 7, "pos", 10, 2, "Hygiene", "Toiletries", "Conditioner", "Kef");
                inventoryService.addItem("Bun", 15, 13, 0.5, 1.5, "pos", 30, 0.5, "Bread", "Buns", "", "Angel");
/*                inventoryService.updateDamagedItem(1, 4);
                inventoryService.updateDamagedItem(2, 2);*/
            }

    public int getCurrentBranchId() {
        return currentBranchId;
    }

    public void setCurrentBranchId(int currentBranchId) {
        this.currentBranchId = currentBranchId;
    }
}
