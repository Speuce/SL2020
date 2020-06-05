package main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.Sides;

import com.jfoenix.controls.JFXButton;
import javafx.scene.layout.Pane;
import main.java.lucia.client.content.menu.item.IDCaster;
import main.java.lucia.client.content.menu.item.descriptor.AddonDescriptor;
import main.java.lucia.client.content.menu.item.descriptor.ItemModifiableDescriptor;
import main.java.lucia.client.content.menu.item.descriptor.SimpleItemDescriptor;
import main.java.lucia.consts.FoodConstants.Dinner.DinnerSidesConstants;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerModules.DinnerAddonPaneCoordinates;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.DinnerModules.DinnerAddonPaneDesigns;
import main.java.lucia.fxml.controllers.impl.DynamicLoading.Dinner.MakeButton.MakeButtonDynamicLoad;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Dynamic Loading Manager for the Sides selection in the FXML
 */
public class SidesDynamicLoad {

    private PickupDeliveryPaneController pickupDeliveryPaneController;
    // the instance of the pickup delivery controller in order to control FXML methods
    public ArrayList<Pane> menuSidesPanes = new ArrayList<>(); //panes for the dinners
    private DinnerSidesConstants dinnerSidesConstants;
    private SidesCoordinates sC;
    private DinnerAddonPaneCoordinates dinnerAddonPaneCoordinates;
    private DinnerAddonPaneDesigns dinnerAddonPaneDesigns;
    private List<SimpleItemDescriptor> dinnerItems;
    private SidesListeners sidesListeners;
    public ArrayList<SidesListeners> sidesListenerList = new ArrayList<>();
    public Pane blankPane;


    // list for the toppings
    private List<AddonDescriptor> sidesList;

    public SidesDynamicLoad(PickupDeliveryPaneController pickupDeliveryPaneController, List<SimpleItemDescriptor> dinnerItems) {
        this.pickupDeliveryPaneController = pickupDeliveryPaneController;
        this.dinnerItems = dinnerItems;
    }

    /**
     * Creates the buttons for the addons associated to the dinner item
     *
     * IF there is no associations (is not an ItemModifiableDescriptor)
     * then it will be left blank, as intended
     *
     * @param pane the Parent addonPane
     * @param dinnerItem the item which is to be used to determine the addons associated
     */
    private void iterateAddOnItems(Pane pane, SimpleItemDescriptor dinnerItem) {
        if(dinnerItem instanceof ItemModifiableDescriptor) {
            dinnerSidesConstants = new DinnerSidesConstants((ItemModifiableDescriptor)dinnerItem);
            sC = new SidesCoordinates((ItemModifiableDescriptor)dinnerItem);
            List<AddonDescriptor> addonList = sC.getDinnerSidesConstants().getDinnerSides();
            JFXButton firstButton = createAddOnButton(sC.getGetStartX(), sC.getGetStartY(), addonList.get(0), sC.getGetSizeX(), sC.getGetSizeY());
            pane.getChildren().add(firstButton);

            for (int x = 1; x < addonList.size(); x++) {
                if (sC.checkLessThanMaxX())
                    sC.addToCurrX(sC.getGetXMargin());
                else if (sC.checkLessThanMaxY()) {
                    sC.resetCurrX();
                    sC.addToCurrY(sC.getGetYMargin());
                }
                //else

                JFXButton button = createAddOnButton(sC.getCurrX(), sC.getCurrY(), addonList.get(x), sC.getGetSizeX(), sC.getGetSizeY());
                pane.getChildren().add(button); // gets the pane at which the buttons are to be stored
            }
        } else { //The pane will be empty, what we want!
             }
    }

    /**
     * Creates the addon panes for each of the items
     *
     * @param parentPane to which will be built upon
     *
     * IF the item is not an instance of modifiableItem, then the pane will be empty
     *                    wanted to be empty, so no modifications can be made
     */
    public void createAddOnPanes(Pane parentPane) {
        for (SimpleItemDescriptor dinnerItem : dinnerItems) {
            Pane addOnPane = new Pane();
            addOnPane.setId(dinnerItem.getBaseName());
            createDinnerPaneAddOnDesigns(addOnPane);
            iterateAddOnItems(addOnPane, dinnerItem);
            parentPane.getChildren().addAll(addOnPane);

            // adds the make button
            MakeButtonDynamicLoad makeButtonDynamicLoad = new MakeButtonDynamicLoad(addOnPane);
            makeButtonDynamicLoad.createMakeButton();

            menuSidesPanes.add(addOnPane);
        }
        blankPane = new Pane();
        createDinnerPaneAddOnDesigns(blankPane);
        parentPane.getChildren().addAll(blankPane);
        parentPane.getChildren().get(parentPane.getChildren().size() - 1).toFront();
        menuSidesPanes.add(blankPane);
    }

    /**
     * Creates the designs for the addonPanes, including the needed coordinates and colours
     *
     * @param pane the parent ADDON pane
     */
    private void createDinnerPaneAddOnDesigns(Pane pane) {
        dinnerAddonPaneCoordinates = new DinnerAddonPaneCoordinates();
        dinnerAddonPaneDesigns = new DinnerAddonPaneDesigns(pane);
        dinnerAddonPaneDesigns.initPaneDesign(pane, dinnerAddonPaneCoordinates.getGetStartX(), dinnerAddonPaneCoordinates.getGetStartY(),
                                              dinnerAddonPaneCoordinates.getGetSizeX(), dinnerAddonPaneCoordinates.getGetSizeY());

    }

    /**
     *  Creates the button when the addon list is being iterated
     *
     *  Calls the design, and creates the listeners for the button
     *  Adds button to the pane, after the fact that the button is tested true that it can be placed
     */
    private JFXButton createAddOnButton(int getX, int getY, AddonDescriptor name, int getSizeX, int getSizeY) {
        JFXButton button = new JFXButton(name.getBaseName());
        SidesDesigns sidesDesigns = new SidesDesigns(name);
        sidesListeners = new SidesListeners(pickupDeliveryPaneController, name, button, sidesDesigns);
        sidesListenerList.add(sidesListeners);

        sidesDesigns.initButtonDesign(button, getX, getY, getSizeX, getSizeY); //todo check button = ...
        sidesListeners.setListeners(); // gets the pane at which the buttons are to be stored

        return button;
    }

    /**
     * Loads the default items accompanied with the dinner
     */
    public void loadDefaultSides(SimpleItemDescriptor dinnerItem) {
        if(dinnerItem instanceof ItemModifiableDescriptor) {
            clearSelectedButtons();
            Map<Integer, Byte> addonsDefault = ((ItemModifiableDescriptor) dinnerItem).getAddonsDefault();
            for (Map.Entry<Integer, Byte> entry : addonsDefault.entrySet()) {
                getSidesListener(new IDCaster<AddonDescriptor>().cast(entry.getKey())).sideClicked(entry.getKey());
            }
        }
    }

    /**
     * Finds the listener for the given dinner
     * @param dinnerItem the current dinner being looked at
     * @return the listener IF there is any
     */
    public SidesListeners getSidesListener(AddonDescriptor dinnerItem) {
        for (SidesListeners sidesListeners : sidesListenerList) {
            if (sidesListeners.getItem().equals(dinnerItem)) {
                return sidesListeners;
            }
        }
            System.out.println("NO SIDES LISTENER FOUND FOR: " + dinnerItem.getBaseName());
        return null;
    }



    /**
     * Clears the selected buttons in the GUI
     */
    public void clearSelectedButtons() {
        for(SidesListeners sidesListeners : sidesListenerList) {
            SidesDesigns sidesDesigns = new SidesDesigns(sidesListeners.getItem());
            sidesListeners.setStyle(sidesDesigns.getDefaultStyleString());
        }
    }
}
