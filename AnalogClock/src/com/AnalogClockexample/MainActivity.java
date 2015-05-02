package com.AnalogClockexample;

import android.app.Activity;  
import android.os.Bundle;  
import android.widget.AnalogClock;  
import android.widget.DigitalClock;  
  
public class MainActivity extends Activity {  
    /** Called when the activity is first created. */  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.main);  
        DigitalClock digitalclock = (DigitalClock)findViewById(R.id.digitalclock01);  
        AnalogClock analogclock = (AnalogClock)findViewById(R.id.analogclock01);  
    }  
}  
