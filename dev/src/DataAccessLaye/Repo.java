package DataAccessLaye;
import bussinessLayer.OrderPackage.Item;
import bussinessLayer.OrderPackage.LineCatalogItem;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Repo {
    private Connection con;
    private BranchDAO branchDAO;
    private CatalogItemDAOImpl catalogItemDAO;
    private ContactDaoImpl contactDao;
    private DamagedControllerDAO damagedControllerDAO;
    private DeliveryDaysDAOImpl deliveryDaysDAO;
    private InventoryDAO inventoryDAO;
    private ItemDAO itemDAO;
    private ItemStatusDAO itemStatusDAO;
    private LineCatalogItemInCartDAOImpl lineCatalogItemInCartDAO;
    private OrderDAOImpl orderDAO;
    private RangesDAODAOImpl rangesDAODAO;
    private ScheduledDAOImpl scheduledDAO;
    private SupplierDAOImpl supplierDAO;

}
