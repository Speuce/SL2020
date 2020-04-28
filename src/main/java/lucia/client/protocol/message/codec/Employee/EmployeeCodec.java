package main.java.lucia.client.protocol.message.codec.Employee;

/**
 * a format to store employee info
 * @author Matt Kwiatkowski
 */
public class EmployeeCodec {

    /**
     * The JSON containing the employee
     */
    private String EmployeeJSON;

    /**
     * The employee's name. Used for mapping
     */
    private String name;

    /**
     * The employee's Id. Used for mapping
     */
    private Integer id;

    public EmployeeCodec(String employeeJSON, String name, Integer id) {
        EmployeeJSON = employeeJSON;
        this.name = name;
        this.id = id;
    }


    public String getEmployeeJSON() {
        return EmployeeJSON;
    }

    public void setEmployeeJSON(String employeeJSON) {
        EmployeeJSON = employeeJSON;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
