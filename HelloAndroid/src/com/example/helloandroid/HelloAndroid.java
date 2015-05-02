package com.example.helloandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HelloAndroid extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //以下代码适合非 layout 文件模式
        TextView tv = new TextView(this);
        tv.setText("Hello, Android!");
        setContentView(tv);
        //setContentView(R.layout.main);
    }
}