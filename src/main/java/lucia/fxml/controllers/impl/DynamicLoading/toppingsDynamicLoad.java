package main.java.lucia.fxml.controllers.impl.DynamicLoading;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Cursor;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import main.java.lucia.consts.FoodConstants.PizzaToppingConstants;
import main.java.lucia.fxml.controllers.impl.main.tabs.order.PickupDeliveryPane.Controllers.PizzaController;

public class toppingsDynamicLoad {

    public toppingsDynamicLoad(PizzaController pizzaController) {
        this.pizzaController = pizzaController;
    }

    private PizzaController pizzaController;
    PizzaToppingConstants pizzaToppingConstants = new PizzaToppingConstants();
    private String[] toppingsList = pizzaToppingConstants.getToppingsList();

    public void createToppings() {
        int getStartX = pizzaToppingConstants.getInitX();
        int getStartY = pizzaToppingConstants.getInitY();
        int currX = getStartX;
        int currY = getStartY;
        int getSizeX = pizzaToppingConstants.getToppingSizeX();
        int getSizeY = pizzaToppingConstants.getToppingSizeY();
        int getXMargin = pizzaToppingConstants.getxMarginTopping();
        int getYMargin = pizzaToppingConstants.getyMarginTopping();
        int getMaxY = pizzaToppingConstants.getMaxY() - getYMargin;
        int getMaxX = pizzaToppingConstants.getMaxX() - getXMargin;

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
