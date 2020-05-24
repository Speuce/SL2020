package main.java.lucia.consts.FoodConstants.Dinner;

/**
 * Class that gives the constants for the line between the dinner halves in FXML
 *
 * Items | Modifiable Items
 *
 * This is for the '|'
 */
public class DinnerLineConstants {

    //coordinates and values needed to create the line
    private final int layoutX;
    private final int layoutY;
    private final int startX;
    private final int startY;
    private final int endX;
    private final int endY;
    private final int strokeWidth;


    public DinnerLineConstants() {
        layoutX = 669;
        layoutY = 215;
        startX = 34;
        startY = -215;
        endX = 34;
        endY = 564;
        strokeWidth = 3;
    }

    /** ACCESSORS **/
    public int getLayoutX() {
        return layoutX;
    }

    public int getLayoutY() {
        return layoutY;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }
}
