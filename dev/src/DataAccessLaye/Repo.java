package DataAccessLaye;

import ServiceLayer.ServiceObjects.*;
import javafx.util.Pair;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class Repo {
    public static Repo repo;
    private Connection con;
    private BranchDAO branchDAO;
    private ICatalogItemDAO catalogItemDAO;
    private IContactDAO contactDao;
    private DamagedControllerDAO damagedControllerDAO;
    private IDeliveryDaysDAO deliveryDaysDAO;
    private InventoryDAO inventoryDAO;
    private ItemDAO itemDAO;
    private ItemStatusDAO itemStatusDAO;
    private ILineCatalogItemInCartDAO lineCatalogItemInCartDAO;
    private IOrderDAO orderDAO;
    private IRangesDAO rangesDAODAO;
    private IScheduledOrderDAO scheduledDAO;
    private ISupplierDAO supplierDAO;
    private IContractDAO contractDAO;

    private Repo() throws Exception {
        String url = "jdbc:sqlite:C://sqlite/db/test.db";
        con = DriverManager.getConnection(url);
        branchDAO = new BranchDAO(con);
        catalogItemDAO = new CatalogItemDAOImpl(con);
        contactDao = new ContactDaoImpl(con);
        damagedControllerDAO = new DamagedControllerDAO(con);
        deliveryDaysDAO = new DeliveryDaysDAOImpl(con);
        inventoryDAO = new InventoryDAO(con);
        itemDAO = new ItemDAO(con);
        itemStatusDAO = new ItemStatusDAO(con);
        lineCatalogItemInCartDAO = new LineCatalogItemInCartDAOImpl(con);
        orderDAO = new OrderDAOImpl(con);
        rangesDAODAO = new RangesDAODAOImpl(con);
        scheduledDAO = new ScheduledDAOImpl(con);
        supplierDAO = new SupplierDAOImpl(con);
        contractDAO = new ContractDAOImpl(con);
    }

    public static Repo getInstance() throws Exception {
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
                + "CONSTRAINT  FK_Contact FOREIGN KEY (supplierId) references  Suppliers(supplierId)/\n"
                + ");\n";
        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS Contracts (\n"
                + "	contractId INTEGER ,\n"
                + "	isDeliver Boolean,\n"
                + "CONSTRAINT PK_Contract Primary KEY(contractId), \n"
                + "CONSTRAINT  FK_Contact FOREIGN KEY (contractId) references Suppliers(supplierId) \n"
                + ");\n";
        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS DeliveryDays (\n"
                + "	contractId INTEGER ,\n"
                + "Deliday varchar \n"
                + "CONSTRAINT PK_DeliDays Primary KEY(Deliday,contractId), \n"
                + "CONSTRAINT  FK_DeliDays FOREIGN KEY (contractId) references Contracts(contractId) \n"
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
                + "CONSTRAINT  FK_Orders FOREIGN KEY (supplierId) references Suppliers(supplierId) \n"
                + ");\n";
        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS Ranges (\n"
                + " rangeId INTEGER ,\n"
                + "	catalogItemId INTEGER ,\n"
                + " contractId INTEGER , \n"
                + "	minimum INTEGER , \n"
                + " maximum INTEGER ,\n"
                + " price DOUBLE \n"
                + "CONSTRAINT PK_Ranges Primary KEY(rangeId,catalogItemId,contractId), \n"
                + "CONSTRAINT  FK_Ranges FOREIGN KEY (contractId) references Contracts(contractId), \n"
                + "CONSTRAINT FK_Ranges2 FOREIGN  key (catalogItemId) references CatalogItem(catalogItemId)\n"
                + ");\n";
        sqlQ = sqlQ + "CREATE TABLE IF NOT EXISTS CatalogItem (\n"
                + "	catalogItemId INTEGER ,\n"
                + "	contractId INTEGER ,\n"
                + "itemId INTEGER  , \n"
                + "price DOUBLE,  \n"
                + "CONSTRAINT PK_CatalogItem Primary KEY(catalogItemId,contractId), \n"
                + "CONSTRAINT  FK_CatalogItem FOREIGN KEY (contractId) references Contracts(contractId) \n"
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
                + "CONSTRAINT  FK_ScheduledOrder FOREIGN KEY (supplierId) references Suppliers(supplierId) \n"
                + "CONSTRAINT  FK_ScheduledOrder2 FOREIGN KEY (catalogItemId) references CatalogItem(catalogItemId) \n"
                + "CONSTRAINT  FK_ScheduledOrder3 FOREIGN KEY (branchId) references Branches(id) \n"
                + ");\n";
        sqlQ = sqlQ + "CREATE INDEX rangeId on Ranges(rangeId);";


        Statement stmt = con.createStatement();
        stmt.execute(sqlQ);

    }

    public CatalogItemDTO getCatalogItem(int catalogItemId, int contractId) throws SQLException {
        return this.catalogItemDAO.find(catalogItemId,contractId);
    }

    public void updateCatalogItem(int catalogItemId, int contractId, double price) throws SQLException {

        String sql = "UPDATE CatalogItem SET price = ? where catalogItemId = ? AND contractId = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setDouble(1,price);
        pstmt.setInt(2,catalogItemId);
        pstmt.setInt(3,contractId);
        pstmt.executeUpdate();

    }

    public void deleteCatalogItem(int contractId, int catalogItemId) {

    }

    public ContactDTO getSpecificContact(int supplierId, String phoneNumber) throws SQLException {
        return  this.contactDao.find(supplierId,phoneNumber);
    }

    public void updateContact(int supplierId, String phoneNumber, String firstName, String lastName, String address) throws SQLException {
        String sql = "UPDATE Contact SET phoneNumber = ? , firstName = ? ,lastName = ? , address = ? where supplierId = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,phoneNumber);
        pstmt.setString(2,firstName);
        pstmt.setString(3,lastName);
        pstmt.setString(4,address);
        pstmt.setInt(5,supplierId);
        pstmt.executeUpdate();
    }

    public List<ContactDTO> getAllContactBySupplier(int supplierId) throws SQLException {

        return this.contactDao.findAllBySupplier(supplierId);
    }


    public ContractDTO getContract(int contractId) throws SQLException {

        return this.contractDAO.find(contractId);

    }


    public void updateContract( int contractId, boolean isDeliver) throws SQLException {
        String sql = "UPDATE Contract SET  isDeliver = ? where contractId = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setBoolean(1,isDeliver);
        pstmt.setInt(2,contractId);
        pstmt.executeUpdate();
    }

    public void updateDeliveryDaysByContract(int contractId) {

    }

    public DeliveryDaysDTO getAllDeliveryDaysByContract(int contractId) throws SQLException {
        return this.deliveryDaysDAO.findAllByContract(contractId);
    }

    public void insertDeliveryDays() {

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

    public void deleteItemFromOrder(int catalodItemId, int orderId) {

    }

    public OrderDTO getOrderByID(int orderId) {
        return null;
    }

    public void updateOrder(int orderId, int branchId, LocalDateTime actualDeliverDate, String status, int supplierId, LocalDateTime creationTime, LocalDateTime deliveryDate) {

    }

    public List<OrderDTO> getSupplierOrders(int supplierId) {
        return null;
    }

    public void insertOrder() {

    }

    public HashMap<Integer, List<Pair<RangeDTO, Double>>> getAllRangesByContract(int contractId) {
        return null;
    }

    public List<Pair<RangeDTO, Double>> getAllRangeForCatalogItemId(int contractId, int catalogItemId) {
        return null;
    }

    public void deleteAllRangesByContractId(int contractId, int catalogItemId) {

    }

    public void updateRange(int rangeId, int catalogItemId, int contracId, int minimun, int maximum, double price) {

    }

    public List<ScheduledDTO> getAllScheduled() {
        return null;
    }

    public void deleteScheduledBySupplier(int supplierId) {

    }

    public SupplierDTO getSupplierById(int supplierId) {
        return null;
    }

    public void updateSupplier(String supplierName, int supplierId, int bankAccountNumber, String bilingOptions) {

    }

    public List<SupplierDTO> getAllSuppliers() {
        return null;
    }

    public void insertSupplier() {

    }
}
