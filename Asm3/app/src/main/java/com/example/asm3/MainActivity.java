package com.example.asm3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText lastName, firstName, phoneNumber, address, email, birthday;
    String Date;
    String tag="Screen Change: ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        lastName = (EditText)findViewById(R.id.lastName);
        firstName = (EditText)findViewById(R.id.firstName);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        address = (EditText) findViewById(R.id.address);
        email = (EditText) findViewById(R.id.email);
        birthday = (EditText) findViewById(R.id.birthday);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
            getTime();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
            getTime();
        }
    }
    public void getTime(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        Date = s.format(new Date());
        Log.d(tag, tag + Date);
    }
}
