package perturbator_classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

import data_classes.Course;
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

        int rowOne = random.nextInt(numDays);
        int rowTwo = random.nextInt(numDays);

        //make sure the two rows are not the same
        if(rowOne == rowTwo)
            while(true){
                if(rowOne == rowTwo)
                    rowTwo = random.nextInt(numDays);
                else
                    break;
            }

        List<String> rowOneCourses = new ArrayList<String>();
        List<String> rowTwoCourses = new ArrayList<String>();

        for(int period = 0; period < periodsPerDay; period++){//swap 2 random rows
            List<Integer> temp = new ArrayList<Integer>();
            for(int roomIndex = 0; roomIndex < numRooms; roomIndex++){
                rowOneCourses.add(copy[rowOne][period][roomIndex]);
                rowTwoCourses.add(copy[rowTwo][period][roomIndex]);

                copy[rowOne][period][roomIndex] = copy[rowTwo][period][roomIndex];
                copy[rowTwo][period][roomIndex] = rowOneCourses.get(rowOneCourses.size()-1);
                temp.add(roomIndex);
            }
        }
        return copy;
    }
}
