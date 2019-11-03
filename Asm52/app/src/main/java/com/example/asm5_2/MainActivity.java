package com.example.asm5_2;
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
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
public class MainActivity extends AppCompatActivity {
    EditText userNametEditText = null;
    EditText passwordEditText = null;
    TextView summaryTextView = null;
    LayoutParams viewLayoutParams = null;
    TextView passwordTextView;
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
// Here we define a text view
        TextView userNameTextView = new TextView(this);
        userNameTextView.setText("User name");
        userNameTextView.setLayoutParams(viewLayoutParams);
        linearLayout.addView(userNameTextView);
// Here we define the edit text
        userNametEditText = new EditText(this);
        userNametEditText.setLayoutParams(viewLayoutParams);
        userNametEditText.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ((EditText) v).setBackgroundColor(Color.CYAN);
                return false;
            }
        });
        linearLayout.addView(userNametEditText);
// Here we define a text view
        passwordTextView = new TextView(this);
        passwordTextView.setText("Password");
        passwordTextView.setLayoutParams(viewLayoutParams);
        linearLayout.addView(passwordTextView);
        passwordTextView.setVisibility(View.INVISIBLE);
// Here we define the edit text
        passwordEditText = new EditText(this);
        passwordEditText.setLayoutParams(viewLayoutParams);
        passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        passwordEditText.setVisibility(View.INVISIBLE);
// Here we hide the content of the password field
        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordEditText.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ((EditText) v).setBackgroundColor(Color.rgb(150, 120, 200));
                return false;
            }
        });
        linearLayout.addView(passwordEditText);
        Button continueButton = new Button(this);
        continueButton.setText("Continue");
        continueButton.setLayoutParams(viewLayoutParams);
        continueButton.setOnClickListener(cbuttonClickListener);
        linearLayout.addView(continueButton);
        Button backButton = new Button(this);
        backButton.setText("Back");
        backButton.setLayoutParams(viewLayoutParams);
        backButton.setOnClickListener(bbuttonClickListener);
        linearLayout.addView(backButton);
// Here we define a text view
        summaryTextView = new TextView(this);
        summaryTextView.setLayoutParams(viewLayoutParams);
        summaryTextView.setVisibility(View.INVISIBLE);
        linearLayout.addView(summaryTextView);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        this.addContentView(linearLayout, linearLayoutParams);
    }
    private OnClickListener cbuttonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean feedback = false;
            if (passwordTextView.getVisibility() == View.INVISIBLE) {
                if (userNametEditText.getText().length() == 0) {
                    userNametEditText.setBackgroundColor(Color.rgb(254, 150, 150));
                    feedback = true;
                }
                if (feedback)
                    Toast.makeText(getBaseContext(), "Missing data!", Toast.LENGTH_SHORT).show();
                else {
                    userNametEditText.setBackground(null);
                    passwordEditText.setVisibility(View.VISIBLE);
                    passwordTextView.setVisibility(View.VISIBLE);
                }
            }
            if (passwordTextView.getVisibility() == View.VISIBLE) {
                if (userNametEditText.getText().length() == 0) {
                    userNametEditText.setBackgroundColor(Color.rgb(254, 150, 150));
                    feedback = true;
                }
                if (passwordEditText.getText().length() == 0) {
                    passwordEditText.setBackgroundColor(Color.rgb(254, 150, 150));
                    feedback = true;
                }
                if (feedback)
                    Toast.makeText(getBaseContext(), "Missing data!", Toast.LENGTH_SHORT).show();
                else {
                    userNametEditText.setBackground(null);
                    passwordEditText.setBackground(null);
                    summaryTextView.setVisibility(View.VISIBLE);
                    summaryTextView
                            .setText(userNametEditText.getText().toString() + "\n" + passwordEditText.getText().toString() +"\n"+getDateTime());
                }
            }
        }
    };
    private OnClickListener bbuttonClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (passwordEditText.getVisibility() == View.VISIBLE && summaryTextView.getVisibility() == View.INVISIBLE) {
                passwordEditText.setVisibility(View.INVISIBLE);
                passwordEditText.setText("");
                passwordTextView.setVisibility(View.INVISIBLE);
            }
            if (passwordEditText.getVisibility() == View.VISIBLE && summaryTextView.getVisibility() == View.VISIBLE) {
                summaryTextView.setVisibility(View.INVISIBLE);
                summaryTextView.setText("");

            }
        }
    };
    protected String getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String currentDateandTime = sdf.format(new Date());
        return " at " + currentDateandTime;
    }
}