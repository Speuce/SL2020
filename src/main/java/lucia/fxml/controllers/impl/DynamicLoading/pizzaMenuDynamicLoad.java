package main.java.lucia.fxml.controllers.impl.DynamicLoading;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Cursor;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import main.java.lucia.consts.PizzaListConstants;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

public class pizzaMenuDynamicLoad {

    public pizzaMenuDynamicLoad(PizzaController pizzaController) {
        this.pizzaController = pizzaController;
    }

    PizzaController pizzaController;


    PizzaListConstants pizzaListConstants = new PizzaListConstants();
    private String[] toppingsList = pizzaListConstants.getToppingsList();

    public void createToppings() {
        int getStartX = pizzaListConstants.getInitX();
        int getStartY = pizzaListConstants.getInitY();
        int currX = getStartX;
        int currY = getStartY;
        int getSizeX = pizzaListConstants.getToppingSizeX();
        int getSizeY = pizzaListConstants.getToppingSizeY();
        int getXMargin = pizzaListConstants.getxMarginTopping();
        int getYMargin = pizzaListConstants.getyMarginTopping();
        int getMaxY = pizzaListConstants.getMaxY() - getYMargin;
        int getMaxX = pizzaListConstants.getMaxX() - getXMargin;

        JFXButton firstButton = createButton(getStartX, getStartY, toppingsList[0], getSizeX, getSizeY);
        pizzaController.pizzaButtons.getChildren().add(firstButton);

        for(int x = 1; x < toppingsList.length; x++) {
            if(currX + getXMargin <= getMaxX)
                currX += getXMargin;
            else if((currY + getYMargin) <= getMaxY){
                currX = getStartX;
                currY += getYMargin;
            }
            //else

            JFXButton button = createButton(currX, currY, toppingsList[x], getSizeX, getSizeY);
            pizzaController.pizzaButtons.getChildren().add(button);

            //todo rechange cheese methods
        }

    }

    public JFXButton createButton(int getX, int getY, String name, int getSizeX, int getSizeY) {
        JFXButton button = new JFXButton(name);
        button.setTextFill(Paint.valueOf("white"));
        button.setFont(Font.font("Modern No. 20"));
        button.getStyleClass().add("ToppingsDefault");
        button.setStyle("-fx-font-size: 30");
        button.getStylesheets().add("..\\..\\..\\css\\main.css"); //todo
        button.setLayoutX(getX);
        button.setLayoutY(getY);
        button.setPrefSize(getSizeX, getSizeY);
        button.setCursor(Cursor.OPEN_HAND);

        button.setOnMouseClicked(pizzaController::selected);
        button.setOnMouseEntered(pizzaController::activateHover);
        button.setOnMouseExited(pizzaController::deactivateHover);

        //fxid?
        button.setId(name.toLowerCase());
        return button;
    }
}
