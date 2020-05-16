package bussinessLayer.SupplierPackage;

import java.util.ArrayList;
import java.util.List;

import Data.Data;
import ServiceLayer.ServiceObjects.ContactDTO;
import ServiceLayer.ServiceObjects.ContractDTO;
import ServiceLayer.ServiceObjects.SupplierDTO;

public class SupplierController {

	public void AddSupplier(String supplierName, int supplierId, int bankAccount, String bilingOptions, boolean isDeliver) throws Exception {
		try {
			Data.getSupplierById(supplierId);
		}catch(Exception e) {
			bussinessLayer.SupplierPackage.Supplier s = new bussinessLayer.SupplierPackage.Supplier(supplierName, supplierId, bankAccount, Supplier.billingOptions.valueOf(bilingOptions), isDeliver);
			Data.getSuppliers().add(s);
			return;
		}
		
		throw new Exception("Supplier Already Exists");
		
	}

	public void updateSupplierBankAccount(int supplierId, int bankAccount)throws Exception {
		bussinessLayer.SupplierPackage.Supplier s = Data.getSupplierById(supplierId);
		s.setBankAccountNumber(bankAccount);
	}

	public void updateSupplierName(int supplierId, String name) throws Exception {
		bussinessLayer.SupplierPackage.Supplier s = Data.getSupplierById(supplierId);
		s.setName(name);

	}

	public void addContact(int supplierId, String firstName, String lastName, String phoneNum, String address) throws Exception {
		bussinessLayer.SupplierPackage.Supplier s = Data.getSupplierById(supplierId);
		s.setContact(firstName, lastName, phoneNum, address);
	}

	public void deleteContact(int supplierId, int contactId) throws Exception {
		bussinessLayer.SupplierPackage.Supplier s = Data.getSupplierById(supplierId);
		s.deleteContact(contactId);
	}

	public void updateContact(int supplierId, String[] updated, int contactId) throws Exception {
		bussinessLayer.SupplierPackage.Supplier temp = Data.getSupplierById(supplierId);
		for (int j = 0; j < temp.getContactsList().size(); j++) {
			if (temp.getContactsList().get(j).getContactId() == contactId) {
				temp.getContactsList().get(j).setFirstName(updated[0]);
				temp.getContactsList().get(j).setLastName(updated[1]);
				temp.getContactsList().get(j).setPhonNumber(updated[2]);
				temp.getContactsList().get(j).setAddress(updated[3]);

			}
		}

	}

	public void updateContractIsDeliver(int supplierId, boolean isDeliver) throws Exception {
		bussinessLayer.SupplierPackage.Supplier supplier = Data.getSupplierById(supplierId);
		supplier.setDeliverContrect(isDeliver);

	}

	public void updateBillingOptions(int supplierId, String bilingOption) throws Exception {
		bussinessLayer.SupplierPackage.Supplier supplier = Data.getSupplierById(supplierId);
		supplier.updateBilingOptions(bilingOption);

	}

	public void UpdateMap(int supplierId, int catalogItemId, int min, int max, double priceafterDisc) throws Exception {
		bussinessLayer.SupplierPackage.Supplier s = Data.getSupplierById(supplierId);
		s.updateMap(catalogItemId, min, max, priceafterDisc);

	}

	public void removeSupplier(int supplierId) throws Exception {
		bussinessLayer.SupplierPackage.Supplier supplier = Data.getSupplierById(supplierId);
		Data.getSuppliers().remove(supplier);

	}

	public void addCatalogItemToCatalogInContract(int supplierId, int itemId, int catalogItemId, double price) throws Exception {
		bussinessLayer.SupplierPackage.Supplier supplier = Data.getSupplierById(supplierId);
		supplier.addCatalogItemToCatalogIncontract(itemId, catalogItemId, price);

	}

	public void deleteCatalogItemFromCatlogInContract(int supplierId, int catalogItemId) throws Exception {
		bussinessLayer.SupplierPackage.Supplier s = Data.getSupplierById(supplierId);
		s.removItemFromCatalog(s.getCatalogItem(catalogItemId));

	}

	public List<ServiceLayer.ServiceObjects.SupplierDTO> getSuppliersInfo() {
		List<bussinessLayer.SupplierPackage.Supplier> suppliers = Data.getSuppliers();
		List<ServiceLayer.ServiceObjects.SupplierDTO> servSup = new ArrayList<ServiceLayer.ServiceObjects.SupplierDTO>();
		for (bussinessLayer.SupplierPackage.Supplier sup : suppliers) {
			ContractDTO contractDTO = new ContractDTO(sup.getSupplierId(),sup.getContract().getConstDayDeliviery(),sup.getContract().isDeliver());
			List<ContactDTO> contactDTOS = new ArrayList<>();
			for (Contact c : sup.getContactsList())
			{
				ContactDTO contactDTO = new ContactDTO(c.getContactId(),c.getFirstName(),c.getLastName(),c.getPhonNumber(),c.getAddress());
				contactDTOS.add(contactDTO);
			}
			String billingOptions = sup.getBilingOption().name();

			servSup.add(new ServiceLayer.ServiceObjects.SupplierDTO(sup.getSupplierId(), sup.getName(),SupplierDTO.billingOption.valueOf(billingOptions),sup.getBankAccountNumber(),contractDTO,contactDTOS));
		}
		return servSup;
	}

	public Catalog getCatalog(int supplierId) throws Exception {
		bussinessLayer.SupplierPackage.Supplier s = Data.getSupplierById(supplierId);
		return s.getCatalog();
	}

	public void addConstDeliveryDays(String[] constDayDeli, int supplierId) throws Exception {
		Data.getSupplierById(supplierId).addConstDayDeliveryDays(constDayDeli);
	}

	public List<bussinessLayer.SupplierPackage.Contact> getContactsList(int supplierId) throws Exception {
		return Data.getSupplierById(supplierId).getContactsList();
	}

	public ServiceLayer.ServiceObjects.ContractDTO getContractDetails(int supplierId) throws Exception {
		Contract contract = Data.getSupplierById(supplierId).getContract();
		return new ServiceLayer.ServiceObjects.ContractDTO(contract.getSupplierId(), contract.getConstDayDeliviery(), contract.isDeliver());
	}

	public void isSupplierExist(String supplierId) throws Exception {
		int supplierIdInteger;
		try{supplierIdInteger = Integer.valueOf(supplierId);}catch(Exception e){throw new Exception("Invalid supplierId");}
		Data.getSupplierById(supplierIdInteger);
		
	}

	public ServiceLayer.ServiceObjects.SupplierDTO getSupplierInfo(int supplierId) throws Exception {
		Supplier supplier = Data.getSupplierById(supplierId);
		ContractDTO contractDTO = new ContractDTO(supplier.getSupplierId(),supplier.getContract().getConstDayDeliviery(),supplier.getContract().isDeliver());
		List<ContactDTO> contactDTOS = new ArrayList<>();
		for (Contact c : supplier.getContactsList())
		{
			ContactDTO contactDTO = new ContactDTO(c.getContactId(),c.getFirstName(),c.getLastName(),c.getPhonNumber(),c.getAddress());
			contactDTOS.add(contactDTO);
		}
		String billingOptions = supplier.getBilingOption().name();

		return new ServiceLayer.ServiceObjects.SupplierDTO(supplier.getSupplierId(), supplier.getName(), SupplierDTO.billingOption.valueOf(billingOptions),supplier.getBankAccountNumber(),contractDTO,contactDTOS);
	}

}
