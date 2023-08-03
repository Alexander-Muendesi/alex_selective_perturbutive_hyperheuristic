package data_classes;

import java.util.ArrayList;

public class Constraints extends Data {
    private ArrayList<String> courseCodes;
    private ArrayList<Integer> day;
    private ArrayList<Integer> dayPeriod;

    /**
     * 
     * @param numCourses
     * @param numRooms
     * @param numDays
     * @param periodsPerDay
     * @param numCurricula
     * @param numConstraints
     */
    public Constraints(int numCourses, int numRooms, int numDays, int periodsPerDay, int numCurricula, int numConstraints){
        super(numCourses, numRooms, numDays, periodsPerDay, numCurricula, numConstraints);
        this.courseCodes = new ArrayList<String>();
        this.day = new ArrayList<Integer>();
        this.dayPeriod = new ArrayList<Integer>();
    }

    /**
     * 
     * @param courseCode
     * @param day
     * @param dayPeriod
     */
    public void addEntry(String courseCode, int day, int dayPeriod){
        this.courseCodes.add(courseCode);
        this.day.add(day);
        this.dayPeriod.add(dayPeriod);
    }

    public String getCourseCode(int index){
        return this.courseCodes.get(index);
    }

    public int getDay(int index){
        return this.day.get(index);
    }

    public int getDayPeriod(int index){
        return this.dayPeriod.get(index);
    }

    public void print(){
        for(int i=0;i<courseCodes.size();i++){
            System.out.println(courseCodes.get(i) + " " + day.get(i) + " " + dayPeriod.get(i));
        }
    }
}
