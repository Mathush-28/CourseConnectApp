package com.example.courseconnectapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME = "courses.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_COURSES = "courses";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_COST = "cost";
    private static final String COLUMN_DURATION = "duration";
    private static final String COLUMN_START_DATE = "startDate";
    private static final String COLUMN_DUE_DATE = "dueDate";
    private static final String COLUMN_BRANCHES = "branches";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_COURSES_TABLE = "CREATE TABLE " + TABLE_COURSES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_COST + " TEXT,"
                + COLUMN_DURATION + " TEXT,"
                + COLUMN_START_DATE + " TEXT,"
                + COLUMN_DUE_DATE + " TEXT,"
                + COLUMN_BRANCHES + " TEXT" + ")";
        db.execSQL(CREATE_COURSES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        onCreate(db);
    }

    public void addCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, course.getName());
        values.put(COLUMN_COST, course.getCost());
        values.put(COLUMN_DURATION, course.getDuration());
        values.put(COLUMN_START_DATE, course.getStartDate());
        values.put(COLUMN_DUE_DATE, course.getDueDate());
        values.put(COLUMN_BRANCHES, course.getBranches());

        db.insert(TABLE_COURSES, null, values);
        db.close();
    }

    public List<Course> getAllCourses() {
        List<Course> courseList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_COURSES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Course course = new Course(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COST)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DURATION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DUE_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BRANCHES))
                );
                courseList.add(course);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return courseList;
    }
}

