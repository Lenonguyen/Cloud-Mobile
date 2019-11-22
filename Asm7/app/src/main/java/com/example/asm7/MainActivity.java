package com.example.asm7;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Environment;
import android.graphics.Color;

import android.widget.LinearLayout;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.AutoCompleteTextView;
import android.widget.AdapterView.OnItemSelectedListener;

import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Vector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener{
    private EditText firstname;
    private EditText lastname;
    private EditText phone;
    private EditText edu;
    private EditText hobby;

    private TextView summaryTextView;

    private AutoCompleteTextView searchFieldF;
    private AutoCompleteTextView searchFieldL;
    private AutoCompleteTextView searchFieldP;

    //LinearLayout ly;

    Spinner colorPicker;
    //Spinner bgcolorPicker;

    Button submitBtn;
    Button saveInBtn;
    Button loadInBtn;
    Button saveExBtn;
    Button loadExBtn;
    Button saveUIBtn;

    Vector<Contact> phoneCatalog = new Vector<Contact>();

    ArrayList<String> suggestionsF = new ArrayList<String>();
    ArrayList<String> suggestionsL = new ArrayList<String>();
    ArrayList<String> suggestionsP = new ArrayList<String>();
    ArrayList<String> data = new ArrayList<String>();
    ArrayList<String> colors = new ArrayList<String>();
    ArrayAdapter<String> colorAdapter;

    private String selectedColor;
    private int textSize;
    private String prefsFileName;
    private SeekBar seekBar;
    private SharedPreferences sharedPreferences;
    private static final String FONT_SIZE = "font_size";
    private static final String TEXT_COLOR = "text_color";

    //THIS IS FOR FILE SAVING

    private static final int BLOCK_SIZE = 128;
    //Here we define file path on the internal memory
    String path;
    //Here we define full file name with its path
    String FileName = "phoneData.txt";
    File file;

    String sdCardPath, destPath;
    String dirName="Assignment7_data";
    // Here we define full file name with its path

    File sdCard, destPathFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        path = getFilesDir().getAbsolutePath() + "/";

        sdCard= Environment.getExternalStorageDirectory();
        sdCardPath=sdCard.getAbsolutePath();

        destPathFile=getApplicationContext().getExternalFilesDir(dirName);

        if(!destPathFile.exists()) {
            //Here we create the directory on the SD card
            destPathFile.mkdirs();
        }
        Toast.makeText(getApplicationContext(),
                destPathFile.getAbsolutePath() + " exist? " + destPathFile.exists() , Toast.LENGTH_LONG)
                .show();

        firstname = (EditText) findViewById(R.id.firstName);
        lastname = (EditText) findViewById(R.id.lastname);
        phone = (EditText) findViewById(R.id.phone);
        edu = (EditText) findViewById(R.id.edu);
        hobby = (EditText) findViewById(R.id.hobby);
        /*searchFieldF = (AutoCompleteTextView) findViewById(R.id.searchFieldF);
        searchFieldL = (AutoCompleteTextView) findViewById(R.id.searchFieldL);
        searchFieldP = (AutoCompleteTextView) findViewById(R.id.searchFieldP);*/
        summaryTextView = (TextView) findViewById(R.id.summaryTextView);

        submitBtn = (Button) findViewById(R.id.submitBtn);
        saveUIBtn = (Button) findViewById(R.id.saveUIbtn);
        saveInBtn = (Button) findViewById(R.id.saveInBtn);
        loadInBtn = (Button) findViewById(R.id.loadInBtn);
        saveExBtn = (Button) findViewById(R.id.saveExBtn);
        loadExBtn = (Button) findViewById(R.id.loadExBtn);

        saveInBtn.setOnClickListener(buttonSaveInternalOnClick);
        loadInBtn.setOnClickListener(buttonLoadInternalOnClick);
        saveExBtn.setOnClickListener(buttonSaveExternalOnClick);
        loadExBtn.setOnClickListener(buttonLoadExternalOnClick);
        saveUIBtn.setOnClickListener(buttonSaveUIOnClick);

/*        searchFieldF.setThreshold(1);
        searchFieldL.setThreshold(1);
        searchFieldP.setThreshold(1);*/

        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
// TODO Auto-generated method stub
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
// TODO Auto-generated method stub
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                textSize = progress;
                onTextSizeChange();
            }
        });
        colors.add("#000000");
        colors.add("#008577");
        colors.add("#00574B");
        colors.add("#D81B60");
        colors.add("#FFFFFF");

        colorPicker = (Spinner) findViewById(R.id.colorspinner);
        // Spinner click listener
        colorPicker.setOnItemSelectedListener(this);
        // Creating adapter for spinner
        colorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colors);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        colorPicker.setAdapter(colorAdapter);

        /*bgcolorPicker = (Spinner) findViewById(R.id.bgcolorspinner);
        bgcolorPicker.setOnItemSelectedListener(this);
        bgcolorPicker.setAdapter(colorAdapter);*/

        loadUISettings();

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
/*                    addToSuggestionFirstname(newContact);
                    addToSuggestionLastname(newContact);
                    addToSuggestionPhone(newContact);*/
                    phoneCatalog.add(newContact);
                    String summaryString ="";

                    for (Contact e : phoneCatalog) {
                        summaryString += e.toString() + "\n";
                    }

                    summaryTextView.setText(summaryString);
                    Toast.makeText(getBaseContext(), "Contact added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /*protected void addToSuggestionFirstname(Contact c) {
        suggestionsF.add(c.getFirstName() + " "+ c.getLastName() + " " + c.getPhoneNumber());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, suggestionsF);
        searchFieldF.setAdapter(adapter);
    }
    protected void addToSuggestionLastname(Contact c) {
        suggestionsL.add(c.getLastName() + " "+ c.getFirstName() + " " + c.getPhoneNumber());
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, suggestionsL);
        searchFieldL.setAdapter(adapter1);
    }
    protected void addToSuggestionPhone(Contact c) {
        suggestionsP.add(c.getPhoneNumber() + " "+ c.getFirstName() + " " + c.getLastName());
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, suggestionsP);
        searchFieldP.setAdapter(adapter2);
    }*/
    /*private void updatingArrayAdapter(String returnData) {
        String[] dataSplit = returnData.split("\n");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, dataSplit );
        searchFieldF.setAdapter(adapter);
    };*/
    private void loadUISettings() {

        SharedPreferences loadedSharedPrefs = getSharedPreferences(prefsFileName, MODE_PRIVATE);
//Here we set the TextView font size to the previously saved values
        float fontSize = loadedSharedPrefs.getFloat(FONT_SIZE, 14.0f);
        selectedColor = loadedSharedPrefs.getString(TEXT_COLOR, "#000000");
        seekBar.setProgress((int) fontSize);
        int spinnerPosition = colorAdapter.getPosition(selectedColor);
        colorPicker.setSelection(spinnerPosition);
        onTextSizeChange();
        onTextColorChange();
    }

    private void onTextSizeChange() {
        firstname.setTextSize(textSize);
        lastname.setTextSize(textSize);
        phone.setTextSize(textSize);
        edu.setTextSize(textSize);
        hobby.setTextSize(textSize);
    }

    private void onTextColorChange() {
        firstname.setTextColor(Color.parseColor(selectedColor));
        lastname.setTextColor(Color.parseColor(selectedColor));
        phone.setTextColor(Color.parseColor(selectedColor));
        edu.setTextColor(Color.parseColor(selectedColor));
        hobby.setTextColor(Color.parseColor(selectedColor));
    }
    /*private void onBGColorChange() {
        ly.setBackgroundColor(Color.parseColor(selectedColor));
    }*/
    private OnClickListener buttonSaveInternalOnClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            String summaryString ="";

            for (Contact e : phoneCatalog) {
                summaryString += e.toString() + "\n";
            }

            try {

                file = new File(FileName);
//Here we make the file readable by other applications too.
                file.setReadable(true, false);
                FileOutputStream fileOutputStream = openFileOutput(FileName, MODE_PRIVATE);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
//Here we write the text to the file
                outputStreamWriter.write(summaryString);
                outputStreamWriter.close();
                Toast.makeText(getApplicationContext(), getString(R.string.file_save_fb), Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                Toast.makeText(getApplicationContext(), getString(R.string.file_not_found_excp), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), getString(R.string.io_excp), Toast.LENGTH_LONG).show();
            }
        };
    };

    private OnClickListener buttonLoadInternalOnClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            FileInputStream fileInputStream;
            InputStreamReader inputStreamReader;
            try {
                fileInputStream = openFileInput(FileName);
                inputStreamReader = new InputStreamReader(fileInputStream);
                char[] inputBuffer = new char[BLOCK_SIZE];
                String fileContent="";
                int charRead;
                while((charRead = inputStreamReader.read(inputBuffer))>0) {
//Here we convert chars to string
                    String readString =String.copyValueOf(inputBuffer, 0, charRead);
                    fileContent+=readString;
//Here we re-initialize the inputBuffer array to remove its content
                    inputBuffer = new char[BLOCK_SIZE];
                }
//Here we set the text of the commentEditText to the one, which has been read
                //updatingArrayAdapter(fileContent);
                summaryTextView.setText(fileContent);
                Toast.makeText(getApplicationContext(), getString(R.string.file_load_fb), Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                Toast.makeText(getApplicationContext(), getString(R.string.file_not_found_excp), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), getString(R.string.io_excp), Toast.LENGTH_LONG).show();
            }
        };
    };

    private OnClickListener buttonSaveExternalOnClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            String summaryString ="";

            for (Contact e : phoneCatalog) {
                summaryString += e.toString() + "\n";
            }

            try {
                //Here we initialize the File object, which refers to
                //the comment file on the SD card under the specified directory
                File file = new File(destPathFile, FileName);
                //Here we overwrite the previous file content with the new content
                FileOutputStream fileOutputStream = new FileOutputStream(file, false);
                //Here we initialize the write stream
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                //Here we write the text to the file
                outputStreamWriter.write(summaryString);
                //Here we close the write stream
                outputStreamWriter.close();
                Toast.makeText(getApplicationContext(),
                        "Data was written to :" + file.getAbsolutePath(), Toast.LENGTH_LONG)
                        .show();
            } catch (FileNotFoundException e) {
                Toast.makeText(getApplicationContext(),
                        e.getMessage(), Toast.LENGTH_LONG)
                        .show();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),
                        e.getMessage(), Toast.LENGTH_LONG)
                        .show();
            }
        };
    };

    private OnClickListener buttonLoadExternalOnClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            FileInputStream fileInputStream;
            InputStreamReader inputStreamReader;
            try {

                file = new File(destPathFile, FileName);
                fileInputStream = new FileInputStream(file);
                inputStreamReader = new InputStreamReader(fileInputStream);
                char[] inputBuffer = new char[BLOCK_SIZE];
                String fileContent = "";
                int charRead;
                while ((charRead = inputStreamReader.read(inputBuffer)) > 0) {
                    // Here we convert chars to string
                    String readString = String.copyValueOf(inputBuffer, 0,
                            charRead);
                    fileContent += readString;
                    // Here we re-initialize the inputBuffer array to remove
                    // its content
                    inputBuffer = new char[BLOCK_SIZE];
                }
                // Here we set the text of the commentEditText to the one,
                // which has been read
                //updatingArrayAdapter(fileContent);
                summaryTextView.setText(fileContent);
                Toast.makeText(getApplicationContext(),
                        getString(R.string.file_load_fb), Toast.LENGTH_LONG)
                        .show();
            } catch (FileNotFoundException e) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.file_not_found_excp), Toast.LENGTH_LONG)
                        .show();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.io_excp), Toast.LENGTH_LONG)
                        .show();
            }
        };
    };
    private OnClickListener buttonSaveUIOnClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            //Here we initialize the SharedPreferences object
            sharedPreferences =getSharedPreferences(prefsFileName, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

//Here we save the values in the EditText view to preferences
            editor.putFloat(FONT_SIZE, firstname.getTextSize());
            editor.putString(TEXT_COLOR, selectedColor);

//Here we save the values
            editor.commit();
            Toast.makeText(getApplicationContext(), getString(R.string.save_fb), Toast.LENGTH_LONG).show();
        };
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        selectedColor = parent.getItemAtPosition(position).toString();
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + selectedColor, Toast.LENGTH_LONG).show();

        onTextColorChange();
        //onBGColorChange();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
