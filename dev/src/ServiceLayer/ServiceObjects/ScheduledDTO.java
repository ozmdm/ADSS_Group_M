package ServiceLayer.ServiceObjects;

import java.time.LocalDateTime;

public class ScheduledDTO {

    private LocalDateTime day;
    private int supplierId;
    private int catalogItemId;
    private int amount;

    public ScheduledDTO(LocalDateTime day, int supplierId, int catalogItemId, int amount) {
        this.day = day;
        this.supplierId = supplierId;
        this.catalogItemId = catalogItemId;
        this.amount = amount;
    }

    public LocalDateTime getDay() {
        return day;
    }

    public void setDay(LocalDateTime day) {
        this.day = day;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getCatalogItemId() {
        return catalogItemId;
    }

    public void setCatalogItemId(int catalogItemId) {
        this.catalogItemId = catalogItemId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


}
