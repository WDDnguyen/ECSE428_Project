package mcgill.shredit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import mcgill.shredit.data.Repository;
import mcgill.shredit.model.Equipment;
import mcgill.shredit.model.Exercise;

public class AdminModifyExerciseActivity extends AppCompatActivity {

    Repository rp = Repository.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_modify_exercise);

        ArrayList<Exercise> exercises = createMockExercises();


    }

    public void onDoneClick(View view){
        Intent intent = new Intent(this, AdminHomeActivity.class);
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
}
