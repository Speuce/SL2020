package main.java.lucia.client.protocol.packet.in.customer;

import main.java.lucia.client.content.customer.CustomerDetails;
import main.java.lucia.client.protocol.packet.IncomingAuthPacket;
import main.java.lucia.net.protocol.opcode.OpcodeConstants;

/**
 * Packet sent from server to client after a {@link main.java.lucia.client.protocol.packet.outgoing.customer.PacketOutFindCustomerByPhone}
 * @author Matthew Kwiatkowski
 */
public class PacketInFoundCustomer extends IncomingAuthPacket {

    /**
     * The item requested for searching
     */
    private String request;

    /**
     * The customer found.
     */
    private CustomerDetails customer;

    public PacketInFoundCustomer(int echoCode, String request, CustomerDetails customer) {
        super(echoCode, OpcodeConstants.CUSTOMER_FOUND_OPCDOE);
        this.request = request;
        this.customer = customer;
    }

    /**
     * The item requested for searching
     */
    public String getRequest() {
        return request;
    }

    /**
     * The customer found.
     */
    public CustomerDetails getCustomer() {
        return customer;
    }

    /**
     * The customer found.
     */
    public void setCustomer(CustomerDetails customer) {
        this.customer = customer;
    }

    /**
     * The item requested for searching
     */
    public void setRequest(String request) {
        this.request = request;
    }
}
