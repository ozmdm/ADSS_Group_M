package ServiceLayer.ServiceObjects;

import java.util.Map;

public class BranchDTO {

    private int id;
    private String description;
    private DamagedControllerDTO damagedControllerDTO;
    private InventoryDTO inventoryDTO;
    private Map<Integer, ItemStatusDTO> stockByItemId;

    public BranchDTO(int id, String description, DamagedControllerDTO damagedControllerDTO, InventoryDTO inventoryDTO, Map<Integer, ItemStatusDTO> stockByItemId) {
        this.id = id;
        this.description = description;
        this.damagedControllerDTO = damagedControllerDTO;
        this.inventoryDTO = inventoryDTO;
        this.stockByItemId = stockByItemId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DamagedControllerDTO getDamagedControllerDTO() {
        return damagedControllerDTO;
    }

    public void setDamagedControllerDTO(DamagedControllerDTO damagedControllerDTO) {
        this.damagedControllerDTO = damagedControllerDTO;
    }

    public InventoryDTO getInventoryDTO() {
        return inventoryDTO;
    }

    public void setInventoryDTO(InventoryDTO inventoryDTO) {
        this.inventoryDTO = inventoryDTO;
    }

    public Map<Integer, ItemStatusDTO> getStockByItemId() {
        return stockByItemId;
    }

    public void setStockByItemId(Map<Integer, ItemStatusDTO> stockByItemId) {
        this.stockByItemId = stockByItemId;
    }
}
