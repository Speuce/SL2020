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
}
