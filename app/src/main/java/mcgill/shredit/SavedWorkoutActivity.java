package mcgill.shredit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class SavedWorkoutActivity extends AppCompatActivity {

    ListView savedWorkoutView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_workouts);
        savedWorkoutView = findViewById(R.id.saved_workout_view);

        final ArrayList<String> savedWorkoutList = querySavedWorkouts();
        adapter = new ArrayAdapter<String>(
                SavedWorkoutActivity.this,
                android.R.layout.simple_list_item_1,
                savedWorkoutList
        );



    }

    private ArrayList<String> querySavedWorkouts(){
        String[] array = {"Chest and Back", "Abs and Legs", "Shoulders and Arms"};
        ArrayList<String> savedWorkouts = new ArrayList<>(Arrays.asList(array));
        return savedWorkouts;
    }
}
