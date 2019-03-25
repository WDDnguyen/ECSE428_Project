package mcgill.shredit.data;

import java.util.List;

import mcgill.shredit.model.Equipment;
import mcgill.shredit.model.Exercise;
import mcgill.shredit.model.Gym;
import mcgill.shredit.model.User;
import mcgill.shredit.model.Workout;

public interface DataSource {

    List<Equipment> getEquipmentList();

    List<Exercise> getExerciseList(String muscleGroup, String username, String gymName);

    List<Gym> getGymList(String username);

    List<Workout> getWorkoutList(String username);

    boolean checkPassword(String username,String password);

    boolean addUser(String username, String password);

    boolean removeUser(String username);

    boolean addEquipment(Equipment equipment);

    boolean removeEquipment(Equipment equipment);

    boolean addExercise(Exercise exercise);

    boolean removeExercise(Exercise exercise);

    boolean addGym(String username, Gym gym);

    boolean removeGym(String username, Gym gym);

    boolean addWorkout(String username, Workout workout);

    boolean removeWorkout(String username, Workout workout);
}