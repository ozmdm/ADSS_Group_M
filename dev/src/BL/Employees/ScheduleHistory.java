package BL.Employees;

import javafx.util.Pair;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class ScheduleHistory {
    private HashMap<Pair<LocalDate,String>,WorkingSchedule> record;//string is the Business_Layer.kind of shift, ex: morning
    private static ScheduleHistory scheduleController = null;

    private ScheduleHistory(){
        record = new HashMap<>();
    }

    public static ScheduleHistory getInstance()
    {
        if(scheduleController == null)
            scheduleController = new ScheduleHistory();
        return scheduleController;
    }

    public HashMap<Pair<LocalDate,String>,WorkingSchedule> getRecord()
    {
        return record;
    }

    public boolean addWorkingSchedule(WorkingSchedule ws) throws SQLException {
        if (validWorkingSchedule(ws)) {
            //record.put(new Pair<>(ws.getDate(), ws.getKind()), ws);
            DL.Employees.WorkingSchedule.insertWorkingSchedule(new DL.Employees.DTO.Shifts(ws.getDate(),ws.getKind(),ws.getShiftManager()));
            return true;
        }
        return false;
    }

    public boolean addWorkersToShift(String role, int id, LocalDate date, String Kind) throws SQLException {
        if(!shiftContainsEmployee(date,Kind,id)&&employeeCanWork(date,Kind,id)) {
            DL.Employees.Employee.AddEmployeeToShift(id, role, Date.valueOf(date),Kind);
            return true;
        }
        return false;
    }

    private boolean shiftContainsEmployee(LocalDate date,String kind, int id) throws SQLException {
        return DL.Employees.WorkingSchedule.ShiftContainsEmployee(date,kind,id);
    }

    private boolean employeeCanWork(LocalDate date,String kind, int id) throws SQLException {
        return DL.Employees.WorkingSchedule.EmployeeCanWork(date.getDayOfWeek().toString(),kind,id);
    }

    public boolean validWorkingSchedule(WorkingSchedule ws) throws SQLException { // check if the shift already exists
       /* for (Pair<LocalDate,String> p : record.keySet())
        {
            if(p.getKey().equals(ws.getDate()) && p.getValue().equals(ws.getKind()))
                return false;
        }*/
        return DL.Employees.WorkingSchedule.validShift(ws.getDate(),ws.getKind());
    }

    public boolean removeEmployeeFromShift(int id, LocalDate date, String Kind) throws SQLException {
        if(shiftContainsEmployee(date,Kind,id)) {
            DL.Employees.Employee.removeEmployeeFromShift(id, Date.valueOf(date),Kind);
            return true;
        }
        return false;
    }

    public boolean ChangeEmployeeRole(int id, String role, LocalDate date, String Kind) throws SQLException {
        if(shiftContainsEmployee(date,Kind,id)) {
            DL.Employees.Employee.ChangeEmployeeRole(id, role, Date.valueOf(date),Kind);
            return true;
        }
        return false;
    }

    public boolean shiftOccur(Pair<LocalDate,String> p)
    {
        for (Pair<LocalDate,String> p1 : record.keySet())
        {
            if(p1.getKey().equals(p.getKey()) && p1.getValue().equals(p.getValue()))
                return (record.get(p1).getShiftManager() && record.get(p1).getDate().compareTo(LocalDate.now()) <= 0);
        }
        return false;
    }

    public boolean getShift(LocalDate date,String kind) throws SQLException {
        return DL.Employees.WorkingSchedule.CheckShift(date,kind);
    }

    public void deleteEmployeeFromShift(int id) throws SQLException {
        DL.Employees.WorkingSchedule.deleteEmployeeFromShifts(id);
    }

    public void addFakeShifts() throws SQLException {
        WorkingSchedule w = new WorkingSchedule(LocalDate.of(2020,5,27),"Evening");
        addWorkingSchedule(w);
        WorkingSchedule w1 = new WorkingSchedule(LocalDate.of(2020,6,7),"Morning");
        addWorkingSchedule(w1);
        WorkingSchedule w2 = new WorkingSchedule(LocalDate.of(2020,6,12),"Evening");
        addWorkingSchedule(w2);
        WorkingSchedule w3 = new WorkingSchedule(LocalDate.of(2020,5,22),"Morning");
        addWorkingSchedule(w3);
        WorkingSchedule w4 = new WorkingSchedule(LocalDate.of(2020,5,15),"Evening");
        addWorkingSchedule(w4);
    }

    public String toString(){
        HashMap<Pair<Date, String>, LinkedList<Pair<Integer, String>>> h = null;
        try {
            h = DL.Employees.WorkingSchedule.displayShifts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String ret = "";
        for(Map.Entry<Pair<Date, String>, LinkedList<Pair<Integer, String>>> entry : h.entrySet())
        {
            String date =  entry.getKey().getKey().toString();
            String kind = entry.getKey().getValue();
            ret += date + ": " + kind + "\n" + entry.getValue().toString() + "\n";
        }
        return ret;
    }

    public void removeShift(LocalDate date, String kind) throws SQLException {
        DL.Employees.WorkingSchedule.removeShift(date,kind);
    }

    public boolean checkForDriver(int id, String shift,LocalDate date) throws Exception {
        try {
            if(!DL.Employees.WorkingSchedule.CheckForDriver(id,date,shift))
                return false;
            return true;
        }
       catch (Exception e)
       {
           throw e;
       }
    }

    public boolean checkforStoreKeeper(String shift, LocalDate date) throws Exception {
       try{
           if(!DL.Employees.WorkingSchedule.checkStoreKeper(date,shift))
               return false;
           return true;
       }catch (Exception e)
       {
           throw e;
       }
    }

    public boolean addWorkingSchedule(LocalDate date, String kind) throws SQLException {
        WorkingSchedule ws = new WorkingSchedule(date,kind);
        if (validWorkingSchedule(ws)) {
            record.put(new Pair<>(date, kind), ws);
            DL.Employees.WorkingSchedule.insertWorkingSchedule(new DL.Employees.DTO.Shifts(ws.getDate(),ws.getKind(),ws.getShiftManager()));
            return true;
        }
        return false;
    }


}
