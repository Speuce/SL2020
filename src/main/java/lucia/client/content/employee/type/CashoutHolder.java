package main.java.lucia.client.content.employee.type;

import main.java.lucia.client.content.payment.cashout.CashOutOfTill;
import main.java.lucia.client.content.payment.cashout.Cashout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Any object which can hold a cashout (and permissions)
 * @author Matthew Kwiatkowski
 */
public class CashoutHolder extends PermissionHolder{

    /**
     * The default float given to each cashout
     */
    private static final long FLOAT = 83000L;

    /**
     * Cash taken out of this cashout for various reasons
     */
    private List<CashOutOfTill> outOfTills;

    /**
     * List of the orders from today in this cashout
     */
    private List<Integer> ordersTaken;

    public CashoutHolder() {
        super();
        outOfTills = new LinkedList<>();
        ordersTaken = new ArrayList<>();
    }

    /**
     * @return Cash taken out of this cashout for various reasons
     */
    public List<CashOutOfTill> getOutOfTills() {
        return outOfTills;
    }

    /**
     * @return List of the orders from today in this cashout
     */
    public List<Integer> getOrdersTaken() {
        return ordersTaken;
    }

    public Cashout createCashout() {
        return new Cashout(ordersTaken, outOfTills, this, FLOAT);
    }
}
