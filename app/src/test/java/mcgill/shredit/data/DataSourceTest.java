package mcgill.shredit.data;

import org.junit.Assert;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

    @Test
    public void testAddRemoveUser() {
        User newUser = new User("User2", "password123");
        assertTrue(dataSource.addUser(newUser.getUsername(), newUser.getPassword()));
        assertTrue(dataSource.checkPassword(newUser.getUsername(), newUser.getPassword()));
        assertTrue(dataSource.removeUser(newUser.getUsername()));
        assertFalse(dataSource.checkPassword(newUser.getUsername(), newUser.getPassword()));
    }

    @Test
    public void testAddRemoveEquipment() {
        Equipment newEquipment = new Equipment("equipment1");
        assertTrue(dataSource.addEquipment(newEquipment));
        boolean flag = false;
        for(Equipment equipment : dataSource.getEquipmentList()) {
            if (equipment.getName().equals(newEquipment.getName())) {
                flag = true;
            }
        }
        assertTrue(flag);
        assertTrue(dataSource.removeEquipment(newEquipment));
        for(Equipment equipment : dataSource.getEquipmentList())
            if(equipment.getName().equals(newEquipment.getName()))
                fail();
    }

    @Test
    public void testAddRemoveExercise() {
        Exercise newExercise = new Exercise("exercise1", "description", MuscleGroup.ABS, testEquipment);
        assertTrue(dataSource.addExercise(newExercise));
        boolean flag = false;
        for(Exercise exercise : dataSource.getExerciseList(null, null, null)) {
            if (exercise.getName().equals(newExercise.getName())) {
                flag = true;
            }
        }
        assertTrue(flag);
        assertTrue(dataSource.removeExercise(newExercise));
        for(Exercise exercise : dataSource.getExerciseList(null, null, null))
            if(exercise.getName().equals(newExercise.getName()))
                fail();
    }

    @Test
    public void testAddRemoveGym() {
        assertTrue(dataSource.removeGym(testUser.getUsername(), testGym));
        for(Gym gym : dataSource.getGymList(testUser.getUsername()))
            if(isTestGym(gym))
                fail();
        assertTrue(dataSource.addGym(testUser.getUsername(), testGym));
        boolean flag = false;
        for(Gym gym : dataSource.getGymList(testUser.getUsername())) {
            if (isTestGym(gym)) {
                flag = true;
            }
        }
        assertTrue(flag);
    }

    @Test
    public void testAddRemoveWorkout() {
        assertTrue(dataSource.removeWorkout(testUser.getUsername(), testWorkout));
        for(Workout workout : dataSource.getWorkoutList(testUser.getUsername()))
            if(isTestWorkout(workout))
                fail();
        assertTrue(dataSource.addWorkout(testUser.getUsername(), testWorkout));
        boolean flag = false;
        for(Workout workout : dataSource.getWorkoutList(testUser.getUsername())) {
            if (isTestWorkout(workout)) {
                flag = true;
            }
        }
        assertTrue(flag);
    }
}