package perturbator_classes;

import java.util.Random;

import data_classes.DataReader;

public class DayChange extends Heuristic {
    
    /**
     * 
     * @param n
     * @param random
     * @param reader
     * @param alpha
     * @param beta
     * @param delta
     */
    public DayChange(int n, Random random, DataReader reader, double alpha, double beta, double delta){
        super(n, random, reader, alpha, beta, delta);
    }

    /**
     * this method swaps 2 days in the timetable whilst maintaining the structure of the courses
     * @param timetable
     */
    public String[][][] executeHeuristic(String[][][] timetable){
        String [][][]copy = createTimetableCopy(timetable);

        int dayOne = random.nextInt(reader.numDays);
        int dayTwo = random.nextInt(reader.numDays);

        while(true){
            if(dayOne == dayTwo){
                dayOne = random.nextInt(reader.numDays);
            }
            else
                break;
        }

        //swap the elements between the days
        for(int period=0; period < reader.periodsPerDay; period++){
            for(int roomIndex = 0;roomIndex < reader.rooms.size(); roomIndex++){
                String temp = copy[dayOne][period][roomIndex];
                copy[dayOne][period][roomIndex] = copy[dayTwo][period][roomIndex];
                copy[dayTwo][period][roomIndex] = temp;
            }
        }

        return copy;
    }
}
