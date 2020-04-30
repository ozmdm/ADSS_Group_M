package ServiceLayer;

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

    public String AddSupplier(String supplierName, int supplierId, int bankAccount, String bilingOptions, boolean isDeliver) {
        try {
            supplierService.isExistForCreatSupp(supplierId);
            bussinessLayer.SupplierPackage.Supplier s = new bussinessLayer.SupplierPackage.Supplier(supplierName, supplierId, bankAccount, bussinessLayer.SupplierPackage.Supplier.bilingOption.valueOf(bilingOptions), isDeliver);
            Data.getSuppliers().add(s);
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    private String isExistForCreatSupp(int supplierId) {
        try {
            getSupplierById(supplierId);
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    public bussinessLayer.SupplierPackage.Supplier getSupplierById(int supplierId) throws Exception {
        List<bussinessLayer.SupplierPackage.Supplier> suppliers = Data.getSuppliers();
        if (suppliers.isEmpty()) throw new Exception("Supplier list is empty");
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getSupplierId() == supplierId) {
                return suppliers.get(i);
            }
        }
        throw new Exception("supplier doesnt exist");

    }

    public String updateSupplierBankAccount(int supplierId, int bankAccount) {
        try {
            bussinessLayer.SupplierPackage.Supplier s = getSupplierById(supplierId);
            s.setBankAccountNumber(bankAccount);
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }

    }


    ///
    public String updateSupplierName(int supplierId, String name) {

        try {
            bussinessLayer.SupplierPackage.Supplier s = getSupplierById(supplierId);
            s.setName(name);
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }


    }

    public String addContact(int supplierId, String firstName, String lastName, String phoneNum, String address) {
        try {
            bussinessLayer.SupplierPackage.Supplier s = getSupplierById(supplierId);
            s.setContact(firstName, lastName, phoneNum, address);
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    public String deleteContact(int supplierId, int contactId) {
        try {
            bussinessLayer.SupplierPackage.Supplier s = getSupplierById(supplierId);
            s.deleteContact(contactId);
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String updateContact(int supplierId, String[] updated, int contactId) {  /// update[t] == "" meaning not need to update else update the fields
        try {
            bussinessLayer.SupplierPackage.Supplier temp = getSupplierById(supplierId);
            for (int j = 0; j < temp.getContactsList().size(); j++) {
                if (temp.getContactsList().get(j).getContactId() == contactId) {
                    temp.getContactsList().get(j).setFirstName(updated[0]);
                    temp.getContactsList().get(j).setLastName(updated[1]);
                    temp.getContactsList().get(j).setPhonNumber(updated[2]);
                    temp.getContactsList().get(j).setAddress(updated[3]);

                }
            }
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String updateContractIsDeliver(int supplierId, boolean isDeliver) {
        try {
            bussinessLayer.SupplierPackage.Supplier supplier = getSupplierById(supplierId);
            supplier.setDeliverContrect(isDeliver);
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    public String updateBillingOptions(int supplierid, String bilingOption) {

        try {
            bussinessLayer.SupplierPackage.Supplier supplier = getSupplierById(supplierid);
            supplier.updateBilingOptions(bilingOption);
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public String UpdateMap(int supplierId, int catalogItemId, int min, int max, double priceafterDisc) { // TODO -> SUPPURET UPDATE RANGE AND DISCOUNT FOR A ITEM INT CATALOGITEMA
        try {
            bussinessLayer.SupplierPackage.Supplier s = getSupplierById(supplierId);
            s.updateMap(catalogItemId, min, max, priceafterDisc);
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    public String removeSupplier(int SupplierId) {
        try {
            bussinessLayer.SupplierPackage.Supplier supplier = getSupplierById(SupplierId);
            Data.getSuppliers().remove(supplier);
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }

    }


    public String addCatalogItemToCatalogInContract(int supplierId, int itemId, int catalogItemId, double price) {
        try {
            bussinessLayer.SupplierPackage.Supplier supplier = getSupplierById(supplierId);
            supplier.addCatalogItemToCatalogIncontract(itemId, catalogItemId, price);
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String deleteCatalogItemFromCatlogInContract(int supplierId, int catalogItemId) {
        try {
            bussinessLayer.SupplierPackage.Supplier s = getSupplierById(supplierId);
            s.removItemFromCatalog(s.getCatalogItem(catalogItemId));
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public void loadFirstSuppliers() {
        bussinessLayer.SupplierPackage.Supplier.loadFirstSuppliers();
    }

    public String getSuppliersInfo() {
        List<bussinessLayer.SupplierPackage.Supplier> suppliers = Data.getSuppliers();
        String s = "";
        for (bussinessLayer.SupplierPackage.Supplier sup : suppliers) {
            s = s + "\n" + sup.getSupplierInfo();
        }
        return s;
    }

    public String getCatalogPrinted(int supplierId) {
        try {
            bussinessLayer.SupplierPackage.Supplier s = getSupplierById(supplierId);
            return s.getCatalogItemPrinted();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String getSuppliersId() {
        String s = "";
        for (bussinessLayer.SupplierPackage.Supplier sup : Data.getSuppliers()) {
            s += "\n" + sup.getSupplierId() + "\t" + sup.getName();
        }

        return s;
    }

    public String isExist(int supplierId) {
        try {
            getSupplierById(supplierId);
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String addConstDeliveryDays(String[] constDayDeli, int supplierId) {
        try {

            getSupplierById(supplierId).addConstDayDeliveryDays(constDayDeli);
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String contactListPrinted(int supplierId) {
        try {

            return getSupplierById(supplierId).contactListPrinted();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String cleanRangeListItemFromMap(int supplierId, int catalogItemId) {
        try {
            bussinessLayer.SupplierPackage.Supplier supplier = getSupplierById(supplierId);
            supplier.cleanRangeListItemFromMap(catalogItemId);
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String getBilingToPrint(int supplierId) {
        try {

            return getSupplierById(supplierId).getBilingToPrint();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String getConstDayDelivierToPrinted(int supplierId) {
        try {
            return getSupplierById(supplierId).getConstDayDelivierToPrinted();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}


