package mcgill.shredit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import mcgill.shredit.data.Repository;
import mcgill.shredit.data.DBService;
import mcgill.shredit.data.DataSourceStub;
import mcgill.shredit.data.MuscleGroup;
import mcgill.shredit.model.*;

public class WorkoutActivity extends AppCompatActivity implements WorkoutSwapPopupActivity.WorkoutSwapDialogListener{

    // values used between threads
    Repository repo;
    List<Equipment> equipments;
    HashMap<String, Integer> muscleGroups;
    HashMap<Exercise, String> allExercises;   //format exercise, muscle group
    Map<String, List<Exercise>> exercisesWithMuslces;   //inverse of allExercises
    Workout workout;
    final Context context = this; //for use with dialogPrompt (in Save Workout S7 task)
    private String saveWorkoutDialogText = "";
    //Repository rp = Repository.getInstance();
    DataSourceStub dss = new DataSourceStub();
    String username;

    // output details
    ArrayAdapter adapter;
    ArrayList<String> exerciseList;
    ListView listview;
    String prevClassName;

    // popup relevant variables
    List<Exercise> exercisePool;
    Exercise targetExercise;
    int listenedIndex;
    int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        repo = Repository.getInstance();

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

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // open popup on-click
                Exercise selectedExercise;
                selectedExercise = workout.getExercises().get(position);
                selectedIndex = position;
                createPopup(selectedExercise);
            }
        });

        // set values to display
        exerciseList = new ArrayList<String>();
        String display;
        for (Exercise exercise : workout.getExercises()){
            display = exercise.getName();
            exerciseList.add(display);
        }

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
        System.out.println(generatedWorkout);
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
        username = intent.getStringExtra("USER");
    }

    //TODO Restore proper database querying
    public HashMap<Exercise, String> queryValidExercises(List<Equipment> equipmentList,
        HashMap<String, Integer> muscleGroup, String gymName) {

//        HashMap<Exercise, String> res = new HashMap<>();
//        Equipment none = new Equipment("None");
//        res.put(new Exercise("test1", "description", "Abs", none), "Abs");
//        res.put(new Exercise("test2", "description", "Abs", none), "Abs");
//        res.put(new Exercise("test3", "description", "Abs", none), "Abs");
//        return res;

        // build gym object
//        String gname = "";
//        for(Equipment x : equipmentList) {
//            gname = gname + x.getName() + " ";
//        }
//        Gym g = new Gym(username + gname);
//        for (Equipment e : equipmentList) {
//            g.addEquipment(e);
//        }
//        repo.addGym(username, g);

        //cycle through muscle groups
        List<Exercise> exercises;
        HashMap<Exercise, String> validExercises = new HashMap<>();

        for(String muscle : muscleGroup.keySet()) {
            exercises = new ArrayList<>();
            exercises.addAll(repo.getExerciseList(null, null, null));
//            exercises.addAll(repo.getExerciseList(muscle, "public", ""));

            for(Exercise ex : exercises) {
                System.out.println(ex.getName());
                validExercises.put(ex, muscle);
            }
        }

//        return exercises;


        // for each muscle
//        for(String muscle : muscleGroup.keySet()) {
//            for(Exercise exercise: exercises) {
//                validExercises.put(exercise, muscle);
//            }
//        }
        return validExercises;
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


    public void createPopup(Exercise exercise) {
        // create list of valid exercises

        String muscle = allExercises.get(exercise.getName());
        HashMap<String, Integer> group = new HashMap<>();
        group.put(muscle,0);
        HashMap<Exercise, String> possibleReplacements = queryValidExercises(equipments, group, "");

        // Exercise pool holds all valid exercises
        exercisePool = new ArrayList<>();
        exercisePool.addAll(possibleReplacements.keySet());

        //select a random one
        Random rand = new Random();
        if (exercisePool.size() > 0) {
            Exercise randExercise = exercisePool.get(rand.nextInt(exercisePool.size()));
            exercisePool.add(0, randExercise);  // add this random exercise to front of list
        } else {
            // no replacement exercises exist
            Toast.makeText(getApplicationContext(),
                    "No replacement exercises available",
                    Toast.LENGTH_SHORT).show();
        }

        // convert list to string array, to send to popup
        String[] stringExercisePool = new String[exercisePool.size()];
        int counter = 0;
        for(Exercise x : exercisePool) {
            stringExercisePool[counter++] = x.getName();
        }

        //rename item 0 to random
        stringExercisePool[0] = "Random Exercise";

        targetExercise = exercise;
        WorkoutSwapPopupActivity popup = WorkoutSwapPopupActivity.newInstance(exercise.getName(), stringExercisePool);
        popup.show(getSupportFragmentManager(), "Dialog");
    }

    @Override
    public void applyIndex(int index) {
        listenedIndex = index;

        // modify workout
        removeExFromWorkout(workout, targetExercise);
        addExToWorkout(workout, exercisePool.get(index));

        updateAdapter(exercisePool.get(index));
    }

    public void updateAdapter(Exercise exToReplace) {
        exerciseList.remove(selectedIndex);
        exerciseList.add(selectedIndex, exToReplace.getName());
        adapter.notifyDataSetChanged();
        listview.setAdapter(adapter);
    }

    //User presses Home button
    public void onWorkoutDoneClick(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("USER", username);
        startActivity(intent);
    }

    //User presses Save Workout button
    public void onSaveWorkoutButtonClick(View view){
        //load dialog_save_workout_name.xml DialogPrompt and inflate for the View
        View promptSaveWorkoutView = LayoutInflater.from(context).inflate(R.layout.dialog_save_workout_name, null);
        //Link the input EditText from the layout
        final EditText userInput = (EditText) promptSaveWorkoutView.findViewById(R.id.saveWorkoutNameInput);

        //Builder is the viewable dialog prompt
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setTitle("Save This Workout");
        alertBuilder.setMessage("Enter a unique name to save your current workout.");
        alertBuilder.setView(promptSaveWorkoutView);

        // Set up the buttons
        alertBuilder.setPositiveButton("Save Workout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String userInputString = userInput.getText().toString();
                if(userInputString != null && !userInputString.isEmpty()) {
                    //TODO:Check if user's inputted workout name is unique in database
                    //if (workout name is valid) then
                    if(isSaveWorkoutNameUnique(userInputString)){
                        saveWorkoutDialogText = userInputString;
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                                "Workout saved successfully!",
                                Toast.LENGTH_SHORT).show();

                    //else: error message
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Please enter a unique name for your workout",
                                Toast.LENGTH_SHORT).show();
                        //alertBuilder.setMessage("Please enter a unique name for your workout!");
                    }

                } else{
                    Toast.makeText(getApplicationContext(),
                            "Please enter a unique name for your workout",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertBuilder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        alertBuilder.show();
    }
    
    public boolean isSaveWorkoutNameUnique (String workoutName){
        return true;
    }
}
