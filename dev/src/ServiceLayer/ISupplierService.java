package ServiceLayer;

import bussinessLayer.Supplier;

public interface ISupplierService {
    
    public String AddSupplier(String supplierName, int supplierId, int bankAccount, String bilingOptions, boolean isDeliver);
    public Supplier getSupplierById(int supplierId);
    public String updateSupplierBankAccount(int supplierId, int bankAccount);
    public String updateSupplierName(int supplierId, String name);
    public String addContact(int supplierId, String firstName, String lastName, String phoneNum, String address);
    public String deleteContact(int supplierId, int contactId);
    public String updateContact(int supplierId, String[] updated, int contactId);
    public String updateContractIsDeliver(int supplierId, boolean isDeliver);
    public String updateBillingOptions(int supplierid, String bilingOption);
    public String UpdateMap(int supplierId, int catalogItemId, int min, int max, double priceafterDisc);
    public String removeSupplier(int SupplierId);
    public String addCatalogItemToCatalogInContract(int supplierId, int itemId, int catalogItemId, double price);
    public String deleteCatalogItemFromCatlogInContract(int supplierId, int catalogItemId);
    public String getSuppliersInfo();
    public String getCatalogPrinted(int supplierId);
    public String getSuppliersId();
    public String addConstDeliveryDays(String[] constDayDeli, int supplierId);
    public String contactListPrinted(int supplierId);
    public String getBilingToPrint(int supplierId);
    public String getConstDayDelivierToPrinted(int supplierId);
    public String cleanRangeListItemFromMap(int supplierId, int catalogItemId);
    
}