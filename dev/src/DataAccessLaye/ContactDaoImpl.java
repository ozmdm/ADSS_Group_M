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
    public ContactDTO find(int supplierId, String phoneNumber) {
        return null;
    }

    @Override
    public void insert(ContactDTO contactDTO) {

    }

    @Override
    public List<ContactDTO> findAll() {
        return null;
    }
}
