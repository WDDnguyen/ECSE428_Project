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

public class WorkoutActivity extends AppCompatActivity {

    List<Equipment> equipments;
    HashMap<String, Integer> muscleGroups;
    HashMap<Exercise, String> allExercises;   //format exercise, muscle group
    Map<String, List<Exercise>> exercisesWithMuslces;   //inverse of allExercises
    List<Exercise> chosenExercises;
    Workout workout;
    ArrayAdapter adapter;
    ArrayList<String> exerciseList;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        getIntentValues();
        printEquipments();
        printMuscleGroups();

        listview = findViewById(R.id.list_workout);

        //todo if no workout provided, generate one
        workout = createWorkout();

        // set values to display
        exerciseList = new ArrayList<String>();
        String display;
        for (Exercise exercise : chosenExercises){
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
                selectedExercise = chosenExercises.get(position);

                createPopup(selectedExercise);
                Exercise replacement = generateReplacementExercise(selectedExercise);

                chosenExercises.remove(position);
                chosenExercises.add(position, replacement);

                // Update list displaying to screen
                exerciseList = new ArrayList<String>();
                String display;
                for (Exercise exercise : chosenExercises){
                    display = exercise.getName();
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
        chosenExercises = new ArrayList<>();
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
        equipments = (List<Equipment>) intent.getSerializableExtra("MUSCLE_GROUP_EQUIPMENT_LIST");
        muscleGroups = (HashMap<String, Integer>) intent.getSerializableExtra("MUSCLE_GROUPS_HASHMAP");
    }

    public void onWorkoutDoneClick(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
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

    /**
     * Create popup, display name of given exercise
     * @param exercise Exercise name to be displayed
     */
    public void createPopup(Exercise exercise) {
        WorkoutSwapPopupActivity popup = WorkoutSwapPopupActivity.newInstance(exercise.getName());
        popup.show(getSupportFragmentManager(), "Dialog");
    }

    /**
     * Create a replacement exercise for targetExercise
     * @param targetExercise Exercise to be swapped
     * @return
     */
    public Exercise generateReplacementExercise(Exercise targetExercise) {
        String muscle = allExercises.get(targetExercise.getName());
        HashMap<String, Integer> group = new HashMap<>();
        group.put(muscle,0);
        HashMap<Exercise, String> possibleReplacements = queryValidExercises(equipments, group, "");

        //select a random one
        List<Exercise> exercises = new ArrayList<>();
        exercises.addAll(possibleReplacements.keySet());
        Random rand = new Random();
        Exercise chosenExercise = exercises.get(rand.nextInt(exercises.size()));
        return chosenExercise;
    }

    public void onSaveClick(View view) {
        //todo save workout
        //include a popup to input a name, before changing pages to home activity.
        //Workout already has temp name
    }
}
