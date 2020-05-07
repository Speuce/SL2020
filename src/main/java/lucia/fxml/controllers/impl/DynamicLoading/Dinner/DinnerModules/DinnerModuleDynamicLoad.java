package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerModules;

import com.jfoenix.controls.JFXButton;
import main.java.lucia.consts.FoodConstants.Dinner.DinnerButtonTabConstants;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerItems.DinnerDynamicLoad;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Dynamic Loading Manager for the Dinner Module in the FXML
 */
public class DinnerModuleDynamicLoad {
    private PickupDeliveryPaneController pickupDeliveryPaneController;
    // the instance of the pickup delivery pane in order to control FXML methods
    private DinnerButtonTabConstants dinnerButtonTabConstants = new DinnerButtonTabConstants();
    private DinnerModuleCoordinates dC = new DinnerModuleCoordinates();
    private DinnerDynamicLoad dinnerDynamicLoad;

    // list of the dinner modules
    private List<String> dinnerModuleList;

    public DinnerModuleDynamicLoad(PickupDeliveryPaneController pickupDeliveryPaneController, DinnerDynamicLoad dinnerDynamicLoad) {
        this.pickupDeliveryPaneController = pickupDeliveryPaneController;
        this.dinnerDynamicLoad = dinnerDynamicLoad;
        dinnerModuleList = dinnerButtonTabConstants.getDinnerModuleList();
    }

    /**
     *  Initial method to start the loading of the dinner module buttons
     */
    public void createDinnerModules() {
        JFXButton firstButton = createButton(dC.getGetStartX(), dC.getGetStartY(), "Pizza", dC.getGetSizeX(), dC.getGetSizeY());
        pickupDeliveryPaneController.PaneChange.getChildren().add(firstButton);
        // gets the pane at which the buttons are to be stored
        iterateModules();
    }

    /**
     *  Goes through the dinner module list and adds all of them
     *  given the rules are correct for the entire list
     *
     *  Will add the Modules in the list up to the point where it cannot go farther
     *
     *  todo will make into scrollpane for the ability for infinite buttons
     */
    private void iterateModules() {
        for(int x = 0; x < dinnerModuleList.size(); x++) {
            if(dC.checkLessThanMaxX())
                dC.addToCurrX(dC.getGetXMargin());
            else System.out.println("WARNING: ScrollPane for The Modules may get large!");

            JFXButton button = createButton(dC.getCurrX(), dC.getGetStartY(), dinnerModuleList.get(x), dC.getGetSizeX(), dC.getGetSizeY());
            pickupDeliveryPaneController.PaneChange.getChildren().add(button); // gets the pane at which the buttons are to be stored
        }
    }

    /**
     *  Creates the button when the list is being iterated
     *
     *  Calls the design, and creates the listeners for the button
     *  Adds button to the pane, after the fact that the button is tested true that it can be placed
     */
    private JFXButton createButton(int getX, int getY, String name, int getSizeX, int getSizeY) {
        name = StringUtils.capitalize(name);
        JFXButton button = new JFXButton(name);
        DinnerModuleDesigns dinnerModuleDesigns = new DinnerModuleDesigns(name);
        DinnerModuleListeners dinnerModuleListeners = new DinnerModuleListeners(pickupDeliveryPaneController, dinnerDynamicLoad,
                                                                                name, button);

        dinnerModuleDesigns.initButtonDesign(button, getX, getY, getSizeX, getSizeY); //todo check button = ...
        dinnerModuleListeners.setListeners();

        return button;
    }
}
