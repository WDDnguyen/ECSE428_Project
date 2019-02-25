package mcgill.shredit;

import mcgill.shredit.model.*;
import java.util.List;
import java.util.ArrayList;

public class Test212425 {
    public static void main(String[] args) {
        System.out.println("Test generation of workout : " + passFail(testGenWO()));
        System.out.println("Test adding exercise : " + passFail(testAddEx()));
        System.out.println("Test removing exercise : " + passFail(testRemoveEx()));
    }

    public static boolean testGenWO() {

        Workout controlWO = woTestData();
        Workout testWO = WorkoutActivity.generateWorkout(exTestData(), "Control WO", 1);

        return workoutsAreEqual(controlWO, testWO);
    }

    public static boolean testAddEx() {

        Workout wo = woTestData();
        boolean verif = checkValidEx(wo, 2, 4);

        Exercise newEx = new Exercise("Cardio", "Bike", "Pedal on the stationary bike", 5, new Equipment("StatBike", 6));
        wo.addExercise(newEx);

        verif = verif && checkValidEx(wo, 3, 5);
        return verif;

    }

    public static boolean testRemoveEx() {

        Workout wo = woTestData();
        Exercise toRemove = wo.getExercise(wo.getExercises().size() - 1);
        boolean verif = checkValidEx(wo, 2, 4);
        wo.removeExercise(toRemove);
        verif = verif && checkValidEx(wo, 1, 2);
        return verif;
    }

    private static Workout woTestData() {

        Workout controlWO = new Workout("Control WO", 1);
        List<Exercise> exTestData = exTestData();
        for (Exercise ex : exTestData) {
            controlWO.addExercise(ex);
        }
        return controlWO;

    }

    private static List<Exercise> exTestData() {
        List<Exercise> exs = new ArrayList<Exercise>();
        exs.add(new Exercise("Biceps", "Dumbbells", "Lift a db", 2, new Equipment("dumbbells", 3)));
        exs.add(new Exercise("Cardio", "Treadmill", "Run on treadmill", 4, new Equipment("treadmill", 5)));
        return exs;
    }

    private static boolean workoutsAreEqual(Workout w1, Workout w2) {

        if (!w1.getName().equals(w2.getName())) {
            return false;
        }

        if (w1.getId() != w2.getId()) {
            return false;
        }

        if (w1.getExercises().size() != w2.getExercises().size()) {
            return false;
        }

        for (int i = 0; i < w1.getExercises().size(); i++) {
            if (!exercisesAreEqual(w1.getExercise(i), w2.getExercise(i))) {
                return false;
            }
        }

        return true;

    }

    private static boolean exercisesAreEqual(Exercise e1, Exercise e2) {

        if (!e1.getDescription().equals(e2.getDescription())) {
            return false;
        }

        if (!e1.getMuscleGroup().equals(e2.getMuscleGroup())) {
            return false;
        }

        if (!e1.getName().equals(e2.getName())) {
            return false;
        }

        if (e1.getId() != e2.getId()) {
            return false;
        }

        if (!equipmentsAreEqual(e1.getEquipement(), e2.getEquipement())) {
            return false;
        }

        return true;

    }

    private static boolean equipmentsAreEqual(Equipment e1, Equipment e2) {

        if (!e1.getName().equals(e2.getName())) {
            return false;
        }

        if (e1.getId() != e2.getId()) {
            return false;
        }

        return true;
    }

    private static boolean checkValidEx(Workout wo, int expNumOfEx, int expIDOfLast) {
        boolean check = wo.getExercises().size() == expNumOfEx;
        check = check && wo.getExercises().get(wo.getExercises().size() - 1).getId() == expIDOfLast;
        return check;
    }

    private static String passFail(boolean result) {
        String returned = (result ? "Success" : "Failure");
        return returned;
    }
}
