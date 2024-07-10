package com.example.courseconnectapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//public class Gfragment2 extends Fragment {
//
//        @Override
//        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//            // Inflate the layout for this fragment
//            return inflater.inflate(R.layout.fragment_gfragment2, container, false);
//        }
//    }



public class GFragment2 extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gfragment2, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        addMarkers();
    }

    private void addMarkers() {
        // Define coordinates for the locations
        LatLng colombo = new LatLng(6.9271, 79.8612);
        LatLng jaffna = new LatLng(9.6615, 80.0255);
        LatLng galle = new LatLng(6.0535, 80.2200);
        LatLng gampaha = new LatLng(7.0916, 79.9996);
        LatLng kottawa = new LatLng(6.8401, 79.9672);

        // Add markers to the map
        mMap.addMarker(new MarkerOptions().position(colombo).title("Colombo"));
        mMap.addMarker(new MarkerOptions().position(jaffna).title("Jaffna"));
        mMap.addMarker(new MarkerOptions().position(galle).title("Galle"));
        mMap.addMarker(new MarkerOptions().position(gampaha).title("Gampaha"));
        mMap.addMarker(new MarkerOptions().position(kottawa).title("Kottawa"));

        // Move the camera to the first location and set zoom level
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(colombo, 8));
    }
}
