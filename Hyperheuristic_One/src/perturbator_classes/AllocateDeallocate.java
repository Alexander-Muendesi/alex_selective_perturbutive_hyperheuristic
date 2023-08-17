package perturbator_classes;

import java.util.Random;

import data_classes.DataReader;

public class AllocateDeallocate extends Heuristic{

    /**
     * 
     * @param n num invocations
     * @param random
     * @param reader
     * @param alpha
     * @param beta
     * @param delta
     */
    public AllocateDeallocate(int n, Random random, DataReader reader, double alpha, double beta, double delta){
        super(n,random,reader, alpha, beta, delta);
    }
    
    /**
     * This method unassigns a lecture then reassigns it somewhere different
     * @param timetable
     * @return the modified timetable
     */
    public String[] executeHeuristic(String[] timetable){
        String []copy = createTimetableCopy(timetable);

        //select a random lecture
        int day = random.nextInt(reader.numDays);
        int period = random.nextInt(reader.periodsPerDay);
        int roomIndex = random.nextInt(reader.rooms.size());

        int targetIndex = day * (reader.periodsPerDay * reader.rooms.size()) + period * reader.rooms.size() + roomIndex;

        while(true){//make sure there is a course at the selected timeslot
            if(copy[targetIndex] != null)
                break;
            day = random.nextInt(reader.numDays);
            period = random.nextInt(reader.periodsPerDay);
            roomIndex = random.nextInt(reader.rooms.size());
            targetIndex = day * (reader.periodsPerDay * reader.rooms.size()) + period * reader.rooms.size() + roomIndex;
        }

        String courseName = copy[targetIndex];
        copy[targetIndex] = null;

        boolean foundNewSlot = false;
        //reassign the lecture somewhere it fits
        int d=0,p=0,r=0;
        for(int i=0; i<timetable.length && !foundNewSlot;i++){
            if(copy[i] == null && !(day==d && period == p && roomIndex == r)){
                copy[i] = courseName;
                foundNewSlot = true;
            }
            r++;
            if(r == reader.rooms.size()){
                r = 0;
                p++;
            }

            if(p == reader.periodsPerDay){
                p = 0;
                d++;
            }
        }
        
        if(!foundNewSlot){
            System.out.println("Failed to find a new slot to assign lecture in AllocateDeallocate. This is error");
        }

        return copy;
    }
}
