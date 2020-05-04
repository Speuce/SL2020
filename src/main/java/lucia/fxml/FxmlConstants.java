package main.java.lucia.fxml;

import com.google.common.collect.ImmutableList;

/**
 * A list of all of the constants associated
 * with FXML
 *
 * @author Brett Downey
 */
public class FxmlConstants {
    public static final String LOGIN_FXML_DIRECTORY = "fxml/loginWindow.fxml";

    public static final String MAIN_FXML_DIRECTORY = "fxml/mainGUI.fxml";

    public static final String HTML_MAPS_DIRECTORY = "map/map.html";

    public static final String HTML_DRIVER_MAPS_DIRECTORY = "map/driverMap.html";

    public static final String JAVASCRIPT_MAPS_DIRECTORY = "map/main.js";

    public static final String JAVASCRIPT_DRIVER_MAPS_DIRECTORY = "map/driverMain.js";

    public static final String BETA_FXML_DIRECTORY = "fxml/testerGUI.fxml";

    public static final int LOGIN_WIDTH = 655;

    public static final int LOGIN_HEIGHT = 490;

    public static final ImmutableList<String> IGNORED_FXML_EXCEPTIONS =
        ImmutableList.of("java.lang.IllegalStateException: pipeline already created:com.sun.prism");
}