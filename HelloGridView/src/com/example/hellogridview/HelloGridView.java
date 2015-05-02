package com.example.hellogridview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.view.*;

public class HelloGridView extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(HelloGridView.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }
}