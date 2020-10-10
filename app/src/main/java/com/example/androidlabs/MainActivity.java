package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText email,password;
    Button button;
    SharedPreferences sp;
    String emailStr,passStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.editTextTextEmailAddress2);
        password = findViewById(R.id.editTextTextPassword);
        button = findViewById(R.id.button);





        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE );
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
                emailStr = email.getText().toString();
                passStr = password.getText().toString();

                SharedPreferences.Editor editor =sp.edit();

                editor.putString("email", emailStr );
                editor.putString("password", passStr);
                editor.commit();



            }
        });


    }
    public void onPause(){
        super.onPause();
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String emailS = sp.getString("email", "");
        String passwordS = sp.getString("password", " ");
        email.setText(emailS);
        password.setText(passwordS);
    }




}