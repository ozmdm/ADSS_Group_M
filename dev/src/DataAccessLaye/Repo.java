package DataAccessLaye;
import bussinessLayer.OrderPackage.Item;
import bussinessLayer.OrderPackage.LineCatalogItem;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    }

    public static Repo getInstance() throws Exception {
        if (repo!=null) return repo;
        repo = new Repo();
        return repo;
    }

    public void creatTable() throws SQLException {

        String sqlQ = "CREATE TABLE IF NOT EXISTS Supplier (\n"
                + "	name varchar(50),\n"
                + "	supplierId INTEGER NOT NULL,\n"
                + "	bankAccountNumber INTEGER \n"
                +"CONSTRAINT PK_Supplier Primary KEY(supplierId) \n"
                +");";
        sqlQ += "CREATE TABLE IF NOT EXISTS Supplier (\n"
                + "	name varchar(50),\n"
                + "	supplierId INTEGER NOT NULL,\n"
                + "	bankAccountNumber INTEGER \n"
                +"CONSTRAINT PK_Supplier Primary KEY(supplierId) \n"
                +");";

        Statement stmt = con.createStatement();
        stmt.execute(sqlQ);

    }
}
