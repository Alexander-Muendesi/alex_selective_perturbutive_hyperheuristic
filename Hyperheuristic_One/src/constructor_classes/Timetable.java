package constructor_classes;

import constraints.Constraints;
import data_classes.Course;
import data_classes.Curriculum;
import data_classes.DataReader;

public class Timetable {
    private String[][][] timetable;
    private int fitness;
    private int hardConstraintCost;
    private int softConstraintCost;
    private final DataReader reader;
    private final Constraints constraints;

    public Timetable(String[][][] timetable, DataReader reader){
        this.timetable = timetable;
        fitness = Integer.MAX_VALUE;
        hardConstraintCost = Integer.MAX_VALUE;
        softConstraintCost = Integer.MAX_VALUE;
        this.reader = reader;
        this.constraints = new Constraints(reader);
    }

    public int calculateFitness(){
        hardConstraintCost = calculateHardConstraintCost();
        softConstraintCost = calculateSoftConstraintCost();
        fitness = hardConstraintCost + softConstraintCost;
        return fitness;
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

    public int calculateSoftConstraintCost(){
        
    }


    /**
     * Updates timetable with a new timetable
     * @param timetable
     */
    public void setTimetable(String[][][] timetable){
        this.timetable = timetable;
    }

    public String[][][] getTimetable(){
        return this.timetable;
    }
    /**
     * Creates a copy of the current timetable
     * @param timetable
     * @return copy of timetable
     */
    public String[][][] getTimetableCopy(String[][][] timetable){
        String [][][]result = new String[timetable.length][timetable[0].length][timetable[0][0].length];

        for(int i=0; i < timetable.length; i++)
            for(int j=0; j < timetable[0].length; j++)
                for(int k=0; k < timetable[0][0].length; k++)
                    if(timetable[i][j][k] != null)
                        result[i][j][k] = new String(timetable[i][j][k]);
                    else
                        result[i][j][k] = null;

        return result;
    }
}
