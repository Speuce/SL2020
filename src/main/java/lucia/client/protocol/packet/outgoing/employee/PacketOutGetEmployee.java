package main.java.lucia.client.protocol.packet.outgoing.employee;

import main.java.lucia.client.protocol.packet.OutgoingAuthPacket;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import main.java.lucia.net.protocol.opcode.OpcodeConstants;

/**
 * Outgoing packet requesting information of an employee with the given id.
 * @author Matthew Kwiatkowski
 */
public class PacketOutGetEmployee extends OutgoingAuthPacket {

    /**
     * The id of the Employee TO be searched for.
     */
    private int employeeID;

    public PacketOutGetEmployee(int employeeID) {
        super(OpcodeConstants.GET_EMPLOYEE_OPCODE);
        this.employeeID = employeeID;
    }

    /**
     * Used for specifying serialization behaviour of this packet
     *
     * @return the entire packet, represented as a json string
     */
    @Override
    public String serialize() {
        return GsonTypeFactory.BASIC_GSON.toJson(this, this.getClass());
    }

    /**
     * The id of the Employee TO be searched for.
     */
    public int getEmployeeID() {
        return employeeID;
    }

    /**
     * The id of the Employee TO be searched for.
     */
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }
}
