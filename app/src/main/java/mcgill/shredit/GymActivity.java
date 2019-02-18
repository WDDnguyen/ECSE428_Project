package mcgill.shredit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mcgill.shredit.model.Equipment;
import mcgill.shredit.model.Gym;

public class GymActivity extends AppCompatActivity {

    // TO DO : generate gym data; Remove this when completed.
    Gym selectedGym = new Gym("FlexBoi", 1);
    Equipment gymEquipment = new Equipment("treadmill", 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym);
    }

    // TO DO : Need to pass actual generated gym equipment data to MuscleGroupActivity
    public void onGymConfirmClick(View view) {
        selectedGym.addEquipment(gymEquipment);
        List<Equipment> gymEquipments = selectedGym.getEquipments();

        if(!gymEquipments.isEmpty()){
            Intent intent = new Intent(this, MuscleGroupActivity.class);
            intent.putExtra("EQUIPMENT_LIST", (Serializable) gymEquipments);
            startActivity(intent);
        }else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "A GYM HAS TO BE SELECTED",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
