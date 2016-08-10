package com.example.alexeypc.mytravels;

import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class Drawer {

    public static void drawLineFragment(LatLng startPosition, LatLng endPosition, GoogleMap mMap){
        PolylineOptions polylineOptions = new PolylineOptions()
                .add(startPosition)
                .add(endPosition)
                .color(Color.RED)
                .width(2);
        mMap.addPolyline(polylineOptions);
    }

    public static void addMarker(LatLng position, GoogleMap mMap){
        mMap.addMarker(new MarkerOptions()
                .position(position)
                .title("Marker")
                .draggable(true));
    }

    public static void changeCameraPosition(LatLng position, GoogleMap mMap){
        CameraPosition cameraPosition = new CameraPosition.Builder().target(position).zoom(14).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(cameraUpdate);
    }
}
