package mcgill.shredit;

import android.app.AlertDialog;
import android.content.Context;
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

        //No workouts error check
        if (savedWorkoutNames.isEmpty()){
            openNoWorkoutsErrorPrompt();
        }
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

    //opens error prompt
    public void openNoWorkoutsErrorPrompt() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Go and create one!\nHit the save button to access it here.");
        builder.setTitle("No Saved Workouts");
        builder.setPositiveButton("Home", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(SavedWorkoutActivity.this, HomeActivity.class);
                intent.putExtra("USER", username);
                startActivity(intent);
                dialog.dismiss();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Delete workout on database (Will change to workout object once WorkoutActivity is changed)
    private void writeDeleteSavedWorkout(Workout wo) {
        //TODO: Add db call to remove workout once repository is working
        rp.removeWorkout(username, wo);
    }

    /*---- Queries ----*/

    private List<Workout> querySavedWorkouts() {
        return rp.getWorkoutList(username);
    }

    public void getIntentValues(){
        Intent intent = getIntent();
        username = intent.getStringExtra("USER");
    }
}
