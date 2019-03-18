package mcgill.shredit;

import android.content.Intent;
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

import mcgill.shredit.data.DBService;
import mcgill.shredit.model.*;

public class WorkoutActivity extends AppCompatActivity {

    List<Equipment> equipments;
    HashMap<String, Integer> muscleGroups;
    HashMap<Exercise, String> allExercises;   //format exercise, muscle group
    Map<String, List<Exercise>> exercisesWithMuslces;   //inverse of allExercises
    List<Exercise> chosenExercises;
    Workout workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        getIntentValues();
        printEquipments();
        printMuscleGroups();

        ListView listview = (ListView) findViewById(R.id.list_workout);

        // generate workouts
        // set a name
        String exerciseName = "Workout:";
        for (String muscleGroup : muscleGroups.keySet()) {
            exerciseName = exerciseName + " " + muscleGroup;
        }

        //TODO send gym name through activities
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
            availableExercises.addAll(exercisesWithMuslces.get(muscleGroup));

            // select exercises from provided list
            for(int i = 0; i < muscleGroups.get(muscleGroup); i++) {
                chosenExercises.add(availableExercises.get(rand.nextInt(availableExercises.size())));
            }
        }

        Workout generatedWorkout =  generateWorkout(chosenExercises, exerciseName);

        // set values to display
        ArrayList<String> list = new ArrayList<String>();
        String display;

        for (Exercise exercise : chosenExercises){
            display = exercise.getName();
            list.add(display);
        }


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // open popup on-click
                //get workoutname
                String exerciseName;
                exerciseName = chosenExercises.get(position).getName();

                randomWorkout(exerciseName);
            }
        });

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);

        listview.setAdapter(adapter);

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

//        DBService db = new DBService();
//
//        HashMap<Exercise, String> validExercises = new HashMap<>();
//
//        // for each muscle
//        for(String muscle : muscleGroup.keySet()) {
//            List<Exercise> groupExercises = db.getExerciseList(muscle, gymName=="" ? null : gymName);
//            for(Exercise exercise: groupExercises) {
//                validExercises.put(exercise, muscle);
//            }
//        }
//
//        return validExercises;
    }

    public static Workout generateWorkout(List<Exercise> exercises, String name) {
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
     * Dialog for randomly generating new exercise
     */
    public void randomWorkout(String exerciseName) {
        WorkoutSwapPopupActivity popup = WorkoutSwapPopupActivity.newInstance(exerciseName);
        popup.show(getSupportFragmentManager(), "Dialog");
    }
}
