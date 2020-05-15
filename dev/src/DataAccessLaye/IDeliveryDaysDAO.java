package DataAccessLaye;

import ServiceLayer.ServiceObjects.DeliveryDaysDTO;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface IDeliveryDaysDAO {
   DeliveryDaysDTO findAllByContract(int contractId) throws SQLException;
    void insert(DeliveryDaysDTO deliveryDaysDTO, int contractId) throws SQLException;
}
