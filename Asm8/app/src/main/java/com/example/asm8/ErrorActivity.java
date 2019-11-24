package com.example.asm8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ErrorActivity extends AppCompatActivity {
    Button Backbtn;
    TextView errorTV = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        Backbtn = (Button) findViewById(R.id.Backbtn);
        errorTV = (TextView) findViewById(R.id.error);
        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String error = "";
// Here we access the incoming Intent object
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            error = extras.getString("error");
// Here we set the hint value of the edit text to the data
// sent by calling activity
        errorTV.append(" " + error);
    }
}
