package main.java.lucia.client.protocol.packet.outgoing.customer;

import main.java.lucia.client.protocol.packet.OutgoingAuthPacket;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import main.java.lucia.net.protocol.opcode.OpcodeConstants;

/**
 * Outgoing request to the server to find customers with a phone number similar to
 * the one provided.
 * @author Matthew Kwiatkowski
 */
public class PacketOutFindCustomerByPhone extends OutgoingAuthPacket {

    /**
     * The phone number requested for searching
     */
    private int phone;

    public PacketOutFindCustomerByPhone(int phone) {
        super(OpcodeConstants.SEARCH_CUSTOMER_PHONE_OPCODE);
        this.phone = phone;
    }

    /**
     * The phone number requested for searching
     */
    public int getPhone() {
        return phone;
    }

    /**
     * The phone number requested for searching
     */
    public void setPhone(int phone) {
        this.phone = phone;
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
