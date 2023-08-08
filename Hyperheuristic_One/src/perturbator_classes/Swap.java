package perturbator_classes;

import java.util.Random;

import data_classes.Course;
import data_classes.Curriculum;
import data_classes.DataReader;

public class Swap extends Heuristic{
    /**
     * num invocations
     * @param n
     */
    public Swap(int n, Random random, DataReader reader){
        super(n, random, reader);
    }

    /**
     * This method swaps 2 randomly selected courses
     * @param timetable This should be a copy of the original timetable
     */
    public String[][][] executeHeuristic(String[][][] timetable){
        boolean stop = false;
        while(!stop){//make sure the swap results in all hard constraints being satisfied.
            String [][][]copy = createTimetableCopy(timetable);

            //randomly select 2 different timeslots
            int dayOne = random.nextInt(copy.length);
            int periodOne = random.nextInt(copy[0].length);
            int roomIndexOne = random.nextInt(copy[0][0].length);

            int dayTwo = random.nextInt(copy.length);
            int periodTwo = random.nextInt(copy[0].length);
            int roomIndexTwo = random.nextInt(copy[0][0].length);

            String courseOne = copy[dayOne][periodOne][roomIndexOne];
            String courseTwo = copy[dayTwo][periodTwo][roomIndexTwo];

            //swap the courses at the timeslots
            copy[dayOne][periodOne][roomIndexOne] = courseTwo;
            copy[dayTwo][periodTwo][roomIndexTwo] = courseOne;

            //check whether all the hard constraints are met
            String teacherIdOne = null;
            Curriculum curriculumOne = null;
            Course courseOnee = null;

            String teacherIdTwo = null;
            Curriculum curriculumTwo = null;
            Course courseTwoo = null;
            
            for(Course c: reader.courses)
                if(c.courseId.equals(courseOne)){
                    teacherIdOne = c.teacherId;
                    courseOnee = c;
                }
                else if(c.courseId.equals(courseTwo)){
                    teacherIdTwo = c.teacherId;
                    courseTwoo = c;
                }
                else if(courseOnee != null && courseTwoo != null)
                    break;

            for(Curriculum c: reader.curricula){
                for(String cc: c.courses)
                    if(cc.equals(courseOnee.courseId)){
                        curriculumOne = c;
                    }
                    else if(cc.equals(courseTwoo.courseId)){
                        curriculumTwo = c;
                    }
                    else if(curriculumOne != null && curriculumTwo != null)
                        break;
                if(curriculumOne != null && curriculumTwo != null)
                    break;
            }

            //since we are swapping two courses it means the lecturesConstraints, RoomOccupancyConstraint will not be affected and hence not checked for here.
            if(constraints.teacherConstraint(copy, dayOne, periodOne, teacherIdOne) && constraints.teacherConstraint(copy, dayTwo, periodTwo, teacherIdTwo) &&
                constraints.conflictsConstraint(copy, dayOne, periodOne, curriculumOne) && constraints.conflictsConstraint(copy, dayTwo, periodTwo, curriculumTwo) &&
                constraints.unavailabilityConstraint(courseOne, dayOne, periodOne) && constraints.unavailabilityConstraint(courseTwo, dayTwo, periodTwo)){
                    stop = true;
                    timetable = copy;
            }
        }

        return timetable;
        
    }

    private String[][][] createTimetableCopy(String[][][] timetable){
        String [][][]result = new String[timetable.length][timetable[0].length][timetable[0][0].length];

        for(int i=0; i < timetable.length; i++)
            for(int j=0; j < timetable[0].length; j++)
                for(int k=0; k < timetable[0][0].length; k++)
                    result[i][j][k] = new String(timetable[i][j][k]);

        return result;
    }
}
