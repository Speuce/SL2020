package main.java.lucia.client.protocol.packet.in.order;

import main.java.lucia.client.protocol.packet.OutgoingAuthPacket;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import main.java.lucia.net.protocol.opcode.OpcodeConstants;

import java.time.LocalDate;

/**
 * incoming packet saying the # of orders on a given day
 * (for reports, etc)
 * @author Matthew Kwiatkowski
 */
public class PacketInFoundOrderNumberOfDay extends OutgoingAuthPacket {

    /**
     * The number of orders on the given day
     */
    private int orders;

    /**
     * The day requested
     */
    private LocalDate day;

    public PacketInFoundOrderNumberOfDay() {
        super(OpcodeConstants.FOUND_ORDERNUM_DAY_OPCODE);
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
     * The number of orders on the given day
     */
    public int getOrders() {
        return orders;
    }

    /**
     * The number of orders on the given day
     */
    public void setOrders(int orders) {
        this.orders = orders;
    }

    /**
     * The day requested
     */
    public LocalDate getDay() {
        return day;
    }

    /**
     * The day requested
     */
    public void setDay(LocalDate day) {
        this.day = day;
    }
}
