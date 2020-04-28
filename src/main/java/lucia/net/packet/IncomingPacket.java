package main.java.lucia.net.packet;

public abstract class IncomingPacket {

    private final int echoCode;

    public IncomingPacket(final int echoCode) {
        this.echoCode = echoCode;
    }

    public int getEchoCode() {
        return echoCode;
    }

    public abstract OutgoingPacket createOutgoingPacket();
}