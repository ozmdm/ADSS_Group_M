package Business_Layer;

import javafx.util.Pair;

import java.time.LocalDate;
import java.util.HashMap;

public class ScheduleHistory {
    public static HashMap<Pair<LocalDate,String>,WorkingSchedule> record;//string is the Business_Layer.kind of shift, ex: morning

    public ScheduleHistory(){
        record = new HashMap<>();
    }

    public HashMap<Pair<LocalDate,String>,WorkingSchedule> getRecord()
    {
        return record;
    }

    public boolean addWorkingSchedule(WorkingSchedule ws) {
        if (validWorkingSchedule(ws)) {
            ScheduleHistory.record.put(new Pair<>(ws.getDate(), ws.getKind()), ws);
            return true;
        }
        return false;
    }

    private boolean validWorkingSchedule(WorkingSchedule ws){ // check if the shift already exists
        for (Pair<LocalDate,String> p : record.keySet())
        {
            if(p.getKey().equals(ws.getDate()) && p.getValue().equals(ws.getKind()))
                return false;
        }
        return true;
    }

    private boolean shiftOccur(Pair<LocalDate,String> p)
    {
        for (Pair<LocalDate,String> p1 : record.keySet())
        {
            if(p1.getKey().equals(p.getKey()) && p1.getValue().equals(p.getValue()))
                return (record.get(p1).getShiftManager() && record.get(p1).getDate().compareTo(LocalDate.now()) <= 0);
        }
        return false;
    }

    public WorkingSchedule getShift(LocalDate date,String kind)
    {
        for (Pair<LocalDate,String> p1 : record.keySet())
        {
            if(p1.getKey().equals(date) && p1.getValue().equals(kind))
                return record.get(p1);
        }
        return null;
    }

    public void deleteEmployeeFromShift(Employee e) {
        for (Pair<LocalDate,String> p: record.keySet()){
            WorkingSchedule ws = record.get(p);
            if (ws.getDate().compareTo(LocalDate.now()) > 0) {//removing only from future shifts
                ws.removeEmployeeFromShift(e);
            }
        }
    }

    public void addFakeShifts()
    {
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
        String ret = "";
        for(WorkingSchedule ws: record.values())
        {
            ret += ws.toString() + "\n";
        }
        return ret;
    }

    public boolean removeShift(LocalDate date, String kind){
        for (Pair<LocalDate,String> p : record.keySet()) {
            if(p.getKey().compareTo(date)==0&&p.getValue().equals(kind)){
                record.remove(p);
                return true;
            }
        }
        return false;
    }
}
