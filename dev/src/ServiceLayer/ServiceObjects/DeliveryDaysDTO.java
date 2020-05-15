package ServiceLayer.ServiceObjects;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDaysDTO {

    private List<DayOfWeek> dayOfWeeks;

    public DeliveryDaysDTO(List<DayOfWeek> dayOfWeek)
    {

        dayOfWeeks = new ArrayList<>();
        for (DayOfWeek day : dayOfWeek)
        {
            dayOfWeek.add(day);
        }
    }

    public List<DayOfWeek> dayOfWeeks()
    {
        return this.dayOfWeeks;
    }
}
