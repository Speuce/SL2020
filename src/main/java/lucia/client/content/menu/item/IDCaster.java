package main.java.lucia.client.content.menu.item;

import main.java.lucia.client.content.files.MLogger;
import main.java.lucia.client.content.menu.Menu;

public class IDCaster<T extends IDAble> {

    public T cast(int id){
        IDAble d = Menu.get.getItemFromId(id);
        T result = null;
        if(d == null){
            MLogger.logError("Error casting id: " + id + " -- Id not found!");
        }else{
            try{
                //noinspection unchecked
                result = (T)d;
            }catch (ClassCastException e){
                MLogger.logError("Error casting id: " + id + " -- Not correct type!");
            }
        }
        return result;
    }
}
