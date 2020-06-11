package main.java.lucia.client.content.order.discount.impl.times;

import main.java.lucia.client.content.time.TimeFormat;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * An object that has two {@link java.time.LocalTime} objects
 * and can tell whether or not a given Localtime is between the two.
 */
public class LocalTimeRange {

    /**
     * The times defining the range, respectively.
     */
    private final LocalTime from;
    private final LocalTime to;

    public LocalTimeRange(LocalTime from, LocalTime to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Test if a date/time is within the range
     * @return true if the date/time is within the range, false otherwise.
     */
    public boolean isWithin(LocalDateTime time){
        return this.isWithin(time.toLocalTime());
    }

    /**
     * Test if a date/time is within the range
     * @return true if the date/time is within the range, false otherwise.
     */
    public boolean isWithin(LocalTime time){
        return time.isAfter(from) && time.isBefore(to);
    }

    public LocalTime getFrom() {
        return from;
    }

    public LocalTime getTo() {
        return to;
    }

    public void print(PrintStream out){
        out.print("From: " + getFrom().format(TimeFormat.FORMATTER_12_HOUR_TIME.getFormat()));
        out.print(" To: " + getTo().format(TimeFormat.FORMATTER_12_HOUR_TIME.getFormat()));
    }
}
