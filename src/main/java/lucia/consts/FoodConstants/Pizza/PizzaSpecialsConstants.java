package main.java.lucia.consts.FoodConstants.Pizza;

import main.java.lucia.client.content.menu.pizza.ToppingType;
import main.java.lucia.consts.FoodConstants.FoodConstants;

import java.util.List;

public class PizzaSpecialsConstants extends FoodConstants {

    private String[] specialPizzas = {"Pep", "Salami", "Beef", "S.Bacon", "B.Bacon", "Sausage", "Ham", "Shrimp", "Mush",
            "Tomato", "Pineapple", "GPepper", "Jalapeno", "Anch", "BOlive", "GOlive", "Banana", "Onion", "Red Onion",
            "SunTom", "Artichoke", "Feta", "Cheese", "Cheddar", "Chorizo", "Chicken", "Prosciutto", "Spinach", "Broc",
            "Eggplant", "RPepper", "ChiliP" };

    public PizzaSpecialsConstants() {

    }

    public List<ToppingType> createLists() {
        //get from json
        return null;
    }

    public String[] getSpecialPizzas() {
        return specialPizzas;
    }
}
