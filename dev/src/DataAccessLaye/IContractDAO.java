package DataAccessLaye;

import ServiceLayer.ServiceObjects.ContractDTO;

public interface IContractDAO {
    ContractDTO getContract(int contractId);
    void updateContract();
    void insertContract();

}
