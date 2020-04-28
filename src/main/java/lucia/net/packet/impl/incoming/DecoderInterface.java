package main.java.lucia.net.packet.impl.incoming;

import main.java.lucia.net.packet.IncomingPacket;

public interface DecoderInterface {

    IncomingPacket getPacket(String message);

    IncomingPacket process(String message) throws Exception;

    DecoderInterface next();

    default IncomingPacket attemptToGetPacket(String message) {
        try {
            return getPacket(message);
        } catch (Exception e) {
            return null;
        }
    }
}