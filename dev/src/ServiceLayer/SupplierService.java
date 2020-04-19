package ServiceLayer;


import bussinessLayer.CatalogItem;
import bussinessLayer.Supplier;
import Data.Data;

import java.util.List;

public class SupplierService {
    static SupplierService supplierService;

    private SupplierService() {}

    public static SupplierService getInstance() {
        if (supplierService == null) {
            supplierService = new SupplierService();
        }
        return supplierService;
    }

    public void AddSupplier(String supplierName, int supplierId, int bankAccount, String bilingOptions, boolean isDeliver) {
        Supplier s = new Supplier(supplierName, supplierId, bankAccount, Supplier.bilingOption.valueOf(bilingOptions), isDeliver);
        Data.getSuppliers().add(s);
    }

    public Supplier getSupplier(Supplier supplier) {
    	List<Supplier> suppliers = Data.getSuppliers();
        Supplier ans = null;
        if (suppliers.isEmpty() || !suppliers.contains(supplier)) return ans;
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).equals(supplier)) {
                ans = suppliers.get(i);
                break;
            }
        }
        return ans;
    }

    public Supplier getSupplierById(int supplierId) {
    	List<Supplier> suppliers = Data.getSuppliers();
        Supplier ans = null;
        if (suppliers.isEmpty()) return ans;
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getSupplierId() == supplierId) {
                ans = suppliers.get(i);
                break;
            }
        }
        return ans;
    }

    public void updateSupplierBankAccount(int supplierId, int bankAccount) {
    	List<Supplier> suppliers = Data.getSuppliers();
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getSupplierId() == supplierId) {
                suppliers.get(i).setBankAccountNumber(bankAccount);
            }
        }
    }


    ///
    public void updateSupplierName(int supplierId, String name) {
    	List<Supplier> suppliers = Data.getSuppliers();
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getSupplierId() == supplierId) {
                suppliers.get(i).setName(name);
            }
        }
    }

    public void addContact(int supplierId, String firstName, String lastName, String phoneNum, String address) {
    	List<Supplier> suppliers = Data.getSuppliers();
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getSupplierId() == supplierId) {
                suppliers.get(i).setContact(firstName, lastName, phoneNum, address);
                break;
            }
        }
    }

    public void deleteContact(int supplierId, int contactId) {
    	List<Supplier> suppliers = Data.getSuppliers();
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getSupplierId() == supplierId) {
                suppliers.get(i).deleteContact(contactId);
                break;
            }
        }
    }

    public void updateContact(int supplierId, String[] updated, int contactId) {  /// update[t] == "" meaning not need to update else update the fields
    	List<Supplier> suppliers = Data.getSuppliers();
        Supplier temp = null;
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierId() == supplierId)
                temp = supplier;
            break;
        }
        {
            if (temp != null) {
                for (int j = 0; j < temp.getContactsList().size(); j++) {
                    if (temp.getContactsList().get(j).getContactId() == contactId) {
                        temp.getContactsList().get(j).setFirstName(updated[0]);
                        temp.getContactsList().get(j).setLastName(updated[1]);
                        temp.getContactsList().get(j).setPhonNumber(updated[2]);
                        temp.getContactsList().get(j).setAddress(updated[3]);
                        break;
                    }
                }
            }

        }
    }

    public void updateContractIsDeliver(int supplierId, boolean isDeliver) {
    	List<Supplier> suppliers = Data.getSuppliers();
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierId() == supplierId) {
                supplier.setDeliverContrect(isDeliver);
                break;
            }
        }
    }

    public void addToMap(int supplierId, int catalogItemId, int minAmount, int maxAmount, double price) {
    	List<Supplier> suppliers = Data.getSuppliers();
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierId() == supplierId) {
                supplier.addToMap(catalogItemId, minAmount, maxAmount, price);
                break;
            }
        }
    }

    public void updateBillingOptions(int supplierid, String bilingOption) {
    	List<Supplier> suppliers = Data.getSuppliers();
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierId() == supplierid) {
                supplier.updateBilingOptions(bilingOption);
                break;
            }
        }
    }

    public void DeleteFromMap(int supplierId, int catalogItemId) { // TODO -> SUPPURET UPDATE RANGE AND DISCOUNT FOR A ITEM INT CATALOGITEMA
    	List<Supplier> suppliers = Data.getSuppliers();
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierId() == supplierId) {
                supplier.deleteFromMap(catalogItemId);
                break;
            }
        }
    }

    public void removeSupplier(int SupplierId) {
    	List<Supplier> suppliers = Data.getSuppliers();
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierId() == SupplierId)
                suppliers.remove(supplier);
            break;
        }

    }

    public void addCatalogItemToCatalogInContract(int supplierId, int itemId, int catalogItemId, double price) {
    	List<Supplier> suppliers = Data.getSuppliers();
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierId() == supplierId) {
                supplier.addCatalogItemToCatalogIncontract(itemId, catalogItemId, price);
                break;
            }
        }
    }

    public void deleteCatalogItemFromCatlogInContract(int supplierId, int catalogItemId) {
    	List<Supplier> suppliers = Data.getSuppliers();
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierId() == supplierId) {
                CatalogItem c = supplier.getCatalogItem(catalogItemId);
                supplier.getContract().removItemFromCatalog(c);
                break;
            }
        }
    }

	public void loadFirstSuppliers() {
        Supplier.loadFirstSuppliers();
	}

    public String getSuppliersInfo() {
    	List<Supplier> suppliers = Data.getSuppliers();
        String s ="";
        for (Supplier sup : suppliers)
        {
           s = s+ "\n" +sup.getSupplierInfo();
        }
        return s;
    }
    public String getCatalogPrinted(int supplierId)
    {
    	List<Supplier> suppliers = Data.getSuppliers();
        String s="";
        for (Supplier sup : suppliers)
        {
            if(sup.getSupplierId() == supplierId)
            {
              s = s +  sup.getCatalogItemPrinted();
              break;
            }
        }
    return s;
    }

	public String getSuppliersId() {
		String s = "";
		for(Supplier sup : Data.getSuppliers()) {
			s += "\n" + sup.getSupplierId() + "\t" + sup.getName();
		}
		
		return s;
	}

    public boolean isExist(int supplierId) {
        for (Supplier supplier : Data.getSuppliers())
        {
            if (supplier.getSupplierId() == supplierId) return true;
        }
        return false;
    }

    public void addConstDeliveryDays(String[] constDayDeli,int supplierId) {
        Data.getSupplierById(supplierId).addConstDayDeliveryDays(constDayDeli);
    }
}


