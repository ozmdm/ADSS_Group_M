package BL.DriverPackage;

import java.util.*;

public class Driver {

    private String id; //should be removed after connection with Employee module
    private String name; //should be removed after connection with Employee module
    private String licenseType;
    private Date expLicenseDate;
    private boolean isDriving;

    public Driver(String id, String name, String licenseType, Date expLicenseDate) {
        this.id = id;
        this.name = name;
        this.licenseType = licenseType;
        this.expLicenseDate = expLicenseDate;
        this.isDriving = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public Date getExpLicenseDate() {
        return expLicenseDate;
    }

    public void setExpLicenseDate(Date expLicenseDate) {
        this.expLicenseDate = expLicenseDate;
    }

    public boolean isDriving() {
        return isDriving;
    }

    public void setDriving() {
        isDriving = true;
    }

    public void setNotDriving() {
        isDriving = false;
    }

    @Override
    public String toString() {
        return "Driver {" +
                "id='" + id + '\'' +
                ", licenseType='" + licenseType + '\'' +
                ", expLicenseDate=" + expLicenseDate +
                '}';
    }
}
