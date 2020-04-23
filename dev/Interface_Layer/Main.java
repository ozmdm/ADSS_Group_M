package Interface_Layer;

import javafx.util.Pair;
import Business_Layer.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;


public class Main {


    enum menuOptions {Register_Employee, Edit_Employee_Details, Display_Employee,
        Add_Shift, Edit_Employee_In_Shift, Display_Shift, LoadSystem,Delete_Employee, Delete_Shift, EXIT}

    enum roleOptions {Cashier, Shift_Manager, Driver, Store_Keeper}

    enum employeeDetails {changeName, changeBankAccount, changeSalary, changeVacationDays, addConstraints, removeConstraint, addRole, removeRole, exit}

    enum shift {Morning, Evening}

    enum days {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY}

    enum actionInShift {addEmployee, RemoveEmployee, ChangEmployeeRole, Exit}

    public static void main(String[] args) {
        boolean loop = true;
        Scanner myScanner = new Scanner(System.in);
        EmployeeController employeeController = new EmployeeController();
        ScheduleHistory scheduleHistory = new ScheduleHistory();
        while (loop) {
            System.out.println("choose an option: ");
            for (int i = 0; i < menuOptions.values().length; i++) {
                System.out.println(i + 1 + ". " + menuOptions.values()[i].toString());
            }
            int userInput = myScanner.nextInt();
            switch (userInput) {
                case 1:
                    Employee e = Register_Employee();
                    if (e == null || !employeeController.registerEmployee(e)) {
                        System.out.println("registration was not completed");
                    }
                    break;
                case 2:
                    editEmployee(employeeController);
                    break;
                case 3:
                    System.out.print(employeeController.toString());
                    break;
                case 4:
                    WorkingSchedule ws = Add_Shift();
                    if (ws == null || !scheduleHistory.addWorkingSchedule(ws)) {
                        System.out.println("WorkingSchedule was not created");
                    }
                    break;
                case 5:
                    Edit_Employee_In_Shift(scheduleHistory, employeeController);
                    break;
                case 6:
                    System.out.println(scheduleHistory.toString());
                    break;
                case 7:
                    LoadSystem(scheduleHistory,employeeController);
                    break;
                case 8:
                    Delete_Employee(scheduleHistory,employeeController);
                    break;
                case 9:
                    Delete_Shift(scheduleHistory);
                    break;
                case 10:
                    loop = false;
                    System.out.println("Exiting System");
                    break;
                default:
                    //do nothing
                    break;

            }
        }
    }

    private static Employee Register_Employee() {
        boolean moreRoles = true;
        Scanner myScanner = new Scanner(System.in);
        System.out.print("do you wish to proceed? choose [Y/N]: ");
        String exit = myScanner.next();
        if (!exit.equals("Y")) {
            return null;
        }
        System.out.print("enter employee name: ");
        String name = myScanner.next();
        System.out.print("enter employee ID: ");
        Integer ID = myScanner.nextInt();
        System.out.print("enter employee bank account: ");
        Integer bank = myScanner.nextInt();
        System.out.print("enter employee vacation days: ");
        Integer vacation = myScanner.nextInt();
        System.out.print("enter employee salary: ");
        Integer salary = myScanner.nextInt();
        LinkedList<String> role = new LinkedList<>();
        while (moreRoles) {
            System.out.println("choose employee role from the following: ");
            for (int i = 0; i < roleOptions.values().length; i++) {
                System.out.println(i + 1 + ". " + roleOptions.values()[i].toString());
            }
            Integer roleChoice = myScanner.nextInt();
            if (!inBounds(roleOptions.values().length, roleChoice))
                System.out.println("index out of bounds");
            else if (!role.contains(roleOptions.values()[roleChoice - 1].toString()))
                role.add(roleOptions.values()[roleChoice - 1].toString());
            System.out.print("any more roles? choose [Y/N]: ");
            String ans = myScanner.next();
            if (!ans.equals("Y") && !role.isEmpty()) {
                moreRoles = false;
            }
        }
        Employee e = new Employee(name, ID, bank, LocalDate.now(), salary, vacation, role);
        return e;
    }

    private static void editEmployee(EmployeeController emp) {
        boolean exit = false;
        Scanner myScanner = new Scanner(System.in);
        System.out.print("do you wish to proceed? choose [Y/N]: ");
        String goOut = myScanner.next();
        if (!goOut.equals("Y")) {
            return;
        }
        while (!exit) {
            System.out.println("choose an action: ");
            for (int i = 0; i < employeeDetails.values().length; i++) {
                System.out.println(i + 1 + ". " + employeeDetails.values()[i].toString());
            }

            int userInput = myScanner.nextInt();
            Integer ID = 0;
            if (userInput < 9 && userInput > 0) {
                System.out.print("enter employee ID: ");
                ID = myScanner.nextInt();
                if (emp.validID(ID))
                    userInput = 10;
            }
            switch (userInput) {
                case 1:
                    System.out.print("enter employee name: ");
                    String name = myScanner.next();
                    emp.setEmployeeName(ID, name);
                    break;
                case 2:
                    System.out.print("enter bank account number: ");
                    Integer bankAccount = myScanner.nextInt();
                    emp.setBankAccount(ID, bankAccount);
                    break;
                case 3:
                    System.out.print("enter new salary: ");
                    Integer salary = myScanner.nextInt();
                    emp.setSalary(ID, salary);
                    break;
                case 4:
                    System.out.print("enter new vacation days number: ");
                    Integer vacationDays = myScanner.nextInt();
                    emp.setVacationDays(ID, vacationDays);
                    break;
                case 5:
                    boolean moreConstraints = true;
                    while (moreConstraints) {
                        System.out.println("choose constraint day from the following: ");
                        for (int i = 0; i < days.values().length; i++) {
                            System.out.println(i + 1 + ". " + days.values()[i].toString());
                        }
                        Integer constraintDay = myScanner.nextInt();
                        System.out.println("choose constraint shift from the following: ");
                        for (int i = 0; i < shift.values().length; i++) {
                            System.out.println(i + 1 + ". " + shift.values()[i].toString());
                        }
                        Integer constraintShift = myScanner.nextInt();
                        if (inBounds(shift.values().length, constraintShift) && inBounds(days.values().length, constraintDay)) {
                            Pair<String, String> p = new Pair(days.values()[constraintDay - 1].toString(), shift.values()[constraintShift - 1].toString());
                            emp.addConstraints(ID, p);
                        } else {
                            System.out.println("action was not valid");
                        }
                        System.out.print("any more constraints? choose [Y/N]: ");
                        String ans = myScanner.next();
                        if (!ans.equals("Y")) {
                            moreConstraints = false;
                        }
                    }
                    break;
                case 6:
                    boolean toContinue = true;
                    Employee e = emp.getEmployeeMap().get(ID);
                    if (e.getConstrains().isEmpty()) {
                        System.out.println("the employee does not have constraints");
                    } else {
                        while (toContinue) {
                            System.out.println("choose constraint to remove from the following: ");
                            for (int i = 0; i < e.getConstrains().size(); i++) {
                                System.out.println(i + 1 + ". " + e.getConstrains().get(i).toString());
                            }
                            Integer constraint = myScanner.nextInt();
                            if (inBounds(e.getConstrains().size(), constraint)) {
                                e.removeConstraints(constraint - 1);
                            } else {
                                System.out.println("index out of bounds");
                            }
                            System.out.print("any more constraints to remove? choose [Y/N]: ");
                            String ans = myScanner.next();
                            if (!ans.equals("Y")) {
                                toContinue = false;
                            }
                        }
                    }
                    break;
                case 7:
                    boolean moreRoles = true;
                    while (moreRoles) {
                        System.out.println("choose role from the following: ");
                        for (int i = 0; i < roleOptions.values().length; i++) {
                            System.out.println(i + 1 + ". " + roleOptions.values()[i].toString());
                        }
                        Integer role = myScanner.nextInt();
                        if (inBounds(roleOptions.values().length, role)) {
                            String toAdd = roleOptions.values()[role - 1].toString();
                            emp.addRole(ID, toAdd);
                        } else {
                            System.out.println("index out of bounds");
                        }
                        System.out.print("any more roles? choose [Y/N]: ");
                        String ans = myScanner.next();
                        if (!ans.equals("Y")) {
                            moreRoles = false;
                        }
                    }
                    break;
                case 8:
                    boolean toC = true;
                    Employee e1 = emp.getEmployeeMap().get(ID);
                    while (toC) {
                        System.out.println("choose role to remove from the following: ");
                        for (int i = 0; i < e1.getRoles().size(); i++) {
                            System.out.println(i + 1 + ". " + e1.getRoles().get(i));
                        }
                        Integer role = myScanner.nextInt();
                        if (inBounds(e1.getRoles().size(), role)) {
                            if (e1.getRoles().size() != 1) {
                                e1.removeRole(role - 1);
                            } else {
                                System.out.println("role list can not be empty, action was not committed");
                            }
                        } else
                            System.out.println("index out of bounds, action was not committed");
                        System.out.print("any more roles to remove? choose [Y/N]: ");
                        String ans = myScanner.next();
                        if (!ans.equals("Y")) {
                            toC = false;
                        }
                    }
                    break;
                case 9:
                    exit = true;
                    break;
                case 10:
                    System.out.println("wrong ID");
                    break;
                default:
                    System.out.println("choice out of bounds");
                    break;
            }
        }
    }

    private static boolean inBounds(Integer arr, Integer index) {
        return index >= 0 && index <= arr;
    }

    private static WorkingSchedule Add_Shift() {
        Scanner myScanner = new Scanner(System.in);
        System.out.print("do you wish to proceed? choose [Y/N]: ");
        String exit = myScanner.next();
        if (!exit.equals("Y")) {
            return null;
        }
        System.out.print("enter date as so dd/mm/yyyy: ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String date = myScanner.next();
        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(date, formatter);
        System.out.println("choose shift kind from the following: ");
        for (int i = 0; i < shift.values().length; i++) {
            System.out.println(i + 1 + ". " + shift.values()[i].toString());
        }
        Integer shiftKind = myScanner.nextInt();
        if (!inBounds(shift.values().length, shiftKind) || localDate.compareTo(LocalDate.now()) <= 0) {
            System.out.println("shift kind or date are invalid");
            return null;
        }
        WorkingSchedule ws = new WorkingSchedule(localDate, shift.values()[shiftKind-1].toString());
        return ws;
    }

    private static void Edit_Employee_In_Shift(ScheduleHistory scheduleHistory, EmployeeController employeeController) {
        Scanner myScanner = new Scanner(System.in);
        System.out.print("do you wish to proceed? choose [Y/N]: ");
        String exit = myScanner.next();
        if (!exit.equals("Y")) {
            return;
        }
        System.out.print("enter date as so dd/mm/yyyy: ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String date = myScanner.next();
        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(date, formatter);
        System.out.println("choose shift kind from the following: ");
        for (int i = 0; i < shift.values().length; i++) {
            System.out.println(i + 1 + ". " + shift.values()[i].toString());
        }
        Integer shiftKind = myScanner.nextInt();
        if (!inBounds(shift.values().length, shiftKind) || localDate.compareTo(LocalDate.now()) <= 0) {
            System.out.println("shift kind or date are invalid");
            return;
        }
        WorkingSchedule ws = scheduleHistory.getShift(localDate, shift.values()[shiftKind-1].toString());
        if (ws == null) {
            System.out.println("WorkingSchedule is not in record");
            return;
        }
        System.out.println("choose an action: ");
        for (int i = 0; i < actionInShift.values().length; i++) {
            System.out.println(i + 1 + ". " + actionInShift.values()[i].toString());
        }
        int choice = myScanner.nextInt();
        Employee e = null;
        if (choice > 0 && choice < actionInShift.values().length) {
            System.out.print("enter employee ID: ");
            Integer ID = myScanner.nextInt();
            if (employeeController.validID(ID)) {
                System.out.println("employee does not exist");
                choice = 6;
            } else {
                e = employeeController.getEmployeeMap().get(ID);
            }
        }
        switch (choice) {
            case 1:
                System.out.println("choose a roll from the following:");
                for (int i = 0; i < e.getRoles().size(); i++) {
                    System.out.println(i + 1 + ". " + e.getRoles().get(i));
                }
                Integer index = myScanner.nextInt();
                if (inBounds(e.getRoles().size(), index)) {
                    if(!ws.addWorkersToShift(e.getRoles().get(index - 1), e))
                        System.out.println("the employee is unavailable");
                } else {
                    System.out.println("index out of bound");
                }
                break;
            case 2:
                if (!ws.removeEmployeeFromShift(e)) {
                    System.out.println("action was not committed, check your input");
                }
                break;
            case 3:
                System.out.println("choose a roll from the following:");
                for (int i = 0; i < e.getRoles().size(); i++) {
                    System.out.println(i + 1 + ". " + e.getRoles().get(i));
                }
                Integer roleIndex = myScanner.nextInt();
                if (inBounds(e.getRoles().size(), roleIndex)) {
                    if(!ws.changeEmployeeRole(e,e.getRoles().get(roleIndex-1))){
                        System.out.println("employee is not apart of the shift");
                    }
                } else {
                    System.out.println("action was not committed, index out of bound");
                }
                break;
            default:
                break;
        }
    }

    private static void LoadSystem(ScheduleHistory ws, EmployeeController emp){
        emp.addFakeEmployees();
        ws.addFakeShifts();
    }

    private static void Delete_Employee(ScheduleHistory scheduleHistory, EmployeeController employeeController){
        Scanner myScanner = new Scanner(System.in);
        System.out.print("do you wish to proceed? choose [Y/N]: ");
        String exit = myScanner.next();
        if (!exit.equals("Y")) {
            return;
        }
        System.out.print("enter employee ID: ");
        Integer ID = myScanner.nextInt();
        if (ID>=0 && !employeeController.validID(ID)){
            scheduleHistory.deleteEmployeeFromShift(employeeController.getEmployeeMap().get(ID));
            employeeController.deleteEmployee(ID);
            return;
        }
        System.out.println("Employee does not exit in record");
    }

    private static void Delete_Shift(ScheduleHistory scheduleHistory){
        Scanner myScanner = new Scanner(System.in);
        System.out.print("do you wish to proceed? choose [Y/N]: ");
        String exit = myScanner.next();
        if (!exit.equals("Y")) {
            return;
        }
        System.out.print("enter date as so dd/mm/yyyy: ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String date = myScanner.next();
        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(date, formatter);
        System.out.println("choose shift kind from the following: ");
        for (int i = 0; i < shift.values().length; i++) {
            System.out.println(i + 1 + ". " + shift.values()[i].toString());
        }
        Integer shiftKind = myScanner.nextInt();
        if (!inBounds(shift.values().length, shiftKind) || localDate.compareTo(LocalDate.now()) <= 0) {
            System.out.println("shift kind or date are invalid");
            return;
        }
        if(!scheduleHistory.removeShift(localDate, shift.values()[shiftKind-1].toString())){
            System.out.println("shift is not in record");
        }
    }

}
