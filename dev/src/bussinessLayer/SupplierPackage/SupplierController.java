package bussinessLayer.SupplierPackage;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import DataAccessLaye.Repo;
import ServiceLayer.ServiceObjects.CatalogDTO;
import ServiceLayer.ServiceObjects.CatalogItemDTO;
import ServiceLayer.ServiceObjects.ContactDTO;
import ServiceLayer.ServiceObjects.ContractDTO;
import ServiceLayer.ServiceObjects.DeliveryDaysDTO;
import ServiceLayer.ServiceObjects.RangeDTO;

public class SupplierController {

		/**
	 * Update Supplier using his whole structure
	 * 
	 * @param s Supplier object transfered with all the info needed
	 * @throws SQLException
	 */
	private void updateSupplier(Supplier s) throws SQLException {
		Repo.getInstance().updateSupplier(s.convertToDTO());
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
		ContactDTO contactDTO = new ContactDTO(firstName,lastName,phoneNum,address);
		Repo.getInstance().insertContact(supplierId, contactDTO);
	}

	public void deleteContact(int supplierId, String phoneNumber) throws Exception {
/*
		getSupplierById(supplierId);// TODO SUPPLIER ID NOT NECESSARY?!
*/
		Repo.getInstance().deleteContact(phoneNumber,supplierId);
	}

	public void updateContact(int supplierId, String[] updated, String phoneNumber) throws Exception {
		ContactDTO contactDTO = new ContactDTO(updated[0],updated[1],updated[2],updated[3]);
		Repo.getInstance().updateContact(phoneNumber,supplierId,contactDTO);
		
	}

	public void updateContractIsDeliver(int supplierId, boolean isDeliver) throws Exception {
		Supplier supplier = getSupplierById(supplierId);
		supplier.setDeliverContrect(isDeliver);
		ContractDTO contractDTO =  supplier.convertToDTO().getContractDTO();
		Repo.getInstance().updateContract(contractDTO);
	}

	public void updateBillingOptions(int supplierId, String bilingOption) throws Exception {
		bussinessLayer.SupplierPackage.Supplier supplier = getSupplierById(supplierId);
		supplier.updateBilingOptions(bilingOption);
		updateSupplier(supplier);
	}

	public void UpdateMap(int supplierId, int catalogItemId, int min, int max, double priceafterDisc) throws Exception {
		bussinessLayer.SupplierPackage.Supplier s = getSupplierById(supplierId);
		s.updateMap(catalogItemId, min, max, priceafterDisc);
		Repo.getInstance().insertRange(new RangeDTO(min,max), supplierId, catalogItemId, priceafterDisc);

	}

	public void removeSupplier(int supplierId) throws Exception {
		Repo.getInstance().deleteSupplierById(supplierId);

	}

	public void addCatalogItemToCatalogInContract(int supplierId, int itemId, int catalogItemId, double price) throws Exception {
		Repo.getInstance().insertCatalogItem(new CatalogItemDTO(catalogItemId,Repo.getInstance().getItemDescription(itemId),price, itemId), supplierId);
	}

	public void deleteCatalogItemFromCatlogInContract(int supplierId, int catalogItemId) throws Exception {
		Repo.getInstance().deleteCatalogItem(supplierId, catalogItemId);
	}

	public List<ServiceLayer.ServiceObjects.SupplierDTO> getSuppliersInfo() throws SQLException {
		return Repo.getInstance().getAllSuppliers();
	}

	public CatalogDTO getCatalog(int supplierId) throws Exception {
		return Repo.getInstance().getCatalog(supplierId);
	}

	public void addConstDeliveryDays(String[] constDayDeli, int supplierId) throws Exception {
		List<DayOfWeek> list = new ArrayList<DayOfWeek>();
		for(int i=0; i < constDayDeli.length;i++ ) {
			list.add(DayOfWeek.valueOf(constDayDeli[i]));
		}
		Repo.getInstance().insertDeliveryDays(new DeliveryDaysDTO(list), supplierId);
	}

	public List<ContactDTO> getContactsList(int supplierId) throws Exception {
		return Repo.getInstance().getAllContactBySupplier(supplierId);
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

	public void cleanRangeListItemFromMap(int supplierId, int catalogItemId) throws Exception {
		Repo.getInstance().deleteAllRangesByContractId(supplierId, catalogItemId);
	}

}
