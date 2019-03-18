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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        getIntentValues();
        printEquipments();
        printMuscleGroups();

        ListView listview = (ListView) findViewById(R.id.listview);

        // generate workouts
        String exercise_name = "Workout:";
        List<Exercise> workout_exercises;

        // set a name
        for (String muscleGroup : muscleGroups.keySet()) {
            exercise_name = exercise_name + " " + muscleGroup;
        }

        //TODO get all valid exercises
        workout_exercises = queryValidExercises(equipments, muscleGroups);

        Workout generatedWorkout =  generateWorkout(workout_exercises, exercise_name);

        // set values to display
        ArrayList<String> list = new ArrayList<String>();
        String display;
//        ListView display;
//        for (String muscleGroup : muscleGroups.keySet()){
//            display="MUSCLE GROUP : " + muscleGroup  + " NUMBER OF EXERCISES : " + muscleGroups.get(muscleGroup);

        for (Exercise exercise : workout_exercises){
//            display="MUSCLE GROUP : " + muscleGroup  + " NUMBER OF EXERCISES : " + muscleGroups.get(muscleGroup);
            display = exercise.getName();

//            display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    //onGymConfirmClick(arg1);
//                    Intent intent = new Intent(getApplication(), GymPresetActivity.class);
//                    intent.putExtra("item_gym_name", arrayGyms.get(position)); //pass the gym name onto the next activity
//                    startActivity(intent);
//                }
//            })

            list.add(display);
        }

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
}
