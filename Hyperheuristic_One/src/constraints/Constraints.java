package constraints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        return (cost == -1) ? 0 : cost;
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

        return (cost == -1) ? 0 : cost;
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

    /**
     * Each student above the room capacity is 1 penalty;
     * @param timetable
     * @return 
     */
    public int roomCapacityConstraintCost(String[][][] timetable){
        int roomCost = 0;

        for(int day=0; day < reader.numDays;  day++)
            for(int period = 0; period < reader.periodsPerDay; period++)
                for(int roomIndex=0;roomIndex<reader.rooms.size();roomIndex++){
                    if(timetable[day][period][roomIndex] != null){
                        int numStudentsEnrolled = reader.coursesMap.get(timetable[day][period][roomIndex]).numStudentsEnrolled;
                        int roomSize = reader.rooms.get(roomIndex).capacity;
                        int difference = roomSize - numStudentsEnrolled;

                        if(difference < 0){
                            roomCost += -1 * difference;
                        }
                    }
                }
        return roomCost;
    }

    /**
     * The lectures of each course must be spread into the given minimum number of days.
     * Each day below the minimum counts as 5 points of penalty
     * @param timetable
     * @return
     */
    public int minimumWorkingDaysConstraintCost(String[][][] timetable){
        int finalCost = 0;
        for(Course course: reader.courses){
            int minWorkingDays = course.minWorkingDays;
            int currWorkingDays = 0;
            int numLecturesFound = 0;//to stop the looping process once all lectures found
            boolean stop = false;

            for(int day=0; day< reader.numDays && !stop;day++){
                for(int period=0;period< reader.periodsPerDay && !stop;period++){
                    for(int roomIndex = 0; roomIndex < reader.rooms.size() && !stop; roomIndex++){
                        if(timetable[day][period][roomIndex] != null && timetable[day][period][roomIndex].equals(course.courseId)){
                            numLecturesFound++;
                            stop = (numLecturesFound >= course.numLectures)? true : false;
                        }
                    }
                }
                currWorkingDays = (stop) ? currWorkingDays+1 : currWorkingDays;
            }

            int result = currWorkingDays - minWorkingDays;
            if(result < 0){
                result = -1 * result * 5;
            }

            finalCost += (result < 0) ? -1*result*5 : 0;

        }
        return finalCost;
    }

    /**
     * Each isolated lecture in a curriculum counts as 2 points
     * @param timetable
     * @return
     */
    public int curriculumCompactnessCost(String[][][] timetable){
        int finalCost = 0;
        int isolatedLectures = 0;
        for(Curriculum c: reader.curricula){
            for(String course: c.courses){
                int lastPeriod = -1;
                int lastDay = -1;
                boolean foundCourse = false;
                //iterate through the timetable to find adjacent lectures
                for(int day=0; day < reader.numDays; day++){
                    for(int period=0; period < reader.periodsPerDay; period++){
                        for(int roomIndex = 0; roomIndex < reader.rooms.size(); roomIndex++){
                            if(timetable[day][period][roomIndex] != null && course.equals(timetable[day][period][roomIndex])){
                                if(!foundCourse){//found unpaired course
                                    lastDay = day;
                                    lastPeriod = period;
                                    foundCourse = true;
                                }
                                else if(foundCourse && lastDay == day && lastPeriod+1 == period){
                                    foundCourse = false;
                                }
                                else if(foundCourse){
                                    isolatedLectures++;
                                    lastDay = day;
                                    lastPeriod = period;
                                }
                            }
                        }
                    }
                    if(foundCourse){
                        isolatedLectures++;
                        foundCourse = false;
                    }
                }

            }
        }

        finalCost = isolatedLectures * 2;
        return finalCost;
        
    }

    /**
     * Each distinct room used for lectures of a course but the first counts as 1 point of penalty
     * @param timetable
     * @return
     */
    public int roomStabilityConstraintCost(String[][][] timetable){
        Map<String,List<Integer>> rooms = new HashMap<String, List<Integer>>();
        int finalCost = 0;

        for(Course course: reader.courses){
            rooms.put(course.courseId, new ArrayList<Integer>());
        }

        for(int day=0; day < reader.numDays; day++){
            for(int period = 0; period < reader.periodsPerDay; period++){
                for(int roomIndex = 0; roomIndex < reader.rooms.size(); roomIndex++){
                    if(timetable[day][period][roomIndex] != null){
                        rooms.get(timetable[day][period][roomIndex]).add(roomIndex);
                    }
                }
            }
        }

        for(Map.Entry<String,List<Integer>> val : rooms.entrySet()){
            List<Integer> value = val.getValue().stream().distinct().collect(Collectors.toList());
            String key = val.getKey();
            rooms.put(key, value);

            int cost = value.size()-1;
            if(cost > 0)
                finalCost += cost;
        }
        return finalCost;
    }
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
