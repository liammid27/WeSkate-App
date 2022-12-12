package com.vegaschool.s10110678.weskate;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.vegaschool.s10110678.weskate.databinding.ActivityMaps2Binding;

public class MapsActivity2 extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    //Declaring all variables before use
    private GoogleMap mMap;
    private ActivityMaps2Binding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private CameraPosition cameraPosition;
    private Polyline currentPolyline;

    private boolean locationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private Location lastKnownLocation;
    private static final int DEFAULT_ZOOM = 15;
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);

    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    MarkerOptions place1, place2, place3, place4, place5, place6, place7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Checking for saved instance of last position and camera position
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        //Binding map
        binding = ActivityMaps2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Getting fused location provider
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Setting skate locations on map
        place1 = new MarkerOptions().position(new LatLng(-33.9249, 18.4241)).title("You are Here");
        place2 = new MarkerOptions().position(new LatLng(-33.9268, 18.4212)).title("Half-Pipe");
        place3 = new MarkerOptions().position(new LatLng(-33.9279, 18.4244)).title("Skate Park");
        place4 = new MarkerOptions().position(new LatLng(-33.9242, 18.4296)).title("Long Route");
        place5 = new MarkerOptions().position(new LatLng(-33.9285, 18.4232)).title("Rail");
        place6 = new MarkerOptions().position(new LatLng(-33.9278, 18.4278)).title("Curb");
        place7 = new MarkerOptions().position(new LatLng(-33.9253, 18.4229)).title("Jump");


    }

    @Override
    public void onMapReady (GoogleMap googleMap) {
        mMap = googleMap;
        Polyline currentPolyLine;

        //Adding skate locations as map markers
        mMap.addMarker(place1);
        mMap.addMarker(place2);
        mMap.addMarker(place3);
        mMap.addMarker(place4);
        mMap.addMarker(place5);
        mMap.addMarker(place6);
        mMap.addMarker(place7);

        //Moving camera to cape town
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(-33.9249,
                        18.4241), DEFAULT_ZOOM));


        //Adding locations to Url and fetching it
        String url = getURL(place1.getPosition(), place4.getPosition(), "driving");
        new FetchURL(MapsActivity2.this).execute(url, "driving");

        // Prompt the user for permission.
        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

    }

    private String getURL(LatLng origin, LatLng destination, String transportMode){

        //Formatting url properly based on map co ordinates
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + destination.latitude + "," + destination.longitude;
        String mode = "mode=" + transportMode;

        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";

        //Creating the url string and adding our parameters and api key
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=AIzaSyDjwwWGKpnr_IjFFVJlGN6qLXZ2dWvws4c";
        return url;
    }



    private void getLocationPermission() {
        //Checking for device permission to access location data
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //Checking if permission is granted and updating map
        locationPermissionGranted = false;
        if (requestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        //Updating map location if permission is granted
        if (mMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onTaskDone(Object... values) {
        //Drawing polylines on map based on co ordinates
        if(currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

}