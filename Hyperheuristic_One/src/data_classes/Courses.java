package data_classes;

import java.util.ArrayList;

public class Courses extends Data{
    private ArrayList<String> courseId;
    private ArrayList<String> teacherId;
    private ArrayList<Integer> numPeriods;
    private ArrayList<Integer> numLectures;
    private ArrayList<Integer> numStudentsEnrolled;
    
    /**
     * 
     * @param numCourses
     * @param numRooms
     * @param numDays
     * @param periodsPerDay
     * @param numCurricula
     * @param numConstraints
     */
    public Courses(int numCourses, int numRooms, int numDays, int periodsPerDay, int numCurricula, int numConstraints){
        super(numCourses, numRooms, numDays, periodsPerDay, numCurricula, numConstraints);
        courseId = new ArrayList<String>();
        teacherId = new ArrayList<String>();
        numPeriods = new ArrayList<Integer>();
        numLectures = new ArrayList<Integer>();
        numStudentsEnrolled = new ArrayList<Integer>();
    }

    /**
     * This method adds a row from the data set in the courses section into the various data structures
     * @param course
     * @param teacherId
     * @param numPeriods
     * @param numLectures
     * @param numStudentsEnrolled
     */
    public void addEntry(String courseId, String teacherId, int numPeriods, int numLectures, int numStudentsEnrolled){
        this.courseId.add(courseId);
        this.teacherId.add(teacherId);
        this.numPeriods.add(numPeriods);
        this.numLectures.add(numLectures);
        this.numStudentsEnrolled.add(numStudentsEnrolled);
    }
    
    public String getCourseId(int index){
        return courseId.get(index);
    }

    public String getTeacherId(int index){
        return teacherId.get(index);
    }

    public int getNumPeriods(int index){
        return numPeriods.get(index);
    }

    public int getNumLectures(int index){
        return numLectures.get(index);
    }

    public int getNumStudentsEnrolled(int index){
        return numStudentsEnrolled.get(index);
    }
}
