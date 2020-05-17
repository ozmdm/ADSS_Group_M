package PresentationLayer;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import DataAccessLaye.Repo;
import Menu.mainMenu;
import ServiceLayer.*;
import ServiceLayer.ServiceObjects.*;
import bussinessLayer.BranchPackage.Branch;
import bussinessLayer.InventoryPackage.Inventory;

public class MainUserInterface {

    private IOrderService oService = OrderService.getInstance();
    private ISupplierService supService = SupplierService.getInstance();
    private InventoryService invService = new InventoryService(Inventory.getInstance());
    private UserService userService = new UserService();
    private Scanner sc = new Scanner(System.in);

    public void start() {

        loadProgramDefault(); //TODO: INITIAL OBJECTS
        int input = 0;
        do {
            //TODO ADDING USER LOGIN OZ AND LIDOR
            printMenu();
            try {
                input = Integer.valueOf(getUserInput());
            } catch (Exception e) {
                input = -1;
            }
            int branchId = -1;

            switch (input) {
                case 1:
                    int supplierId = chooseSupplier();
                    try {
                        branchId = chooseBranch();
                    } catch (Exception e) {
                       System.out.println(e.getMessage());
                    }
                    mainMenu.currentBranchId = branchId;
                    if (branchId == -1 || supplierId == -1) break;
                    manageSuppliers(supplierId, branchId);
                    break;
                case 2:
                    creatSupplierAndContract();// CREAT A NEW SUPPLIER AND ADD IT TO SYSTEM
                    break;
                case 3:
                    try {
                        branchId = chooseBranch();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    mainMenu.currentBranchId = branchId;
                    System.out.println("Please choose a menu:");
                    System.out.println("1) Inventory menu");
                    System.out.println("2) Branch menu");

                    int choice = sc.nextInt();
                    sc.nextLine();
                    if(choice == 1)
                        Menu.mainMenu.showInventoryMenu();
                    else if(choice == 2)
                        Menu.mainMenu.showBranchMenu();
                    else
                        System.out.println("Wrong input.");
                    //TODO MANAGE INVENTORY OPTION
                    //TODO: update inventory menu's currentBranchId
                    break;
                case 4:
                    Quit();
                    break;
                default:
                    System.out.println("wrong - Input");
            }

        } while (input != 4);
    }

    /**
     * Menu to choose branch
     *
     * @return The Branch ID
     */
    private int chooseBranch() throws Exception {
        String choice = "";
        while (true) {
            try {
                printAllBranches();            //TODO OZ AND LIDOR
            } catch (SQLException throwables) {
                throw new Exception("Error while trying to print branches. "+throwables.getSQLState());
            }
            System.out.println("Enter the Branch ID you wish to manage:");
            choice = getUserInput();
            boolean isExist = false;
            try {
                isExist = branchExist(choice);
            }catch (Exception e)
            {
                throw new Exception("Error while trying to get branches from Repo" + e.getMessage());
            }
            if (!isExist) { //TODO CHECK IF BRANCH EXIST OZ AND LIDOR
                throw new Exception("Branch does not exist");
            } else {
                return Integer.parseInt(choice);
            }
        }
    }

    private boolean branchExist(String choice) throws SQLException {
        List<BranchDTO> branchDTOS = Repo.getInstance().getAllBranches();
        List<Branch> branches = new LinkedList<>();
        for (BranchDTO branch : branchDTOS) {
            branches.add(branch.convertFromDTO());
        }
        for (Branch branch: branches) {
            if(branch.getId() == Integer.parseInt(choice))
                return true;
        }
        return false;
    }

    private void printAllBranches() throws SQLException {
        List<BranchDTO> branchDTOS = Repo.getInstance().getAllBranches();
        List<Branch> branches = new LinkedList<>();
        for (BranchDTO branch : branchDTOS) {
            branches.add(branch.convertFromDTO());
        }
        for (Branch branch: branches) {
            System.out.println("Branch id: "+branch.getId() +", description: "+branch.getDescription());
        }
    }

	/*private void addNewItem() {
		System.out.println("Enter Item description");
		String itemDes = getUserInput();
		System.out.println("Enter Item manufacture");
		String manufactuer = getUserInput();
		String s = oService.addItem(itemDes, manufactuer);
		if (s.equals("Done")) {
			System.out.println("Done");
		} else
			System.out.println(s);
	}*/

    /**
     * Choose supplier menu
     *
     * @return supplier ID
     */
    private int chooseSupplier() {
        String choice = "";
        while (true) {
            printSuppliers();
            System.out.println("Enter the supplier's ID you wish to manage or \"b\" to return to menu:");
            choice = getUserInput();
            Response response = supService.isSupplierExist(choice);
            if (choice.equals("b")) return -1;
            if (!response.isErrorOccured()) break;
            System.out.println(response.getMessage());
        }

        return Integer.valueOf(choice);
    }

    /**
     * Print all suppliers
     */
    private void printSuppliers() {
        ResponseT<List<SupplierDTO>> r = supService.getSuppliersInfo();
        String s = "";
        if (r.isErrorOccured()) {
            System.out.println("There are no Suppliers");
            return;
        }
        for (SupplierDTO sup : r.getObj()) {
            s += "\n" + sup.getSupplierId() + "\t" + sup.getName();
        }

        System.out.println(s);
    }

    /**
     * Manage Supplier menu showing all the option
     * which the user could perform on a supplier
     *
     * @param supplierId The supplier ID to act on
     */
    private void manageSuppliers(int supplierId, int branchId) {

        int input = 0;
        do {
            printSupplierMenu();
            try {
                input = Integer.valueOf(getUserInput());
            } catch (Exception e) {
                input = -1;
            }
            switch (input) {
                case 1:
                    manageOrders(supplierId, branchId);
                    break;
                case 2:
                    System.out.println("are you sure? [y/n] ");
                    if (getUserInput().equals("n"))
                        break;
                    System.out.println(supService.removeSupplier(supplierId));// DELETE SUPPLIER FROM THE SYSTEM
                    return;
                case 3:
                    updateSupplier(supplierId);// UPDATE FIELDS OF SUPPLIER
                    break;
                case 4:
                    deleteContactFromSupplier(supplierId); // DELETE CONTACT LIST FROM SPECIFIC SUPPLIER
                    break;
                case 5:
                    updateContactForSupplier(supplierId); // UPDATE CONTACT INFO FROM SPECIFIC SUPPLIER
                    break;
                case 6:
                    addItemToSupplierCatalog(supplierId); // ADD NEW ITEM TO CATALOG FOR SPECIFIC SUPPLIER
                    break;
                case 7:
                    deleteItemFromCatalog(supplierId);// DELETE ITEM FROM CATALOG FOR SPECIFIC SUPPLIER
                    break;
                case 8:
                    getSuppliersInfo(supplierId); // PRINT THE SUPPLIERS INFORMATION (NAME,ID,BANK-ACCOUNT) //TODO
                    // CHANGE TO ONLY SPECIFIC SUPPLIER
                    break;
                case 9:
                    return; // RETURN TO PREVIOUS MENU
                default:
                    System.out.println("Invalid input");
            }
        } while (input != 9);

    }

    /**
     * Menu for managing orders
     *
     * @param supplierId The supplier ID
     * @param branchId   The branch ID
     */
    private void manageOrders(int supplierId, int branchId) {
        int input = 0;
        do {
            printManageOrdersMenu();
            try {
                input = Integer.valueOf(getUserInput());
            } catch (Exception e) {
                input = -1;
            }
            switch (input) {
                case 1:
                    makeAnOrder(supplierId, branchId); // ORDER MENU
                    break;
                case 2:
                    printOrdersFromSupplier(supplierId,branchId); // PRINTS ALL ORDERS FROM SUPPLIER
                    break;
                case 3:
                    endOrder(); // CHANGE ORDER'S STATUS TO INPROGRESS
                    break;
                case 4:
                    getOrderDetails();// GET ORDER DETAILS OF A specific order
                    break;
                case 5:
                    return; // RETURNS TO THE PREVIOUS MENU
                default:
                    System.out.println("Invalid Input");
            }
        } while (input != 5);

    }

    /**
     * Prints the Manage orders menu options
     */
    private void printManageOrdersMenu() {
        System.out.println(
                "1) Make an order\n2) Print all orders from supplier\n3) End order\n4) Get order details\n5) Return to previous Menu");

    }

    /**
     * Prints the main menu options
     */
    private void printMenu() {
        System.out.println("1) Manage Suppliers\n2) Create new Supplier\n3) Manage Inventory and Branches\n4) Quit");

    }

    /**
     * Menu for showing supplier's details
     *
     * @param supplierId The supplier ID
     */
    private void getSuppliersInfo(int supplierId) {
        int input;
        do {
            System.out.println(
                    "1) present the catalogItem for Supplier\n2) present the contact list for Supplier\n3) present payment option for Supplier\n4) present what days Supplier do delivery\n5) Return to previous menu");
            String userInput = getUserInput();
            if (userInput.equals("b"))
                return;
            try {
                input = Integer.valueOf(userInput);
            } catch (Exception e) {
                input = -1;
            }
            switch (input) {
                case 1:
                    System.out.println(supService.getCatalog(supplierId).getObj());
                    break;
                case 2:
                    System.out.println(supService.getContactsList(supplierId).getObj());
                    break;
                case 3:
                    System.out.println(supService.getSupplierInfo(supplierId).getObj());
                    break;
                case 4:
                    System.out.println(supService.getContractDetails(supplierId).getObj());
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid Input");
                    break;
            }
        } while (input != 5);

    }

    /**
     * Menu for deleting item from Supplier's catalog
     *
     * @param supplierId The supplier ID
     */
    private void deleteItemFromCatalog(int supplierId) {
        int catalogItemId = 0;
        String string = "";
        System.out.println("Please enter CatalogItemId For the item you want to remove");
        string = getUserInput();
        if (string.equals("b"))
            return;
        try {
            catalogItemId = Integer.valueOf(string);
            System.out.println(supService.deleteCatalogItemFromCatlogInContract(supplierId, catalogItemId));
        } catch (Exception e) {
            System.out.println("catalog item id invalid input");
        }
    }


    /**
     * Menu for adding item to Supplier's catalog
     *
     * @param supplierId The supplier ID
     */
    private void addItemToSupplierCatalog(int supplierId) {
        int ItemId = 0;
        int catalogItemId = 0;
        double price = 0;
        String string = "";
        System.out.println("Please enter ItemId from the list of items");
        ResponseT<List<ItemDTO>> itemsList = invService.getItemsList();
        if (itemsList.isErrorOccured()) {
            System.out.println(itemsList.getMessage());
            return;
        }
        printItemsFromInventory(itemsList);
        string = getUserInput();
        if (string.equals("b"))
            return;
        try {
            ItemId = Integer.valueOf(string);
            System.out.println("Please enter CatalogItemId For the item you choose");
            string = getUserInput();
            if (string.equals("b"))
                return;
            catalogItemId = Integer.valueOf(string);
            System.out.println("Please enter price for CatalogItem you choose");
            string = getUserInput();
            if (string.equals("b"))
                return;
            price = Double.valueOf(string);
            System.out.println(supService.addCatalogItemToCatalogInContract(supplierId, ItemId, catalogItemId, price));
            addNewAgreementToItem(supplierId, catalogItemId);
        } catch (Exception e) {
            System.out.println("Input Invalid");
            return;
        }
    }

    /**
     * Prints all items from inventory
     *
     * @param itemsList list of all items
     */
    private void printItemsFromInventory(ResponseT<List<ItemDTO>> itemsList) {
        System.out.println("ID\tDescription");
        for (ItemDTO item : itemsList.getObj()) {
            System.out.println(item.getId() + "\t" + item.getDescription());
        }
    }

    /**
     * Menu for adding new agreement(Ranges) for catalog item related to specific
     * supplier
     *
     * @param supplierId    The supplier ID
     * @param catalogItemId The catalog item ID
     */
    private void addNewAgreementToItem(int supplierId, int catalogItemId) {
        String string;
        int max = 0;
        int min = 0;
        double priceafterDisc = 0;
        supService.cleanRangeListItemFromMap(supplierId, catalogItemId);
        do {
            System.out.println(
                    "Enter item amount price agreement as follow: min:max:price if entered max = -1 means done");
            string = getUserInput();
            String split[] = string.split(":");
            try {
                min = Integer.valueOf(split[0]);
                max = Integer.valueOf(split[1]);
                priceafterDisc = Double.valueOf(split[2]);
            } catch (Exception e) {
                System.out.println("Not Numbers");
            }
            System.out.println(supService.UpdateMap(supplierId, catalogItemId, min, max, priceafterDisc).getMessage()); // TODO
        } while (max != -1);
    }

    /**
     * Opens menu for updating supplier's details
     *
     * @param supplierId The supplier ID
     */
    private void updateContactForSupplier(int supplierId) {
        String phoneNum = "";
        System.out.println(supService.getContactsList(supplierId));
        System.out.println("Please enter phone Number of the contact you would like to change from the list above");
        phoneNum = getUserInput();
        if (phoneNum.equals("b")) return;
        System.out.println(
                "Please enter details as follow: firstName:lastName:phoneNumber:address if change is not needed for one of the fields just enter empty like this: ''  ");
        System.out.println("example: ' dov:itzhak::bit yani");
        String split = getUserInput();
        if (split.equals("b"))
            return;
        String[] update = split.split(":");
        System.out.println(supService.updateContact(supplierId, update, phoneNum));

    }

    /**
     * Opens a menu for deleting contact from supplier
     *
     * @param supplierId The supplier ID
     */
    private void deleteContactFromSupplier(int supplierId) {
        System.out.println("Please enter phone number of the contact you would like to delete from list Of contact");
        System.out.println(supService.getContactsList(supplierId));
        String s = getUserInput();
        if (s.equals("b"))
            return;
        System.out.println(supService.deleteContact(supplierId, s));
    }

    /**
     * Opens a menu for updating supplier
     *
     * @param supplierId The supplier ID
     */
    private void updateSupplier(int supplierId) {
        int input = 0;
        String s = "";

        do {
            System.out.println(
                    "1) update Supplier name \n2) update bank Account Number\n3) update billingOption \n4) update if supplier is deliver\n5)update const day delivery days \n6)Add Contact\n7)Change agreement contract for specific Item\n8)exit");
            s = getUserInput();
            if (s.equals("b"))
                return;
            try {
                input = Integer.valueOf(s);
            } catch (Exception e) {
                input = -1;
            }
            switch (input) {
                case 1:
                    System.out.println("Please enter new Supplier name:");
                    s = getUserInput();
                    if (s.equals("b"))
                        return;
                    String name = s;
                    System.out.println(supService.updateSupplierName(supplierId, name));
                    break;
                case 2:
                    System.out.println("Please enter new Supplier bank Account");
                    s = getUserInput();
                    if (s.equals("b"))
                        return;
                    int bankAccount;
                    try {
                        bankAccount = Integer.valueOf(s);
                    } catch (Exception e) {
                        System.out.println("Invalid input");
                        break;
                    }
                    System.out.println(supService.updateSupplierBankAccount(supplierId, bankAccount));
                    break;
                case 3:
                    System.out.println(
                            "Please enter one of Supplier billing option -> {eom30 / eom 60 / cash / bankTransfer / Check}");
                    s = getUserInput();
                    if (s.equals("b"))
                        return;
                    String bilingOption = s;
                    System.out.println(supService.updateBillingOptions(supplierId, bilingOption));
                    break;
                case 4:
                    System.out.println(
                            "Please enter if supplier has Deliveries or not -> press (y/other button except b)");
                    s = getUserInput();
                    if (s.equals("b"))
                        return;
                    String IsDelivery = s;
                    boolean isDeliver = false;
                    if (IsDelivery.equals("y")) {
                        isDeliver = true;
                    }
                    String error = supService.updateContractIsDeliver(supplierId, isDeliver).getMessage();
                    System.out.println(error);
                    if (error.equals("Done"))
                        addConstDayDelivery(supplierId);
                    break;
                case 5:
                    addConstDayDelivery(supplierId);
                    break;
                case 6:
                    addContact(supplierId);
                    break;
                case 7:
                    System.out.println("Please enter Catalog-item-id to change in the agreement:");
                    String userInput = getUserInput();
                    if (userInput.equals("b"))
                        break;
                    int catalogItemId;
                    try {
                        catalogItemId = Integer.valueOf(userInput);
                    } catch (Exception e) {
                        System.out.println("Invalid input");
                        break;
                    }
                    addNewAgreementToItem(supplierId, catalogItemId);
                    break;
                default:
                    System.out.println("Invalid Input");
                    break;

            }
        } while (input != 8);
    }

    /**
     * Opens menu for adding contact to supplier
     *
     * @param supplierId The supplier ID
     */
    private void addContact(int supplierId) {
        String s;
        System.out.println("add contact in this way -> firstName:lastName:phoneNumber:address ,  to add contact pleas pres enter to finish pres 0 and then enter");
        // input = Integer.valueOf(getUserInput());
        s = getUserInput();
        if (s.equals("b") || s.equals("0"))
            return;
        String[] contact = s.split(":");
        System.out.println(supService.addContact(supplierId, contact[0], contact[1], contact[2], contact[3]));

    }

    /**
     * Opens Menu for creating supplier and contract
     */
    private void creatSupplierAndContract() {
        try {
            System.out.println("Enter supplier ID:");
            String s = getUserInput();
            if (s.equals("b"))
                return;
            int supplierId = Integer.valueOf(s);
            System.out.println("Enter supplier Name:");
            s = getUserInput();
            if (s.equals("b"))
                return;
            String SupplierName = s;
            System.out.println("Enter supplier BankAccount:");
            s = getUserInput();
            if (s.equals("b"))
                return;
            int bankAcount = Integer.valueOf(s);
            System.out.println("Enter billing Options, choose one of the presented potions");
            System.out.println(
                    "please enter the details exactly in the next format: {EOM30 / EOM60 / CASH / BANKTRANSFER / CHECK} ");
            s = getUserInput();
            if (s.equals("b"))
                return;
            String bilingOptions = s;
            boolean isDeliver = false;
            System.out.println("the supplier is deliver if y/n ?");
            s = getUserInput();
            if (s.equals("b"))
                return;
            String IsDelivery = s;
            if (IsDelivery.equals("y")) {
                isDeliver = true;
            }
            String error = supService.AddSupplier(SupplierName, supplierId, bankAcount, bilingOptions, isDeliver).getMessage();
            System.out.println(error);
            if (!error.equals("Done")) {
                return;
            }

            if (IsDelivery.equals("y"))
                addConstDayDelivery(supplierId);

            completeContract(supplierId);

            do {
                System.out.println(
                        "add contact in this way -> firstName:lastName:phoneNumber:address ,  to add contact please press enter to finish the process pres 0");
                // input = Integer.valueOf(getUserInput());
                s = getUserInput();
                if (s.equals("b") || s.equals("0"))
                    return;
                String[] contact = s.split(":");
                System.out.println(supService.addContact(supplierId, contact[0], contact[1], contact[2], contact[3]));
            } while (true);
        } catch (Exception e) {
            System.out.println("Invalid Input");
            return;
        }

    }

    /**
     * Adding const day delivery
     *
     * @param supplierId
     */
    private void addConstDayDelivery(int supplierId) {

        System.out.println(
                "Please enter const day delivery with big letters in this way -> MONDAY:SUNDAY:..: , if not deliver in const days press 'b' ");
        String input = getUserInput();
        if (input.equals("b"))
            return;
        String[] constDayDeli = input.split(":");
        System.out.println(supService.addConstDeliveryDays(constDayDeli, supplierId));
    }

    private void completeContract(int supplierId) {
        System.out.println("Now you should add items to catalog");
        do {
            System.out.println("continue? [y/n]");
            if (getUserInput().equals("n"))
                return;
            addItemToSupplierCatalog(supplierId);
        } while (true);
    }

    /**
     * closing the scanner and prints goodbye
     */
    private void Quit() {
        sc.close();
        oService.purgeTimer();
        System.out.println("BYE!");
    }

    /**
     * Print specific Order
     */
    private void getOrderDetails() { // GET ORDER DETAILS OF ORDER NO.@orderId
        System.out.println("Enter order ID:");
        String s = getUserInput();
        if (s.equals("b"))
            return;
        int orderId;
        try {
            orderId = Integer.valueOf(s);
        } catch (Exception e) {
            System.out.println("Invalid input");
            return;
        }
        System.out.println(oService.getOrderDetails(orderId));
    }

    /**
     * End order means the order arrived
     */
    private void endOrder() { // CHANGES ORDER STATUS TO COMPLETE
        System.out.println("Enter order ID:");
        String s = getUserInput();
        if (s.equals("b"))
            return;
        int orderId;
        try {
            orderId = Integer.valueOf(s);
        } catch (Exception e) {
            System.out.println("Invalid input");
            return;
        }
        System.out.println(oService.endOrder(orderId).getMessage());
    }

    /**
     * Prints all orders from a certain supplier
     *
     * @param supplierId The supplier ID
     */
    private void printOrdersFromSupplier(int supplierId,int branchId) { // PRINTS ALL ORDERS FROM SUPPLIER
        System.out.println(oService.printOrdersFromSupplier(supplierId,branchId));
    }

    private void makeAnOrder(int supplierId, int branchId) { // ORDER MENU
        int input = 0;
        int orderId = oService.createAnOrder(supplierId, branchId).getObj();
        System.out.println(supService.getCatalog(supplierId));

        do {
            System.out.println("1) Add item\n2) Remove item\n3) Confirm order");
            try {
                input = Integer.valueOf(getUserInput());
            } catch (Exception e) {
                input = -1;
            }
            switch (input) {
                case 1:
                    addItemToCart(orderId); // ADD ITEM TO CART
                    break;
                case 2:
                    removeItemFromCart(orderId); // REMOVES AN ITEM FROM CART
                    break;
                case 3:
                    System.out.println(oService.sendOrder(orderId)); // CONFIRM ORDER(SEND ORDER())
                    break;
                default:
                    System.out.println("Invalid Input try again");
                    break;
            }
        } while (input != 3);
    }

    /**
     * Removing Item to order
     *
     * @param orderId The order ID which we want to remove the item from
     */
    private void removeItemFromCart(int orderId) { // REMOVES AN ITEM FROM CART
        System.out.println("Enter catalog item ID:");
        String s = getUserInput();
        if (s.equals("b"))
            return;
        String catalogItemId = s;
        try {
            System.out.println(oService.removeFromCart(orderId, Integer.valueOf(catalogItemId)));
        } catch (Exception e) {
            System.out.println("Invalid Input");
        }
    }

    /**
     * Adding Item to order
     *
     * @param orderId The order ID which we want to add to
     */
    private void addItemToCart(int orderId) { // ADD ITEM TO CART
        System.out.println("Enter as Follow: CatalogItemId:Amount");
        String s = getUserInput();
        if (s.equals("b"))
            return;
        String split[] = s.split(":");
        try {
            System.out.println(oService.addItemToCart(orderId, Integer.valueOf(split[0]), Integer.valueOf(split[1])));
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }

    /**
     * Loading the program with basic objects or clean start
     */
    public void loadProgramDefault() {
        while (true) {
            System.out.println("1) Load with objects\n2) Clean start");
            int input;
            try {
                input = Integer.valueOf(getUserInput());
            } catch (Exception e) {
                System.out.println("Invalid Input");
                return;
            }
            if (input == 1) {
                loadFirstObjectsToProgram();
                //TODO OZ AND LIDOR LOAD INITIAL OBJECT
            }
            if (input == 0 || input == 1) break;
        }
    }

    /**
     * Loads first object to the program
     */
    private void loadFirstObjectsToProgram() {
        supService.loadFirstSuppliers();
    }

    /**
     * Prints the Options of manage suppliers
     */
    public void printSupplierMenu() {
        System.out.println("1) Manage Orders\n2) Delete Supplier\n3) Update supplier\n4) Delete Contact\n5) Update Contact\n6) Add Item to supplier's catalog\n7) Delete item from catalog\n8) Print supplier Info\n9) Return to previous menu");
    }

    /**
     * Gets input from the user
     *
     * @return The input received from the user as string
     */
    public String getUserInput() { // GET USER INPUT
        String input = sc.nextLine();
        return input;
    }

}
