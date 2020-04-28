package main.java.lucia.client.protocol.message.impl.customer;

import com.google.gson.Gson;
import main.java.lucia.client.content.customer.CustomerDetails;
import main.java.lucia.client.protocol.message.Message;
import main.java.lucia.client.protocol.message.impl.MattMessage;
import main.java.lucia.fxml.controllers.ControllerMap;
import main.java.lucia.fxml.controllers.ControllerType;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.EnterNumberPane;
import main.java.lucia.net.packet.PacketProcessor;
import main.java.lucia.net.packet.impl.GsonTypeFactory;
import main.java.lucia.net.packet.impl.outgoing.PacketSender;
import main.java.lucia.net.packet.impl.outgoing.codec.OutgoingAuthenticatedPacket;
import main.java.lucia.net.protocol.opcode.OpcodeConstants;

public class CreateCustomerMessage extends MattMessage {


  /**
   * The constructor for a {@link Message} which provides the needed {@link PacketProcessor}.
   */
  public CreateCustomerMessage(PacketProcessor packetProcessor) {
    super(packetProcessor);
  }

  /**
   * Sends the save customer request to the server
   * @param details the {@link CustomerDetails}  to save
   */
  public static void saveCustomer(CustomerDetails details){
    OutgoingAuthenticatedPacket out = new OutgoingAuthenticatedPacket(OpcodeConstants.SET_CUSTOMER_OPCODE);
    out.setJsonRequest(GsonTypeFactory.BASIC_GSON.toJson(details));
    PacketSender.getCurrentPacketSender().sendMessage(out);
  }

  @Override
  public int getReplyOpcode() {
    return -1;
  }

  @Override
  public OutgoingAuthenticatedPacket getReplyPacket() {
    return null;
  }

  @Override
  public void process() {
    getPacketProcessor().setOutgoingPacket(null);
    if(getPacketProcessor().getJsonRequest().length() >= 15){
      CustomerDetails ret = GsonTypeFactory.BASIC_GSON.fromJson(getPacketProcessor().getJsonRequest(), CustomerDetails.class);
      ((EnterNumberPane)ControllerMap.getController(ControllerType.ENTER_NUMBER_PANE_CONTROLLER)).gotCustomer(ret);
    }
  }
}
