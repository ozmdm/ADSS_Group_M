package DataAccessLaye;

import ServiceLayer.ServiceObjects.ScheduledDTO;

import java.util.List;

public interface IScheduledOrderDAO {
    List<ScheduledDTO> getAllScheduled();

    void deleteScheduledBySupplier(int supplierId);
}
