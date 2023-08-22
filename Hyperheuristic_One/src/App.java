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

        int []files = {1,3,4,11,13,14,15, 18,19 };
        while(true){
            long seed = System.currentTimeMillis();
            System.out.println("seed: " + 0);
            for(int i=0; i<files.length;i++){
                Random random = new Random(seed);
                DataReader reader = new DataReader(files[i]);
                int numInvocations = 26;

                double alpha = 0.6953194531250001, beta = 0.335940859375, delta = 1.836727890625;//delta was 0.9 b4/0.5 seems to be best for now

                int iterationLimit = 7085;
                
                double thresholdValue = 0.5585859375, thresholdAdaptationFactor = 0.8367421875;
        
                Perturbator p = new Perturbator(random, reader, numInvocations, alpha, beta, delta, iterationLimit, thresholdValue, thresholdAdaptationFactor,3);
                p.execute();
            }
        }

        // SobolReader s = new SobolReader();
        // Double[] params = null;
        // int skip = 0;

        // int best = 0;
        // int currentFitness = 0,bestFitness = Integer.MAX_VALUE;

        // while((params = s.getParams()) != null){
        //     if(skip != 0){
        //         System.out.println(skip + ": ");
    
        //         for (Double val : params) {
        //             System.out.print(val + " ");
        //         }
        //         System.out.println("\n");

                
        //         for(int k=1;k<2;k++){
        //             Random random = new Random(0);
        //             DataReader reader = new DataReader(k);
    
        //             Perturbator p = new Perturbator(random, reader, (int)params[0].intValue(), params[1], params[2], params[3], params[4].intValue(), params[5], params[6], params[7].intValue());
        //             currentFitness += p.execute();
        //         }
        //         if(currentFitness < bestFitness){
        //             bestFitness = currentFitness;
        //             best = skip;
        //         }
        //         System.out.println("\nBest: " + best);
        //         currentFitness = 0;
        //     }
        //     skip++;
        // }
        
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



