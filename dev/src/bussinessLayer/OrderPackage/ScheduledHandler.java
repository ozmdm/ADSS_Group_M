package bussinessLayer.OrderPackage;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import DataAccessLaye.Repo;
import ServiceLayer.ServiceObjects.ScheduledDTO;

public class ScheduledHandler {
    private static ScheduledHandler scHandler = null;
    private Timer timer;

    private ScheduledHandler() {
        timer = new Timer();
    }

    public static ScheduledHandler getInstance() {
        if (scHandler != null)
            return scHandler;
        scHandler = new ScheduledHandler();
        return scHandler;
    }

    public void start() throws SQLException {
        List<ScheduledDTO> list = Repo.getInstance().getAllScheduled();
        for (ScheduledDTO scheduledDTO : list) {
            Date nextDate = getNextDateToCreateOrder(scheduledDTO.getDay());
            timer.schedule(new TimerTaskImpl(scHandler.getTimer(),nextDate, scheduledDTO), java.sql.Timestamp.valueOf(LocalDateTime.now().plusMinutes(1)));
        }
    }

    private Date getNextDateToCreateOrder(DayOfWeek day) {
        int difference = Math.abs(day.getValue()-Calendar.getInstance().get(Calendar.DAY_OF_WEEK))-1;
        return java.sql.Timestamp.valueOf(LocalDateTime.now().plusDays(difference));
    }

    /**
     * @return the timer
     */
    public Timer getTimer() {
        return timer;
    }

}