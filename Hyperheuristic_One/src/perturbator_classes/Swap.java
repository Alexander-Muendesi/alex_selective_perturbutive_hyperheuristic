package perturbator_classes;

import java.util.Random;
import data_classes.DataReader;

public class Swap extends Heuristic{
    /**
     * 
     * @param n num invocations
     * @param random random number generator
     * @param reader data reader
     * @param alpha
     * @param beta
     * @param delta
     */
    public Swap(int n, Random random, DataReader reader, double alpha, double beta, double delta){
        super(n, random, reader, alpha, beta, delta);
    }

    /**
     * This method swaps 2 randomly selected courses
     * @param timetable This should be a copy of the original timetable
     */
    public String[][][] executeHeuristic(String[][][] timetable){
        String [][][]copy = createTimetableCopy(timetable);

        //randomly select 2 different timeslots
        int dayOne = random.nextInt(reader.numDays);
        int periodOne = random.nextInt(reader.periodsPerDay);
        int roomIndexOne = random.nextInt(reader.rooms.size());

        int dayTwo = random.nextInt(reader.numDays);
        int periodTwo = random.nextInt(reader.periodsPerDay);
        int roomIndexTwo = random.nextInt(reader.rooms.size());

        //make sure the 2 timeslots are not the same
        if(dayOne == dayTwo && periodOne == periodTwo && roomIndexOne == roomIndexTwo){
            while(true){
                if(dayOne == dayTwo && periodOne == periodTwo && roomIndexOne == roomIndexTwo){
                    dayTwo = random.nextInt(reader.numDays);
                    periodTwo = random.nextInt(reader.periodsPerDay);
                    roomIndexTwo = random.nextInt(reader.rooms.size());
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
