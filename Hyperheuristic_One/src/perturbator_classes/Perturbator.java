package perturbator_classes;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import constructor_classes.Solutions;
import constructor_classes.Timetable;
import data_classes.DataReader;

public class Perturbator {
    private final Random random;
    private final DataReader reader;
    private List<Heuristic> heuristics;
    private final int numInvocations;
    double alpha,beta, delta, thresholdValue, thresholdAdaptationFactor;
    private int iterationLimit;

    /**
     * 
     * @param random
     * @param reader
     * @param numInvocations
     * @param alpha
     * @param beta
     * @param delta
     * @param iterationLimit
     * @param thresholdValue
     * @param thresholdAdaptationFactor
     */
    public Perturbator(Random random, DataReader reader, int numInvocations, double alpha, double beta, double delta,
                        int iterationLimit, double thresholdValue, double thresholdAdaptationFactor){
        this.random = random;
        this.reader = reader;
        this.heuristics = new ArrayList<Heuristic>();
        this.numInvocations = numInvocations;
        this.alpha = alpha; this.beta = beta; this.delta = delta;
        this.iterationLimit = iterationLimit;
        this.thresholdValue = thresholdValue;
        this.thresholdAdaptationFactor = thresholdAdaptationFactor;

        heuristics.add(new Swap(numInvocations, random, reader, alpha,beta,delta));
        heuristics.add(new SwapRow(numInvocations, random, reader, alpha,beta,delta));
        heuristics.add(new AllocateDeallocate(numInvocations, random, reader, alpha,beta,delta));
    }

    /**
     * This method follows Algorithm 7 in the Textbook
     */
    public void execute(){
        //randomly generate several initial solutions and select the one with the best fitness
        Solutions []s = new Solutions[10];
        int best = 0;
        double tempFitness = 0;

        for(int i=0; i<10;i++){
            s[i] = new Solutions(reader,random);
            Timetable temp = new Timetable(s[i].generateSolution(), reader);
            double t = temp.calculateFitness();
            if(i == 0)
                tempFitness = t;
            else if(t < tempFitness){
                tempFitness=t;
                best = i;
            }
        }
        //create an initial solution
        // Solutions solution = new Solutions(reader, random);
        // Timetable timetable = new Timetable(solution.generateSolution(), reader);
        Timetable timetable = new Timetable(s[best].timetable, reader);
        Timetable copyTimetable = new Timetable(timetable.getTimetable(),reader); 
        int numIterations = 0;
        double bestFitness = timetable.calculateFitness();
        List<Double> movingAverage = new ArrayList<Double>();
        double prevAverage = -1;
        int movingAverageIterations = 1;
        double stopCounter = 0;
        double result  = 0;

        int c = 1;
        while(true && numIterations < 100000){//change this stopping condition later. Thinking a 30 period moving average of fitness of timetable
            if(numIterations % 10000 == 0)
                System.out.println(c++ + ": Best fitness: " + bestFitness);
            //heuristic selection
            Heuristic heuristic = null;
            if(numIterations < numInvocations){
                //randomly select one of the heuristics until you have numInvocations. Afterwards you can use history as choice function is intended.
                int index = random.nextInt(heuristics.size());
                heuristic = heuristics.get(index);
            }
            else{//choice function has enough data to work now.
                int counter = 0;
                int smallest = 0;
                double rank = 0;
                for(Heuristic h: heuristics){
                    if(counter == 0){
                        rank = h.calculateRank(heuristics);
                    }
                    else{
                        double temp = h.calculateRank(heuristics);
                        if(temp < rank){
                            rank = temp;
                            smallest = counter;
                        }
                    }
                    counter++;
                }
                heuristic = heuristics.get(smallest);
            }

            //execute chosen heuristic
            copyTimetable.setTimetable(heuristic.executeHeuristic(timetable.getTimetable()));

            if(heuristic.lastApplicationTime == Double.MAX_VALUE){//first time the heuristic is being invoked
                heuristic.addTime(0);//for first time invocation set the time to 0 as our default value
                heuristic.lastApplicationTime = System.currentTimeMillis();
                heuristic.addFitness(copyTimetable.calculateFitness());
            }
            else{
                heuristic.addTime(System.currentTimeMillis() - heuristic.lastApplicationTime);
                heuristic.lastApplicationTime = System.currentTimeMillis();
                heuristic.addFitness(copyTimetable.calculateFitness());
            }

            //move acceptance stuff
            double currentFitness = copyTimetable.calculateFitness();
            if(currentFitness <= bestFitness){//accept the move
                timetable.setTimetable(copyTimetable.getTimetable());
                bestFitness = currentFitness;
            }
            else if(numIterations > iterationLimit && currentFitness < thresholdValue * bestFitness){//accept move based on threshold criteria
                timetable.setTimetable(copyTimetable.getTimetable());
                bestFitness = currentFitness;
            }
            else{//move is rejected
                copyTimetable.setTimetable(timetable.getTimetable());
            }

            if(currentFitness >= bestFitness){//adapt the threshold if there is no improvement in fitness
                thresholdValue *= thresholdAdaptationFactor;
            }

            movingAverage.add(bestFitness);

            //stopping condition stuff
            if(numIterations > numInvocations && movingAverageIterations % 12 == 0){
                double denominator = 12;
                double average = 0;

                for(double val: movingAverage)
                    average += val;
                average = average / denominator;
                // System.out.println("average: " + average + " previous average: " + prevAverage);

                if(prevAverage == -1)
                    prevAverage = average;
                else{
                    if(Math.abs(prevAverage - average) < 0.1){
                        stopCounter++;
                    }
                    else{
                        // System.out.println("difference: " + Math.abs(prevAverage - average));
                        prevAverage = average;
                    }
                }
                movingAverageIterations = 1;
                movingAverage.clear();
            }
            if(numIterations >= numInvocations)
                movingAverageIterations++;
            numIterations++;
            result = bestFitness;
        }
        System.out.println("result: " + result);
    }
}
