package main.java.lucia.client.content.employee.type;

/**
 * Represents a server.
 * Servers have cashouts.
 * @author Matthew Kwiatkowski
 */
public class Server extends CashoutHolder{

    //TODO tables

    public Server(String name, int employeeID, String password, long pay, String displayName) {
        super(name, employeeID, password, pay, displayName);
    }

    /**
     * This is called when the employee's cashout is being re-calculates.
     * In this method, the cashout should be modified to fit the given
     * employee's specifications
     */
    @Override
    public void onCashoutCalc() {

    }
}
