package mcgill.shredit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MuscleGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_group);
    }

    // TO DO : Need to pass filtered equipment list to workout activity
    public void onMuscleGroupConfirmClick(View view) {
        Intent intent = new Intent(this, WorkoutActivity.class);
        startActivity(intent);
    }
}
