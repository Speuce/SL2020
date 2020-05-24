package main.java.lucia.client.content.utils;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

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
    private ArrayList<IDAble> map = new ArrayList<>();

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
        if(nextAvailID <= id){
            nextAvailID = id;
        }
        if(map.size() < id+1){
            addItems(id - map.size()+1);
        }
        map.set(id, idAble);
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
     * Adds x null entries into the list
     */
    private void addItems(int x){
        for(int i = 0; i < x; i++){
            map.add(null);
        }
    }

    /**
     * Increments the next available id, returns it
     */
    public int getNextAvailID(){
        nextAvailID++;
        return nextAvailID;
    }



}
