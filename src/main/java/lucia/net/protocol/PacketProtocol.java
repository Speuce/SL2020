package main.java.lucia.net.protocol;

import main.java.lucia.client.content.menu.io.ItemGson;
import main.java.lucia.client.protocol.packet.in.customer.PacketInFoundCustomer;
import main.java.lucia.client.protocol.packet.in.employee.PacketInEmployeePermissionDenied;
import main.java.lucia.client.protocol.packet.in.employee.PacketInSetEmployee;
import main.java.lucia.client.protocol.packet.in.employee.PacketInSetEmployeeMap;
import main.java.lucia.client.protocol.packet.in.order.PacketInFoundOrderNumberOfDay;
import main.java.lucia.client.protocol.packet.in.order.PacketInSetOrder;
import main.java.lucia.client.protocol.packet.in.order.PacketInSetPreOrder;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import main.java.lucia.net.protocol.opcode.OpcodeConstants;

/**
 * Implemented protocol to deal with the new packet system
 * @author Matthew Kwiatkowski
 */
public class PacketProtocol extends Protocol{
    /**
     * Registers all Packets as necessary
     */
    @Override
    protected void registerAll() {
        register(OpcodeConstants.CUSTOMER_FOUND_OPCODE, PacketInFoundCustomer.class, GsonTypeFactory.BASIC_GSON);

        register(OpcodeConstants.EMPLOYEE_PERMISSION_DENIED, PacketInEmployeePermissionDenied.class, GsonTypeFactory.BASIC_GSON);
        register(OpcodeConstants.SET_EMPLOYEE_OPCODE, PacketInSetEmployee.class, GsonTypeFactory.BASIC_GSON);
        register(OpcodeConstants.GET_EMPLOYEE_MAP_OPCODE, PacketInSetEmployeeMap.class, GsonTypeFactory.BASIC_GSON);

        register(OpcodeConstants.FOUND_ORDERNUM_DAY_OPCODE, PacketInFoundOrderNumberOfDay.class, GsonTypeFactory.BASIC_GSON);
        register(OpcodeConstants.SET_ORDER_OPCODE, PacketInSetOrder.class, ItemGson.ITEM_GSON);
        register(OpcodeConstants.SET_PREORDER_OPCODE, PacketInSetPreOrder.class, ItemGson.ITEM_GSON);
    }
}
