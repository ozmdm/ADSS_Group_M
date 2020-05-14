package DataAccessLaye;

import ServiceLayer.ServiceObjects.DeliveryDaysDTO;

import java.sql.Connection;
import java.util.List;

public class DeliveryDaysDAOImpl implements IDeliveryDaysDAO {
    private Connection conn;

    public DeliveryDaysDAOImpl(Connection conn)
    {
        this. conn = conn;
    }
    @Override
    public void updateDeliveryDaysByContract(int contractId) {

    }

    @Override
    public List<DeliveryDaysDTO> getAllDeliveryDaysByContract(int contractId) {
        return null;
    }

    @Override
    public void insertDeliveryDays() {

    }
}
