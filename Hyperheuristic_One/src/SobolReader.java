import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * num invocations: [5, 50]
 * alpha: [0,1]
 * beta: [0,1]
 * delta: [0.1, 2.0]
 * iteration limit: [1000, 20000]
 * threshold value: [0.01, 0.9]
 * threshold adaptor value: [0.01, 0.99]
 * tournament size: [2,5]
 */
public class SobolReader {
    private List<List<Double>> sobolNumbers;
    private String filename = "master_Sobol_numbers.txt";
    private double []minVals = {5, 0, 0, 0.1, 1000, 0.01, 0.01,2};
    private double []maxVals = {50, 1.00001, 1.00001, 2.00001, 20000, 0.999, 0.999,6};
    private int index = 0;

    public SobolReader(){
        sobolNumbers = new ArrayList<List<Double>>();
        readFile();
    }

    private void readFile(){
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line = "";
            while((line = reader.readLine()) != null){
                if(line.equals(""))
                    break;

                int startIndex = 0;
                int endIndex = line.indexOf('\t');
                List<Double> data = new ArrayList<Double>();

                int count = 0;
                while(endIndex >= 0){
                    String elem = line.substring(startIndex,endIndex);
                    double val = Double.valueOf(elem);
                    
                    double scaledVal = minVals[count] + (maxVals[count]-minVals[count]) * val;//min-max normalization
                    
                    data.add(scaledVal);
                    count++;

                    startIndex = endIndex+1;
                    endIndex = line.indexOf('\t', startIndex);

                    if(count >=8)
                        break;
                }
                sobolNumbers.add(data);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public Double[] getParams(){
        if(index >= sobolNumbers.size())
            return null;

        List<Double> params = sobolNumbers.get(index++);
        return params.toArray(new Double[params.size()]);
    }
}
