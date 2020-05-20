package main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.lucia.Client;
import main.java.lucia.Zach.StreetNames;
import main.java.lucia.client.AsynchronousTaskService;
import main.java.lucia.client.content.customer.CustomerDetails;
import main.java.lucia.client.content.javascript.JavaScriptBridge;
import main.java.lucia.client.content.order.impl.Address;
import main.java.lucia.client.content.time.ClientTime;
import main.java.lucia.client.protocol.message.impl.customer.CreateCustomerMessage;
import main.java.lucia.fxml.FxmlConstants;
import main.java.lucia.fxml.controllers.ControllerMap;
import main.java.lucia.fxml.controllers.ControllerType;
import main.java.lucia.fxml.controllers.impl.Controller;
import main.java.lucia.fxml.controllers.impl.main.Utils.AutoCompleteComboBoxListener;
import main.java.lucia.fxml.controllers.impl.main.Utils.ParentController;
import main.java.lucia.fxml.controllers.impl.main.tabs.PendingOrdersPane;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDelivery;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;
import netscape.javascript.JSObject;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * The enter number pane which handles creation of new customers and other related operations
 *
 * @author Zachery Unrau
 * @author Brett Downey
 * @author Matthew Kwiatkowski
 */
public class EnterNumberPane implements Controller, ParentController<PickupDelivery> {


  @FXML
  private Pane enterNumberPane;

  @FXML
  private Pane extendedMapPane;

  @FXML
  private Pane confirmationPane;

  @FXML
  private Pane toggleMappane;

  @FXML
  private JFXButton toggleMapButton;

  @FXML
  private Pane mappane;

  @FXML
  private WebView webvis;

  @FXML
  private JFXTextField areaCodeField;

  @FXML
  private JFXTextField phoneField;

  @FXML
  private JFXRadioButton delivery;

  @FXML
  private JFXRadioButton pickup;

  @FXML
  private Pane loadPickup;

  @FXML
  private Label loadPickupNameView;

  @FXML
  private JFXButton loadCustomerPickup;

  @FXML
  private JFXTextField loadPickupName;

  @FXML
  private Pane createPickup;

  @FXML
  private Label createPickupNameView;

  @FXML
  private JFXButton createCustomerPickup;

  @FXML
  private JFXTextField createPickupName;

  @FXML
  private Pane createDelivery;

  @FXML
  private Label createDeliveryView;

  @FXML
  private JFXTextField createDeliveryAppt;

  @FXML
  private JFXButton createCustomerDel;

  @FXML
  private JFXTextField createDeliveryBuzz;

  @FXML
  private Label errorLabelCheckAddress;

  @FXML
  private JFXComboBox<String> createDeliveryStreet;

  @FXML
  private Pane loadDelivery;

  @FXML
  private Label loadDeliveryView;

  @FXML
  private JFXTextField loadDeliveryStreet;

  @FXML
  private JFXTextField loadDeliveryNum;

  @FXML
  private JFXTextField loadDeliveryAppt;

  @FXML
  private JFXTextField loadDeliveryBuzz;

  @FXML
  private JFXTextField createDeliveryStreetNum;

  @FXML
  private JFXButton loadCustomerDel;

  @FXML
  private Pane summaryOrder;

  @FXML
  private Label takeoutMethodLabel;

  @FXML
  private Label orderETA;

  @FXML
  private Label errorLabelSummary;

  @FXML
  private Pane orderRecieved;

  @FXML
  private Pane addressConfirmed;

  @FXML
  private Pane orderNotRecieved;

  @FXML
  private Pane addressNotConfirmed;

  @FXML
  private Label customerName;

  @FXML
  private Label customerNumber;

  @FXML
  private Label customerAddress;

  @FXML
  private Pane orderViewPane;

  @FXML
  private Pane paymentMethodPane;

  @FXML
  private JFXToggleButton staffShiftToggle;


  @FXML
  private JFXComboBox<String> paymentMethod;

  @FXML
  private Pane pastOrders;

  @FXML
  private Pane preorderPane;

  @FXML
  private JFXDatePicker calendarPreorder;

  @FXML
  private JFXComboBox<String> comboBoxTimeHour;

  @FXML
  private JFXComboBox<String> comboBoxTimeMin;

  @FXML
  private JFXComboBox<String> comboBoxTimeAMPM;

  @FXML
  private JFXComboBox<String> paymentMethodPreorder;

  @FXML
  private Label errorLabelPreorder;

  @FXML
  private Label errorLabelConfirmationAddress;

  @FXML
  private Label takeoutTime;

  @FXML
  private Pane discountPane;

  @FXML
  private Pane staffPane;

  @FXML
  private Pane caaPane;



  public String[] blackListedStringsPickup = {"", " ", "Walkin", "Walk in", "No Name", "Null"};
  public String[] blackListedStringsDelivery = {"", " ", "675 Harbison Avenue East"};

  private final String DEFAULT_ERROR_MESSAGE = "The entered address does not exist!";

  /**
   * The max length of a phone number that can be given
   */
  private final int MAX_PHONE_LENGTH = 7;

  /**
   * The max length of an area code that can be given
   */
  private final int MAX_AREA_CODE_LENGTH = 3;

  /**
   * The flag which indicates if the Google Maps {@link WebView} was loaded
   */
  private boolean loadedMap;

  /**
   * Indicates if the current {@link CustomerDetails} that was loaded is a delivery
   */
  private boolean isDelivery = false;

  /**
   * The loaded HTML file
   */
  private String HTML;

  /**
   * The loaded JavaScript file
   */
  private String JS;

  /**
   * The bridge from javascript to Java for our
   * geocoding asynchronous response
   */
  private JavaScriptBridge bridge;

  private boolean deliveryChecked = false;

  /**
   * The initialize call number,
   */
  private static int initializeCallNumb = 0;
  private boolean promptPreorder;
  private boolean confirm = true;
  private PickupDelivery parent;
  private TextInputControl foundStoreLabel;
  private boolean pizzaFirst = false;

  // ------------------- for some reason the initialize is called only once now ----------------------

  @FXML
  public void initialize() {
      ControllerMap.addController(ControllerType.ENTER_NUMBER_PANE_CONTROLLER, this);
      phoneField.setAlignment(Pos.CENTER_LEFT);
      Runnable phoneAreaChecks = phoneAreaChecks();
      phoneField.setTextFormatter(buildNumericFormatList(MAX_PHONE_LENGTH, phoneAreaChecks));
      areaCodeField.focusedProperty().addListener(new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
            Boolean newValue) {
          if(newValue){
            areaCodeField.setText("");
          }else{
            if(areaCodeField.getText().length() == 0){
              areaCodeField.setText("204");
            }
          }
        }
      });
      areaCodeField.setTextFormatter(buildNumericFormatList(MAX_AREA_CODE_LENGTH, phoneAreaChecks));
      AsynchronousTaskService.process(() -> Platform.runLater(this::enableMap));

      calendarPreorder.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
      calendarPreorder.focusedProperty().addListener((observable, oldValue, newValue) -> {
        if(newValue)
          calendarPreorder.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
      });
      for(int x = 3; x >= 0; x--)
        comboBoxTimeMin.getItems().add(String.valueOf(15 * x));
      for(int x = 1; x <= 12; x++)
        comboBoxTimeHour.getItems().add(String.valueOf(x));
    comboBoxTimeAMPM.getItems().addAll("AM", "PM");
    paymentMethodPreorder.getItems().addAll("Debit", "Cash", "Mastercard", "Visa", "Gift", "Cheque");
    paymentMethod.getItems().addAll("Debit", "Cash", "Mastercard", "Visa", "Gift", "Cheque");

    createDeliveryStreet.getEditor().textProperty().addListener((obs, oldText, newText) -> {
      clearErrorLabel();
      resetBackgrounds();
      updateAddress();
    });

    StreetNames streetNames = new StreetNames();
    createDeliveryStreet.getItems().addAll(streetNames.getNames());


    new AutoCompleteComboBoxListener<>(createDeliveryStreet);

    delivery.setTooltip(new Tooltip("test"));




    // orderView.setScaleX(0.75);
    // orderView.setScaleY(0.60);
    //  orderView.setLayoutX(-44);
    //  orderView.setLayoutY(-84);

  }
  public void setParent(PickupDeliveryPaneController p) {
    this.parent = p;

  }

  /**
   * Toggles the driverMap interface
   */
  public void toggleMap() {
    toggleMap(false);
  }

  /**
   * Toggles the driverMap depending on the given variable
   * {@code justOpen}.
   *
   * @param justOpen
   */
  private void toggleMap(boolean justOpen) {
    if (!justOpen && mappane.isVisible()) {
      mappane.setVisible(false);
      extendedMapPane.setVisible(false);
      enterNumberPane.setLayoutX(385);
    } else {
      mappane.setVisible(true);
      extendedMapPane.setVisible(true);
      enterNumberPane.setLayoutX(200);
    }
  }

  /**
   * Enables the driverMap, if the JavaScript and HTML files have not yet been loaded, then they will be.
   */
  private void enableMap() {
    if (loadedMap) {
      return;
    }

    if (JS == null || HTML == null) {
      HTML = load(FxmlConstants.HTML_MAPS_DIRECTORY);
      JS = load(FxmlConstants.JAVASCRIPT_MAPS_DIRECTORY);

      webvis.getEngine().setCreatePopupHandler(config -> {
        Stage stage = new Stage(StageStyle.UTILITY);
        WebView popup = new WebView();
        stage.setScene(new Scene(popup));
        stage.show();
        return popup.getEngine();
      });
    }

    webvis.getEngine().loadContent(HTML);
    webvis.getEngine().executeScript(JS);

    bridge = new JavaScriptBridge();
    webvis.getEngine().getLoadWorker().stateProperty().addListener((observableValue, oldState, newState) -> {
      if (newState == State.SUCCEEDED) {
        JSObject window = (JSObject) webvis.getEngine().executeScript("window");
        window.setMember("javaBridge", bridge);
        loadedMap = true;
      }
    });
  }

  /**
   * Loads the given directory into a single String
   *
   * @param directory The directory to load
   * @return The loaded String
   */
  private String load(String directory) {
    String loaded = null;
    try (Stream<String> reader = Files
            .lines(Paths.get(getClass().getResource(directory).toURI()))) {
      loaded = reader.collect(Collectors.joining());
    } catch (IOException | URISyntaxException e) {
      Client.logger.error("An error has occurred while loading the HTML maps information", e);
    }

    return loaded;
  }

  /**
   * Opens the Santa Lucia website with a JavaScript link
   */
  public void openWebsite() {
    webvis.getEngine().executeScript("openWebsite()");
  }

  /**
   * Builds our number format listener used different numeric only text fields
   *
   * @param maxLength The max length the field can hold
   * @param addition Any additional checks that need to be performed
   * @return The text formatter
   */
  public static TextFormatter<Object> buildNumericFormatList(int maxLength, Runnable addition) {
    DecimalFormat format = new DecimalFormat("#");
    return new TextFormatter<>(change -> {
      addition.run();
      return getChange(maxLength, format, change);
    });
  }

  public static TextFormatter.Change getChange(int maxLength, DecimalFormat format, TextFormatter.Change change) {
    if (change.getControlNewText().isEmpty()) {
      return change;
    }

    ParsePosition parsePosition = new ParsePosition(0);
    Object object = format.parse(change.getControlNewText(), parsePosition);
    if (object == null || parsePosition.getIndex() < change.getControlNewText().length()) {
      return null;
    } else {
      /* Verify it's not breaking our text format rules */
      if (change.getControlNewText().length() > maxLength || change.getControlText()
              .contains(".")) {
        change.setText(StringUtils.EMPTY);
      }
      return change;
    }
  }

  /**
   * The additional checks required for the phone area checks
   *
   * @return The checks
   */
  private Runnable phoneAreaChecks() {
    return () -> {
      boolean pickupOrDel = pickup.isSelected() || delivery.isSelected();
      boolean lengthVerify = areaCodeField.getLength() >= MAX_AREA_CODE_LENGTH - 1
          && phoneField.getLength() >= MAX_PHONE_LENGTH - 1;
      if (lengthVerify && pickupOrDel){
        //System.out.println("hey1");
        //CustomerFoundMessage.findCustomerByPhone(areaCodeField.getText() + phoneField.getText(), this::customerFound);
      }
      else if(pickupOrDel)
        disableAll();
      else disableAll();
    };
  }

  @FXML
  private void check() {
    if(!(phoneField.getLength() >= MAX_PHONE_LENGTH))
      disableAll();
  }

  @FXML
  private void checkArea() {
    if(!(areaCodeField.getLength() >= MAX_AREA_CODE_LENGTH))
      disableAll();
  }


  @FXML
  private void checkAddress() {
    deliveryChecked = true;
    AsynchronousTaskService.process(() -> Platform.runLater(() -> {
      String streetName = null;
      if (createDelivery.isVisible()) {
        streetName = createDeliveryStreetNum.getText() + " " + createDeliveryStreet.getValue();
      } else if (loadDelivery.isVisible()) {
        streetName = loadDeliveryStreet.getText();
      }

      if (streetName != null) {
        String combined = streetName + " Manitoba Canada";
        System.out.println("----------- : " + combined);
        String script = ("findLocation(" + "'" + combined + "'" + ")");
        webvis.getEngine().executeScript(script); // TODO Make work for more than just Winnipeg when we need to
      }

      toggleMap(true);
    }));
  }

  @FXML
  private void updateAddress() {
    String name = StringUtils.capitalize(createDeliveryStreet.getEditor().getText());
    String num = createDeliveryStreetNum.getText() + " ";
    String appt = createDeliveryAppt.getText();
    String buzz = createDeliveryBuzz.getText();
    if(buzz.equals("") && appt.equals(""))
      createDeliveryView.setText(num + name);
    else if(buzz.equals(""))
      createDeliveryView.setText(appt + " " + name);
    else createDeliveryView.setText(buzz + "-" + appt + " " + name);
  }

  @FXML
  private void updateName() {
    //createPickupNameView.setText(StringUtils.capitalize(createPickupName.getText()));
  }

  private void customerFound(CustomerDetails c){
    if(c == null){
      getCustomerPane("");
    }else{
      parent.getCurrentOrder().setCustomerDetails(c);
      getCustomerPane(c.getPhoneNumber());
    }
  }

  private void getCustomerPane(String knownNumber) {
    String phoneNumber = parent.getCurrentOrder().getCustomerDetails().getPhoneNumber();
    if (pickup.isSelected()) {
      if (phoneNumber != null && phoneNumber.equals(knownNumber)) {
        loadPickupPane();
      } else {
        loadCreatePickupPane();
      }
    } else {
      if (phoneNumber != null && phoneNumber.equals(knownNumber)) {
        loadDeliveryPane();
      } else {
        loadCreateDeliveryPane();
      }
    }
  }


  /**Prin
   * Loads the delivery pane and unloads all other panes
   */
  public void loadDeliveryPane() {
    PickupDeliveryPaneController controller = (PickupDeliveryPaneController) ControllerMap.getController(ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER);
    controller.setDelivery();
    loadCustomerFields();
    loadDelivery.setVisible(true);
    createDelivery.setVisible(false);
    loadPickup.setVisible(false);
    createPickup.setVisible(false);
    isDelivery = true;
  }

  private void loadCustomerFields() {
    if(parent.getCurrentOrder().getCustomerDetails() != null){
      loadPickupName.setText(parent.getCurrentOrder().getCustomerDetails().getName());
      if(parent.getCurrentOrder().getCustomerDetails().getAddress() != null){
        loadDeliveryAppt.setText(parent.getCurrentOrder().getCustomerDetails().getAddress().getApartmentNumber());
        loadDeliveryBuzz.setText(parent.getCurrentOrder().getCustomerDetails().getAddress().getBuzzCode());
        loadDeliveryNum.setText(parent.getCurrentOrder().getCustomerDetails().getAddress().getNumber());
        loadDeliveryStreet.setText(parent.getCurrentOrder().getCustomerDetails().getAddress().getStreet());
      }
    }
  }

  /**
   * Loads the create delivery pane and unloads all other panes
   */
  public void loadCreateDeliveryPane() {
    PickupDeliveryPaneController controller = (PickupDeliveryPaneController) ControllerMap.getController(ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER);
    controller.setDelivery();
    loadDelivery.setVisible(false);
    createDelivery.setVisible(true);
    loadPickup.setVisible(false);
    createPickup.setVisible(false);
    isDelivery = true;
  }

  /**
   * Loads the pickup pane and unloads all other panes
   */
  public void loadPickupPane() {
    PickupDeliveryPaneController controller = (PickupDeliveryPaneController) ControllerMap.getController(ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER);
    controller.setPickup();
    //updateName();
    loadCustomerFields();

    loadDelivery.setVisible(false);
    createDelivery.setVisible(false);
    loadPickup.setVisible(true);
    createPickup.setVisible(false);
    isDelivery = false;
  }

  /**
   * Loads the create pickup pane and unloads all other panes
   */
  public void loadCreatePickupPane() {
    PickupDeliveryPaneController controller = (PickupDeliveryPaneController) ControllerMap.getController(ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER);
    controller.setPickup();
    loadDelivery.setVisible(false);
    createDelivery.setVisible(false);
    loadPickup.setVisible(false);
    createPickup.setVisible(true);
  }

  /**
   * Sets the phone field and the area code field editable
   */
  @FXML
  public void setEditable() {
    phoneField.setEditable(true);
    areaCodeField.setEditable(true);
  }

  /**
   * The on click function which is used for the {code delivery} and {@code pickup} {@link
   * JFXRadioButton}s
   *
   * @param event The click event
   */
  @FXML
  public void onClick(ActionEvent event) {
    disableAll();
    Object source = event.getSource();
    if (source.equals(delivery) || source.equals(pickup)) {
      if (phoneField.getLength() >= MAX_PHONE_LENGTH) {
        //System.out.println("hey2");
        //CustomerFoundMessage.findCustomerByPhone(areaCodeField.getText() + phoneField.getText(), this::customerFound);
      }
    }
    if(source.equals(delivery))
      isDelivery = true;
    if(source.equals(pickup))
      isDelivery = false;
  }

  @FXML
  public void resetBackgrounds() {
    createPickupName.setStyle("-fx-background-color: white");
    createDeliveryStreet.setStyle("-fx-background-color: white");
  }

  /**
   * Adds the given data to the customer interface. This confirms beforehand which data we need to
   * load, as there are two different areas where customer data is entered.
   */
  @FXML
  public void addToCustomer() {
    checkAddress();
    if(confirm) {
      if (loadPickup.isVisible()) {
        parent.getCurrentOrder().getCustomerDetails().setName(loadPickupName.getText());
        PickupDeliveryPaneController cont = (PickupDeliveryPaneController) ControllerMap.getController(ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER);
        //cont.getCompletedOrder().
        parent.getCurrentOrder().getCustomerDetails().setPhoneNumber(areaCodeField.getText() + phoneField.getText());
      } else if (createPickup.isVisible()) {
        if (checkValidStringPickup(createPickupName.getText())) {
          parent.getCurrentOrder().getCustomerDetails().setName(createPickupName.getText());
          parent.getCurrentOrder().getCustomerDetails().setPhoneNumber(areaCodeField.getText() + phoneField.getText());
        } else {
          createPickupName.setStyle("-fx-background-color: #717516");
          return;
        }
      } else if (createDelivery.isVisible() || loadDelivery.isVisible()) {

        String address = "null";
        String streetNumber = "null";
        try {
          address = String.valueOf(createDeliveryStreet.getValue());
          streetNumber = createDeliveryStreetNum.getText() + " ";
        } catch (Exception e) {
          errorLabelCheckAddress.setText("Invalid Address!");
        }
        if (checkValidStringDelivery(streetNumber + address)) {
          parent.getCurrentOrder().getCustomerDetails().setPhoneNumber(areaCodeField.getText() + phoneField.getText());
          parent.getCurrentOrder().getCustomerDetails().setAddress(
                  new Address(address, streetNumber, createDeliveryAppt.getText(), createDeliveryBuzz.getText()));
          PickupDeliveryPaneController cont = (PickupDeliveryPaneController) ControllerMap.getController(ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER);
          cont.getCurrentOrder().setDelivery(true);
        } else {
          createDeliveryStreet.setStyle("-fx-background-color: #717516");
          return;
        }
      }
      //call create customer
      CreateCustomerMessage.saveCustomer(parent.getCurrentOrder().getCustomerDetails());
      if (promptPreorder)
        preorder();
      else if(pizzaFirst)
        loadSummaryPane();
      else
        addToCustomerPickupDel();
    }
  }

  /**
   * Called when the createcustomermessage returns the customer
   * @param i the {@link CustomerDetails} returned
   */
  public void gotCustomer(CustomerDetails i) {
    if (parent.getCurrentOrder().getCustomerDetails().getPhoneNumber().equals(i.getPhoneNumber())) {
      parent.getCurrentOrder().setCustomerDetails(i);
    }
  }

  @FXML
  public void onExit() {
    pizzaFirst = true;
    addToCustomerPickupDel();
  }

  private boolean checkValidStringPickup(String string) {
    for(int x = 0; x < blackListedStringsPickup.length; x++)
      if(string.equalsIgnoreCase(blackListedStringsPickup[x]))
        return false;
    return true;
  }

  private boolean checkValidStringDelivery(String string) {
    if(!string.contains(" "))
      return false;
    else if(!checkNum(string)) {
      return false;
    }
    for(int x = 0; x < blackListedStringsDelivery.length; x++)
      if(string.equalsIgnoreCase(blackListedStringsDelivery[x]))
        return false;
    return true;
  }

  private boolean checkNum(String string) {
    try {
      Integer.parseInt(string.substring(0, string.indexOf(" ")));
    }
    catch (Exception e) {
      System.out.println("FALSE!");
      return false;
    }
    return true;
  }

  @FXML
  public void addToCustomerPickupDel() {
    disableAll();
    //parent.setChild();
    PickupDeliveryPaneController controller = (PickupDeliveryPaneController) ControllerMap
            .getController(ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER);
    controller.addToCustomer();
  }

  public void disableAll() {
    createPickup.setVisible(false);
    createDelivery.setVisible(false);
    loadPickup.setVisible(false);
    loadDelivery.setVisible(false);
    summaryOrder.setVisible(false);
  }

  public void reset() {
    disableAll();
    Pane[] panes = {createDelivery, createPickup, loadDelivery, createDelivery};
    for(int x = 0; x < panes.length; x++) {
      panes[x].getChildren().forEach(theButtons -> {
        if (theButtons instanceof JFXTextField) {
          JFXTextField text = (JFXTextField) theButtons;
          text.clear();
        }
      });
    }
    resetPreorder();
    areaCodeField.setText("204");
    paymentMethodPane.setVisible(false);
    confirmationPane.setVisible(false);
    clearErrorLabel();
    updateAddress();
    updateName();
    phoneField.clear();
    pickup.setSelected(false);
    delivery.setSelected(false);
    takeoutTime.setText("NOW");
    pizzaFirst = false;
    PendingOrdersPane controller = (PendingOrdersPane) ControllerMap.getController(ControllerType.PENDING_ORDERS_PANE_CONTROLLER);
    controller.refresh();
    //runner()
  }

  @FXML
  public void cancelOrder() {
    PickupDeliveryPaneController controller = (PickupDeliveryPaneController) ControllerMap
            .getController(ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER);
    controller.cancelOrder();
    //parent.setChild();
    clearSummary();
    reset();
  }

  public void enableGeocodeError() {
    Platform.runLater(() -> {
      errorLabelCheckAddress.setVisible(true);
      errorLabelCheckAddress.setText(DEFAULT_ERROR_MESSAGE);
    });
  }

  public void enableGeocodeError(String errorMessage) {
    Platform.runLater(() -> {
      confirmationPane.setVisible(true);
      errorLabelConfirmationAddress.setVisible(true);
      errorLabelConfirmationAddress.setText(errorMessage);
    });
  }

  public void disableGeocodeError() {
    Platform.runLater(() -> errorLabelCheckAddress.setVisible(false));
  }

  public void setStoreName(String name) {
    System.out.println("WORK");
    Platform.runLater(() -> {
      foundStoreLabel.setVisible(true);
      // TODO Clean loose strings up
      if(name.equals("undefined")) {
        if(confirm) {
          confirmationPane.setVisible(true);
          confirmationPane.setVisible(true);
          errorLabelConfirmationAddress.setText("Address is not in the Delivery Area");
        }
        else {
          confirmationPane.setVisible(true);
          errorLabelConfirmationAddress.setText("The given location is not within any store location's delivery area");
          errorLabelConfirmationAddress.setStyle("-fx-text-fill: #ff2f2f");
        }
      }
      else {
        if(confirm) {
          System.out.println("HERE");
          confirmationPane.setVisible(true);
          errorLabelConfirmationAddress.setText("Address is within within " + name + "'s delivery area");
        }
        else {
          System.out.println("HERE");
          confirmationPane.setVisible(true);
          errorLabelConfirmationAddress.setText("The given location is within " + name + "'s delivery area");
          errorLabelConfirmationAddress.setStyle("-fx-text-fill: black");
        }
      }
    });
  }

  @FXML
  public void confirmInvalidAddress() {
    confirm = true;
  }

  @FXML
  public void resetPreorder() {
    calendarPreorder.setValue(ClientTime.getWinnipegLocalDate());
    paymentMethodPreorder.setValue(null);
    comboBoxTimeAMPM.setValue(null);
    comboBoxTimeHour.setValue(null);
    comboBoxTimeMin.setValue(null);
  }

  @FXML
  public void loadSummaryPane() {
    clearSummary();
    this.orderViewPane.getChildren().clear();
    this.orderViewPane.getChildren().add(((PickupDeliveryPaneController)parent).getOrderView());
    this.orderViewPane.setScaleX(0.75);
    this.orderViewPane.setScaleY(0.60);
    this.orderViewPane.setLayoutX(-20);
    this.orderViewPane.setLayoutY(0);
    summaryOrder.setVisible(true);
    if(pickup.isSelected()) {
      takeoutMethodLabel.setText("PICKUP");
      orderETA.setText("15 - 20 MIN");
    }
    else if(delivery.isSelected()) {
      takeoutMethodLabel.setText("DELIVERY");
      orderETA.setText("45 MIN - 1 HR");
    }
    if(parent.getCurrentOrder().isPreOrder()) {
      if(!parent.getCurrentOrder().isFuturePreorder()) {
        takeoutTime.setText(parent.getCurrentOrder().getOrderTime().toLocalDate().getHour() + ":"
                + parent.getCurrentOrder().getOrderTime().toLocalDate().getMinute());
        takeoutTime.setStyle("-fx-font-size: 15");
        orderETA.setText(parent.getCurrentOrder().getOrderTime().toLocalDate().getHour() + ":"
                + parent.getCurrentOrder().getOrderTime().toLocalDate().getMinute());
      }else{
        //TODO its an order for a future day.
      }
    }
    confirmGeneralInfo();
    loadCustomerInfo();
  }

  public void loadCustomerInfo() {
    if(pickup.isSelected())
      customerName.setText(StringUtils.capitalize(parent.getCurrentOrder().getCustomerDetails().getName()));
    customerNumber.setText(parent.getCurrentOrder().getCustomerDetails().getPhoneNumberFormatted());
    if(delivery.isSelected())
      customerAddress.setText(parent.getCurrentOrder().getCustomerDetails().getAddress().toString());
  }

  public void clearGeneralInfoImages() {
    orderRecieved.setVisible(false);
    orderNotRecieved.setVisible(false);
    addressConfirmed.setVisible(false);
    addressNotConfirmed.setVisible(false);
  }

  public void clearSummary() {
    customerAddress.setText("");
    customerNumber.setText("");
    customerName.setText("");
  }

  public void confirmGeneralInfo() {
    PickupDeliveryPaneController controller = (PickupDeliveryPaneController) ControllerMap.getController(ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER);
    clearGeneralInfoImages();
    if(controller.getCurrentOrder().isEmpty())
      orderNotRecieved.setVisible(true);
    else orderRecieved.setVisible(true);
    if(delivery.isSelected()) {
      if (deliveryChecked)
        addressConfirmed.setVisible(true);
      else addressNotConfirmed.setVisible(true);
    }
  }

  @FXML
  public void confirmOrder() {
    PickupDeliveryPaneController controller = (PickupDeliveryPaneController) ControllerMap.getController(ControllerType.PICKUP_DELIVERY_PANE_CONTROLLER);

    if(!orderRecieved.isVisible())
      errorLabelSummary.setText("Place the Order");
    if(delivery.isSelected()) {
      controller.setDelivery();
      paymentMethodPane.setVisible(true);
    }
    else controller.setPickup();
    if(delivery.isSelected()) {
      if(paymentMethod.getValue() == null)
        errorLabelSummary.setText("Payment Method Needed!");
      else {
        System.out.println(controller.getCurrentOrder().isDelivery());
        System.out.println(controller.getCurrentOrder().getGrandTotalTax().toString());
        //controller.getCurrentOrder().setPaymentType(PaymentType.valueOf(paymentMethod.getValue().toUpperCase()));
        controller.finalizeOrder();
      }
    }
    else controller.finalizeOrder();
  }

  @FXML
  public void loadOrder() {
    reset();
    addToCustomerPickupDel();
  }

  @FXML
  public void clearErrorLabel() {
    errorLabelSummary.setText(" ");
    errorLabelCheckAddress.setText(" ");
    errorLabelConfirmationAddress.setText(" ");
  }

  @FXML
  public void changeInfo() {
    summaryOrder.setVisible(false);
  }

  /**
   * Determines if the current order being placed is for a delivery
   *
   * @return the delivery flag
   */
  public boolean isDelivery() {
    return isDelivery;
  }

  /**
   * Gets the {@link ControllerType}
   *
   * @return The controller type
   */
  @Override
  public ControllerType getType() {
    return ControllerType.ENTER_NUMBER_PANE_CONTROLLER;
  }

  @FXML
  public void openPastOrders() {
    pastOrders.setVisible(true);
  }

  @FXML
  public void backPreorder() {
    preorderPane.setVisible(false);
    resetPreorder();
  }

  @FXML
  public void preorder() {
    preorderPane.setVisible(true);
  }
  @FXML
  public void promptPreorder() {
    promptPreorder = true;
  }
  @FXML
  public void confirmPreorder() {
    if(paymentMethodPreorder.getValue() == null)
      errorLabelPreorder.setText("Payment Method Needed!");
    else if(comboBoxTimeHour.getValue() == null)
      errorLabelPreorder.setText("Hour Needed!");
    else if(comboBoxTimeMin.getValue() == null)
      errorLabelPreorder.setText("Minutes Needed!");
    else if(comboBoxTimeAMPM.getValue() == null)
      errorLabelPreorder.setText("AM/PM Needed!");
    else if(calendarPreorder.getValue() == null)
      errorLabelPreorder.setText("Date Needed!");
    else {
      //parent.getCurrentOrder().setPaymentType(PaymentType.valueOf(paymentMethodPreorder.getValue().toUpperCase()));
      int hour;
      if(Integer.valueOf(comboBoxTimeHour.getValue()) == 12)
        if(comboBoxTimeAMPM.equals("AM"))
          hour = 0;
        else
          hour = 12;
      else if(comboBoxTimeAMPM.getValue().equals("PM"))
        hour = Integer.valueOf(comboBoxTimeHour.getValue()) + 12;
      else hour = Integer.valueOf(comboBoxTimeHour.getValue());
      parent.getCurrentOrder().setPreorder(true);
      parent.getCurrentOrder().setPreorderTime(calendarPreorder.getValue().getYear(), calendarPreorder.getValue().getMonth(),
              calendarPreorder.getValue().getDayOfMonth(), hour, Integer.valueOf(comboBoxTimeMin.getValue()));
      preorderPane.setVisible(false);
      if(promptPreorder)
        addToCustomerPickupDel();
      else loadSummaryPane();
    }
  }
  @FXML
  public void discounts() {
    discountPane.toFront();
    discountPane.setVisible(true);
  }

  @FXML
  void acceptCAA(ActionEvent event) {
    discountPane.setVisible(false);
  }

  @FXML
  void acceptStaff(ActionEvent event) {
//    if(staffShiftToggle.isSelected())
//      parent.getCurrentOrder().addStaffDiscount(true);
//    else parent.getCurrentOrder().addStaffDiscount(false);
    staffPane.setVisible(false);
    discountPane.setVisible(false);
    staffShiftToggle.setText("Off Shift");
    staffShiftToggle.setSelected(false);
  }

  @FXML
  void staffOrder(ActionEvent event) {
    staffPane.setVisible(true);
  }

  @Override
  public void close() {

  }

  @Override
  public Object getCurrentItem() {
    return null;
  }

  @Override
  public void open() {
    areaCodeField.setText("204");
    phoneField.setText("");
  }

  @Override
  public void setParent(PickupDelivery o) {
    this.parent = o;
  }

  @FXML
  public void shiftToggle() {
    if(staffShiftToggle.isSelected())
      staffShiftToggle.setText("On Shift");
    else staffShiftToggle.setText("Off Shift");
  }
}