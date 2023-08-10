package perturbator_classes;

import java.util.Random;

import constraints.Constraints;
import data_classes.DataReader;

public abstract class Heuristic {
    public int nInvocations;
    protected double[] fitnessHistory;//keeps track of In for f1
    protected double[] timeHistory;//keeps track of Tn for f1
    protected double lastApplicationTime;//last time the heuristic was applied
    protected int index;//will keep track of the number of times the heuristic has been called
    protected Random random;
    protected DataReader reader;
    protected Constraints constraints;
    /**
     * 
     * @param numInvocations
     */
    public Heuristic(int numInvocations, Random random, DataReader reader){
        this.nInvocations = numInvocations;
        fitnessHistory = new double[numInvocations];
        timeHistory = new double[numInvocations];
        lastApplicationTime = Double.MAX_VALUE;
        index = 0;
        this.random = random;
        this.reader = reader;
        this.constraints = new Constraints(reader);
    }

    public double[] getFitnessHistory(){
        return this.fitnessHistory;
    }

    public double[] getTimeHistory(){
        return this.timeHistory;
    }

    public double getLastApplicationTime(){
        return this.lastApplicationTime;
    }

    public abstract String[][][] executeHeuristic(String[][][] timetable);

    protected String[][][] createTimetableCopy(String[][][] timetable){
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
