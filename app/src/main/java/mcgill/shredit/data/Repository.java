package mcgill.shredit.data;

import java.util.List;

import mcgill.shredit.model.*;

public final class Repository implements DataSource {

    private static Repository instance;

    private DataSource dbs;

    private Repository() {
        dbs = new DBService();
    }

    public static Repository getInstance() {
        if (instance == null)
            instance = new Repository();
        return instance;
    }

    @Override
    public List<Equipment> getEquipmentList() {
        return dbs.getEquipmentList();
    }

    @Override
    public List<Exercise> getExerciseList(String muscleGroup, String username, String gymName) {
        return dbs.getExerciseList(muscleGroup, username, gymName);
    }

    @Override
    public List<Gym> getGymList(String username) {
        return dbs.getGymList(username);
    }

    @Override
    public List<Workout> getWorkoutList(String username) {
        return dbs.getWorkoutList(username);
    }

    @Override
    public boolean checkPassword(String username, String password) {
        return dbs.checkPassword(username, password);
    }

    @Override
    public boolean addUser(String username, String password) {
        return dbs.addUser(username, password);
    }

    @Override
    public boolean removeUser(String username) {
        return dbs.removeUser(username);
    }

    @Override
    public boolean addEquipment(Equipment equipment) {
        return dbs.addEquipment(equipment);
    }

    @Override
    public boolean removeEquipment(Equipment equipment) {
        return dbs.removeEquipment(equipment);
    }

    @Override
    public boolean addExercise(Exercise exercise) {
        return dbs.addExercise(exercise);
    }

    @Override
    public boolean removeExercise(Exercise exercise) {
        return dbs.removeExercise(exercise);
    }

    @Override
    public boolean addGym(String username, Gym gym) {
        return dbs.addGym(username, gym);
    }

    @Override
    public boolean removeGym(String username, Gym gym) {
        return dbs.removeGym(username, gym);
    }

    @Override
    public boolean addWorkout(String username, Workout workout) {
        return dbs.addWorkout(username, workout);
    }

    @Override
    public boolean removeWorkout(String username, Workout workout) {
        return dbs.removeWorkout(username, workout);
    }
}
