package ServiceLayer;


import bussinessLayer.CatalogItem;
import bussinessLayer.Supplier;
import Data.Data;

import java.util.List;

public class SupplierService {
    static SupplierService supplierService;

    private SupplierService() {
    }

    public static SupplierService getInstance() {
        if (supplierService == null) {
            supplierService = new SupplierService();
        }
        return supplierService;
    }

    public void AddSupplier(String supplierName, int supplierId, int bankAccount, String bilingOptions, boolean isDeliver) throws Exception {
        supplierService.isExistForCreatSupp(supplierId);
        Supplier s = new Supplier(supplierName, supplierId, bankAccount, Supplier.bilingOption.valueOf(bilingOptions), isDeliver);
        Data.getSuppliers().add(s);
    }


    private void isExistForCreatSupp(int supplierId) throws Exception {
        for (Supplier supplier : Data.getSuppliers()) {
            if (supplier.getSupplierId() == supplierId) return;
        }
        throw new Exception("supplier already exist");
    }


    public Supplier getSupplierById(int supplierId) throws Exception {
        List<Supplier> suppliers = Data.getSuppliers();
        if (suppliers.isEmpty()) throw new Exception("Supplier list is empty");
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getSupplierId() == supplierId) {
                return suppliers.get(i);
            }
        }
        throw new Exception("supplier doesnt exist");

    }

    public void updateSupplierBankAccount(int supplierId, int bankAccount) throws Exception {
        List<Supplier> suppliers = Data.getSuppliers();
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getSupplierId() == supplierId) {
                suppliers.get(i).setBankAccountNumber(bankAccount);
                return;
            }
        }
        throw new Exception("supplier do not found");

    }


    ///
    public void updateSupplierName(int supplierId, String name) throws Exception {
        List<Supplier> suppliers = Data.getSuppliers();
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getSupplierId() == supplierId) {
                suppliers.get(i).setName(name);
                return;
            }
        }
        throw new Exception("supplier do not found");
    }

    public void addContact(int supplierId, String firstName, String lastName, String phoneNum, String address) throws Exception {
        List<Supplier> suppliers = Data.getSuppliers();
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getSupplierId() == supplierId) {
                suppliers.get(i).setContact(firstName, lastName, phoneNum, address);
                return;
            }
        }
    }

    public void deleteContact(int supplierId, int contactId) throws Exception {
        List<Supplier> suppliers = Data.getSuppliers();
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getSupplierId() == supplierId) {
                suppliers.get(i).deleteContact(contactId);
                break;
            }
        }
    }

    public void updateContact(int supplierId, String[] updated, int contactId) throws Exception {  /// update[t] == "" meaning not need to update else update the fields
        List<Supplier> suppliers = Data.getSuppliers();
        Supplier temp = null;
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierId() == supplierId)
                temp = supplier;
            break;
        }
        if (temp == null) throw new Exception("supplier do not found");

        for (int j = 0; j < temp.getContactsList().size(); j++) {
            if (temp.getContactsList().get(j).getContactId() == contactId) {
                temp.getContactsList().get(j).setFirstName(updated[0]);
                temp.getContactsList().get(j).setLastName(updated[1]);
                temp.getContactsList().get(j).setPhonNumber(updated[2]);
                temp.getContactsList().get(j).setAddress(updated[3]);
                return;
            }
        }
        throw new Exception("contact to update do not found");


    }

    public void updateContractIsDeliver(int supplierId, boolean isDeliver) throws Exception {
        List<Supplier> suppliers = Data.getSuppliers();
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierId() == supplierId) {
                supplier.setDeliverContrect(isDeliver);
                return;
            }
        }
        throw new Exception("supplier do not found");
    }

/*    public void addToMap(int supplierId, int catalogItemId, int minAmount, int maxAmount, double price) {
        List<Supplier> suppliers = Data.getSuppliers();
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierId() == supplierId) {
                supplier.addToMap(catalogItemId, minAmount, maxAmount, price);
                break;
            }
        }
    }*/

    public void updateBillingOptions(int supplierid, String bilingOption) throws Exception {
        List<Supplier> suppliers = Data.getSuppliers();
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierId() == supplierid) {
                supplier.updateBilingOptions(bilingOption);
                return;
            }
        }
        throw new Exception("supplier do not found");
    }

    public void UpdateMap(int supplierId, int catalogItemId, int min, int max, double priceafterDisc) throws Exception { // TODO -> SUPPURET UPDATE RANGE AND DISCOUNT FOR A ITEM INT CATALOGITEMA
        List<Supplier> suppliers = Data.getSuppliers();
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierId() == supplierId) {
                supplier.updateMap(catalogItemId, min, max, priceafterDisc);
                return;
            }
        }
        throw new Exception("supplier do not found");
    }

    public void removeSupplier(int SupplierId) throws Exception {
        List<Supplier> suppliers = Data.getSuppliers();
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierId() == SupplierId) {
                suppliers.remove(supplier);
                return;
            }
        }
        throw new Exception("supplier do not found");

    }

    public void addCatalogItemToCatalogInContract(int supplierId, int itemId, int catalogItemId, double price) throws Exception {
        List<Supplier> suppliers = Data.getSuppliers();
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierId() == supplierId) {
                supplier.addCatalogItemToCatalogIncontract(itemId, catalogItemId, price);
                return;
            }
        }
        throw new Exception("supplier do not found");
    }

    public void deleteCatalogItemFromCatlogInContract(int supplierId, int catalogItemId) throws Exception {
        List<Supplier> suppliers = Data.getSuppliers();
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierId() == supplierId) {
                supplier.getContract().removItemFromCatalog(supplier.getCatalogItem(catalogItemId));
                return;
            }
        }
        throw new Exception("supplier do not found");
    }

    public void loadFirstSuppliers() {
        Supplier.loadFirstSuppliers();
    }

    public String getSuppliersInfo() {
        List<Supplier> suppliers = Data.getSuppliers();
        String s = "";
        for (Supplier sup : suppliers) {
            s = s + "\n" + sup.getSupplierInfo();
        }
        return s;
    }

    public String getCatalogPrinted(int supplierId) throws Exception {
        List<Supplier> suppliers = Data.getSuppliers();

        for (Supplier sup : suppliers) {
            if (sup.getSupplierId() == supplierId) {
               String s =  sup.getCatalogItemPrinted();
                return s;
            }
        }
        throw new Exception("supplier do not found");
    }

    public String getSuppliersId() {
        String s = "";
        for (Supplier sup : Data.getSuppliers()) {
            s += "\n" + sup.getSupplierId() + "\t" + sup.getName();
        }

        return s;
    }

    public void isExist(int supplierId) throws Exception {
        for (Supplier supplier : Data.getSuppliers()) {
            if (supplier.getSupplierId() == supplierId) return;
        }
        throw new Exception("supplier do not exist");
    }

    public void addConstDeliveryDays(String[] constDayDeli, int supplierId) throws Exception {
        if (constDayDeli.length<=0) throw new Exception("you must to give legal const day delivery ");
        getSupplierById(supplierId).addConstDayDeliveryDays(constDayDeli);
    }

    public String contactListPrinted(int supplierId) throws Exception {
        return getSupplierById(supplierId).contactListPrinted();
    }

    public void cleanRangeListItemFromMap(int supplierId, int catalogItemId) throws Exception {
        List<Supplier> suppliers = Data.getSuppliers();
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierId() == supplierId) {
                supplier.cleanRangeListItemFromMap(catalogItemId);
                return;
            }
        }
        throw new Exception("supplier do not found");
    }

    public String getBilingToPrint(int supplierId) throws Exception {
        return getSupplierById(supplierId).getBilingToPrint();
    }

    public String getConstDayDelivierToPrinted(int supplierId) throws Exception {
        return getSupplierById(supplierId).getConstDayDelivierToPrinted();
    }
}


