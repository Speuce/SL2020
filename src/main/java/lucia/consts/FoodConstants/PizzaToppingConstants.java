package main.java.lucia.consts.FoodConstants;

public class PizzaToppingConstants extends FoodConstants {

    private String[] toppings = {"Pep", "Salami", "Beef", "S.Bacon", "B.Bacon", "Sausage", "Ham", "Shrimp", "Mush",
            "Tomato", "Pineapple", "GPepper", "Jalapeno", "Anch", "BOlive", "GOlive", "Banana", "Onion", "Red Onion",
            "SunTom", "Artichoke", "Feta", "Cheese", "Cheddar", "Chorizo", "Chicken", "Prosciutto", "Spinach", "Broc",
            "Eggplant", "RPepper", "ChiliP" };

    public PizzaToppingConstants() {
        setInitX(18);
        setInitY(5);
        setxMarginTopping(189);
        setyMarginTopping(90);
        setToppingSizeX(171);
        setToppingSizeY(80);
        setMaxX(1341);
        setMaxY(457);
    }


    public void createLists() {
        // get from json
    }

    public String[] getToppingsList() {
        return toppings;
    }
}
