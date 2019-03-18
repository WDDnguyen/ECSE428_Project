package mcgill.shredit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;



import mcgill.shredit.MuscleGroupActivity;
import mcgill.shredit.model.*;

public class WorkoutActivity extends AppCompatActivity {

    List<Equipment> equipments;
    HashMap<String, Integer> muscleGroups;
    List<Exercise> allExercises;
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
        String exerciseName = "Workout:";
        List<Exercise> workout_exercises;

        // set a name
        for (String muscleGroup : muscleGroups.keySet()) {
            exerciseName = exerciseName + " " + muscleGroup;
        }

        //TODO get all valid exercises
        allExercises = queryValidExercises(equipments, muscleGroups);

        //TODO select subset of exercises
        chosenExercises = allExercises;

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

    // TODO query database. Don't forget to randomly select from list based on musclegroup integer's
    // specifications
    public static List<Exercise> queryValidExercises(List<Equipment> equipmentList, HashMap<String, Integer> muscleGroup) {
        List<Exercise> res;
        Equipment none = new Equipment("None");

        res =  Arrays.asList(
            new Exercise("test1", "description", "Muscle group", none),
            new Exercise("test2", "description", "Muscle group", none)
        );

        return res;
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
