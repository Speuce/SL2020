package main.java.lucia.client.protocol.packet.outgoing.customer;

import main.java.lucia.client.content.customer.CustomerDetails;
import main.java.lucia.client.protocol.packet.OutgoingAuthPacket;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import main.java.lucia.net.protocol.opcode.OpcodeConstants;

/**
 * Packet sent to server to save a customer
 * @author Matthew Kwiatkowski
 */
public class PacketOutSaveCustomer extends OutgoingAuthPacket {

    /**
     * The customer to save
     */
    private CustomerDetails customer;

    public PacketOutSaveCustomer(CustomerDetails customer) {
        super(OpcodeConstants.SAVE_CUSTOMER_OPCODE);
        this.customer = customer;
    }

    /**
     * The customer to save
     */
    public CustomerDetails getCustomer() {
        return customer;
    }

    /**
     * The customer to save
     */
    public void setCustomer(CustomerDetails customer) {
        this.customer = customer;
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
