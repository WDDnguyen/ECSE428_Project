package mcgill.shredit.data;

import mcgill.shredit.model.Equipment;
import mcgill.shredit.model.Exercise;
import mcgill.shredit.model.Gym;
import mcgill.shredit.model.User;
import mcgill.shredit.model.Workout;

public interface TestData {

    User testUser = new User("User1", "password123");
    Equipment testEquipment[] = {
            new Equipment("Dumbbells"),
            new Equipment("Bench"),
            new Equipment("Stationary Bike")
    };
    Exercise testExercises[] = {
            new Exercise("Dumbbell curls", "Lift a dumbbell", MuscleGroup.BICEPS, testEquipment[0]),
            new Exercise("Dumbbell flies", "Lift a dumbbell", MuscleGroup.CHEST, testEquipment[0]),
            new Exercise("Bench press", "Lift a barbell", MuscleGroup.CHEST, testEquipment[1])
    };
    Gym testGyms[] = {
            new Gym("myGym")
    };
    Workout testWorkouts[] = {
            new Workout("myWorkout")
    };
}
