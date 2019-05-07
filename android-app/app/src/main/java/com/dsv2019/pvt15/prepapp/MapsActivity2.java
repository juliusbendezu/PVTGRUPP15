package com.dsv2019.pvt15.prepapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dsv2019.pvt15.prepapp.mapobject.BombShelter;
import com.dsv2019.pvt15.prepapp.mapobject.Hospital;
import com.dsv2019.pvt15.prepapp.mapobject.MapObject;
import com.dsv2019.pvt15.prepapp.mapobject.Position;
import com.dsv2019.pvt15.prepapp.mapobject.Water;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private ImageButton vattenButton;
    private ImageButton skyddButton;
    private ImageButton akutButton;

    ArrayList<MapObject> mapObjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        getLocationPermission();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
        }
        initializeMarkers();


    }

    private void initializeMarkers() {

        LatLng stockholm = new LatLng(59, 18);
        mMap.addMarker(new MarkerOptions().position(stockholm).title("Marker in Sthlm"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(stockholm));

        Position pos = new Position(59.5, 18.5);
        BombShelter bm = new BombShelter(pos, "BS1-Hågavägen 95, 112 61 sthlm",180);
        mapObjects.add(bm);
        Position posi = new Position(60, 19);
        Hospital bmm = new Hospital(posi, "BS-Hågavägen 95, 112 61 sthlm", "Akutmottagning");
        mapObjects.add(bmm);
        Position posit = new Position(59.3, 18.7);
        Water w1 = new Water(posit, "V1-Hågavägen 95, 112 61 sthlm");
        mapObjects.add(w1);
        Position positi = new Position(59.8, 18.9);
        Water w = new Water(positi, "V-Hågavägen 95, 112 61 sthlm");
        mapObjects.add(w);

        vattenButton = findViewById(R.id.vatten);
        skyddButton = findViewById(R.id.skyddsrum);
        akutButton = findViewById(R.id.akutmottagning);


        vattenButton.setOnClickListener(new View.OnClickListener() {
            boolean visible = false;

            @Override
            public void onClick(View v) {
                // vad som ska hända om man klickar på skyddsrum
                if (visible == false) {
                    for (MapObject mo : mapObjects) {
                        if (mo instanceof Water) {
                            LatLng marker = new LatLng(mo.getPos().getX(), mo.getPos().getY());
                            mMap.addMarker(new MarkerOptions().position(marker).title(mo.toString()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
                        }
                    }
                    visible = true;
                } else {
                    mMap.clear();
                    //mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
                    visible = false;

                }
            }
        });

        akutButton.setOnClickListener(new View.OnClickListener() {
            boolean visible = false;
            @Override
            public void onClick(View v) {
                // vad som ska hända om man klickar på skyddsrum
                if (visible == false) {
                    for (MapObject mo : mapObjects) {
                        if (mo instanceof Hospital) {
                            LatLng marker = new LatLng(mo.getPos().getX(), mo.getPos().getY());
                            mMap.addMarker(new MarkerOptions().position(marker).title(mo.toString()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
                        }
                    }
                    visible = true;
                } else {
                    mMap.clear();
                    //mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
                    visible = false;

                }
            }
        });

        skyddButton.setOnClickListener(new View.OnClickListener() {
            boolean skyddsRumvisible = false;

            @Override
            public void onClick(View v) {
                // vad som ska hända om man klickar på skyddsrum
                if (skyddsRumvisible == false) {
                    for (MapObject mo : mapObjects) {
                        if (mo instanceof BombShelter) {
                            LatLng marker = new LatLng(mo.getPos().getX(), mo.getPos().getY());
                            mMap.addMarker(new MarkerOptions().position(marker).title(mo.toString()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
                        }
                    }
                    skyddsRumvisible = true;
                } else {
                    mMap.clear();
                            //mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
                        skyddsRumvisible = false;
                        }
                    }
        });
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGranted) {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM);

                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapsActivity2.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapsActivity2.this);
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }


}


