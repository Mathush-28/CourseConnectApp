package com.example.courseconnectapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisteredCourses extends AppCompatActivity {

    private RegisteredCoursesDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registered_courses);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new RegisteredCoursesDBHelper(this);

        LinearLayout layout = findViewById(R.id.layout_buttons);

        Cursor cursor = dbHelper.getAllRegisteredCourses();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String courseName = cursor.getString(cursor.getColumnIndex(RegisteredCoursesDBHelper.COLUMN_COURSE_NAME));
                int courseId = cursor.getInt(cursor.getColumnIndex(RegisteredCoursesDBHelper.COLUMN_ID));

                Button button = new Button(this);

                button.setText(courseName);
                button.setTextSize(22);
                button.setOnClickListener(v -> {
                    Intent intent = new Intent(RegisteredCourses.this, RegisteredCourseDetail.class);
                    intent.putExtra("COURSE_ID", courseId);
                    startActivity(intent);
                });

                layout.addView(button);
            } while (cursor.moveToNext());
            cursor.close();
        }


    }
}