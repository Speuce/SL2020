package main.java.lucia.fxml.controllers.impl.main.tabs.order.enternumber;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import main.java.lucia.client.content.customer.CustomerDetails;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.protocol.packet.in.customer.PacketInFoundPastCustomerOrder;
import main.java.lucia.client.protocol.packet.outgoing.customer.PacketOutFindPastCustomerOrders;
import main.java.lucia.net.packet.event.PacketEventHandler;
import main.java.lucia.net.packet.event.PacketHandler;
import main.java.lucia.net.packet.event.PacketListenerManager;
import main.java.lucia.net.packet.impl.outgoing.PacketSender;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * The Controller for the past orders view Pane
 * @author Matthew Kwiatkowski
 */
public class PastOrdersPaneController implements PacketHandler {

    @FXML
    private Pane pastOrderView1;

    @FXML
    private Pane pastOrderView2;

    @FXML
    private Pane pastOrderView3;

    @FXML
    private PastOrderViewPaneController pastOrderView1Controller;

    @FXML
    private PastOrderViewPaneController pastOrderView2Controller;

    @FXML
    private PastOrderViewPaneController pastOrderView3Controller;

    /**
     * The current customer whose orders are displayed
     */
    @Nullable
    private CustomerDetails customer = null;

    /**
     * Array of past order panes
     */
    private Pane[] pastOrderPanes;

    /**
     * Array of past order pane controllers
     */
    private PastOrderViewPaneController[] pastOrderPaneControllers;

    public PastOrdersPaneController() {
        //register listener
        PacketListenerManager.get.registerListener(this);
    }

    @FXML
    public void initialize(){
        pastOrderPanes = new Pane[]{pastOrderView1, pastOrderView2, pastOrderView3};
        pastOrderPaneControllers = new PastOrderViewPaneController[]{pastOrderView1Controller, pastOrderView2Controller, pastOrderView3Controller};
    }

    /**
     * Load past orders for the given customer
     * @param c the customer to load for.
     * @param onSelected the consumer called when a past order is chosen to be loaded.
     */
    public void loadForCustomer(CustomerDetails c, Consumer<Order> onSelected){
        this.customer = c;
        PacketSender.sendPacket(new PacketOutFindPastCustomerOrders(3, this.customer.getRowNum()));
        for (Pane pastOrderPane : pastOrderPanes) {
            pastOrderPane.setVisible(false);
        }
        for (PastOrderViewPaneController pastOrderPaneController : pastOrderPaneControllers) {
            pastOrderPaneController.setOrderLoaded(onSelected);
        }
    }

    @PacketEventHandler
    public void onPastOrderFind(PacketInFoundPastCustomerOrder past){
        //TODO may need to run in javafx thread
        if(customer != null){
            if(past.getCustomerId() == customer.getRowNum()){
                int index = past.getNum()-1;
                if(index < pastOrderPanes.length){
                    pastOrderPaneControllers[index].displayOrder(past.getOrder());
                    pastOrderPanes[index].setVisible(true);
                }
            }
        }
    }

    /**
     * Closes the past orders pane.
     */
    public void close(){

    }

}
