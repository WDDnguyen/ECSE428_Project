package mcgill.shredit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import mcgill.shredit.data.Repository;


public class LoginActivity extends AppCompatActivity {

    private Button buttonRegister;
    private EditText editTextUsername;
    private EditText editTextPassword;
    Repository rp =new Repository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        buttonRegister = (Button)findViewById(R.id.login_button);
        editTextUsername = (EditText)findViewById(R.id.username);
        editTextPassword = (EditText)findViewById(R.id.password);

        refreshData();
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
        }

        else {
            return true;
        }

    }


    public void onLoginClick(View view){
        if (view == buttonRegister){
            if(loginUser()) {
                Intent intent = new Intent(this, HomeActivity.class);
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

        else if(rp.checkPassword(username,password)){// register
            Toast.makeText(this, "Register Successful",Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(this, "Username already exists",Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    
    public void onSignUpClick(View view)throws Exception {
            if(signUpUser()) {
                Toast.makeText(this, "Click LOGIN to log in",Toast.LENGTH_SHORT).show();
            }
    }
}
