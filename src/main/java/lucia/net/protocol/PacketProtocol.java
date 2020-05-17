package main.java.lucia.net.protocol;

import main.java.lucia.client.protocol.packet.in.customer.PacketInFoundCustomer;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import main.java.lucia.net.protocol.opcode.OpcodeConstants;

/**
 * Implemented protocol to deal with the new packet system
 * @author Matthew Kwiatkowski
 */
public class PacketProtocol extends Protocol{
    /**
     * Registers all Packets as necessary
     */
    @Override
    protected void registerAll() {
        register(OpcodeConstants.CUSTOMER_FOUND_OPCDOE, PacketInFoundCustomer.class, GsonTypeFactory.BASIC_GSON);
    }
}
