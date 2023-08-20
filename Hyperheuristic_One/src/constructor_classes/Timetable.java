package constructor_classes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import constraints.Constraints;
import data_classes.Course;
import data_classes.Curriculum;
import data_classes.DataReader;

public class Timetable {
    private String[] timetable;
    private int fitness;
    private int hardConstraintCost;
    private int softConstraintCost;
    private final DataReader reader;
    private final Constraints constraints;

    public Timetable(String[] timetable, DataReader reader){
        this.timetable = getTimetableCopy(timetable);
        fitness = Integer.MAX_VALUE;
        hardConstraintCost = Integer.MAX_VALUE;
        softConstraintCost = Integer.MAX_VALUE;
        this.reader = reader;
        this.constraints = new Constraints(reader);
    }

    /**
     * 0 is for hard constraints, 1 for softconstraints
     * @return
     */
    public int[] calculateFitness(){
        hardConstraintCost = calculateHardConstraintCost();
        softConstraintCost = calculateSoftConstraintCost();
        // fitness = hardConstraintCost + softConstraintCost;
        int []arr = {hardConstraintCost,softConstraintCost}; 
        return arr;
    }

    public int calculateHardConstraintCost(){
        //lecture constraint is never violated as of current setup with low level heuristics
        //room occupancy is never violated as of current setup with low level heuristics
        
        //check the teacherConstraint
        int teacherConstraintCost = 0;

        for(Course c: reader.courses){
            for(int day=0; day < reader.numDays; day++)
                for(int period=0; period < reader.periodsPerDay; period++)
                    teacherConstraintCost += constraints.teacherConstraintCost(timetable, day, period, c.teacherId);
        }

        //check the conflicts hard constraint
        int conflictsConstraintCost = 0;

        for(Curriculum c: reader.curricula){
            for(int day=0; day < reader.numDays; day++)
                for(int period=0; period < reader.periodsPerDay; period++)
                conflictsConstraintCost += constraints.conflictsConstraintCost(timetable, day, period, c);
        }

        return conflictsConstraintCost + teacherConstraintCost;
    }

    /**
     * This methd calculates the soft constraint costs of the current timetable
     * @return Soft constraint cost
     */
    public int calculateSoftConstraintCost(){
        int cost = 0;
        // ExecutorService executorService = Executors.newFixedThreadPool(4);
        // Future<Integer> future1 = executorService.submit(() -> constraints.roomCapacityConstraintCost(timetable));
        // Future<Integer> future2 = executorService.submit(() -> constraints.minimumWorkingDaysConstraintCost(timetable));
        // Future<Integer> future3 = executorService.submit(() -> constraints.curriculumCompactnessCost(timetable));
        // Future<Integer> future4 = executorService.submit(() -> constraints.roomStabilityConstraintCost(timetable));
        // executorService.shutdown();
        // try{
        //     cost = future1.get() + future2.get() + future3.get() + future4.get();
        // }
        // catch(Exception e){
        //     e.printStackTrace();
        // }
        
        int roomCapacityConstraintCost = constraints.roomCapacityConstraintCost(timetable);
        int minimumWorkingDaysConstraintCost = constraints.minimumWorkingDaysConstraintCost(timetable);
        int curriculumCompactnessCost = constraints.curriculumCompactnessCost(timetable);
        int roomStabilityConstraintCost = constraints.roomStabilityConstraintCost(timetable);

        cost = roomCapacityConstraintCost + minimumWorkingDaysConstraintCost + curriculumCompactnessCost + roomStabilityConstraintCost;
        return cost;
    }


    /**
     * Updates timetable with a new timetable
     * @param timetable
     */
    public void setTimetable(String[] timetable){
        this.timetable = getTimetableCopy(timetable);
    }

    public String[] getTimetable(){
        return this.timetable;
    }
    /**
     * Creates a copy of the current timetable
     * @param timetable
     * @return copy of timetable
     */
    public String[] getTimetableCopy(String[] timetable){
        String []result = new String[timetable.length];

        for(int k=0; k < timetable.length; k++)
            if(timetable[k] != null)
                result[k] = new String(timetable[k]);
            else
                result[k] = null;

        return result;
    }
}
