package mcgill.shredit;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import mcgill.shredit.model.*;

public class WorkoutActivity extends AppCompatActivity implements WorkoutSwapPopupActivity.WorkoutSwapDialogListener{

    List<Equipment> equipments;
    HashMap<String, Integer> muscleGroups;
    HashMap<Exercise, String> allExercises;   //format exercise, muscle group
    Map<String, List<Exercise>> exercisesWithMuslces;   //inverse of allExercises
    Workout workout;
    ArrayAdapter adapter;
    ArrayList<String> exerciseList;
    ListView listview;
    String prevClassName;

    int listenedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        getIntentValues();

        listview = findViewById(R.id.list_workout);

        // if no workout provided create one
        if (prevClassName.equals("MuscleGroupActivity")) {
            workout = createWorkout();
        } else {
            getWorkoutInfo();
        }

        printEquipments();
        printMuscleGroups();

        // set values to display
        exerciseList = new ArrayList<String>();
        String display;
        for (Exercise exercise : workout.getExercises()){
            display = exercise.getName();
            exerciseList.add(display);
        }


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // open popup on-click
                // get workoutname
                String exerciseName;
                Exercise selectedExercise;
                selectedExercise = workout.getExercises().get(position);

                Exercise replacement = createPopup(selectedExercise);

                removeExFromWorkout(workout, selectedExercise);
                addExToWorkout(workout, replacement);

                // Update list displaying to screen
                exerciseList = new ArrayList<String>();
                String display;
                System.out.println("After");
                for (Exercise exercise : workout.getExercises()){
                    display = exercise.getName();
//                    System.out.println(display);
                    exerciseList.add(display);
                }

                adapter.notifyDataSetChanged();
                listview.setAdapter(adapter);
            }
        });

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, exerciseList);

        listview.setAdapter(adapter);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    public Workout createWorkout() {
        // generate workouts
        // set a name
        String workoutName = "Workout:";
        for (String muscleGroup : muscleGroups.keySet()) {
            workoutName = workoutName + " " + muscleGroup;
        }

        //TODO fix db querying
        allExercises = queryValidExercises(equipments, muscleGroups, "");

        // invert hashmap allExercises
        exercisesWithMuslces = new HashMap<String, List<Exercise>>();   //muslce, exercises
        for(Map.Entry<Exercise, String> entry : allExercises.entrySet()){
            List<Exercise> list = new ArrayList<>();

            if(exercisesWithMuslces.containsKey(entry.getValue()))
                list = exercisesWithMuslces.get(entry.getValue());

            list.add(entry.getKey());
            exercisesWithMuslces.put(entry.getValue(), list);
        }


        // for each muscle group entry, get random exercises of the muscle group
        Random rand = new Random();
        List<Exercise> chosenExercises = new ArrayList<>();
        for (String muscleGroup : muscleGroups.keySet()) {
            //Get exercises for muscle group
            List<Exercise> availableExercises = new ArrayList<>();
            List<Exercise> muscleGroupExercises = exercisesWithMuslces.get(muscleGroup);
            if(muscleGroupExercises != null) {
                availableExercises.addAll(muscleGroupExercises);
            }

            // select exercises from provided list
            int numExercises = availableExercises.size();
            if (numExercises > 0) {
                for(int i = 0; i < muscleGroups.get(muscleGroup); i++) {
                    chosenExercises.add(availableExercises.get(rand.nextInt(availableExercises.size())));
                }
            }
        }

        Workout generatedWorkout =  generateWorkout(chosenExercises, workoutName, 1);
        return generatedWorkout;
    }

    /*----@bendwilletts----*/

    public void getWorkoutInfo(){
        muscleGroups = new HashMap<>();
        equipments = new ArrayList<>();
        for (Exercise exercise : workout.getExercises()) {
            addChosenEquipment(exercise.getEquipment());
            addChosenMuscleGroup(exercise.getMuscleGroup());
        }
        allExercises = queryValidExercises(equipments, muscleGroups, "");
        // invert hashmap allExercises
        exercisesWithMuslces = new HashMap<String, List<Exercise>>();   //muslce, exercises
        for(Map.Entry<Exercise, String> entry : allExercises.entrySet()){
            List<Exercise> list = new ArrayList<>();

            if(exercisesWithMuslces.containsKey(entry.getValue()))
                list = exercisesWithMuslces.get(entry.getValue());

            list.add(entry.getKey());
            exercisesWithMuslces.put(entry.getValue(), list);
        }


    }

    private void addChosenEquipment(Equipment chosenEquipment) {
        if(!equipments.contains(chosenEquipment)){
            equipments.add(chosenEquipment);
        }
    }

    private void addChosenMuscleGroup(String chosenMuscleGroup) {
        if(muscleGroups.containsKey(chosenMuscleGroup)) {
            muscleGroups.put(chosenMuscleGroup, muscleGroups.get(chosenMuscleGroup) + 1);
        } else {
            muscleGroups.put(chosenMuscleGroup, 1);
        }
    }

    /*-------------------*/

    public void printEquipments(){
        for (Equipment equipment : equipments){
            System.out.println("EQUIPEMENT RECEIVED : " + equipments);
        }
    }

    public void printMuscleGroups(){
        for (String muscleGroup : muscleGroups.keySet()){
            System.out.println("MUSCLE GROUP : " + muscleGroup  + " NUMBER OF EXERCISES : " + muscleGroups.get(muscleGroup));
        }
    }

    // Get intent content from MuscleGroupActivity
    public void getIntentValues(){
        Intent intent = getIntent();
        prevClassName = intent.getStringExtra("CLASS");
        if (prevClassName.equals("MuscleGroupActivity")) {
            equipments = (List<Equipment>) intent.getSerializableExtra("MUSCLE_GROUP_EQUIPMENT_LIST");
            muscleGroups = (HashMap<String, Integer>) intent.getSerializableExtra("MUSCLE_GROUPS_HASHMAP");
        } else {
            workout = (Workout) intent.getSerializableExtra("WORKOUT");
        }
    }

    //TODO Restore proper database querying
    public static HashMap<Exercise, String> queryValidExercises(List<Equipment> equipmentList,
        HashMap<String, Integer> muscleGroup, String gymName) {

        HashMap<Exercise, String> res = new HashMap<>();
        Equipment none = new Equipment("None");
        res.put(new Exercise("test1", "description", "Abs", none), "Abs");
        res.put(new Exercise("test2", "description", "Abs", none), "Abs");
        return res;

        //todo replace with actual values
//        Repository repo = Repository.getInstance();
//        List<Exercise> exercises = repo.getExerciseList("Gym name", "", "");
//        exercises.addAll(repo.getExerciseList("Gym name", "public", ""));
//
//        HashMap<Exercise, String> validExercises = new HashMap<>();
//
//        // for each muscle
//        for(String muscle : muscleGroup.keySet()) {
//            for(Exercise exercise: exercises) {
//                validExercises.put(exercise, muscle);
//            }
//        }
//        return validExercises;


    }

    public static Workout generateWorkout(List<Exercise> exercises, String name, int id) {
        Workout wo = new Workout(name);

        for (Exercise ex : exercises) {
            wo.addExercise(ex);
        }

        return wo;
    }

    public static boolean addExToWorkout(Workout wo, Exercise ex) {
        boolean wasAdded = false;

        wasAdded = wo.addExercise(ex);

        return wasAdded;
    }

    public static boolean removeExFromWorkout(Workout wo, Exercise ex) {
        boolean wasRemoved = false;

        wasRemoved = wo.removeExercise(ex);

        return wasRemoved;
    }


    public Exercise createPopup(Exercise exercise) {
        // create list of valid exercises

        String muscle = allExercises.get(exercise.getName());
        HashMap<String, Integer> group = new HashMap<>();
        group.put(muscle,0);
        HashMap<Exercise, String> possibleReplacements = queryValidExercises(equipments, group, "");

        // Exercise pool holds all valid exercises
        List<Exercise> exercisePool = new ArrayList<>();
        exercisePool.addAll(possibleReplacements.keySet());

        //select a random one
        Random rand = new Random();
        Exercise randExercise = exercisePool.get(rand.nextInt(exercisePool.size()));
        exercisePool.add(0, randExercise);  // add this random exercise to front of list

        // convert list to string array, to send to popup
        String[] stringExercisePool = new String[exercisePool.size()];
        int counter = 0;
        for(Exercise x : exercisePool) {
            stringExercisePool[counter++] = x.getName();
        }

        //rename item 0 to random
        stringExercisePool[0] = "Random Exercise";

        //todo udpate popup to give output index
        listenedIndex = -1;
        WorkoutSwapPopupActivity popup = WorkoutSwapPopupActivity.newInstance(exercise.getName(), stringExercisePool);
        popup.show(getSupportFragmentManager(), "Dialog");

        // get index of selected iteam from exercisePool
//        int popupIndex = popup.getIndex();
//        while(listenedIndex == -1) {}
//        Exercise replacementExercise = exercisePool.get(listenedIndex);
        Exercise replacementExercise = exercisePool.get(0);
        return replacementExercise;

    }

    public void onSaveClick(View view) {
        //todo save workout
        //include a popup to input a name, before changing pages to home activity.
        //Workout already has temp name
    }

    @Override
    public void applyIndex(int index) {
        listenedIndex = index;
        System.out.println(Integer.toString(listenedIndex) + ": Triggerred listener");

        // modify workout
//        removeExFromWorkout(workout, selectedWorkout);
//        addExToWorkout(workout, replacement);

        // update output
//        // Update list displaying to screen
//        exerciseList = new ArrayList<String>();
//        String display;
//        System.out.println("After");
//        for (Exercise exercise : chosenExercises){
//            display = exercise.getName();
//            exerciseList.add(display);
//        }
//
//        adapter.notifyDataSetChanged();
//        listview.setAdapter(adapter);
//
//        updateAdapter;
    }
}
