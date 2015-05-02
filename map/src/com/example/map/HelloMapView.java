package com.example.map;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.app.Activity;
import android.os.Bundle;

public class HelloMapView  extends MapActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        MapView mapView;
        mapView= (MapView) findViewById(R.id.mapView);
        mapView.setBuiltInZoomControls(true);
        GeoPoint point = new GeoPoint(25800000,-80266667); // Miami City
        MapController controller = mapView.getController();
        controller.animateTo(point);
        controller.setZoom(3);
    }
    
    protected boolean isRouteDisplayed() {
    return false;
    }
}