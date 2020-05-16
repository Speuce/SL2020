package main.java.lucia.client.protocol.packet.in.employee;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import main.java.lucia.client.protocol.packet.IncomingAuthPacket;
import main.java.lucia.net.protocol.opcode.OpcodeConstants;

import java.util.Map;

/**
 * Packet sent from server as a response to {@link main.java.lucia.client.protocol.packet.outgoing.employee.PacketOutGetEmployeeMap}
 * @author Matthew Kwiatkowski
 */
public class PacketInSetEmployeeMap extends IncomingAuthPacket {

    /**
     * The map, got from the server of
     * employee ids:name
     */
    Map<Integer, String> map;

    public PacketInSetEmployeeMap(int echoCode, Map<Integer, String> map) {
        super(echoCode, OpcodeConstants.GET_EMPLOYEE_MAP_OPCODE);
        this.map = map;
    }

    /**
     * The map, got from the server of
     * employee ids:name
     */
    public Map<Integer, String> getMap() {
        return map;
    }

    /**
     * @return The map, got from the server of
     * employee ids:name
     */
    public void setMap(Map<Integer, String> map) {
        this.map = map;
    }

    /**
     * @return The map, got from the server of
     * employee ids:name
     * As a {@link BiMap}
     */
    public BiMap<Integer, String> getMapAsBiMap(){
        return HashBiMap.create(getMap());
    }
}
