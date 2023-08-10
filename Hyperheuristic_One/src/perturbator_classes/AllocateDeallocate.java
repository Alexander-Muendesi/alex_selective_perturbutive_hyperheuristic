package perturbator_classes;

import java.util.Random;

import data_classes.DataReader;

public class AllocateDeallocate extends Heuristic{

    /**
     * 
     * @param n
     * @param random
     * @param reader
     */
    public AllocateDeallocate(int n, Random random, DataReader reader){
        super(n,random,reader);
    }
    
    /**
     * This method unassigns a lecture then reassigns it somewhere different
     * @param timetable
     * @return the modified timetable
     */
    public String[][][] executeHeuristic(String[][][] timetable){
        String [][][]copy = createTimetableCopy(timetable);

        //select a random lecture
        int day = random.nextInt(reader.numDays);
        int period = random.nextInt(reader.periodsPerDay);
        int roomIndex = random.nextInt(reader.rooms.size());

        while(true){//make sure there is a course at the selected timeslot
            if(copy[day][period][roomIndex] != null)
                break;
            day = random.nextInt(reader.numDays);
            period = random.nextInt(reader.periodsPerDay);
            roomIndex = random.nextInt(reader.rooms.size());
        }

        String courseName = copy[day][period][roomIndex];
        copy[day][period][roomIndex] = null;

        boolean foundNewSlot = false;
        //reassign the lecture somewhere it fits
        for(int d=0; d<reader.numDays && !foundNewSlot; d++)
            for(int p=0; p<reader.periodsPerDay && !foundNewSlot;p++)
                for(int r=0;r<reader.rooms.size() && !foundNewSlot;r++)
                    if(copy[d][p][r] == null && !(day==d && period == p && roomIndex == r)){
                        copy[d][p][r] = courseName;
                        foundNewSlot = true;
                    }
        
        if(!foundNewSlot){
            System.out.println("Failed to find a new slot to assign lecture in AllocateDeallocate. This is error");
        }

        return copy;
    }
}
