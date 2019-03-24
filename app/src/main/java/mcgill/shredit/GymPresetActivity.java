package mcgill.shredit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mcgill.shredit.MuscleGroupActivity;
import mcgill.shredit.data.DataSourceStub;
import mcgill.shredit.model.Equipment;
import mcgill.shredit.model.Gym;


public class GymPresetActivity extends AppCompatActivity {
    String username;

    //Repository rp = Repository.getInstance();
    DataSourceStub dss = new DataSourceStub();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_preset);

        //Display name of gym the user clicked on previously in GymActivity
        String textViewData;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                textViewData = null;
            } else {
                textViewData = extras.getString("item_gym_name");
            }
        }
        else
        {
            textViewData = (String) savedInstanceState.getSerializable("item_gym_name");
        }
        Intent intent = getIntent();
        username = intent.getStringExtra("USER");
        ((TextView)findViewById(R.id.gymPresetTextView)).setText(textViewData);


        //Get the gym's presets (equipment list) and display in a ListView
        ArrayList<String> gymEquipmentArrayList = queryGymEquipment();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                GymPresetActivity.this,
                android.R.layout.simple_list_item_1,
                gymEquipmentArrayList
        );

        ListView gymPreset_ListView = (ListView) findViewById(R.id.gymPresetListView);
        gymPreset_ListView.setAdapter(adapter);
    }

    //TODO: query gym equipment data from database
    private ArrayList<String> queryGymEquipment() {
        String[] array = {"Squat Rack: 2", "Row Machine: 3", "Power Rack: 1"};
        ArrayList<String> gymEquipment = new ArrayList<>(Arrays.asList(array));
        return gymEquipment;
    }

    // Moved here from GymActivity.java
    // TODO : Need to pass actual generated gym equipment data to MuscleGroupActivity
    public void onGymConfirmClick(View view) {
        //dummy data
        Gym selectedGym = new Gym("FlexBoi");
        Equipment gymEquipment = new Equipment("treadmill");

        selectedGym.addEquipment(gymEquipment);
        List<Equipment> gymEquipments = selectedGym.getEquipments();

        if(!gymEquipments.isEmpty()){
            Intent intent = new Intent(this, MuscleGroupActivity.class);
            intent.putExtra("EQUIPMENT_LIST", (Serializable) gymEquipments);
            intent.putExtra("USER", username);
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Error loading this gym",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
