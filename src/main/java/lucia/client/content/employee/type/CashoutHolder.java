package main.java.lucia.client.content.employee.type;

import main.java.lucia.client.content.payment.cashout.CashOutOfTill;
import main.java.lucia.client.content.payment.cashout.Cashout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Any Employee which can hold a cashout (and permissions)
 * @author Matthew Kwiatkowski
 */
public abstract class CashoutHolder extends Employee{

    /**
     * The default float given to each cashout
     */
    private static final long FLOAT = 83000L;

    /**
     * The cashout that is held today by this employee.
     */
    private final Cashout cashout;

    /**
     * Cash taken out of this cashout for various reasons
     */
    private final List<CashOutOfTill> outOfTills;

    /**
     * List of the orders from today in this cashout
     */
    private final List<Integer> ordersTaken;

    /**
     * The name that will be displayed with the cashout.
     */
    private final String displayName;

    public CashoutHolder(String name, int employeeID, String password, long pay, String displayName) {
        super(name, employeeID, password, pay);
        outOfTills = new LinkedList<>();
        ordersTaken = new ArrayList<>();
        this.displayName = displayName;
        cashout = new Cashout(this);
    }

    /**
     * This is called when the employee's cashout is being re-calculates.
     * In this method, the cashout should be modified to fit the given
     * employee's specifications
     */
    public abstract void onCashoutCalc();

    /**
     * @return The name that will be displayed with the cashout.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets this employee's cashout.
     */
    public Cashout getCashout(){
        return cashout;
    }
}
