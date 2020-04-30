package main.java.lucia.fxml.controllers.impl.DynamicLoading;

import com.jfoenix.controls.JFXButton;
import javafx.scene.paint.Paint;
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
        int getX = pizzaListConstants.getInitX();
        int getY = pizzaListConstants.getInitY();

    }

    public void test() {
        JFXButton button = new JFXButton();
        button.setTextFill(Paint.valueOf("red"));
        button.setText("TEST");
        button.setPrefSize(100,100);
        
        pizzaController.pizzaButtons.getChildren().add(button);
    }
    public JFXButton createButton(int getX, int getY) {
        PizzaController controller;
        return null;
    }
}
