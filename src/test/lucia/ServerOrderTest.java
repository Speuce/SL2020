package test.lucia;

import main.java.lucia.Client;
import main.java.lucia.client.ClientBuilder;
import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.test.ItemTester;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.content.order.OrderType;
import main.java.lucia.client.content.order.test.ItemListPrinter;
import main.java.lucia.client.manager.impl.OrderManager;
import main.java.lucia.client.protocol.packet.in.order.PacketInSetOrder;
import main.java.lucia.client.protocol.packet.outgoing.order.PacketOutSubmitOrder;
import main.java.lucia.net.NetworkBuilder;
import main.java.lucia.net.packet.event.ListenerPriority;
import main.java.lucia.net.packet.event.PacketEventHandler;
import main.java.lucia.net.packet.event.PacketHandler;
import main.java.lucia.net.packet.event.PacketListenerManager;
import main.java.lucia.net.packet.impl.incoming.codec.IncomingHandshakePacket;
import main.java.lucia.net.packet.impl.incoming.codec.login.IncomingLoginAttemptPacket;
import main.java.lucia.net.packet.impl.outgoing.PacketSender;
import main.java.lucia.net.packet.impl.outgoing.codec.login.OutgoingLoginAttemptPacket;
import main.java.lucia.net.security.passwords.CryptographicHash;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.io.IoBuilder;

import java.io.PrintStream;

/**
 * Tests sending an order to the server, and awaiting a response.
 * @author Matthew Kwiatkowski
 */
public class ServerOrderTest implements PacketHandler {

    public static void main(String[] args){
        new ServerOrderTest();
    }

    private ClientBuilder cl;

    public ServerOrderTest() {
        Client.logger.info("Starting Server Connection Test...");
        Menu.loadFromTestMenu();
        PacketListenerManager.get.registerListener(this);
        //System.setProperty("io.netty.tryReflectionSetAccessible", "true");
        cl = new ClientBuilder(new NetworkBuilder(), null).setRunGUI(false).initialize();
    }

    private void sendTestOrder(){
        Order o = new Order(OrderType.PICKUP);
        o.setCustomerDetails(null);
        o.addItem(ItemTester.getSomeSimpleItem());
        o.addItem(ItemTester.build10Chz());
        OrderManager.submitOrder(o);
    }

    @PacketEventHandler
    public void onHandShakeComplete(IncomingHandshakePacket hand){
        Client.logger.info("Completed Handshake! Sending login");
        PacketSender.getCurrentPacketSender().sendMessage(new OutgoingLoginAttemptPacket("123", CryptographicHash.hashPassword("123")));
    }

    @PacketEventHandler
    public void onLoginReceive(IncomingLoginAttemptPacket e){
        Client.logger.info("Got incoming login packet as response with code: " + e.getLoginResponseOpcode());
        if(e.getLoginResponseOpcode() == 2){
            Client.logger.info("Successfully logged in!");
            sendTestOrder();
        }
    }

    @PacketEventHandler
    public void onOrderSent(PacketOutSubmitOrder o){
        Client.logger.info("Sent out packet: \n" + o.serialize());
    }

    @PacketEventHandler(priority = ListenerPriority.LOWEST)
    public void onOrderReceive(PacketInSetOrder o){
        Client.logger.info("Got order packet!");
        ItemListPrinter pr = new ItemListPrinter();
        PrintStream r = IoBuilder.forLogger(Client.logger).setLevel(Level.INFO).buildPrintStream();
        pr.printList(o.getOrder(), r);
        o.setCancelled(true);
        Client.logger.info("Test Completed!");
        cl.shutdown(0);
    }
}
