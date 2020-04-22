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

			switch(input) {
			case 1:
				int supplierId = chooseSupplier();
				if (supplierExist(supplierId)){
				manageSuppliers(supplierId);
				}
				else System.out.println("supplier doesnt exist");
				break;
			case 2:
				creatSupplierAndContract();//CREAT A NEW SUPPLIER AND ADD IT TO SYSTEM
				break;
			case 3:
				Quit();
				break;
			}

		} while (input != 3);
	}

	private boolean supplierExist(int supplierId) {
		 return supService.isExist(supplierId);
	}

	private int chooseSupplier() {
		System.out.println(supService.getSuppliersId()); //TODO THE FUNCTION getSuppliersId NEEDS TO RETURN STRING WHICH CONTAINS ALL SUPPLIERS ID'S AND THEIR NAME BY THEIR SIDE
		System.out.println("Enter the supplier's ID you wish to manage:");
		return Integer.valueOf(getUserInput());
	}

	private void manageSuppliers(int supplierId) {

		int input = 0;
		do {
			printSupplierMenu();
			input = Integer.valueOf(getUserInput());
			switch (input) {
			case 1:
				manageOrders(supplierId);
				break;
			case 2:
				System.out.println("are you sure? [y/n] ");
				if(getUserInput().equals("n")) break;
				supService.removeSupplier(supplierId);//DELETE SUPPLIER FROM THE SYSTEM
				return;
			case 3:
				updateSupplier(supplierId);//UPDATE FIELDS OF SUPPLIER
				break;
			case 4:
				deleteContactFromSupplier(supplierId); // DELETE CONTACT LIST FROM SPECIFIC SUPPLIER
				break;
			case 5:
				updateContactForSupplier(supplierId);  // UPDATE CONTACT INFO FROM SPECIFIC SUPPLIER
				break;
			case 6:
				addItemToSupplierCatalog(supplierId);  // ADD NEW ITEM TO CATALOG FOR SPECIFIC SUPPLIER
				break;
			case 7:
				deleteItemFromCatalog(supplierId);// DELETE ITEM  FROM CATALOG FOR SPECIFIC SUPPLIER
				break;
			case 8:
				getSuppliersInfo(supplierId); //PRINT THE SUPPLIERS INFORMATION (NAME,ID,BANK-ACCOUNT) //TODO CHANGE TO ONLY SPECIFIC SUPPLIER
				break;
			case 9:
				return; //RETURN TO PREVIOUS MENU
			}
		} while(input != 9);

	}

	private void manageOrders(int supplierId) {
		int input = 0;
		do {
			printManageOrdersMenu();
			input = Integer.valueOf(getUserInput());
			switch (input) {
			case 1:
				makeAnOrder(supplierId); //ORDER MENU
				break;
			case 2:
				printOrdersFromSupplier(supplierId); // PRINTS ALL ORDERS FROM SUPPLIER
				break;
			case 3:
				endOrder(); //CHANGE ORDER'S STATUS TO INPROGRESS
				break;
			case 4:
				getOrderDetails();//GET ORDER DETAILS OF A specific order
				break;
			case 5:
				return; // RETURNS TO THE PREVIOUS MENU
			}
		} while(input != 5);

	}

	private void printManageOrdersMenu() {
		System.out.println("1) Make an order\n2) Print all orders from supplier\n3) End order\n4) Get order details\n5) Return to previous Menu");

	}

	private void printMenu() {
		System.out.println("1) Manage Suppliers\n2) Create new Supplier\n3) Quit");

	}

	private void getSuppliersInfo(int supplierId) {
		int input;
		do {
			System.out.println("1) present the catalogItem for Supplier\n2) present the contact list for Supplier\n3) present payment option for Supplier\n4) present what days Supplier do delivery\n5) Return to previous menu");
			String userInput = getUserInput();
			if (userInput.equals("b")) return;
			input = Integer.valueOf(userInput);
			switch (input) {
			case 1:
				System.out.println(supService.getCatalogPrinted(supplierId));
				break;
			case 2:
				System.out.println(supService.contactListPrinted(supplierId)); 
				break;
			case 3:
				System.out.println(supService.getSupplierById(supplierId).getBilingToPrint());
				break;
			case 4:
				System.out.println(supService.getSupplierById(supplierId).getContract().getConstDayDelivierToPrinted());
				break;
			case 5:
				return;
			}
		} while (input != 5);

	}

	private void  deleteItemFromCatalog(int supplierId){
		int catalogItemId = 0;
		String string = "";
		System.out.println("Please enter CatalogItemId For the item you want to remove");
		string = getUserInput();
		if (string.equals("b")) return;
		catalogItemId = Integer.valueOf(string);
		supService.deleteCatalogItemFromCatlogInContract(supplierId,catalogItemId);
	}

	private void addItemToSupplierCatalog(int supplierId) {
		int ItemId = 0;
		int catalogItemId = 0;
		double price = 0;
		String string = "";
		System.out.println("Please enter ItemId from the list of items");
		System.out.println(Data.toStringItemsList());
		string = getUserInput();
		if (string.equals("b")) return;
		ItemId = Integer.valueOf(string);
		System.out.println("Please enter CatalogItemId For the item you choose");
		string = getUserInput();
		if (string.equals("b")) return;
		catalogItemId = Integer.valueOf(string);
		System.out.println("Please enter price for CatalogItem you choose");
		string = getUserInput();
		if (string.equals("b")) return;
		price = Double.valueOf(string);
		supService.addCatalogItemToCatalogInContract(supplierId, ItemId, catalogItemId, price);
		addNewAgreementToItem(supplierId, catalogItemId);
	}

	private void addNewAgreementToItem(int supplierId, int catalogItemId) {
		String string;
		int max = 0;
		int min = 0;
		double priceafterDisc = 0;
		supService.cleanRangeListItemFromMap(supplierId,catalogItemId);
		do {
			System.out.println("Enter item amount price agreement as follow: min:max:price if entered max = -1 means done");
			string = getUserInput();
			if (string.equals("b")) return;
			String split[] = string.split(":");
			min = Integer.valueOf(split[0]);
			max = Integer.valueOf(split[1]);
			priceafterDisc = Double.valueOf(split[2]);
			supService.UpdateMap(supplierId, catalogItemId, min, max, priceafterDisc);
		}
		while (max != -1);
	}

	private void updateContactForSupplier(int supplierId) {
		int contactId = 0;
		String string = "";
		System.out.println(supService.getSupplierById(supplierId).contactListPrinted());
		System.out.println("Please enter contactId you would like to change from the list above");
		string = getUserInput();
		if (string.equals("b")) return;
		contactId = Integer.valueOf(string);
		System.out.println("Please enter detailes as follow: firstName:lastName:phoneNumber:address if change is not needed for one put \" \"");
		string = getUserInput();
		if (string.equals("b")) return;
		String[] update = string.split(":");
		supService.updateContact(supplierId, update, contactId);

	}

	private void deleteContactFromSupplier(int supplierId) {
		int contactId = 0;
		System.out.println("Please enter contactId you would like to delete from list Of contact");
		System.out.println(supService.getSupplierById(supplierId).contactListPrinted());
		String s = getUserInput();
		if (s.equals("b")) return;
		contactId = Integer.valueOf(s);
		supService.deleteContact(supplierId, contactId);
	}

	private void updateSupplier(int supplierId) {
		int input = 0;
		String s = "";
		
		do {
			System.out.println("1) update Supplier name \n2) update bank Account Number\n3) update bilingOption \n4) update if supplier is deliver\n5)update const day delivery days \n6)Add Contact\n7)Change agreement contract for specific Item\n8)exit");
			s = getUserInput();
			if (s.equals("b")) return;
			input = Integer.valueOf(s);
			switch (input) {
			case 1:
				System.out.println("Please enter new Supplier name:");
				s = getUserInput();
				if (s.equals("b")) return;
				String name = s;
				supService.updateSupplierName(supplierId, name);
				break;
			case 2:
				System.out.println("Please enter new Supplier bank Account");
				s = getUserInput();
				if (s.equals("b")) return;
				int bankAccount = Integer.valueOf(s);
				supService.updateSupplierBankAccount(supplierId, bankAccount); 
				break;
			case 3:
				System.out.println("Please enter one of Supplier biling option -> {eom30 / eom 60 / cash / bankTransfer / Check}");
				s = getUserInput();
				if (s.equals("b")) return;
				String bilingOption = s;
				supService.updateBillingOptions(supplierId, bilingOption);
				break;
			case 4:
				System.out.println("Please enter if supplier Deliver or not -> (y/n)");
				s = getUserInput();
				if (s.equals("b")) return;
				String IsDelivery = s;
				boolean isDeliver = false;
				if (IsDelivery.equals("y")) {
					isDeliver = true;
				}
				supService.updateContractIsDeliver(supplierId, isDeliver);
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
					if (userInput.equals("b")) break;
					int catalogItemId = Integer.valueOf(userInput);
					addNewAgreementToItem(supplierId,catalogItemId);
					break;

			}
		} while (input != 8);
	}


	private void addContact(int supplierId) {
		String s;
		System.out.println("add contact -> {firstName:lastName:phoneNumber:address},  to add contact pleas pres enter to finish pres 0 and then enter");
		// input = Integer.valueOf(getUserInput());
		s = getUserInput();
		if (s.equals("b") || s.equals("0")) return;
		String[] contact = s.split(":");
		supService.addContact(supplierId, contact[0], contact[1], contact[2], contact[3]);

		
	}

	private void creatSupplierAndContract() {
		System.out.println("Enter supplier ID:");
		String s = getUserInput();
		if (s.equals("b")) return;
		int supplierId = Integer.valueOf(s);
		System.out.println("Enter supplier Name:");
		s = getUserInput();
		if (s.equals("b")) return;
		String SupplierName = s;
		System.out.println("Enter supplier BankAccount:");
		s = getUserInput();
		if (s.equals("b")) return;
		int bankAcount = Integer.valueOf(s);
		System.out.println("Enter biling Options -> {EOM30 / EOM60 / CASH / BANKTRANSFER / CHECK}:");
		s = getUserInput();
		if (s.equals("b")) return;
		String bilingOptions = s;
		System.out.println("the supplier is deliver if y/n ?");
		s = getUserInput();
		if (s.equals("b")) return;
		String IsDelivery = s;
		boolean isDeliver;
		if (IsDelivery.equals("y")) {
			isDeliver = true;
			addConstDayDelivery(supplierId);
		} else {
			isDeliver = false;
		}
		supService.AddSupplier(SupplierName, supplierId, bankAcount, bilingOptions, isDeliver);

		completeContract(supplierId);

		do {
			System.out.println("add contact -> {firstName:lastName:phoneNumber:address},  to add contact please press enter to finish pres 0 and then enter");
			// input = Integer.valueOf(getUserInput());
			s = getUserInput();
			if (s.equals("b") || s.equals("0")) return;
			String[] contact = s.split(":");
			supService.addContact(supplierId, contact[0], contact[1], contact[2], contact[3]);
		}
		while (true);

	}

	private void addConstDayDelivery(int supplierId) {

		System.out.println("Please enter const day delivery with big letters -> {MONDAY:SUNDAY:..:} if not deliver in const days press b");
		String input = getUserInput();
		if(input.equals("b")) return;
		String [] constDayDeli = input.split(":");
		supService.addConstDeliveryDays(constDayDeli,supplierId);
	}

	private void completeContract(int supplierId) {
		System.out.println("Now you should add items to catalog");
		do{
			System.out.println("continue? [y/n]");
			if(getUserInput().equals("n")) return;
			addItemToSupplierCatalog(supplierId);
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
		System.out.println(oService.getOrderDetails(orderId));
	}

	private void endOrder() { // CHANGES ORDER STATUS TO COMPLETE
		System.out.println("Enter order ID:");
		String s = getUserInput();
		if (s.equals("b")) return;
		int orderId = Integer.valueOf(s);
		oService.endOrder(orderId);
	}

	private void printOrdersFromSupplier(int supplierId) { // PRINTS ALL ORDERS FROM SUPPLIER
		System.out.println(oService.printOrdersFromSupplier(supplierId));
	}

	private void makeAnOrder(int supplierId) { // ORDER MENU
		int input = 0;
		int orderId = Integer.valueOf(oService.createAnOrder(supplierId));
		System.out.println(supService.getCatalogPrinted(supplierId));

		do {
			System.out.println("1) Add item\n2) Remove item\n3) Confirm order");
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
		System.out.println("Enter catalog item ID:");
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

	public void printSupplierMenu() { // PRINTS THE MENU
		System.out.println("1) Manage Orders\n2) Delete Supplier\n3) Update supplier\n4) Delete Contact\n5) Update Contact\n6) Add Item to supplier's catalog\n7) Delete item from catalog\n8) Print supplier Info\n9) Return to previous menu");
	}

	public String getUserInput() { //GET USER INPUT
		String input = sc.nextLine();
		return input;
	}


}