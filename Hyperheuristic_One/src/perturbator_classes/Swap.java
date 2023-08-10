package perturbator_classes;

import java.util.Random;

import data_classes.Course;
import data_classes.Curriculum;
import data_classes.DataReader;

public class Swap extends Heuristic{
    /**
     * 
     * @param n num invocations
     * @param random random number generator
     * @param reader data reader
     */
    public Swap(int n, Random random, DataReader reader){
        super(n, random, reader);
    }

    /**
     * This method swaps 2 randomly selected courses
     * @param timetable This should be a copy of the original timetable
     */
    public String[][][] executeHeuristic(String[][][] timetable){
        String [][][]copy = createTimetableCopy(timetable);

        //randomly select 2 different timeslots
        int dayOne = random.nextInt(copy.length);
        int periodOne = random.nextInt(copy[0].length);
        int roomIndexOne = random.nextInt(copy[0][0].length);

        int dayTwo = random.nextInt(copy.length);
        int periodTwo = random.nextInt(copy[0].length);
        int roomIndexTwo = random.nextInt(copy[0][0].length);

        //make sure the 2 timeslots are not the same
        if(dayOne == dayTwo && periodOne == periodTwo && roomIndexOne == roomIndexTwo){
            while(true){
                if(dayOne == dayTwo && periodOne == periodTwo && roomIndexOne == roomIndexTwo){
                    dayTwo = random.nextInt(copy.length);
                    periodTwo = random.nextInt(copy[0].length);
                    roomIndexTwo = random.nextInt(copy[0][0].length);
                }
                else
                    break;
            }
        }

        String courseOne = copy[dayOne][periodOne][roomIndexOne];
        String courseTwo = copy[dayTwo][periodTwo][roomIndexTwo];

        
        //swap the courses at the timeslots
        copy[dayOne][periodOne][roomIndexOne] = courseTwo;
        copy[dayTwo][periodTwo][roomIndexTwo] = courseOne;
            
        return copy;
    }
}
