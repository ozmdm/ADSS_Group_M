package BL.Employees;

import BL.Transports.DriverPackage.Driver;

import DL.Transports.DTO;
import DL.Employees.*;
import javafx.util.Pair;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class EmployeeController {
    private static EmployeeController employeeController = null;
    private HashMap<Integer,Employee> employeeMap;
    private Map<Integer, Driver> drivers;

    private EmployeeController(){
        employeeMap = new HashMap<>();
        drivers = new HashMap<>();
    }

    public static EmployeeController getInstance()
    {
        if(employeeController == null)
            employeeController = new EmployeeController();
        return employeeController;
    }

    public List<Pair<String,String>> getConstraints(int id) throws SQLException {
        try
        {
            return DL.Employees.Employee.getConstraint(id);
        }catch (Exception e)
        {
            throw e;
        }
    }

    public List<String> getRoles(int id) throws SQLException {
        try
        {
            return DL.Employees.Employee.getRoles(id);
        }catch (Exception e)
        {
            throw e;
        }
    }

    public void removeConstraints(Integer ID,Pair<String,String> p) throws SQLException {
        try
        {
            DL.Employees.Employee.removeConstraint(new DL.Employees.DTO.EmployeeConstraints(ID,p.getKey(),p.getValue()));

        }catch (Exception e)
        {
            throw e;
        }
    }

    public Employee getEmp(int id) throws Exception {
        try
        {
            Employee e= DL.Employees.Employee.checkEmployee(id);
            if(e==null)
                throw new Exception("employee doesn't exists");
            return e;
        } catch (Exception e) {
            throw e;
        }
    }
    public HashMap<Integer,Employee> getEmployeeMap()
    {
        return employeeMap;
    }
    public Map<Integer, Driver> getDrivers() { return drivers; }

    public boolean registerEmployee(Employee e) throws SQLException {
        try {
            if (validID(e.getID())) {
                //employeeMap.put(e.getID(), e);
                DL.Employees.Employee.insertEmployee(new DL.Employees.DTO.Employees(e.getID(), e.getName(), e.getBankAccount(), e.getStartWorkingDate(), e.getSalary(), e.getVacationDays()));
                for (int i = 0; i < e.getRoles().size(); i++) {
                    DL.Employees.Employee.insertEmployeeRoles(new DL.Employees.DTO.EmployeeRoles(e.getID(), e.getRoles().get(i)));
                }
                return true;
            }
        }
        catch (Exception exc)
        {
            throw exc;
        }
        return false;
    }

    public boolean registerEmployee(String name, Integer ID, Integer bankAccount, LocalDate startWorkingDate,
                                    Integer salary, Integer vacationDays, LinkedList<String> roles) throws SQLException {
        try {
            if (validID(ID)) {
                //employeeMap.put(ID, new Employee(name, ID, bankAccount, startWorkingDate, salary, vacationDays, roles));
                DL.Employees.Employee.insertEmployee(new DL.Employees.DTO.Employees(ID, name, bankAccount, startWorkingDate, salary, vacationDays));
                for (int i = 0; i < roles.size(); i++) {
                    DL.Employees.Employee.insertEmployeeRoles(new DL.Employees.DTO.EmployeeRoles(ID, roles.get(i)));
                }
                return true;
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        return false;
    }

    public boolean registerDriver(Driver d) throws SQLException {
        try {
            if (validID(d.getID())) {
                //employeeMap.put(d.getID(), d);
                //drivers.put(d.getID(), d);
                DL.Employees.Employee.insertEmployee(new DL.Employees.DTO.Employees(d.getID(), d.getName(), d.getBankAccount(), d.getStartWorkingDate(), d.getSalary(), d.getVacationDays()));
                DL.Transports.Driver.insertDriver(new DTO.Driver(d.getID(), d.getLicenseType(), d.getExpLicenseDate(), d.isDriving()));
                for (int i = 0; i < d.getRoles().size(); i++) {
                    DL.Employees.Employee.insertEmployeeRoles(new DL.Employees.DTO.EmployeeRoles(d.getID(), d.getRoles().get(i)));
                }
                return true;
            }
            }catch (Exception e)
        {
            throw e;
        }
        return false;
    }

    public Employee createEmployee(String name, Integer ID, Integer bankAccount,
                                   Integer salary, Integer vacationDays, LinkedList<String> roles) throws SQLException {
        Employee e= new Employee(name, ID, bankAccount, LocalDate.now(), salary, vacationDays, roles);
        registerEmployee(e);
        return e;
    }

    public Driver createDriver(String name, Integer ID, Integer bankAccount, Integer salary, Integer vacationDays,
                               LinkedList<String> roles, String licenseType, Date expLicenseDate) throws Exception
    {
        try {
            Date date = new Date();
            if (DL.Transports.Driver.checkDriver(ID) != null)
                throw new Exception("the driver already exists");
            if (expLicenseDate.compareTo(date) < 0)
                throw new Exception("license date already expired");
            Driver d = new Driver(name, ID, bankAccount, LocalDate.now(), salary, vacationDays, roles, licenseType, expLicenseDate);
            registerDriver(d);

            return d;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public boolean validID(Integer i) throws SQLException {//if employee does not exist, return yes
        //return !employeeMap.containsKey(i);
        try {
            return DL.Employees.Employee.validID(i);

        }catch (Exception e)
        {
            throw e;
        }
    }

    public void addRole(Integer ID, String role) throws SQLException {
        //employeeMap.get(ID).addRole(role);
        try {
            DL.Employees.Employee.insertEmployeeRoles(new DL.Employees.DTO.EmployeeRoles(ID, role));
        }catch (Exception e)
        {
            throw e;
        }
    }

    public void addRole(Integer ID, String role,String licensType,Date date) throws Exception {
        //Employee e=employeeMap.get(ID);
        try {


            Employee e = DL.Employees.Employee.checkEmployee(ID);
            Date date1 = new Date();
            if (DL.Transports.Driver.checkDriver(ID) != null)
                throw new Exception("the driver already exists");
            if (date.compareTo(date1) < 0)
                throw new Exception("license date already expired");
            Driver d = new Driver(e.getName(), e.getID(), e.getBankAccount(), e.getStartWorkingDate(), e.getSalary(), e.getVacationDays(), e.getRoles(), licensType, date);
            d.addRole(role);
            //employeeMap.remove(ID);
            DL.Transports.Driver.insertDriver(new DTO.Driver(d.getID(),d.getLicenseType(),new java.sql.Date(d.getExpLicenseDate().getTime()),d.isDriving()));
        }catch (Exception e)
        {
            throw e;
        }
    }


    public void setEmployeeName(Integer ID, String name) throws SQLException {
        //employeeMap.get(ID).setName(name);
        try
        {
            DL.Employees.Employee.updateName(ID,name);
        }catch (Exception e)
        {
            throw e;
        }
    }

    public void setBankAccount(Integer ID, Integer bankAccount) throws SQLException {
        //employeeMap.get(ID).setBankAccount(bankAccount);
        try
        {
            DL.Employees.Employee.updateBankAccount(ID,bankAccount);

        }catch (Exception e)
        {
            throw e;
        }
    }

    public void setSalary(Integer ID, Integer salary) throws SQLException {
        //employeeMap.get(ID).setSalary(salary);
        try
        {
            DL.Employees.Employee.updateSalary(ID,salary);

        }catch (Exception e)
        {
            throw e;
        }
    }

    public void setVacationDays(Integer ID, Integer vacationDays) throws SQLException {
        //employeeMap.get(ID).setVacationDays(vacationDays);
        try
        {
            DL.Employees.Employee.updateVacationDays(ID,vacationDays);

        }catch (Exception e)
        {
            throw e;
        }
    }

    public void addConstraints(Integer ID,Pair<String,String> p) throws SQLException {
        //employeeMap.get(ID).addConstraints(p);
        try
        {
            DL.Employees.Employee.insertEmployeeConstraint(new DL.Employees.DTO.EmployeeConstraints(ID,p.getKey(),p.getValue()));

        }catch (Exception e)
        {
            throw e;
        }
    }

    public void deleteEmployee(Integer ID) throws Exception {
        try {
            if (DL.Employees.Employee.checkEmployee(ID) == null)
                throw new Exception("the employee doesn't exists");
            //employeeMap.remove(ID);
            if (DL.Transports.Driver.checkDriver(ID) != null)
                DL.Transports.Driver.deleteDriver(ID);
            //this.drivers.remove(ID);
            DL.Employees.Employee.deleteEmployee(ID);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

   /* public DTO.Driver getDriver(int id) throws Exception {
        DTO.Driver d= DL.Transports.Driver.checkDriver(id);
        if(d==null)
            throw new Exception("the driver doesn't exists");
        return d;
    }*/


    public void changeExpDate(Integer id, Date expLicenseDate) throws Exception {
        try {

            Date date = new Date();
            if (expLicenseDate.compareTo(date) < 0)
                throw new Exception("license date already expired");
            if (DL.Transports.Driver.checkDriver(id) == null)
                throw new Exception("the driver doesn't exists");
            //drivers.get(id).setExpLicenseDate(expLicenseDate);
            DL.Transports.Driver.updateExpDate(id, new java.sql.Date(expLicenseDate.getTime()));
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void changeLicenseType(Integer id, String licenseType) throws Exception {
        try {
            if (DL.Transports.Driver.checkDriver(id) == null)
                throw new Exception("the driver doesn't exists");
            //drivers.get(id).setLicenseType(licenseType);
            DL.Transports.Driver.updateLicenseType(id, licenseType);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void setDriverToDrive(Integer id) throws Exception {
        try {
            if (DL.Transports.Driver.checkDriver(id) == null)
                throw new Exception("the driver doesn't exists");
            //drivers.get(id).setDriving();
            DL.Transports.Driver.updateStatus(id, true);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void setDriverNotToDrive(Integer id) throws Exception {
        try {
            if (DL.Transports.Driver.checkDriver(id) == null)
                throw new Exception("the driver doesn't exists");
            //drivers.get(id).setNotDriving();
            DL.Transports.Driver.updateStatus(id, false);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public boolean checkLicense(int id, Date date) throws Exception
    {
        try {

            Driver d = DL.Transports.Driver.checkDriver(id);
            if (d == null)
                throw new Exception("the driver doesn't exists");
            if (d.getExpLicenseDate().compareTo(date) < 0)
                throw new Exception("license experation date is lower then delivery date");
            return true;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public boolean checkShiftManager(int id) throws Exception
    {
        try {
            if(DL.Employees.Employee.CheckShiftManager(id))
                return true;
            return false;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public String getDriversLicesnesType(int id) throws Exception {
        try {
            Driver d = DL.Transports.Driver.checkDriver(id);
            if (d == null)
                throw new Exception("the driver doesn't exists");
            return d.getLicenseType();
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void removeDriver(int ID) throws Exception {
        try{
            Driver d=DL.Transports.Driver.checkDriver(ID);
            if(d==null)
                throw new Exception("the employee is not a driver");
            DL.Transports.Driver.deleteDriver(ID);
            DL.Employees.Employee.deleteRole(ID,"Driver");
        }catch (Exception e)
        {
            throw e;
        }

       /* Driver d=drivers.remove(ID);
        DL.Employees.Employee.deleteEmployee(ID);
        int index=d.getRoles().indexOf("Driver");
        d.getRoles().remove(index);
        employeeMap.remove(ID);
        boolean b=registerEmployee(d.getName(),d.getID(),d.getBankAccount(),d.getStartWorkingDate(),d.getSalary(),d.getVacationDays(),d.getRoles());
        if(!b)
            throw new Exception("can't remove driver");*/


    }

    public void deleteRole(int id,String role) throws Exception {
        try
        {
            if(DL.Employees.Employee.CheckRole(id,role))
                DL.Employees.Employee.deleteRole(id,role);
            else
            {
                System.out.println("first remove the employee from shifts with that role");
            }
        }catch (Exception e)
        {
            throw e;
        }
    }

    public void addFakeEmployees() throws ParseException, SQLException {
        try{
            LinkedList<String> list = new LinkedList();
            list.add("Cashier");
            Employee e = new Employee("Barak",1,11111,LocalDate.now(),10000,25,list);
            registerEmployee(e);
            LinkedList<String> list1 = new LinkedList();
            list1.add("Store_Keeper");
            list1.add("Shift_Manager");
            Employee e1 = new Employee("Lin",2,22222,LocalDate.now(),13000,28,list1);
            addConstraints(e.getID(),new Pair("SUNDAY","Morning"));
            registerEmployee(e1);
            LinkedList<String> list2 = new LinkedList();
            list2.add("Cashier");
            Employee e2 = new Employee("Inbar",3,33333,LocalDate.now(),12000,22,list2);
            registerEmployee(e2);
            addConstraints(e2.getID(),new Pair("FRIDAY","Evening"));
            LinkedList<String> list4 = new LinkedList();
            list4.add("Shift_Manager");
            Employee e4 = new Employee("Alex",5,55555,LocalDate.now(),15000,30,list4);
            registerEmployee(e4);


            Date date = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/2030");
            LinkedList<String> list3 = new LinkedList();
            list3.add("Driver");
            Driver d1=new Driver("omri",208938985,6666, LocalDate.now(),2000,34,list3,"A",date);
            registerDriver(d1);
            Driver d2=new Driver("noa",312164668,7777, LocalDate.now(),2000,34,list3,"B",date);
            registerDriver(d2);
            Driver d3=new Driver("lidor",123456789,8888, LocalDate.now(),2000,34,list3,"C",date);
            registerDriver(d3);
        }catch (Exception err)
        {
            throw err;
        }
            /*service.createDriver("208938985", "omri", "A", date);
            service.createDriver("312164668", "noa", "B", date);
            service.createDriver("123456789", "lidor", "C", date);*/
    }

    public String toString()
    {
        HashMap<Pair<Integer,String>, LinkedList<String>> h = null;
        try {
            h = DL.Employees.Employee.displayEmployees();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String ret = "";
        for(Map.Entry<Pair<Integer,String>, LinkedList<String>> entry : h.entrySet())
        {
            Integer id =  entry.getKey().getKey();
            String name = entry.getKey().getValue();
            ret += "ID: " + id + "\nName: " + name + "\n" + entry.getValue().toString() + "\n";
        }
        return ret;
    }

    public void printEmployees() throws SQLException {
        DL.Employees.Employee.printEmps();
    }
}
