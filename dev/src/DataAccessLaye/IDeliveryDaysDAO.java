package DataAccessLaye;

import ServiceLayer.ServiceObjects.DeliveryDaysDTO;

import java.util.List;

public interface IDeliveryDaysDAO {
    void updateDeliveryDaysByContract(int contractId);
    List<DeliveryDaysDTO> getAllDeliveryDaysByContract(int contractId);
    void insertDeliveryDays();
}
