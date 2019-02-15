package mcgill.shredit.model;

import java.util.ArrayList;

public class Equipment {
    private ArrayList<String> muscleGroups;
    private String name;
    private int id;

    public Equipment(String name, int id, ArrayList<String> muscleGroups){
        this.name = name;
        this.id = id;
        this.muscleGroups = muscleGroups;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMuscleGroups(ArrayList<String> muscleGroups) {
        this.muscleGroups = muscleGroups;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getMuscleGroups() {
        return muscleGroups;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
