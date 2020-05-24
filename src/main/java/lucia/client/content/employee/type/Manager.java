package main.java.lucia.client.content.employee.type;

/** Represents a manager, which is an employee
 *
 * @author Matt Kwiatkowski
 */
public class Manager extends Employee {

//    /**
//     * The specific id of this pizza on the server.
//     * DO NOT SET. Let Gson do that.
//     */
//    private int rowNum = -1;

    /**
     * Creates a manager. Should only be allowed by owners
     *
     * @param name       the employee's name
     * @param employeeID the employee's ID (4 or 5 digits)
     * @param password   the unhashed default password of the employee
     */
    public Manager(String name, int employeeID, String password) {
        super(name, employeeID, password, true);
    }
}
