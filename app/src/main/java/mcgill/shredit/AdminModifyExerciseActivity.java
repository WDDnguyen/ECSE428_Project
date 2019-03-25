package mcgill.shredit;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import mcgill.shredit.data.MuscleGroup;
import mcgill.shredit.data.Repository;
import mcgill.shredit.model.Equipment;
import mcgill.shredit.model.Exercise;

public class AdminModifyExerciseActivity extends AppCompatActivity {

    Repository rp = Repository.getInstance();
    String username;
    ArrayList<Exercise> exercises = new ArrayList<>();
    ArrayList<String> exerciseName = new ArrayList<>();
    ArrayList<Boolean> checkedExercises = new ArrayList<>();
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_modify_exercise);

        getIntentValues();

        ArrayList<Exercise> exercises = createMockExercises();
        exercises = createMockExercises();

        ListView exerciseList = (ListView) findViewById(R.id.exercise_list);

        for (Exercise exercise : exercises){
            exerciseName.add(exercise.getName());
        }

        for (int i = 0; i < exerciseName.size() ; i++){
            checkedExercises.add(false);
        }

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_single_choice
                , exerciseName);

        exerciseList.setAdapter(adapter);
        exerciseList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        exerciseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    public void onDeleteClick(View view){
        ListView listview = (ListView) findViewById(R.id.exercise_list);
        ArrayAdapter adapter = (ArrayAdapter) listview.getAdapter();
        int position = listview.getCheckedItemPosition();
        exerciseName.remove(position);
        exercises.remove(position);

        adapter.notifyDataSetChanged();

    }

    public void onAddClick(View view){
        Intent intent = new Intent(this, AdminAddNewExerciseActivity.class);
        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        ListView listview = (ListView) findViewById(R.id.exercise_list);
        ArrayAdapter adapter = (ArrayAdapter) listview.getAdapter();

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                exerciseName.add(result);
                adapter.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

    public void onDoneClick(View view){
        Intent intent = new Intent(this, AdminHomeActivity.class);
        intent.putExtra("USER", username);
        startActivity(intent);
    }

    public ArrayList<Exercise> createMockExercises(){
        ArrayList<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise("exercise1", "des1", "Forearms" ,new Equipment("None")));
        exercises.add(new Exercise("exercise2", "des2", "Forearms" ,new Equipment("None")));
        exercises.add(new Exercise("exercise3", "des3", "Back" ,new Equipment("None")));
        exercises.add(new Exercise("exercise4", "des4", "Abs" ,new Equipment("None")));
        exercises.add(new Exercise("exercise5", "des5", "Triceps" ,new Equipment("None")));
        exercises.add(new Exercise("exercise6", "des1", "Forearms" ,new Equipment("None")));
        exercises.add(new Exercise("exercise7", "des2", "Forearms" ,new Equipment("None")));
        exercises.add(new Exercise("exercise8", "des3", "Back" ,new Equipment("None")));
        exercises.add(new Exercise("exercise9", "des4", "Abs" ,new Equipment("None")));
        exercises.add(new Exercise("exercise10", "des5", "Triceps" ,new Equipment("None")));
        return exercises;
    }

    public void getIntentValues(){
        Intent intent = getIntent();
        username = intent.getStringExtra("USER");
    }
}
