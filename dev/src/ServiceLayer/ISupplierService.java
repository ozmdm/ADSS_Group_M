package ServiceLayer;

import java.util.List;
import ServiceLayer.ServiceObjects.*;

public interface ISupplierService {
    
    /**
     * Add Supplier to DB and RAM
     * @param supplierName The Supplier's name
     * @param supplierId - The Supplier's Id
     * @param bankAccount - Bank account number
     * @param bilingOptions - Which payment methods the store pays to the supplier 
     * @param isDeliver - Supplier has deliveries or not
     * @return 
     */
    public Response AddSupplier(String supplierName, int supplierId, int bankAccount, String bilingOptions, boolean isDeliver);
    public Response updateSupplierBankAccount(int supplierId, int bankAccount);
    public Response updateSupplierName(int supplierId, String name);
    public Response addContact(int supplierId, String firstName, String lastName, String phoneNum, String address);
    public Response deleteContact(int supplierId, int contactId);
    public Response updateContact(int supplierId, String[] updated, int contactId);
    public Response updateContractIsDeliver(int supplierId, boolean isDeliver);
    public Response updateBillingOptions(int supplierId, String bilingOption);
    public Response UpdateMap(int supplierId, int catalogItemId, int min, int max, double priceafterDisc);
    public Response removeSupplier(int SupplierId);
    public Response addCatalogItemToCatalogInContract(int supplierId, int itemId, int catalogItemId, double price);
    public Response deleteCatalogItemFromCatlogInContract(int supplierId, int catalogItemId);
    public ResponseT<List<Supplier>> getSuppliersInfo();
    public ResponseT<Catalog> getCatalog(int supplierId);
    public Response addConstDeliveryDays(String[] constDayDeli, int supplierId);
    public ResponseT<List<Contact>> getContactsList(int supplierId);
    public ResponseT<Contract> getContractDetails(int supplierId);
    public String cleanRangeListItemFromMap(int supplierId, int catalogItemId);
    public Response isSupplierExist(int supplierId);
    
}