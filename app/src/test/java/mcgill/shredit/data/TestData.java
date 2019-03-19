package mcgill.shredit.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mcgill.shredit.model.Equipment;
import mcgill.shredit.model.Exercise;
import mcgill.shredit.model.Gym;
import mcgill.shredit.model.User;
import mcgill.shredit.model.Workout;

public final class TestData {

    public static User testUser = new User("User1", "password123");
    public static Equipment testEquipment[] = {
            new Equipment("Dumbbells"),
            new Equipment("Bench"),
            new Equipment("Stationary Bike")
    };
    public static Exercise testExercises[] = {
            new Exercise("Dumbbell curls", "Lift a dumbbell", MuscleGroup.BICEPS, testEquipment[0]),
            new Exercise("Dumbbell flies", "Lift a dumbbell", MuscleGroup.CHEST, testEquipment[0]),
            new Exercise("Bench press", "Lift a barbell", MuscleGroup.CHEST, testEquipment[1])
    };
    public static Gym testGyms[] = {
            buildGym("myGym", Arrays.asList(testEquipment))
    };
    public static Workout testWorkouts[] = {
            buildWorkout("myWorkout", Arrays.asList(testExercises))
    };

    private static Gym buildGym(String gymName, List<Equipment> equipment) {
        Gym gym = new Gym(gymName);
        for(Equipment e : equipment)
            gym.addEquipment(e);
        return gym;
    }

    private static Workout buildWorkout(String workoutName, List<Exercise> exercises) {
        Workout workout = new Workout(workoutName);
        for(Exercise e : exercises)
            workout.addExercise(e);
        return workout;
    }
}
