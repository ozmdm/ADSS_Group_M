package Business_Layer;

import javafx.util.Pair;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

public class EmployeeController {
    private static HashMap<Integer,Employee> employeeMap;

    public EmployeeController(){
        employeeMap = new HashMap<>();
    }

    public HashMap<Integer,Employee> getEmployeeMap()
    {
        return employeeMap;
    }

    public boolean registerEmployee(Employee e)
    {
        if(validID(e.getID())) {
            employeeMap.put(e.getID(), e);
            return true;
        }
        return false;
    }

    public boolean validID(Integer i){//if employee does not exist, return yes
        return !employeeMap.containsKey(i);
    }

    public void addRole(Integer ID, String role)
    {
        employeeMap.get(ID).addRole(role);
    }

    public void setEmployeeName(Integer ID, String name)
    {
        employeeMap.get(ID).setName(name);
    }

    public void setBankAccount(Integer ID, Integer bankAccount)
    {
        employeeMap.get(ID).setBankAccount(bankAccount);
    }

    public void setSalary(Integer ID, Integer salary)
    {
        employeeMap.get(ID).setSalary(salary);
    }

    public void setVacationDays(Integer ID, Integer vacationDays)
    {
        employeeMap.get(ID).setVacationDays(vacationDays);
    }

    public void addConstraints(Integer ID,Pair<String,String> p)
    {
        employeeMap.get(ID).addConstraints(p);
    }

    public void deleteEmployee(Integer ID){
        employeeMap.remove(ID);
    }

    public void addFakeEmployees()
    {
        LinkedList<String> list = new LinkedList();
        list.add("Cashier");
        Employee e = new Employee("Barak",1,11111,LocalDate.now(),10000,25,list);
        registerEmployee(e);
        LinkedList<String> list1 = new LinkedList();
        list1.add("Store_Keeper");
        list1.add("Shift_Manager");
        Employee e1 = new Employee("Lin",2,22222,LocalDate.now(),13000,28,list1);
        e1.addConstraints(new Pair("SUNDAY","Morning"));
        registerEmployee(e1);
        LinkedList<String> list2 = new LinkedList();
        list2.add("Cashier");
        list2.add("Driver");
        Employee e2 = new Employee("Inbar",3,33333,LocalDate.now(),12000,22,list2);
        e2.addConstraints(new Pair("FRIDAY","Evening"));
        registerEmployee(e2);
        LinkedList<String> list3 = new LinkedList();
        list3.add("Driver");
        Employee e3 = new Employee("Nir",4,44444,LocalDate.now(),8000,24,list3);
        registerEmployee(e3);
        LinkedList<String> list4 = new LinkedList();
        list4.add("Shift_Manager");
        Employee e4 = new Employee("Alex",5,55555,LocalDate.now(),15000,30,list4);
        registerEmployee(e4);
    }

    public String toString()
    {
        String ret = "";
        for(Employee e: employeeMap.values())
        {
            ret += e.toString() + "\n";
        }
        return ret;
    }
}
