package mcgill.shredit.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import mcgill.shredit.model.Equipment;
import mcgill.shredit.model.Exercise;
import mcgill.shredit.model.Gym;
import mcgill.shredit.model.Workout;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class DataSourceTest {

    private static final String USERNAME = "User1";
    private static final String PASSWORD = "password";
    private static final int equipmentID = 0;
    private static final String equipmentName = "Dumbbells";
    private static final int exerciseID = 0;
    private static final String exerciseName = "Curls";
    private static final String exerciseDescription = "TODO";
    private static final String exerciseMuscleGroup = MuscleGroup.BICEPS;
    private static final int gymID = 0;
    private static final String gymName = "Gym1";
    private static final int workoutID = 0;
    private static final String workoutName = "Workout1";

    private static final String filterMuscleGroup = MuscleGroup.BICEPS;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {new DBService()},
                {Repository.getInstance()},
        });
    }

    private DataSource dataSource;

    public DataSourceTest(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private boolean isTestEquipment(Equipment equipment) {
        return equipment != null &&
                equipment.getId() == equipmentID &&
                equipment.getName().equals(equipmentName);
    }
    private boolean isTestExercise(Exercise exercise) {
        return exercise != null &&
                exercise.getId() == exerciseID &&
                exercise.getName().equals(exerciseName) &&
                exercise.getDescription().equals(exerciseDescription) &&
                exercise.getMuscleGroup().equals(exerciseMuscleGroup) &&
                isTestEquipment(exercise.getEquipment());
    }

    private boolean isTestGym(Gym gym) {
        return gym != null &&
                gym.getId() == gymID &&
                gym.getName().equals(gymName);
    }

    private boolean isTestWorkout(Workout workout) {
        return workout != null &&
                workout.getId() == workoutID &&
                workout.getName().equals(workoutName);
    }

    @Test
    public void testGetEquipment() {
        List<Equipment> equipmentList = dataSource.getEquipmentList();
        assertNotNull(equipmentList);
        boolean found = false;
        for (Equipment equipment : equipmentList) {
            if (isTestEquipment(equipment)) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testGetExerciseListNoFilter() {
        List<Exercise> exerciseList = dataSource.getExerciseList(null, -1);
        assertNotNull(exerciseList);
        boolean found = false;
        for (Exercise exercise : exerciseList) {
            if (isTestExercise(exercise)) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testGetExerciseListMuscleGroupFilter() {
        List<Exercise> exerciseList = dataSource.getExerciseList(filterMuscleGroup, -1);
        assertNotNull(exerciseList);
        boolean found = false;
        for (Exercise exercise : exerciseList) {
            if (isTestExercise(exercise)) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testGetExerciseListGymFilter() {
        List<Exercise> exerciseList = dataSource.getExerciseList(null, gymID);
        assertNotNull(exerciseList);
        boolean found = false;
        for (Exercise exercise : exerciseList) {
            if (isTestExercise(exercise)) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testGetExerciseListMuscleGroupAndGymFilter() {
        List<Exercise> exerciseList = dataSource.getExerciseList(filterMuscleGroup, gymID);
        assertNotNull(exerciseList);
        boolean found = false;
        for (Exercise exercise : exerciseList) {
            if (isTestExercise(exercise)) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testGetGymList() {
        List<Gym> gymList = dataSource.getGymList(USERNAME);
        assertNotNull(gymList);
        boolean found = false;
        for(Gym gym: gymList) {
            if (isTestGym(gym)) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testGetWorkoutList() {
        List<Workout> workoutList = dataSource.getWorkoutList(USERNAME);
        assertNotNull(workoutList);
        boolean found = false;
        for(Workout workout: workoutList) {
            if (isTestWorkout(workout)) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testCheckPassword() {
        Repository repo = Repository.getInstance();
        assertTrue(repo.checkPassword(USERNAME, PASSWORD));
    }
}