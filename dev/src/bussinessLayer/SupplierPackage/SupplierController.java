package bussinessLayer.SupplierPackage;

import java.sql.SQLException;
import java.util.List;

import DataAccessLaye.Repo;

public class SupplierController {

		/**
	 * Update Supplier using his whole structure
	 * 
	 * @param s Supplier object transfered with all the info needed
	 * @throws SQLException
	 */
	private void updateSupplier(Supplier s) throws SQLException {
		Repo.getInstance().updateSupplier(s.getName(), s.getSupplierId(), s.getBankAccountNumber(), s.getBilingOption().name());
	}

	/**
	 * Get buissness supplier by ID
	 * @param supplierId
	 * @return
	 * @throws Exception
	 */
	public Supplier getSupplierById(int supplierId) throws Exception {
		return new Supplier(getSupplierInfo(supplierId));
	}

	public void AddSupplier(String supplierName, int supplierId, int bankAccount, String bilingOptions, boolean isDeliver) throws Exception {
		getSupplierInfo(supplierId);
		bussinessLayer.SupplierPackage.Supplier s = new bussinessLayer.SupplierPackage.Supplier(supplierName, supplierId, bankAccount, Supplier.BillingOptions.valueOf(bilingOptions), isDeliver);
		Repo.getInstance().insertSupplier(s);
	}

	public void updateSupplierBankAccount(int supplierId, int bankAccount)throws Exception {
		bussinessLayer.SupplierPackage.Supplier s = getSupplierById(supplierId);
		s.setBankAccountNumber(bankAccount);
		updateSupplier(s);
	}

	public void updateSupplierName(int supplierId, String name) throws Exception {
		bussinessLayer.SupplierPackage.Supplier s = getSupplierById(supplierId);
		s.setName(name);
		updateSupplier(s);

	}

	public void addContact(int supplierId, String firstName, String lastName, String phoneNum, String address) throws Exception {
		bussinessLayer.SupplierPackage.Supplier s = getSupplierById(supplierId);
		s.setContact(firstName, lastName, phoneNum, address);
		Repo.getInstance().insertContact(supplierId, phoneNum, firstName, lastName, address);
	}

	public void deleteContact(int supplierId, String phoneNumber) throws Exception {
		getSupplierById(supplierId);// TODO SUPPLIER ID NOT NECESSARY?!
		Repo.getInstance().deleteContact(phoneNumber);
	}

	public void updateContact(int supplierId, String[] updated, String phoneNumber) throws Exception {
		Repo.getInstance().updateContact(phoneNumber);
		
	}

	public void updateContractIsDeliver(int supplierId, boolean isDeliver) throws Exception {
		Repo.getInstance().updateContract(supplierId, isDeliver);
	}

	public void updateBillingOptions(int supplierId, String bilingOption) throws Exception {
		bussinessLayer.SupplierPackage.Supplier supplier = getSupplierById(supplierId);
		supplier.updateBilingOptions(bilingOption);

	}

	public void UpdateMap(int supplierId, int catalogItemId, int min, int max, double priceafterDisc) throws Exception {
		bussinessLayer.SupplierPackage.Supplier s = getSupplierById(supplierId);
		s.updateMap(catalogItemId, min, max, priceafterDisc);

	}

	public void removeSupplier(int supplierId) throws Exception {
		bussinessLayer.SupplierPackage.Supplier supplier = getSupplierById(supplierId);
		Data.getSuppliers().remove(supplier);

	}

	public void addCatalogItemToCatalogInContract(int supplierId, int itemId, int catalogItemId, double price) throws Exception {
		bussinessLayer.SupplierPackage.Supplier supplier = getSupplierById(supplierId);
		supplier.addCatalogItemToCatalogIncontract(itemId, catalogItemId, price, Repo.getInstance().getItemDescription(itemId));

	}

	public void deleteCatalogItemFromCatlogInContract(int supplierId, int catalogItemId) throws Exception {
		bussinessLayer.SupplierPackage.Supplier s = getSupplierById(supplierId);
		s.removItemFromCatalog(s.getCatalogItem(catalogItemId));

	}

	public List<ServiceLayer.ServiceObjects.SupplierDTO> getSuppliersInfo() throws SQLException {
		return Repo.getInstance().getAllSuppliers();
	}

	public Catalog getCatalog(int supplierId) throws Exception {
		bussinessLayer.SupplierPackage.Supplier s = getSupplierById(supplierId);
		return s.getCatalog();
	}

	public void addConstDeliveryDays(String[] constDayDeli, int supplierId) throws Exception {
		getSupplierById(supplierId).addConstDayDeliveryDays(constDayDeli);
	}

	public List<bussinessLayer.SupplierPackage.Contact> getContactsList(int supplierId) throws Exception {
		return getSupplierById(supplierId).getContactsList();
	}

	public ServiceLayer.ServiceObjects.ContractDTO getContractDetails(int supplierId) throws Exception {
		return Repo.getInstance().getContract(supplierId);
	}

	public void isSupplierExist(String supplierId) throws Exception {
		getSupplierInfo(Integer.valueOf(supplierId));
	}

	public ServiceLayer.ServiceObjects.SupplierDTO getSupplierInfo(int supplierId) throws Exception {
		return Repo.getInstance().getSupplierById(supplierId);
	}

}
