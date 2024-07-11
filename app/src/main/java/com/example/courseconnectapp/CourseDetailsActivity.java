package com.example.courseconnectapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseDetailsActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private TextView tvCourseNameValue;
    private TextView tvCostValue;
    private TextView rgBranch;
    private TextView startDateValue;
    private TextView dueDateValue;
    private TextView duration;
    private Button btnRegister;
    private MapView mapView;
    private GoogleMap googleMap;

    private DatabaseHelper databaseHelper;
    private Intent intent;

    private final LatLng userLocation = new LatLng(6.9271, 79.8612); // Example user location (Colombo)
    private final Map<String, LatLng> courseLocations = new HashMap<String, LatLng>() {{
        put("Colombo", new LatLng(6.9271, 79.8612));
        put("Jaffna", new LatLng(9.6615, 80.0255));
        put("Galle", new LatLng(6.0535, 80.2200));
        put("Gampaha", new LatLng(7.0916, 79.9996));
        put("Kottawa", new LatLng(6.8401, 79.9672));
    }};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvCourseNameValue = findViewById(R.id.tvCourseNameValue);
        tvCostValue = findViewById(R.id.tvCostValue);
        rgBranch = findViewById(R.id.textView);
        startDateValue = findViewById(R.id.tvstartvalue);
        dueDateValue = findViewById(R.id.tvDueDateValue);
        duration = findViewById(R.id.durationValue);
        btnRegister = findViewById(R.id.btnAddToCart);
        mapView = findViewById(R.id.mapView);

        databaseHelper = new DatabaseHelper(this);

        String courseName = getIntent().getStringExtra("courseName");
        loadCourseData(courseName);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this::onMapReady);

        btnRegister.setOnClickListener(v -> {
            intent = new Intent(CourseDetailsActivity.this, RegistrationActivity.class);
            intent.putExtra("courseName", courseName);
            intent.putExtra("finalPrice", tvCostValue.getText().toString());
            startActivity(intent);
        });
    }

    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        // Enable the My Location layer if the permission has been granted.
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request location permission
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            googleMap.setMyLocationEnabled(true);
        }

        // Add logic to show the map and mark locations
        showNearestCourseLocation();
    }

    private void loadCourseData(String courseName) {
        List<Course> courses = databaseHelper.getAllCourses();
        for (Course course : courses) {
            if (course.getName().equals(courseName)) {
                tvCourseNameValue.setText(course.getName());
                tvCostValue.setText(course.getCost());
                rgBranch.setText(course.getBranches());
                startDateValue.setText(course.getStartDate());
                dueDateValue.setText(course.getDueDate());
                duration.setText(course.getDuration());

                intent = new Intent(CourseDetailsActivity.this, RegistrationActivity.class);
                intent.putExtra("courseName", courseName);
                intent.putExtra("finalPrice", course.getCost());
                break;
            }
        }
    }

    private void showNearestCourseLocation() {
        if (googleMap == null) return;

        // Add marker for user's location
        googleMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10));

        // Find and add marker for the nearest course location
        String nearestLocation = findNearestLocation(userLocation);
        LatLng nearestLatLng = courseLocations.get(nearestLocation);

        if (nearestLatLng != null) {
            googleMap.addMarker(new MarkerOptions().position(nearestLatLng).title(nearestLocation));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nearestLatLng, 10));
        }
    }

    private String findNearestLocation(LatLng userLocation) {
        String nearestLocation = null;
        float minDistance = Float.MAX_VALUE;

        for (Map.Entry<String, LatLng> entry : courseLocations.entrySet()) {
            float[] results = new float[1];
            Location.distanceBetween(userLocation.latitude, userLocation.longitude,
                    entry.getValue().latitude, entry.getValue().longitude, results);
            float distance = results[0];
            if (distance < minDistance) {
                minDistance = distance;
                nearestLocation = entry.getKey();
            }
        }
        return nearestLocation;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}