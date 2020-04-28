package main.java.lucia.fxml.controllers.impl.main;

/**
 * Represents a compatible resolution for the program
 *
 * @author Matthew Kwiatkowski
 */
public class Resolution {
    private double width, height;
    private double scaleX, scaleY, offsetX, offSetY;

    public Resolution(double width, double height, double scaleX, double scaleY, double offsetX, double offSetY) {
        this.width = width;
        this.height = height;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.offsetX = offsetX;
        this.offSetY = offSetY;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getScaleX() {
        return scaleX;
    }

    public double getScaleY() {
        return scaleY;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffSetY() {
        return offSetY;
    }
}
