package mcgill.shredit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

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

        final ListView listview = (ListView) findViewById(R.id.listview);
        ArrayList<String> list = new ArrayList<String>();

        String display;
        for (String muscleGroup : muscleGroups.keySet()){
            display="MUSCLE GROUP : " + muscleGroup  + " NUMBER OF EXERCISES : " + muscleGroups.get(muscleGroup);
            list.add(display);
        }

        final ArrayAdapter adapter = new ArrayAdapter(this,
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

    public static Workout generateWorkout(List<Exercise> exercises, String name, int id) {
        Workout wo = new Workout(name, id);

        for (Exercise ex : exercises) {
            wo.addExercise(ex);
        }

        return wo;
    }

    public static boolean swapExercise(Workout wo, Exercise orig, Exercise newex){
        boolean success_rm=false;
        boolean success_add=false;
        success_rm = wo.removeExercise(orig);
        success_add = wo.addExercise(newex);
        return (success_rm && success_add);
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
