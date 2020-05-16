package ServiceLayer;

import Data.Data;
import ServiceLayer.ServiceObjects.CatalogDTO;
import ServiceLayer.ServiceObjects.CatalogItemDTO;
import ServiceLayer.ServiceObjects.ContactDTO;
import ServiceLayer.ServiceObjects.ContractDTO;
import ServiceLayer.ServiceObjects.SupplierDTO;
import bussinessLayer.SupplierPackage.SupplierController;

import java.util.ArrayList;
import java.util.List;

public class SupplierService implements ISupplierService {

	static SupplierService supplierService;
	private SupplierController supController;

	private SupplierService() {
		supController = new SupplierController();
	}

	public static SupplierService getInstance() {
		if (supplierService == null) {
			supplierService = new SupplierService();
		}
		return supplierService;
	}

	public Response AddSupplier(String supplierName, int supplierId, int bankAccount, String bilingOptions, boolean isDeliver) {
		try {
			supController.AddSupplier(supplierName, supplierId, bankAccount, bilingOptions, isDeliver);
			return new Response();
		} catch (Exception e) {
			return new Response(e.getMessage());
		}
	}

	public Response updateSupplierBankAccount(int supplierId, int bankAccount) {
		try {
			supController.updateSupplierBankAccount(supplierId, bankAccount);
			return new Response();
		} catch (Exception e) {
			return new Response(e.getMessage());
		}

	}


	///
	public Response updateSupplierName(int supplierId, String name) {
		try {
			supController.updateSupplierName(supplierId, name);
			return new Response();
		} catch (Exception e) {
			return new Response(e.getMessage());
		}


	}

	public Response addContact(int supplierId, String firstName, String lastName, String phoneNum, String address) {
		try {
			supController.addContact(supplierId, firstName, lastName, phoneNum, address);
			return new Response();
		} catch (Exception e) {
			return new Response(e.getMessage());
		}
	}


	public Response deleteContact(int supplierId, String phoneNumber) {
		try {
			supController.deleteContact(supplierId, phoneNumber);
			return new Response();
		} catch (Exception e) {
			return new Response(e.getMessage());
		}
	}

	public Response updateContact(int supplierId, String[] updated,String phoneNumber) {  /// update[t] == "" meaning not need to update else update the fields
		try {
			supController.updateContact(supplierId, updated, phoneNumber);
			return new Response();
		} catch (Exception e) {
			return new Response(e.getMessage());
		}
	}

	public Response updateContractIsDeliver(int supplierId, boolean isDeliver) {
		try {
			supController.updateContractIsDeliver(supplierId, isDeliver);
			return new Response();
		} catch (Exception e) {
			return new Response(e.getMessage());
		}
	}


	public Response updateBillingOptions(int supplierId, String bilingOption) {

		try {
			supController.updateBillingOptions(supplierId, bilingOption);
			return new Response();
		} catch (Exception e) {
			return new Response(e.getMessage());
		}

	}

	public Response UpdateMap(int supplierId, int catalogItemId, int min, int max, double priceafterDisc) { // TODO -> SUPPURET UPDATE RANGE AND DISCOUNT FOR A ITEM INT CATALOGITEMA
		try {
			supController.UpdateMap(supplierId, catalogItemId, min, max, priceafterDisc);
			return new Response();
		} catch (Exception e) {
			return new Response(e.getMessage());
		}
	}


	public Response removeSupplier(int SupplierId) {
		try {
			supController.removeSupplier(SupplierId);
			return new Response();
		} catch (Exception e) {
			return new Response(e.getMessage());
		}

	}


	public Response addCatalogItemToCatalogInContract(int supplierId, int itemId, int catalogItemId, double price) {
		try {
			supController.addCatalogItemToCatalogInContract(supplierId, itemId, catalogItemId, price);
			return new Response();
		} catch (Exception e) {
			return new Response(e.getMessage());
		}
	}

	public Response deleteCatalogItemFromCatlogInContract(int supplierId, int catalogItemId) {
		try {
			supController.deleteCatalogItemFromCatlogInContract(supplierId, catalogItemId);
			return new Response();
		} catch (Exception e) {
			return new Response(e.getMessage());
		}
	}

	public void loadFirstSuppliers() {
		bussinessLayer.SupplierPackage.Supplier.loadFirstSuppliers();
	}

	public ResponseT<List<SupplierDTO>> getSuppliersInfo() {
		try {
			return new ResponseT<List<SupplierDTO>>(supController.getSuppliersInfo());
		}catch(Exception e) {
			return new ResponseT<List<SupplierDTO>>(e.getMessage());
		}

	}

	public ResponseT<CatalogDTO> getCatalog(int supplierId) {
		try {
			bussinessLayer.SupplierPackage.Catalog catalog = supController.getCatalog(supplierId);
			return new ResponseT<CatalogDTO>(new CatalogDTO(convertToServiceCatalogItem(catalog.getItems())));
		} catch (Exception e) {
			return new ResponseT<CatalogDTO>(e.getMessage());
		}
	}

	private List<CatalogItemDTO> convertToServiceCatalogItem(List<bussinessLayer.SupplierPackage.CatalogItem> items) {
		List<CatalogItemDTO> list = new ArrayList<CatalogItemDTO>();
		for(bussinessLayer.SupplierPackage.CatalogItem it : items) {
			list.add(new CatalogItemDTO(it.getCatalogItemId(), it.getDescription(), it.getPrice(), it.getItemId()));
		}
		return list;
	}

	public Response addConstDeliveryDays(String[] constDayDeli, int supplierId) {
		try {
			supController.addConstDeliveryDays(constDayDeli, supplierId);
			return new Response();
		} catch (Exception e) {
			return new Response(e.getMessage());
		}
	}

	public ResponseT<List<ContactDTO>> getContactsList(int supplierId) {
		try {
			return new ResponseT<List<ContactDTO>>(converToServiceContacts(supController.getContactsList(supplierId)));
		} catch (Exception e) {
			return new ResponseT<List<ContactDTO>>(e.getMessage());
		}
	}

	private List<ContactDTO> converToServiceContacts(List<bussinessLayer.SupplierPackage.Contact> contacts) {
		List<ContactDTO> list = new ArrayList<ContactDTO>();
		for(bussinessLayer.SupplierPackage.Contact contact : contacts) {
			list.add(new ContactDTO( contact.getFirstName(), contact.getLastName(), contact.getPhonNumber(), contact.getAddress()));
		}
		return list;

	}

	public String cleanRangeListItemFromMap(int supplierId, int catalogItemId) {
		try {
			bussinessLayer.SupplierPackage.Supplier supplier = Data.getSupplierById(supplierId);
			supplier.cleanRangeListItemFromMap(catalogItemId);
			return "Done";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public ResponseT<ContractDTO> getContractDetails(int supplierId) {
		try {
			return new ResponseT<ContractDTO>(supController.getContractDetails(supplierId));
		} catch (Exception e) {
			return new ResponseT<ContractDTO>(e.getMessage());
		}
	}

	public Response isSupplierExist(int supplierId) {
		try {
			supController.isSupplierExist(supplierId);
			return new Response();
		}catch(Exception e) {
			return new Response(e.getMessage());
		}
	}

	public ResponseT<SupplierDTO> getSupplierInfo(int supplierId) {
		try {
			return new ResponseT<SupplierDTO>(supController.getSupplierInfo(supplierId));
		}catch(Exception e) {
			return new ResponseT<SupplierDTO>(e.getMessage());
		}
	}
}


