import data_classes.Data;
import data_classes.DataReader;

public class App {
    public static void main(String[] args) throws Exception {
        // DataReader r = new DataReader(18);
        // Data[] data = r.getData();

        // for(Data d: data){
        //     d.print();
        // }

        for(int i=1;i<22;i++){
            System.out.println(i);
            DataReader r = new DataReader(i);
        }
    }
}




