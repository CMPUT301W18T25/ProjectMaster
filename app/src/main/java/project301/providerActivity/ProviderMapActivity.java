package project301.providerActivity;

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

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.google.android.gms.location.FusedLocationProviderClient;


/**
 * ProviderMapActivity handles the map activity for the provider. The main purpose of
 * this task is too display all tasks within 5km of the provider on the map UI, and
 * allow the provider to click on each task to view further information. This activity
 * first asks user for permission to access the devices location. It then gets the current
 * location of the user and navigates the map CameraActivity to this location.
 *
 * Currently, the Task class doesn't have functionality for location, so a 'mockupTasks'
 * of ArrayList<Locations> is used to test the markers of each task.
 *
 * Source: The majority of the map code was implemented using the Google developer documentation
 * (https://developers.google.com/maps/documentation/android-api/start)
 *
 * @Date :   18/03/2018
 * @author : Xingyuan Yang
 * @author : Julian Stys
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */


@SuppressWarnings({"ALL", "ConstantConditions"})
public class ProviderMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private project301.Task projectTask;
    private Task apiTask;

    private GoogleMap mMap;
    private String userName;
    private String userId;

    private Boolean mLocationPermissionGranted;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=1;
    private static final int DEFAULT_ZOOM = 15;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";


    private CameraPosition mCameraPosition;
    private static final String TAG = ProviderMapActivity.class.getSimpleName();

    private Location mLastKnownLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    // Default location is set to the University of Alberta
    private final LatLng mDefaultLocation = new LatLng(53.5273, -113.5296);

    private ArrayList<project301.Task> taskList;
    //private LatLng location;

    // Testing variables
    private ArrayList<Location> mockupTasks;



    /**
     * The onCreate method checks if a previous state exists, and sets the last
     * known location if a previous state is found. The FusedLocationProviderClient
     * is initialized, and is responsible for getting the current location of the
     * user. The content view is set to view_on_map.xml, and the map fragment is
     * initialized. The 'Show List' button is implemented which goes back to the
     * previous Provider Task view.
     *
     * @param savedInstanceState
     */
    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationPermissionGranted = false;

        // Checks if previous saved state exists
        if (savedInstanceState != null) {
            Log.d(TAG,"savedInstanceState Found");
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        final Intent intent = getIntent();
        //noinspection ConstantConditions,ConstantConditions
        userId = intent.getExtras().get("userId").toString();

        setContentView(R.layout.view_on_map);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Show List button initialization and connection
        Button showListButton = (Button) findViewById(R.id.show_list);
        showListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Show List pressed");
                Intent intent = new Intent(ProviderMapActivity.this,
                        ProviderMainActivity.class);
                intent.putExtra("userId",userId);
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
     * Get the info of all task
     */

    private void getAllTaksInfo() {

        TaskController.searchAllRequestingTasks search = new TaskController.searchAllRequestingTasks();
        search.execute();
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
     * Updates the user's location and sets the map CameraActivity to this location. Only works
     * if user has given the app permission to access the current location
     *
     */
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
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
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
                            // Set the map's CameraActivity position to the current location of the device.
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

        mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);

        for (int i=0;i<taskList.size();i++){
            project301.Task currTask=taskList.get(i);

            if (currTask.getTasklgtitude() != null
                    && currTask.getTasklatitude() != null){

                if (getTaskDistance(currTask) <= 5000){
                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(currTask.getTasklatitude(), currTask.getTasklgtitude()))
                            .anchor(0.5f,0.5f)
                            .title(currTask.getTaskName())
                    );
                    Log.d(TAG,"Adding marker task name: "+marker.getTitle());
                    marker.setTag(i);
                    marker.showInfoWindow();
                }
                else{
                    Log.d(TAG,"Task is located too far away: "+currTask.getTaskName());
                }

            }
            else{
                Log.d(TAG,"No location for: "+taskList.get(i).getTaskName());

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

        Intent info1 = new Intent(ProviderMapActivity.this, ProviderTaskBidActivity.class);

        info1.putExtra("taskId",clickedTask.getId());
        //provide the task status for the next activity
        info1.putExtra("info", markerIndex);
        info1.putExtra("status","request");
        info1.putExtra("userId",userId);
        startActivity(info1);
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

        getAllTaksInfo();
        getLocationPermission();
        updateLocationUI();
        getDeviceLocation();

        // Don't call displayTaskLocations for now since it is used only for testing at the moment
        //displayTaskLocations();
    }


}