package BL.DeliveryPackage;

import java.util.*;
import java.util.regex.Pattern;

public class LocationController {

    private Map<String, Location> locations;
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

    public Location getLocation(String id) throws Exception {
        if(!locations.containsKey(id))
            throw new Exception("the location doesn't exists");
        return locations.get(id);
    }

    public Location createLocation(String id, String name, String address, String telNumber, String contactName, String shippingArea) throws Exception {
        if(!telNumber.matches("[0-9]+"))
            throw new Exception("the telephone number contains illegal numbers");
        if(!contactName.matches("[a-zA-Z]+"))
            throw new Exception("the contact name contains illegal characters");
        if(locations.containsKey(id))
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
        if(locations.containsKey(location.getId()))
            throw new Exception("the location already exists");
        if(location.getShippingArea().compareTo("north") != 0 && location.getShippingArea().compareTo("south") != 0 && location.getShippingArea().compareTo("center") != 0)
            throw new Exception("the location area doesn't exist");
        this.locations.put(location.getId(), location);
    }

    public void removeLocation(Location location) throws Exception {
        if(!locations.containsKey(location.getId()))
            throw new Exception("the location doesn't exists");
        this.locations.remove(location.getId());
    }

    public void changetelNumber(String id, String telNumber) throws Exception {
        if(!telNumber.matches("[0-9]+"))
            throw new Exception("the telephone number contains illegal numbers");
        if(!locations.containsKey(id))
            throw new Exception("the location doesn't exists");
        locations.get(id).setTelNumber(telNumber);
    }

    public void changecontactName(String id, String contactName) throws Exception {
        if(!contactName.matches("[a-zA-Z]+"))
            throw new Exception("the contact name contains illegal characters");
        if(!locations.containsKey(id))
            throw new Exception("the location doesn't exists");
        locations.get(id).setContactName(contactName);
    }

    public Map<String, Location> getLocations() {
        return locations;
    }
}
