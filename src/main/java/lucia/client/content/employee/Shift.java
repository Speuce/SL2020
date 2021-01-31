package main.java.lucia.client.content.employee;

import main.java.lucia.client.content.time.ClientTime;
import main.java.lucia.client.content.time.TimeFormat;

import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a shift (current or past) worked by an employee
 * @author Matt Kwiatkowski
 */
public class Shift implements Comparable<Shift> {

    /**
     * The specific id of this pizza on the server.
     * DO NOT SET. Let Gson do that.
     */
    private final int rowNum = -1;

    /**
     * A list of entries within the shift, where the first line is always the start time
     * if the size is even, the employee is not currently working
     * if the size is odd, the employee is currently working.
     */
    private final List<ClientTime> shiftEntryList;


    public Shift(ClientTime started){
        shiftEntryList = new LinkedList<>();
        shiftEntryList.add(started);
    }
    public Shift(){
        this(new ClientTime(TimeFormat.FORMATTER_ISO_STANDARD, ZoneId.systemDefault()));
    }

    /**
     * @return A list of entries within the shift, where the first line is always the start time
     * if the size is even, the employee is not currently working
     * if the size is odd, the employee is currently working.
     */
    public List<ClientTime> getShiftEntryList() {
        return shiftEntryList;
    }

    /**
     * @return the time that this given shift was started.
     */
    public ClientTime getStarted(){
        return shiftEntryList.get(0);
    }

    /**
     *
     * @return a nice formatted {@link String} rounded to 2 decimal places
     * representing the hours worked during this shift
     */
    public String getHoursWorkedStr(){
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(getMinutesWorked()/60.0);
    }

    /**
     * Adds the current time in to the shift entires;
     */
    public void addEntry(){
        shiftEntryList.add(new ClientTime(TimeFormat.FORMATTER_ISO_STANDARD, ZoneId.systemDefault()));
    }

    /**
     * Counts the entries in the shift list and calculates total worked time.
     * @return a long representing the minutes worked. Is NOT rounded.
     */
    public long getMinutesWorked(){
        int mins = 0;
        ClientTime i1, i2;
        Iterator<ClientTime> lister = shiftEntryList.iterator();
        while(((i1 = lister.next())!= null) && ((i2) = lister.next()) != null){
            mins += ChronoUnit.MINUTES.between(i1.toLocalDate(), i2.toLocalDate());
        }
        return mins;
    }

    @Override
    public int compareTo(Shift o) {
        return o.getStarted().toLocalDate().compareTo(this.getStarted().toLocalDate());
    }
}