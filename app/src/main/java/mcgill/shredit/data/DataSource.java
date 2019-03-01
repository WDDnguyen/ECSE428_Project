package mcgill.shredit.data;

import java.util.List;

import mcgill.shredit.model.Equipment;
import mcgill.shredit.model.Exercise;
import mcgill.shredit.model.Gym;
import mcgill.shredit.model.Workout;

public interface DataSource {

    List<Equipment> getEquipmentList();

    List<Exercise> getExerciseList(String muscleGroup, int gymID);

    List<Gym> getGymList(String username);

    List<Workout> getWorkoutList(String username);

    boolean checkPassword(String username,String password);
}
