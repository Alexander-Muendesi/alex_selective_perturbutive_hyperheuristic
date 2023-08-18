package perturbator_classes;

import java.util.List;
import java.util.Random;

import constraints.Constraints;
import data_classes.DataReader;

public abstract class Heuristic {
    public int nInvocations;
    protected double[] fitnessHistory;//keeps track of In for f1
    protected double[] timeHistory;//keeps track of Tn for f1
    public double lastApplicationTime;//last time the heuristic was applied
    protected int index;//will keep track of the number of times the heuristic has been called
    protected Random random;
    protected DataReader reader;
    protected Constraints constraints;
    protected double rank;//smaller values are considered better??
    protected int timeIndex;
    protected int fitnessIndex;
    protected int numInvocations;
    protected double alpha, beta, delta;

    /**
     * 
     * @param numInvocations
     * @param random
     * @param reader
     * @param alpha
     * @param beta
     * @param delta
     */
    public Heuristic(int numInvocations, Random random, DataReader reader, double alpha, double beta, double delta){
        this.nInvocations = numInvocations;
        fitnessHistory = new double[numInvocations];
        timeHistory = new double[numInvocations];
        lastApplicationTime = Double.MAX_VALUE;
        index = 0;
        this.random = random;
        this.reader = reader;
        this.constraints = new Constraints(reader);
        this.rank = Integer.MAX_VALUE;
        this.numInvocations = numInvocations;
        this.alpha = alpha; this.beta = beta; this.delta = delta;

        for(int i=0; i< numInvocations;i++){
            fitnessHistory[i] = 0;
            timeHistory[i] = 0;
        }
        timeIndex = 0;
        fitnessIndex = 0;
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

    public void addTime(double time){
        timeHistory[timeIndex++] = time;
        if(timeIndex % this.numInvocations == 0)
            timeIndex = 0;
    }

    public void addFitness(int[] fitness){
        fitnessHistory[fitnessIndex] = fitness[0] + fitness[1];
        if(fitnessIndex % numInvocations == 0)
            fitnessIndex = 0;
    }

    public abstract String[] executeHeuristic(String[] timetable);

    protected String[] createTimetableCopy(String[] timetable){
        String []result = new String[timetable.length];

        for(int k=0; k < timetable.length; k++)
            if(timetable[k] != null)
                result[k] = new String(timetable[k]);
            else
                result[k] = null;

        return result;
    }
}
