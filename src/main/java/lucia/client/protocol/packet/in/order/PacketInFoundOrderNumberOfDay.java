package main.java.lucia.client.protocol.packet.in.order;

import main.java.lucia.client.protocol.packet.IncomingAuthPacket;

import java.time.LocalDate;

/**
 * incoming packet saying the # of orders on a given day
 * (for reports, etc)
 * @author Matthew Kwiatkowski
 */
public class PacketInFoundOrderNumberOfDay extends IncomingAuthPacket {

    /**
     * The number of orders on the given day
     */
    private int orders;

    /**
     * The day requested
     */
    private LocalDate day;

    public PacketInFoundOrderNumberOfDay(int echoCode, int opcode, int orders, LocalDate day) {
        super(echoCode, opcode);
        this.orders = orders;
        this.day = day;
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
