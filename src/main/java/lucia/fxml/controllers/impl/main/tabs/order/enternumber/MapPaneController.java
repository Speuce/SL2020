package main.java.lucia.fxml.controllers.impl.main.tabs.order.enternumber;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.lucia.Client;
import main.java.lucia.client.AsynchronousTaskService;
import main.java.lucia.client.content.javascript.JavaScriptBridge;
import main.java.lucia.fxml.FxmlConstants;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Controller for the map pane
 * @author Matthew Kwiatkowski
 * @author Brett Downeye
 */
public class MapPaneController {

    @FXML
    private Pane mappane;

    @FXML
    private WebView webvis;

    @FXML
    private ImageView refreshImage1;

    /**
     * The flag which indicates if the Google Maps {@link WebView} was loaded
     */
    private boolean loadedMap;

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

    @FXML
    private void initialize(){
        AsynchronousTaskService.process(() -> Platform.runLater(this::enableMap));
    }

    /**
     * Shows the given address on the map.
     * @param streetName the address to show, i.e '123 Abc Street'
     */
    public void showAddress(final String streetName){
        AsynchronousTaskService.process(() -> Platform.runLater(() -> {
                String combined = streetName + " Manitoba Canada";
                //System.out.println("----------- : " + combined);
                String script = ("findLocation(" + "'" + combined + "'" + ")");
                webvis.getEngine().executeScript(script); // TODO Make work for more than just Winnipeg when we need to
        }));
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
            if (newState == Worker.State.SUCCEEDED) {
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

    @FXML
    void openWebsite(MouseEvent event) {

    }
}
