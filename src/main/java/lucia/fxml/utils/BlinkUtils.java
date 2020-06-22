package main.java.lucia.fxml.utils;

import javafx.scene.Node;
import main.java.lucia.client.AsynchronousTaskService;
import main.java.lucia.fxml.FxmlConstants;

import java.awt.*;

/**
 * Utility for making nodes 'blink'
 * @author Matthew Kwiatkowski
 */
public class BlinkUtils {

    private static final Color RED = Color.decode("#AA0000");

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
                    f.setStyle(FxmlConstants.CSS_BACKGROUND + getHex(original));
                }else{
                    f.setStyle(FxmlConstants.CSS_BACKGROUND + getHex(blink));
                }
            }, x*lasting);
        }
    }

    /**
     * Blinks a node, with preservation of css
     * @param f the field to make blink
     * @param blink the color to blink to
     * @param blinks the number of blinks that should occur
     * @param lasting how many milliseconds each blink should last.
     */
    public static void blink(Node f,Color blink, int blinks, long lasting){
        final String initCss = f.getStyle();
        final String cssWithColor = setBackgroundColor(f.getStyle(), blink);
        for(int x = 1; x <(blinks*2+1); x++){
            int finalX = x;
            AsynchronousTaskService.scheduleProcessMils(() ->{
                if(finalX % 2 == 0){
                    f.setStyle(initCss);
                }else{
                    f.setStyle(cssWithColor);
                }
            }, x*lasting);
        }
    }

    /**
     * Sets the background color of the given node, without destroying the css
     * @param css the css of the node to change
     * @param col the color to make the node.
     * @return the new css string
     */
    private static String setBackgroundColor(String css, Color col){
        int ind = css.indexOf(FxmlConstants.CSS_BACKGROUND);
        if(ind > -1){
            int colorIndex = css.indexOf("#", ind);
            return css.substring(0, colorIndex) + getHex(col) + css.substring(colorIndex+7);
        }else{
            String ret = css;
            if (ret.endsWith(";")) {
                ret = ret+";";
            }
            ret = ret+FxmlConstants.CSS_BACKGROUND + getHex(col) + ";";
            return ret;
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
    public static void wrong(Node f){ BlinkUtils.blink(f, RED, 3, 180L);
    }
}
