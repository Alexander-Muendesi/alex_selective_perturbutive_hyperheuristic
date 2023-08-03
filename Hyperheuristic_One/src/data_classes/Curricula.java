package data_classes;

import java.util.ArrayList;

public class Curricula extends Data{
    private ArrayList<String> curriculaIds;
    private ArrayList<Integer> numCourses;
    private ArrayList<String[]> courses;

    /**
     * 
     * @param numCourses
     * @param numRooms
     * @param numDays
     * @param periodsPerDay
     * @param numCurricula
     * @param numConstraints
     */
    public Curricula(int numCourses, int numRooms, int numDays, int periodsPerDay, int numCurricula, int numConstraints){
        super(numCourses, numRooms, numDays, periodsPerDay, numCurricula, numConstraints);
        this.curriculaIds = new ArrayList<String>();
        this.numCourses = new ArrayList<Integer>();
        this.courses = new ArrayList<String[]>();
    }

    /**
     * 
     * @param curriculumId
     * @param numCourses
     * @param courses
     */
    public void addEntry(String curriculumId, int numCourses, String[] courses){
        this.curriculaIds.add(curriculumId);
        this.numCourses.add(numCourses);
        this.courses.add(courses);
    }

    public String getCurriculumId(int index){
        return this.curriculaIds.get(index);
    }

    public int getNumCourses(int index){
        return numCourses.get(index);
    }

    public String[] getCourses(int index){
        return this.courses.get(index);
    }

    public void print(){
        for(int i=0; i<curriculaIds.size();i++){
            System.out.print(curriculaIds.get(i) + ' ' + numCourses.get(i) + ' ');

            for(String val: courses.get(i))
                System.out.print(val + ' ');
                
            System.out.println();
        }
    }
}
