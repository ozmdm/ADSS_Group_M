package DataAccessLaye;

import ServiceLayer.ServiceObjects.ContractDTO;

import java.sql.Connection;

public class ContractDAOImpl implements IContractDAO {

    private Connection conn;
    public ContractDAOImpl (Connection conn)
    {
        this.conn = conn;
    }
    @Override
    public ContractDTO getContract(int contractId) {
        return null;
    }

    @Override
    public void updateContract() {

    }

    @Override
    public void insertContract() {

    }
}
