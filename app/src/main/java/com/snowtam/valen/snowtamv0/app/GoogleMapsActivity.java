package com.snowtam.valen.snowtamv0.app;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.snowtam.valen.snowtamv0.R;
import com.snowtam.valen.snowtamv0.model.Snowtam;
import com.snowtam.valen.snowtamv0.model.Snowtam2;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //private GeoPoint location;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    //LocationRequest mLocationRequest;

    private int id;
    private ArrayList<Snowtam2> snowtams;
    private Snowtam2 selectedSnowtam;

    private TextView TV_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);


        snowtams = MainActivity.getSnowtams();
        Intent intent = getIntent();
        id = intent.getIntExtra(MainActivity.ID_SNOWTAM, 0);

        TV_name = (TextView) findViewById(R.id.name_airport);

        for(int i = 0; i < snowtams.size(); i++){
            if(id == snowtams.get(i).getId()){
                selectedSnowtam = snowtams.get(i);
                i = snowtams.size();
            }
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        String aff = selectedSnowtam.getOACI();
        if(!selectedSnowtam.getAirport().getNom().equals("undefine"))
                aff +=  " - " + selectedSnowtam.getAirport().getNom();
        TV_name.setText(aff);


    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng pos = new LatLng(selectedSnowtam.getAirport().getLat(), selectedSnowtam.getAirport().getLon());
        mMap.addMarker(new MarkerOptions().position(pos).title("Marker on " + selectedSnowtam.getOACI()));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
        float zoomLevel = 13;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, zoomLevel));
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
}
