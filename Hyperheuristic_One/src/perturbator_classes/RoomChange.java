package perturbator_classes;

import java.util.Random;

import data_classes.DataReader;

public class RoomChange extends Heuristic {
    
    /**
     * 
     * @param n
     * @param random
     * @param reader
     * @param alpha
     * @param beta
     * @param delta
     */
    public RoomChange(int n, Random random, DataReader reader, double alpha, double beta, double delta){
        super(n, random, reader, alpha, beta, delta);
    }

    /**
     * this method moves the current course to another room within the same day and period
     */
    public String[][][] executeHeuristic(String[][][] timetable){
        String[][][] copy = createTimetableCopy(timetable);

        int roomOne = random.nextInt(reader.rooms.size());
        int roomTwo = random.nextInt(reader.rooms.size());
        int day = random.nextInt(reader.numDays);
        int period = random.nextInt(reader.periodsPerDay);

        while(roomOne == roomTwo){
            roomOne = random.nextInt(reader.rooms.size());
        }

        String temp = copy[day][period][roomOne];
        copy[day][period][roomOne] = copy[day][period][roomTwo];
        copy[day][period][roomTwo] = temp;

        return copy;
    }
}
