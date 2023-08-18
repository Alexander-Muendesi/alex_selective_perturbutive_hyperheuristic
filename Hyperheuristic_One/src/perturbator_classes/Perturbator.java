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
    private int tournamentSize = 3;

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
        heuristics.add(new DayChange(iterationLimit, random, reader, alpha, beta, delta));
        heuristics.add(new RoomChange(iterationLimit, random, reader, alpha, beta, delta));
        heuristics.add(new PeriodChange(iterationLimit, random, reader, alpha, beta, delta));
    }

    /**
     * This method follows Algorithm 7 in the Textbook
     */
    public void execute(){
        //create an initial solution
        Solutions solution = new Solutions(reader, random);
        Timetable timetable = new Timetable(solution.generateSolution(), reader);
        Timetable copyTimetable = new Timetable(timetable.getTimetable(),reader); 
        int numIterations = 0;
        int[] bestFitness = timetable.calculateFitness();
        List<Double> movingAverage = new ArrayList<Double>();
        double prevAverage = -1;
        int movingAverageIterations = 1;
        double stopCounter = 0;
        int[] result = {};

        int c = 1;
        while(numIterations <= 100000){//change this stopping condition later. Thinking a 30 period moving average of fitness of timetable
            if(numIterations % 10000 == 0){
                System.out.println(c++ + ": Best fitness: " + bestFitness[0] + " : " + bestFitness[1] );
            }
            //heuristic selection and execution of heuristic
            copyTimetable = tournamentSelection(timetable.getTimetable(), reader);

            //move acceptance stuff
            int[] currentFitness = copyTimetable.calculateFitness();
            
            if(currentFitness[0] <= bestFitness[0] && currentFitness[1] <= bestFitness[1]){
                timetable.setTimetable(copyTimetable.getTimetable());
                bestFitness = currentFitness;
            }
            //new code is below
            else if(numIterations > iterationLimit && currentFitness[0]+currentFitness[1] < thresholdValue * bestFitness[0]+bestFitness[1]){//accept move based on threshold criteria
                timetable.setTimetable(copyTimetable.getTimetable());
                bestFitness = currentFitness;
            }

            if(currentFitness[0]+currentFitness[1] > bestFitness[0]+bestFitness[1]){//adapt the threshold if there is no improvement in fitness
                thresholdValue *= thresholdAdaptationFactor;
            }

            numIterations++;
            result = bestFitness;
        }
        System.out.println("result: " + result[0] + " " + result[1]);
    }

    public Timetable tournamentSelection(String[] timetable, DataReader reader){
        List<Heuristic> population = new ArrayList<Heuristic>();

        while(population.size() < tournamentSize){
            Heuristic temp = heuristics.get(random.nextInt(heuristics.size()));
            if(!population.contains(temp)) 
                population.add(temp);
        }

        Timetable[] timetables = new Timetable[population.size()];
        int counter = 0;
        int bestFitness = Integer.MAX_VALUE;
        int[] tempFitness = {};
        int bestIndex = 0;
        for(Timetable t: timetables){
            t = new Timetable(timetable, reader);
            timetables[counter] = t;
            t.setTimetable(population.get(counter).executeHeuristic(timetable));
            tempFitness = t.calculateFitness();
            if(tempFitness[0] + tempFitness[1] < bestFitness){
                bestIndex = counter;
                bestFitness = tempFitness[0]+tempFitness[1];
            }
            counter++;
        }
        return timetables[bestIndex];
    }
}
