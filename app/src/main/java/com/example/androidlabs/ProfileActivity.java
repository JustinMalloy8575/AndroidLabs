package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    static final int  CAMERA_REQUEST_CODE = 1001;
    Button chat;
    ImageButton b;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.e(ACTIVITY_NAME, "IN FUNCTION"+ "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        chat = findViewById(R.id.buttonchat);
        tv = findViewById(R.id.editTextTextPersonName2);
        b = findViewById(R.id.imageButtonS);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String e = sp.getString("email", "");
        tv.setText(e);


        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentchat = new Intent(ProfileActivity.this,ChatRoomActivity.class);
                startActivity(intentchat);
            }
        });



        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(ACTIVITY_NAME, "IN FUNCTION"+ "onActivityResult()");
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            b.setImageBitmap(imageBitmap);
        }
    }
    protected void onDestroy(){
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "IN FUNCTION"+ "onDestroy()");
    }
    protected void onStop(){
        super.onStop();
        Log.e(ACTIVITY_NAME, "IN FUNCTION"+ "onStop()");
    }
    protected void onStart(){
        super.onStart();
        Log.e(ACTIVITY_NAME, "IN FUNCTION"+ "onStart()");
    }

    protected void onResume(){
        super.onResume();
        Log.e(ACTIVITY_NAME, "IN FUNCTION"+ "onResume()");
    }




}