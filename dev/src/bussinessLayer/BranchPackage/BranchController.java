package bussinessLayer.BranchPackage;

import DataAccessLaye.Repo;
import ServiceLayer.ServiceObjects.BranchDTO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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

    public int getIdCounter() throws SQLException {
        //return idCounter;
        return Repo.getInstance().getAllBranches().size();
    }

    public void setIdCounter(int idCounter) {
        this.idCounter = idCounter;
        //Repo.getInstance().
    }

    public int createBranch(String description) throws Exception {
        if (description.length() == 0) {
            throw new Exception("Cannot create branch without a description");
        }
        this.idCounter++;
//        this.branches.put(idCounter, new Branch(idCounter, description));
        Branch branch = new Branch(idCounter, description);
        Repo.getInstance().createBranch(branch.convertToDTO());
        return this.idCounter;
    }

    public void editBranchDescription(int branchId, String description) throws Exception {
        if (description.length() == 0) {
            throw new Exception("Cannot change to empty description");
        }
        //this.branches.get(branchId).setDescription(description);
        Repo.getInstance().updateBranchDescription(branchId, description);
    }


    //**NOT YET IMPLEMENTED WITH REPO
    public void deleteBranch(int branchId) throws Exception {
        if (!this.branches.keySet().contains(branchId)) {
            throw new Exception("Branch not found");
        }
        this.branches.remove(branchId);
    }

    public Map<Integer, Branch> getBranches() throws SQLException {
        List<BranchDTO> list = Repo.getInstance().getAllBranches();
        Map<Integer, Branch> map = new HashMap<>();
        for (BranchDTO branchDTO:list) {
            map.put(branchDTO.getId(), branchDTO.convertFromDTO());
        }
        return map;
        //return branches;

    }

    public void setBranches(Map<Integer, Branch> branches) {
        this.branches = branches;
    }
}
