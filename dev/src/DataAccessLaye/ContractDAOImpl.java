package DataAccessLaye;

import ServiceLayer.ServiceObjects.ContractDTO;

import java.sql.Connection;
import java.util.List;

public class ContractDAOImpl implements IContractDAO {

    private Connection conn;
    public ContractDAOImpl (Connection conn)
    {
        this.conn = conn;
    }

    @Override
    public ContractDTO find(int contractId) {
        return null;
    }

    @Override
    public void insert(IContractDAO contractDAO) {

    }

    @Override
    public List<ContractDTO> findAll() {
        return null;
    }
}
