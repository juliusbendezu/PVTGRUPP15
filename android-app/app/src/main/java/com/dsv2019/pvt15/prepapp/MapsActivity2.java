package com.dsv2019.pvt15.prepapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.widget.EditText;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    public static final String TAG = "MyActivity";
    public static final int FASTEST_INTERVAL = 5000;
    public static final int UPDATE_INTERVAL = 10000;
    public static final int MY_REQUEST_FINE_LOCATION = 11;
    public static final int MY_REQUEST_INTERNET = 12;
    public static final int MY_REQUEST_COARSE_LOCATION = 13;
    public static final int MY_REQUEST_NETWORK_STATE = 14;
    public static final String DIALOG_TITLE = "Add a description for your run";
    public static final String DIALOG_OK = "OK";
    public static final String DIALOG_CANCEL = "Cancel";
    public static final String TYPE_KEY = "type";
    public static final String RECORD_KEY = "record";
    public static final String DISPLAY_KEY = "display";
    public static final String LAST_LOCATION_ERROR = "Error trying to get last GPS location";
    public static final String NO_PATH_ERROR = "No recorded path in chosen activity";
    public static final String DESCRIPTION_KEY = "description";
    public static final String DISTANCE_KEY = "distance";


    private PolylineOptions mPolylineOptions;
    private Polyline mPolyline;
    private ArrayList<LatLng> mLocations;
    private ArrayList<Location> mPath;

    private String mDescription;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private String mTypeOfActivity;
    private int mRunId;


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
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng stockholm = new LatLng(59, 18);
        mMap.addMarker(new MarkerOptions().position(stockholm).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(stockholm));
    }


}