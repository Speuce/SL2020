package main.java.lucia.client.manager;

import main.java.lucia.client.content.files.MLogger;
import main.java.lucia.client.protocol.packet.in.system.PacketInError;
import main.java.lucia.net.packet.event.PacketEventHandler;
import main.java.lucia.net.packet.event.PacketHandler;

/**
 * Handler for server-sent error messages
 * @author Matthew Kwiatkowski
 */
public class ErrorManager implements PacketHandler {

    @PacketEventHandler
    public void onErrorReceive(PacketInError e){
        MLogger.logError("Got an Error! /n" +
                e.getMessage());
    }
}
