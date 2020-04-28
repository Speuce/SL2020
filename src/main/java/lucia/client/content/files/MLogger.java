package main.java.lucia.client.content.files;

public class MLogger {

    public static void log(String s){
        System.out.println(s);
    }

    public static void parseError(String s){
        log("[Parse Error]: " + s);
    }

    public static void logError(String s){
        log("[ERR]: " + s);
    }
}
