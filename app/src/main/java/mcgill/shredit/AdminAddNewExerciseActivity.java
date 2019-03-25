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

import mcgill.shredit.data.MuscleGroup;
import mcgill.shredit.model.Equipment;
import mcgill.shredit.model.Exercise;

public class AdminAddNewExerciseActivity extends AppCompatActivity {

    String[] muscleGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_exercise);

        muscleGroups = MuscleGroup.ALL;

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

        String exName = exerciseText.getText().toString();
        String equipmentName = equipmentText.getText().toString();
        String muscleGroup = spinner.getSelectedItem().toString();

        Exercise exercise = new Exercise(exName, "description",muscleGroup, new Equipment(equipmentName));
        System.out.println("SUCCESSFULLY CREATED EXERCISE ");
        String result = exercise.getName();

        System.out.println("PUTTING RESULT " + result);

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",result);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();

    }

}
