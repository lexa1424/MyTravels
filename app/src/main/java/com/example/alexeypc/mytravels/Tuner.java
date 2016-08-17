package com.example.alexeypc.mytravels;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class Tuner {

    private static final CharSequence[] MAP_TYPE_ITEMS =
            {"Road Map", "Hybrid", "Satellite", "Terrain"};

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

    public static void showMapTypeSelectorDialog(final GoogleMap mMap, Context context) {
        // Prepare the dialog by setting up a Builder.
        final String fDialogTitle = "Select Map Type";
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(fDialogTitle);

        // Find the current map type to pre-check the item representing the current state.
        int checkItem = mMap.getMapType() - 1;

        // Add an OnClickListener to the dialog, so that the selection will be handled.
        builder.setSingleChoiceItems(
                MAP_TYPE_ITEMS,
                checkItem,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {
                        // Locally create a finalised object.
                        final GoogleMap map = mMap;
                        // Perform an action depending on which item was selected.
                        switch (item) {
                            case 1:
                                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                                break;
                            case 2:
                                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                                break;
                            case 3:
                                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                                break;
                            default:
                                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        }
                        dialog.dismiss();
                    }
                }
        );

        // Build the dialog and show it.
        AlertDialog fMapTypeDialog = builder.create();
        fMapTypeDialog.setCanceledOnTouchOutside(true);
        fMapTypeDialog.show();
    }
}
