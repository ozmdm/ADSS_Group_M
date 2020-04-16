package ServiceLayer;

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

}
