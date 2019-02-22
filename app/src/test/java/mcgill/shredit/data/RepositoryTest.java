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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class RepositoryTest {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"User1", "password",
                        0, "Dumbbells", 0,
                        "Curls", "TODO", MuscleGroup.BICEPS,
                        0, "Gym1",
                        0, "Workout1",
                        MuscleGroup.BICEPS, true},
                {"User1", "password",
                        0, "Dumbbells", 0,
                        "Curls", "TODO", MuscleGroup.BICEPS,
                        0, "Gym1",
                        0, "Workout1",
                        MuscleGroup.CHEST, true},
        });
    }

    private String username;

    private String password;

    private int equipmentID;

    private String equipmentName;

    private int exerciseID;

    private String exerciseName;

    private String exerciseDescription;

    private String exerciseMuscleGroup;

    private int gymID;

    private String gymName;

    private int workoutID;

    private String workoutName;

    private String filterMuscleGroup;

    private boolean equipmentAvailable;

    public RepositoryTest(String username,
                          String password,
                          int equipmentID,
                          String equipmentName,
                          int exerciseID,
                          String exerciseName,
                          String exerciseDescription,
                          String exerciseMuscleGroup,
                          int gymID,
                          String gymName,
                          int workoutID,
                          String workoutName,
                          String filterMuscleGroup,
                          boolean equipmentAvailable) {
        this.username = username;
        this.password = password;
        this.equipmentID = equipmentID;
        this.equipmentName = equipmentName;
        this.exerciseID = exerciseID;
        this.exerciseName = exerciseName;
        this.exerciseDescription = exerciseDescription;
        this.exerciseMuscleGroup = exerciseMuscleGroup;
        this.gymID = gymID;
        this.gymName = gymName;
        this.workoutID = workoutID;
        this.workoutName = workoutName;
        this.filterMuscleGroup = filterMuscleGroup;
        this.equipmentAvailable = equipmentAvailable;
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
        Repository repo = Repository.getInstance();
        List<Equipment> equipmentList = repo.getEquipmentList();
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
        Repository repo = Repository.getInstance();
        List<Exercise> exerciseList = repo.getExerciseList(null, -1);
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
        Repository repo = Repository.getInstance();
        List<Exercise> exerciseList = repo.getExerciseList(filterMuscleGroup, -1);
        assertNotNull(exerciseList);
        boolean found = false;
        for (Exercise exercise : exerciseList) {
            if (isTestExercise(exercise)) {
                found = true;
                break;
            }
        }
        if (exerciseMuscleGroup.equals(filterMuscleGroup)) {
            assertTrue(found);
        } else {
            assertFalse(found);
        }
    }

    @Test
    public void testGetExerciseListGymFilter() {
        Repository repo = Repository.getInstance();
        List<Exercise> exerciseList = repo.getExerciseList(null, gymID);
        assertNotNull(exerciseList);
        boolean found = false;
        for (Exercise exercise : exerciseList) {
            if (isTestExercise(exercise)) {
                found = true;
                break;
            }
        }
        if (equipmentAvailable) {
            assertTrue(found);
        } else {
            assertFalse(found);
        }
    }

    @Test
    public void testGetExerciseListMuscleGroupAndGymFilter() {
        Repository repo = Repository.getInstance();
        List<Exercise> exerciseList = repo.getExerciseList(filterMuscleGroup, gymID);
        assertNotNull(exerciseList);
        boolean found = false;
        for (Exercise exercise : exerciseList) {
            if (isTestExercise(exercise)) {
                found = true;
                break;
            }
        }
        if (exerciseMuscleGroup.equals(filterMuscleGroup) && equipmentAvailable) {
            assertTrue(found);
        } else {
            assertFalse(found);
        }
    }

    @Test
    public void testGetGymList() {
        Repository repo = Repository.getInstance();
        List<Gym> gymList = repo.getGymList(username);
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
        Repository repo = Repository.getInstance();
        List<Workout> workoutList = repo.getWorkoutList(username);
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
    public void testGetPassword() {
        Repository repo = Repository.getInstance();
        assertEquals(password, repo.getPassword(username));
    }
}