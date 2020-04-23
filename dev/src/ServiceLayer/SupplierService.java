package ServiceLayer;


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

    public String AddSupplier(String supplierName, int supplierId, int bankAccount, String bilingOptions, boolean isDeliver) {
        try {
            supplierService.isExistForCreatSupp(supplierId);
            Supplier s = new Supplier(supplierName, supplierId, bankAccount, Supplier.bilingOption.valueOf(bilingOptions), isDeliver);
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

    public String updateSupplierBankAccount(int supplierId, int bankAccount) {
        try {
            Supplier s = getSupplierById(supplierId);
            s.setBankAccountNumber(bankAccount);
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }

    }


    ///
    public String updateSupplierName(int supplierId, String name) {

        try {
            Supplier s = getSupplierById(supplierId);
            s.setName(name);
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }


    }

    public String addContact(int supplierId, String firstName, String lastName, String phoneNum, String address) {
        try {
            Supplier s = getSupplierById(supplierId);
            s.setContact(firstName, lastName, phoneNum, address);
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    public String deleteContact(int supplierId, int contactId) {
        try {
            Supplier s = getSupplierById(supplierId);
            s.deleteContact(contactId);
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String updateContact(int supplierId, String[] updated, int contactId) {  /// update[t] == "" meaning not need to update else update the fields
        try {
            Supplier temp = getSupplierById(supplierId);
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
            Supplier supplier = getSupplierById(supplierId);
            supplier.setDeliverContrect(isDeliver);
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }
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

    public String updateBillingOptions(int supplierid, String bilingOption) {

        try {
            Supplier supplier = getSupplierById(supplierid);
            supplier.updateBilingOptions(bilingOption);
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public String UpdateMap(int supplierId, int catalogItemId, int min, int max, double priceafterDisc) { // TODO -> SUPPURET UPDATE RANGE AND DISCOUNT FOR A ITEM INT CATALOGITEMA
        try {
            Supplier s = getSupplierById(supplierId);
            s.updateMap(catalogItemId, min, max, priceafterDisc);
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    public String removeSupplier(int SupplierId) {
        try {
            Supplier supplier = getSupplierById(SupplierId);
            Data.getSuppliers().remove(supplier);
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }

    }


    public String addCatalogItemToCatalogInContract(int supplierId, int itemId, int catalogItemId, double price) {
        try {
            Supplier supplier = getSupplierById(supplierId);
            supplier.addCatalogItemToCatalogIncontract(itemId, catalogItemId, price);
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String deleteCatalogItemFromCatlogInContract(int supplierId, int catalogItemId) {
        try {
            Supplier s = getSupplierById(supplierId);
            s.removItemFromCatalog(s.getCatalogItem(catalogItemId));
            return "Done";
        } catch (Exception e) {
            return e.getMessage();
        }
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

    public String getCatalogPrinted(int supplierId) {
        try {
            Supplier s = getSupplierById(supplierId);
            return s.getCatalogItemPrinted();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String getSuppliersId() {
        String s = "";
        for (Supplier sup : Data.getSuppliers()) {
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
            Supplier supplier = getSupplierById(supplierId);
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


