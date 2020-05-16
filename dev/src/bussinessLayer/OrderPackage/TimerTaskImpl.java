package bussinessLayer.OrderPackage;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import DataAccessLaye.Repo;
import ServiceLayer.OrderService;
import ServiceLayer.ServiceObjects.ScheduledDTO;

public class TimerTaskImpl extends TimerTask {
    private final Timer timer;
    private Date nextDate;
    private ScheduledDTO scheduled;

    public TimerTaskImpl(Timer timer, Date nextDate, ScheduledDTO scheduled){
        this.timer = timer;
        this.nextDate = nextDate;
        this.scheduled = scheduled;
    }

    @Override
    public void run() {
        if(!getspecificSchedule(scheduled.getBranchId(), scheduled.getDay().getValue(),scheduled.getSupplierId())){
            this.cancel();
            return;
        }
        if(orderExist(scheduled.getSupplierId(),scheduled.getBranchId(),nextDate)){
            return;
        }
        OrderService.getInstance().createScheduledOrder(scheduled, nextDate);
        this.nextDate = java.sql.Timestamp.valueOf(LocalDateTime.now().plusDays(7));
        //this.nextDate = getNextDateToCreateOrder(scheduled.getDay());
        timer.schedule(this, this.nextDate);

    }

    private boolean getspecificSchedule(int branchId, int day, int supplierId) {
        try{Repo.getInstance().getSpecificScheduled(branchId, day, supplierId);return true;}catch(Exception e){return false;}
    }

    private boolean orderExist(int supplierId, int branchId, Date nextDate) {
        try{Repo.getInstance().getOrderByDateSupplier(supplierId, branchId,nextDate); return true;}
        catch(Exception e){return false;}
    }

    /*private Date getNextDateToCreateOrder(DayOfWeek day) {
        int difference = Math.abs(day.getValue()-Calendar.getInstance().get(Calendar.DAY_OF_WEEK))-1;
        return java.sql.Timestamp.valueOf(LocalDateTime.now().plusDays(difference));
    }*/
    
}