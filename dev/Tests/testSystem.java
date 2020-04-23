package Tests;

import Business_Layer.Employee;
import Business_Layer.EmployeeController;
import Business_Layer.ScheduleHistory;
import Business_Layer.WorkingSchedule;
import javafx.util.Pair;
import org.junit.Test;

import java.time.LocalDate;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class testSystem {

    //EmployeeController e = new EmployeeController();

    @Test
    public void test1() // check if we can register 2 employees with the same ID
    {
        EmployeeController e = new EmployeeController();
        Employee e1 = new Employee("Bob",25,3456, LocalDate.now(),4000,22,null);
        e.registerEmployee(e1);
        Employee e2 = new Employee("John",25,3456, LocalDate.now(),4000,22,null);
        assertEquals(e.registerEmployee(e2),false);
    }

    @Test
    public void test2() // check if we can set employee name and it is shown in the employee map
    {
        EmployeeController e = new EmployeeController();
        Employee e1 = new Employee("Bob",25,3456, LocalDate.now(),4000,22,null);
        e.registerEmployee(e1);
        Employee e2 = new Employee("John",20,3456, LocalDate.now(),4000,22,null);
        e.registerEmployee(e2);
        e2.setName("Daniel");
        assertEquals(e.getEmployeeMap().get(e2.getID()).getName(),"Daniel");
    }

    @Test
    public void test3() // check if the constraint was added
    {
        EmployeeController e = new EmployeeController();
        Employee e1 = new Employee("Bob",25,3456, LocalDate.now(),4000,22,null);
        e.registerEmployee(e1);
        Pair<String,String> p = new Pair<>("SUNDAY","Morning");
        e1.addConstraints(p);
        assertEquals(e.getEmployeeMap().get(e1.getID()).getConstrains().isEmpty(),false);
    }

    @Test
    public void test4() // check if the constraint wes deleted
    {
        EmployeeController e = new EmployeeController();
        Employee e1 = new Employee("Bob",25,3456, LocalDate.now(),4000,22,null);
        e.registerEmployee(e1);
        Pair<String,String> p = new Pair<>("SUNDAY","Morning");
        e1.addConstraints(p);
        e1.removeConstraints(0);
        assertEquals(e.getEmployeeMap().get(e1.getID()).getConstrains().isEmpty(),true);
    }

    @Test
    public void test5() // add roles to employee and check if there is a role that we didn't add
    {
        EmployeeController e = new EmployeeController();
        LinkedList<String> roles = new LinkedList<>();
        roles.add("Cashier");
        Employee e1 = new Employee("Bob",25,3456, LocalDate.now(),4000,22,roles);
        e.registerEmployee(e1);
        e1.addRole("cashier");
        e1.addRole("shift manager");
        assertEquals(e.getEmployeeMap().get(e1.getID()).getRoles().remove(0),"Cashier");
    }

    @Test
    public void test6() // check if we can add worker to shift that he has constraint on her
    {
        EmployeeController e = new EmployeeController();
        ScheduleHistory sc = new ScheduleHistory();
        WorkingSchedule ws = new WorkingSchedule(LocalDate.of(2020,5,15),"Evening");
        sc.addWorkingSchedule(ws);
        LinkedList<String> roles = new LinkedList<>();
        roles.add("Cashier");
        Employee e1 = new Employee("Bob",25,3456, LocalDate.now(),4000,22,roles);
        e.registerEmployee(e1);
        e1.addConstraints(new Pair<>("FRIDAY","Evening"));
        ws.addWorkersToShift("Cashier",e1);
        assertEquals(sc.getShift(LocalDate.of(2020,5,15),"Evening").getEmployees().isEmpty(),true);
    }

    @Test
    public void test7() // check if there is a shift manager in the shift
    {
        EmployeeController e = new EmployeeController();
        ScheduleHistory sc = new ScheduleHistory();
        WorkingSchedule ws = new WorkingSchedule(LocalDate.of(2020,5,15),"Evening");
        sc.addWorkingSchedule(ws);
        LinkedList<String> roles = new LinkedList<>();
        roles.add("Shift_Manager");
        Employee e1 = new Employee("Bob",25,3456, LocalDate.now(),4000,22,roles);
        e.registerEmployee(e1);
        e1.addConstraints(new Pair<>("FRIDAY","Morning"));
        ws.addWorkersToShift("Shift_Manager",e1);
        assertEquals(sc.getShift(LocalDate.of(2020,5,15),"Evening").getShiftManager(),true);
    }

    @Test
    public void test8() // check if we can add a shift that is already exists
    {
        ScheduleHistory sc = new ScheduleHistory();
        WorkingSchedule ws = new WorkingSchedule(LocalDate.of(2020,5,15),"Evening");
        sc.addWorkingSchedule(ws);
        WorkingSchedule ws1 = new WorkingSchedule(LocalDate.of(2020,5,15),"Evening");
        sc.addWorkingSchedule(ws1);
        assertEquals(sc.getRecord().size(),1);
    }

    @Test
    public void test9() // check if we can add two shifts in the same day
    {
        ScheduleHistory sc = new ScheduleHistory();
        WorkingSchedule ws = new WorkingSchedule(LocalDate.of(2020,5,15),"Evening");
        sc.addWorkingSchedule(ws);
        WorkingSchedule ws1 = new WorkingSchedule(LocalDate.of(2020,5,15),"Morning");
        sc.addWorkingSchedule(ws1);
        assertEquals(sc.getRecord().size(),2);
    }

    @Test
    public void test10() // check if we can add worker to
    {
        EmployeeController e = new EmployeeController();
        ScheduleHistory sc = new ScheduleHistory();
        WorkingSchedule ws = new WorkingSchedule(LocalDate.of(2020,5,15),"Evening");
        sc.addWorkingSchedule(ws);
        LinkedList<String> roles = new LinkedList<>();
        roles.add("Shift_Manager");
        roles.add("Cashier");
        Employee e1 = new Employee("Bob",25,3456, LocalDate.now(),4000,22,roles);
        e.registerEmployee(e1);
        e1.addConstraints(new Pair<>("FRIDAY","Morning"));
        ws.addWorkersToShift("Shift_Manager",e1);
        ws.changeEmployeeRole(e1,"Cashier");
        assertEquals(sc.getShift(LocalDate.of(2020,5,15),"Evening").getEmployees().get(0).getKey(),"Cashier");
    }

    @Test
    public void test11() // check if we delete an employee and he deleted from the shifts that have him, however we didn't delete him from shifts
    {
        EmployeeController e = new EmployeeController();
        ScheduleHistory sc = new ScheduleHistory();
        WorkingSchedule ws = new WorkingSchedule(LocalDate.of(2020,5,15),"Evening");
        sc.addWorkingSchedule(ws);
        LinkedList<String> roles = new LinkedList<>();
        roles.add("Shift_Manager");
        Employee e1 = new Employee("Bob",25,3456, LocalDate.now(),4000,22,roles);
        e.registerEmployee(e1);
        e1.addConstraints(new Pair<>("FRIDAY","Morning"));
        ws.addWorkersToShift("Shift_Manager",e1);
        e.deleteEmployee(25);
        assertEquals(sc.getShift(LocalDate.of(2020,5,15),"Evening").getEmployees().isEmpty(),false);
    }

    @Test
    public void test12() // check if we delete an employee and he deleted from the shifts that have him
    {
        EmployeeController e = new EmployeeController();
        ScheduleHistory sc = new ScheduleHistory();
        WorkingSchedule ws = new WorkingSchedule(LocalDate.of(2020,5,15),"Evening");
        sc.addWorkingSchedule(ws);
        LinkedList<String> roles = new LinkedList<>();
        roles.add("Shift_Manager");
        Employee e1 = new Employee("Bob",25,3456, LocalDate.now(),4000,22,roles);
        e.registerEmployee(e1);
        e1.addConstraints(new Pair<>("FRIDAY","Morning"));
        ws.addWorkersToShift("Shift_Manager",e1);
        sc.deleteEmployeeFromShift(e1);
        e.deleteEmployee(25);
        assertEquals(sc.getShift(LocalDate.of(2020,5,15),"Evening").getEmployees().isEmpty(),true);
    }

    @Test
    public void test13() // check if we can delete shift
    {
        ScheduleHistory sc = new ScheduleHistory();
        WorkingSchedule ws = new WorkingSchedule(LocalDate.of(2020,5,15),"Evening");
        sc.addWorkingSchedule(ws);
        sc.removeShift(LocalDate.of(2020,5,15),"Evening");
        assertEquals(sc.getShift(LocalDate.of(2020,5,15),"Evening"),null);
    }
}
