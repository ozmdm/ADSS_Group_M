package DataAccessLaye;

import ServiceLayer.ServiceObjects.ContactDTO;

import java.util.List;

public interface IContactDAO {

    ContactDTO getSpecificContact(int supplierId, String phoneNumber);

    void updateContact();

    List<ContactDTO> getAllContactBySupplier(int supplierId);

    void insertContact();
}
