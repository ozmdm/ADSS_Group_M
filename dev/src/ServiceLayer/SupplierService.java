package ServiceLayer;

import bussinessLayer.CatalogItem;
import bussinessLayer.Supplier;

import java.util.ArrayList;
import java.util.List;

public class SupplierService {
    public List<Supplier> suppliers;

    public SupplierService() {
        suppliers = new ArrayList<>();
    }

    public Supplier getSupplier(Supplier supplier) {
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
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getSupplierId() == supplierId) {
                suppliers.get(i).setBankAccountNumber(bankAccount);
            }
        }
    }

    public void updateSupplierName(int supplierId, String name) {
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getSupplierId() == supplierId) {
                suppliers.get(i).setName(name);
            }
        }
    }

    public void addContect(int supplierId, String firstName, String lastName, String phoneNum, String address) {

        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getSupplierId() == supplierId) {
                suppliers.get(i).setContact(firstName, lastName, phoneNum, address);
            }
        }
    }

    public void deleteContact(int supplierId, int contactId) {

        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getSupplierId() == supplierId) {
                suppliers.get(i).deleteContact(contactId);
            }
        }
    }

    public void updateContact(int supplierId, String[] updated, int contactId) {  /// update[t] == "" meaning not need to update else update the fields
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
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierId() == supplierId) {
                supplier.setDeliverContrect(isDeliver);
                break;
            }
        }
    }

    public void addToMap(int supplierId, int catalogItemId, int minAmount, int maxAmount, double price) {
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierId() == supplierId) {
                supplier.addToMap(catalogItemId, minAmount, maxAmount, price);
                break;
            }
        }
    }

    public void DeleteFromMap(int supplierId, int catalogItemId) {
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierId() == supplierId) {
                supplier.deleteFromMap(catalogItemId);
                break;
            }
        }
    }

    public void addCatalogItemToCatalogInContract(int supplierId, int itemId, int catalogId, double price) {
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierId() == supplierId) {
                supplier.addCatalogItemToCatalogIncontract(itemId, catalogId, price);
                break;
            }
        }
    }

    public void deleteCatalogItemFromCatlogInContract(int supplierId, boolean isDeliver) {
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierId() == supplierId) {
                supplier.getContract().setDeliver(isDeliver);
                break;
            }
        }
    }

}


