package BL.Transports.DeliveryPackage;

import DL.Transports.DTO;

import java.sql.SQLException;
import java.util.*;

public class LocationController {

    private Map<Integer, Location> locations;
    private static LocationController locationController = null;

    private LocationController()
    {
        this.locations = new HashMap<>();
    }

    public static LocationController getInstance()
    {
        if(locationController == null)
            locationController = new LocationController();
        return locationController;
    }

    public Location getLocation(int id) throws Exception {
        Location l=DL.Transports.Location.checkLocation(id);
        if(l==null)
            throw new Exception("the location doesn't exists");
        return l;
    }

    public Location createLocation(int id, String name, String address, String telNumber, String contactName, String shippingArea) throws Exception {
        if(!telNumber.matches("[0-9]+"))
            throw new Exception("the telephone number contains illegal numbers");
        if(!contactName.matches("[a-zA-Z]+"))
            throw new Exception("the contact name contains illegal characters");
        if(DL.Transports.Location.checkLocation(id)!=null)
            throw new Exception("the location already exists");
        if(shippingArea.compareTo("north") != 0 && shippingArea.compareTo("south") != 0 && shippingArea.compareTo("center") != 0)
            throw new Exception("the location area doesn't exist");
        Location location = new Location(id, name, address, telNumber, contactName, shippingArea);
        return location;
    }

    public void addLocation(Location location) throws Exception {
        if(!location.getTelNumber().matches("[0-9]+"))
            throw new Exception("the telephone number contains illegal numbers");
        if(!location.getContactName().matches("[a-zA-Z]+"))
            throw new Exception("the contact name contains illegal characters");
        if(DL.Transports.Location.checkLocation(location.getId())!=null)
            throw new Exception("the location already exists");
        if(location.getShippingArea().compareTo("north") != 0 && location.getShippingArea().compareTo("south") != 0 && location.getShippingArea().compareTo("center") != 0)
            throw new Exception("the location area doesn't exist");
        this.locations.put(location.getId(), location);
        DL.Transports.Location.insertLocation(new DTO.Location(location.getId(),location.getName(),location.getAddress(),location.getTelNumber(),location.getContactName(),location.getShippingArea()));

    }

    public void removeLocation(Location location) throws Exception {
        if(DL.Transports.Location.checkLocation(location.getId())==null)
            throw new Exception("the location doesn't exists");
        //this.locations.remove(location.getId());
        DL.Transports.Location.deleteLocation(location.getId());
    }

    public void changetelNumber(int id, String telNumber) throws Exception {
        if(!telNumber.matches("[0-9]+"))
            throw new Exception("the telephone number contains illegal numbers");
        if(DL.Transports.Location.checkLocation(id)==null)
            throw new Exception("the location doesn't exists");
        //locations.get(id).setTelNumber(telNumber);
        DL.Transports.Location.updateTel(id,telNumber);
    }

    public void changecontactName(int id, String contactName) throws Exception {
        if(!contactName.matches("[a-zA-Z]+"))
            throw new Exception("the contact name contains illegal characters");
        if(DL.Transports.Location.checkLocation(id)==null)
            throw new Exception("the location doesn't exists");
        //locations.get(id).setContactName(contactName);
        DL.Transports.Location.updateName(id,contactName);
    }

    public Map<Integer, Location> getLocations() {
        return locations;
    }

    public void printLocations() throws SQLException {
        DL.Transports.Location.printLocation();
    }
}
