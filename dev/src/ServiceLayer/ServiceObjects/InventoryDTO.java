package ServiceLayer.ServiceObjects;

public class InventoryDTO {

    private int idCounter; //counts items

    public InventoryDTO(int idCounter) {
        this.idCounter = idCounter;
    }

    public int getIdCounter() {
        return idCounter;
    }

    public void setIdCounter(int idCounter) {
        this.idCounter = idCounter;
    }
}
