package mcgill.shredit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mcgill.shredit.data.DataSourceStub;


public class LoginActivity extends AppCompatActivity {

    private Button buttonRegister;
    private EditText editTextUsername;
    private EditText editTextPassword;
    //Repository rp = Repository.getInstance();
    DataSourceStub dss = new DataSourceStub();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonRegister = (Button)findViewById(R.id.login_button);
        editTextUsername = (EditText)findViewById(R.id.username);
        editTextPassword = (EditText)findViewById(R.id.password);

        refreshData();

        // TODO: to remove when repository can fetch from user table
        dss.addUser("abc", "123");

    }

    public void refreshData () {
        editTextUsername.setText("");
        editTextPassword.setText("");
    }

    public boolean loginUser () {
        final String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter username and password",Toast.LENGTH_SHORT).show();

            return false;
        }
        else if(TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter username ",Toast.LENGTH_SHORT).show();

            return false;
        }
        else if(TextUtils.isEmpty(password) && !TextUtils.isEmpty(username)){
            Toast.makeText(this, "Please enter password",Toast.LENGTH_SHORT).show();

            return false;
            // TODO: replace dss when repository is ready
        } else if (!dss.checkPassword(username,password)){
            Toast.makeText(this, "Invalid username or password",Toast.LENGTH_SHORT).show();
            return false;
        }

        else {
            return true;
        }

    }

    public void onLoginClick(View view){
        if (view == buttonRegister){
            if(loginUser()) {
                Intent intent;
                if (editTextUsername.getText().toString().contains("@admin")){
                    // go to admin home view
                    intent = new Intent(this, AdminHomeActivity.class);
                } else {
                    // any other user go to user home view
                    intent = new Intent(this, HomeActivity.class);
                }

                startActivity(intent);
            }
        }
    }
    
    public boolean signUpUser ()throws Exception  {
        final String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter username and password",Toast.LENGTH_SHORT).show();

            return false;
        }
        else if(TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter username ",Toast.LENGTH_SHORT).show();

            return false;
        }
        else if(TextUtils.isEmpty(password) && !TextUtils.isEmpty(username)){
            Toast.makeText(this, "Please enter password",Toast.LENGTH_SHORT).show();

            return false;
        }

        else if(!dss.checkPassword(username,password)){// register
            // replace when RP can write to User table
            dss.addUser(username,password);
            Toast.makeText(this, "Register Successful",Toast.LENGTH_SHORT).show();
            return true;
        }
        else{
            Toast.makeText(this, "Username already exists",Toast.LENGTH_SHORT).show();
            return false;
        }

    }
    
    public void onSignUpClick(View view)throws Exception {
            if(signUpUser()) {
                Toast.makeText(this, "Register Successful. Click LOGIN to log in",Toast.LENGTH_SHORT).show();
            }
    }
}
