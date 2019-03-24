package mcgill.shredit;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import mcgill.shredit.data.MuscleGroup;
import mcgill.shredit.model.Equipment;
import mcgill.shredit.model.Exercise;

public class MuscleGroupActivity extends AppCompatActivity {

    List<Equipment> equipments;
    TableLayout tl;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_group);

        getIntentValues();
        String[] muscleGroups = MuscleGroup.ALL;

        tl = (TableLayout) findViewById(R.id.muscle_group_table);
        tl.setColumnStretchable(1,true);
        generateMuscleGroupTable(muscleGroups);
    }

    // get intent content from GymActivity
    public void getIntentValues(){
        Intent intent = getIntent();
        equipments = (List<Equipment>) intent.getSerializableExtra("EQUIPMENT_LIST");
        username = intent.getStringExtra("USER");
    }

    // filter muscle groups based on user inputs
    public HashMap<String, Integer> filterMuscleGroups(){
        HashMap<String, Integer> filteredMuscleGroups = new HashMap<String, Integer>();
        for (int i = 0; i < tl.getChildCount(); i++){
            TableRow tableRow = (TableRow) tl.getChildAt(i);
            CheckBox muscleGroupCb = (CheckBox) tableRow.getChildAt(0);
            EditText textValue = (EditText) tableRow.getChildAt(1);
            int numberOfExercises = Integer.parseInt(textValue.getText().toString());

            if (muscleGroupCb.isChecked() && numberOfExercises > 0 ){
                filteredMuscleGroups.put(muscleGroupCb.getText().toString(), numberOfExercises);
            }
        }
        return filteredMuscleGroups;
    }

    // Dynamically fill TableLayout with muscle groups and exercises
    public void generateMuscleGroupTable(String[] muscleGroups){

        TableRow[] row = new TableRow[muscleGroups.length];

        for(int i = 0; i < muscleGroups.length; i++){

            String muscleGroupsName = muscleGroups[i];

            //Create the tablerows
            row[i] = new TableRow(this);
            row[i].setId(i+1);
            row[i].setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            // Create dynamically rows.
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText(muscleGroupsName);
            row[i].addView(cb);

            EditText decimalText = new EditText(getApplicationContext());
            decimalText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            decimalText.setText("0");
            row[i].addView(decimalText);

            tl.addView(row[i],new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        }
    }

    // sending  muscle groups, exercises and equipement list to workout activity
    public void onMuscleGroupConfirmClick(View view) {

        HashMap<String, Integer> filteredMuscleGroups = filterMuscleGroups();

        if(!filteredMuscleGroups.isEmpty()){
            Intent intent = new Intent(this, WorkoutActivity.class);
            intent.putExtra("MUSCLE_GROUP_EQUIPMENT_LIST", (Serializable) equipments);
            intent.putExtra("MUSCLE_GROUPS_HASHMAP", filteredMuscleGroups);
            intent.putExtra("CLASS", "MuscleGroupActivity");
            intent.putExtra("USER", username);
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "AT LEAST 1 MUSCLE GROUP AND ITS NUMBER OF EXERCISE MUST BE SET",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
