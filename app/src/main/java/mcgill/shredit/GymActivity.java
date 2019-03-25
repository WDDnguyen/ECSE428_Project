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
import android.widget.SearchView;
import android.widget.ListView;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mcgill.shredit.data.DataSourceStub;
import mcgill.shredit.data.Repository;
import mcgill.shredit.model.*;

public class GymActivity extends AppCompatActivity {

    ListView search_gym;
    ArrayAdapter<String> adapter;
    String username;
    List<Gym> listOfGyms;
    List<String> gymNames;

    Repository rp = Repository.getInstance(this);
    //DataSourceStub dss = new DataSourceStub();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym);
        getIntentValues();

        //addTestData();

        // Search bar
        search_gym = findViewById(R.id.search_gym);
        listOfGyms = queryGyms(); //Made final for now so that onItemClick can access this ArrayList. May need to find alternative solution

        gymNames = new ArrayList<>();
        for (Gym g : listOfGyms) {
            gymNames.add(g.getName());
        }

        adapter = new ArrayAdapter<String>(
                GymActivity.this,
                android.R.layout.simple_list_item_1,
                gymNames
        );

        // ListArray listeners
        search_gym.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //onGymConfirmClick(arg1);
                Intent intent = new Intent(getApplication(), GymPresetActivity.class);
                intent.putExtra("USER", username);
                intent.putExtra("item_gym_name", gymNames.get(position)); //pass the gym name onto the next activity
                intent.putExtra("GYM_OBJECT", (Serializable) listOfGyms.get(position));

                startActivity(intent);
            }
        });

        search_gym.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_gym_menu, menu);
        MenuItem item = menu.findItem(R.id.search_gym);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

//    public void addTestData() {
//        dss.addUser(username, "123");
//        dss.addUser("public", "");
//        Equipment e1 = new Equipment("Squat Rack");
//        Equipment e2 = new Equipment( "Row Machine");
//        Equipment e3 = new Equipment("Power Rack");
//        Gym g1 = new Gym("McGill Fitness Center");
//        Gym g2 = new Gym("Econofitness St Catherine");
//        Gym g3 =  new Gym( "Econofitness Atwater");
//        g1.addEquipment(e1);
//        g1.addEquipment(e2);
//        g1.addEquipment(e3);
//        g2.addEquipment(e1);
//        g2.addEquipment(e2);
//        g2.addEquipment(e3);
//        g3.addEquipment(e1);
//        g3.addEquipment(e2);
//        g3.addEquipment(e3);
//        dss.addGym("public", g1);
//        dss.addGym("public", g2);
//        dss.addGym("public", g3);
//    }

    // TODO query gym data
    private List<Gym> queryGyms() {
//        String[] array = {"McGill Fitness Center", "Econofitness St Catherine", "Econofitness Atwater"};
//        ArrayList<String> gyms = new ArrayList<>(Arrays.asList(array));

        List<Gym> publicGyms = rp.getGymList("public");
        List<Gym> userGyms = rp.getGymList(username);
        System.out.println(userGyms);
        System.out.println(username);
        List<Gym> retGyms = new ArrayList<>();
        if (!publicGyms.isEmpty()) {
            for (Gym g : publicGyms) {
                retGyms.add(g);
            }
        }
        if(!userGyms.isEmpty()) {
            for (Gym g : userGyms) {
                retGyms.add(g);
            }
        }
        return retGyms;
    }

    public void getIntentValues(){
        Intent intent = getIntent();
        username = intent.getStringExtra("USER");
    }
}
