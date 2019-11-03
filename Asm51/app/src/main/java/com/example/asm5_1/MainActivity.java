package com.example.asm5_1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    LayoutParams viewLayoutParams = null;
    Button vButton;
    Button verButton;
    int loc = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Here we define parameters for views
        viewLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        viewLayoutParams.leftMargin = 40;
        viewLayoutParams.rightMargin = 40;
        viewLayoutParams.topMargin = 10;
        viewLayoutParams.bottomMargin = 10;

        // Here we create the layout
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        //linearLayout.setBackgroundColor(Color.GREEN);
        // Button 1
        verButton = new Button(this);
        verButton.setText("Move in vertical direction");
        verButton.setLayoutParams(viewLayoutParams);
        verButton.setOnClickListener(verticalButtonClickListener);
        linearLayout.addView(verButton);
        vButton = new Button(this);
        vButton.setText("Set visibility");
        vButton.setLayoutParams(viewLayoutParams);
        vButton.setOnClickListener(visibilityButtonClickListener);
        linearLayout.addView(vButton);
        // Button 2

        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                2560/*LayoutParams.WRAP_CONTENT*/);
        //linearLayoutParams.height=2000;
        this.addContentView(linearLayout, linearLayoutParams);
    }
    private OnClickListener visibilityButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (verButton.getVisibility() == View.VISIBLE) {
                // Its visible
                verButton.setVisibility(View.INVISIBLE);
            } else {
                // Either gone or invisible
                verButton.setVisibility(View.VISIBLE);
            }

        }
    };
    private OnClickListener verticalButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (loc < 1700) {
                loc += 50;
                vButton.setTranslationY(loc);
            }
            else {
                loc = 0 ;
                vButton.setTranslationY(loc);
            }

        }
    };
}
