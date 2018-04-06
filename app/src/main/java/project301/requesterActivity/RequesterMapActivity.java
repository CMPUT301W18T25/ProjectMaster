package project301.requesterActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import project301.R;

import project301.controller.TaskController;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * @classname : RequesterMapActivity
 * @class Detail :
 *
 * @Date :   18/03/2018
 * @author : Julian Stys
 * @author : Xingyuan Yang
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */


@SuppressWarnings({"ALL", "ConstantConditions"})
public class RequesterMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private String userId;

    private static final String TAG = RequesterMapActivity.class.getSimpleName();

    private Boolean mLocationPermissionGranted;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=1;
    private static final int DEFAULT_ZOOM = 15;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";


    private Location mLastKnownLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private final LatLng mDefaultLocation = new LatLng(53.5273, -113.5296);


    private ArrayList<project301.Task> taskList;

    // Testing variables
    private ArrayList<Location> mockupTasks;



    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();
        //noinspection ConstantConditions,ConstantConditions
        userId = intent.getExtras().get("userId").toString();

        setContentView(R.layout.view_on_map_req);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button showListButton = (Button) findViewById(R.id.go_back);
        showListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Go Back pressed");
                Intent intent = new Intent(RequesterMapActivity.this, RequesterAllListActivity.class);
                intent.putExtra("userId",userId);

                startActivity(intent);


                startActivity(intent);
            }
        });
    }

    /**
     * Requests permission from user for app to access the device location. The user
     * only needs to give permission once, and then future launches will automatically
     * have permission
     */
    private void getLocationPermission() {
        mLocationPermissionGranted=false;
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


    /**
     * Manages the result from the permission request from getLocationPermission()
     * Only ACCESS_FINE_LOCATION is needed and used.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;

        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }


    /**
     * Updates the user's location and sets the map camera to this location. Only works
     * if user has given the app permission to access the current location
     *
     */
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted == null){
                return;
            }
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


    /**
     * Get the location of the device. On a virtual device, this location is set manually
     * (see Google Map Setup page on wiki), and on Android phones will get the location
     * automatically. Assumes that the user has given the app permission to access the location
     *
     */
    private void getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();

                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            displayTaskLocations();

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
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

    /**
     * Saves the current state of the map upon app closure
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }


    /**
     * For each task with a location, will display a marker on the map.
     * The markers will able to be pressed, which will transition to the
     * ProviderTaskBidActivity
     *
     * Currently, the tasks are ArrayList<Location>, which when clicked on,
     * calls onMarkerClick and Logs the id of the pressed marker
     */
    private void displayTaskLocations() {
        Log.d(TAG,"displayTaskLocations");
        mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);

        for (int i = 0; i < taskList.size(); i++) {
            project301.Task currTask = taskList.get(i);

            if (currTask.getTasklgtitude() != null
                    && currTask.getTasklatitude() != null
                    && currTask.getTaskStatus() != "done") {

                if (getTaskDistance(currTask) <= 5000) {

                   Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(currTask.getTasklatitude(), currTask.getTasklgtitude()))
                            .anchor(0.5f, 0.5f)
                            .title(currTask.getTaskName())
                    );
                    // Make bidden tasks have blue icon
                    if (currTask.getTaskStatus().equals("bidden")){
                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                    }
                    // Make assigned task have a green icon
                    else if (currTask.getTaskStatus().equals("assigned")) {
                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    }
                    Log.d(TAG, "Adding marker task name: " + marker.getTitle());
                    marker.setTag(i);
                    marker.showInfoWindow();
                } else {
                    Log.d(TAG, "Task is located too far away: " + currTask.getTaskName());
                }

            } else {
                Log.d(TAG, "No location for: " + taskList.get(i).getTaskName());

            }
        }
    }
    // source: https://stackoverflow.com/questions/2741403/get-the-distance-between-two-geo-points
    private double getTaskDistance(project301.Task currentTask){
        Location taskLocation = new Location("");
        taskLocation.setLatitude(currentTask.getTasklatitude());
        taskLocation.setLongitude(currentTask.getTasklgtitude());

        double distance = taskLocation.distanceTo(mLastKnownLocation);
        return distance;
    }

    /**
     * Override the behavior when a user clicks on a map marker. Right now, it only
     * logs the ID of the marker that was pressed, but will eventually transition to
     * ProviderTaskBidActivity once the Task object has been implemented fully.
     *
     * @param marker
     * @return
     */
    @Override
    public boolean onMarkerClick(Marker marker){
        int markerIndex = (Integer) marker.getTag();
        project301.Task clickedTask = taskList.get(markerIndex);
        Log.d(TAG,"Clicked on marker "+String.valueOf(markerIndex));
        Log.d(TAG,"Task info: "+clickedTask.getTaskName());
        Log.d(TAG,"Task info: "+clickedTask.getTaskAddress());



        Intent info1 = new Intent(RequesterMapActivity.this, RequesterViewTaskRequestActivity.class);
        info1.putExtra("info", markerIndex);
        info1.putExtra("userId",userId);
        startActivity(info1);

        /*Intent info1 = new Intent(RequesterMapActivity.this, ProviderTaskBidActivity.class);
        info1.putExtra("info", markerIndex);
        info1.putExtra("status","request");
        info1.putExtra("userId",userId);
        startActivity(info1);*/
        return true;
    }


    /**
     * onMapReady is called when the map has been initialized and connected to the current view.
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        getLocationPermission();
        updateLocationUI();
        getDeviceLocation();
        getAllTaksInfo();

        getAllTaksInfo();
        Log.d(TAG, "getAllTaskInfo complete");



        // Don't call displayTaskLocations for now since it is used only for testing at the moment
        /*displayTaskLocations();*/
    }

    private void getAllTaksInfo() {

        TaskController.searchAllTasksOfThisRequester search = new TaskController.searchAllTasksOfThisRequester();
        search.execute(userId);
        taskList = new ArrayList<project301.Task>();
        ArrayList<project301.Task> searchedTask = new ArrayList<>();
        try {
            searchedTask = search.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        taskList.addAll(searchedTask);
        for (int i =0;i<taskList.size();i++){
            Log.d(TAG,"Requester TASK: "+taskList.get(i).getTaskName()+", "+taskList.get(i).getTaskStatus());
        }
    }

}
