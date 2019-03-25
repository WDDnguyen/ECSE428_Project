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

import mcgill.shredit.model.*;

public class GymActivity extends AppCompatActivity {

    ListView search_gym;
    ArrayAdapter<String> adapter;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym);
        getIntentValues();

        // Search bar
        search_gym = findViewById(R.id.search_gym);
        final ArrayList<String> arrayGyms = queryGyms(); //Made final for now so that onItemClick can access this ArrayList. May need to find alternative solution

        adapter = new ArrayAdapter<String>(
                GymActivity.this,
                android.R.layout.simple_list_item_1,
                arrayGyms
        );

        // ListArray listeners
        search_gym.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //onGymConfirmClick(arg1);
                Intent intent = new Intent(getApplication(), GymPresetActivity.class);
                intent.putExtra("USER", username);
                intent.putExtra("item_gym_name", arrayGyms.get(position)); //pass the gym name onto the next activity

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

    // TODO query gym data
    private ArrayList<String> queryGyms() {
        String[] array = {"McGill Fitness Center", "Econofitness St Catherine", "Econofitness Atwater"};
        ArrayList<String> gyms = new ArrayList<>(Arrays.asList(array));
        return gyms;
    }

    public void getIntentValues(){
        Intent intent = getIntent();
        username = intent.getStringExtra("USER");
    }
}
