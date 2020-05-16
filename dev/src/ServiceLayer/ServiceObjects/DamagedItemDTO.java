package ServiceLayer.ServiceObjects;

public class DamagedItemDTO {

    private int branchId;
    private int itemId;
    private int quantityDamaged;

    public DamagedItemDTO(int branchId, int itemId, int quantityDamaged) {
        this.branchId = branchId;
        this.itemId = itemId;
        this.quantityDamaged = quantityDamaged;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantityDamaged() {
        return quantityDamaged;
    }

    public void setQuantityDamaged(int quantityDamaged) {
        this.quantityDamaged = quantityDamaged;
    }
}
