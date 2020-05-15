package DataAccessLaye;

import ServiceLayer.ServiceObjects.DeliveryDaysDTO;

import java.sql.SQLException;

public interface IDeliveryDaysDAO {
   DeliveryDaysDTO findAllByContract(int contractId) throws SQLException;
    void insert(DeliveryDaysDTO deliveryDaysDTO, int contractId) throws SQLException;
}
