import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import constructor_classes.Solutions;
import constructor_classes.Timetable;
import data_classes.Course;
import data_classes.DataReader;
import perturbator_classes.Swap;
import perturbator_classes.AllocateDeallocate;
import perturbator_classes.Perturbator;
import perturbator_classes.AllocateDeallocate;

public class App {
    public static int counter = 0;
    public static void main(String[] args) throws Exception {
        // DataReader r = new DataReader(18);
        // Data[] data = r.getData();

        // for(Data d: data){
        //     d.print();
        // }

        // for(int i=1;i<23;i++){
        //     System.out.println(i);
        //     DataReader r = new DataReader(i);

        //     for(Course c: r.courses)
        //         System.out.println(c);
        // }

        System.out.println("Run Two: " + 23);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        int []files = {1,3,4,11,13,14,15, 18,19 };
        AtomicInteger count = new AtomicInteger(0);
        List<Future<?>> fs = new ArrayList<>();
        
            fs.add(executorService.submit(()->execute1()));
            fs.add(executorService.submit(()->execute3()));
            fs.add(executorService.submit(()->execute4()));
            fs.add(executorService.submit(()->execute11()));
            fs.add(executorService.submit(()->execute13()));
            fs.add(executorService.submit(()->execute14()));
            fs.add(executorService.submit(()->execute15()));
            fs.add(executorService.submit(()->execute18()));
            // fs.add(executorService.submit(()->execute19()));
            counter++;

        //     if(counter % 1 == 0)
        //         try{
        //             for(Future<?> f: fs)
        //                 f.get();
        //             fs.clear();
        //         }
        //         catch(Exception e){
        //             e.printStackTrace();
        //         }
        try{
            for(Future<?> f: fs)
                f.get();
            fs.clear();
            executorService.shutdown();
        }
        catch(Exception e){
            e.printStackTrace();
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

    public static void execute19(){
        Random random = new Random(23);
        DataReader reader = new DataReader(19);
        int numInvocations = 26;
        double alpha = 0.6953194531250001, beta = 0.335940859375, delta = 1.836727890625;//delta was 0.9 b4/0.5 seems to be best for now
        int iterationLimit = 7085;
        double thresholdValue = 0.5585859375, thresholdAdaptationFactor = 0.8367423875;

        Perturbator p = new Perturbator(random, reader, numInvocations, alpha, beta, delta, iterationLimit, thresholdValue, thresholdAdaptationFactor,3);
        p.execute();
    }
    public static void execute18(){
        Random random = new Random(23);
        DataReader reader = new DataReader(18);
        int numInvocations = 26;
        double alpha = 0.6953194531250001, beta = 0.335940859375, delta = 1.836727890625;//delta was 0.9 b4/0.5 seems to be best for now
        int iterationLimit = 7085;
        double thresholdValue = 0.5585859375, thresholdAdaptationFactor = 0.8367423875;

        Perturbator p = new Perturbator(random, reader, numInvocations, alpha, beta, delta, iterationLimit, thresholdValue, thresholdAdaptationFactor,3);
        p.execute();
    }

    public static void execute15(){
        Random random = new Random(23);
        DataReader reader = new DataReader(15);
        int numInvocations = 26;
        double alpha = 0.6953194531250001, beta = 0.335940859375, delta = 1.836727890625;//delta was 0.9 b4/0.5 seems to be best for now
        int iterationLimit = 7085;
        double thresholdValue = 0.5585859375, thresholdAdaptationFactor = 0.8367423875;

        Perturbator p = new Perturbator(random, reader, numInvocations, alpha, beta, delta, iterationLimit, thresholdValue, thresholdAdaptationFactor,3);
        p.execute();
    }
    public static void execute14(){
        Random random = new Random(23);
        DataReader reader = new DataReader(14);
        int numInvocations = 26;
        double alpha = 0.6953194531250001, beta = 0.335940859375, delta = 1.836727890625;//delta was 0.9 b4/0.5 seems to be best for now
        int iterationLimit = 7085;
        double thresholdValue = 0.5585859375, thresholdAdaptationFactor = 0.8367423875;

        Perturbator p = new Perturbator(random, reader, numInvocations, alpha, beta, delta, iterationLimit, thresholdValue, thresholdAdaptationFactor,3);
        p.execute();
    }

    public static void execute13(){
        Random random = new Random(23);
        DataReader reader = new DataReader(13);
        int numInvocations = 26;
        double alpha = 0.6953194531250001, beta = 0.335940859375, delta = 1.836727890625;//delta was 0.9 b4/0.5 seems to be best for now
        int iterationLimit = 7085;
        double thresholdValue = 0.5585859375, thresholdAdaptationFactor = 0.8367423875;

        Perturbator p = new Perturbator(random, reader, numInvocations, alpha, beta, delta, iterationLimit, thresholdValue, thresholdAdaptationFactor,3);
        p.execute();
    }
    public static void execute11(){
        Random random = new Random(23);
        DataReader reader = new DataReader(11);
        int numInvocations = 26;
        double alpha = 0.6953194531250001, beta = 0.335940859375, delta = 1.836727890625;//delta was 0.9 b4/0.5 seems to be best for now
        int iterationLimit = 7085;
        double thresholdValue = 0.5585859375, thresholdAdaptationFactor = 0.8367423875;

        Perturbator p = new Perturbator(random, reader, numInvocations, alpha, beta, delta, iterationLimit, thresholdValue, thresholdAdaptationFactor,3);
        p.execute();
    }

    public static void execute4(){
        Random random = new Random(23);
        DataReader reader = new DataReader(4);
        int numInvocations = 26;
        double alpha = 0.6953194531250001, beta = 0.335940859375, delta = 1.836727890625;//delta was 0.9 b4/0.5 seems to be best for now
        int iterationLimit = 7085;
        double thresholdValue = 0.5585859375, thresholdAdaptationFactor = 0.8367423875;

        Perturbator p = new Perturbator(random, reader, numInvocations, alpha, beta, delta, iterationLimit, thresholdValue, thresholdAdaptationFactor,3);
        p.execute();
    }

    public static void execute1(){
        Random random = new Random(23);
        DataReader reader = new DataReader(1);
        int numInvocations = 26;
        double alpha = 0.6953194531250001, beta = 0.335940859375, delta = 1.836727890625;//delta was 0.9 b4/0.5 seems to be best for now
        int iterationLimit = 7085;
        double thresholdValue = 0.5585859375, thresholdAdaptationFactor = 0.8367423875;

        Perturbator p = new Perturbator(random, reader, numInvocations, alpha, beta, delta, iterationLimit, thresholdValue, thresholdAdaptationFactor,3);
        p.execute();
    }

    public static void execute3(){
        Random random = new Random(23);
        DataReader reader = new DataReader(3);
        int numInvocations = 26;
        double alpha = 0.6953194531250001, beta = 0.335940859375, delta = 1.836727890625;//delta was 0.9 b4/0.5 seems to be best for now
        int iterationLimit = 7085;
        double thresholdValue = 0.5585859375, thresholdAdaptationFactor = 0.8367423875;

        Perturbator p = new Perturbator(random, reader, numInvocations, alpha, beta, delta, iterationLimit, thresholdValue, thresholdAdaptationFactor,3);
        p.execute();
    }
}

/*
 * Range of values to consider for parameters
 * ThresholdAdaptationFactor: [0.95, 0.99]
 * Threshold: [0.01, 0.9]
 * IterationLimit: [2300, 23 000]//was 23 000 initially
 * alpha, beta: [0,1]
 * num invocations: [5,50]//was 23 originally
 * delta: [0.5, 1.0]//suppossedly a good range for exploration and exploitation. for more exploration use 1.0 to 2.0. for more exploitation use 0.1 to 0.5
 */



