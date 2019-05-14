package com.example.projectuts_00000017639;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String PREFERENCES_FILENAME = "loginpage";
    private static final int PREFERENCES_MODE = Context.MODE_PRIVATE;
    private static final String KEY_USERNAME = "user";
    private static final String KEY_PASS= "pass";
    EditText edtUserName;
    EditText edtPassword;

    boolean isMenu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //Context context = getApplicationContext();
        edtUserName = findViewById(R.id.username);
        edtPassword = findViewById(R.id.password);

        Button btnLogin = findViewById(R.id.loginbutton);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SharedPreferences sharedPreferences =
                        getSharedPreferences(PREFERENCES_FILENAME, PREFERENCES_MODE);
                SharedPreferences.Editor editor =sharedPreferences.edit();

                editor.putString(KEY_USERNAME, "user");
                editor.putString(KEY_PASS, "useruser");

                editor.commit();

                String uname1 = sharedPreferences.getString("KEY_USERNAME", "user");
                String pass1 = sharedPreferences.getString("KEY_PASS", "useruser");

                String uname = edtUserName.getText().toString();
                String pass = edtPassword.getText().toString();


                if (uname.equals(uname1) && pass.equals(pass1)){
                    Log.d("Login Success", "onClick: ");
                    Toast.makeText(MainActivity.this, "Login Successful! Welcome!", Toast.LENGTH_SHORT).show();
                    openMenuActivity();
                }
                else{
                    Log.d("Login Failed", "onClick: ");
                    Toast.makeText(MainActivity.this, "Login failed, check again your username and password.", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }



    public void openMenuActivity(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
