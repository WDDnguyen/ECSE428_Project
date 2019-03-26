package mcgill.shredit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import mcgill.shredit.data.MuscleGroup;
import mcgill.shredit.data.Repository;
import mcgill.shredit.model.Equipment;
import mcgill.shredit.model.Exercise;

public class AdminAddNewExerciseActivity extends AppCompatActivity {

    Repository rp = Repository.getInstance(this);
    String[] muscleGroups;
    ArrayList<Exercise> dbExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_exercise);

        muscleGroups = MuscleGroup.ALL;
        dbExercises =  new ArrayList<>(rp.getExerciseList(null,null,null));

        Spinner spinner = (Spinner) findViewById(R.id.spinner_id);
        // Create an ArrayAdapter using the string array and a default spinner layout

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, muscleGroups);

        // Specify the layout to use when the list of choices appears
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(spinnerArrayAdapter);

    }

    public void onDoneAddingClick(View view){
        TextView exerciseText = (TextView) findViewById(R.id.exercise_text);
        TextView equipmentText = (TextView) findViewById(R.id.equipment_text);
        Spinner spinner = (Spinner) findViewById(R.id.spinner_id);
        boolean validExercise = true;

        String exName = exerciseText.getText().toString();
        String equipmentName = equipmentText.getText().toString();
        String muscleGroup = spinner.getSelectedItem().toString();

        Exercise newExercise = new Exercise(exName, "description",muscleGroup, new Equipment("None"));
        rp.addExercise(newExercise);

        if (exName.equals("") || exName == null  ){
            Toast.makeText(this, "Please enter an exercise name",Toast.LENGTH_SHORT).show();
            validExercise = false;

        } else if (equipmentName.equals("") || equipmentName == null){
            Toast.makeText(this, "Please enter an equipment name",Toast.LENGTH_SHORT).show();
            validExercise = false;
        } else {
            for (Exercise exercise : dbExercises){
                if (exercise.getName().equals(exName)){
                    validExercise = false;
                    break;
                }
            }
        }

        if (validExercise){

            String result = newExercise.getName();

            Intent returnIntent = new Intent();
            returnIntent.putExtra("result",result);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();

        } else {
            Toast.makeText(this, "Exercise already exist",Toast.LENGTH_SHORT).show();
        }


    }

}
