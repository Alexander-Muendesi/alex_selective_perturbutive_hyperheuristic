package data_classes;

import java.util.ArrayList;

public class Room{
    String roomId;
    int capacity;

    /**
     * 
     * @param roomId
     * @param capacity
     */
    public Room(String roomId, int capacity){
        this.roomId = roomId;
        this.capacity = capacity;
    }

    @Override
    public String toString(){
        return roomId + " " + capacity;
    }
}
