package mcgill.shredit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
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

        /*Create a String List of equipment names*/
        for (Equipment temp : allEquipments) {
            allEquipmentString.add(temp.getName());
            //equipmentIsChecked.add(i,false);
        }

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
            }
        });
    }

    /**
     * When "Submit Equipment" Button is clicked,
     * go to MuscleGroupActivity Page
     * with list of checked equipments
     */
    public void onSubmitEquipmentClick(View view) {
        Intent intent = new Intent(getApplication(), MuscleGroupActivity.class);
        //intent.putExtra("EQUIPMENT_LIST", (Serializable) gymEquipments);
        startActivity(intent);
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
     * Generates mock equipments. Delete when database is implemented                         (1)
     * @return mock equipment list
     */
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