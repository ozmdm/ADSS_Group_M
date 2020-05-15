package BranchPackage;

import java.util.HashMap;
import java.util.Map;

public class DamagedController {

    private Map<Integer, Integer> quantityById;

    public DamagedController() {
    }

    public DamagedController(Map<Integer, Integer> quantityById) {
        this.quantityById = new HashMap<>();
    }

    public Map<Integer, Integer> getQuantityById() {
        return quantityById;
    }

    public void setQuantityById(Map<Integer, Integer> quantityById) {
        this.quantityById = quantityById;
    }


}
