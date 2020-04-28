package main.java.lucia.client.protocol.message.codec.Employee;

import java.util.Map;

/**
 * Codec for employee map requests
 * @author Matt Kwiatkowski
 */
public class EmployeeMapCodec {

    /**
     * the employee map to be sent
     */
    private Map<Integer, String> employeeMap;

    public EmployeeMapCodec(Map<Integer, String> employeeMap) {
        this.employeeMap = employeeMap;
    }

    public Map<Integer, String> getEmployeeMap() {
        return employeeMap;
    }

    public void setEmployeeMap(Map<Integer, String> employeeMap) {
        this.employeeMap = employeeMap;
    }
}
