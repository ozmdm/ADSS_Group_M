package DataAccessLaye;

import ServiceLayer.ServiceObjects.ContactDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDaoImpl implements  IContactDAO {

    private Connection conn;
    public ContactDaoImpl(Connection conn)
    {
        this.conn = conn;
    }

    @Override
    public ContactDTO find(int supplierId, String phoneNumber) throws SQLException {
        String sql = "SELECT * "
                + "FROM Contact WHERE supplierId = ? AND phoneNumber = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);

      /*  // set the value
        pstmt.set(1, catalogItemId,contractId);*/
        //
        ResultSet rs = pstmt.executeQuery();
        int contactId = rs.getInt("index"); // TODO : creatIndexs IN TABLE
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        String phoneNumbers = rs.getString("phoneNumber");
        String address = rs.getString("address");


        ContactDTO contactDTO = new ContactDTO(contactId,firstName,lastName,phoneNumbers,address);
        return contactDTO;
    }

    @Override
    public void insert(ContactDTO contactDTO, int supplierId) throws SQLException {
        String sql = "INSERT INTO Contact(supplierId,firstName,lastName,phoneNumber,address) VALUES(?,?,?,?,?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,contactDTO.getContactId());
        pstmt.setInt(2, supplierId);
        pstmt.setString(3, contactDTO.getFirstName());
        pstmt.setString(4, contactDTO.getLastName());
        pstmt.setString(5, contactDTO.getPhoneNumber());
        pstmt.setString(6, contactDTO.getFirstName());
        pstmt.executeUpdate();

    }

    @Override
    public List<ContactDTO> findAllBySupplier(int supplierId) throws SQLException {
        List<ContactDTO> contactDTOS = new ArrayList<>();

        String sql = "SELECT * "
                + "FROM Contact WHERE supplierId = ? ";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        //
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            int contactId = rs.getInt("index"); // TODO : creatIndexs IN TABLE
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String phoneNumbers = rs.getString("phoneNumber");
            String address = rs.getString("address");
            ContactDTO contactDTO = new ContactDTO(contactId,firstName,lastName,phoneNumbers,address);
            contactDTOS.add(contactDTO);
        }
        return contactDTOS;
    }
}
