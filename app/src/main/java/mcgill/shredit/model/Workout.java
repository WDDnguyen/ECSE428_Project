package mcgill.shredit.model;

import java.util.ArrayList;

public class Workout {
    private String name;
    private int id;
    private ArrayList<Exercise> exercises;

    public Workout(String name, int id, ArrayList<Exercise> exercises){
        this.name = name;
        this.id = id;
        this.exercises = exercises;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }
}
