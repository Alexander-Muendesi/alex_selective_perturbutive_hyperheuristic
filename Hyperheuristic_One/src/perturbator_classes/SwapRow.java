package perturbator_classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import data_classes.DataReader;

//This heuristic swaps 2 rows in the timetable.
public class SwapRow extends Heuristic {
    
    /**
     * 
     * @param n number of invocations
     * @param random
     * @param reader
     */
    public SwapRow(int n, Random random, DataReader reader){
        super(n, random, reader);
    }

    public String[][][] executeHeuristic(String[][][] timetable){
        String[][][] copy = createTimetableCopy(timetable);
        int numDays = copy.length;
        int periodsPerDay = copy[0].length;
        int numRooms = copy[0][0].length;

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
