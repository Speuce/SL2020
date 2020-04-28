package main.java.lucia.client.content.employee;

import main.java.lucia.client.content.time.ClientTime;
import main.java.lucia.client.content.time.TimeFormat;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

/**
 * Represents a shift (current or past) worked by an employee
 * @author Matt Kwiatkowski
 */
public class Shift implements Comparable<Shift> {

    /**
     * The specific id of this pizza on the server.
     * DO NOT SET. Let Gson do that.
     */
    private int rowNum = -1;

    /**
     * Started is a {@link ClientTime} representing the time and ClientTime that the employee logged in
     * Ended is a {@link ClientTime} representing the time and ClientTime that the employee clocked out
     */
    private ClientTime started;
    private ClientTime ended;

    /**
     * True if the shift is still in progress (Ended will be null in this case)
     */
    private boolean inProgress;

    public Shift(ClientTime started, ClientTime ended){
        this.started = started;
        this.ended = ended;
        inProgress = false;
    }
    public Shift(ClientTime started){
        inProgress = true;
        this.started = started;
    }
    public Shift(){
        this(new ClientTime(TimeFormat.FORMATTER_ISO_STANDARD, ZoneId.systemDefault()));
    }

    public ClientTime getStarted() {
        return started;
    }

    public ClientTime getEnded() {
        return ended;
    }

    /**
     * Ends the current shift. Note that {@code inProgress} should
     * be checked before using this method to verify that the
     * shift is still in progress.
     *
     * @throws RuntimeException if the employee has already finished this shift
     */
    public void endShift() {
        if(!this.inProgress) {
            this.ended = new ClientTime(TimeFormat.FORMATTER_ISO_STANDARD, ZoneId.systemDefault());
        } else {
            throw new RuntimeException("Employee's shift is not in progress, cannot end shift.");
        }
    }

    /**
     *
     * @return {@code true} if the shift is still in progress
     * ended will be null in this case.
     */
    public boolean isInProgress() {
        return inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    /**
     *
     * @return a nice formatted {@link String} rounded to 2 decimal places
     * representing the hours worked during this shift
     */
    public String getHoursWorkedStr(){
        DecimalFormat df = new DecimalFormat("#0.00");
        if(this.inProgress){
            long workedMins = ChronoUnit.MINUTES.between(LocalDateTime.now(), started.getThisTime());
            return df.format(workedMins/60.0);
        }else{
            long workedMins = ChronoUnit.MINUTES.between(ended.getThisTime(), started.getThisTime());
            return df.format(workedMins/60.0);
        }

    }

    /**
     *
     * @return a long representing the minutes worked. Is NOT rounded.
     */
    public long getMinutesWorked(){
        if(this.inProgress){
            long workedMins = ChronoUnit.MINUTES.between(LocalDateTime.now(), started.getThisTime());
            return workedMins;
        }else{
            long workedMins = ChronoUnit.MINUTES.between(ended.getThisTime(), started.getThisTime());
            return workedMins;
        }

    }

    @Override
    public int compareTo(Shift o) {
        return o.getStarted().getThisTime().compareTo(this.getStarted().getThisTime());
    }
}