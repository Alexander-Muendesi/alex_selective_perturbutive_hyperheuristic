package data_classes;

import java.util.ArrayList;

public class Rooms extends Data{
    ArrayList<String> roomId;
    ArrayList<Integer> capacity;

    /**
     * 
     * @param numCourses
     * @param numRooms
     * @param numDays
     * @param periodsPerDay
     * @param numCurricula
     * @param numConstraints
     */
    public Rooms(int numCourses, int numRooms, int numDays, int periodsPerDay, int numCurricula, int numConstraints){
        super(numCourses, numRooms, numDays, periodsPerDay, numCurricula, numConstraints);
        roomId = new ArrayList<String>();
        capacity = new ArrayList<Integer>();
    }

    /**
     * 
     * @param roomId
     * @param capacity
     */
    public void addEntry(String roomId, int capacity){
        this.roomId.add(roomId);
        this.capacity.add(capacity);
    }

    public String getRoomId(int index){
        return this.roomId.get(index);
    }

    public int getCapacity(int index){
        return this.capacity.get(index);
    }
}
