package main.java.lucia.client.content.files;

import main.java.lucia.Client;

import java.io.PrintStream;

public class MLogger {

    public static PrintStream getErrorStream(){
        return System.err;
    }


    public static void log(String s){
        System.out.println(s);
    }

    public static void parseError(String s){
        log("[Parse Error]: " + s);
    }

    public static void logError(String s){
        Client.logger.error(s, new Exception(s));
    }
}
