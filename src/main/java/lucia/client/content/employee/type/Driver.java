package main.java.lucia.client.content.employee.type;

public class Driver extends Employee {

    /**
     * The specific id of this pizza on the server.
     * DO NOT SET. Let Gson do that.
     */
    private int rowNum = -1;

    /**
     * Creates a default employee earning minimum wage
     * This should only be allowed by managers.
     * Managers should give the employee a default password, that should be changed asap.
     *
     * @param name       the employee's name
     * @param employeeID the employee's ID (4 or 5 digits)
     * @param password   the unhashed default password of the employee
     */
    private boolean loggedIn = false;

    public Driver(String name, int employeeID, String password) {
        super(name, employeeID, password, true);
        this.setPayRate(750);
    }

    public void loggedIn(boolean b) {
        loggedIn = b;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}
