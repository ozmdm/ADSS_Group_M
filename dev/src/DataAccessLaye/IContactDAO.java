package DataAccessLaye;

import ServiceLayer.ServiceObjects.ContactDTO;

import java.util.List;

public interface IContactDAO {

    ContactDTO find(int supplierId, String phoneNumber);

    void insert(ContactDTO contactDTO);

    List<ContactDTO> findAll();


}
