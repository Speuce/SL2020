package main.java.lucia.client.content.javascript;

import main.java.lucia.Zach.DeliveryAreaConvert;
import main.java.lucia.client.content.javascript.google.GoogleGeocodeResponse;
import main.java.lucia.fxml.controllers.ControllerMap;
import main.java.lucia.fxml.controllers.ControllerType;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.EnterNumberPane;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;
import netscape.javascript.JSObject;

/**
 * The bridge given to JavaScript to send
 * callbacks back to Java
 *
 * @author Brett Downey
 */
public class JavaScriptBridge {

  private GoogleGeocodeResponse response;
  DeliveryAreaConvert deliveryAreaConvert = new DeliveryAreaConvert();

  public void testInfo(String name) {
    System.out.println("DEL AREA:");
    System.out.println(name);
    ((PickupDeliveryPaneController)ControllerMap.getController(ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER)).
            getCurrentOrder().getCustomerDetails().setDeliveryArea(deliveryAreaConvert.getAreaHenderson(name.substring(0, name.indexOf(" |"))));
  }

  public void geocodeCallBack(Object callback) {
    JSObject received = (JSObject) callback;
    String storeName = (String) received.getMember("name");
    String json = (String) received.getMember("json");
    String warning = (String) received.getMember("warningMessage");

    if(json.equals("undefined") && !warning.equals("undefined")) {
     // System.out.println(json);
      System.out.println(warning);
      enableGeocodeError(warning);
    } else {
      setLocation(storeName);

      if (!warning.equals("undefined")) {
        enableGeocodeError(warning);
        return;
      } else {
        disableGeocodeError();
      }

      response = new GoogleGeocodeResponse();
      response.setAllResults(GoogleGeocodeResponse.resultsFromJson(json));
      response.setLocationFormatted(storeName);
      response.setLocation((String) received.getMember("type"));
    }
  }

  private void disableGeocodeError() {
    EnterNumberPane numberPane = (EnterNumberPane) ControllerMap
        .getController(ControllerType.ENTER_NUMBER_PANE_CONTROLLER);
    numberPane.disableGeocodeError();
  }

  private void enableGeocodeError(String message) {
    EnterNumberPane numberPane = (EnterNumberPane) ControllerMap
        .getController(ControllerType.ENTER_NUMBER_PANE_CONTROLLER);
    if(message == null) {
      numberPane.enableGeocodeError();
    } else {
      numberPane.enableGeocodeError(message);
    }
  }

  private void setLocation(String storeName) {
    System.out.println("HERE~!!!!!!!!!");
    EnterNumberPane numberPane = (EnterNumberPane) ControllerMap
        .getController(ControllerType.ENTER_NUMBER_PANE_CONTROLLER);
    numberPane.setStoreName(storeName);
  }
}