package main.java.lucia.consts;

public class PizzaListConstants {

    private String[] toppings = {"Pep", "Salami", "Beef", "S.Bacon", "B.Bacon", "Sausage", "Ham", "Shrimp", "Mush",
            "Tomato", "Pineapple", "GPepper", "Jalapeno", "Anch", "BOlive", "GOlive", "Banana", "Onion", "Red Onion",
            "SunTom", "Artichoke", "Feta", "Chorizo", "Chicken", "Prosciutto", "Spinach", "Broc", "Eggplant", "RPepper", "ChiliP" };

    private final int xMarginTopping = 189;
    private final int yMarginTopping = 90;
    private final int initX = 18;
    private final int initY = 5;
    private final int toppingSizeX = 171;
    private final int toppingSizeY = 80;



    public void createLists() {
        // get from json
    }

    public String[] getToppingsList() {
        return toppings;
    }

    public int getxMarginTopping() {
        return xMarginTopping;
    }

    public int getyMarginTopping() {
        return yMarginTopping;
    }

    public int getInitX() {
        return initX;
    }

    public int getInitY() {
        return initY;
    }

    public int getToppingSizeX() {
        return toppingSizeX;
    }

    public int getToppingSizeY() {
        return toppingSizeY;
    }
}
