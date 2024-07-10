package com.example.courseconnectapp;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class MyViewModel extends AndroidViewModel {
    private MutableLiveData<List<Course>> courses;
    private DatabaseHelper databaseHelper;

    public MyViewModel(Application application) {
        super(application);
        databaseHelper = new DatabaseHelper(application);
    }

    public LiveData<List<Course>> getCourses() {
        if (courses == null) {
            courses = new MutableLiveData<>();
            loadCourses();
        }
        return courses;
    }

    public void addCourse(Course course) {
        databaseHelper.addCourse(course);
        loadCourses(); // Refresh the courses list
    }

    private void loadCourses() {
        List<Course> courseList = databaseHelper.getAllCourses();
        courses.setValue(courseList);
    }


//    public MutableLiveData<List<Course>> getCourses() {
//        if (courses == null) {
//            courses = new MutableLiveData<>(new ArrayList<>());
//        }
//        return courses;
//    }

//    public void addCourse(Course course) {
//        List<Course> currentCourses = courses.getValue();
//        if (currentCourses == null) {
//            currentCourses = new ArrayList<>();
//        }
//        currentCourses.add(course);
//        courses.setValue(currentCourses);
//    }
}
