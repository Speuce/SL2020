package main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.scene.input.MouseEvent;

public class Style {

    public static void deactivateHover(MouseEvent event) {
        JFXButton hovered = (JFXButton) event.getSource();
        hovered.getStyleClass().add("ToppingsDefault");
        if(!isEnabled(hovered, "ToppingsSelected")) {
            if (isEnabled(hovered, "ToppingsEnableHover"))
                hovered.getStyleClass().removeAll("ToppingsEnableHover");
        }
    }

    public static void deactivateHoverGreen(Event event) {
        JFXButton hovered = (JFXButton) event.getSource();
        hovered.getStyleClass().add("ToppingsDefaultGreen");
        if(!isEnabled(hovered, "ToppingsSelectedGreen"))
            hovered.getStyleClass().removeAll("ToppingsEnableHoverGreen");
    }

    public static boolean isEnabled(JFXButton button, String styleClass) {
        for (String type : button.getStyleClass())
            if (type.equals(styleClass))
                return true;
        return false;
    }
}
