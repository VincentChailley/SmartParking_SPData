package com.smartparkingdata.vincent.spdat;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MapsActivity extends Activity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {

        LatLng ESIEA = new LatLng(48.838111, 2.352913);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ESIEA, 13));

        map.addMarker(new MarkerOptions()
                .title("ESIEA")
//                .snippet("The most populous city in Australia.")
                .position(ESIEA));
        //Appel de la classe Modeles pour récupérer les données des points et afficher un nouveau marker sur la Map
        /*
        Modeles point = new Modeles();
        String rue_obj = Modeles.setRue_obj();
        int pointEstim = point.setEstimation_obj();
        double pointLat = point.setLatitude_obj();
        double pointLong = point.setLongitude_obj();

        LatLng rue_obj = new LatLng(pointLat,pointLong);
        map.addMarker(new MarkerOptions()
                .title(pointEstim)
                .position(rue_obj));
        */
    }
}