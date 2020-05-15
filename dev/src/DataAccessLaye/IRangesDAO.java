package DataAccessLaye;

import ServiceLayer.ServiceObjects.RangeDTO;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public interface IRangesDAO {
    HashMap<Integer, List<Pair<RangeDTO,Double>>> findAll(int contractId);
    List<Pair<RangeDTO,Double>> find(int contractId, int catalogItemId);
    void insert(RangeDTO rangeDTO);
    void deleteAllRangesByContractId(int contractId, int catalogItemId );
}
