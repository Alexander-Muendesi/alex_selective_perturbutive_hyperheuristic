import java.util.Random;

import constructor_classes.Solutions;
import constructor_classes.Timetable;
import data_classes.Course;
import data_classes.DataReader;
import perturbator_classes.Swap;
import perturbator_classes.AllocateDeallocate;
import perturbator_classes.Perturbator;
import perturbator_classes.AllocateDeallocate;

public class App {
    public static void main(String[] args) throws Exception {
        // DataReader r = new DataReader(18);
        // Data[] data = r.getData();

        // for(Data d: data){
        //     d.print();
        // }

        // for(int i=1;i<22;i++){
        //     System.out.println(i);
        //     DataReader r = new DataReader(i);

        //     for(Course c: r.courses)
        //         System.out.println(c);
        // }

        Random random = new Random(0);
        DataReader reader = new DataReader(1);
        int numInvocations = 25;
        double alpha = 0.1, beta = 0.9, delta = 0.5;//delta was 0.9 b4/0.5 seems to be best for now
        int iterationLimit = 20000;
        double thresholdValue = 0.01, thresholdAdaptationFactor = 0.9;

        Perturbator p = new Perturbator(random, reader, numInvocations, alpha, beta, delta, iterationLimit, thresholdValue, thresholdAdaptationFactor);
        p.execute();
        
    }
}

/*
 * Range of values to consider for parameters
 * ThresholdAdaptationFactor: [0.95, 0.99]
 * Threshold: [0.01, 0.9]
 * IterationLimit: [1000, 20 000]//was 10 000 initially
 * alpha, beta: [0,1]
 * num invocations: [5,50]//was 20 originally
 * delta: [0.5, 1.0]//suppossedly a good range for exploration and exploitation. for more exploration use 1.0 to 2.0. for more exploitation use 0.1 to 0.5
 */



