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

import mcgill.shredit.data.DataSourceStub;
import mcgill.shredit.data.Repository;
import mcgill.shredit.model.*;

public class SavedWorkoutActivity extends AppCompatActivity {

    ListView savedWorkoutView;
    ArrayAdapter<String> adapter;
    int selectedPos;
    boolean isSelected;
    List<Workout> savedWorkoutList;
    ArrayList<String> savedWorkoutNames;
    String username;

    Repository rp = Repository.getInstance(this);
    //DataSourceStub dss = new DataSourceStub();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_workouts);
        savedWorkoutView = findViewById(R.id.saved_workout_view);
        savedWorkoutView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        getIntentValues();

        //addTestData(); //remove once Repository is working

        //db query calls (need to change to use Workout object once WorkoutActivity facilitates accepting multiple intent)
        savedWorkoutNames = new ArrayList<>();
        savedWorkoutList = querySavedWorkouts();

        for (Workout wo : savedWorkoutList) {
            savedWorkoutNames.add(wo.getName());
        }

        //set init selected state
        selectedPos = -1;
        isSelected = false;

        adapter = new ArrayAdapter<>(
                SavedWorkoutActivity.this,
                android.R.layout.simple_list_item_1,
                savedWorkoutNames
        );

        //display name of workouts as list item
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

    //Passes data selected to WorkoutActivity
    public void onLoadButtonClick(View view) {
        if(isSelected){
            Intent intent = new Intent(this, WorkoutActivity.class);
            intent.putExtra("WORKOUT", savedWorkoutList.get(selectedPos));
            intent.putExtra("CLASS", "SavedWorkoutActivity");
            intent.putExtra("USER", username);
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Please select a workout to load!",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //Deletes data locally and on the database
    public void onDeleteButtonClick(View view) {
        if(isSelected) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to delete " + savedWorkoutNames.get(selectedPos));
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Workout delWorkout = savedWorkoutList.get(selectedPos);
                    writeDeleteSavedWorkout(delWorkout);
                    savedWorkoutView.clearChoices();
                    savedWorkoutList.remove(selectedPos);
                    savedWorkoutNames.remove(selectedPos);
                    adapter.notifyDataSetChanged();
                    savedWorkoutView.setAdapter(adapter);
                    selectedPos = -1;
                    isSelected = false;
                    Toast toast = Toast.makeText(getApplicationContext(),
                             delWorkout.getName() + " workout deleted.",
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

    //Delete workout on database (Will change to workout object once WorkoutActivity is changed)
    private void writeDeleteSavedWorkout(Workout wo) {
        //TODO: Add db call to remove workout once repository is working
        rp.removeWorkout(username, wo);
    }

    /*---- Queries ----*/
    //TODO: Change to call db for workouts once repository is working
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

//    public void addTestData() {
//        dss.addUser(username, "123");
//        Workout wo1 = new Workout("");
//        Workout wo2 = new Workout("");
//        wo1.setName("Abs x2");
//        wo2.setName("Abs x4");
//        Equipment none = new Equipment("None");
//        wo1.addExercise(new Exercise("test1", "description", "Abs", none));
//        wo1.addExercise(new Exercise("test2", "description", "Abs", none));
//        wo2.addExercise(new Exercise("test2", "description", "Abs", none));
//        wo2.addExercise(new Exercise("test1", "description", "Abs", none));
//        wo2.addExercise(new Exercise("test2", "description", "Abs", none));
//        wo2.addExercise(new Exercise("test1", "description", "Abs", none));
//        dss.addWorkout(username, wo1);
//        dss.addWorkout(username, wo2);
//    }

    private List<Workout> querySavedWorkouts() {
        return rp.getWorkoutList(username);
    }

    public void getIntentValues(){
        Intent intent = getIntent();
        username = intent.getStringExtra("USER");
    }
}
