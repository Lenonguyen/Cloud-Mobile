package com.example.asm4_3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    EditText num1, num2;
    Button addBtn, minBtn, mulBtn, divBtn, modBtn;
    TextView result;
    float rel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        addBtn = findViewById(R.id.addBtn);
        minBtn = findViewById(R.id.minBtn);
        mulBtn = findViewById(R.id.mulBtn);
        divBtn = findViewById(R.id.divBtn);
        modBtn = findViewById(R.id.modBtn);
        result = findViewById(R.id.result);

        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int number1 = Integer.parseInt(num1.getText().toString());
                int number2 = Integer.parseInt(num2.getText().toString());
                rel = number1 + number2;
                result.setText(String.valueOf(rel));
            }
        });
        minBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int number1 = Integer.parseInt(num1.getText().toString());
                int number2 = Integer.parseInt(num2.getText().toString());
                rel = number1 - number2;
                result.setText(String.valueOf(rel));
            }
        });
        mulBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int number1 = Integer.parseInt(num1.getText().toString());
                int number2 = Integer.parseInt(num2.getText().toString());
                rel = number1 * number2;
                result.setText(String.valueOf(rel));
            }
        });
        divBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int number1 = Integer.parseInt(num1.getText().toString());
                int number2 = Integer.parseInt(num2.getText().toString());
                rel = number1 / number2;
                result.setText(String.valueOf(rel));
            }
        });
        modBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int number1 = Integer.parseInt(num1.getText().toString());
                int number2 = Integer.parseInt(num2.getText().toString());
                rel = number1 % number2;
                result.setText(String.valueOf(rel));
            }
        });
    }


}
