/*
 * Copyright (c) Zachery Unrau
 */

package main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.java.lucia.client.content.menu.legacy.Menu;
import main.java.lucia.client.content.menu.legacy.impl.Italian;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadeAddonNames;
import main.java.lucia.client.content.menu.legacy.premade.impl.names.PremadeItalianName;
import main.java.lucia.fxml.controllers.impl.main.Utils.ParentController;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.PickupDeliveryPaneController;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Style;

public class ItalianController implements ParentController<PickupDeliveryPaneController> {

    @FXML
    private Pane italianPane;

    @FXML
    private JFXButton ravioli;

    @FXML
    private JFXButton spaghetti;

    @FXML
    private JFXButton lasagna;

    @FXML
    private JFXButton fettuccine;

    @FXML
    private JFXButton chicken_parmesan;

    @FXML
    private JFXButton manicotti;

    @FXML
    private Pane italianCustomPane;

    @FXML
    private JFXButton XCHEESE;

    @FXML
    private JFXButton MUSHROOM_PASTA;

    @FXML
    private JFXButton XXCHEESE;

    @FXML
    private JFXButton NO_CHEESE;

    @FXML
    private JFXButton MEATSAUCE;

    @FXML
    private JFXButton MARINARA;

    @FXML
    private JFXButton CHICKEN_PASTA;

    @FXML
    private JFXButton MEATBALLS_PASTA;

    @FXML
    private JFXButton ALFREDO;

    @FXML
    private JFXButton SHRIMP_PASTA;

    @FXML
    private JFXButton CHEESE_TOAST_UPGRADE;

    JFXButton[] cheeseArrayItalian;
    JFXButton[] sauceArray;

    private PickupDeliveryPaneController parent;
    private Italian pasta = new Italian("test");

    @FXML
    public void initialize() {
        cheeseArrayItalian = new JFXButton[]{XCHEESE, XXCHEESE, NO_CHEESE};
        sauceArray = new JFXButton[]{MEATSAUCE, MARINARA, ALFREDO};
    }
    public void setPasta(Italian pasta) {
        this.pasta = pasta;
    }
    public Italian getPasta() {
        return pasta;
    }
    public void setItalianPane(Pane italianPane) {
        this.italianPane = italianPane;
    }
    @Override
    public void setParent(PickupDeliveryPaneController p) {
        this.parent = p;
    }
    public boolean isEnabled(JFXButton button, String styleClass) {
        for (String type : button.getStyleClass())
            if (type.equals(styleClass))
                return true;
        return false;
    }

    // ------ "ADD TO ORDER" SERIES ------
    @FXML
    void addToOrderPasta(MouseEvent event) {
        JFXButton[] buttons = {ravioli, spaghetti, lasagna, fettuccine, chicken_parmesan, manicotti};
        JFXButton[] buttonsCustom = {MUSHROOM_PASTA, MEATSAUCE, MARINARA, CHICKEN_PASTA, MEATBALLS_PASTA, ALFREDO, SHRIMP_PASTA, CHEESE_TOAST_UPGRADE};
        try {
            for (int x = 0; x < buttons.length; x++)
                if (isEnabled(buttons[x], "ToppingsSelected")) {
                    if(buttons[x].equals(chicken_parmesan) || buttons[x].equals(manicotti)) {
                        pasta = (Italian) Menu.loadedPreMadeFoods.getPremadeItemSafe(PremadeItalianName.valueOf(buttons[x].getId().toUpperCase()).name());
                        pasta.setDiscountEligible(false);
                    }
                    else {
                        pasta = (Italian)Menu.loadedPreMadeFoods.getPremadeItemSafe(PremadeItalianName.valueOf(buttons[x].getId().toUpperCase()).name());
                        pasta.setDiscountEligible(true);
                    }
                    buttons[x].getStyleClass().removeAll("ToppingsSelected");
                }
            for (int x = 0; x < buttonsCustom.length; x++)
                if (isEnabled(buttonsCustom[x], "ToppingsSelectedGreen")) {
                    if (isEnabled(buttonsCustom[x], "ToppingsSelectedGreen"))
                        pasta.addAddon(Menu.loadedPreMadeFoods.getAddons().get(PremadeAddonNames.valueOf(buttonsCustom[x].getId().toUpperCase())));
                    buttonsCustom[x].getStyleClass().removeAll("ToppingsSelectedGreen");
                }
            if(isEnabled(NO_CHEESE, "ToppingsSelectedGreen")) {
                pasta.setNoCheese(true);
                NO_CHEESE.getStyleClass().removeAll("ToppingsSelectedGreen");
            }
            if(isEnabled(XCHEESE, "ToppingsSelectedGreen")) {
                pasta.setExtraCheese(true);
                XCHEESE.getStyleClass().removeAll("ToppingsSelectedGreen");
            }
            if(isEnabled(XXCHEESE, "ToppingsSelectedGreen")) {
                pasta.setExtraExtraCheese(true);
                XXCHEESE.getStyleClass().removeAll("ToppingsSelectedGreen");
            }

        }
        catch (Exception e) {
            System.out.println(e);
        }
        pasta.calcNameAndPrice();
        //parent.addToOrder(pasta);
        parent.orderCount++;
        italianCustomPane.setVisible(false);
        pasta.clearSI();
        pasta.clearAddons();
    }

    // ------ "HOVER" SERIES ------
    @FXML
    void deactivateHover(MouseEvent event) {
        Style.deactivateHover(event);
    }
    @FXML
    void deactivateHoverGreen(MouseEvent event) {
        Style.deactivateHoverGreen(event);
    }
    @FXML
    void activateHover(MouseEvent event) {
        parent.activateHover(event);
    }
    @FXML
    void activateHoverGreen(MouseEvent event) {
        Style.deactivateHoverGreen(event);
    }

    // ------ "SELECTED" SERIES ------
    @FXML
    void selectedPasta(MouseEvent event) {
        parent.setOptionPanesVisible(false);
        parent.changeGroupButton(event, italianPane, "ToppingsSelected");
        italianCustomPane.setVisible(true);
    }

    @FXML
    void selectedPastaCustom(MouseEvent event) {
        parent.setOptionPanesVisible(false);
        changeGroupButtonPasta(event, italianCustomPane, "ToppingsSelectedGreen");
    }

    // ------ "CHANGE GROUP BUTTON" SERIES ------
    public void changeGroupButtonPasta(MouseEvent event, Pane pane, String selectedCSS) {
        JFXButton button = (JFXButton) event.getSource();
        if (isEnabled(button, selectedCSS))
            button.getStyleClass().removeAll(selectedCSS);
        else {
            if (button.equals(XCHEESE) || button.equals(XXCHEESE) || button.equals(NO_CHEESE))
                for (int x = 0; x < cheeseArrayItalian.length; x++) {
                    cheeseArrayItalian[x].getStyleClass().removeAll(selectedCSS);
                    button.getStyleClass().add(selectedCSS);
                }
            if (button.equals(MEATSAUCE) || button.equals(MARINARA) || button.equals(ALFREDO)) {
                for (int x = 0; x < sauceArray.length; x++) {
                    sauceArray[x].getStyleClass().removeAll(selectedCSS);
                    button.getStyleClass().add(selectedCSS);
                }
            }
            if (button.equals(MUSHROOM_PASTA) || button.equals(CHICKEN_PASTA) || button.equals(MEATBALLS_PASTA) || button.equals(SHRIMP_PASTA))
                button.getStyleClass().add(selectedCSS);
            if(button.equals(CHEESE_TOAST_UPGRADE))
                button.getStyleClass().add(selectedCSS);
        }
    }

    @Override
    public void close() {

    }

    @Override
    public Object getCurrentItem() {
        return null;
    }

}
