package main.java.lucia.client.content.employee;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.lucia.client.content.employee.type.Driver;
import main.java.lucia.client.content.employee.type.Employee;
import main.java.lucia.client.content.employee.type.Manager;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import main.java.lucia.util.gson.RuntimeTypeAdapterFactory;

/**
 * {@link Gson} for serializing {@link main.java.lucia.client.content.employee.type.Employee} and all subclasses.
 * @author Matthew Kwiatkowski
 */
public class EmployeeGson {

    /**
     * Get the gson for serializing employees.
     */
    public static Gson EMPLOYEE_GSON = getEmployeeGson();

    /**
     * build the gson for serializing employees.
     */
    private static Gson getEmployeeGson(){
        GsonBuilder builder = new GsonBuilder();
        GsonTypeFactory.addExclusionPolicy(builder);
        RuntimeTypeAdapterFactory<Employee> add = RuntimeTypeAdapterFactory.of(Employee.class)
                .registerSubtype(Manager.class, "Manager")
                .registerSubtype(Driver.class, "Driver")
                .registerSubtype(Employee.class, "Employee");
        builder.registerTypeAdapterFactory(add);
        builder.serializeNulls();
        return builder.create();
    }
}
