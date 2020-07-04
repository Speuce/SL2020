package main.java.lucia.client.content.employee.type;

import main.java.lucia.client.content.employee.Shift;

import java.util.SortedSet;

/**
 * Any object which can have 'shifts'
 * @author Matthew Kwiatkowski
 */
public class ShiftWorker extends PermissionHolder{

    /**
     * Past shifts worked by the employee this is for calculating hours and pay TODO Auto Delete
     * shifts after they have been paid.
     */
    private SortedSet<Shift> pastShifts;

    /**
     * The rate of pay for the employee, default == 1135
     */
    private long payRate;

    /**
     * If the employee is currently working a shift, this won't be null
     */
    private Shift currentShift = null;

    /**
     * Ends the current shift if possible, else the user is informed that the shift that they
     * attempted to end was unable to be ended since the employee was not currently working
     */
    public void endShift() {
        // Instead of assert, inform the user (kos or whoever) that the employee isn't working
        if (currentShift == null) {
            // TODO Instead of assert, inform the user (kos or whoever) that the employee isn't working
        } else {
            currentShift.endShift();
            pastShifts.add(currentShift);
            //TODO update server
            currentShift = null;
        }
    }

    /**
     * Starts a new shift if the employee is not currently working
     */
    public void startShift() {
        if (currentShift != null) {
            // TODO Instead of assert, inform the user that they are already working/started their shift
        } else {
            currentShift = new Shift();
        }
    }
}
