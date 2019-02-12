package mcgill.shredit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onSearchGymsClick(View view) {
        //TODO: Link with List of Gyms Activity
    }

    public void onCustomizeGymClick(View view) {
        //TODO: Link with List of Equipment Activity
    }

    public void onLoadWorkoutClick(View view) {
        //TODO: Link with Load Workout Activity
    }

    public void onManageWorkoutsClick(View view) {
        //TODO: Link with Manage Workouts Activity
    }
}
