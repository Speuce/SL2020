package main.java.lucia.client.protocol.packet.outgoing.order;

import main.java.lucia.client.protocol.packet.OutgoingAuthPacket;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import main.java.lucia.net.protocol.opcode.OpcodeConstants;

import java.time.LocalDate;

/**
 * Outgoing packet requesting the # of orders on a given day
 * (for reports, etc)
 * @author Matthew Kwiatkowski
 */
public class PacketOutGetOrderNumberOfDay extends OutgoingAuthPacket {

    /**
     * The day requesting ordernum for.
     */
    private LocalDate date;

    public PacketOutGetOrderNumberOfDay(LocalDate date) {
        super(OpcodeConstants.GET_ORDERNUM_DAY_OPCODE);
        this.date = date;
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
     * The day requesting ordernum for.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * The day requesting ordernum for.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }
}
