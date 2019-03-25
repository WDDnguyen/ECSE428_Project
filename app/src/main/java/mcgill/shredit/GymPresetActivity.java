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

    //Repository rp = Repository.getInstance();
    DataSourceStub dss = new DataSourceStub();

    String gymName;
    String username;
    Gym selectedGym;
    List<Equipment> gymEquipmentArrayList;
    List<String> gymEquipmentNames;

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
                gymName = textViewData;
            }
        }
        else
        {
            textViewData = (String) savedInstanceState.getSerializable("item_gym_name");
        }
        Intent intent = getIntent();
        username = intent.getStringExtra("USER");
        selectedGym = (Gym) intent.getSerializableExtra("GYM_OBJECT");
        ((TextView)findViewById(R.id.gymPresetTextView)).setText(textViewData);


        //Get the gym's presets (equipment list) and display in a ListView
        gymEquipmentArrayList = queryGymEquipment();
        gymEquipmentNames = new ArrayList<>();

        for (Equipment eq : gymEquipmentArrayList) {
            gymEquipmentNames.add(eq.getName());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                GymPresetActivity.this,
                android.R.layout.simple_list_item_1,
                gymEquipmentNames
        );

        ListView gymPreset_ListView = (ListView) findViewById(R.id.gymPresetListView);
        gymPreset_ListView.setAdapter(adapter);
    }

    private List<Equipment> queryGymEquipment() {
        return selectedGym.getEquipments();

    }

    // Moved here from GymActivity.java
    // TODO : Need to pass actual generated gym equipment data to MuscleGroupActivity
    public void onGymConfirmClick(View view) {

        if(!gymEquipmentArrayList.isEmpty()){
            Intent intent = new Intent(this, MuscleGroupActivity.class);
            intent.putExtra("EQUIPMENT_LIST", (Serializable) gymEquipmentArrayList);
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
