package perturbator_classes;

import java.util.Random;
import data_classes.DataReader;

//This heuristic swaps 2 rows in the timetable.
public class SwapRow extends Heuristic {
    
    /**
     * 
     * @param n number of invocations
     * @param random
     * @param reader
     * @param alpha
     * @param beta
     * @param delta
     */
    public SwapRow(int n, Random random, DataReader reader, double alpha, double beta, double delta){
        super(n, random, reader, alpha, beta, delta);
    }

    public String[][][] executeHeuristic(String[][][] timetable){
        String[][][] copy = createTimetableCopy(timetable);
        int numDays = reader.numDays;
        int periodsPerDay = reader.periodsPerDay;
        int numRooms = reader.rooms.size();

        int day = random.nextInt(numDays);
        int rowOne = random.nextInt(periodsPerDay);
        int rowTwo = random.nextInt(periodsPerDay);

        //make sure the two rows are not the same
        if(rowOne == rowTwo){
            while(true){
                if(rowOne == rowTwo)
                    rowTwo = random.nextInt(periodsPerDay);
                else
                    break;
            }
        }

        for(int roomIndex = 0; roomIndex < numRooms; roomIndex++){
            String temp = copy[day][rowOne][roomIndex];

            copy[day][rowOne][roomIndex] = copy[day][rowTwo][roomIndex];
            copy[day][rowTwo][roomIndex] = temp;
        }
        
        return copy;
    }
}
