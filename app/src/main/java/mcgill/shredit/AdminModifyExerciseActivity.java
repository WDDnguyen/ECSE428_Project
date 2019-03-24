package mcgill.shredit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import mcgill.shredit.data.Repository;
import mcgill.shredit.model.Equipment;
import mcgill.shredit.model.Exercise;

public class AdminModifyExerciseActivity extends AppCompatActivity {

    Repository rp = Repository.getInstance();
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_modify_exercise);

        getIntentValues();

        ArrayList<Exercise> exercises = createMockExercises();

        ListView exerciseList = (ListView) findViewById(R.id.exercise_list);
        ArrayList<String> exerciseName = new ArrayList<>();
        for (Exercise exercise : exercises){
            exerciseName.add(exercise.getName());
        }

        exerciseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // open popup on-click
//                //get workoutname
//                String exerciseName;
//                exerciseName = chosenExercises.get(position).getName();
//
//                randomWorkout(exerciseName);
            }
        });

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, exerciseName);

        exerciseList.setAdapter(adapter);

    }

    public void onDoneClick(View view){
        Intent intent = new Intent(this, AdminHomeActivity.class);
        intent.putExtra("USER", username);
        startActivity(intent);
    }

    public ArrayList<Exercise> createMockExercises(){
        ArrayList<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise("e1", "des1", "Forearms" ,new Equipment("None")));
        exercises.add(new Exercise("e2", "des2", "Forearms" ,new Equipment("None")));
        exercises.add(new Exercise("e3", "des3", "Back" ,new Equipment("None")));
        exercises.add(new Exercise("e4", "des4", "Abs" ,new Equipment("None")));
        exercises.add(new Exercise("e5", "des5", "Triceps" ,new Equipment("None")));
        return exercises;
    }

    public void getIntentValues(){
        Intent intent = getIntent();
        username = intent.getStringExtra("USER");
    }
}
