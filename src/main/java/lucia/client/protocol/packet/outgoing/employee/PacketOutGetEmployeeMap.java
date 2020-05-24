package main.java.lucia.client.protocol.packet.outgoing.employee;

import main.java.lucia.client.protocol.packet.OutgoingAuthPacket;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import main.java.lucia.net.protocol.opcode.OpcodeConstants;

/**
 * Packet sent out by client to request the map of employees
 * @author Matthew Kwiatkowski
 */
public class PacketOutGetEmployeeMap extends OutgoingAuthPacket {

    public PacketOutGetEmployeeMap() {
        super(OpcodeConstants.GET_EMPLOYEE_MAP_OPCODE);
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
}
