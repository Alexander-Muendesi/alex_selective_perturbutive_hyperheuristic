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

    public void addFitness(double fitness){
        fitnessHistory[fitnessIndex] = fitness;
        if(fitnessIndex % numInvocations == 0)
            fitnessIndex = 0;
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

    /**
     * This function calculates the rank assigned by the choice function
     * @param heuristics the list of heuristics available
     * @return
     */
    public double calculateRank(List<Heuristic> heuristics){
        //TODO: verify you are interpreting those summations correctly
        double f1 = calculateF1();
        double f2 = calculateF2(heuristics);
        double f3 = delta * ((System.currentTimeMillis() - lastApplicationTime) / 1000);//since we want CPU seconds an not milliseonds

        this.rank = f1 + f2 + f3;
        return rank;
    }

    /**
     * f1 in the choice function
     */
    private double calculateF1(){
        double result = 0;
        for(int i=0; i< numInvocations;i++){
            double divisor = (timeHistory[i] == 0)? 1:timeHistory[i];
            result += (Math.pow(alpha, i - 1)) * (fitnessHistory[i] / divisor);
        }
        return result;
    }

    private double calculateF2(List<Heuristic> heuristics){
        int result = 0;
        for(Heuristic h: heuristics){
            if(!h.equals(this)){
                for(int i=0; i<numInvocations;i++){
                    double divisor = (h.timeHistory[i] - timeHistory[i] == 0) ? 1 : h.timeHistory[i] - timeHistory[i];
                    result += (Math.pow(beta, i-1)) * ((h.fitnessHistory[i]-fitnessHistory[i]) / divisor);
                }
            }
        }
        return result;
    }
}
