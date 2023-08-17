package perturbator_classes;

import java.util.Random;

import data_classes.DataReader;

public class PeriodChange extends Heuristic{
    /**
     * 
     * @param n
     * @param random
     * @param reader
     * @param alpha
     * @param beta
     * @param delta
     */
    public PeriodChange(int n, Random random, DataReader reader, double alpha, double beta, double delta){
        super(n, random, reader, alpha, beta, delta);
    }
    
    public String[] executeHeuristic(String[] timetable){
        String[] copy = createTimetableCopy(timetable);

        int day = random.nextInt(reader.numDays);
        int periodOne = random.nextInt(reader.periodsPerDay);
        int periodTwo = random.nextInt(reader.periodsPerDay);
        int roomIndex = random.nextInt(reader.rooms.size());

        while(periodOne == periodTwo){
            periodOne = random.nextInt(reader.periodsPerDay);
        }

        int targetIndexOne = day * (reader.periodsPerDay * reader.rooms.size()) + periodOne * reader.rooms.size() + roomIndex;
        int targetIndexTwo = day * (reader.periodsPerDay * reader.rooms.size()) + periodTwo * reader.rooms.size() + roomIndex;

        String temp = copy[targetIndexOne];
        copy[targetIndexOne] = copy[targetIndexTwo];
        copy[targetIndexTwo] = temp;

        return copy;
    }
}
