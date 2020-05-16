package bussinessLayer.BranchPackage;

import DataAccessLaye.Repo;

import java.util.HashMap;
import java.util.Map;

public class DamagedController {

    private Map<Integer, Integer> quantityById;

    public DamagedController() {
       //for example: List<ItemDTO> bb=  Repo.getInstance().getAllDamagedItemsByBranch();
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
