package main.java.lucia.client.content.utils;

import main.java.lucia.client.content.files.MLogger;
import main.java.lucia.client.content.menu.Menu;

/**
 * This is used for converting numerical id's to the respective
 * menu items that they represent.
 * @param <T> the type of the item we want to cast to.
 */
public class IDCaster<T extends IDAble> {

    /**
     * Cast the given id to the givn type
     * @param id the id of the item we want to cast
     * @return an object of type T with the id we wanted.
     */
    public T cast(int id){
        IDAble d = IDManager.instance.getMapping(id);
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
