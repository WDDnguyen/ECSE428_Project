package mcgill.shredit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import mcgill.shredit.model.Gym;

public class GymActivity extends AppCompatActivity {

    ArrayList<Gym> gyms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym);
    }

    // TO DO : Create Gyms using mock data
    public void generateGyms(){

    }

    // TO DO : search function based based on input given from user.
    public void searchGym(){

    }

    // TO DO : Display gyms list based on the gym view design
    public void displayGyms(){

    }

    // TO DO : Pass gym's equipment list to muscle group Activity
    public void sendIntent(){

    }

}
