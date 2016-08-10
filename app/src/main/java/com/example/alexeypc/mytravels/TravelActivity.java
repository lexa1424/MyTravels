package com.example.alexeypc.mytravels;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class TravelActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    String[] latString;
    String[] lngString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);

        Bundle bundle = getIntent().getExtras();
        String date = bundle.getString("date");

        DataBase db = DataBase.getInstance(this);
        db.open();
        latString = db.getCoord(date, DataBase.COLUMN_LAT).split(",");
        lngString = db.getCoord(date, DataBase.COLUMN_LNG).split(",");
        db.close();

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.travelMap);
        mapFragment.getMapAsync(this);
    }

    private void drawMotions(String[] lat, String[] lng){
        LatLng startPosition = null;
        LatLng endPosition = null;

        for(int i = 0; i < lat.length; i++){
            if(i == 0) {
                startPosition = new LatLng(Double.valueOf(lat[i]), Double.valueOf(lng[i]));
            }
            endPosition = new LatLng(Double.valueOf(lat[i]), Double.valueOf(lng[i]));
            Drawer.drawLineFragment(startPosition, endPosition, mMap);
            Drawer.addMarker(endPosition, mMap);
            startPosition = endPosition;
        }

        Drawer.changeCameraPosition(endPosition, mMap);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        drawMotions(latString, lngString);
    }
}
