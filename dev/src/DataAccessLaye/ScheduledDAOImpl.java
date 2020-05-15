package DataAccessLaye;

import ServiceLayer.ServiceObjects.ScheduledDTO;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

public class ScheduledDAOImpl implements  IScheduledOrderDAO{
    private Connection conn;

    public ScheduledDAOImpl(Connection conn)
    {
        this.conn = conn;
    }

    @Override
    public List<ScheduledDTO> findAll(int supplierId) {
        return null;
    }

    @Override
    public ScheduledDTO find(int supplierId, int catalogItemId, LocalDateTime day) {
        return null;
    }

    @Override
    public void insert(ScheduledDTO scheduledDTO) {

    }

    @Override
    public void deleteScheduledBySupplier(int supplierId) {

    }
}
