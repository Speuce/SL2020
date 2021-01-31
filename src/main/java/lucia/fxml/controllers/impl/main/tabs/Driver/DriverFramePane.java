package main.java.lucia.fxml.controllers.impl.main.tabs.Driver;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.lucia.Client;
import main.java.lucia.client.content.employee.type.Driver;
import main.java.lucia.client.content.javascript.JavaScriptBridge;
import main.java.lucia.client.content.order.Order;
import main.java.lucia.client.manager.impl.OrderManager;
import main.java.lucia.fxml.FxmlConstants;
import main.java.lucia.fxml.controllers.ControllerMap;
import main.java.lucia.fxml.controllers.ControllerType;
import main.java.lucia.fxml.controllers.impl.Controller;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * The controller which controls the driver frame
 *
 * @author Brett Downey
 */
public class DriverFramePane implements Controller {
    private String HTML;
    private String JS;
    private JavaScriptBridge bridge;
    private boolean loadedMap;

    @FXML
    private Pane driverHomePage;

    @FXML
    private Pane driverLoggedIn;

    @FXML
    private Pane transferDelivery;

    @FXML
    private Pane settings;

    @FXML
    private Pane driverReport;

    @FXML
    private Pane driverCredsFrame;

    @FXML
    private Pane driverCashout;

    @FXML
    private Pane changeAddress;

    @FXML
    private Pane employeeHomePage;

    @FXML
    private JFXButton cashoutButton;

    @FXML
    private JFXButton driverReportButton;

    @FXML
    private JFXButton transferDeliveryButton;

    @FXML
    private JFXButton changeAddressButton;

    @FXML
    private JFXButton settingsButton;

    @FXML
    private JFXButton startShiftButton;

    @FXML
    private WebView webvis;

    @FXML
    private Pane claimDeliveryPane;

    @FXML
    private Pane loginPane;

    @FXML
    private Pane checkInPane;

    @FXML
    private GridPane toClaimDeliveryGrid;

    @FXML
    private GridPane loginLineupDrivers;

    @FXML
    private GridPane checkedInDrivers;

    @FXML
    private GridPane driverLineupGrid;

    @FXML
    private GridPane viewOtherDriversGrid;

    @FXML
    private JFXComboBox<?> viewOtherDriversList;

    @FXML
    private Pane viewDriverPane;

    private static Driver testEmployee;

    @FXML
    public void initialize() {
        ControllerMap.addController(ControllerType.DRIVER_FRAME_PANE_CONTROLLER, this);

        enableMap();

        buildDrivers();

        loadDeliveries();
    }

    @Override
    public ControllerType getType() {
        return ControllerType.DRIVER_FRAME_PANE_CONTROLLER;
    }


    private void enableMap() {
        if (loadedMap) {
            return;
        }

        if (JS == null || HTML == null) {
            HTML = load(FxmlConstants.HTML_DRIVER_MAPS_DIRECTORY);
            JS = load(FxmlConstants.JAVASCRIPT_DRIVER_MAPS_DIRECTORY);

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
            if (newState == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) webvis.getEngine().executeScript("window");
                window.setMember("javaBridge", bridge);
                loadedMap = true;
            }
        });
    }

    private String load(String directory) {
        String loaded = null;
        try (Stream<String> reader = Files
                .lines(Paths.get(Objects.requireNonNull(
                        getClass().getClassLoader().getResource(directory)).toURI()))) {
            loaded = reader.collect(Collectors.joining());
        } catch (IOException | URISyntaxException e) {
            Client.logger.error("An error has occurred while loading the HTML maps information", e);
        }
        return loaded;
    }

    public void openWebsite() {
        webvis.getEngine().executeScript("openWebsite()");
    }

    @FXML
    public void changePane(Event event) {
        JFXButton source = (JFXButton) event.getSource();
        if (source.equals(cashoutButton))
            driverCashout.toFront();
        else {
            changePane(source, driverReportButton, driverReport, transferDeliveryButton, transferDelivery, changeAddressButton, changeAddress, settingsButton, settings, startShiftButton, driverHomePage);
        }
    }

    public static void changePane(JFXButton source, JFXButton driverReportButton, Pane driverReport, JFXButton transferDeliveryButton, Pane transferDelivery, JFXButton changeAddressButton, Pane changeAddress, JFXButton settingsButton, Pane settings, JFXButton startShiftButton, Pane driverHomePage) {
        if (source.equals(driverReportButton))
            driverReport.toFront();
        else if (source.equals(transferDeliveryButton))
            transferDelivery.toFront();
        else if (source.equals(changeAddressButton))
            changeAddress.toFront();
        else if (source.equals(settingsButton))
            settings.toFront();
        else if (source.equals(startShiftButton))
            driverHomePage.toFront();
    }

    public void buildDrivers() {
        testEmployee = new Driver("Zach", 0000, "pass", 750, "zach");
    }

    public void loadDeliveries() {
        boolean anyDeliveries = false;
        if (!OrderManager.INSTANCE.getPendingOrdersDelivery().isEmpty())
            anyDeliveries = true;

        if (anyDeliveries) {
            int x = 0;
            for (Order o : OrderManager.INSTANCE.getPendingOrdersDelivery()) {
                Label label = new Label(o.getCustomerDetails().getAddress().toString());
                toClaimDeliveryGrid.add(label, 0, x);
                x++;
            }
        }

    }

    @FXML
    public void login(Event event) {
        driverCredsFrame.toFront();
    }

    @FXML
    public void checkin(Event event) {
        loadCheckIn();
        checkInPane.toFront();
    }

    @FXML
    public void loadDrivers() {

    }

    @FXML
    public void loggedIn(Event event) {
        driverLoggedIn.toFront();
        driverCredsFrame.toBack();
        //testEmployee.loggedIn(true);
        testEmployee.startShift();
        loadCheckIn();
    }

    @FXML
    public void loadCheckIn() {
        if (testEmployee.isWorking()) {
            JFXButton button = new JFXButton(testEmployee.getName());
            button.setOnMouseClicked((EventHandler<Event>) this::addToLineup);
            button.setPrefSize(360, 40);
            button.setMaxSize(360, 40);
            button.setMinSize(360, 40);
            button.setStyle("-fx-font-size: 20; -fx-text-alignment: right; -fx-alignment: right; -fx-border-color: black; -fx-border-radius: 10; -fx-border-width: 2");
            checkedInDrivers.add(button, 0, 0);
        }
    }

    @FXML
    public void addToLineup(Event event) {
        driverHomePage.toFront();
        JFXButton button = new JFXButton(testEmployee.getName());
        button.setOnMouseClicked((EventHandler<Event>) this::claimDelivery);
        button.setPrefSize(200, 50);
        button.setMaxSize(200, 50);
        button.setMinSize(200, 50);
        button.setStyle("-fx-font-size: 25; -fx-border-color: black; -fx-background-color: #800000; -fx-background-radius: 10; -fx-text-fill: white; -fx-border-radius: 10; -fx-border-width: 2");
        driverLineupGrid.add(button, 0, 0);
    }

    @FXML
    public void claimDelivery(Event event) {
        loadDeliveries();
        claimDeliveryPane.toFront();
    }

    @FXML
    public void cancelClaimDeliveries() {
        claimDeliveryPane.toBack();
    }

    @FXML
    public void viewOrders() {

        viewDriverPane.setVisible(true);
    }
}


