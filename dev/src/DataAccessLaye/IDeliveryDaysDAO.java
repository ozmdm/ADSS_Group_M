package DataAccessLaye;

import ServiceLayer.ServiceObjects.DeliveryDaysDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface IDeliveryDaysDAO {
    DeliveryDaysDTO find(int contractId, LocalDateTime day);
    List<DeliveryDaysDTO> findAll();
    void insert(DeliveryDaysDTO deliveryDaysDTO);
}
