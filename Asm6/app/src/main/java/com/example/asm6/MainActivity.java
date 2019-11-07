package com.example.asm6;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private EditText firstname;
    private EditText lastname;
    private EditText phone;
    private EditText edu;
    private EditText hobby;
    private EditText searchField;

    private TextView searchResult;

    Button submitBtn;
    Button searchBtn;

    Vector<Contact> phoneCatalog = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstname = (EditText) findViewById(R.id.firstName);
        lastname = (EditText) findViewById(R.id.lastname);
        phone = (EditText) findViewById(R.id.phone);
        edu = (EditText) findViewById(R.id.edu);
        hobby = (EditText) findViewById(R.id.hobby);
        searchField = (EditText) findViewById(R.id.searchField);

        submitBtn = (Button) findViewById(R.id.submitBtn);
        searchBtn = (Button) findViewById(R.id.searchBtn);

        searchResult = (TextView) findViewById(R.id.searchResult);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean feedback = false;
                if (firstname.getText().length() == 0) {
                    firstname.setBackgroundColor(Color.rgb(254, 150, 150));
                    feedback = true;
                }
                if (lastname.getText().length() == 0) {
                    lastname.setBackgroundColor(Color.rgb(254, 150, 150));
                    feedback = true;
                }
                if (phone.getText().length() == 0) {
                    phone.setBackgroundColor(Color.rgb(254, 150, 150));
                    feedback = true;
                }
                if (edu.getText().length() == 0) {
                    edu.setBackgroundColor(Color.rgb(254, 150, 150));
                    feedback = true;
                }
                if (hobby.getText().length() == 0) {
                    hobby.setBackgroundColor(Color.rgb(254, 150, 150));
                    feedback = true;
                }
                if (feedback)
                    Toast.makeText(getBaseContext(), "Missing data!", Toast.LENGTH_SHORT).show();
                else {
                    Contact newContact = new Contact(firstname.getText().toString(),
                            lastname.getText().toString(),
                            phone.getText().toString(),
                            edu.getText().toString(),
                            hobby.getText().toString());

                    phoneCatalog.add(newContact);
                    Toast.makeText(getBaseContext(), "Contact added", Toast.LENGTH_SHORT).show();
                }
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchField.getText().length() == 0) {
                    searchField.setBackgroundColor(Color.rgb(254, 150, 150));
                } else {
                    String searchString = "";
                    for (Contact e : phoneCatalog) {
                        if (e.Search(searchField.getText().toString())) {
                            searchString += e.toString() + "\n";
                        }
                    }
                    if (searchString.length() >0) searchResult.setText(searchString); else
                        searchResult.setText("Not Found !!!");
                }
            }
        });
    }
}
