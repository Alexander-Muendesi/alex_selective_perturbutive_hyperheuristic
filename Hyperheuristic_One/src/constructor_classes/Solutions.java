package constructor_classes;

import java.util.List;
import java.util.Random;

import constraints.Constraints;
import data_classes.Course;
import data_classes.DataReader;

public class Solutions {
    private final DataReader reader;
    public String[][][] timetable;//numDays x PeriodsPerDay x numRooms
    private Random random;
    private final Constraints constraints;

    /**
     * 
     * @param random
     * @param reader
     */
    public Solutions(DataReader reader, Random random){
        this.reader = reader;
        this.random = random;
        this.constraints = new Constraints(reader);
        timetable = new String[reader.numDays][reader.periodsPerDay][reader.rooms.size()];
        initializeTimetable();
    }

    public String[][][] generateSolution(){
        List<Course> courses = fisherYatesShuffle(reader.courses);

        //loop through courses and assign lectures to the timetable;
        int roomIndex = 0,day = 0,period = 0;

        int counter = 1;
        for(Course course: courses){
            int lecturesAssigned = 0;

            while(lecturesAssigned < course.numLectures){//this condition ensures the lecture allocations hard constraint is satisfied.
                if(timetable[day][period][roomIndex] == null){//check if current slot is available
                    //check if all hard constraints are satisfied
                    boolean allConstraintsSatisfied = true;

                    allConstraintsSatisfied = constraints.roomOccupancyConstraint(timetable, day, period, roomIndex);
                    // System.out.println("1: " + allConstraintsSatisfied);
                    allConstraintsSatisfied = constraints.teacherConstraint(timetable,day,period,course.teacherId);
                    // System.out.println("2: " + allConstraintsSatisfied);
                    allConstraintsSatisfied = constraints.conflictsConstraint(timetable, day, period, constraints.searchFoCurriculumByCourseId(course.courseId));
                    // System.out.println("3: " + allConstraintsSatisfied);
                    allConstraintsSatisfied = constraints.unavailabilityConstraint(course.courseId, day, period);
                    // System.out.println("4: " + allConstraintsSatisfied);

                    if(allConstraintsSatisfied){
                        timetable[day][period][roomIndex] = course.courseId;
                        lecturesAssigned++;
                        // System.out.println("Lectures Assigned: " + lecturesAssigned);
                    }
                }

                period++;//move to the next period
                if(period >= reader.periodsPerDay){
                    period = 0;
                    day++;
                }
    
                if(day >= reader.numDays){
                    day = 0;
                    roomIndex++;

                    if(roomIndex >= reader.rooms.size())
                        roomIndex = 0;
                }
            }
            // System.out.println("Counter: " + counter++);
        }

        return timetable;
    }

    private void initializeTimetable(){
        for(String[][] dim1: timetable)
            for(String[] dim2: dim1)
                for(String val: dim2)
                    val = null;
    }

    /**
     * This methods performs a shuffle on the courses list so that the initial solution is diverse
     * @param courses
     * @return shuffled list of courses
     */
    private List<Course> fisherYatesShuffle(List<Course> courses){
        for(int i=courses.size() - 1;i > 0; i--){
            int j= random.nextInt(i+1);

            //swap the elements at the i an j indices
            Course temp = courses.get(i);
            courses.set(i, courses.get(j));
            courses.set(j,temp);
        }

        return courses;
    }

    public void printTimetable(){
        for(int day=0; day < reader.numDays; day++){
            for(int period = 0; period < reader.periodsPerDay; period++){
                for(int roomIndex = 0; roomIndex < reader.rooms.size(); roomIndex++){
                    String courseId = timetable[day][period][roomIndex];
                    System.out.print(courseId != null ? courseId : " - ");
                    System.out.print("\t");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
