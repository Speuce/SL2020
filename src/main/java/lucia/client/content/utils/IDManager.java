package main.java.lucia.client.content.utils;

import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Maintains a map of all {@link IDAble} objects, used for ID casting.
 * @author Matthew Kwiatkowski
 */
public class IDManager {

    /**
     * The current instance of idmanager
     */
    public static IDManager instance = new IDManager();


    //-----------------

    /**
     * Provides mappings of IDAble objects.
     */
    private Map<Integer, IDAble> map;

    /**
     * The Next available id
     */
    private int nextAvailID = 0;

    /**
     * Adds a mapping for the given idable
     * @param idAble the idable to add mapping for.
     */
    public void addMapping(IDAble idAble){
        int id = idAble.getId();
        nextAvailID = Math.max(nextAvailID, id+1);
        map.put(id, idAble);
    }

    /**
     * Get the given {@link IDAble} given the id
     * @param id the Object, if found, null otherwise
     */
    @Nullable
    public IDAble getMapping(int id){
        if(id < map.size()){
            return map.get(id);
        }
        return null;
    }
    
    /**
     * Increments the next available id, returns it
     */
    public int getNextAvailID(){
        nextAvailID++;
        return nextAvailID;
    }



}
