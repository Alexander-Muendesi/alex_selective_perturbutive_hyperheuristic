package data_classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class DataReader {
    private int numCourses,  numRooms,  numDays,  periodsPerDay,  numCurricula,  numConstraints;
    private Courses courses;
    private Rooms rooms;
    private Curricula curricula;
    private Constraints constraints;
    private String filename = "Hyperheuristic_One/data/comp";
    private Data[] data;

    /**
     * 
     * @param fileNumber
     */
    public DataReader(int fileNumber){
        if(fileNumber < 10)
            filename += "0"+ String.valueOf(fileNumber) + ".ctt.txt";
        else
            filename += String.valueOf(fileNumber) + ".ctt.txt";

        data = new Data[4];

        readFile();
    }

    public void readFile(){
        Boolean headerData = false;
        Boolean courses = false;
        Boolean rooms = false;
        Boolean curricula = false;
        Boolean constraints = false;

        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line = "";

            while((line = reader.readLine()) != null){
                ArrayList<String> data = new ArrayList<String>();

                if(line.equals(""))//skip blank lines
                    continue;

                int startIndex = 0;
                int endIndex = line.indexOf(' ');

                while(endIndex >= 0){
                    data.add(line.substring(startIndex, endIndex));
                    startIndex = endIndex + 1;
                    endIndex = line.indexOf(' ', startIndex);
                }
                data.add(line.substring(startIndex));

                if(data.get(0).equals("Courses:"))
                    this.numCourses = Integer.parseInt(data.get(1));
                else if(data.get(0).equals("Rooms:"))
                    this.numRooms = Integer.parseInt(data.get(1));
                else if(data.get(0).equals("Days:"))
                    this.numDays = Integer.parseInt(data.get(1));
                else if(data.get(0).equals("Periods_per_day:"))
                    this.periodsPerDay = Integer.parseInt(data.get(1));
                else if(data.get(0).equals("Curricula:"))
                    this.numCurricula = Integer.parseInt(data.get(1));
                else if(data.get(0).equals("Constraints:")){
                    this.numConstraints = Integer.parseInt(data.get(1));
                    headerData = true;
                    this.courses = new Courses(numCourses, numRooms, numDays, periodsPerDay, numCurricula, numConstraints);
                    this.rooms = new Rooms(numCourses, numRooms, numDays, periodsPerDay, numCurricula, numConstraints);
                    this.curricula = new Curricula(numCourses, numRooms, numDays, periodsPerDay, numCurricula, numConstraints);
                    this.constraints = new Constraints(numCourses, numRooms, numDays, periodsPerDay, numCurricula, numConstraints);
                }
                else if(headerData){
                    if(data.get(0).equals("COURSES:")){
                        courses = true;
                    }
                    else if(data.get(0).equals("ROOMS:")){
                        rooms = true;
                        courses = false;
                    }
                    else if(data.get(0).equals("CURRICULA:")){
                        curricula = true;
                        rooms = false;
                    }
                    else if(data.get(0).equals("UNAVAILABILITY_CONSTRAINTS:")){
                        constraints = true;
                        curricula = false;
                    }
                    else if(data.get(0).equals("END.")){
                        constraints = false;
                    }
                    else if(courses)
                        this.courses.addEntry(data.get(0), data.get(1), Integer.parseInt(data.get(2)), Integer.parseInt(data.get(3)), Integer.parseInt(data.get(4)));
                    else if(rooms)
                        this.rooms.addEntry(data.get(0), Integer.parseInt(data.get(1)));
                    else if(curricula){
                            String[] temp = new String[data.size() - 2];
                            int counter = 0;
                            int index = 0;

                            for(String val: data){
                                if(counter > 1)
                                    temp[index++] = data.get(counter);
                                counter++;
                            }
                            this.curricula.addEntry(data.get(0), Integer.parseInt(data.get(1)), temp);
                    }
                    else if(constraints)
                        this.constraints.addEntry(data.get(0), Integer.parseInt(data.get(1)), Integer.parseInt(data.get(2)));

                }
            }

            this.data[0] = this.courses;
            this.data[1] = this.rooms;
            this.data[2] = this.curricula;
            this.data[3] = this.constraints;

            //free I/O
            reader.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * index 0: Courses, index 1: rooms, index 2: curricula, index 3: Constraints
     * @return
     */
    public Data[] getData(){
        return this.data;
    }

}
