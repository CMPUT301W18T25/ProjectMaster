package com.example.mayingnan.project301.providerActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.mayingnan.project301.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;
import java.util.Map;


/**
 * Manipulates the map once available.
 * This callback is triggered when the map is ready to be used.
 * This is where we can add markers or lines, add listeners or move the camera. In this case,
 * we just add a marker near Sydney, Australia.
 * If Google Play services is not installed on the device, the user will be prompted to install
 * it inside the SupportMapFragment. This method will only be triggered once the user has
 * installed Google Play services and returned to the app.

 @Override
 public void onMapReady(GoogleMap googleMap) {
 MapsInitializer.initialize(getContext());

 mGoogleMap = googleMap;
 googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

 googleMap.addMarker(new MarkerOptions().position(new LatLng(40.7,-74.04)).title("Statue of liberty").snippet("asdf"));

 CameraPosition Liberty = CameraPosition.builder().target(new LatLng(40.7,-74.04)).zoom(16).bearing(0).tilt(45).build();

 googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));

 }
    }

}

/*public class ProviderMapActivity extends Fragment implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private View mView;

    public ProviderMapActivity() {
        // Empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        mView = inflater.inflate(R.layout.view_on_map, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) mView.findViewById(R.id.map);
        if (mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        googleMap.addMarker(new MarkerOptions().position(new LatLng(40.7,-74.04)).title("Statue of liberty").snippet("asdf"));

        CameraPosition Liberty = CameraPosition.builder().target(new LatLng(40.7,-74.04)).zoom(16).bearing(0).tilt(45).build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));

    }
}*/


public class ProviderMapActivity extends Fragment implements OnMapReadyCallback {
    private GoogleMap mGoogleMap;
    private MapView mMapView;

    private View mView;

    private ArrayList tasklist;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        mView = inflater.inflate(R.layout.view_on_map, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mView.findViewById(R.id.map);
        if (mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap){
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(40,-74)).title("Statue of liberty").snippet("random text"));

        CameraPosition Liberty = CameraPosition.builder().target(new LatLng(40,-74)).zoom(16).bearing(0).tilt(45).build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));

    }

    public void showList(){}
    public void editInfo(){}
    public void OnClick(){}


}
