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
import mcgill.shredit.model.User;
import mcgill.shredit.model.Workout;
import mcgill.shredit.DataSourceStub;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class DataSourceTest {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {new DBService()},
                {Repository.getInstance()},
                {createStub()}
        });
    }

    private DataSource dataSource;

    private static final User testUser = TestData.testUser;
    private static final Equipment testEquipment = TestData.testEquipment[0];
    private static final Exercise testExercise = TestData.testExercises[0];
    private static final Gym testGym = TestData.testGyms[0];
    private static final Workout testWorkout = TestData.testWorkouts[0];
    private static final String muscleGroupFilter = MuscleGroup.BICEPS;

    public DataSourceTest(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static DataSource createStub() {
        DataSource dataSource = new DataSourceStub();
        dataSource.addUser(testUser.getUsername(), testUser.getPassword());
        for(Equipment equipment : TestData.testEquipment)
            dataSource.addEquipment(equipment);
        for(Exercise exercise : TestData.testExercises)
            dataSource.addExercise(exercise);
        for(Gym gym : TestData.testGyms)
            dataSource.addGym(testUser.getUsername(), gym);
        for(Workout workout : TestData.testWorkouts)
            dataSource.addWorkout(testUser.getUsername(), workout);
        return dataSource;
    }

    private boolean isTestEquipment(Equipment equipment) {
        return equipment != null &&
                equipment.getName().equals(testEquipment.getName());
    }
    private boolean isTestExercise(Exercise exercise) {
        return exercise != null &&
                exercise.getName().equals(testExercise.getName()) &&
                exercise.getDescription().equals(testExercise.getDescription()) &&
                exercise.getMuscleGroup().equals(testExercise.getMuscleGroup()) &&
                isTestEquipment(exercise.getEquipment());
    }

    private boolean isTestGym(Gym gym) {
        return gym != null &&
                gym.getName().equals(testGym.getName());
    }

    private boolean isTestWorkout(Workout workout) {
        return workout != null &&
                workout.getName().equals(testWorkout.getName());
    }

    @Test
    public void testGetEquipment() {
        List<Equipment> equipmentList = dataSource.getEquipmentList();
        assertNotNull(equipmentList);
        assertFalse(equipmentList.isEmpty());
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
        List<Exercise> exerciseList = dataSource.getExerciseList(null, null, null);
        assertNotNull(exerciseList);
        assertFalse(exerciseList.isEmpty());
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
        List<Exercise> exerciseList = dataSource.getExerciseList(muscleGroupFilter, null, null);
        assertNotNull(exerciseList);
        assertFalse(exerciseList.isEmpty());
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
        List<Exercise> exerciseList = dataSource.getExerciseList(null, testUser.getUsername(), testGym.getName());
        assertNotNull(exerciseList);
        assertFalse(exerciseList.isEmpty());
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
        List<Exercise> exerciseList = dataSource.getExerciseList(muscleGroupFilter, testUser.getUsername(), testGym.getName());
        assertNotNull(exerciseList);
        assertFalse(exerciseList.isEmpty());
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
        List<Gym> gymList = dataSource.getGymList(testUser.getUsername());
        assertNotNull(gymList);
        assertFalse(gymList.isEmpty());
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
        List<Workout> workoutList = dataSource.getWorkoutList(testUser.getUsername());
        assertNotNull(workoutList);
        assertFalse(workoutList.isEmpty());
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
        assertTrue(dataSource.checkPassword(testUser.getUsername(), testUser.getPassword()));
    }
}