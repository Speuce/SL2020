package main.java.lucia.fxml.utils;

import javafx.scene.Node;
import main.java.lucia.client.AsynchronousTaskService;

import java.awt.*;

/**
 * Utility for making nodes 'blink'
 * @author Matthew Kwiatkowski
 */
public class BlinkUtils {

    /**
     * Blinks a textbox, as seen with employee login
     * @param f the field to make blink
     * @param original the original color to revert to
     * @param blink the color to blink to
     *
     */
    public static void blink(Node f, Color original, Color blink, int blinks, long lasting){
        for(int x = 1; x <(blinks*2+1); x++){
            final int y = x;
            AsynchronousTaskService.scheduleProcessMils(() ->{
                if(y % 2 == 0){
                    f.setStyle("-fx-background-color: " + getHex(original));
                }else{
                    f.setStyle("-fx-background-color: " + getHex(blink));
                }
            }, x*lasting);
        }
    }

    /**
     * Blinks a textbox, as seen with employee login
     * @param f the field to make blink
     * @param original the original color to revert to
     * @param blink the color to blink to
     */
    public static void blink(Node f, Color original, Color blink){
        blink(f, original, blink, 3, 180L);
    }

    /**
     * Used for converting {@link Color}
     * @param c the Color you wish to convert
     * @return the Hex String beginning with an '#'
     */
    public static String getHex(Color c){
        return String.format("#%02X%02X%02X", c.getRed(), c.getGreen(),c.getBlue());
    }

    /**
     * Blinks the given node red.
     */
    public static void wrong(Node f){ BlinkUtils.blink(f, Color.white, Color.decode("#AA0000"));
    }
}
