package Menu;

import bussinessLayer.InventoryPackage.Inventory;
import ServiceLayer.Response;
import ServiceLayer.ResponseT;
import ServiceLayer.InventoryService;
import ServiceLayer.UserService;
import MessageTypes.*;

import java.util.Scanner;

public class mainMenu {
    private static UserService userService = new UserService();
    private static InventoryService inventoryService = new InventoryService(Inventory.getInstance());
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int choice;


        /*comment*/
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
                    password=scanner.nextLine();
                    response = userService.register(password);
                    System.out.println(response.getMessage());
                    if(response.isErrorOccured())
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

    public static void showMainMenu(){
        System.out.println("This is the main menu");
        System.out.println("Please choose an option:");
        System.out.println("1) login");
        System.out.println("2) register");
        System.out.println("3) load data");
        System.out.println("4) quit");

    }

    public static void showInventoryMenu() {
        Response response;
        Scanner scanner = new Scanner(System.in);
        boolean returnMainMenu = false;
        int choice, itemId, quantity, price;
        while(true) {
            System.out.println("Welcome to the inventory menu.");
            System.out.println("Please select an option:");
            System.out.println("1) add item\n"
                    + "Edit an item:\n"
                    + "\t2) edit minimum quantity for an item\n"
                    + "\t3) update shelf quantity for an item by delta\n"
                    + "\t4) update stock quantity for an item by delta\n"
                    + "\t5) use Canceling Card for an item\n"
                    + "Update price:\n"
                    + "\t6) update cost price for an item\n"
                    + "\t7) update sale price for an item\n"
                    + "8) update delta quantity for a damaged item\n"
                    + "Generate a report:\n"
                    + "\t9) stock report\n"
                    + "\t10) damaged items report\n"
                    + "\t11) warnings report\n"
                    + "\t12) items to order report\n"
                    +"13) return to main menu");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
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
                    System.out.println("Insert the DELTA for shelf quantity to be added to the current quantity - positive or negative:");
                    quantity = Integer.parseInt(scanner.nextLine()); //delta
                    response = inventoryService.updateItemShelfQuantity(itemId, quantity);
                    System.out.println(response.getMessage());
                    break;
                case 4:
                    System.out.println("Insert item id:");
                    itemId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Insert the DELTA for stock quantity to be added to the current quantity - positive or negative:");
                    quantity = Integer.parseInt(scanner.nextLine()); //delta
                    response = inventoryService.updateItemStockQuantity(itemId, quantity);
                    System.out.println(response.getMessage());
                    break;
                case 5:
                    System.out.println("Insert item id:");
                    itemId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Insert the quantity of sale to cancel - a NON negative number:");
                    quantity = Integer.parseInt(scanner.nextLine());
                    response = inventoryService.cancelCard(itemId, quantity);
                    System.out.println(response.getMessage());
                    break;
                case 6:
                    System.out.println("Insert item id:");
                    itemId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Insert new cost price:");
                    price = Integer.parseInt(scanner.nextLine());
                    response = inventoryService.updateItemCostPrice(itemId, price);
                    System.out.println(response.getMessage());
                    break;
                case 7:
                    System.out.println("Insert item id:");
                    itemId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Insert new sale price:");
                    price = Integer.parseInt(scanner.nextLine());
                    response = inventoryService.updateItemSalePrice(itemId, price);
                    System.out.println(response.getMessage());
                    break;
                case 8:
                    System.out.println("Insert item id:");
                    itemId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Insert the DELTA for damaged quantity to be added to the current quantity - positive or negative:");
                    quantity = Integer.parseInt(scanner.nextLine());
                    response = inventoryService.updateDamagedItem(itemId, quantity);
                    System.out.println(response.getMessage());
                    break;
                case 9:
                    System.out.println("Please insert following data. If you wish to show all of the category/sub category - press only Enter, without an input: ");
                    System.out.println("Insert first category:");
                    category = scanner.nextLine();
                    if (category.length() == 0){
                        ResponseT<StockReport> responseT = inventoryService.generateStockReport(new String[0]);
                        System.out.println(responseT.getObj());
                    }
                    else{
                        System.out.println("Insert sub category:");
                        subCategory = scanner.nextLine();
                        if (subCategory.length() == 0){
                            ResponseT<StockReport> responseT = inventoryService.generateStockReport(new String[]{category});
                            System.out.println(responseT.getObj());
                        }
                        else{
                            System.out.println("Insert sub sub (2) category:");
                            sub2Category = scanner.nextLine();
                            if (sub2Category.length() == 0){
                                ResponseT<StockReport> responseT = inventoryService.generateStockReport(new String[]{category, subCategory});
                                System.out.println(responseT.getObj());
                            }
                            else{
                                ResponseT<StockReport> responseT = inventoryService.generateStockReport(new String[]{category, subCategory, sub2Category});
                                System.out.println(responseT.getObj());
                            }
                        }
                    }
                    break;
                case 10:
                    ResponseT<Damaged> responseT = inventoryService.generateDamagedReport();
                    System.out.println(responseT.getObj());
                    break;
                case 11:
                    ResponseT<ItemWarning> responseTt = inventoryService.generateWarningReport();
                    System.out.println(responseTt.getObj());
                    break;
                case 12:
                    ResponseT<ToOrder> respons = inventoryService.generateToOrderReport();
                    System.out.println(respons.getObj());
                    break;
                case 13:
                    returnMainMenu = true;
                    break;
            }
            if (returnMainMenu)
                break; //break the while and return to main menu
        }
    }

    public static void initData() {
        userService.register("1234");
        userService.register("AbccbA");
        inventoryService.addItem("Milk", 10,15,3.5,5, "pos",7,1.5,"Dairy", "Milk", "3%", "Tnuva");
        inventoryService.addItem("Cheese", 7,7,7.5,10, "pos",10,0.5,"Dairy", "Cheese", "7%", "Tnuva");
        inventoryService.addItem("Shampoo", 20,30,4.5,6.5, "pos",20,2,"Hygiene", "Toiletries", "Shampoo", "Kef");
        inventoryService.addItem("Conditioner", 20,30,4.5,7, "pos",10,2,"Hygiene", "Toiletries", "Conditioner", "Kef");
        inventoryService.addItem("Bun", 15,13,0.5,1.5, "pos",30,0.5,"Bread", "Buns", "", "Angel");
        inventoryService.updateDamagedItem(1,4);
        inventoryService.updateDamagedItem(2,2);
    }
}
