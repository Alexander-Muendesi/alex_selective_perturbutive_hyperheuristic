package constructor_classes;


public class Timetable {
    private String[][][] timetable;
    private int fitness;
    private int hardConstraintCost;
    private int softConstraintCost;

    public Timetable(String[][][] timetable){
        this.timetable = timetable;
        fitness = Integer.MAX_VALUE;
        hardConstraintCost = Integer.MAX_VALUE;
        softConstraintCost = Integer.MAX_VALUE;
    }
}
