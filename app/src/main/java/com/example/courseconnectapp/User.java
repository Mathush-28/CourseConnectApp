package com.example.courseconnectapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class User extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.user), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button myCoursesButton = (Button) findViewById(R.id.btn_my_courses);
        Button availableCoursesButton = findViewById(R.id.btn_available_courses);

        myCoursesButton.setOnClickListener(v -> {
            Intent intent = new Intent(User.this, RegisteredCourses.class);
            startActivity(intent);
        });

        availableCoursesButton.setOnClickListener(v -> {
            Intent intent = new Intent(User.this, AvailableCourses.class);
            startActivity(intent);
        });
    }
}
