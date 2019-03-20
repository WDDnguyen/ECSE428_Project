package mcgill.shredit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import mcgill.shredit.model.*;

public class SavedWorkoutActivity extends AppCompatActivity {

    ListView savedWorkoutView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_workouts);
        savedWorkoutView = findViewById(R.id.saved_workout_view);

        final ArrayList<String> savedWorkoutList = querySavedWorkoutNames();
        final ArrayList<List<Equipment>> savedEquipmentList = querySavedEquipments();
        final ArrayList<HashMap<String,Integer>> savedMuscleGroupList = querySavedMuscleGroups();

        adapter = new ArrayAdapter<String>(
                SavedWorkoutActivity.this,
                android.R.layout.simple_list_item_1,
                savedWorkoutList
        );

        savedWorkoutView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplication(), WorkoutActivity.class);
                intent.putExtra("MUSCLE_GROUP_EQUIPMENT_LIST", (Serializable) savedEquipmentList.get(position));
                intent.putExtra("MUSCLE_GROUPS_HASHMAP", savedMuscleGroupList.get(position));
                startActivity(intent);
            }
        });
        savedWorkoutView.setAdapter(adapter);
    }

    private ArrayList<String> querySavedWorkoutNames(){
        String[] array = {"Chest and Arms", "Abs"};
        ArrayList<String> savedWorkouts = new ArrayList<>(Arrays.asList(array));
        return savedWorkouts;
    }

    private ArrayList<List<Equipment>> querySavedEquipments(){
        ArrayList<List<Equipment>> savedEquipments = new ArrayList<>();
        List<Equipment> e1 = Arrays.asList(new Equipment("Barbells"), new Equipment("Exercise Ball"));
        List<Equipment> e2 = Arrays.asList(new Equipment("Medicine Ball"));
        savedEquipments.add(e1);
        savedEquipments.add(e2);
        return savedEquipments;
    }

    private ArrayList<HashMap<String, Integer>> querySavedMuscleGroups(){
        ArrayList<HashMap<String,Integer>> savedMuscleGroups = new ArrayList<>();
        HashMap<String,Integer> m1 = new HashMap<>();
        m1.put("Chest", 2);
        m1.put("Back", 2);
        HashMap<String,Integer> m2 = new HashMap<>();
        m2.put("Abs", 4);
        savedMuscleGroups.add(m1);
        savedMuscleGroups.add(m2);
        return savedMuscleGroups;
    }
}
