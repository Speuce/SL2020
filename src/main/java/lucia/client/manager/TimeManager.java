package main.java.lucia.client.manager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Manages time.
 * @author Matthew Kwiatkowski
 */
public class TimeManager {

    /**
     * The time that the day resets
     * TODO get from server
     */
    public static final LocalTime DAY_RESET = LocalTime.of(5, 0);

    /**
     * The date/time that this store's day is over
     */
    public static LocalDateTime resetTime = getResetTime();

    /**
     * Calculates the day's reset time
     */
    private static LocalDateTime getResetTime(){
        if(LocalTime.now().isBefore(DAY_RESET)){
            return LocalDate.now().atTime(DAY_RESET);
        }
        return LocalDate.now().plusDays(1).atTime(DAY_RESET);
    }
}
