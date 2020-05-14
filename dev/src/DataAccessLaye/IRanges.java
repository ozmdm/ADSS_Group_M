package DataAccessLaye;

import ServiceLayer.ServiceObjects.RangeDTO;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public interface IRanges {
    HashMap<Integer, List<Pair<RangeDTO,Double>>> getAllRangesByContract(int contractId);
    List<Pair<RangeDTO,Double>> getAllRangeForCatalogItemId(int contractId, int catalogItemId);
    void deleteAllRangesByContractId(int contractId, int catalogItemId );
}
