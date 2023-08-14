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
    double alpha,beta, tao;

    public Perturbator(Random random, DataReader reader, int numInvocations, double alpha, double beta, double tao){
        this.random = random;
        this.reader = reader;
        this.heuristics = new ArrayList<Heuristic>();
        this.numInvocations = numInvocations;
        this.alpha = alpha; this.beta = beta; this.tao = tao;

        heuristics.add(new Swap(numInvocations, random, reader, alpha,beta,tao));
        heuristics.add(new SwapRow(numInvocations, random, reader, alpha,beta,tao));
        heuristics.add(new AllocateDeallocate(numInvocations, random, reader, alpha,beta,tao));
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

        while(true){//change this stopping condition later
            //heuristic selection
            Heuristic heuristic = null;
            if(numIterations < numInvocations){
                //randomly select one of the heuristics until you have numInvocations. Afterwards you can use history as choice function is intended.
                int index = random.nextInt(heuristics.size());
                heuristic = heuristics.get(index);
            }
            else{//choice function has enough data to work now.

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

            numIterations++;
        }
    }
}
