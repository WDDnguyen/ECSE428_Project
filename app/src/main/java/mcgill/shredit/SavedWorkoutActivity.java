package mcgill.shredit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import mcgill.shredit.model.*;

public class SavedWorkoutActivity extends AppCompatActivity {

    ListView savedWorkoutView;
    ArrayAdapter<String> adapter;
    int selectedPos;
    boolean isSelected;
    ArrayList<HashMap<String,Integer>> savedMuscleGroupList;
    ArrayList<List<Equipment>> savedEquipmentList;
    ArrayList<String> savedWorkoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_workouts);
        savedWorkoutView = findViewById(R.id.saved_workout_view);
        savedWorkoutView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        savedWorkoutList = querySavedWorkoutNames();
        savedEquipmentList = querySavedEquipments();
        savedMuscleGroupList = querySavedMuscleGroups();

        selectedPos = -1;
        isSelected = false;

        adapter = new ArrayAdapter<>(
                SavedWorkoutActivity.this,
                android.R.layout.simple_list_item_1,
                savedWorkoutList
        );

        savedWorkoutView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPos = position;
                isSelected = true;
                savedWorkoutView.setSelection(selectedPos);
            }
        });
        savedWorkoutView.setAdapter(adapter);
    }

    public void onLoadButtonClick(View view) {
        if(isSelected){
            Intent intent = new Intent(this, WorkoutActivity.class);
            intent.putExtra("MUSCLE_GROUP_EQUIPMENT_LIST", (Serializable) savedEquipmentList.get(selectedPos));
            intent.putExtra("MUSCLE_GROUPS_HASHMAP", savedMuscleGroupList.get(selectedPos));
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Please select a workout to load!",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onDeleteButtonClick(View view) {
        if(isSelected) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to delete " + savedWorkoutList.get(selectedPos));
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    String woName = savedWorkoutList.get(selectedPos);
                    writeDeleteSavedWorkout(woName, savedEquipmentList.get(selectedPos), savedMuscleGroupList.get(selectedPos));
                    savedWorkoutView.clearChoices();
                    savedWorkoutList.remove(selectedPos);
                    savedEquipmentList.remove(selectedPos);
                    savedMuscleGroupList.remove(selectedPos);
                    adapter.notifyDataSetChanged();
                    savedWorkoutView.setAdapter(adapter);
                    selectedPos = -1;
                    isSelected = false;
                    Toast toast = Toast.makeText(getApplicationContext(),
                            woName + " workout deleted.",
                            Toast.LENGTH_SHORT);
                    toast.show();
                    dialog.dismiss();

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Please select a workout to delete!",
                    Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    private void writeDeleteSavedWorkout(String workoutName, List<Equipment> workoutEquipment, HashMap<String,Integer> workoutMuscleGroups) {

    }

    private ArrayList<String> querySavedWorkoutNames() {
        String[] array = {"Chest and Arms", "Abs"};
        ArrayList<String> savedWorkouts = new ArrayList<>(Arrays.asList(array));
        return savedWorkouts;
    }

    private ArrayList<List<Equipment>> querySavedEquipments() {
        ArrayList<List<Equipment>> savedEquipments = new ArrayList<>();
        List<Equipment> e1 = Arrays.asList(new Equipment("Barbells"), new Equipment("Exercise Ball"));
        List<Equipment> e2 = Arrays.asList(new Equipment("Medicine Ball"));
        savedEquipments.add(e1);
        savedEquipments.add(e2);
        return savedEquipments;
    }

    private ArrayList<HashMap<String, Integer>> querySavedMuscleGroups() {
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
