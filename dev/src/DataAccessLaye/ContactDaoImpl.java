package DataAccessLaye;

import ServiceLayer.ServiceObjects.ContactDTO;

import java.sql.Connection;
import java.util.List;

public class ContactDaoImpl implements  IContactDAO {

    private Connection conn;
    public ContactDaoImpl(Connection conn)
    {
        this.conn = conn;
    }
    @Override
    public ContactDTO getSpecificContact(int supplierId, String phoneNumber) {
        return null;
    }

    @Override
    public void updateContact() {

    }

    @Override
    public List<ContactDTO> getAllContactBySupplier(int supplierId) {
        return null;
    }

    @Override
    public void insertContact() {

    }
}
