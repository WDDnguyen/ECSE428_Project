package mcgill.shredit.model;

import java.util.ArrayList;

public class Exercise {

    private String name;
    private int id;
    private ArrayList<String> muscleGroups;
    private Equipment equipment;
    private String description;

    public Exercise(String name, int id, ArrayList<String> muscleGroups, Equipment equipment, String description){
        this.name = name;
        this.id = id;
        this.muscleGroups = muscleGroups;
        this.equipment = equipment;
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMuscleGroups(ArrayList<String> muscleGroups) {
        this.muscleGroups = muscleGroups;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public ArrayList<String> getMuscleGroups() {
        return muscleGroups;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public String getDescription() {
        return description;
    }
}
