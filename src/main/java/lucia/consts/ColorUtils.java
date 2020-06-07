package main.java.lucia.consts;

import main.java.lucia.client.content.files.MLogger;

import java.awt.*;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Utility for converting {@link java.awt.Color}
 * to Hex and back
 * @author Matthew Kwiatkowski
 */
public class ColorUtils {

    private static Pattern hexPattern = Pattern.compile("#[0-9A-F]{6}");

    private static Random rand = new Random();

    /**
     * Converts a given string into hex format
     * @param c the Color to convert
     * @return the color represented in hex format #ABCDEF
     */
    public static String toHex(Color c){
        return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }

    /**
     * Converts a given hex string to a java AWT color
     * @param hex the color, in hex format #ABCDEF
     * @return the color, as {@link Color}
     */
    public static Color fromHex(String hex){
        return Color.decode(hex);
    }

    /**
     * Converts a given hex string to javafx scene paint color
     * @param hex the color, in hex format #ABCDEF
     * @return the color as {@link javafx.scene.paint.Color}
     */
    public static javafx.scene.paint.Color fromHexfx(String hex){
        return javafx.scene.paint.Color.web(hex);
    }

    /**
     * Checks if a given string matches hex.
     * Throws a lil' error if it doesnt.
     */
    public static boolean matchesHex(String s){
        if(hexPattern.matcher(s).matches()){
            return true;
        }
        MLogger.logError("Error paring hex: " + s);
        return false;
    }

    /**
     * Parses a given string of hex.
     * @param s the hex to parse
     * @return the hex string, if valid, or a fun random color if there is an issue
     */
    public static String parseHex(String s){
        if(matchesHex(s)){
            return s;
        }
        return randomHex();
    }

    /**
     * Generates a random hex color
     */
    public static String randomHex(){
        String zeros = "000000";
        Random rnd = new Random();
        String s = String.format("%06x", rnd.nextInt(0x1000000));
        s = zeros.substring(s.length()) + s;
        return '#' + s.toUpperCase();
    }


}
