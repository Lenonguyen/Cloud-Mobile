package com.example.asm8;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class SecondActivity extends AppCompatActivity {
    Button btn_add;
    Button btn_update;
    Button btn_delete;
    Button btn_get_single;
    Button btn_get_all;

    EditText et_id;
    EditText et_firstname;
    EditText et_lastname;
    EditText et_hobby;
    EditText et_edu;
    EditText et_phone;

    TextView timeTextView;
    TextView summaryTextView;

    DBAdapter dbAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        dbAdapter = new DBAdapter(getApplicationContext());

        et_id = (EditText) findViewById(R.id.et_id);
        et_firstname = (EditText) findViewById(R.id.et_firstname);
        et_lastname = (EditText) findViewById(R.id.et_lastname);
        et_hobby = (EditText) findViewById(R.id.et_hobby);
        et_edu = (EditText) findViewById(R.id.et_edu);
        et_phone = (EditText) findViewById(R.id.et_phone);

        timeTextView = (TextView) findViewById(R.id.timeTextView);
        summaryTextView = (TextView) findViewById(R.id.summaryTextView);

        btn_add = (Button) findViewById(R.id.btn_add);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_get_all = (Button) findViewById(R.id.btn_get_all);
        btn_get_single = (Button) findViewById(R.id.btn_get_single);


        String data = "";
// Here we access the incoming Intent object
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            data = extras.getString("data");

        if (!"admin".equals(data)) {
            dbAdapter.open();
            //long id = Integer.parseInt(userName);
            Cursor cursor = dbAdapter.getContactByFirstName(data);

            et_id.setText(cursor.getString(0));
            et_firstname.setText(cursor.getString(1));
            et_lastname.setText(cursor.getString(2));
            et_phone.setText(cursor.getString(3));
            et_edu.setText(cursor.getString(4));
            et_hobby.setText(cursor.getString(5));

            String displayUser = "User ID: " + cursor.getString(0) + " --- First Name: " + cursor.getString(1) + " --- Last Login: " + cursor.getString(6);
            timeTextView.setText(displayUser);
        }
        btn_add.setOnClickListener(Add_contact);
        btn_update.setOnClickListener(Update_contact);
        btn_delete.setOnClickListener(Delete_contact);
        btn_get_all.setOnClickListener(Get_all_contact);
        btn_get_single.setOnClickListener(Get_contact);
    }

    private View.OnClickListener Add_contact = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (checkEmpty()) {
                Toast.makeText(getApplication(), getResources().getString(R.string.empty_fields_txt), Toast.LENGTH_LONG).show();
            } else {
                //Here we add a new customer data
                dbAdapter.open();
                String date = "" + new Date();
                long id = dbAdapter.addContact(Integer.parseInt(et_id.getText().toString()),
                        et_firstname.getText().toString(),
                        et_lastname.getText().toString(),
                        et_phone.getText().toString(),
                        et_edu.getText().toString(),
                        et_hobby.getText().toString(),
                        date);
                displayResult(id);
                //Here we close database connection
                dbAdapter.close();
            }
        }
    };
    private View.OnClickListener Update_contact = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (checkEmpty()) {
                Toast.makeText(getApplication(), getResources().getString(R.string.empty_fields_txt), Toast.LENGTH_LONG).show();
            } else {
                //Here we add a new customer data
                dbAdapter.open();
                long id = Integer.parseInt(et_id.getText().toString());
                String date = "" + new Date();
                if (dbAdapter.updateContact(id,
                        et_firstname.getText().toString(),
                        et_lastname.getText().toString(),
                        et_phone.getText().toString(),
                        et_edu.getText().toString(),
                        et_hobby.getText().toString(),
                        date))
                    Toast.makeText(getApplication(), getResources().getString(R.string.update_txt) + id + getResources().getString(R.string.success_txt), Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplication(), getResources().getString(R.string.update_txt) + id + getResources().getString(R.string.failure_txt), Toast.LENGTH_LONG).show();
                //Here we close database connection
                dbAdapter.close();
            }
        }
    };

    private View.OnClickListener Delete_contact = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (checkEmpty()) {
                Toast.makeText(getApplication(), getResources().getString(R.string.empty_fields_txt), Toast.LENGTH_LONG).show();
            } else {
                //Here we add a new customer data
                dbAdapter.open();
                long id= Integer.parseInt(et_id.getText().toString());
                if(dbAdapter.deleteContact(id))
                    Toast.makeText(getApplication(),  getResources().getString(R.string.delete_txt) + id + getResources().getString(R.string.success_txt), Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplication(), getResources().getString(R.string.delete_txt) + id + getResources().getString(R.string.failure_txt), Toast.LENGTH_LONG).show();
                //Here we close database connection
                dbAdapter.close();
            }
        }
    };

    private View.OnClickListener Get_contact = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (checkEmpty()) {
                Toast.makeText(getApplication(), getResources().getString(R.string.empty_fields_txt), Toast.LENGTH_LONG).show();
            } else {
                //Here we add a new customer data
                dbAdapter.open();
                long id= Integer.parseInt(et_id.getText().toString());
                Cursor cursor = dbAdapter.getContact(id);
                if(cursor.moveToFirst()) {
                    summaryTextView.setText(displayData(cursor));
                } else {
                    Toast.makeText(getApplication(), getResources().getString(R.string.customer_id_txt) + id + getResources().getString(R.string.not_found_txt), Toast.LENGTH_LONG).show();
                }
                //Here we close database connection
                dbAdapter.close();
            }
        }
    };

    private View.OnClickListener Get_all_contact = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dbAdapter.open();
            Cursor cursor = dbAdapter.getAllContacts();
            summaryTextView.setText("");
            if(cursor.moveToFirst()) {
                do {
                    summaryTextView.append(displayData(cursor) + "\n");
                } while(cursor.moveToNext());
            }
            //Here we close database connection
            dbAdapter.close();
        }
    };
    private String displayData(Cursor cursor) {
        return "ID: " + cursor.getString(0)
                + " -- FirstName:" + cursor.getString(1)
                + " -- LastName: " + cursor.getString(2)
                + " -- Phone Number: " + cursor.getString(3)
                + " -- Education Level: " + cursor.getString(4)
                + " -- Hobbies: " + cursor.getString(5)
                + " -- Last Login: " + cursor.getString(6);
    }

    private void displayResult(long id) {
        Toast.makeText(getApplication(), getResources().getString(R.string.returned_value_txt) + " " + id, Toast.LENGTH_LONG).show();
    }

    private boolean checkEmpty() {
        if (et_id.getText().length() == 0 || et_firstname.getText().length() == 0 || et_phone.getText().length() == 0
                || et_lastname.getText().length() == 0 || et_hobby.getText().length() == 0 || et_edu.getText().length() == 0) {
            Toast.makeText(getApplication(), getResources().getString(R.string.empty_fields_txt), Toast.LENGTH_LONG).show();
            return true;
        }
        else return false;
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String data = "";
// Here we access the incoming Intent object
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            data = extras.getString("data");
        if (!"admin".equals(data)) {
            dbAdapter.open();
            long id= Integer.parseInt(et_id.getText().toString());
            String date = "" + new Date();
            if(dbAdapter.updateContact(id,
                    et_firstname.getText().toString(),
                    et_lastname.getText().toString(),
                    et_phone.getText().toString(),
                    et_edu.getText().toString(),
                    et_hobby.getText().toString(),
                    date))
                Toast.makeText(getApplication(), getResources().getString(R.string.update_txt) + id +getResources().getString(R.string.success_txt), Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplication(), getResources().getString(R.string.update_txt) + id + getResources().getString(R.string.failure_txt), Toast.LENGTH_LONG).show();
            //Here we close database connection
            dbAdapter.close();
        }
    }
}