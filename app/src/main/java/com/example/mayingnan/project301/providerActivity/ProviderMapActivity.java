package com.example.mayingnan.project301.providerActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mayingnan.project301.R;
import com.example.mayingnan.project301.User;
import com.example.mayingnan.project301.controller.UserListController;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import com.google.android.gms.location.FusedLocationProviderClient;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;

public class ProviderMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private com.example.mayingnan.project301.Task projectTask;
    private Task apiTask;

    public double latitude;
    public double longitude;

    private GoogleMap mMap;
    private String userName;

    public Criteria criteria;

    private Boolean mLocationPermissionGranted;

    private GoogleApiClient mGoogleApiClient;

    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=1;
    private static final int DEFAULT_ZOOM = 15;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private static final int M_MAX_ENTRIES = 5;
    private String[] mLikelyPlaceNames;
    private String[] mLikelyPlaceAddresses;
    private String[] mLikelyPlaceAttributions;
    private LatLng[] mLikelyPlaceLatLngs;

    public String bestProvider;

    private CameraPosition mCameraPosition;
    private static final String TAG = ProviderMapActivity.class.getSimpleName();

    private Location mLastKnownLocation;
    public LocationManager locationManager;

    private Location mLastLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    private final LatLng mDefaultLocation = new LatLng(53.5273, -113.5296);

    // Testing variables
    private ArrayList<Location> mockupTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationPermissionGranted = false;
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        final Intent intent = getIntent();
        userName = intent.getExtras().get("userName").toString();
        Log.d("Username", userName);

        setContentView(R.layout.view_on_map);
        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        // Construct a FusedLocationProviderClient.

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Log.d("Success", "map fragment");

        mockupTasks=new ArrayList<Location>();
        Location loc1 = new Location("");
        loc1.setLatitude(53.5283d);
        loc1.setLongitude(-113.5296d);
        mockupTasks.add(loc1);
        Location loc2 = new Location("");
        loc2.setLatitude(53.5233d);
        loc2.setLongitude(-113.5296d);
        mockupTasks.add(loc2);
        Location loc3 = new Location("");
        loc3.setLatitude(53.5253d);
        loc3.setLongitude(-113.5316d);
        mockupTasks.add(loc3);
        Location loc4 = new Location("");
        loc4.setLatitude(53.5243d);
        loc4.setLongitude(-113.5156d);
        mockupTasks.add(loc4);

        //settle signup button
        Button showListButton = (Button) findViewById(R.id.show_list);

        showListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setResult(RESULT_OK);

                Log.d("Starting ", "new activity");
                Intent intent = new Intent(ProviderMapActivity.this, ProviderMainActivity.class);
                intent.putExtra("userName",userName);
                startActivity(intent);

            }
        });



    }

    private void displayTaskLocations() {
        mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);


        for (int i=0;i<mockupTasks.size();i++){
            Log.d(TAG,"Adding marker "+i+", lat: "+mockupTasks.get(i).getLatitude()+", long: "+mockupTasks.get(i).getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(mockupTasks.get(i).getLatitude(), mockupTasks.get(i).getLongitude()))
                    .anchor(0.5f,0.5f)
                    .title(String.valueOf(i)));
            marker.showInfoWindow();
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker){
        Log.d(TAG,"Clicked on marker "+marker.getTitle());
        return true;
    }

    private void getLocationPermission() {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        Log.d("Requesting", "Permission");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }


    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                Log.d(TAG,"Location permission granted");
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                Log.d(TAG,"Location permission denied");
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }



    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();

                mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {


                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Log.d(TAG, "Longtitude 1: " + String.valueOf(location.getLongitude()));
                            Log.d(TAG, "Latitude 1: " + String.valueOf(location.getLatitude()));
                        }
                    }


                });



                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            // Set the map's camera position to the current location of the device.
                            Log.d(TAG, "Current location good.");

                            mLastKnownLocation = task.getResult();
                            Log.d(TAG, "Latitude: "+String.valueOf(mLastKnownLocation.getLatitude()));
                            Log.d(TAG, "Longitude: "+String.valueOf(mLastKnownLocation.getLongitude()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }





    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Log.d(TAG,"onMapReady");
        getLocationPermission();
        updateLocationUI();
        getDeviceLocation();
        Log.d(TAG,"onMapReady finished");
        /*mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(53.5283d, -113.5296d))
                        .anchor(0.5f,0.5f)
                        .title("0"));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.5293d, -113.5296d))
                .anchor(0.5f,0.5f)
                .title("1"));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.5293d, -113.5266d))
                .anchor(0.5f,0.5f)
                .title("2"));*/
        displayTaskLocations();
        //getDeviceLocation();
        /*mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/


    }


}
