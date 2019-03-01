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

    public List<Equipment> getEquipmentList() {
        return dbs.getEquipmentList();
    }

    public List<Exercise> getExerciseList(String muscleGroup, int gymID) {
        return dbs.getExerciseList(muscleGroup, gymID);
    }

    public List<Gym> getGymList(String username) {
        return dbs.getGymList(username);
    }

    public List<Workout> getWorkoutList(String username) {
        return dbs.getWorkoutList(username);
    }

    public boolean checkPassword(String username, String password) {
        return dbs.checkPassword(username, password);
    }
}
