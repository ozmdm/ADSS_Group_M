package bussinessLayer.BranchPackage;

import java.util.HashMap;
import java.util.Map;

public class BranchController {
    // static variable single_instance of type Singleton
    private static BranchController single_instance = null;

    // variable of type String
    private Map<Integer, Branch> branches;
    private int idCounter;

    // private constructor restricted to this class itself
    private BranchController() {
        this.branches = new HashMap<>();
        this.idCounter = 0;
    }

    // static method to create instance of Singleton class
    public static BranchController getInstance() {
        // To ensure only one instance is created
        if (single_instance == null) {
            single_instance = new BranchController();
        }
        return single_instance;
    }

    public int getIdCounter() {
        return idCounter;
    }

    public void setIdCounter(int idCounter) {
        this.idCounter = idCounter;
    }

    public int createBranch(String description) throws Exception {
        if (description.length() == 0) {
            throw new Exception("Cannot create branch without a description");
        }
        this.idCounter++;
        this.branches.put(idCounter, new Branch(idCounter, description));
        return this.idCounter;
    }

    public void deleteBranch(int branchId) throws Exception {
        if (!this.branches.keySet().contains(branchId)) {
            throw new Exception("Branch not found");
        }
        this.branches.remove(branchId);
    }



}
