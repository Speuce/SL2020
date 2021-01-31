package main.java.lucia.client.content.employee.type;

import main.java.lucia.client.content.employee.Permission;
import main.java.lucia.client.content.employee.Shift;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.SortedSet;

/**
 * Any object which can have 'shifts'
 * @author Matthew Kwiatkowski
 */
public abstract class ShiftWorker extends PermissionHolder{

    /**
     * Past shifts worked by the employee this is for calculating hours and pay TODO Auto Delete
     * shifts after they have been paid.
     */
    private final SortedSet<Shift> pastShifts;

    /**
     * The rate of pay for the employee, default == 1135
     */
    private long payRate;

    /**
     * If the employee is currently working a shift, this won't be null
     */
    private Shift currentShift = null;

    public ShiftWorker(Set<Permission> permissions, SortedSet<Shift> pastShifts, long payRate) {
        super(permissions);
        this.pastShifts = pastShifts;
        this.payRate = payRate;
    }

    /**
     * Ends the current shift if possible, else the user is informed that the shift that they
     * attempted to end was unable to be ended since the employee was not currently working
     */
    public void endShift() {
        // Instead of assert, inform the user (kos or whoever) that the employee isn't working
        if (currentShift == null) {
            // TODO Instead of assert, inform the user (kos or whoever) that the employee isn't working
        } else {
            currentShift.addEntry();
            pastShifts.add(currentShift);
            //TODO update server
            currentShift = null;
        }
    }

    /**
     * Starts/ends the break of the employee
     */
    public void startOrEndBreak(){
        if(currentShift != null){
            currentShift.addEntry();
        }else{
            //TODO error
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

    /**
     * Past shifts worked by the employee this is for calculating hours and pay TODO Auto Delete
     * shifts after they have been paid.
     */
    public SortedSet<Shift> getPastShifts() {
        return pastShifts;
    }

    /**
     * @return The rate of pay for the employee, default == 1135
     */
    public long getPayRate() {
        return payRate;
    }

    /**
     * @param payRate The rate of pay for the employee, default == 1135
     */
    public void setPayRate(long payRate) {
        this.payRate = payRate;
    }

    /**
     * If the employee is currently working a shift, this won't be null
     */
    @Nullable
    public Shift getCurrentShift() {
        return currentShift;
    }

    /**
     * Whether or not the employee is currently on shift.
     * @return true if the employee is on shift, false otherwise.
     */
    public boolean isWorking(){
        return getCurrentShift() != null;
    }
}
