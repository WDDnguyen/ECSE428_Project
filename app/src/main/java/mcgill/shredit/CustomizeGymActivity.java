
package mcgill.shredit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mcgill.shredit.model.Equipment;
import mcgill.shredit.model.Gym;
import mcgill.shredit.data.Repository;


public class CustomizeGymActivity extends AppCompatActivity {
    Repository rp;
    ListView listView;
    List<Equipment> arrayAllEquipment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeequipment);

        Repository rp = Repository.getInstance();
        listView = (ListView)findViewById(R.id.equipmentListView);
        //arrayAllEquipment = queryEquipments();
        arrayAllEquipment = getMockEquipments();


    }

    private void displayEquipments(List<Equipment> allEquipments){

        for(int i = 0; i < allEquipments.size(); i++) {

            String EquipmentName = allEquipments.get(i).getName();


        }
    }

    public void onSubmitEquipmentClick(View view) {
        Intent intent = new Intent(getApplication(), MuscleGroupActivity.class);
        //intent.putExtra("item_gym_name", arrayGyms.get(position)); //pass the gym name onto the next activity
        startActivity(intent);
    }

    private List<Equipment> queryEquipments() {
        List<Equipment> allEquipments = rp.getEquipmentList();
        /*
        List<String> allEquipmentString = new List<String>;
        for (Equipment temp : allEquipments) {
            allEquipmentString.add(temp.getName());
        }
        return allEquipmentString ;*/
        return allEquipments ;
    }

    private List<Equipment> getMockEquipments() {

        Equipment mock0=new Equipment("none");
        Equipment mock1=new Equipment("treadmill");
        Equipment mock2=new Equipment("dumb bell");
        Equipment mock3=new Equipment("protein milkshake");
        List<Equipment> allEquipments=new ArrayList();
        allEquipments.add(mock0);
        allEquipments.add(mock1);
        allEquipments.add(mock2);
        allEquipments.add(mock3);
        return allEquipments ;
    }



}