package main.java.lucia.client.protocol.packet.outgoing.employee;

import main.java.lucia.client.content.employee.EmployeeGson;
import main.java.lucia.client.content.employee.type.Employee;
import main.java.lucia.client.protocol.packet.OutgoingAuthPacket;
import main.java.lucia.net.protocol.opcode.OpcodeConstants;

/**
 * Packet sent by client to server to indicate a new employee being created.
 * @author Matthew Kwiatkowski
 */
public class PacketOutNewEmployee extends OutgoingAuthPacket {

    /**
     * The employee that is newly created.
     */
    private Employee newEmployee;

    public PacketOutNewEmployee(Employee newEmployee) {
        super(OpcodeConstants.NEW_EMPLOYEE_OPCODE);
        this.newEmployee = newEmployee;
    }

    /**
     * The employee that is newly created.
     */
    public Employee getNewEmployee() {
        return newEmployee;
    }

    /**
     * The employee that is newly created.
     */
    public void setNewEmployee(Employee newEmployee) {
        this.newEmployee = newEmployee;
    }

    /**
     * Used for specifying serialization behaviour of this packet
     *
     * @return the entire packet, represented as a json string
     */
    @Override
    public String serialize() {
        return EmployeeGson.EMPLOYEE_GSON.toJson(this);
    }

}
