package data_classes;

import java.util.ArrayList;

public class Constraints extends Data {
    private ArrayList<String> courseCodes;
    private ArrayList<Integer> numPeriodsUnavailable;
    private ArrayList<Integer> periodsUnavailable;

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
        this.numPeriodsUnavailable = new ArrayList<Integer>();
        this.periodsUnavailable = new ArrayList<Integer>();
    }

    /**
     * 
     * @param courseCode
     * @param numPeriodsUnavailable
     * @param periodsUnavailable
     */
    public void addEntry(String courseCode, int numPeriodsUnavailable, int periodsUnavailable){
        this.courseCodes.add(courseCode);
        this.numPeriodsUnavailable.add(numPeriodsUnavailable);
        this.periodsUnavailable.add(periodsUnavailable);
    }

    public String getCourseCode(int index){
        return this.courseCodes.get(index);
    }

    public int getNumPeriodsPerDay(int index){
        return this.numPeriodsUnavailable.get(index);
    }

    public int getPeriodUnavailable(int index){
        return this.periodsUnavailable.get(index);
    }
}
