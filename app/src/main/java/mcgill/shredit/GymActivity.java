package mcgill.shredit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GymActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym);
    }

    // TO DO : Need to pass gym's equipment list to muscle group activity
    public void onGymConfirmClick(View view) {
        Intent intent = new Intent(this, MuscleGroupActivity.class);
        startActivity(intent);
    }
}
