package data_classes;

import java.util.ArrayList;

public class Courses extends Data{
    private ArrayList<String> courseId;
    private ArrayList<String> teacherId;
    private ArrayList<Integer> numLectures;
    private ArrayList<Integer> minWorkingDays;
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
        numLectures = new ArrayList<Integer>();
        minWorkingDays = new ArrayList<Integer>();
        numStudentsEnrolled = new ArrayList<Integer>();
    }

    /**
     * This method adds a row from the data set in the courses section into the various data structures
     * @param course
     * @param teacherId
     * @param numLectures
     * @param minWorkingDays
     * @param numStudentsEnrolled
     */
    public void addEntry(String courseId, String teacherId, int numLectures, int minWorkingDays, int numStudentsEnrolled){
        this.courseId.add(courseId);
        this.teacherId.add(teacherId);
        this.numLectures.add(numLectures);
        this.minWorkingDays.add(minWorkingDays);
        this.numStudentsEnrolled.add(numStudentsEnrolled);
    }
    
    public String getCourseId(int index){
        return courseId.get(index);
    }

    public String getTeacherId(int index){
        return teacherId.get(index);
    }

    public int getNumLectures(int index){
        return numLectures.get(index);
    }

    public int getMinWorkingDays(int index){
        return minWorkingDays.get(index);
    }

    public int getNumStudentsEnrolled(int index){
        return numStudentsEnrolled.get(index);
    }

    public void print(){
        for(int i = 0; i < courseId.size(); i++){
            System.out.println(courseId.get(i) + " " + teacherId.get(i) + " " + numLectures.get(i) + " " + minWorkingDays.get(i) + " " + numStudentsEnrolled.get(i));
        }
    }
}
