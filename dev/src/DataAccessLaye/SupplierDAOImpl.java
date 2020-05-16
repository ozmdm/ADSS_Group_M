package DataAccessLaye;

import ServiceLayer.ServiceObjects.*;
import bussinessLayer.SupplierPackage.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements ISupplierDAO {
    private Connection conn;
    private ContractDAOImpl contractDAO;
    private ContactDaoImpl contactDao;

    public SupplierDAOImpl(Connection conn) {
        this.conn = conn;
        contractDAO = new ContractDAOImpl(conn);
        contactDao = new ContactDaoImpl(conn);
    }


    @Override
    public SupplierDTO find(int supplierId) throws SQLException {
        String sql = "SELECT * "
                + "FROM Supplier WHERE supplierId = ? ";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, supplierId);
      /*  // set the value
        pstmt.set(1, catalogItemId,contractId);*/
        //
        ResultSet rs = pstmt.executeQuery();
        String supplierName = rs.getString("supplierName");
        int bankAccountNumber = rs.getInt("bankAccountNumber");
        String bilingOptions = rs.getString("bilingOptions");
        ContractDTO contractDTO = contractDAO.find(supplierId);
        List<ContactDTO> contactDTOS = contactDao.findAllBySupplier(supplierId);
        SupplierDTO res = new SupplierDTO(supplierId, supplierName, SupplierDTO.billingOption.valueOf(bilingOptions), bankAccountNumber, contractDTO, contactDTOS);
        return res;
    }

    @Override
    public List<SupplierDTO> findAll() throws SQLException {
        List<SupplierDTO> supplierDTOList = new ArrayList<>();

        String sql = "SELECT * "
                + "FROM Supplier";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            String supplierName = rs.getString("supplierName");
            int supplierId = rs.getInt("supplierId");
            int bankAccountNumber = rs.getInt("bankAccountNumber");
            String bilingOptions = rs.getString("bilingOptions");
            ContractDTO contractDTO = contractDAO.find(supplierId);
            List<ContactDTO> contactDTOS = contactDao.findAllBySupplier(supplierId);
            SupplierDTO res = new SupplierDTO(supplierId, supplierName, SupplierDTO.billingOption.valueOf(bilingOptions), bankAccountNumber, contractDTO, contactDTOS);
            supplierDTOList.add(res);
        }
        return supplierDTOList;

    }

    @Override
    public void insertSupplier(bussinessLayer.SupplierPackage.Supplier supplier) throws SQLException {
        contractDAO.insert(new ContractDTO(supplier.getSupplierId(), supplier.getContract().getConstDayDeliviery(), supplier.getContract().isDeliver()));
        for (Contact c : supplier.getContactsList()) {
            contactDao.insert(new ContactDTO( c.getFirstName(), c.getLastName(), c.getPhonNumber(), c.getAddress()), supplier.getSupplierId());
        }
        String sql = "INSERT INTO Suppliers(supplierName,supplierId,bankAccountNumber,bilingOptions) VALUES(?,?,?,?)";


        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, supplier.getName());
        pstmt.setInt(2, supplier.getSupplierId());
        pstmt.setInt(3, supplier.getBankAccountNumber());
        pstmt.setString(4, supplier.getBilingOption().name());
        pstmt.executeUpdate();
    }
}
