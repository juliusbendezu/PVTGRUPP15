package com.dsv2019.pvt15.prepapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dsv2019.pvt15.prepapp.models.HealthClinic;
import com.dsv2019.pvt15.prepapp.models.Shelter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements
        OnMapReadyCallback
{
    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private ToggleButton toggleButton;
    private List<Shelter> shelterList;
    private List<HealthClinic> clinicList;
    private ClusterManager<Shelter> shelterClusterManager;
    private ClusterManager<HealthClinic> healthClinicClusterManager;

    //These two are used in connection with the customized info windows, to get a reference to the ClusterItem clicked
    private Shelter clickedShelter;
    private HealthClinic clickedHealthClinic;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        toggleButton = findViewById(R.id.map_toggle_button);
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            if (isChecked)
            {
                //removes the health clinic manager so we can set up the shelter manager
                healthClinicClusterManager.clearItems();
                healthClinicClusterManager.cluster();
                healthClinicClusterManager = null;

                setupShelterClustering();
            }
            else
            {
                //removes the shelter manager so we can set up the health clinic manager
                shelterClusterManager.clearItems();
                shelterClusterManager.cluster();
                shelterClusterManager = null;

                setupHealthClinicClustering();
            }
        });
        getLocationPermission();
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        if (mLocationPermissionsGranted)
        {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setMapToolbarEnabled(false);


            //This is here because of the temporary bug fix with Volley and loading in of Health Clinic objects.
            healthClinicClusterManager = new ClusterManager<>(this, googleMap);

            shelterList = generateShelterObjects();
            clinicList = generateHealthClinicObjects();
            setupShelterClustering();
        }
    }

    private void getDeviceLocation()
    {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try
        {
            if (mLocationPermissionsGranted)
            {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener()
                {
                    @Override
                    public void onComplete(@NonNull Task task)
                    {
                        if (task.isSuccessful() && task.getResult() != null)
                        {
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM);

                        }
                        else
                        {
                            Toast.makeText(MapActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e)
        {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom)
    {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void initMap()
    {
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapActivity.this);
    }

    private void getLocationPermission()
    {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                mLocationPermissionsGranted = true;
                initMap();
            }
            else
            {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
        else
        {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        mLocationPermissionsGranted = false;

        switch (requestCode)
        {
            case LOCATION_PERMISSION_REQUEST_CODE:
            {
                if (grantResults.length > 0)
                {
                    for (int i = 0; i < grantResults.length; i++)
                    {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED)
                        {
                            mLocationPermissionsGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionsGranted = true;
                    initMap();
                }
            }
        }
    }

    /* Reads in the shelters from the .csv file, turning them into Shelter objects, finally adding them to a list so they can be processed by the cluster manager.*/
    private List<Shelter> generateShelterObjects()
    {
        List<Shelter> shelterList = new ArrayList<>();

        InputStream is = getResources().openRawResource(R.raw.shelters_csv_file);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";

        try
        {
            while ((line = reader.readLine()) != null)
            {
                // Split the line into different tokens (using the comma as a separator).
                String[] tokens = line.split(",");

                String address = tokens[0];
                double latitude = Double.parseDouble(tokens[1]);
                double longitude = Double.parseDouble(tokens[2]);
                LatLng latLng = new LatLng(latitude, longitude);
                String numberOfOccupants = tokens[3];

                Shelter clusterItem = new Shelter(latLng, address, numberOfOccupants);
                shelterList.add(clusterItem);


            }

        } catch (IOException e1)
        {
            Log.e(TAG, "Error" + line, e1);
            e1.printStackTrace();
        }

        return shelterList;
    }

    /* Reads information from the Eniro API and creates HealthClinic objects, finally adding them to a list so they can be processed by the cluster manager.*/
    private List<HealthClinic> generateHealthClinicObjects()
    {
        List<HealthClinic> clinicList = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://api.eniro.com/cs/search/basic?profile=PVT15&key=2588305061743139325&country=se&version=1.1.3&search_word=akutmottagning&geo_area=Stockholm&from_list=1&to_list=100";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response ->
                {
                    try
                    {
                        JSONArray jsonArray = response.getJSONArray("adverts");

                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject healthClinic = jsonArray.getJSONObject(i);

                            JSONObject companyInfo = healthClinic.getJSONObject("companyInfo");
                            String companyName = companyInfo.getString("companyName");

                            JSONObject addressInfo = healthClinic.getJSONObject("address");
                            String address = addressInfo.getString("streetName");

                            JSONObject location = healthClinic.getJSONObject("location");
                            JSONArray coordinates = location.getJSONArray("coordinates");

                            /*Some locations don't have coordinates available so we only process those that have them*/
                            if (coordinates.length() > 0)
                            {
                                JSONObject coordinatesInfo = (JSONObject) coordinates.get(0);
                                double longitude = Double.parseDouble(coordinatesInfo.getString("longitude"));
                                double latitude = Double.parseDouble(coordinatesInfo.getString("latitude"));
                                LatLng latLng = new LatLng(latitude, longitude);

                                HealthClinic clinic = new HealthClinic(latLng, address, companyName);
                                clinicList.add(clinic);
                                healthClinicClusterManager.addItem(clinic);
                            }
                        }
                        Log.i(TAG, "" + clinicList.size());
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                }, error -> error.printStackTrace());

        requestQueue.add(request);

        return clinicList;
    }

    private void addShelterClusterItems()
    {
        shelterClusterManager.addItems(shelterList);
    }

    private void addHealthClinicClusterItems()
    {
        healthClinicClusterManager.addItems(clinicList);
    }

    private void setupShelterClustering()
    {
        shelterClusterManager = new ClusterManager<>(this, mMap);
        ShelterClusterRenderer<Shelter> clusterRenderer = new ShelterClusterRenderer<>(this, mMap, shelterClusterManager);
        shelterClusterManager.setRenderer(clusterRenderer);
        mMap.setOnCameraIdleListener(shelterClusterManager);
        mMap.setOnMarkerClickListener(shelterClusterManager);
        addShelterClusterItems();

        shelterClusterManager.setOnClusterItemClickListener(
                new ClusterManager.OnClusterItemClickListener<Shelter>()
                {
                    @Override
                    public boolean onClusterItemClick(Shelter shelter)
                    {

                        clickedShelter = shelter;

                        return false;
                    }
                });


        mMap.setInfoWindowAdapter(shelterClusterManager.getMarkerManager());
        mMap.setOnInfoWindowClickListener(shelterClusterManager);

        shelterClusterManager.getMarkerCollection().setOnInfoWindowAdapter(new GoogleMap.InfoWindowAdapter()
        {
            @Override
            public View getInfoWindow(Marker marker)
            {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View view = inflater.inflate(R.layout.custom_info_window_shelter, null);

                TextView address = view.findViewById(R.id.info_window_address);
                TextView occupants = view.findViewById(R.id.info_window_occupants);
                TextView latitude = view.findViewById(R.id.info_window_latitude);
                TextView longitude = view.findViewById(R.id.info_window_longitude);

                address.append(clickedShelter.getTitle());
                occupants.append(clickedShelter.getSnippet());
                latitude.append("" + clickedShelter.getLatitude());
                longitude.append("" + clickedShelter.getLongitude());


                return view;
            }

            @Override
            public View getInfoContents(Marker marker)
            {
                return null;
            }
        });


        shelterClusterManager.cluster();
    }

    private void setupHealthClinicClustering()
    {
        healthClinicClusterManager = new ClusterManager<>(this, mMap);
        HealthClinicClusterRenderer<HealthClinic> clusterRenderer = new HealthClinicClusterRenderer(this, mMap, healthClinicClusterManager);
        healthClinicClusterManager.setRenderer(clusterRenderer);
        mMap.setOnCameraIdleListener(healthClinicClusterManager);
        mMap.setOnMarkerClickListener(healthClinicClusterManager);
        addHealthClinicClusterItems();

        healthClinicClusterManager.setOnClusterItemClickListener(
                new ClusterManager.OnClusterItemClickListener<HealthClinic>()
                {
                    @Override
                    public boolean onClusterItemClick(HealthClinic clinic)
                    {

                        clickedHealthClinic = clinic;

                        return false;
                    }
                });

        mMap.setInfoWindowAdapter(healthClinicClusterManager.getMarkerManager());
        mMap.setOnInfoWindowClickListener(healthClinicClusterManager);

        healthClinicClusterManager.getMarkerCollection().setOnInfoWindowAdapter(new GoogleMap.InfoWindowAdapter()
        {
            @Override
            public View getInfoWindow(Marker marker)
            {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View view = inflater.inflate(R.layout.custom_info_window_healthclinic, null);

                TextView name = view.findViewById(R.id.info_window_clinic_name);
                TextView address = view.findViewById(R.id.info_window_clinic_address);
                TextView latitude = view.findViewById(R.id.info_window_clinic_latitude);
                TextView longitude = view.findViewById(R.id.info_window_clinic_longitude);

                name.append(clickedHealthClinic.getTitle());
                address.append(clickedHealthClinic.getSnippet());
                latitude.append("" + clickedHealthClinic.getLatitude());
                longitude.append("" + clickedHealthClinic.getLongitude());


                return view;
            }

            @Override
            public View getInfoContents(Marker marker)
            {
                return null;
            }
        });

        healthClinicClusterManager.cluster();


    }
}