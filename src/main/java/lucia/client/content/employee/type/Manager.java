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


    public Manager(String name, int employeeID, String password, long pay) {
        super(name, employeeID, password, pay);
    }
}
