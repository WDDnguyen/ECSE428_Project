package mcgill.shredit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mcgill.shredit.model.Equipment;
import mcgill.shredit.data.Repository;

/**
 * @author Jiawei Ni <jiawei.ni@mail.mcgill.ca>
 */
public class CustomizeGymActivity extends AppCompatActivity {
    Repository rp;
    ListView listView;
    List<Equipment> arrayAllEquipment;
    List<Equipment> checkedEquipment;
    boolean[] equipmentIsChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeequipment);

        Repository rp = Repository.getInstance();
        listView = (ListView)findViewById(R.id.equipmentListView);
        //arrayAllEquipment = queryEquipments(); //Uses Repository's function to query database
        arrayAllEquipment = getMockEquipments(); //gets mock equipment list
        displayEquipments(arrayAllEquipment); //displays all equipment in list view

    }

    /**
     * Displays equipment in listView with checkboxes
     * @param allEquipments list of equipments to display in list
     */
    private void displayEquipments(List<Equipment> allEquipments){
        List<String> allEquipmentString = new ArrayList<String>();

        /* keeps track of which equipment is checked */
        equipmentIsChecked= new boolean[allEquipments.size()];
        for (int i=0;i<allEquipments.size();i++){ // all equipements
            equipmentIsChecked[i]=false; // preset to false(not checked)
        }

        /* Create a String List of equipment names*/
        for (Equipment temp : allEquipments) {
            allEquipmentString.add(temp.getName());
        }

        /* Display the equipment names in listview */
        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_checked, allEquipmentString);
        listView.setAdapter(adapter);
        /* listen for clicks on list items */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* if item in list is clicked
             * Check/uncheck the checkbox according to its current "isChecked" state */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
                CheckedTextView textview = (CheckedTextView)view;
                textview.setChecked(!textview.isChecked());
                equipmentIsChecked[position]=textview.isChecked();
            }
        });
    }

    /**
     * When "Submit Equipment" Button is clicked,
     * go to MuscleGroupActivity Page
     * with list of checked equipments
     */
    public void onSubmitEquipmentClick(View view) {
        boolean anyItemChecked=false; // for checking that at least 1 equipment is selected

        checkedEquipment = new ArrayList<Equipment>();
        /* search all equipment for checked ones */
        for(int i=0;i<equipmentIsChecked.length;i++){
            if(equipmentIsChecked[i]){
                anyItemChecked=true;
                checkedEquipment.add(arrayAllEquipment.get(i));
                Toast.makeText(this, "selected "+arrayAllEquipment.get(i).getName(),Toast.LENGTH_SHORT).show(); // test code, delete
            }
        }
        if(anyItemChecked){ // check if at least 1 equipment checked
            /* goto MuscleGroupActivity view with list of chosen equipments*/
            Intent intent = new Intent(getApplication(), MuscleGroupActivity.class);
            intent.putExtra("EQUIPMENT_LIST", (Serializable) checkedEquipment);
            startActivity(intent);
        }else{ //if no equipment checked, display error message
            Toast.makeText(this, "Please select at least 1 equipment",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Get equipment list containing all type of equipments in database
     * Uses Repository's function to query database,
     * Currently getEquipmentList() crashes app
     * @return List of all types of equipments.
     */
    private List<Equipment> queryEquipments() {
        List<Equipment> allEquipments = rp.getEquipmentList(); //Use Repository's function to query database,
        return allEquipments ; // return list of all equipments in database
    }

    /**
     * Generates mock equipments. Delete when database is implemented
     * @return mock equipment list
     */
    private List<Equipment> getMockEquipments() {
        Equipment mock0=new Equipment("none");
        Equipment mock1=new Equipment("treadmill");
        Equipment mock2=new Equipment("dumb bell");
        Equipment mock3=new Equipment("Row Machine");
        Equipment mock4=new Equipment("Squat Rack");
        Equipment mock5=new Equipment("protein milkshake");
        Equipment mock6=new Equipment("towel");
        Equipment mock7=new Equipment("shower");
        Equipment mock8=new Equipment("test");
        Equipment mock9=new Equipment("test2");
        List<Equipment> allEquipments=new ArrayList();
        allEquipments.add(mock0);
        allEquipments.add(mock1);
        allEquipments.add(mock2);
        allEquipments.add(mock3);
        allEquipments.add(mock4);
        allEquipments.add(mock5);
        allEquipments.add(mock6);
        allEquipments.add(mock7);
        allEquipments.add(mock8);
        allEquipments.add(mock9);
        return allEquipments ;
    }


}