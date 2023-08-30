import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import data_classes.DataReader;
import perturbator_classes.Perturbator;

public class App {
    public static int counter = 0;
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String message = "If this first time running program, just type \'y\' \n";
        message += "if program fails to run for some reason enter a number of threads in the range [4,8] with no spaces after number. The more the better\n";
        message += "Program is set to 8 threads initially which could potentially cause memory issues for less powerful machines";

        System.out.println(message);

        String input = scanner.nextLine();
        int numThreads = 8;
        if(input.equals("y") == false){
            numThreads = Integer.parseInt(input);
        }

        System.out.println("seed: " + 2);
        // ExecutorService executorService = Executors.newFixedThreadPoolk(Runtime.getRuntime().availableProcessors());
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        List<Future<?>> fs = new ArrayList<>();
        
            fs.add(executorService.submit(()->execute1()));
            fs.add(executorService.submit(()->execute3()));
            fs.add(executorService.submit(()->execute4()));
            fs.add(executorService.submit(()->execute11()));
            fs.add(executorService.submit(()->execute13()));
            fs.add(executorService.submit(()->execute14()));
            fs.add(executorService.submit(()->execute15()));
            fs.add(executorService.submit(()->execute18()));

        try{
            for(Future<?> f: fs)
                f.get();
            fs.clear();
            executorService.shutdown();
            scanner.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }

    public static void execute19(){
        Random random = new Random(2);
        DataReader reader = new DataReader(19);
        int numInvocations = 26;
        double alpha = 0.6953194531250001, beta = 0.335940859375, delta = 1.836727890625;//delta was 0.9 b4/0.5 seems to be best for now
        int iterationLimit = 7085;
        double thresholdValue = 0.5585859375, thresholdAdaptationFactor = 0.8367421875;

        Perturbator p = new Perturbator(random, reader, numInvocations, alpha, beta, delta, iterationLimit, thresholdValue, thresholdAdaptationFactor,3);
        p.execute();
    }
    public static void execute18(){
        Random random = new Random(2);
        DataReader reader = new DataReader(18);
        int numInvocations = 26;
        double alpha = 0.6953194531250001, beta = 0.335940859375, delta = 1.836727890625;//delta was 0.9 b4/0.5 seems to be best for now
        int iterationLimit = 7085;
        double thresholdValue = 0.5585859375, thresholdAdaptationFactor = 0.8367421875;

        Perturbator p = new Perturbator(random, reader, numInvocations, alpha, beta, delta, iterationLimit, thresholdValue, thresholdAdaptationFactor,3);
        p.execute();
    }

    public static void execute15(){
        Random random = new Random(2);
        DataReader reader = new DataReader(15);
        int numInvocations = 26;
        double alpha = 0.6953194531250001, beta = 0.335940859375, delta = 1.836727890625;//delta was 0.9 b4/0.5 seems to be best for now
        int iterationLimit = 7085;
        double thresholdValue = 0.5585859375, thresholdAdaptationFactor = 0.8367421875;

        Perturbator p = new Perturbator(random, reader, numInvocations, alpha, beta, delta, iterationLimit, thresholdValue, thresholdAdaptationFactor,3);
        p.execute();
    }
    public static void execute14(){
        Random random = new Random(2);
        DataReader reader = new DataReader(14);
        int numInvocations = 26;
        double alpha = 0.6953194531250001, beta = 0.335940859375, delta = 1.836727890625;//delta was 0.9 b4/0.5 seems to be best for now
        int iterationLimit = 7085;
        double thresholdValue = 0.5585859375, thresholdAdaptationFactor = 0.8367421875;

        Perturbator p = new Perturbator(random, reader, numInvocations, alpha, beta, delta, iterationLimit, thresholdValue, thresholdAdaptationFactor,3);
        p.execute();
    }

    public static void execute13(){
        Random random = new Random(2);
        DataReader reader = new DataReader(13);
        int numInvocations = 26;
        double alpha = 0.6953194531250001, beta = 0.335940859375, delta = 1.836727890625;//delta was 0.9 b4/0.5 seems to be best for now
        int iterationLimit = 7085;
        double thresholdValue = 0.5585859375, thresholdAdaptationFactor = 0.8367421875;

        Perturbator p = new Perturbator(random, reader, numInvocations, alpha, beta, delta, iterationLimit, thresholdValue, thresholdAdaptationFactor,3);
        p.execute();
    }
    public static void execute11(){
        Random random = new Random(2);
        DataReader reader = new DataReader(11);
        int numInvocations = 26;
        double alpha = 0.6953194531250001, beta = 0.335940859375, delta = 1.836727890625;//delta was 0.9 b4/0.5 seems to be best for now
        int iterationLimit = 7085;
        double thresholdValue = 0.5585859375, thresholdAdaptationFactor = 0.8367421875;

        Perturbator p = new Perturbator(random, reader, numInvocations, alpha, beta, delta, iterationLimit, thresholdValue, thresholdAdaptationFactor,3);
        p.execute();
    }

    public static void execute4(){
        Random random = new Random(2);
        DataReader reader = new DataReader(4);
        int numInvocations = 26;
        double alpha = 0.6953194531250001, beta = 0.335940859375, delta = 1.836727890625;//delta was 0.9 b4/0.5 seems to be best for now
        int iterationLimit = 7085;
        double thresholdValue = 0.5585859375, thresholdAdaptationFactor = 0.8367421875;

        Perturbator p = new Perturbator(random, reader, numInvocations, alpha, beta, delta, iterationLimit, thresholdValue, thresholdAdaptationFactor,3);
        p.execute();
    }

    public static void execute1(){
        Random random = new Random(2);
        DataReader reader = new DataReader(1);
        int numInvocations = 26;
        double alpha = 0.6953194531250001, beta = 0.335940859375, delta = 1.836727890625;//delta was 0.9 b4/0.5 seems to be best for now
        int iterationLimit = 7085;
        double thresholdValue = 0.5585859375, thresholdAdaptationFactor = 0.8367421875;

        Perturbator p = new Perturbator(random, reader, numInvocations, alpha, beta, delta, iterationLimit, thresholdValue, thresholdAdaptationFactor,3);
        p.execute();
    }

    public static void execute3(){
        Random random = new Random(2);
        DataReader reader = new DataReader(3);
        int numInvocations = 26;
        double alpha = 0.6953194531250001, beta = 0.335940859375, delta = 1.836727890625;//delta was 0.9 b4/0.5 seems to be best for now
        int iterationLimit = 7085;
        double thresholdValue = 0.5585859375, thresholdAdaptationFactor = 0.8367421875;

        Perturbator p = new Perturbator(random, reader, numInvocations, alpha, beta, delta, iterationLimit, thresholdValue, thresholdAdaptationFactor,3);
        p.execute();
    }
}

/*
 * Range of values to consider for parameters
 * ThresholdAdaptationFactor: [0.95, 0.99]
 * Threshold: [0.01, 0.9]
 * IterationLimit: [2300, 23000]//was 23000 initially
 * alpha, beta: [0,1]
 * num invocations: [5,50]//was 23_originally
 * delta: [0.5, 1.0]//suppossedly a good range for exploration and exploitation. for more exploration use 1.0 to 2.0. for more exploitation use 0.1 to 0.5
 */



