package ServiceLayer.ServiceObjects;

import bussinessLayer.SupplierPackage.Supplier.bilingOption;

public class Supplier {

	private bilingOption billingOption;
	private String name;
	private int supplierId;

	public Supplier(int supplierId, String name, bilingOption billingOption) {
		this.supplierId = supplierId;
		this.name = name;
		this.billingOption = billingOption;
	}

	public bilingOption getBillingOption() {
		return billingOption;
	}

	public String getName() {
		return name;
	}

	public int getSupplierId() {
		return supplierId;
	}

	@Override
	public String toString() {
		return "\n" + supplierId + "\t" + name + "\t" + billingOption;
	}
    
}