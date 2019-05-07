package com.dsv2019.pvt15.prepapp;


import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.dsv2019.pvt15.prepapp.mapobject.BombShelter;
import com.dsv2019.pvt15.prepapp.mapobject.MapObject;
import com.dsv2019.pvt15.prepapp.mapobject.Position;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;

    private ImageButton vattenButton;
    private ImageButton skyddButton;
    private ImageButton akutButton;

    ArrayList<MapObject> mapObjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney and move the camera
        mMap = googleMap;
        LatLng stockholm = new LatLng(59, 18);
        mMap.addMarker(new MarkerOptions().position(stockholm).title("Marker in Sthlm"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(stockholm));

        Position pos = new Position(59.5, 18.5);
        BombShelter bm = new BombShelter(pos, "Hågavägen 95, 112 61 sthlm", 180);
        mapObjects.add(bm);
        Position posi = new Position(60, 19);
        BombShelter bmm = new BombShelter(posi, "Hågavägen 95, 112 61 sthlm", 180);
        mapObjects.add(bmm);
        Position posit = new Position(59.3, 18.7);
        BombShelter bmmm = new BombShelter(posit, "Hågavägen 95, 112 61 sthlm", 180);
        mapObjects.add(bmmm);
        Position positi = new Position(59.8, 18.9);
        BombShelter bmmmm = new BombShelter(positi, "Hågavägen 95, 112 61 sthlm", 180);
        mapObjects.add(bmmmm);

        vattenButton = findViewById(R.id.vatten);
        skyddButton = findViewById(R.id.skyddsrum);
        akutButton = findViewById(R.id.akutmottagning);
        vattenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // vad som ska hända om man klickar på vattenknapp
            }
        });

        akutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // vad som ska hända om man klickar på akutmottagningsknapp
            }
        });

        skyddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // vad som ska hända om man klickar på skyddsrum

                for(MapObject mo :mapObjects){
                    System.out.print(mapObjects);
                    if (mo instanceof BombShelter) {
                        LatLng marker = new LatLng(mo.getPos().getX(), mo.getPos().getY());
                        mMap.addMarker(new MarkerOptions().position(marker).title(mo.toString()));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
                    }}
                }


        });


    }
}
