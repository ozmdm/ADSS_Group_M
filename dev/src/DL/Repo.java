package DL;

import BL.Transports.DeliveryPackage.Delivery;

import java.io.File;
import java.sql.*;
import java.util.Date;
import java.util.List;

public class Repo {
    private static final String databaseName = "WorkersAndTransports.db";

    public static Connection openConnection() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
            connection.createStatement().execute("PRAGMA foreign_keys = ON");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void createDatabase()
    {
        Connection conn=openConnection();
        try (Statement stmt = conn.createStatement();) {
            createEmployees(conn);
            createEmployeeConstraints(conn);
            createEmployeeRoles(conn);
            creatTruck(conn);
            creatLocations(conn);
            creatOrders(conn);
            createDrivers(conn);
            createItemsForOrder(conn);
            creatDeliveries(conn);
            creatOrdersForDelivery(conn);
            creatLocationsForDelivery(conn);
            createShifts(conn);
            createEmployeesShifts(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static boolean openDatabase() {
        File f = new File(databaseName);
        if (!f.exists())
        {
            createDatabase();
            return false;
        }
        return true;

    }

    public static void creatTruck(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS Trucks " +
                    "(ID VARCHAR(100) PRIMARY KEY NOT NULL," +
                    "MODEL           VARCHAR(100)    NOT NULL, " +
                    "NETO_WEIGHT         DOUBLE NOT NULL ," +
                    "MAX_WEIGHT         DOUBLE NOT NULL, " +
                    "ISUSED INT NOT NULL)";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void creatLocations(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS Locations " +
                    "(ID INT PRIMARY KEY NOT NULL," +
                    "NAME           VARCHAR(100)    NOT NULL, " +
                    "ADDRESS         VARCHAR(100) NOT NULL ," +
                    "TEL_NUMBER         VARCHAR(100) NOT NULL, "+
                    "CONTACT_NAME  VARCHAR(100) NOT NULL,"+
                    "SHIIPING_EREA VARCHAR(100) NOT NULL )";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void creatOrders(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS Orders" +
                    "(ID INT PRIMARY KEY NOT NULL," +
                    "SUPPLIER           VARCHAR(100)    NOT NULL, " +
                    "TARGET_LOCATION         INT NOT NULL ," +
                    "TOTAL_WEIGHT         DOUBLE NOT NULL,"+
                    "FOREIGN KEY (TARGET_LOCATION) REFERENCES Locations(ID) ON DELETE Restrict )";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createItemsForOrder(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS ItemsForOrder " +
                    "(ORDER_ID INT NOT NULL," +
                    "ITEM   VARCHAR(100)     NOT NULL, " +
                    "QUINTITY         INT NOT NULL,"+
                    " PRIMARY KEY (ORDER_ID, ITEM),"+
                    "FOREIGN KEY (ORDER_ID) REFERENCES Orders(ID) ON DELETE RESTRICT )";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createDrivers(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS Drivers" +
                    "(ID INT PRIMARY KEY NOT NULL," +
                    "License_Type VARCHAR (10)   NOT NULL, " +
                    "Expiration_Date DATE NOT NULL," +
                    "STATUS INT NOT NULL,"+
                    "FOREIGN KEY (ID) REFERENCES Employees (ID) ON DELETE CASCADE )";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void creatDeliveries(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS Deliveries" +
                    "(ID VARCHAR(100) PRIMARY KEY NOT NULL," +
                    "DELIVERY_DATE DATE    NOT NULL, " +
                    "DELIVER_TIME  TIME NOT NULL ," +
                    "DRIVER_ID INT NOT NULL, "+
                    "SOURCE_LOCATION INT NOT NULL, "+
                    "WEIGHT DOUBLE NOT NULL, "+
                    "TRUCK_ID VARCHAR (100) NOT NULL, "+
                    "STATUS VARCHAR (100) NOT NULL,"+
                    "FOREIGN KEY (DRIVER_ID) REFERENCES Drivers(ID) ON DELETE RESTRICT ,"+
                    "FOREIGN KEY (SOURCE_LOCATION) REFERENCES Locations(ID) ON DELETE RESTRICT ,"+
                    "FOREIGN KEY (TRUCK_ID) REFERENCES Trucks(ID) ON DELETE RESTRICT )";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void creatOrdersForDelivery(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS OrdersForDelivery" +
                    "(DELIVERY_ID VARCHAR(100)  NOT NULL," +
                    "ORDER_ID INT  NOT NULL, "+
                    "PRIMARY KEY (DELIVERY_ID, ORDER_ID),"+
                    "FOREIGN KEY (DELIVERY_ID) REFERENCES Deliveries(ID) ON DELETE RESTRICT ,"+
                    "FOREIGN KEY (ORDER_ID) REFERENCES Orders(ID) ON DELETE RESTRICT )";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void creatLocationsForDelivery(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS LocationsForDelivery " +
                    "(DELIVERY_ID VARCHAR(100)  NOT NULL," +
                    "LOCATION_ID INT  NOT NULL, " +
                    "PRIMARY KEY (DELIVERY_ID, LOCATION_ID),"+
                    "FOREIGN KEY (DELIVERY_ID) REFERENCES Deliveries(ID) ON DELETE RESTRICT ,"+
                    "FOREIGN KEY (LOCATION_ID) REFERENCES Locations(ID) ON DELETE RESTRICT )";


            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createEmployees(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS Employees" +
                    "(ID INT PRIMARY KEY NOT NULL," +
                    "Name VARCHAR (20)   NOT NULL, " +
                    "Bank_Account INTEGER NOT NULL," +
                    "Start_Working_Date DATE NOT NULL," +
                    "Salary INTEGER NOT NULL," +
                    "Vacation_Days INTEGER NOT NULL)";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createEmployeeRoles(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS EmployeesRoles" +
                    "(ID INT NOT NULL," +
                    "Role VARCHAR (20) NOT NULL," +
                    "PRIMARY KEY (ID,Role),"+
                    "FOREIGN KEY (ID) REFERENCES Employees(ID) ON DELETE CASCADE)";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createEmployeeConstraints(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS EmployeesConstraints" +
                    "(ID INT NOT NULL," +
                    "DayConstraint VARCHAR (20) NOT NULL, " +
                    "KindConstraint VARCHAR (20) NOT NULL," +
                    "PRIMARY KEY (ID,DayConstraint,KindConstraint),"+
                    "FOREIGN KEY (ID) REFERENCES Employees(ID) ON DELETE CASCADE)";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createShifts(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS Shifts" +
                    "(Date DATE NOT NULL," +
                    "Kind VARCHAR (20) NOT NULL, " +
                    "ShiftManager BIT DEFAULT 0 NOT NULL,"+
                    "PRIMARY KEY (Date,Kind))";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createEmployeesShifts(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS EmployeesShifts" +
                    "(Date DATE NOT NULL," +
                    "Kind VARCHAR (20) NOT NULL, " +
                    "ID INTEGER NOT NULL, "+
                    "Role VARCHAR (20) NOT NULL,"+
                    "PRIMARY KEY (Date,Kind,ID),"+
                    "FOREIGN KEY (Date,Kind) REFERENCES Shifts(Date,Kind) ON DELETE CASCADE ," +
                    "FOREIGN KEY (ID) REFERENCES Employees(ID) ON DELETE CASCADE)";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void deleteDataBase()
    {
        Connection conn=openConnection();
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "DROP TABLE OrdersForDelivery";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE LocationsForDelivery";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE ItemsForOrder";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE Deliveries";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE Trucks";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE Orders";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE Locations";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE Drivers";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE EmployeesShifts";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE Shifts";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE EmployeesConstraints";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE EmployeesRoles";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE Employees";
            stmt.executeUpdate(sql1);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


}



