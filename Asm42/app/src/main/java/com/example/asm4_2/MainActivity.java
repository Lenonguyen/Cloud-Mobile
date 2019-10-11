package com.example.asm4_2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText username, comment;
    Button submitBtn;
    TextView result;
    Vector<String> v = new Vector<>();
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        comment = findViewById(R.id.comment);
        submitBtn = findViewById(R.id.submitBtn);
        result = findViewById(R.id.resultView);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(username) || isEmpty(comment)) {
                    Toast.makeText(getBaseContext(), "Some input is missing", Toast.LENGTH_SHORT).show();
                } else {
                    i++;
                    addComments(username.getText().toString(), comment.getText().toString());
                    String rel = fetchComments();
                    result.setText(rel);
                }
            }
        });
    }
    void addComments(String username, String comment) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String format = s.format(new Date());
        String commentNew = i + ", " + format + ", " + username + ", " + comment;
        this.v.add(commentNew);
    }
    String fetchComments() {
        StringBuilder s= new StringBuilder();
        for (int i=0; i<v.size();i++) {
            s.append(v.get(i)).append("\n");
        }
        return s.toString();
    }
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }
}
