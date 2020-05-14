package DataAccessLaye;

import ServiceLayer.ServiceObjects.ScheduledDTO;

import java.sql.Connection;
import java.util.List;

public class ScheduledDAOImpl implements  IScheduledOrderDAO{
    private Connection conn;

    public ScheduledDAOImpl(Connection conn)
    {
        this.conn = conn;
    }
    @Override
    public List<ScheduledDTO> getAllScheduled() {
        return null;
    }

    @Override
    public void deleteScheduledBySupplier(int supplierId) {

    }
}
