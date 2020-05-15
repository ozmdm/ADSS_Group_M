package DataAccessLaye;

import ServiceLayer.ServiceObjects.ContractDTO;

import java.util.List;

public interface IContractDAO {
    ContractDTO find(int contractId);
    void insert(IContractDAO contractDAO);
    List<ContractDTO> findAll();

}
