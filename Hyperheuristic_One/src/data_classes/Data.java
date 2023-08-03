package data_classes;


public abstract class Data {
    protected int numCourses, numRooms, numDays, periodsPerDay, numCurricula, numConstraints;

    /**
     * 
     * @param numCourses
     * @param numRooms
     * @param numDays
     * @param periodsPerDay
     * @param numCurricula
     * @param numConstraints
     */
    public Data(int numCourses, int numRooms, int numDays, int periodsPerDay, int numCurricula, int numConstraints){
        this.numCourses = numCourses;
        this.numRooms = numRooms;
        this.numDays = numDays;
        this.periodsPerDay = periodsPerDay;
        this.numCurricula = numCurricula;
        this.numConstraints = numConstraints;
    }

    public int getNumCourses(){
        return this.numCourses;
    }

    public int getNumRooms(){
        return this.numRooms;
    }

    public int getNumDays(){
        return this.numDays;
    }

    public int getNumPeriodsPerDay(){
        return this.periodsPerDay;
    }

    public int getNumCurricula(){
        return this.numCurricula;
    }

    public int getNumConstraints(){
        return this.numConstraints;
    }

    public abstract void print();
}
