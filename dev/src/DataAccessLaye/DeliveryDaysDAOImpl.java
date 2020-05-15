package DataAccessLaye;

import ServiceLayer.ServiceObjects.DeliveryDaysDTO;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

public class DeliveryDaysDAOImpl implements IDeliveryDaysDAO {
    private Connection conn;

    public DeliveryDaysDAOImpl(Connection conn)
    {
        this. conn = conn;
    }

    @Override
    public DeliveryDaysDTO find(int contractId, LocalDateTime day) {
        return null;
    }

    @Override
    public List<DeliveryDaysDTO> findAll() {
        return null;
    }

    @Override
    public void insert(DeliveryDaysDTO deliveryDaysDTO) {

    }
}
