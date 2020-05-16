package main.java.lucia.client.protocol.packet.in.employee;

import main.java.lucia.client.content.employee.type.Employee;
import main.java.lucia.client.protocol.packet.IncomingAuthPacket;
import main.java.lucia.net.protocol.opcode.OpcodeConstants;

/**
 * Incoming packet to require client to update internal records of a
 * specific employee to the given employee.
 */
public class PacketInSetEmployee extends IncomingAuthPacket {

    /**
     * The employee to set
     */
    private Employee employee;


    public PacketInSetEmployee(int echoCode) {
        super(echoCode, OpcodeConstants.SET_EMPLOYEE_OPCODE);
    }
}
