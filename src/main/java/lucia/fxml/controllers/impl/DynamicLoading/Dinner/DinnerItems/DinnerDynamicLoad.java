package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerItems;

import com.jfoenix.controls.JFXButton;
import javafx.scene.layout.Pane;
import main.java.lucia.client.content.menu.Menu;
import main.java.lucia.client.content.menu.item.descriptor.SimpleItemDescriptor;
import main.java.lucia.consts.FoodConstants.Dinner.DinnerConstants;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerModules.DinnerPaneCoordinates;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerModules.DinnerPaneDesigns;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.Sides.SidesDynamicLoad;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;

import java.util.ArrayList;
import java.util.List;

/**
 * Dynamic Loading Manager for the Dinner selection in the FXML
 */
public class DinnerDynamicLoad {
    Menu menuInstance = Menu.get; // menu instance
    public ArrayList<Pane> menuPaneModules = new ArrayList<>(); //panes for the dinners
    private DinnerConstants dinnerConstants = new DinnerConstants();
    private DinnerCoordinates dC;
    private SidesDynamicLoad sidesDynamicLoad;
    private DinnerPaneCoordinates dinnerPaneCoordinates;
    private DinnerPaneDesigns dinnerPaneDesigns;
    private List<SimpleItemDescriptor> dinnerItems;

    /**
     * Pickup Delivery Controller
     *
     * Adding the Dinners to their desired panes
     */
    private PickupDeliveryPaneController pickupDeliveryPaneController;

    // list for the dinner modules
    private List<String> dinnerList = dinnerConstants.getDinners();

    public DinnerDynamicLoad(PickupDeliveryPaneController pickupDeliveryPaneController) {
        this.pickupDeliveryPaneController = pickupDeliveryPaneController;
    }

    /**
     *  Initial method to start the loading of all of the dinner buttons
     *
     *  Other features can be added in the beginning stages of the dynamic loading
     */
    public void createDinners() {
        iterateDinnerSections();
    }

    /**
     * Iterates through the different modules, making the items per module
     */
    private void iterateDinnerSections() {
        for(int x = 0; x < dinnerList.size(); x++) {
            dinnerItems = menuInstance.getSection(dinnerList.get(x));
            Pane pane = new Pane();
            pane.setId(dinnerList.get(x));
            createDinnerPane(pane, dinnerItems); //todo check
            iterateItems(pane, dinnerItems);
            menuPaneModules.add(pane);
        }
        pickupDeliveryPaneController.paneModules.getChildren().addAll(menuPaneModules);
    }

    /**
     * Creates the dinner pane which contains the items of the section and the addons associated
     *
     * @param pane parentPane to which will be built upon
     * @param dinnerItems the dinner items of the current section
     */
    private void createDinnerPane(Pane pane, List<SimpleItemDescriptor> dinnerItems) {
        createDinnerPaneDesigns(pane);
        sidesDynamicLoad = new SidesDynamicLoad(pickupDeliveryPaneController, dinnerItems);
        sidesDynamicLoad.createAddOnPanes(pane);
    }

    /**
     * Creates the designs for the parentPane, including needed coordinates and colours
     *
     * @param pane the parent pane
     */
    private void createDinnerPaneDesigns(Pane pane) {
        dinnerPaneCoordinates = new DinnerPaneCoordinates();
        dinnerPaneDesigns = new DinnerPaneDesigns(pane);
        dinnerPaneDesigns.initPaneDesign(pane, dinnerPaneCoordinates.getGetStartX(), dinnerPaneCoordinates.getGetStartY(),
                                         dinnerPaneCoordinates.getGetSizeX(), dinnerPaneCoordinates.getGetSizeY());
    }


    /**
     *  Goes through the items list and adds all of them
     *  given the rules are correct for the entire list
     *
     *  Will add the items in the list up to the point where it cannot go farther
     *
     *  todo will make into scrollpane for the ability for infinite buttons
     */
    private void iterateItems(Pane pane, List<SimpleItemDescriptor> dinnerItems) {
        dC = new DinnerCoordinates();
        JFXButton firstButton = createButton(dC.getGetStartX(), dC.getGetStartY(), dinnerItems.get(0), dC.getGetSizeX(), dC.getGetSizeY());
        pane.getChildren().add(firstButton);

        for(int x = 1; x < dinnerItems.size(); x++) {
            if(dC.checkLessThanMaxX()) {
                dC.addToCurrX(dC.getGetXMargin());
            }
            else if(dC.checkLessThanMaxY()) {
                dC.resetCurrX();
                dC.addToCurrY(dC.getGetYMargin());
            }
            //else

            JFXButton button = createButton(dC.getCurrX(), dC.getCurrY(), dinnerItems.get(x), dC.getGetSizeX(), dC.getGetSizeY());
            pane.getChildren().add(button); // gets the pane at which the buttons are to be stored
        }
    }

    /**
     *  Creates the button when the list is being iterated
     *
     *  Calls the design, and creates the listeners for the button
     *  Adds button to the pane, after the fact that the button is tested true that it can be placed
     */
    private JFXButton createButton(int getX, int getY, SimpleItemDescriptor name, int getSizeX, int getSizeY) {
        JFXButton button = new JFXButton(name.getBaseName());
        DinnerDesigns dinnerDesigns = new DinnerDesigns(name);
        DinnerListeners dinnerListeners = new DinnerListeners(pickupDeliveryPaneController, name, button, dinnerDesigns, sidesDynamicLoad);

        dinnerDesigns.initButtonDesign(button, getX, getY, getSizeX, getSizeY); //todo check button = ...
        dinnerListeners.setListeners(); // gets the pane at which the buttons are to be stored

        return button;
    }

    /**
     * LISTENERS
     */
    public PickupDeliveryPaneController getPickupDeliveryPaneController() {
        return pickupDeliveryPaneController;
    }
}
