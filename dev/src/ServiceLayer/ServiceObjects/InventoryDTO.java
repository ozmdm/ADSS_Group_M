package ServiceLayer.ServiceObjects;

import bussinessLayer.InventoryPackage.Item;

import java.util.Map;

public class InventoryDTO {

    private Map<Integer, ItemDTO> itemsDTO;
    private int idCounter; //counts items

    public InventoryDTO(Map<Integer, ItemDTO> itemsDTO, int idCounter) {
        this.itemsDTO = itemsDTO;
        this.idCounter = idCounter;
    }

    public Map<Integer, ItemDTO> getItemsDTO() {
        return itemsDTO;
    }

    public void setItemsDTO(Map<Integer, ItemDTO> itemsDTO) {
        this.itemsDTO = itemsDTO;
    }

    public int getIdCounter() {
        return idCounter;
    }

    public void setIdCounter(int idCounter) {
        this.idCounter = idCounter;
    }
}
