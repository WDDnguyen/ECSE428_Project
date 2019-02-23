package mcgill.shredit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onSearchGymsClick(View view) {
        Intent intent = new Intent(this, GymActivity.class);
        startActivity(intent);
    }

    public void onLogOutClick(View view) {
        Toast.makeText(this, "User account information updated",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
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
