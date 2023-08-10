package constraints;

import data_classes.Constraint;
import data_classes.Course;
import data_classes.Curriculum;
import data_classes.DataReader;

public class Constraints {
    private final DataReader reader;

    public Constraints(DataReader reader){
        this.reader = reader;
    }

    //hard constraints start here

    /**
     * The required number of lectures must be scheduled in the timetable
     * @param timetable
     * @param course
     * @return true=satisfied. false=not satisfied
     */
    public boolean lecturesConstraint(String[][][] timetable, Course course){
        boolean stop = false;
        int numAssignedLectures = 0;

        for(int day = 0; day < reader.numDays && !stop; day++){
            for(int period = 0; period < reader.periodsPerDay && !stop; period++){
                for(int roomIndex = 0; roomIndex < reader.rooms.size() && !stop; roomIndex++){
                    if(numAssignedLectures < course.numLectures){
                        if(course.courseId.equals(timetable[day][period][roomIndex]))
                            numAssignedLectures++;
                    }
                    else{
                        stop = true;
                    }
                }
            }
        }

        return stop;
    }

    /**
     * Each room must only be scheduled once in a period.
     * @param timetable
     * @param day
     * @param period
     * @param roomIndex
     * @return true=available, false = not available
     */
    public  boolean roomOccupancyConstraint(String[][][] timetable,int day, int period, int roomIndex){
        return timetable[day][period][roomIndex] == null;//check if the room is available at the current period in the current day
    }

    /**
     * Each teacher must not be scheduled more than once in a period
     * @param timetable
     * @param day
     * @param period
     * @param teacherId
     * @return true=available, false= not available
     */
    public boolean teacherConstraint(String[][][] timetable, int day, int period, String teacherId){
        for(int roomIndex=0;roomIndex < timetable[day][period].length; roomIndex++){
            String courseId = timetable[day][period][roomIndex];

            if(courseId != null){
                Course course = searchForCourse(courseId);
                if(course == null){
                    System.out.println("There is null even though there shouldn't be in teacher Constraint method");
                    return false;
                }
                else{
                    if(teacherId.equals(course.teacherId))
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * This method calculates the cost of teacher constraint in the timetable
     * @param timetable
     * @param day
     * @param period
     * @param teacherId
     * @return
     */
    public int teacherConstraintCost(String[][][] timetable, int day, int period, String teacherId){
        int cost = -1;
        for(int roomIndex=0;roomIndex < timetable[day][period].length;roomIndex++){
            String courseId =  timetable[day][period][roomIndex];

            if(courseId != null){
                Course course = searchForCourse(courseId);
                if(course == null){
                    System.out.println("Null even though there should not be in teacherConstraintCost");
                    return cost;
                }
                else{
                    if(teacherId.equals(course.teacherId)){
                        if(cost == -1)//ignore first instance of the teacherId
                            cost++;
                        else//one violation for every other occurrence of the teacher id
                            cost++;
                    }
                }
            }
        }
        return cost;
    }

    /**
     * Conflicts hard constraint: Lectures for courses in a curriculum must be scheduled in different periods
     * @param timetable
     * @param day
     * @param period
     * @param curriculum
     * @return true=available, false = not available
     */
    public boolean conflictsConstraint(String[][][] timetable, int day, int period, Curriculum curriculum){
        for(int roomIndex=0;roomIndex < timetable[day][period].length; roomIndex++){
            String courseId = timetable[day][period][roomIndex];
            
            if(courseId != null){
                // Course course = searchForCourse(courseId);
                // if(course == null){
                //     System.out.println("There is a null even though there shouldn't be in conflictsConstraint");
                //     return false;
                // }
                // else if(curriculum.courses.contains(course.courseId))
                //     return false;//curriculum already has a course assigned in this period
                if(curriculum.courses.contains(courseId))
                    return false;
            }
        }

        return true;//curriculum does not have a course assigned in this period
    }

    /**
     * This method calculates the cost of this hard constraint being violated.
     * @param timetable
     * @param day
     * @param period
     * @param curriculum
     * @return
     */
    public int conflictsConstraintCost(String[][][] timetable, int day, int period, Curriculum curriculum){
        int cost = -1;

        for(int roomIndex = 0; roomIndex < timetable[day][period].length;roomIndex++){
            String courseId = timetable[day][period][roomIndex];

            if(courseId != null){
                if(curriculum.courses.contains(courseId)){
                    cost++;
                }
            }
        }

        return cost;
    }

    /**
     * This method returns the number of lectures currently assigned to a course
     * @param timetable
     * @param course
     * @return the number of assigned lectures currently for the course
     */
    public int getAssignedLecturesCount(String[][][] timetable,Course course){
        int lectureCount = 0;

        for(int day = 0; day < reader.numDays; day++){
            for(int period = 0; period < reader.periodsPerDay; period++)
                for(int roomIndex = 0; roomIndex < reader.rooms.size(); roomIndex++){
                    if(timetable[day][period][roomIndex] != null  && course.courseId.equals(timetable[day][period][roomIndex]))
                        lectureCount++;
                }
        }

        return lectureCount;
    }

    /**
     * This method checks if the unavailability constraints in the file are satisfied.
     * @param courseId
     * @param day
     * @param dayPeriod
     * @return true=available. false = not available
     */
    public boolean unavailabilityConstraint(String courseId, int day, int dayPeriod){
        for(Constraint constraint: reader.constraints){
            if(constraint.courseId.equals(courseId) && constraint.day == day && constraint.dayPeriod == dayPeriod)
                return false;//course is not available for this period
        }

        return true;//course is available for the period
    }

    
    //soft constraints start here

    //helper methods

    public Course searchForCourse(String courseId){
        for(Course course: reader.courses){
            if(course.courseId.equals(courseId))
                return course;
        }

        return null;
    }

    public Curriculum searchFoCurriculumByCourseId(String courseId){
        for(Curriculum c: reader.curricula){
            for(String val: c.courses)
                if(val.equals(courseId))
                    return c;
        }

        return null;
    }
}
