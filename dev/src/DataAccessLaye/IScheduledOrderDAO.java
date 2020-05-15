package DataAccessLaye;

import ServiceLayer.ServiceObjects.ScheduledDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface IScheduledOrderDAO {
    List<ScheduledDTO> findAll(int supplierId);
    ScheduledDTO find (int supplierId, int catalogItemId, LocalDateTime day);
    void insert(ScheduledDTO scheduledDTO);
    void deleteScheduledBySupplier(int supplierId);
}
