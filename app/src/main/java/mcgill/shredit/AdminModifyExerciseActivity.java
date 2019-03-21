package mcgill.shredit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminModifyExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_modify_exercise);
    }

    public void onDoneClick(View view){
        Intent intent = new Intent(this, AdminHomeActivity.class);
        startActivity(intent);
    }
}
