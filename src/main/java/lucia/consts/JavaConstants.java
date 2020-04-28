package main.java.lucia.consts;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class JavaConstants {

    public static final Type STRING_LIST_TYPE = new TypeToken<List<String>>(){}.getType();
}
