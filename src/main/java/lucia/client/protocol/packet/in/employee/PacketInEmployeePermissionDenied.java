package main.java.lucia.client.protocol.packet.in.employee;

import main.java.lucia.client.protocol.packet.IncomingAuthPacket;

/**
 * Packet sent when an employee attempts an unauthorized activity
 * @author Matthew Kwiatkowski
 */
public class PacketInEmployeePermissionDenied extends IncomingAuthPacket {

    /**
     * The Employee that attempted this action
     */
    private int employee;


    public PacketInEmployeePermissionDenied(int echoCode, int opcode) {
        super(echoCode, opcode);
    }

    /**
     * The Employee that attempted this action
     */
    public int getEmployee() {
        return employee;
    }

    /**
     * The Employee that attempted this action
     */
    public void setEmployee(int employee) {
        this.employee = employee;
    }
}
