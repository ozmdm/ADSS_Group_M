package DataAccessLaye;

import ServiceLayer.ServiceObjects.ContractDTO;
import ServiceLayer.ServiceObjects.DeliveryDaysDTO;

import java.sql.SQLException;

public interface IDeliveryDaysDAO {
   DeliveryDaysDTO findAllByContract(int contractId) throws SQLException;
    void insert(DeliveryDaysDTO deliveryDaysDTO, int contractId) throws SQLException;

    void deleteEveryThingByContract(int supplierId) throws SQLException;

    void insertEveryTingByContract(ContractDTO contractDTO) throws SQLException;
}
