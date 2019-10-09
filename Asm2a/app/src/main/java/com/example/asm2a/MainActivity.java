package com.example.asm2a;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {
    String tag = "EVH_Demo: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(tag, tag + "onCreate()" + getDateTime());
    }

    protected void onStart() {
        super.onStart();
        Log.d(tag, tag + "onStart()" + getDateTime());
    }

    protected void onRestart() {
        super.onRestart();
        Log.d(tag, tag + "onReStart()" + getDateTime());
    }

    protected void onResume() {
        super.onResume();
        Log.d(tag, tag + "onResume()" + getDateTime());
    }

    protected void onPause() {
        super.onPause();
        Log.d(tag, tag + "onPause()" + getDateTime());
    }

    protected void onStop() {
        super.onStop();

        Log.d(tag, tag + "onStop()" + getDateTime());
    }

    protected void onDestroy() {
        super.onDestroy();

        Log.d(tag, tag + "onDestroy()" + getDateTime());
    }

    protected String getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String currentDateandTime = sdf.format(new Date());
        return " at " + currentDateandTime;
    }
}