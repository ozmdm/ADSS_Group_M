package DataAccessLaye;

import ServiceLayer.ServiceObjects.*;
import bussinessLayer.SupplierPackage.Supplier;
import javafx.util.Pair;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Repo {
    public static Repo repo;
    private Connection con;
    private IBranchDAO branchDAO;
    private ICatalogItemDAO catalogItemDAO;
    private IContactDAO contactDao;
    private IDamagedControllerDAO damagedItemDAO;
    private IDeliveryDaysDAO deliveryDaysDAO;
    private IInventoryDAO inventoryDAO;
    private IItemDAO itemDAO;
    private IItemStatusDAO itemStatusDAO;
    private ILineCatalogItemInCartDAO lineCatalogItemInCartDAO;
    private IOrderDAO orderDAO;
    private IRangesDAO rangesDAODAO;
    private IScheduledOrderDAO scheduledDAO;
    private ISupplierDAO supplierDAO;
    private IContractDAO contractDAO;
    //private IOldCostPriceDAO oldCostPriceDAO;
    //private IOldSalePriceDAO oldSalePriceDAO;

    private Repo() throws SQLException {
        String url = "jdbc:sqlite:C://sqlite/db/test.db"; //TODO CHANGE TO GENERIC ONE
        con = DriverManager.getConnection(url);
        branchDAO = new BranchDAOImpl(con);
        catalogItemDAO = new CatalogItemDAOImpl(con);
        contactDao = new ContactDaoImpl(con);
        damagedItemDAO = new DamagedControllerDAOImpl(con);
        deliveryDaysDAO = new DeliveryDaysDAOImpl(con);
        inventoryDAO = new InventoryDAOImpl(con);
        itemDAO = new ItemDAOImpl(con);
        itemStatusDAO = new ItemStatusDAOImpl(con);
        lineCatalogItemInCartDAO = new LineCatalogItemInCartDAOImpl(con);
        orderDAO = new OrderDAOImpl(con);
        rangesDAODAO = new RangesDAODAOImpl(con);
        scheduledDAO = new ScheduledDAOImpl(con);
        supplierDAO = new SupplierDAOImpl(con);
        contractDAO = new ContractDAOImpl(con);
    }

    public static Repo getInstance() throws SQLException {
        if (repo != null) return repo;
        repo = new Repo();
        return repo;
    }

    public void creatTables() throws SQLException {

        String sqlQ = "CREATE TABLE IF NOT EXISTS Suppliers (\n"
                + "	supplierName varchar(50),\n"
                + "	supplierId INTEGER NOT NULL,\n"
                + "	bankAccountNumber INTEGER, \n"
                + "bilingOptions varchar, \n"
                + "CONSTRAINT PK_Supplier Primary KEY(supplierId) \n"
                + ");\n";
        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS Contacts (\n"
                + "	supplierId INTEGER ,\n"
                + "	firstName varchar,\n"
                + "	lastName varchar, \n"
                + "phoneNumber varchar, \n"
                + "address varchar, \n"
                + "CONSTRAINT PK_Contact Primary KEY(phoneNumber,supplierId), \n"
                + "CONSTRAINT  FK_Contact FOREIGN KEY (supplierId) references  Suppliers(supplierId) on delete cascade \n"
                + ");\n";
        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS Contracts (\n"
                + "	contractId INTEGER ,\n"
                + "	isDeliver Boolean,\n"
                + "CONSTRAINT PK_Contract Primary KEY(contractId), \n"
                + "CONSTRAINT  FK_Contact FOREIGN KEY (contractId) references Suppliers(supplierId) on delete cascade \n"
                + ");\n";
        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS DeliveryDays (\n"
                + "	contractId INTEGER ,\n"
                + "Deliday varchar \n"
                + "CONSTRAINT PK_DeliDays Primary KEY(Deliday,contractId), \n"
                + "CONSTRAINT  FK_DeliDays FOREIGN KEY (contractId) references Contracts(contractId) on delete cascade \n"
                + ");\n";
        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS Orders (\n"
                + "	orderId INTEGER ,\n"
                + "	branchId INTEGER ,\n"
                + "actualDeliverDate TIMESTAMP , \n"
                + "	status varchar, \n"
                + " supplierId INTEGER ,\n"
                + " creationTime TIMESTAMP , \n"
                + " deliveryDate TIMESTAMP \n"
                + "CONSTRAINT PK_Orders Primary KEY(orderId), \n"
                + "CONSTRAINT  FK_Orders FOREIGN KEY (supplierId) references Suppliers(supplierId) on delete no action \n,"
                + "CONSTRAINT  FK_ScheduledOrder3 FOREIGN KEY (branchId) references Branch(branchId) on delete no action \n"
                + ");\n";
        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS Ranges (\n"
                + " rangeId INTEGER ,\n"
                + "	catalogItemId INTEGER ,\n"
                + " contractId INTEGER , \n"
                + "	minimum INTEGER , \n"
                + " maximum INTEGER ,\n"
                + " price DOUBLE \n"
                + "CONSTRAINT PK_Ranges Primary KEY(rangeId,catalogItemId,contractId), \n"
                + "CONSTRAINT  FK_Ranges FOREIGN KEY (contractId) references Contracts(contractId) on delete cascade , \n"
                + "CONSTRAINT FK_Ranges2 FOREIGN  key (catalogItemId) references CatalogItem(catalogItemId) on delete cascade \n"
                + ");\n";
        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS CatalogItem (\n"
                + "	catalogItemId INTEGER ,\n"
                + "	contractId INTEGER ,\n"
                + "itemId INTEGER  , \n"
                + "price DOUBLE,  \n"
                + "CONSTRAINT PK_CatalogItem Primary KEY(catalogItemId,contractId), \n"
                + "CONSTRAINT  FK_CatalogItem FOREIGN KEY (contractId) references Contracts(contractId) on delete cascade \n"
                + ");\n";
        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS LineCatalogItemInCart (\n"
                + "	orderId INTEGER ,\n"
                + "	catalogItemId INTEGER ,\n"
                + "amount INTEGER , \n"
                + "	priceAfterDiscount Double , \n"
                + "CONSTRAINT PK_LineCatalogItemInCart Primary KEY(orderId,catalogItemId), \n"
                + "CONSTRAINT  FK_LineCatalogItemInCart FOREIGN KEY (orderId) references Orders(orderId) \n"
                + ");\n";
        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS ScheduledOrder (\n"
                + "	Sday INTEGER ,\n"
                + "	supplierId INTEGER ,\n"
                + "catalogItemId INTEGER , \n"
                + "	amount INTEGER , \n"
                + "branchId INTEGER \n"
                + "CONSTRAINT PK_ScheduledOrder Primary KEY(Sday,supplierID,catalogItemId,branchId), \n"
                + "CONSTRAINT  FK_ScheduledOrder FOREIGN KEY (supplierId) references Suppliers(supplierId) on delete cascade \n"
                + "CONSTRAINT  FK_ScheduledOrder2 FOREIGN KEY (catalogItemId) references CatalogItem(catalogItemId) on delete cascade \n"
                + "CONSTRAINT  FK_ScheduledOrder3 FOREIGN KEY (branchId) references Branch(branchId) on delete cascade \n"
                + ");\n";
        sqlQ = sqlQ + "CREATE INDEX rangeId on Ranges(rangeId);";

        //tables for Inventory module

        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS Branch (\n"
                + "	branchId INTEGER ,\n"
                + "	description varchar ,\n"
                + "CONSTRAINT PK_Branch Primary KEY(branchID), \n"
                + ");\n";

        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS DamagedItem (\n"
                + "	branchId INTEGER ,\n"
                + "	itemId INTEGER ,\n"
                + "	quantityDamaged INTEGER ,\n"
                + "CONSTRAINT PK_DamagedItem Primary KEY(branchID, itemId), \n"
                + "CONSTRAINT  FK_DamagedItem FOREIGN KEY (branchId) references Branch(branchId) \n"
                + ");\n";

        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS Inventory (\n"
                + "	idCounter INTEGER ,\n"
                + "CONSTRAINT PK_Inventory Primary KEY(idCounter), \n"
                + ");\n";

        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS Item (\n"
                + "	id INTEGER ,\n"
                + "	description varchar ,\n"
                + "	costPrice REAL ,\n"
                + "	salePrice REAL ,\n"
                + "	weight REAL ,\n"
                + "	category varchar ,\n"
                + "	subCategory varchar ,\n"
                + "	sub2Category varchar ,\n"
                + "	manufacturer varchar ,\n"
                + "	costCounter INTEGER ,\n"
                + "	saleCounter INTEGER ,\n"
                + "CONSTRAINT PK_Item Primary KEY(id), \n"
                + ");\n";

        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS ItemStatus (\n"
                + "	branchId INTEGER ,\n"
                + "	itemId INTEGER ,\n"
                + "	quantityShelf INTEGER ,\n"
                + "	quantityStock INTEGER ,\n"
                + "CONSTRAINT PK_ItemStatus Primary KEY(branchId, itemId), \n"
                + "CONSTRAINT  FK_ItemStatus FOREIGN KEY (branchId) references Branch(branchId) \n"
                + "CONSTRAINT  FK_ItemStatus2 FOREIGN KEY (itemId) references Item(id) \n"
                + ");\n";
//
//        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS OldCostPrice (\n"
//                + "	itemId INTEGER ,\n"
//                + "	counter INTEGER ,\n"
//                + "	price INTEGER ,\n"
//                + "CONSTRAINT PK_OldCostPrice Primary KEY(itemId, counter), \n"
//                + "CONSTRAINT  FK_OldCostPrice FOREIGN KEY (itemId) references Item(id) \n"
//                + ");\n";
//
//        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS OldSalePrice (\n"
//                + "	itemId INTEGER ,\n"
//                + "	counter INTEGER ,\n"
//                + "	price INTEGER ,\n"
//                + "CONSTRAINT PK_OldSalePrice Primary KEY(itemId, counter), \n"
//                + "CONSTRAINT  FK_OldSalePrice FOREIGN KEY (itemId) references Item(id) \n"
//                + ");\n";

        Statement stmt = con.createStatement();
        stmt.execute(sqlQ);

    }



    public CatalogItemDTO getCatalogItem(int catalogItemId, int contractId) throws SQLException {
        return this.catalogItemDAO.find(catalogItemId, contractId);
    }

    public void updateCatalogItem(int catalogItemId, int contractId, double price) throws SQLException {

        String sql = "UPDATE CatalogItem SET price = ? where catalogItemId = ? AND contractId = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setDouble(1, price);
        pstmt.setInt(2, catalogItemId);
        pstmt.setInt(3, contractId);
        pstmt.executeUpdate();

    }

    public void deleteCatalogItem(int contractId, int catalogItemId) throws SQLException {
        String sql = "DELETE FROM CatalogItem\n" +
                "WHERE contractId = ? AND  catalogItemId = ?;";

        PreparedStatement stmp = con.prepareStatement(sql);
        stmp.setInt(1, contractId);
        stmp.setInt(2, catalogItemId);
        stmp.executeUpdate();

    }



    public ContactDTO getSpecificContact(int supplierId, String phoneNumber) throws SQLException {
        return this.contactDao.find(supplierId, phoneNumber);
    }

    public void updateContact(String phoneNumber, int supplierId, ContactDTO contactDTO) throws SQLException {
        String sql = "UPDATE Contact SET phoneNumber = ? , firstName = ? ,lastName = ? , address = ? where supplierId = ? AND phoneNumber = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, phoneNumber);
        pstmt.setString(2, contactDTO.getFirstName());
        pstmt.setString(3, contactDTO.getLastName());
        pstmt.setString(4, contactDTO.getAddress());
        pstmt.setInt(5, supplierId);
        pstmt.setString(6, phoneNumber);
        pstmt.executeUpdate();
    }

    public void insertLineCatalogItem(LineCatalogItemDTO lineCatalogItemDTO, int orderId) throws SQLException {
        this.lineCatalogItemInCartDAO.insert(lineCatalogItemDTO, orderId);
    }

    public List<ContactDTO> getAllContactBySupplier(int supplierId) throws SQLException {

        return this.contactDao.findAllBySupplier(supplierId);
    }


    public ContractDTO getContract(int contractId) throws SQLException {

        return this.contractDAO.find(contractId);

    }


    public void updateContract(ContractDTO contractDTO) throws SQLException {
        String sql = "UPDATE Contract SET  isDeliver = ? where contractId = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setBoolean(1, contractDTO.getIsDeliver());
        pstmt.setInt(2, contractDTO.getSupplierId());
        pstmt.executeUpdate();
        this.deliveryDaysDAO.deleteEveryThingByContract(contractDTO.getSupplierId());
        this.deliveryDaysDAO.insertEveryTingByContract(contractDTO);

    }

    public void updateDeliveryDaysByContract(int contractId) {

    }

    public DeliveryDaysDTO getAllDeliveryDaysByContract(int contractId) throws SQLException {
        return this.deliveryDaysDAO.findAllByContract(contractId);
    }

    public void insertDeliveryDays(DeliveryDaysDTO deliveryDaysDTO , int contractId) throws SQLException {
        this.deliveryDaysDAO.insert(deliveryDaysDTO,contractId);

    }

    public void UpdateDeliveryDays(int contractId, LocalDateTime day) {

    }

    public List<LineCatalogItemDTO> getOrderItems(int orderId) {
        return null;
    }

    public void updateOrderItem(int orderId, int catalogItemId, int amount, double priceAfterDiscount) {

    }

    public void insertLineCatalogItem() {

    }

    public void deleteItemFromOrder(int catalogItemId, int orderId) throws SQLException {

        String sql = "DELETE FROM LineCatalogItemInCart\n" +
                "WHERE catalogItemId = ? AND orderId = ?;";

        PreparedStatement stmp = con.prepareStatement(sql);
        stmp.setInt(1, catalogItemId);
        stmp.setInt(2, orderId);
        stmp.executeUpdate();

    }

    public OrderDTO getOrderByID(int orderId) throws SQLException {
        return this.orderDAO.find(orderId);
    }

    public void updateOrder(OrderDTO orderDTO) throws SQLException {
        String sql = "UPDATE Orders SET orderId = ? , branchId = ? ,actualDeliverDate = ? , status = ?, supplierId = ? , creationTime = ?,deliveryDate  where orderId = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, orderDTO.getOrderId());
        pstmt.setInt(2, orderDTO.getBranchId());
        pstmt.setTimestamp(3, Timestamp.valueOf(orderDTO.getActualDate()));
        pstmt.setString(4, orderDTO.getOrderStatus());
        pstmt.setInt(5, orderDTO.getSupplierId());
        pstmt.setTimestamp(6, Timestamp.valueOf(orderDTO.getCreationDate()));
        pstmt.setTimestamp(7, Timestamp.valueOf(orderDTO.getDeliveryDate()));
        pstmt.setInt(8, orderDTO.getOrderId());
        pstmt.executeUpdate();
        orderDTO.getCart().getLineItems();
        orderDTO.getCart().getTotalAmount();
        orderDTO.getCart().getTotalPrice();
        CartDTO cartDTO = orderDTO.getCart();
        for (LineCatalogItemDTO lineCatalogItem : cartDTO.getLineItems()) {
            LineCatalogItemDTO lineCatalogItemDTO = new LineCatalogItemDTO(lineCatalogItem.getCatalogItem(), lineCatalogItem.getAmount(), lineCatalogItem.getPriceAfterDiscount());
            this.UpdateLineCatalog(lineCatalogItemDTO, orderDTO.getOrderId());
        }

    }

    private void UpdateLineCatalog(LineCatalogItemDTO lineCatalogItemDTO, int orderId) throws SQLException {

        String sql = "UPDATE LineCatalogItemInCart SET orderId = ? , catalogItemId = ? ,amount = ? , priceAfterDiscount = ? where orderId = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, orderId);
        pstmt.setInt(2, lineCatalogItemDTO.getCatalogItemId());
        pstmt.setInt(3, lineCatalogItemDTO.getAmount());
        pstmt.setDouble(4, lineCatalogItemDTO.getPriceAfterDiscount());
        pstmt.setInt(5, orderId);

    }

    public CatalogDTO getCatalog(int supplierId) throws SQLException {
        List<CatalogItemDTO> catalogItemDTOS = this.catalogItemDAO.findAll(supplierId);
        return new CatalogDTO(catalogItemDTOS);
    }

    public List<OrderDTO> getSupplierOrders(int supplierId) throws SQLException {
        List<OrderDTO> allOrders = this.orderDAO.findAll();
        List<OrderDTO> supplierOrders = new ArrayList<>();
        for (OrderDTO orderDTO : allOrders) {
            if (orderDTO.getSupplierId() == supplierId) supplierOrders.add(orderDTO);
        }
        return supplierOrders;
    }

    public void insertOrder(OrderDTO orderDTO) throws SQLException {
        this.orderDAO.insert(orderDTO);
    }

    public HashMap<Integer, List<Pair<RangeDTO, Double>>> getAllRangesByContract(int contractId) throws SQLException {
        return this.rangesDAODAO.findAll(contractId);
    }

    public List<Pair<RangeDTO, Double>> getAllRangeForCatalogItemId(int contractId, int catalogItemId) throws SQLException {
        HashMap<Integer, List<Pair<RangeDTO, Double>>> allRangesByContract = this.rangesDAODAO.findAll(contractId);
        List<Pair<RangeDTO, Double>> allRangesForCatalogItem = new ArrayList<>();
        for (Pair<RangeDTO, Double> pair : allRangesByContract.get(catalogItemId)) {
            allRangesForCatalogItem.add(pair);
        }
        return allRangesForCatalogItem;
    }

    public void deleteAllRangesByContractId(int contractId, int catalogItemId) throws SQLException {


        String sql = "DELETE FROM Ranges\n" +
                "WHERE catalogItemId = ? AND contractId = ?;";

        PreparedStatement stmp = con.prepareStatement(sql);
        stmp.setInt(1, catalogItemId);
        stmp.setInt(2, contractId);
        stmp.executeUpdate();

    }

  /*  public void updateRange(RangeDTO rangeDTO, ContractDTO contractDTO, double price) throws SQLException {
        String sql = "DELETE FROM LineCatalogItemInCart\n" +
                "WHERE catalogItemId = ? AND orderId = ?;";

        PreparedStatement stmp = con.prepareStatement(sql);
        stmp.setInt(1, catalogItemId);
        stmp.setInt(2, orderId);
        stmp.executeUpdate();
    }
*/
    public ScheduledDTO getSpecificScheduled(int branchId, int day, int supplierId) throws Exception {

        List<ScheduledDTO> scheduledDTOS = this.scheduledDAO.findAll();
        for (ScheduledDTO scheduledDTO : scheduledDTOS) {
            if (scheduledDTO.getSupplierId() == supplierId && scheduledDTO.getDay().getValue() == day && scheduledDTO.getBranchId() == branchId) {
                return scheduledDTO;
            }
        }

        throw new Exception("Scheduled order not found by input you provied");
    }

    public void deleteScheduledBySupplier(int supplierId) {

    }

    public SupplierDTO getSupplierById(int supplierId) throws SQLException {
        return this.supplierDAO.find(supplierId);
    }

    public void updateSupplier(SupplierDTO supplierDTO) throws SQLException {
        String sql = "UPDATE Suppliers SET supplierId = ? ,supplierName = ?, bankAccountNumber = ? ,bilingOptions = ?  where supplierId = ? ";
        PreparedStatement stmp = con.prepareStatement(sql);
        stmp.setInt(1, supplierDTO.getSupplierId());
        stmp.setString(2, supplierDTO.getName());
        stmp.setInt(3, supplierDTO.getBankAccountNumber());
        stmp.setString(4, supplierDTO.getBillingOption().name());
        stmp.setInt(5, supplierDTO.getSupplierId());
        stmp.executeUpdate();
        this.updateContract(supplierDTO.getContractDTO());
        for (ContactDTO contactDTO : supplierDTO.getContactDTOS()) {
            this.updateContact(contactDTO.getPhoneNumber(), supplierDTO.getSupplierId(), contactDTO);
        }

    }

    public List<SupplierDTO> getAllSuppliers() throws SQLException {
        return this.supplierDAO.findAll();
    }

    public void insertSupplier(Supplier supplier) throws SQLException {
        this.supplierDAO.insertSupplier(supplier);
    }

    public int getSupplierIdByOrder(int orderId) throws SQLException {
        return this.orderDAO.find(orderId).getSupplierId();
    }

    public OrderDTO getOrderByDateSupplier(int supplierId, int branchId, LocalDateTime deliveryDate) throws Exception {
        List<OrderDTO> allOrders = this.orderDAO.findAll();
        for (OrderDTO orderDTO : allOrders) {
            if (orderDTO.getSupplierId() == supplierId && orderDTO.getDeliveryDate().equals(deliveryDate) && orderDTO.getBranchId() == branchId)
                return orderDTO;
        }
        throw new Exception("order dose not exsit by branch id , date and supplierId");
    }

    public List<OrderDTO> getAllOrderByBranchId(int barnchId) throws SQLException {
        List<OrderDTO> allOrders = this.orderDAO.findAll();
        List<OrderDTO> orderByBranchId = new ArrayList<>();
        for (OrderDTO orderDTO : allOrders) {
            if (orderDTO.getBranchId() == barnchId)
                orderByBranchId.add(orderDTO);
        }
        return orderByBranchId;
    }

    public void insertScheduled(ScheduledDTO schedule) throws SQLException {
        this.scheduledDAO.insert(schedule);
    }

    /**
     * Get item description of specific item
     *
     * @param itemId The item ID
     * @return item description
     */
    public String getItemDescription(int itemId) throws Exception {
        List<ItemDTO> itemDTOS = this.itemDAO.findAll();
        for (ItemDTO itemDTO : itemDTOS) {
            if (itemDTO.getId() == itemId) return itemDTO.getDescription();
        }
        throw new Exception("Item do not found!");
    }

    public void insertContact(int supplierId, ContactDTO contactDTO) throws SQLException {
        this.contactDao.insert(contactDTO, supplierId);
    }

    public void deleteContact(String phoneNumber, int supplierId) throws SQLException {
        String sql = "DELETE FROM Contact\n" +
                "WHERE PhoneNumber = ? AND supplierId = ?;";

        PreparedStatement stmp = con.prepareStatement(sql);
        stmp.setString(1, phoneNumber);
        stmp.setInt(2, supplierId);
        stmp.executeUpdate();

    }

    public BranchDTO getBranchById(int branchId) throws SQLException {
	    return this.branchDAO.find(branchId);
    }

    public void createBranch(BranchDTO branch) throws SQLException {
        this.branchDAO.insert(branch);
    }

    public List<BranchDTO> getAllBranches() throws SQLException {
	    return this.branchDAO.findAll();
    }


    public void updateBranchDescription(int branchId, String description) throws SQLException {
	    this.branchDAO.updateDescription(branchId, description);
    }

    public DamagedControllerDTO getDamagedControllerForBranch(int branchId) throws SQLException{
	    return this.damagedItemDAO.findDamageController(branchId);
    }

    public void insertNewDamagedItem(int branchID,int itemId, int quantity) throws SQLException{
	    damagedItemDAO.insertDamagedItem(branchID,itemId, quantity);
    }

    public List<DamagedControllerDTO> getAllDamagedControllers() throws SQLException{
	    return damagedItemDAO.findAll();
    }

    public void updateExistingDamagedItem(int branchId, int itemId, int newQuantity) throws SQLException{
	    damagedItemDAO.updateAnItem( branchId,  itemId,  newQuantity);
    }

    public InventoryDTO getInventory() throws SQLException{
	    return inventoryDAO.find();
    }

    public void createInventory(InventoryDTO inventoryDTO) throws SQLException{
	    inventoryDAO.insert(inventoryDTO);
    }

    public void updateInventoryIdCounter(int idCounter) throws SQLException{
	    inventoryDAO.updateIdCounter(idCounter);
    }

    public ItemDTO getItem(int itemId) throws SQLException{
        return itemDAO.find(itemId);
    }

    public void addNewItem(ItemDTO itemDTO) throws SQLException{
	    itemDAO.insert(itemDTO);
    }

    public List<ItemDTO> getAllItems() throws SQLException{
	    return itemDAO.findAll();
    }

    public void updateAnItemWithoutOldPrices(ItemDTO itemDTO) throws SQLException{
        itemDAO.updateWithoutOldPrices(itemDTO);
    }

    public void updateCostPriceForItem(int itemId, double newPrice, int costCounter) throws SQLException{
	    itemDAO.updateCostPrice( itemId,  newPrice,  costCounter);
    }

    public void updateSalePriceForItem(int itemId, double newPrice, int saleCounter) throws SQLException{
	    itemDAO.updateSalePrice(itemId,  newPrice,  saleCounter);
    }

    public ItemStatusDTO getItemStatus(int branchId, int itemId) throws SQLException{
	    return itemStatusDAO.find(branchId, itemId);
    }

    public void addItemStatus(ItemStatusDTO itemStatusDTO) throws SQLException{
	    itemStatusDAO.insert(itemStatusDTO);
    }

    public List<ItemStatusDTO> getAllItemStatusByBranch(int branchId) throws SQLException{
	    return itemStatusDAO.findAllByBranch(branchId);
    }

    public void updateAnItemStatus(ItemStatusDTO itemStatusDTO) throws SQLException{
	    itemStatusDAO.updateAStatus(itemStatusDTO);
    }



    public void insertRange(RangeDTO rangeDTO, int contractId, int catalogItemId, double price) throws SQLException {
        this.rangesDAODAO.insert(rangeDTO, contractId, catalogItemId, price);
    }

    public void deleteSupplierById(int supplierId) throws SQLException {

        String sql = "DELETE FROM Suppliers\n" +
                "WHERE supplierId = ?;";

        PreparedStatement stmp = con.prepareStatement(sql);
        stmp.setInt(1, supplierId);
        stmp.executeUpdate();

    }

    public void insertCatalogItem(CatalogItemDTO catalogItemDTO, int contractId) throws SQLException {
        this.catalogItemDAO.insert(catalogItemDTO, contractId);
    }

}
