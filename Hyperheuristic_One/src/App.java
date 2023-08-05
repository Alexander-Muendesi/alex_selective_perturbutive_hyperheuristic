import java.util.Random;

import constructor_classes.Solutions;
import data_classes.Course;
import data_classes.DataReader;

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

        Solutions s = new Solutions(reader, random);
        s.generateSolution();
        s.printTimetable();
    }
}




