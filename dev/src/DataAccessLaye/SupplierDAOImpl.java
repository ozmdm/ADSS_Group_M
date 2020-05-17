package DataAccessLaye;

import ServiceLayer.ServiceObjects.*;
import bussinessLayer.SupplierPackage.Contact;
import bussinessLayer.SupplierPackage.Contract;
import bussinessLayer.SupplierPackage.Supplier.BillingOptions;
import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SupplierDAOImpl implements ISupplierDAO {
    private Connection conn;
    private ContractDAOImpl contractDAO;
    private ContactDaoImpl contactDao;
    private IRangesDAO rangesDAO;
    private ICatalogItemDAO catalogItemDAO;

    public SupplierDAOImpl(Connection conn) {
        this.conn = conn;
        contractDAO = new ContractDAOImpl(conn);
        contactDao = new ContactDaoImpl(conn);
        rangesDAO = new RangesDAODAOImpl(conn);
        catalogItemDAO = new CatalogItemDAOImpl(conn);
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
        SupplierDTO res = new SupplierDTO(supplierId, supplierName, BillingOptions.valueOf(bilingOptions), bankAccountNumber, contractDTO, contactDTOS);
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
            SupplierDTO res = new SupplierDTO(supplierId, supplierName, BillingOptions.valueOf(bilingOptions), bankAccountNumber, contractDTO, contactDTOS);
            supplierDTOList.add(res);
        }
        return supplierDTOList;

    }

    @Override
    public void insertSupplier(bussinessLayer.SupplierPackage.Supplier supplier) throws SQLException {
        HashMap<Integer,List<Pair<RangeDTO,Double>>> rangesDto = this.rangesDAO.findAll(supplier.getSupplierId());
        List<CatalogItemDTO> catalogItemDTOList = this.catalogItemDAO.findAll(supplier.getSupplierId());
        CatalogDTO catalogDTO = new CatalogDTO(catalogItemDTOList);
        Contract c = supplier.getContract();
        ContractDTO contractDTO = new ContractDTO(supplier.getSupplierId(),c.getConstDayDeliviery(),c.isDeliver(),catalogDTO,rangesDto);
        contractDAO.insert(new ContractDTO(supplier.getSupplierId(), c.getConstDayDeliviery(),c.isDeliver(),catalogDTO,rangesDto));
        for (Contact contact : supplier.getContactsList()) {
            contactDao.insert(new ContactDTO( contact.getFirstName(), contact.getLastName(), contact.getPhonNumber(), contact.getAddress()), supplier.getSupplierId());
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
